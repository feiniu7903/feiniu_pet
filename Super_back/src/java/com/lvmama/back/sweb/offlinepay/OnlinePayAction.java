package com.lvmama.back.sweb.offlinepay;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

@Results({
	@Result(name = "onlinePay", location = "/WEB-INF/pages/back/offlinePay/onlinePay.jsp")
})
@ParentPackage("json-default")
public class OnlinePayAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3962401361268817193L;

    private Long orderId;
	
	/**
	 * 
	 */
	private PayPaymentService payPaymentService;
	
	/**
	 * 
	 */
	protected TopicMessageProducer resourceMessageProducer;
	
	/**
	 * 消息发送接口
	 */
	private TopicMessageProducer orderMessageProducer;
	
	private OrderService orderServiceProxy=(OrderService)SpringBeanProxy.getBean("orderServiceProxy");
	
	
	private String paymentGateway;

	private String paymentTradeNo;
	
	private String refundSerial;
	
	private String gatewayTradeNo;
	
	private float actualPayFloat;
	
	private Date callbackTime;
	
	private String memo;
	
	private Long amount;
	
	private Long paymentId;
	
	private List<PayPayment> payPaymentList = new ArrayList<PayPayment>();
	
	private ComLogService comLogService;
	private PayPaymentGatewayService payPaymentGatewayService;
	
	private List<PayPaymentGateway> payPaymentGatewayList;
	/**
	 * 
	 * @return
	 */
	@Action(value = "/offlinePay/onlinePay")
	public String doOnlinePay(){
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("gatewayType", Constant.PAYMENT_GATEWAY_TYPE.ONLINE.name());
		paramMap.put("gatewayStatus", Constant.PAYMENT_GATEWAY_STATUS.ENABLE.name());
		paramMap.put("orderby", "PAYMENT_GATEWAY_ID"); 
		paramMap.put("order", "asc");
		payPaymentGatewayList=payPaymentGatewayService.selectPayPaymentGatewayByParamMap(paramMap);
		this.setActualPayFloat(PriceUtil.convertToYuan(amount));
		return "onlinePay";
	}
	

	
	@Action(value="/offlinePay/loadPayPayments",results=@Result(name="payPayments",type="json",
			params={"includeProperties","payPaymentList\\[\\d+\\]\\.paymentTradeNo,payPaymentList\\[\\d+\\]\\.paymentTradeNo"}))
	public String loadPayments(){
		payPaymentList = payPaymentService.selectPayPaymentByObjectIdAndPaymentGatewayAndStatuss(orderId, paymentGateway, "'"+Constant.PAYMENT_SERIAL_STATUS.CREATE.name()+"','"+Constant.PAYMENT_SERIAL_STATUS.FAIL.name()+"'");
		return "payPayments";
	}
	
	
	@Action(value="/offlinePay/loadPaymentAmount",results=@Result(name="payPayments",type="json",
			params={"includeProperties","actualPayFloat"}))
	public String loadPaymentAmount(){
		PayPayment payment = payPaymentService.selectByPaymentTradeNoAndObjectId(paymentTradeNo, orderId+"");
		this.setActualPayFloat(PriceUtil.convertToYuan(payment.getAmount()));
		return "payPayments";
	}
	
	/**
	 * 
	 * @return
	 */
	@Action(value = "/offlinePay/onlinePaySave")
	public void doSave(){
		JSONResult result = new JSONResult();
		try {
			Assert.hasText(paymentTradeNo, "对账流水号不可以为空");
			Assert.hasText(gatewayTradeNo, "银行交易流水号不可以为空");
			Assert.hasText(refundSerial, "退款流水号不可以为空");
			Assert.notNull(callbackTime, "交易时间不可以为空");
			
			PayPayment payment = payPaymentService.selectByPaymentTradeNoAndObjectId(paymentTradeNo, orderId+"");
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setCallbackTime(callbackTime);
			payment.setRefundSerial(refundSerial.trim());
			payment.setGatewayTradeNo(gatewayTradeNo.trim());
			payment.setCallbackInfo("[线下支付]"+memo.trim());
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setOperator(getOperatorName());
			payPaymentService.updatePayment(payment);
			if(payment.isPrePayment()){
				//更新预授权支付数据
				payPaymentService.updatePrePayment(payment);
			}
			paymentId = payment.getPaymentId();
			if (payment!=null && payment.isSuccess()) {
				if(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name().equalsIgnoreCase(payment.getBizType())){
					OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(payment.getObjectId());
					if (payment.isPrePayment() && order.isApprovePass()) {
						Message msg = MessageFactory.newOrderApproveBeforePrepayMessage(order.getOrderId());
						msg.setAddition(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
						orderMessageProducer.sendMsg(msg);
					}else if(order.isApproveFail()){
						orderServiceProxy.autoCreateOrderFullRefund(order, super.getOperatorName(), "资源审核不通过");
					}
				}
				ComLog log = new ComLog();
				log.setObjectId(payment.getPaymentId());
				log.setObjectType("PAY_PAYMENT");
				log.setParentId(payment.getObjectId());
				log.setParentType("ORD_ORDER");
				log.setLogType("PAYMENT_TYPE_OFFLINE");
				log.setLogName("手工线下支付");
				log.setOperatorName(this.getOperatorName());
				log.setContent("由操作人员" + getOperatorName() + "使用" + payment.getPayWayZh() + "线下支付方式支付");
				log.setCreateTime(new Date());
				comLogService.addComLog(log);
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(payment.getPaymentId()));
			}
			result.put("paymentId", paymentId);
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public String getRefundSerial() {
		return refundSerial;
	}

	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public float getActualPayFloat() {
		return actualPayFloat;
	}

	public void setActualPayFloat(float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public List<PayPaymentGateway> getPayPaymentGatewayList() {
		return payPaymentGatewayList;
	}

	public void setPayPaymentGatewayList(List<PayPaymentGateway> payPaymentGatewayList) {
		this.payPaymentGatewayList = payPaymentGatewayList;
	}

	public void setPayPaymentGatewayService(PayPaymentGatewayService payPaymentGatewayService) {
		this.payPaymentGatewayService = payPaymentGatewayService;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public List<PayPayment> getPayPaymentList() {
		return payPaymentList;
	}

	public void setPayPaymentList(List<PayPayment> payPaymentList) {
		this.payPaymentList = payPaymentList;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderMessageProducer(TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}
	
	
}
