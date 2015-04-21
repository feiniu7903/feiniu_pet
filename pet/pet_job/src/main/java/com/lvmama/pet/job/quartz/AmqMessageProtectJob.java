package com.lvmama.pet.job.quartz;

import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jms.core.JmsTemplate;

import com.lvmama.comm.jms.ComAmqMessageService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.pet.po.pub.ComAmqMessage;
import com.lvmama.comm.utils.ObjectUtil;
import com.lvmama.comm.vo.Constant;

public class AmqMessageProtectJob implements Runnable {
	private final Log log = LogFactory.getLog(getClass());
	private ComAmqMessageService comAmqMessageService;
	private String jmsServer;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try{
				log.info("AmqMessageProtectJob lunched.");
				ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
						ActiveMQConnection.DEFAULT_USER,
						ActiveMQConnection.DEFAULT_PASSWORD,
						jmsServer);
				Connection connection = connectionFactory.createConnection();
				connection.start();
				JmsTemplate template = new JmsTemplate();
				template.setConnectionFactory(connectionFactory);
				Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
				List<ComAmqMessage> list = comAmqMessageService.selectMessageUnReceived();
				for (ComAmqMessage comAmqMessage : list) {
					Object obj = ObjectUtil.ByteToObject(comAmqMessage.getObjectJava());
					if (obj!=null) {
						Message message = (Message)obj;
						Destination destination = session.createQueue(comAmqMessage.getForwardQueue());
						template.convertAndSend(destination, message);
						log.info("send a unreceived message : " + comAmqMessage.getAmqMessageId() + " msg: " + message.toString());
						session.commit();
					}else{
						log.info("message object is null, amqMessageId:" + comAmqMessage.getAmqMessageId());
					}
				}
				session.close();
				connection.close();
				comAmqMessageService.cleanFinished();
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setComAmqMessageService(ComAmqMessageService comAmqMessageService) {
		this.comAmqMessageService = comAmqMessageService;
	}

	public void setJmsServer(String jmsServer) {
		this.jmsServer = jmsServer;
	}

}
