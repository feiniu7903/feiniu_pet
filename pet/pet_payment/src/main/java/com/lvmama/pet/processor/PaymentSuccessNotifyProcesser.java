package com.lvmama.pet.processor;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.pet.PayPaymentNotifier;

public class PaymentSuccessNotifyProcesser implements MessageProcesser {

	private PayPaymentService payPaymentService;
	private PayPaymentNotifier payPaymentNotifier;
	
	/**
	 * 接收到支付成功后消息的支付记录和预授权支付记录的处理.
	 */
	@Override
	public void process(Message message) {
//		if(message.isPrePaymentSuccessCallMessage()){
//			PayPayment payment = payPaymentService.selectByPaymentId(message.getObjectId());
//			PayPrePayment prePayment =  payPaymentService.selectPrePaymentByPaymentId(payment.getPaymentId());
//			payPaymentNotifier.notifyPaymentAndPre(payment, prePayment,Constant.PAYMENT_PRE_STATUS.PRE_PAY.name());
//		}
		if (message.isPaymentSuccessCallMessage()) {
			PayPayment payment = payPaymentService.selectByPaymentId(message.getObjectId());
			
			if (payment.isSuccess() && !payment.isNotified()) {
				payPaymentNotifier.notify(payment);
			}
	   }
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setPayPaymentNotifier(PayPaymentNotifier payPaymentNotifier) {
		this.payPaymentNotifier = payPaymentNotifier;
	}

}
