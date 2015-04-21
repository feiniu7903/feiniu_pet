package com.lvmama.front.web.buy;


import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductRoyaltyService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.MobilePayInfo;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.front.web.BaseAction;
import com.tenpay.api.common.util.HttpClientUtil;

public class BaseAppPayAction extends BaseAction{
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
	
	private String visitTime="";//游玩时间yyyyMMddHHmmss
	
	private String approveTime="";//终审时间yyyyMMddHHmmss
	
	private Long waitPayment;//支付等待时间
	

	@Autowired
	private ProdProductRoyaltyService  prodProductRoyaltyService;
	
	protected MobilePayInfo toPay(String payUrl){
		MobilePayInfo msg = null;
		try {
			if (this.isAccessAllowed()) {
				if (order.isUnpay()||order.isPartPay()) {
					Thread.sleep(1500);
					if(super.isLimitMobilePayChannel(order)){
						String name = Constant.MARK_PAYMENT_CHANNEL.getCnName(order.getPaymentChannel());
						//responeStr = "{\"message\":\"请你到驴妈妈网站选择"+name+"支付，可以享受更多优惠        \",\"code\":\"-1\"}";
						 msg = new MobilePayInfo("-1", "请你到驴妈妈网站选择"+name+"支付，可以享受更多优惠");
						 return msg;
					}
					
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
						String toPayRequestUrl = String
								.format(payUrl,
										paymentUrl, order.getOrderId(),
										order.getOughtPayFenLong(), objectType,
										paymentType, bizType, signature,objectName,visitTime,approveTime,waitPayment);
//						if(StringUtil.isNotEmptyString(getRoyaltyParameters())){
//							toPayRequestUrl+="&royaltyParameters="+getRoyaltyParameters();
//						}
						log.info("toPayRequestUrl:"+toPayRequestUrl);
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
	 * 充值 
	 * @param payUrl
	 * @return
	 */
	protected MobilePayInfo toPay4Recharge(String payUrl){
		MobilePayInfo msg = null;
		try {
			
		/*	String objectType = "CASH_RECHARGE";
			String paymentType = Constant.PAYMENT_OPERATE_TYPE.PAY.name();
			
			String waitPayment = "1";
			String approveTime = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
			String visitTime = approveTime;
			String dataStr = cashRechargeId+objectType+rechargeAmount+paymentType+bizType+PaymentConstant.SIG_PRIVATE_KEY;
			log.info("client source: " + dataStr);
			String signature = MD5.md5(dataStr);
			log.info("client md5: " + signature);
		    String rechargeUrl = "?objectId="+cashRechargeId+"&amount="+rechargeAmount+"&objectType="+objectType+"&paymentType="+paymentType+"&bizType="+bizType+"&waitPayment="+waitPayment+"&approveTime=" +approveTime + "&visitTime=" + visitTime + "&signature="+signature;

		 */   
			
			// 要不要睡会觉
			Thread.sleep(1500);
			// 生成signature
			String bizType4R = Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name();
		    String objectType4R = "CASH_RECHARGE";
			String dataStr4R = objectId + objectType4R + this.amount + paymentType + bizType4R + PaymentConstant.SIG_PRIVATE_KEY;
			log.info("source: " + dataStr4R);
			signature = MD5.md5(dataStr4R);
			
			// 生成URL
			String paymentUrl = Constant.getInstance().getPaymentUrl();
			String approveTime = DateUtil.formatDate(new Date(), "yyyyMMddHHmmss");
			String visitTime = approveTime;
			String toPayRequestUrl = String .format(payUrl,paymentUrl, objectId, amount, objectType4R,
					paymentType, bizType4R,signature,approveTime,visitTime);
			msg = new MobilePayInfo("1", "");
			// 获取返回值 
			msg.setReturnUrl(toPayRequestUrl);
			return msg;
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
			selectObjectName(order);
			//objectName=order.getMainProduct().getProductName();
			visitTime= getDateToString(order.getVisitTime());//游玩时间
			
			if(order.getApproveTime()!=null){
				approveTime=getDateToString(order.getApproveTime());//终审时间
			}else{
				Date d=new Date();
				approveTime=getDateToString(d);//终审时间
			}
			
			waitPayment = order.getWaitPayment();//支付等待时间
			log.info("objectName:"+objectName);
			log.info("visitTime:"+visitTime);
			log.info("approveTime:"+approveTime);
			log.info("waitPayment:"+waitPayment);
		}


		return true;
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
	/**获得商品名称
	 * @param objectNameDe
	 * @throws UnsupportedEncodingException
	 */
	public void selectObjectName(OrdOrder order){
		try {
			String objectNameDe=order.getMainProduct().getProductName();
			String zwProductType=order.getMainProduct().getZhProductType();
			objectName=java.net.URLEncoder.encode(zwProductType+"-"+objectNameDe,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

	public String getBizType() {
		return bizType;
	}

	public void setBizType(String bizType) {
		this.bizType = bizType;
	}



	public String getRoyaltyParameters() {
		return royaltyParameters;
	}



	public void setRoyaltyParameters(String royaltyParameters) {
		this.royaltyParameters = royaltyParameters;
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

	public void setAmount(Long amout) {
		this.amount = amout;
	}

	public String getObjectName() {
		return objectName;
	}

	public void setObjectName(String objectName) {
		this.objectName = objectName;
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
