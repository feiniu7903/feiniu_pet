package com.lvmama.pet.processor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.vo.Constant;

/**
 * 
 * @author zhangjie
 *
 */
public class OrderPaySuccessUpdateProcesser implements MessageProcesser {
	protected final Log LOG =LogFactory.getLog(this.getClass());

	private PayPaymentService payPaymentService;
	private TopicMessageProducer resourceMessageProducer;

	/**
	 * 订单支付成功状态修改（补偿订单支付成功但是未回调信息,请核实详细订单支付信息）
	 * @param message
	 */
	public void process(Message message) {
		if (message.isOrderPaySuccessUpdateMsg()) {

			Long paymentId = message.getObjectId();

			LOG.info("Order OrderPaySuccessUpdate init:"+paymentId.toString());
			
			PayPayment payment = payPaymentService.selectByPaymentId(paymentId);
			
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setNotified("false");
			
			boolean isOk = payPaymentService.updatePayment(payment);
			if(isOk){
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
			}
		}
	}
	
	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}
	
}
