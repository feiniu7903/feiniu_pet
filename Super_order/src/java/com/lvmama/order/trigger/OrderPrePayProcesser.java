package com.lvmama.order.trigger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.order.logic.SmsSendLogic;

public class OrderPrePayProcesser implements MessageProcesser  {
	
	private static final Log log = LogFactory.getLog(OrderPrePayProcesser.class);
	private SmsSendLogic smsSendLogic;
	public void process(Message message) {
		if(message.isPrePaymentSuccessCallMessage()){
			log.info("====== PRE_PAY SMS ==============="+message.getObjectId());
		     smsSendLogic.sendOrderPrePay(message);
		}
	}


	public void setSmsSendLogic(SmsSendLogic smsSendLogic) {
		this.smsSendLogic = smsSendLogic;
	}


	
	
}
