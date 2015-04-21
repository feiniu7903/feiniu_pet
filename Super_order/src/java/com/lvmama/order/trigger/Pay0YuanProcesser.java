package com.lvmama.order.trigger;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.vo.Constant;

public class Pay0YuanProcesser implements MessageProcesser  {
	
	private static final Log log = LogFactory.getLog(Pay0YuanProcesser.class);
	private TopicMessageProducer resourceMessageProducer;
	private PayPaymentService payPaymentService;
	
	public void process(Message message) {
		if(message.isOrderPay0Yuan()){
			PayPayment payment = new PayPayment();
			if(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.name().equals(message.getAddition())) {
				payment.setBizType(Constant.PAYMENT_BIZ_TYPE.VST_ORDER.getCode());
			}else {
				payment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.getCode());	
			}
			payment.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER.name());
			payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payment.setObjectId(message.getObjectId());
			payment.setPaymentGateway(Constant.PAYMENT_GATEWAY.PAY_0_YUAN.name());
			payment.setAmount(0L);
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setCallbackTime(new Date());
			payment.setPaymentTradeNo(payment.getSerial());
			payment.setGatewayTradeNo(payment.getSerial());

			Long paymentId = payPaymentService.savePayment(payment);
			if(paymentId!=null){
				resourceMessageProducer.sendMsg(MessageFactory.newPaymentSuccessCallMessage(paymentId));
			}
		}
	}

	public void setResourceMessageProducer(TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}


	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}
	
}
