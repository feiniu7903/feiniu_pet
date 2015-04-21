package com.lvmama.pet.processor;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.service.pay.PayPaymentRefundmentService;
import com.lvmama.pet.PayPaymentNotifier;

public class RefundSuccessNotifyProcesser implements MessageProcesser {

	private PayPaymentRefundmentService payPaymentRefundmentService;
	private PayPaymentNotifier payPaymentNotifier;
	
	/**
	 * 接收到退款成功后消息通知业务系统
	 */
	@Override
	public void process(Message message) {
		if (message.isRefundSuccessCallMessage()) {
			PayPaymentRefundment payRefundment = payPaymentRefundmentService.selectPaymentRefundmentByPK(message.getObjectId());
			if (payRefundment!=null && payRefundment.isSuccess() && !payRefundment.isNotified()) {
				payPaymentNotifier.notify(payRefundment);
			}
	   }
	}

	public void setPayPaymentRefundmentService(
			PayPaymentRefundmentService payPaymentRefundmentService) {
		this.payPaymentRefundmentService = payPaymentRefundmentService;
	}

	public void setPayPaymentNotifier(PayPaymentNotifier payPaymentNotifier) {
		this.payPaymentNotifier = payPaymentNotifier;
	}

}
