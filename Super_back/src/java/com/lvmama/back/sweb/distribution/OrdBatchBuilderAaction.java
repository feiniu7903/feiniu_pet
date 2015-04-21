
package com.lvmama.back.sweb.distribution;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.distribution.DistributorTuanInfo;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.distribution.DistributionTuanService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderBatchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name="add",location="/WEB-INF/pages/back/distribution/batchAbandonOrder/batch_create_order.jsp")
})
public class OrdBatchBuilderAaction extends BaseAction{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6835774588281268793L;
	private OrderService orderServiceProxy;
	private UserUserProxy userUserProxy;
	private ProdProductBranchService prodProductBranchService;
	private ProdProductService prodProductService;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private PayPaymentService payPaymentService;
	private OrderBatchService orderBatchService;
	private TopicMessageProducer resourceMessageProducer; 
	private ComLogService comLogService;
	private DistributionTuanService distributionTuanService;
	
	private final String channel=Constant.CHANNEL.EXPORT_DIEM.name();
	private String booker="银联旅游卡分销专用";//银联旅游卡分销专用
//	private String booker="yuzhibing21";//银联旅游卡分销专用
	private Long branchId;
	private Long distributorId;
	private String isRealPerfrom;
	private Long count;//生成订单数量
	private String contactName; //联系人
	private String contactMobile;//联系人手机号
	private String content;
	private String token;
	private List<DistributorTuanInfo> lists=  new ArrayList<DistributorTuanInfo>();
	
	
	@Action("/distribution/create")
	public String create(){
		token = ""+ new Random().nextInt(1000);
		getSession().setAttribute("token", token);
		lists = distributionTuanService.selectByDistributorChannelType("EXPORT_DIEM");
		return "add";
	}

	@Action("/distribution/findProd")
	public void findProd(){
		JSONObject jsonObj=new JSONObject();
		ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(branchId);
		if(ppb==null){
			jsonObj.put("result", "查询类别为空");
			sendAjaxResultByJson(jsonObj.toString());
			return ;
		}
		ProdProduct pp = prodProductService.getProdProductById(ppb.getProductId());
		if(pp==null){
			jsonObj.put("result", "查询产品为空");
			sendAjaxResultByJson(jsonObj.toString());
			return ;
		}
		jsonObj.put("result", "true");
		jsonObj.put("branchName", ppb.getBranchName());
		jsonObj.put("prodProductId", pp.getProductId());
		jsonObj.put("prodProductName", ""+pp.getProductName());
		sendAjaxResultByJson(jsonObj.toString());
		
	}
	
