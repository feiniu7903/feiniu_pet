package com.lvmama.back.sweb.offlinepay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;


@SuppressWarnings("serial")
@Results({
	@Result(name = "distPay", location = "/WEB-INF/pages/back/offlinePay/distPay.jsp")
})
public class DistPayAction extends BaseAction {
	
	

	private Long orderId;
	/**
	 * 
	 */
	private PayPaymentService payPaymentService;
	/**
	 * 
	 */
	protected TopicMessageProducer resourceMessageProducer;
	
	
	private String paymentGateway;

	private String paymentTradeNo;
	
	private String refundSerial;
	
	private String gatewayTradeNo;
	
	private float actualPayFloat;
	
	private Date callbackTime;
	
	private String memo;
	
	private Long amount;
	
	private Long paymentId;
	
	private ComLogService comLogService;
	
	private PayPaymentGatewayService payPaymentGatewayService;
	
	private List<PayPaymentGateway> payPaymentGatewayList;
	
	/**
	 * 订单的下单渠道.
	 */
	private String channel;
	
	private String zhChannel;
	
	
	/**
	 * 订单服务接口.
	 */
	private OrderService orderServiceProxy;
	
	/**
	 * 
	 * @return
	 */
	@Action(value = "/offlinePay/distPay")
	public String doOtherPay(){
		Map<String,String> paramMap=new HashMap<String,String>();
		paramMap.put("gatewayType", Constant.PAYMENT_GATEWAY_TYPE.DIST.name());
		paramMap.put("gatewayStatus", Constant.PAYMENT_GATEWAY_STATUS.ENABLE.name());
		paramMap.put("orderby", "PAYMENT_GATEWAY_ID"); 
		paramMap.put("order", "asc");
		payPaymentGatewayList=payPaymentGatewayService.selectPayPaymentGatewayByParamMap(paramMap);
		this.setActualPayFloat(PriceUtil.convertToYuan(amount));
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(StringUtils.isNotEmpty(order.getChannel())){
			channel = order.getChannel();
			zhChannel = Constant.CHANNEL.getCnName(channel);
		}		
		return "distPay";
	}
	
	/**
	 * 保存支付.
	 * @return  String
	 */
	@Action(value = "/offlinePay/distPaySave")
	public void doSave() {
		JSONResult result = new JSONResult();
		try {
			Assert.hasText(paymentTradeNo, "对账流水号不可以为空");
			Assert.hasText(gatewayTradeNo, "银行交易流水号不可以为空");
			Assert.hasText(refundSerial, "退款流水号不可以为空");
			Assert.notNull(callbackTime, "交易时间不可以为空");
			
			PayPayment payment = new PayPayment();
			payment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			payment.setObjectId(orderId);
			payment.setObjectType("SUPER_ORDER");
			payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payment.setPaymentGateway(paymentGateway);
			payment.setAmount(PriceUtil.convertToFen(actualPayFloat));
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setPaymentTradeNo(paymentTradeNo);
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setGatewayTradeNo(gatewayTradeNo);
			payment.setRefundSerial(refundSerial);
			payment.setCallbackTime(callbackTime);
			payment.setCallbackInfo("[线下支付]"+memo);
			payment.setOperator(getOperatorName());
			
			//通过PaymentTradeNo判断支付记录是否已存在
			boolean isExists=payPaymentService.isExistsByPaymentTradeNo(payment.getPaymentTradeNo());
			if(!isExists){
				paymentId = payPaymentService.savePayment(payment);
				if (payment!=null && payment.isSuccess()) {
					ComLog log = new ComLog();
					log.setObjectId(payment.getPaymentId());
					log.setObjectType("PAY_PAYMENT");
					log.setParentId(payment.getObjectId());
					log.setParentType("ORD_ORDER");
					log.setLogType("PAYMENT_TYPE_OFFLINE");
					log.setLogName("分销线下支付");
					log.setOperatorName(this.getOperatorName());
					log.setContent("由操作人员" + getOperatorName() + "使用" + payment.getPayWayZh() + "线下支付方式支付");
					log.setCreateTime(new Date());
					comLogService.addComLog(log);
					resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
					result.put("paymentId", paymentId);
				}
			}
			else{
				result.put("code", "1");
				result.put("msg", "已存在的对账流水号,请重新输入!");
			}
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

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
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

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getZhChannel() {
		return zhChannel;
	}

	public void setZhChannel(String zhChannel) {
		this.zhChannel = zhChannel;
	}
	
}
