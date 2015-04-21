package com.lvmama.ord.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;

import com.lvmama.com.dao.ComCodesetDAO;
import com.lvmama.com.dao.ComLogDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdRefundMentItem;
import com.lvmama.comm.bee.po.ord.OrdRefundment;
import com.lvmama.comm.bee.po.ord.OrdRefundmentItemProd;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sale.OrdRefundMentService;
import com.lvmama.comm.pet.service.sale.OrderRefundService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vst.service.VstOrdOrderService;
import com.lvmama.comm.vst.vo.VstOrdOrderItem;
import com.lvmama.comm.vst.vo.VstOrdOrderVo;
import com.lvmama.ord.dao.OrdRefundMentDAO;
import com.lvmama.order.dao.OrderItemProdDAO;

public class OrdRefundMentServiceImpl implements OrdRefundMentService {
	private OrdRefundMentDAO ordRefundMentDAO;
	private OrderItemProdDAO orderItemProdDAO;
	private OrderService orderServiceProxy;
	private ComCodesetDAO comCodesetDAO;
	/**
	 * 新系统订单服务
	 */
	private VstOrdOrderService vstOrdOrderService;
	/**
	 * 增加退款对像服务.
	 */
	private OrderRefundService orderRefundService;
	protected ComLogDAO comLogDAO;
	private Logger log = Logger.getLogger(this.getClass());

	public ComLogDAO getComLogDAO() {
		return comLogDAO;
	}

	public void setComLogDAO(ComLogDAO comLogDAO) {
		this.comLogDAO = comLogDAO;
	}

	public OrderItemProdDAO getOrderItemProdDAO() {
		return orderItemProdDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}

	public void updateOrdRefundmentByPK(OrdRefundment ordrefundment) {
		ordRefundMentDAO.updateOrdRefundmentByPK(ordrefundment);
	}
	
	public Long findOrdRefundByParamCountSaleFinish(Map param) {
		return this.ordRefundMentDAO.findOrdRefundByParamCountSaleFinish(param);
	}

	public Long findOrdRefundByParamCount(Map param) {
		  if(param!=null && param.get("orderId")!=null){
		 	param.put("orderId", String.valueOf(param.get("orderId")).trim());
		  }
		  if(param.get("sysCode")!=null && Constant.COMPLAINT_SYS_CODE.VST.name().equals(param.get("sysCode").toString())) {
			  return ordRefundMentDAO.findVstOrdRefundByParamCount(param);
		  }else {
			  return ordRefundMentDAO.findOrdRefundByParamCount(param);  
		  }
	 }

	public OrdRefundment findOrdRefundmentById(Long refundmentId) {
		return ordRefundMentDAO.findOrdRefundmentById(refundmentId);
	}

	public void saveOrdRefundMent(List ordRefundList, List orderItemIdList,
			String operatorId) {
		// 第一步：用户填写退款记录.
		for (int i = 0; i < ordRefundList.size(); i++) {
			OrdRefundment ordrefundment = (OrdRefundment) ordRefundList.get(i);
			ordRefundMentDAO.insert(ordrefundment);
		}

		// 第二步：部分退款记录保存.
		if (orderItemIdList.size() > 0) {
			for (int i = 0; i < orderItemIdList.size(); i++) {
				Long orderItemId = (Long) orderItemIdList.get(i);
				if (orderItemId != null) {
					orderServiceProxy.updateOrderItemMetaRefund("true",
							orderItemId, operatorId);
				}
			}
		}
	}

	public List<OrdRefundment> findOrdRefundByParamSaleFinish(Map param,
			int skipResults, int maxResults) {
		return ordRefundMentDAO.findOrdRefundByParamSaleFinish(param,
				skipResults, maxResults);
	}

	public List<OrdRefundment> findOrdRefundByParam(Map param, int skipResults, int maxResults) {
		if(param.get("sysCode")!=null && Constant.COMPLAINT_SYS_CODE.VST.name().equals(param.get("sysCode").toString())) {
			return ordRefundMentDAO.findVstOrdRefundByParam(param, skipResults,maxResults);	
		}else {
			return ordRefundMentDAO.findOrdRefundByParam(param, skipResults,maxResults);	
		}
	}

	public java.math.BigDecimal findOrdfundByParamSumAmount(Map param) {
		java.math.BigDecimal total = ordRefundMentDAO
				.findOrdfundByParamSumAmount(param);
		if (total == null) {
			return java.math.BigDecimal.ZERO;
		}
		return total.divide(new java.math.BigDecimal(100));
	}