	@Action("/distribution/saveBatchOrd")
	public void submit(){
		JSONObject jsonObj=new JSONObject();
		jsonObj.put("result", "true");
		
		int needNum=0;
		List<OrdOrderBatch> lists = orderBatchService.selectNeedCreateOrder();
		if(lists!=null){
			for(OrdOrderBatch batch:lists){
				needNum = needNum + batch.getBatchCount();
			}
		}
		int MaxNum = Constant.getInstance().getDistMaxNum();
		if((needNum+count)>MaxNum){
			sendAjaxResultByJson(jsonObj, "已经有" + needNum +"单的任务在等待,总量不能超过:"+MaxNum);
			return ;
		}
		if(!checkToken(jsonObj)){
			return ;
		}
		try{
			String operatorName=getOperatorNameAndCheck();
			UserUser user = userUserProxy.getUsersByIdentity(booker,UserUserProxy.USER_IDENTITY_TYPE.USER_NAME);
			if(user==null){
				sendAjaxResultByJson(jsonObj, "下单人不存在,下单任务未执行");
				return ;
			}
			ProdProductBranch ppb = prodProductBranchService.selectProdProductBranchByPK(branchId);
			if(ppb==null){
				sendAjaxResultByJson(jsonObj, "类别找不到,下单任务未执行");
				return ;
			}
			ProdProduct pp = prodProductService.getProdProductById(ppb.getProductId());
			if(pp==null){
				sendAjaxResultByJson(jsonObj, "类别产品不存在,下单任务未执行");
				return ;
			}
			Date date = prodProductBranchService.selectNearBranchTimePriceByBranchId(branchId);
			if(date==null){
				sendAjaxResultByJson(jsonObj,"类别不存在或不存在可售的时间价格表");
				return;
			}
			ProdProductBranch prodBranch = prodProductBranchService.getProductBranchDetailByBranchId(branchId, date, true);
			if(prodBranch==null){
				sendAjaxResultByJson(jsonObj,"类别不存在或不存在可售的时间价格表");
				return;
			}
			
			if(!prodBranch.getProdProduct().IsAperiodic()){
				List<MetaProductBranch> s = metaProductBranchService.getMetaProductBranchByProdBranchId(prodBranch.getProdBranchId());
				if(CollectionUtils.isNotEmpty(s)){
					MetaProduct mp = metaProductService.getMetaProductByBranchId(s.get(0).getMetaBranchId());
					if(mp!=null && mp.getValidDays()!=null){
						prodBranch.setValidBeginTime(date);
						prodBranch.setValidBeginTime(DateUtil.dsDay_Date(date, mp.getValidDays().intValue()));
					}
				}
			}
			OrdOrderBatch batch = new OrdOrderBatch();
			batch.setBatchCount(Integer.valueOf(""+count));
			batch.setContacts(contactName);
			batch.setContactsPhone(contactMobile);
			batch.setCreator(user.getId());
			batch.setProductBranchId(branchId);
			batch.setProductId(pp.getProductId());
			batch.setStatus(Constant.ORDER_BATCH_STATUS.BATCHWAITTING.name());
			batch.setReson(content);
			batch.setOperatorName(operatorName);
			batch.setIsValid("true");
			batch.setValidBeginDate(prodBranch.getValidBeginTime());
			batch.setValidEndDate(prodBranch.getValidEndTime());
			batch.setCreatetime(new Date());
			batch.getDistributorTuanInfo().setDistributorTuanInfoId(distributorId);
			Long batchId = orderBatchService.insert(batch);
			
			comLogService.insert(Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode(), null, batchId,operatorName,
					Constant.COM_LOG_ORDER_EVENT.batchOrderBooking.name(),
					"批量下单", "创建批量下单任务下单量:" + count, null);
			
//			BuyInfo buyInfo = new BuyInfo();
//			buyInfo.setChannel(channel);
//			buyInfo.setIsAperiodic(prodBranch.getProdProduct().getIsAperiodic());
//			buyInfo.setValidBeginTime(prodBranch.getValidBeginTime());
//			buyInfo.setValidEndTime(prodBranch.getValidEndTime());
//			BuyInfo.Item item = new BuyInfo.Item();
//			item.setProductId(prodBranch.getProductId());
//			item.setProductBranchId(prodBranch.getProdBranchId());
//			item.setIsDefault("true");
//			item.setQuantity(1);
//			item.setVisitTime(date);
//			item.setValidBeginTime(prodBranch.getValidBeginTime());
//			item.setValidEndTime(prodBranch.getValidEndTime());
//			List<BuyInfo.Item> list = new ArrayList<BuyInfo.Item>();
//			list.add(item);
//			buyInfo.setItemList(list);
//			
//			buyInfo.setMainProductType(prodBranch.getProdProduct().getProductType());
//			buyInfo.setMainSubProductType(prodBranch.getProdProduct().getSubProductType());
//			if(prodBranch.getProdProduct().isPaymentToLvmama()){
//				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
//			}else if(prodBranch.getProdProduct().isPaymentToSupplier()){
//				buyInfo.setPaymentTarget(Constant.PAYMENT_TARGET.TOSUPPLIER.name());
//			}
//			
//			buyInfo.setTodayOrder(false);
//			buyInfo.setSelfPack("false");
//			
//			List<Person> personList = new ArrayList<Person>();
//			Person person = new Person();
//			person.setName(contactName);
//			person.setMobile(contactMobile);
//			person.setPersonType(Constant.ORD_PERSON_TYPE.CONTACT.name());
//			personList.add(person);
//			
//			buyInfo.setPersonList(personList);
//			
//			buyInfo.setUserId(user.getUserNo());
//			
//			int resultCount=create(buyInfo,operatorName,batchId);
//			
//			//todo 更新batch状态
//			Map<Object, Object> batchstaus = new HashMap<Object, Object>();
//			//已经生成的订单数
//			batchstaus.put("batchId", batchId);
//			//已经申码成功的订单数
//			batchstaus.put("status", Constant.ORDER_BATCH_STATUS.FINISHED.name());
//			orderBatchService.updateBatchStatus(batchstaus);
			
//			if(count==resultCount){
//				jsonObj.put("message", "批量生成订单结果：批量任务完成，您已成功的生成了" + count+ "笔订单");
//			}else{
//				jsonObj.put("message", "批量生成订单结果：批量任务未完成，您只成功的生成了" + count+ "笔订单");
//			}
//			buyInfo.setResourceConfirmStatus("true");
			jsonObj.put("message", "批量生成订单任务已成功创建");
		}catch(Exception ex){
			jsonObj.put("result", "false");
			jsonObj.put("message", "批量任务执行异常" +ex);
		}
		sendAjaxResultByJson(jsonObj.toString());
	}

