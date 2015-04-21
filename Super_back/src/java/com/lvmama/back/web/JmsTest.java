package com.lvmama.back.web;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.spring.SpringBeanProxy;

public class JmsTest extends BaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3030696091690128928L;
	private String msg;
	
	public void sendMsg(){
		//Textbox t = (Textbox) jmsWin.getFellow("msg");
		//QueueMessageProducer q = (QueueMessageProducer)SpringBeanProxy.getBean("queueMessageProducer"); 
		TopicMessageProducer t = (TopicMessageProducer)SpringBeanProxy.getBean("orderMessageProducer");
		Message mm = MessageFactory.newOrderPaymentMessage(new Long (msg));
		//mm.setEventType(Constant.ORDER_APPROVE_STATUS.VERIFIED.name());
		t.sendMsg(mm);
		
		// Message mm2 = new Message();
		// mm2.setObjectId(1l);
		// mm2.setObjectType("ORD_ORDER");
		// mm2.setEventType("VISIT_DATE_COMMENT");
		// t.sendMsg(mm2);
		//mm.setMsgContent(t.getValue());
		//q.sendMobileMsg(mm);
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
