package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 持久化MESSAGE（保障）.
 * @author Alex Wang
 *
 */
public class ComAmqMessage implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3752014873089810521L;

	/**
	 * PK.
	 */
	private Long amqMessageId;
	
	/**
	 * 消息代码
	 */
	private String messageCode;
	
	/**
	 * 直接发送到的topic.
	 */
	private String compositeTopic;
	/**
	 * 转发到的queue.
	 */
	private String forwardQueue;
	/**
	 * 创建时间.
	 */
	private Date createTime;
	/**
	 * 接收状态.
	 */
	private String receiveStatus;
	/**
	 * 收到时间.
	 */
	private Date receiveTime;
	/**
	 * 对象.
	 */
	private byte[] objectJava;
	
	public Long getAmqMessageId() {
		return amqMessageId;
	}
	public String getCompositeTopic() {
		return compositeTopic;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getForwardQueue() {
		return forwardQueue;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public byte[] getObjectJava() {
		return objectJava;
	}
	public void setAmqMessageId(Long amqMessageId) {
		this.amqMessageId = amqMessageId;
	}
	public void setCompositeTopic(String compositeTopic) {
		this.compositeTopic = compositeTopic;
	}
	public void setForwardQueue(String forwardQueue) {
		this.forwardQueue = forwardQueue;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public void setObjectJava(byte[] objectJava) {
		this.objectJava = objectJava;
	}
	public String getReceiveStatus() {
		return receiveStatus;
	}
	public void setReceiveStatus(String receiveStatus) {
		this.receiveStatus = receiveStatus;
	}
}
