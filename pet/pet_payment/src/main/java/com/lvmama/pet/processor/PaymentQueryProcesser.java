package com.lvmama.pet.processor;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.service.PaymentQueryServiceFactory;

/**
 * 支付查询的消息处理.
 * @author zhangjie
 *
 */
public class PaymentQueryProcesser implements MessageProcesser{
	
	protected final Log LOG =LogFactory.getLog(this.getClass());
	/**
	 * 
	 */
	protected TopicMessageProducer resourceMessageProducer;
	/**
	 * 
	 */
	private PaymentQueryServiceFactory paymentQueryServiceFactory;
	/**
     * 	
     */
	private PayPaymentService payPaymentService;
	
	/**
	 * 支付查询处理.
	 * @param message
	 */
	public void process(Message message) {
		if (message.isPaymentQueryMessage()) {
			LOG.info("message.isPaymentQueryMessage() : " + message.toString());
			PayPayment payment = payPaymentService.selectByPaymentId(message.getObjectId());
			LOG.info("PaymentQuery payment : " + StringUtil.printParam(payment));
			if (null!=payment) {
				paymentQuery(payment);
			}
		}
	}	
	
	/**
	 * 支付查询逻辑
	 * @author ZHANG JIE
	 * @param PayPayment
	 */
	private void paymentQuery(PayPayment payPayment){
		
		PaymentQueryReturnInfo paymentQueryReturnInfo = paymentQueryServiceFactory.query(payPayment);
		LOG.info("PaymentQuery paymentQueryReturnInfo : " + StringUtil.printParam(paymentQueryReturnInfo));
		if(Constant.PAYMENT_QUERY_STATUS.PAYED.name().equalsIgnoreCase(paymentQueryReturnInfo.getCode())){
			if(!Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name().equalsIgnoreCase(payPayment.getStatus())){
				
				PayPayment payment = new PayPayment();
				payment.setPaymentTradeNo(payPayment.getPaymentTradeNo());
				payment.setCallbackInfo(paymentQueryReturnInfo.getCallbackInfo());
				payment.setGatewayTradeNo(paymentQueryReturnInfo.getGatewayTradeNo());
				payment.setRefundSerial(paymentQueryReturnInfo.getRefundSerial());
				payment.setCallbackTime(paymentQueryReturnInfo.getCallbackTime());
				List<PayPayment> paymentDBList = payPaymentService.callBackPayPayment(payment, true);
				
				if(paymentDBList.size()>0){
					resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(payPayment.getPaymentId()));
				}
				
			}
		}	
	}

	public TopicMessageProducer getResourceMessageProducer() {
		return resourceMessageProducer;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public PaymentQueryServiceFactory getPaymentQueryServiceFactory() {
		return paymentQueryServiceFactory;
	}

	public void setPaymentQueryServiceFactory(
			PaymentQueryServiceFactory paymentQueryServiceFactory) {
		this.paymentQueryServiceFactory = paymentQueryServiceFactory;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
}
