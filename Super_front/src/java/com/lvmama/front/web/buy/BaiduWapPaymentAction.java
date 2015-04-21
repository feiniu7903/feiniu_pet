package com.lvmama.front.web.buy;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.MobilePayInfo;
import com.lvmama.comm.vo.PaymentConstant;

public class BaiduWapPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;
	
	@Autowired
	protected transient OrderService orderServiceProxy;
	private Long orderId;
	private OrdOrder order;
	private String signature;
	/**
	 * 对象类型(订单).
	 */
	private String objectType = "ORD_ORDER";
	/**
	 * 支付类型(正常支付/预授权).
	 */
	private String paymentType = Constant.PAYMENT_OPERATE_TYPE.PAY.name();
	/**
	 * 分润账号集
	 */
	private String royaltyParameters;
	
	private String bizType=Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name();
	
	/******** 现金账号充值 ***********/
	private Long objectId; // 充值流水号
	private Long amount; // 充值金额 
	
	
	/**
	 * 以下百度支付新加
	 */
	private String objectName="";//产品名
	
	private String objectDesc="";//产品类型
	
	private String objectPageUrl="";//URL
	
	private String visitTime="";//游玩时间yyyyMMddHHmmss
	
	private String approveTime="";//终审时间yyyyMMddHHmmss
	
	private Long waitPayment;//支付等待时间
	

	@Autowired
	private ProdProductRoyaltyService  prodProductRoyaltyService;
	

	/**
	 * 百度APP 支付
	 * @throws Exception
	 */
	@Action("/baiduApp/toPay")
	public void toPayApp() throws Exception {
		transcoding();
		objectName=java.net.URLEncoder.encode(objectName,"UTF-8");
		objectDesc=java.net.URLEncoder.encode(objectDesc,"UTF-8");
		MobilePayInfo pinfo = this.toPay("%s/pay/baidupayApp.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&objectDesc=%s&objectPageUrl=%s&visitTime=%s&approveTime=%s&waitPayment=%s");
		pinfo.setMessage("<h3>"+pinfo.getMessage()+"</h3>");
		if(pinfo.isSuccess()){
			log.info("success ....");
			log.info("returnUrl:"+pinfo.getReturnUrl());
			String result = HttpsUtil.requestGet(pinfo.getReturnUrl());
			log.info("result:"+result);
			this.sendAjaxMsg(result);
		} else {
			this.sendAjaxMsg(pinfo.getMessage());
		}
	}
	/**
	 * 百度WAP 支付
	 * @throws Exception
	 */
	@Action("/baiduWap/toPay")
	public void toPayWap() throws Exception {
		transcoding();
		objectName=java.net.URLEncoder.encode(objectName,"UTF-8");
		objectDesc=java.net.URLEncoder.encode(objectDesc,"UTF-8");
		MobilePayInfo pinfo = this.toPay("%s/pay/baidupayWap.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&objectDesc=%s&objectPageUrl=%s&visitTime=%s&approveTime=%s&waitPayment=%s");
		pinfo.setMessage("<h3>"+pinfo.getMessage()+"</h3>");
		if(pinfo.isSuccess()){
			log.info("success ....");
			//String result = HttpsUtil.requestGet(pinfo.getReturnUrl());
			log.info("returnUrl:"+pinfo.getReturnUrl());
			this.getResponse().sendRedirect(pinfo.getReturnUrl());
			//log.info("result:"+result);
			//this.sendAjaxMsg(result);
		} else {
			this.sendAjaxMsg(pinfo.getMessage());
		}
	}
	/**
	 * 转码
	 */
	public void transcoding() throws Exception{
		log.info("objectName:objectDesc"+objectName+"--"+objectDesc);
		//中文转码
		String hanzi1 = new String(
				objectName.getBytes("ISO-8859-1"), "UTF-8");
		objectName = java.net.URLDecoder.decode(hanzi1, "UTF-8");
		
		String hanzi2 = new String(
				objectDesc.getBytes("ISO-8859-1"), "UTF-8");
		objectDesc = java.net.URLDecoder.decode(hanzi2, "UTF-8");
	}
	/**
	 * 时间类型转换
	 * @param date
	 * @return
	 */
	public String getDateToString(Date date){
		if(date!=null){
			String newDateString=null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			
			newDateString=dateFormat.format(date);
			return newDateString;
		}else{
			return "";
		}
	}
	protected MobilePayInfo toPay(String payUrl){
		MobilePayInfo msg = null;
		try {
			if (this.isAccessAllowed()) {
				if (order.isUnpay()||order.isPartPay()) {
					Thread.sleep(1500);
					//由于从QING过来的订单都要百度支付这里不限制支付渠道
//					if(!Constant.MARK_PAYMENT_CHANNEL.BAIDU_PAY.name().equals(order.getPaymentChannel())){
//						String name = Constant.MARK_PAYMENT_CHANNEL.getCnName(order.getPaymentChannel());
//						//responeStr = "{\"message\":\"请你到驴妈妈网站选择"+name+"支付，可以享受更多优惠        \",\"code\":\"-1\"}";
//						 msg = new MobilePayInfo("-1", "请你到驴妈妈网站选择"+name+"支付，可以享受更多优惠");
//						 return msg;
//					}
					
					if(order.isCanceled()){
						 msg = new MobilePayInfo("-1", "订单已取消");
						 return msg;
					}
						
					if(!order.isExpireToPay()){
						String dataStr = String.valueOf(order.getOrderId())
								+ objectType
								+ String.valueOf(order.getOughtPayFenLong())
								+ paymentType + bizType
								+ PaymentConstant.SIG_PRIVATE_KEY;
						log.info("source: " + dataStr);
						signature = MD5.md5(dataStr);
						log.info("md5: " + signature);
						String paymentUrl = Constant.getInstance().getPaymentUrl();
//						if(order.getMainProduct()!=null){
//							/**
//							 * 设置分账账号集
//							 */
//							setRoyaltyParameters(prodProductRoyaltyService.getRoyaltysParamers(order.getMainProduct().getProductId(), order));
//						}
						
						visitTime= getDateToString(order.getVisitTime());//游玩时间
						
						if(order.getApproveTime()!=null){
							approveTime=getDateToString(order.getApproveTime());//终审时间
						}else{
							Date d=new Date();
							approveTime=getDateToString(d);//终审时间
						}
						
						waitPayment = order.getWaitPayment();//支付等待时间
						
						String toPayRequestUrl = String
								.format(payUrl,
										paymentUrl, order.getOrderId(),
										order.getOughtPayFenLong(), objectType,
										paymentType, bizType, signature,objectName,objectDesc,objectPageUrl,visitTime,approveTime,waitPayment);
//						if(StringUtil.isNotEmptyString(getRoyaltyParameters())){
//							toPayRequestUrl+="&royaltyParameters="+getRoyaltyParameters();
//						}
						msg = new MobilePayInfo("1", "");
						msg.setReturnUrl(toPayRequestUrl);
						return msg;
					} else {
						msg = new MobilePayInfo("-1", "订单已超过支付等待时间");
						return msg;
					}
					
				} else {
					  msg = new MobilePayInfo("-1", "订单已支付");
					  return msg;
					
				}
			}
		} catch (Exception ex) {
			msg = new MobilePayInfo("-1", "服务异常");
			ex.printStackTrace();
		} 
		
		return msg;
	}
	/**
	 * 订单内容是否可被当前用户访问
	 * 
	 * @return
	 */
	private boolean isAccessAllowed() {
		if (null == orderId) {
			LOG.error("订单号为null,无法展示订单内容");
			return false;
		}

		order = orderServiceProxy.queryOrdOrderByOrderId(orderId);

		if (null == order) {
			LOG.error("无法找到订单号为" + orderId + "的订单，展示订单内容失败!");
			return false;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("订单号" + orderId + "存在且内容将被展现!");
			}
			//支付渠道是百度的设置等待时间15分钟
			if(Constant.MARK_PAYMENT_CHANNEL.BAIDU_PAY.name().equals(order.getPaymentChannel())){
				order.setWaitPayment(15L);
			}
			log.info("paymentChannel:"+order.getPaymentChannel());
			log.info("waitPaymen:"+order.getWaitPayment());
		}


		return true;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public OrdOrder getOrder() {
		return order;
	}
	public void setOrder(OrdOrder order) {
		this.order = order;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public String getObjectType() {
		return objectType;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getRoyaltyParameters() {
		return royaltyParameters;
	}
	public void setRoyaltyParameters(String royaltyParameters) {
		this.royaltyParameters = royaltyParameters;
	}
	public String getBizType() {
		return bizType;
	}
	public void setBizType(String bizType) {
		this.bizType = bizType;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	public ProdProductRoyaltyService getProdProductRoyaltyService() {
		return prodProductRoyaltyService;
	}
	public void setProdProductRoyaltyService(
			ProdProductRoyaltyService prodProductRoyaltyService) {
		this.prodProductRoyaltyService = prodProductRoyaltyService;
	}
	public String getObjectName() {
		return objectName;
	}
	public void setObjectName(String objectName) {
		this.objectName = objectName;
	}
	public String getObjectDesc() {
		return objectDesc;
	}
	public void setObjectDesc(String objectDesc) {
		this.objectDesc = objectDesc;
	}
	public String getObjectPageUrl() {
		return objectPageUrl;
	}
	public void setObjectPageUrl(String objectPageUrl) {
		this.objectPageUrl = objectPageUrl;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public Long getWaitPayment() {
		return waitPayment;
	}
	public void setWaitPayment(Long waitPayment) {
		this.waitPayment = waitPayment;
	}
	
}
