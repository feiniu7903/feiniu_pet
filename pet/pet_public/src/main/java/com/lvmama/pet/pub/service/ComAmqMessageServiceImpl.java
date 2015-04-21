package com.lvmama.pet.pub.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.ComAmqMessageService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.pet.po.pub.ComAmqMessage;
import com.lvmama.comm.utils.ObjectUtil;
import com.lvmama.pet.pub.dao.ComAmqMessageDAO;

public class ComAmqMessageServiceImpl implements ComAmqMessageService {
	private static final Log log = LogFactory.getLog(ComAmqMessageServiceImpl.class);

	private ComAmqMessageDAO comAmqMessageDAO;
	
	public List<ComAmqMessage> selectMessageUnReceived() {
		return comAmqMessageDAO.selectComAmqMessageUnReceived();
	}
	
	public Long countUnReceivedMessage() {
		return comAmqMessageDAO.countUnReceivedMessage();
	}
	
	
	
	@Override
	public void createMessages(Message msg, String compositeTopic, String[] queueArr) {
		for (String forwardQueue : queueArr) {
			ComAmqMessage comAmqMessage = new ComAmqMessage();
			comAmqMessage.setCompositeTopic(compositeTopic);
			comAmqMessage.setCreateTime(new Date());
			comAmqMessage.setForwardQueue(forwardQueue);
			comAmqMessage.setMessageCode(msg.toString());
			comAmqMessage.setObjectJava(ObjectUtil.ObjectToByte(msg));
			comAmqMessageDAO.insertComAmqMessage(comAmqMessage);
		}
	}

	@Override
	public void receivedMessage(Message msg, String destQueue) {
		String messageCode = msg.toString();
		List<ComAmqMessage> list = comAmqMessageDAO.getComAmqMessage(messageCode, destQueue);
		if(list == null || (list != null && list.size()<=0 )){
			log.info("AmqMessageDao getComAmqMessage is null, message code: " + messageCode + ", destQueue: " + destQueue);
		}
		for (ComAmqMessage comAmqMessage : list) {
			comAmqMessage.setReceiveTime(new Date());
			comAmqMessageDAO.updateComAmqMessage(comAmqMessage);
		}
	}

	@Override
	public void cleanFinished() {
		comAmqMessageDAO.cleanFinished();
	}

	public void setComAmqMessageDAO(ComAmqMessageDAO comAmqMessageDAO) {
		this.comAmqMessageDAO = comAmqMessageDAO;
	}

}