	private void sendAjaxResultByJson(JSONObject jsonObj,String content) {
		jsonObj.put("result", "false");
		jsonObj.put("message", content);
		sendAjaxResultByJson(jsonObj.toString());
	}

	private synchronized boolean checkToken(JSONObject jsonObj) {
		if(getSession().getAttribute("token")==null) return false;
		
		String token2 = (String)getSession().getAttribute("token");
		if(!token2.equals(token)){
			jsonObj.put("result", "false");
			jsonObj.put("message", "请求不合法");
			sendAjaxResultByJson(jsonObj.toString());
			return false;
		}
		getSession().setAttribute("token",null);
		return true;
	}
	
	
	private int create(BuyInfo buyInfo,String operatorName,Long batchId){
		int resultCount=0;
		for(int i=0;i<count;i++){
			try{
				OrdOrder order = orderServiceProxy.createOrderWithOperatorId(
						buyInfo, operatorName);
				
				OrdOrderBatchOrder ob = new OrdOrderBatchOrder();
				ob.setBatchId(batchId);
				ob.setOrderId(order.getOrderId());
				orderBatchService.inserBatchOrder(ob);
				
				if(order.isNormal()&&order.isUnpay()&&order.isPayToLvmama()){
					try{
						Thread.sleep(2*1000);//等待2秒，避免消息同时发出
					}catch(InterruptedException exx){
						
					}
					paymentOrder(order,operatorName);
					resultCount++;
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		return resultCount;
	}
	
	
	private boolean paymentOrder(OrdOrder order,String operatorName) {
		PayPayment payPayment = new PayPayment();
		payPayment.setObjectId(order.getOrderId());
		payPayment.setSerial(payPayment.geneSerialNo());
		String key = "PAYMENT_DISTRIBUTION_ACTION" + payPayment.getSerial();
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			return false;
		}
		try {
			Date clllbackTime = new Date();
			
			payPayment.setCallbackInfo("分销支付");
			payPayment.setGatewayTradeNo(DateUtil.formatDate(clllbackTime, "yyyyMMddHHmmssSSS")+order.getOrderId());
			payPayment.setPaymentTradeNo(payPayment.getGatewayTradeNo());
			payPayment.setCallbackTime(clllbackTime);
			payPayment.setCreateTime(clllbackTime);
			payPayment.setPaymentGateway(Constant.PAYMENT_GATEWAY_DIST_MANUAL.EXPORT_DIEM.name());
			payPayment.setAmount(order.getOughtPay());
			payPayment.setOperator(operatorName);
			payPayment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());
			payPayment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payPayment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payPayment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			

			Long paymentId = payPaymentService.savePayment(payPayment);
			resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
		} finally {
			SynchronizedLock.releaseMemCached(key);
		}
		return true;

	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}



	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}



	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}



	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}



	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	

	public DistributionTuanService getDistributionTuanService() {
		return distributionTuanService;
	}

	public void setDistributionTuanService(
			DistributionTuanService distributionTuanService) {
		this.distributionTuanService = distributionTuanService;
	}

	public List<DistributorTuanInfo> getLists() {
		return lists;
	}

	public void setLists(List<DistributorTuanInfo> lists) {
		this.lists = lists;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public Long getBranchId() {
		return branchId;
	}


	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}


	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}


	public Long getCount() {
		return count;
	}


	public void setCount(Long count) {
		this.count = count;
	}


	public String getContactName() {
		return contactName;
	}


	public void setContactName(String contactName) {
		this.contactName = contactName;
	}


	public String getContactMobile() {
		return contactMobile;
	}


	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}
	

	public Long getDistributorId() {
		return distributorId;
	}

	public void setDistributorId(Long distributorId) {
		this.distributorId = distributorId;
	}

	public String getIsRealPerfrom() {
		return isRealPerfrom;
	}

	public void setIsRealPerfrom(String isRealPerfrom) {
		this.isRealPerfrom = isRealPerfrom;
	}

	public void setOrderBatchService(OrderBatchService orderBatchService) {
		this.orderBatchService = orderBatchService;
	}


	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
	

}
