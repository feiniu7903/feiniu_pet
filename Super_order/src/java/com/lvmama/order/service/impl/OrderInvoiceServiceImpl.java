package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ord.OrdInvoice;
import com.lvmama.comm.bee.po.ord.OrdInvoiceRelation;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.service.ord.OrderPersonService;
import com.lvmama.comm.bee.vo.ord.Invoice;
import com.lvmama.comm.bee.vo.ord.Person;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.InvoiceUtil;
import com.lvmama.comm.utils.Pair;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ord.dao.OrdRefundMentDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderInvoiceDAO;
import com.lvmama.order.dao.OrderInvoiceRelationDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.dao.OrderPersonDAO;
import com.lvmama.order.service.InvoiceException;
import com.lvmama.order.service.OrderInvoiceService;

/**
 * 订单发票服务.
 *
 * <pre></pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderInvoiceServiceImpl extends OrderServiceImpl implements OrderInvoiceService {

	private static Logger logger = Logger.getLogger(OrderInvoiceServiceImpl.class);
	
	private OrderDAO orderDAO;
	
	private OrderPersonDAO orderPersonDAO;
	
	private OrderInvoiceDAO orderInvoiceDAO;
	
	private OrderInvoiceRelationDAO orderInvoiceRelationDAO;
	
	private OrderPersonService orderPersonService;
		
	private OrdRefundMentDAO ordRefundMentDAO;
	
	private OrderItemProdDAO orderItemProdDAO;
	
	private PayPaymentService payPaymentService;
	


	/**
	 * @param ordRefundMentDAO the ordRefundMentDAO to set
	 */
	public void setOrdRefundMentDAO(OrdRefundMentDAO ordRefundMentDAO) {
		this.ordRefundMentDAO = ordRefundMentDAO;
	}

	public void setOrderInvoiceDAO(OrderInvoiceDAO orderInvoiceDAO) {
		this.orderInvoiceDAO = orderInvoiceDAO;
	}

	public void setOrderPersonDAO(OrderPersonDAO orderPersonDAO) {
		this.orderPersonDAO = orderPersonDAO;
	}

	
	public void setOrderDAO(OrderDAO orderDAO) {
		this.orderDAO = orderDAO;
	}

	
	@Override
	public boolean delete(Long invoiceId, String operatorId) {
		OrdInvoice ordInvoice = orderInvoiceDAO.selectByPrimaryKey(invoiceId);
		orderInvoiceDAO.deleteByPrimaryKey(invoiceId);
		insertLog(invoiceId, "ORD_INVOICE", ordInvoice.getOrderId(), "ORD_ORDER", operatorId, 
				"删除订单发票", Constant.COM_LOG_ORDER_EVENT.deleteOrderInvoice.name(), "删除订单发票:" +invoiceId );
		return true;
	}
	
	/**
	 * 判断一个订单是否已经存在未取消的发票
	 * @param orderId
	 * @return true为已经存在
	 */
	boolean hasNotCancelInvoice(Long orderId){
		return orderInvoiceDAO.selectNotCancelInvoiceCountByOrderId(orderId,Constant.INVOICE_STATUS.CANCEL.name())>0L;
	}
	
	@Override
	public void insert(List<Pair<Invoice,Person>> invoices,Long orderId,String operatorId)throws InvoiceException{
		OrdOrder order=orderDAO.selectByPrimaryKey(orderId);
		if(order==null||order.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.name())){
			throw new IllegalArgumentException("This orderId:"+orderId+" cancel or null");
		}
		if(StringUtils.equals("true",order.getNeedInvoice())){
			throw new IllegalArgumentException("This orderId:"+orderId+" cancel is billed.");
		}
		if(hasNotCancelInvoice(orderId)){	
			throw new IllegalArgumentException("This orderId:"+orderId+" form includes the receipt which has not cancelled, cannot increase");
		}	
		long orderAmount=order.getActualPay()-getSumCompensationAndRefundment(orderId);
		if(orderAmount<1){//如果订单的可开票金额小于1时异常抛出
			throw new IllegalArgumentException("订单:"+orderId+", 当前的金额不可以开出发票");
		}
		
		orderAmount-=getOrderInvoiceAmountNotInvoiceId(orderId,null);
		long total=0L;
		for(Pair<Invoice,Person> invoice:invoices){
			OrdInvoice ordInvoice = insertInvoiceInfo(invoice, order.getUserId(), operatorId);
			total+=ordInvoice.getAmount();
			insertRelation(ordInvoice.getInvoiceId(), orderId);		
		}
		if(total>orderAmount){
			throw new RuntimeException("开票的总金额超出了订单的金额");
		}
		
		if(!StringUtils.equals("true",order.getNeedInvoice())){
			order.setNeedInvoice("true");
			orderDAO.updateByPrimaryKey(order);
		}
	}
	/**
	 * 写入发票并记日志
	 * @param invoice
	 * @param userId
	 * @param operatorId
	 * @return
	 */
	private OrdInvoice insertInvoiceInfo(Pair<Invoice,Person> invoice,String userId,String operatorId){
		OrdInvoice ordInvoice=converInvoice(invoice.getFirst());
		ordInvoice.setUserId(userId);
		ordInvoice.setLogisticsStatus(Constant.INVOICE_LOGISTICS.NONE.name());
//		Long amount=ordInvoice.getAmount();
//		ordInvoice.setAmount(amount*100);
		orderInvoiceDAO.insert(ordInvoice);	
		
		if (ordInvoice != null) {
			if (!Constant.DELIVERY_TYPE.SELF.name().equals(invoice.getFirst().getDeliveryType()) && invoice.getSecond() != null) {// 如果不是自取的情况下需要写入用户信息
				orderPersonService.insertInvoicePerson(invoice.getSecond(), ordInvoice.getInvoiceId(), operatorId);
			}
		}
		insertLog(ordInvoice.getInvoiceId(), "ORD_INVOICE", ordInvoice.getOrderId(), "ORD_ORDER", operatorId, 
				"新增订单发票", Constant.COM_LOG_ORDER_EVENT.insertOrderInvoice.name(), "新增订单发票:" +ordInvoice.getInvoiceId() );
		
		return ordInvoice;
	}
	
	@Override
	public OrdInvoice insert(Pair<Invoice,Person> invoice, List<Long> orderIds,String operatorId)throws InvoiceException {
		long amount=0;
		String userId=null;
		for(Long orderId:orderIds){
			OrdOrder order=orderDAO.selectByPrimaryKey(orderId);
			//为false直接退出，不操作发票
			if(order==null||order.getOrderStatus().equals(Constant.ORDER_STATUS.CANCEL.name())){
				throw new InvoiceException("This orderId:"+orderId+" cancel or null");
			}
			if(StringUtils.equals("true",order.getNeedInvoice())||StringUtils.equals("part", order.getNeedInvoice())){
				throw new InvoiceException("This orderId:"+orderId+" cancel is billed.");
			}
			if(hasNotCancelInvoice(orderId)){	
				throw new InvoiceException("This orderId:"+orderId+" form includes the receipt which has not cancelled, cannot increase");
			}
			if(StringUtils.isEmpty(userId)){
				userId=order.getUserId();//发票用户ID以第一个订单的userId				
			}
			//去除奖金支付金额
			long orderAmount=order.getActualPay()-getSumCompensationAndRefundment(orderId);
			if(orderAmount<1){//如果订单的可开票金额小于1时异常抛出
				throw new InvoiceException("订单:"+orderId+", 当前的金额不可以开出发票");
			}
			amount+=orderAmount;//以应用金额为准
		}
		
		invoice.getFirst().setAmount(amount);
		OrdInvoice ordInvoice = insertInvoiceInfo(invoice,userId,operatorId);	
		for(Long orderId:orderIds){			
			insertRelation(ordInvoice.getInvoiceId(), orderId);			
			OrdOrder order=orderDAO.selectByPrimaryKey(orderId);			
			if(!StringUtils.equals("true",order.getNeedInvoice())){
				order.setNeedInvoice("true");
				orderDAO.updateByPrimaryKey(order);
			}		
		}
		
		return ordInvoice;
	}
	
	
	
	private OrdInvoice converInvoice(Invoice invoice){
		OrdInvoice ordInvoice = new OrdInvoice();
		ordInvoice.setTitle(invoice.getTitle());
		ordInvoice.setDetail(invoice.getDetail());
		ordInvoice.setMemo(invoice.getMemo());
		ordInvoice.setAmount(invoice.getAmount());
		ordInvoice.setCompany(invoice.getCompany());
//		ordInvoice.setOrderId(orderId);//去掉这个值
		ordInvoice.setStatus(Constant.INVOICE_STATUS.UNBILL.name());
		ordInvoice.setDeliveryType(invoice.getDeliveryType());
		return ordInvoice;
	}
	
	/**
	 * 添加关联商品
	 * @param invoiceId
	 * @param orderId
	 */
	private void insertRelation(Long invoiceId,Long orderId){
		
		OrdInvoiceRelation relation=new OrdInvoiceRelation();
		relation.setOrderId(orderId);
		relation.setInvoiceId(invoiceId);
		orderInvoiceRelationDAO.insert(relation);
	}	
	
	/**
	 * 取到 退款与补偿的费用,发票当中使用
	 * 现在添加了保险也不开发票
	 * @param order
	 * @return 
	 */
	public long getSumCompensationAndRefundment(Long orderId){
		OrdOrder order=orderDAO.selectByPrimaryKey(orderId);
		long sum=0;

		if(order.getBonusPaidAmount()!=null){
			sum = order.getBonusPaidAmount();
		}
		
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("status", Constant.REFUNDMENT_STATUS.REFUNDED.name());
		List<OrdRefundment> listOrdRefundment = ordRefundMentDAO.findOrdRefundByParam(param,0,ordRefundMentDAO.findOrdRefundByParamCount(param).intValue());
		if(CollectionUtils.isNotEmpty(listOrdRefundment)){
			for (OrdRefundment refundment : listOrdRefundment) {
				if (Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name()
						.equalsIgnoreCase(refundment.getRefundType())) {
					sum += refundment.getAmount();
				}				
			}
		}
		//找出用户为储值卡支付的金额
		Long sumCardAmount = payPaymentService.selectCardPaymentSuccessSumAmount(orderId);
		sum = sum + sumCardAmount;
		
		List<OrdOrderItemProd> list = orderItemProdDAO.selectByOrderId(orderId);
		for(OrdOrderItemProd item:list){
			if(item.isOtherProductType()&&StringUtils.equals(Constant.SUB_PRODUCT_TYPE.INSURANCE.name(), item.getSubProductType())){
				sum+=item.getPrice()*item.getQuantity();
			}
		}
		return sum;
	}
	
	/**
	 * 获取订单退款金额
	 * 
	 * @param orderId
	 * @return
	 */
	public long getRefundAmountByOrderId(Long orderId, String sysCode) {
		long sum=0;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("orderId", orderId);
		param.put("status", Constant.REFUNDMENT_STATUS.REFUNDED.name());
		if(StringUtils.isNotBlank(sysCode)&&Constant.COMPLAINT_SYS_CODE.VST.name().equals(sysCode)){
			param.put("sysCode", Constant.COMPLAINT_SYS_CODE.VST.name());
			List<OrdRefundment> listOrdRefundment = ordRefundMentDAO.findVstOrdRefundByParam(param,0,ordRefundMentDAO.findVstOrdRefundByParamCount(param).intValue());
			if(CollectionUtils.isNotEmpty(listOrdRefundment)){
				for (OrdRefundment refundment : listOrdRefundment) {
					if (Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name()
							.equalsIgnoreCase(refundment.getRefundType())) {
						sum += refundment.getAmount();
					}				
				}
			}
		}else{
			List<OrdRefundment> listOrdRefundment = ordRefundMentDAO.findOrdRefundByParam(param,0,ordRefundMentDAO.findOrdRefundByParamCount(param).intValue());
			if(CollectionUtils.isNotEmpty(listOrdRefundment)){
				for (OrdRefundment refundment : listOrdRefundment) {
					if (Constant.REFUNDMENT_TYPE.ORDER_REFUNDED.name()
							.equalsIgnoreCase(refundment.getRefundType())) {
						sum += refundment.getAmount();
					}				
				}
			}
		}
		
		return sum;
	}
	public boolean insert(Invoice invoice, Long orderId, String operatorId, boolean needOrderUpdate) {
		OrdInvoice ordInvoice=converInvoice(invoice);
		
		OrdOrder order=orderDAO.selectByPrimaryKey(orderId);
		ordInvoice.setAmount(order.getOughtPay()-getSumCompensationAndRefundment(order.getOrderId()));//此处用应付金额
		if(ordInvoice.getAmount()<1){
			return false;
		}
		ordInvoice.setUserId(order.getUserId());
		orderInvoiceDAO.insert(ordInvoice);
		//添加订单与对象之间的关联
		insertRelation(ordInvoice.getInvoiceId(), orderId);
		
		if(needOrderUpdate)
		{			
			if(!StringUtils.equals("true",order.getNeedInvoice())){
				order.setNeedInvoice("true");
				orderDAO.updateByPrimaryKey(order);
			}
		}
		insertLog(ordInvoice.getInvoiceId(), "ORD_INVOICE", ordInvoice.getOrderId(), "ORD_ORDER", operatorId, 
				"新增订单发票", Constant.COM_LOG_ORDER_EVENT.insertOrderInvoice.name(), "新增订单发票:" +ordInvoice.getInvoiceId() );
		
		return true;
	}

	
	@Override
	public List<OrdInvoice> queryInvoiceByOrderId(Long orderId) {
		List<OrdInvoice> invoiceList = orderInvoiceDAO.queryInvoiceByOrderId(orderId);
		
		if(invoiceList == null)
			return new ArrayList<OrdInvoice>();
		else
			return invoiceList;
	}
	
	@Override
	public List<OrdInvoice> queryInvoiceByStatus(String status) {
		List<OrdInvoice> invoiceList = orderInvoiceDAO.queryInvoiceByStatus(status);
		
		if(invoiceList == null)
			return new ArrayList<OrdInvoice>();
		else
			return invoiceList;
	}
	
	@Override
	public boolean updateExpressNo(Long invoiceId, String expressNo,
			String operatorId) {
		OrdInvoice ordInvoice = orderInvoiceDAO.selectByPrimaryKey(invoiceId);		
		if(InvoiceUtil.checkChangeExpressNo(ordInvoice)){
			logger.info("当前的发票"+invoiceId+"不可以变更快递号");
			return false;
		}
		
		StringBuffer content=new StringBuffer();
		if(StringUtils.isNotEmpty(ordInvoice.getExpressNo())){
			content.append("快递单号:");
			content.append(ordInvoice.getExpressNo());
			content.append("至");
			content.append(expressNo);
		}else{
			content.append("添加快递单号:");
			content.append(expressNo);
		}
		ordInvoice.setExpressNo(expressNo);
		if(StringUtils.equals(ordInvoice.getLogisticsStatus(),Constant.INVOICE_LOGISTICS.NONE.name())){
			content.append("更改物流状态至已快递");
			//ordInvoice.setStatus(Constant.INVOICE_STATUS.POST.name());
			ordInvoice.setLogisticsStatus(Constant.INVOICE_LOGISTICS.POST.name());
		}
		
		return update(ordInvoice, operatorId, content.toString());
	}
	
	@Override
	public boolean update(String invoiceNo, Date billDate, Long invoiceId, String operatorId) {		
		OrdInvoice ordInvoice = orderInvoiceDAO.selectByPrimaryKey(invoiceId);		
		if(InvoiceUtil.checkChangeInvoiceNo(ordInvoice)){
			logger.info("当前的发票"+invoiceId+"不可以变更发票号");
			return false;
		}
		StringBuffer content=new StringBuffer("变更 ");
		if(StringUtils.isNotEmpty(ordInvoice.getInvoiceNo())){
			content.append("发票号:");
			content.append(ordInvoice.getInvoiceNo());
			content.append("至");
			content.append(invoiceNo);
		}
		ordInvoice.setInvoiceNo(invoiceNo);
		ordInvoice.setBillDate(billDate);
		
		if(!ordInvoice.getStatus().equals(Constant.INVOICE_STATUS.BILLED.name())){
			
			content.append("之前状态:");
			content.append(ordInvoice.getStatus());
			content.append("至");
			content.append(Constant.INVOICE_STATUS.BILLED.name());
			ordInvoice.setStatus(Constant.INVOICE_STATUS.BILLED.name());
		}
		return update(ordInvoice, operatorId, content.toString());
	}
	
	@Override
	public boolean update(String status, Long invoiceId, String operatorId) {
		OrdInvoice ordInvoice = orderInvoiceDAO.selectByPrimaryKey(invoiceId);
		if(ordInvoice==null){
			return false;
		}
		ordInvoice.setStatus(status);
		if(status.equals(Constant.INVOICE_STATUS.BILLED)){
			ordInvoice.setBillDate(new Date());
		}
		String updateContent = "set status = " + status;
		if (StringUtils.equals(status, Constant.INVOICE_STATUS.CANCEL.name())
				&& StringUtils.equals("SYSTEM", operatorId)) {
			updateContent += " 因订单当中出现0或负数的发票金额自动取消";
		}
		return update(ordInvoice, operatorId, updateContent);
	}
	
   /**
    * 更新为红冲
    */
	@Override
	public boolean updateRedFlag(OrdInvoice ordInvoice,String operatorId) {
		boolean flag = orderInvoiceDAO.updateRedFlag(ordInvoice);	
		if(flag){
			insertLog(ordInvoice.getInvoiceId(), "ORD_INVOICE", null, null, operatorId, 
					"设置发票红冲", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), " 更新发票红冲状态到 " + (StringUtils.equals("true", ordInvoice.getRedFlag())?"申请":"关闭"));
		}
		return flag;
	}
	
	@Override
	public OrdInvoice selectByPrimaryKey(Long invoiceId) {
		return orderInvoiceDAO.selectByPrimaryKey(invoiceId);
	}

	@Override
	public boolean update(OrdInvoice ordInvoice, String operatorId) {
		return update(ordInvoice, operatorId, null);
	}
	@Override
	public boolean update(OrdInvoice ordInvoice, String operatorId, String updateContent) {
		orderInvoiceDAO.updateByPrimaryKey(ordInvoice);
		insertLog(ordInvoice.getInvoiceId(), "ORD_INVOICE", ordInvoice.getOrderId(), "ORD_ORDER", operatorId, "修改订单发票", Constant.COM_LOG_ORDER_EVENT.updateOrderInvoice.name(), updateContent);
		return true;
	}
	@Override
	public boolean updateNeedInvoice(String needInvoice, Long orderId, String operatorId)
	{
		OrdOrder order = orderDAO.selectByPrimaryKey(orderId);
		if(order == null)
			return false;
		
		order.setNeedInvoice(needInvoice);
		orderDAO.updateByPrimaryKey(order);
		insertLog(orderId, "ORD_ORDER", null, null, operatorId, 
				"设置订单需要发票", Constant.COM_LOG_ORDER_EVENT.updateOrder.name(), " update needInvoice = " + needInvoice );
		if("false".equalsIgnoreCase(needInvoice))
		{
			List<OrdInvoice> ordInvoiceList = queryInvoiceByOrderId(orderId);
			if(ordInvoiceList != null && ordInvoiceList.size() > 0)
			{
				for(OrdInvoice ordInvoice : ordInvoiceList)
				{
					boolean successFlag = delete(ordInvoice.getInvoiceId(), operatorId);
					if(!successFlag)
					{
						logger.info("update NeedInvoice fail: delete ordInvoice fail with invoiceId = " + ordInvoice.getInvoiceId());
						throw new RuntimeException("update NeedInvoice fail: delete ordInvoice fail with invoiceId = " + ordInvoice.getInvoiceId());
					}
				}
			}
			
			if("false".equalsIgnoreCase(order.getPhysical()))
			{
				Map<String, String> params = new HashMap<String, String>();
				params.put("objectId", orderId.toString());
				params.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());
				params.put("personType", Constant.ORD_PERSON_TYPE.ADDRESS.name());
				List<OrdPerson> ordPersonList = orderPersonDAO.queryOrdPersonByParams(params);
				if(ordPersonList != null && ordPersonList.size() > 0)
				{
					for(OrdPerson ordPerson : ordPersonList)
					{
						int row = orderPersonDAO.deleteByPrimaryKey(ordPerson.getPersonId());
						if(row != 1)
						{
							logger.info("update NeedInvoice fail: delete ordPerson fail with ordPersonId = " + ordPerson.getPersonId());
							throw new RuntimeException("update NeedInvoice fail: delete ordInvoice fail with ordPersonId = " + ordPerson.getPersonId());
						}
					}
				}
			}
		}
		
		return true;
		
	}

	@Override
	public List<OrdInvoiceRelation> selectInvoiceRelationListByInvoiceId(Long invoiceId) {
		return orderInvoiceDAO.queryRelationList(invoiceId);
	}

	/**
	 * @param orderInvoiceRelationDAO the orderInvoiceRelationDAO to set
	 */
	public void setOrderInvoiceRelationDAO(
			OrderInvoiceRelationDAO orderInvoiceRelationDAO) {
		this.orderInvoiceRelationDAO = orderInvoiceRelationDAO;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	@Override
	public long selectInvoiceCountByOrderId(Long orderId) {
		return orderInvoiceRelationDAO.selectInvoiceCountByOrderId(orderId);
	}

	public void setOrderPersonService(OrderPersonService orderPersonService) {
		this.orderPersonService = orderPersonService;
	}

	@Override
	public long getOrderInvoiceAmountNotInvoiceId(Long orderId,
			Long excludeInvoiceId) {
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("orderId", orderId);
		if(excludeInvoiceId!=null){
			param.put("excludeInvoiceId", excludeInvoiceId);
		}
		return this.orderInvoiceDAO.getInvoiceAmountSum(param);
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}	
}