	public Long insert(OrdRefundment record) {
		return ordRefundMentDAO.insert(record);
	}

	public void setOrdRefundMentDAO(OrdRefundMentDAO ordRefundMentDAO) {
		this.ordRefundMentDAO = ordRefundMentDAO;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	
	/**
	 * 统计退款单List总退款金额.
	 * 
	 * @param ordRefundmentList
	 *            退款单List.
	 * @return 总退款金额.
	 */
	@Override
	public float countOrdRefundSumAmount(
			List<OrdRefundment> ordRefundmentList) {
		Long sumAmount = 0L;
		for (OrdRefundment tempRefundment : ordRefundmentList) {
			if(tempRefundment.getAmountYuan() > 0f ){
				sumAmount += tempRefundment.getAmount();
			}
		}
		return PriceUtil.convertToYuan(sumAmount);
	}

	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	@Override
	public Constant.APPLY_REFUNDMENT_RESULT applyRefund(Long orderId, Long saleServiceId,
			List<OrdOrderItemMeta> orderItemMetaList, Long amount, String refundType,
			String refundStatus, String reason, String operatorName, Long penaltyAmount) {
		return  orderRefundService.applyRefund(orderId, saleServiceId, orderItemMetaList,
				   amount, refundType, refundStatus, reason, operatorName, penaltyAmount);
	}
	
	/**
	 * 订单退款.
	 * 
	 * @param orderId
	 *            订单ID
	 * @param saleServiceId
	 *            售后服务ID
	 * @param orderItemMetaIdList
	 *            子子项ID序列
	 * @param amount
	 *            退款金额，以分为单位
	 * @param refundType
	 *            退款类型（补偿 COMPENSATION、订单退款 ORDER_REFUNDED）
	 * @param refundStatus
	 *            退款状态
	 * @param reason
	 *            退款原因
	 * @param operatorName
	 *            操作人
	 * @return <code>Constant.APPLY_REFUNDMENT_RESULT</code>申请退款结果
	 */
	@Override
	public Constant.APPLY_REFUNDMENT_RESULT applyRefundVst(Long orderId, Long saleServiceId,
			List<VstOrdOrderItem> vstOrdOrderItemsList, Long amount, String refundType,
			String refundStatus, String reason, String operatorName, Long penaltyAmount) {
		return  orderRefundService.applyRefundVst(orderId, saleServiceId, vstOrdOrderItemsList,
				   amount, refundType, refundStatus, reason, operatorName, penaltyAmount);
	}

	public OrderRefundService getOrderRefundService() {
		return orderRefundService;
	}

	public void setOrderRefundService(OrderRefundService orderRefundService) {
		this.orderRefundService = orderRefundService;
	}

	public OrdRefundMentDAO getOrdRefundMentDAO() {
		return ordRefundMentDAO;
	}

	/**
	 * 查看退款申请
	 */
	@Override
	public List<OrdRefundment> queryRefundment(Map param) {
		return ordRefundMentDAO.queryRefundment(param);
		
	}
	
	/**
	 * 查看退款申请
	 */
	@Override
	public List<OrdRefundment> queryVstRefundment(Map param) {
		return ordRefundMentDAO.queryVstRefundment(param);
	}

	@Override
	public Long queryRefundmentCount(Map param) {
		return ordRefundMentDAO.queryRefundmentCount(param);
	}
	@Override
	public Long queryVstRefundmentCount(Map param) {
		return ordRefundMentDAO.queryVstRefundmentCount(param);
	}

	@Override
	public List<OrdOrderItemMeta> queryOrdOrderItemMetaList(Long refundMentId) {
		return ordRefundMentDAO.queryOrdOrderItemMetaList(refundMentId);
	}
	@Override
	public List<OrdOrderItemMeta> queryVstOrdOrderItemMetaList(Long refundmentId) {
		List<OrdRefundMentItem> ordRefundMentItemList = this.queryOrdRefundmentItemsByRefundmentId(refundmentId);
		List<OrdOrderItemMeta> ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
		if(ordRefundMentItemList!=null) {
			System.out.println("refundmentId="+refundmentId+" ordRefundMentItemList="+ordRefundMentItemList.size());
			for(OrdRefundMentItem ordRefundMentItem : ordRefundMentItemList) {
				//查询新订单元素
				VstOrdOrderItem vstOrdOrderItem = vstOrdOrderService.getVstOrdOrderItem(ordRefundMentItem.getOrderItemMetaId());
				if(vstOrdOrderItem!=null) {
					OrdOrderItemMeta ordOrderItemMeta = new OrdOrderItemMeta();
					BeanUtils.copyProperties(vstOrdOrderItem, ordOrderItemMeta);
					ordOrderItemMeta.setOrderItemMetaId(vstOrdOrderItem.getOrderItemId());
					ordOrderItemMeta.setMetaProductId(vstOrdOrderItem.getProductId());
					ordOrderItemMeta.setAmountValueYuan(PriceUtil.convertToYuan(vstOrdOrderItem.getAmountValue())+"");
					ordOrderItemMeta.setActualLossYuan(PriceUtil.convertToYuan(vstOrdOrderItem.getActualLoss())+"");
					ordOrderItemMeta.setProductQuantity(1L);	//新系统没有打包概念，默认为1
					//使用退款单元素属性设置新订单元素
					ordOrderItemMeta.setMemo(ordRefundMentItem.getMemo());
					ordOrderItemMeta.setAmountType(ordRefundMentItem.getType());
					ordOrderItemMeta.setAmountValue(ordRefundMentItem.getAmount());
					ordOrderItemMeta.setActualLoss(ordRefundMentItem.getActualLoss());
					ordOrderItemMeta.setRefundmentId(ordRefundMentItem.getRefundmentId());
					ordOrderItemMeta.setRefundmentItemId(ordRefundMentItem.getRefundmentItemId());
					//
					SupSupplier supSupplier = ordRefundMentDAO.querySupplierById(ordOrderItemMeta.getSupplierId());
					if(supSupplier!=null) {
						ordOrderItemMeta.setSupplierName(supSupplier.getSupplierName());
					}
					//
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("setCode", "SETTLEMENT_STATUS");
					map.put("itemCode", ordOrderItemMeta.getSettlementStatus());//SETTLEMENT_STATUS
					List<CodeItem> codeItemList = comCodesetDAO.selectCodeItemBySet(map);
					if(codeItemList!=null && codeItemList.size()>0) {
						CodeItem codeItem = codeItemList.get(0);
						if(codeItem!=null) {
							ordOrderItemMeta.setSettlementStatusStr(codeItem.getName());	
						}
					}
					//
					ordOrderItemMetaList.add(ordOrderItemMeta);
				}
			}	
		}
		return ordOrderItemMetaList;
	}
	
	@Override
	public List<OrdRefundMentItem> queryOrdRefundmentItemsByRefundmentId(Long refundMentId) {
		return ordRefundMentDAO.queryOrdRefundmentItemsByRefundmentId(refundMentId);
	}

	@Override
	public boolean updateRefundStatus(Long refundmentId, String status) {
		return ordRefundMentDAO.updateRefundStatus(refundmentId, status);
	}

	@Override
	public boolean insertOrdRefundmentItem(Map map) {
		return ordRefundMentDAO.insertOrdRefundmentItem(map);
	}
	
	@Override
	public boolean updateOrdRefundmentItem(Map map) {
		return ordRefundMentDAO.updateOrdRefundmentItem(map);
	}

	@Override
	public List<OrdRefundment> queryRefundmentList(Map param) {
		return ordRefundMentDAO.queryRefundmentList(param);
	}
	@Override
	public List<OrdRefundment> queryVstRefundmentList(Map param) {
		return ordRefundMentDAO.queryVstRefundmentList(param);
	}

	@Override
	public Long queryRefundmentListCount(Map param) {
		return ordRefundMentDAO.queryRefundmentListCount(param);
	}

	@Override
	public boolean updateOrderStatus(Long orderId, String status) {
		return ordRefundMentDAO.updateOrderStatus(orderId, status);
	}

	@Override
	public Long queryVstRefundmentListCount(Map param) {
		return ordRefundMentDAO.queryVstRefundmentListCount(param);
	}
	
	@Override
	public void insertLog(String objectType, Long parentId, Long objectId,
			String operatorName, String logType, String logName, String content) {
		ComLog log = new ComLog();
		log.setParentId(parentId);
		log.setObjectType(objectType);
		log.setObjectId(objectId);
		log.setOperatorName(operatorName);
		log.setLogType(logType);
		log.setLogName(logName);
		
		if (content != null)
			log.setContent(content);
		comLogDAO.insert(log);
	}

	@Override
	public List<OrdRefundment> queryRefundmentByOrderId(Map param) {
		return ordRefundMentDAO.queryRefundmentByOrderId(param);
	}

	@Override
	public List<OrdOrderItemProd> queryProds(Long orderId) {
		return ordRefundMentDAO.queryProds(orderId);
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	@Override
	public List<String> queryManagerNameList(Long orderId) {
		return ordRefundMentDAO.queryManagerNameList(orderId);
	}

	@Override
	public List<OrdRefundMentItem> queryRefundMentItem(Long orderItemMetaId) {
		return ordRefundMentDAO.queryRefundMentItem(orderItemMetaId);
	}

	@Override
	public void refundApproveSuccess(OrdRefundment ordrefundment) {
		log.info("start OrdRefundMentServiceImpl.refundApproveSuccess(OrdRefundment ordrefundment) ordrefundment="+StringUtil.printParam(ordrefundment));
		ordRefundMentDAO.updateOrdRefundmentByPK(ordrefundment);
		if(null!=ordrefundment.getSysCode()&&Constant.COMPLAINT_SYS_CODE.VST.name().equalsIgnoreCase(ordrefundment.getSysCode())){
			vstRefundAmountSplit(ordrefundment);
		}else{
			refundAmountSplit(ordrefundment);
		}
		log.info("end OrdRefundMentServiceImpl.refundApproveSuccess(OrdRefundment ordrefundment) ordrefundment="+StringUtil.printParam(ordrefundment));
	}
	
	/**
	 * 退款成功后拆分退款金额
	 * @param ordrefundment
	 */
	@Override
	public void refundAmountSplit(final OrdRefundment ordrefundment){
		try{
			if(ordRefundMentDAO.queryRefundmentItemProdCount(ordrefundment.getRefundmentId())>0){
				return ;
			}
		List<OrdOrderItemProd> prods = orderItemProdDAO.selectByOrderId(ordrefundment.getOrderId());
		List<OrdOrderItemProd> prodList = new ArrayList<OrdOrderItemProd>();
		List<OrdOrderItemProd> insuranceList = new ArrayList<OrdOrderItemProd>();

		Long totalPaidAmount = 0L;
		Long totalInsurancePaidAmount = 0L;
		Long totalProdPaidAmount = 0L;
		
		Long totalRefundAmount = 0L;

		for(int j=0; j < prods.size(); j++){
			OrdOrderItemProd orderItemProd = prods.get(j); 
			totalPaidAmount = totalPaidAmount + orderItemProd.getPaidAmount();
			if(Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(orderItemProd.getSubProductType())){
				totalInsurancePaidAmount = totalInsurancePaidAmount + orderItemProd.getPaidAmount();
				insuranceList.add(orderItemProd);
			}else{
				totalProdPaidAmount = totalProdPaidAmount + orderItemProd.getPaidAmount();
				prodList.add(orderItemProd);
			}
		}
		
		//非保险类产品分拆流程
		if(totalProdPaidAmount.longValue()>0 && prodList.size()>0){
			for(int j=0; j < prodList.size(); j++){
				Long prodRefundAmountTemp;
				Long prodRefundAmount;
	
				if(totalRefundAmount.longValue()<ordrefundment.getAmount().longValue()){
					OrdOrderItemProd orderItemProd = prodList.get(j);
					if(j < prodList.size() - 1){
						double percent=orderItemProd.getPaidAmount().doubleValue()/totalProdPaidAmount.doubleValue();
						prodRefundAmountTemp = new BigDecimal(ordrefundment.getAmount() * percent).setScale(-2, BigDecimal.ROUND_HALF_DOWN).longValue();
					}else{
						prodRefundAmountTemp = ordrefundment.getAmount() - totalRefundAmount;
					}
					prodRefundAmount = prodRefundAmountTemp;
					/**
					if((prodRefundAmountTemp + orderItemProd.getRefundedAmount()) <= orderItemProd.getPaidAmount()){
						prodRefundAmount = prodRefundAmountTemp;
					}else{
						prodRefundAmount = orderItemProd.getPaidAmount() - orderItemProd.getRefundedAmount();
					}
					*/
					totalRefundAmount = totalRefundAmount + prodRefundAmount;
					
					OrdRefundmentItemProd ordRefundmentItemProd = new OrdRefundmentItemProd();
					ordRefundmentItemProd.setRefundmentId(ordrefundment.getRefundmentId());
					ordRefundmentItemProd.setOrdItemProdId(orderItemProd.getOrderItemProdId());
					ordRefundmentItemProd.setRefundedAmount(prodRefundAmount);
					//更新订单子子项支付金额
					ordRefundMentDAO.insertOrdRefundmentItemProd(ordRefundmentItemProd);
				}
			}
		}
		Long insuRefundedAmount= ordrefundment.getAmount() - totalRefundAmount;
		Long totalInsuRefundedAmount = 0L;
		//保险类产品分拆流程
		if(insuRefundedAmount.longValue()>0 && insuranceList.size()>0){
			for(int j=0; j < insuranceList.size(); j++){
				Long prodRefundAmountTemp;
				Long prodRefundAmount = 0L;
	
				if(totalInsuRefundedAmount.longValue()<insuRefundedAmount.longValue()){
					OrdOrderItemProd orderItemProd = insuranceList.get(j);
					if(j < insuranceList.size() - 1){
						double percent=orderItemProd.getPaidAmount().doubleValue()/totalInsurancePaidAmount.doubleValue();
						prodRefundAmountTemp = new BigDecimal(insuRefundedAmount * percent).setScale(-2, BigDecimal.ROUND_HALF_DOWN).longValue();
					}else{
						prodRefundAmountTemp = insuRefundedAmount - totalInsuRefundedAmount;
					}
					prodRefundAmount = prodRefundAmountTemp;
					/**
					if((prodRefundAmountTemp + orderItemProd.getRefundedAmount()) <= orderItemProd.getPaidAmount()){
						prodRefundAmount = prodRefundAmountTemp;
					}else{
						prodRefundAmount = orderItemProd.getPaidAmount() - orderItemProd.getRefundedAmount();
					}
					*/
					totalInsuRefundedAmount = totalInsuRefundedAmount + prodRefundAmount;
					
					OrdRefundmentItemProd ordRefundmentItemProd = new OrdRefundmentItemProd();
					ordRefundmentItemProd.setRefundmentId(ordrefundment.getRefundmentId());
					ordRefundmentItemProd.setOrdItemProdId(orderItemProd.getOrderItemProdId());
					ordRefundmentItemProd.setRefundedAmount(prodRefundAmount);
					//更新订单子子项支付金额
					ordRefundMentDAO.insertOrdRefundmentItemProd(ordRefundmentItemProd);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 退款成功后拆分退款金额
	 * @param ordrefundment
	 */
	@Override
	public void vstRefundAmountSplit(final OrdRefundment ordrefundment){
		try{
			if(ordRefundMentDAO.queryRefundmentItemProdCount(ordrefundment.getRefundmentId())>0){
				return ;
			}
			
			List<OrdOrderItemProd> prods = new ArrayList<OrdOrderItemProd>();
			VstOrdOrderVo vstOrdOrderVo = vstOrdOrderService.getVstOrdOrderVo(ordrefundment.getOrderId());
			if(vstOrdOrderVo!=null) {
				List<VstOrdOrderItem> vstOrdOrderItems = vstOrdOrderVo.getVstOrdOrderItems();
				if(vstOrdOrderItems!=null) {
					for(VstOrdOrderItem vstOrdOrderItem : vstOrdOrderItems) {
						OrdOrderItemProd ordOrderItemProd = new OrdOrderItemProd();
						ordOrderItemProd.setSubProductType(Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name());	//临时放置的一个非“非保险费产品”的值（杨斌：那新系统未来不会存在子类型了。）
						ordOrderItemProd.setPaidAmount(vstOrdOrderItem.getActualSettlementPrice());
						ordOrderItemProd.setOrderItemProdId(vstOrdOrderItem.getOrderItemId());
						prods.add(ordOrderItemProd);	
					}
				}
			}
			
			List<OrdOrderItemProd> prodList = new ArrayList<OrdOrderItemProd>();		//非保险费产品
			List<OrdOrderItemProd> insuranceList = new ArrayList<OrdOrderItemProd>();	//保险产品
	
			Long totalPaidAmount = 0L;			//支付金额累计
			Long totalInsurancePaidAmount = 0L;	//保险费累计
			Long totalProdPaidAmount = 0L;		//非保险费累计
			
			Long totalRefundAmount = 0L;
	
			for(int j=0; j < prods.size(); j++){
				OrdOrderItemProd orderItemProd = prods.get(j); 
				totalPaidAmount = totalPaidAmount + orderItemProd.getPaidAmount();
				if(Constant.SUB_PRODUCT_TYPE.INSURANCE.name().equals(orderItemProd.getSubProductType())){
					totalInsurancePaidAmount = totalInsurancePaidAmount + orderItemProd.getPaidAmount();
					insuranceList.add(orderItemProd);
				}else{
					totalProdPaidAmount = totalProdPaidAmount + orderItemProd.getPaidAmount();
					prodList.add(orderItemProd);
				}
			}
			
			//非保险类产品分拆流程
			if(totalProdPaidAmount.longValue()>0 && prodList.size()>0){
				for(int j=0; j < prodList.size(); j++){
					Long prodRefundAmountTemp;
					Long prodRefundAmount;
		
					if(totalRefundAmount.longValue()<ordrefundment.getAmount().longValue()){
						OrdOrderItemProd orderItemProd = prodList.get(j);
						if(j < prodList.size() - 1){	//累计不好用？要重新计算，保证平账？
							double percent=orderItemProd.getPaidAmount().doubleValue()/totalProdPaidAmount.doubleValue();
							prodRefundAmountTemp = new BigDecimal(ordrefundment.getAmount() * percent).setScale(-2, BigDecimal.ROUND_HALF_DOWN).longValue();
						}else{	//非保险类产品最后一个产品的金额做减法，保证平账
							prodRefundAmountTemp = ordrefundment.getAmount() - totalRefundAmount;
						}
						prodRefundAmount = prodRefundAmountTemp;
						/**
						if((prodRefundAmountTemp + orderItemProd.getRefundedAmount()) <= orderItemProd.getPaidAmount()){
							prodRefundAmount = prodRefundAmountTemp;
						}else{
							prodRefundAmount = orderItemProd.getPaidAmount() - orderItemProd.getRefundedAmount();
						}
						*/
						totalRefundAmount = totalRefundAmount + prodRefundAmount;
						
						OrdRefundmentItemProd ordRefundmentItemProd = new OrdRefundmentItemProd();
						ordRefundmentItemProd.setRefundmentId(ordrefundment.getRefundmentId());
						ordRefundmentItemProd.setOrdItemProdId(orderItemProd.getOrderItemProdId());
						ordRefundmentItemProd.setRefundedAmount(prodRefundAmount);
						//更新订单子子项支付金额
						ordRefundMentDAO.insertOrdRefundmentItemProd(ordRefundmentItemProd);
					}
				}
			}
			Long insuRefundedAmount= ordrefundment.getAmount() - totalRefundAmount;
			Long totalInsuRefundedAmount = 0L;
			//保险类产品分拆流程
			if(insuRefundedAmount.longValue()>0 && insuranceList.size()>0){
				for(int j=0; j < insuranceList.size(); j++){
					Long prodRefundAmountTemp;
					Long prodRefundAmount = 0L;
		
					if(totalInsuRefundedAmount.longValue()<insuRefundedAmount.longValue()){
						OrdOrderItemProd orderItemProd = insuranceList.get(j);
						if(j < insuranceList.size() - 1){
							double percent=orderItemProd.getPaidAmount().doubleValue()/totalInsurancePaidAmount.doubleValue();
							prodRefundAmountTemp = new BigDecimal(insuRefundedAmount * percent).setScale(-2, BigDecimal.ROUND_HALF_DOWN).longValue();
						}else{
							prodRefundAmountTemp = insuRefundedAmount - totalInsuRefundedAmount;
						}
						prodRefundAmount = prodRefundAmountTemp;
						/**
						if((prodRefundAmountTemp + orderItemProd.getRefundedAmount()) <= orderItemProd.getPaidAmount()){
							prodRefundAmount = prodRefundAmountTemp;
						}else{
							prodRefundAmount = orderItemProd.getPaidAmount() - orderItemProd.getRefundedAmount();
						}
						*/
						totalInsuRefundedAmount = totalInsuRefundedAmount + prodRefundAmount;
						
						OrdRefundmentItemProd ordRefundmentItemProd = new OrdRefundmentItemProd();
						ordRefundmentItemProd.setRefundmentId(ordrefundment.getRefundmentId());
						ordRefundmentItemProd.setOrdItemProdId(orderItemProd.getOrderItemProdId());
						ordRefundmentItemProd.setRefundedAmount(prodRefundAmount);
						//更新订单子子项支付金额
						ordRefundMentDAO.insertOrdRefundmentItemProd(ordRefundmentItemProd);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public void setVstOrdOrderService(VstOrdOrderService vstOrdOrderService) {
		this.vstOrdOrderService = vstOrdOrderService;
	}

	public void setComCodesetDAO(ComCodesetDAO comCodesetDAO) {
		this.comCodesetDAO = comCodesetDAO;
	}
	
}
