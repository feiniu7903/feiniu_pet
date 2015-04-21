package com.lvmama.pet.pub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComAmqMessage;

public class ComAmqMessageDAO extends BaseIbatisDAO {
	private static final Log log = LogFactory.getLog(ComAmqMessageDAO.class);

	public List<ComAmqMessage> selectComAmqMessageUnReceived() {
		return super.queryForList("COM_AMQ_MESSAGE.selectMessageUnReceived");
	}

	public Long countUnReceivedMessage() {
		return (Long)super.queryForObject("COM_AMQ_MESSAGE.countUnReceivedMessage");
	}
	
	public List<ComAmqMessage> getComAmqMessage(String messageCode, String destQueue) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("messageCode", messageCode);
		map.put("forwardQueue", destQueue);
		return super.queryForList("COM_AMQ_MESSAGE.selectByMessageCodeAndForwardQueue", map);
	}
	
	public void insertComAmqMessage(ComAmqMessage comAmqMessage) {
		super.insert("COM_AMQ_MESSAGE.insert", comAmqMessage);
	}

	public void updateComAmqMessage(ComAmqMessage comAmqMessage) {
		int result = super.update("COM_AMQ_MESSAGE.updateByPrimaryKey", comAmqMessage);
		if (result<=0){
			log.info("AmqMessageDao updateComAmqMessage is null, message code: " + comAmqMessage.getMessageCode() + ", destQueue: " + comAmqMessage.getForwardQueue() + 
					", messageId: " + comAmqMessage.getAmqMessageId());	
		}
	}
	
	public void cleanFinished() {
		super.delete("COM_AMQ_MESSAGE.cleanFinished");
	}
}
