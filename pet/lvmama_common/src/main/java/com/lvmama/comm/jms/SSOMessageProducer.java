package com.lvmama.comm.jms;

import javax.jms.Queue;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

public class SSOMessageProducer {
	private static final Log LOG = LogFactory.getLog(SSOMessageProducer.class);
	
    private JmsTemplate template;
	private Queue destination;

	public void setTemplate(JmsTemplate template) {
		this.template = template;
	}

	public void setDestination(Queue destination) {
		this.destination = destination;
	}
    
	/**
	 * 发送消息
	 * @param msg
	 */
    public void sendMsg(SSOMessage msg){
    	LOG.info("send sso message:" + msg);
    	template.convertAndSend(this.destination, msg);
    }

}
