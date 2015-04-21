package com.lvmama.pet.web.system;

import java.util.List;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.jms.core.JmsTemplate;

import com.lvmama.comm.jms.ComAmqMessageService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.pet.po.pub.ComAmqMessage;
import com.lvmama.comm.utils.ObjectUtil;
import com.lvmama.pet.web.BaseAction;

/**
 * 
 * 
 * @author Libo Wang
 * 
 */
@Result(name = "index", type="redirect", location = "../index.do")
public class AmqResendAction extends BaseAction {

	private static final long serialVersionUID = 2486028676289572251L;

	private static Logger log = Logger.getLogger(AmqResendAction.class);

	private ComAmqMessageService comAmqMessageService;

	@Action("/comm/resendAmq")
	public String resendAmq() {
		try {
			log.info("AmqResendAction lunched.");
			String jmsServer = "";
			ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
					ActiveMQConnection.DEFAULT_USER, ActiveMQConnection.DEFAULT_PASSWORD, jmsServer);
			Connection connection = connectionFactory.createConnection();
			connection.start();
			JmsTemplate template = new JmsTemplate();
			template.setConnectionFactory(connectionFactory);
			Session session = connection.createSession(Boolean.TRUE, Session.AUTO_ACKNOWLEDGE);
			List<ComAmqMessage> list = comAmqMessageService.selectMessageUnReceived();
			for (ComAmqMessage comAmqMessage : list) {
				Object obj = ObjectUtil.ByteToObject(comAmqMessage.getObjectJava());
				if (obj != null) {
					Message message = (Message) obj;
					Destination destination = session.createQueue(comAmqMessage.getForwardQueue());
					template.convertAndSend(destination, message);
					log.info("send a unreceived message : " + comAmqMessage.getAmqMessageId()
							+ " msg: " + message.toString());
					session.commit();
				} else {
					log.info("message object is null, amqMessageId:"
							+ comAmqMessage.getAmqMessageId());
				}
			}
			session.close();
			connection.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "index";
	}

	public void setComAmqMessageService(ComAmqMessageService comAmqMessageService) {
		this.comAmqMessageService = comAmqMessageService;
	}
}
