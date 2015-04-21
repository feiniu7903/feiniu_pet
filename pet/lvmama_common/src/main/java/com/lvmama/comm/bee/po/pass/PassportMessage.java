/**
 * 
 */
package com.lvmama.comm.bee.po.pass;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.jms.Message;
import com.lvmama.comm.vo.Constant;


/**
 * @author yangbin
 *
 */
public class PassportMessage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7134107487975654909L;
	
	private Long messageId;
	private Long objectId;
	private String objectType;
	private String eventType;
	private String addition;
	private String hostname;
	private Date createTime;
	private String processor;
	
	
	public Long getObjectId() {
		return objectId;
	}
	public String getObjectType() {
		return objectType;
	}
	public String getEventType() {
		return eventType;
	}
	public String getAddition() {
		return addition;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public void setAddition(String addition) {
		this.addition = addition;
	}
	public Long getMessageId() {
		return messageId;
	}
	public String getHostname() {
		return hostname;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public void setHostname(String hostname) {
		this.hostname = hostname;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	public static PassportMessage newPassportMessage(final Message msg,final String hostname){
		PassportMessage m = new PassportMessage();
		m.setAddition(msg.getAddition());
		m.setEventType(msg.getEventType());
		m.setHostname(hostname);
		m.setObjectId(msg.getObjectId());
		m.setObjectType(msg.getObjectType());
		
		return m;
	}
	
	public boolean isPassCodeApplyMsg() {
		return Constant.EVENT_TYPE.PASSCODE_APPLY.name().equals(eventType);
	}
	public boolean isPassCodeEventMsg() {
		return Constant.EVENT_TYPE.PASSCODE_EVENT.name().equals(eventType);
	}
	public String getProcessor() {
		return processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	
}
