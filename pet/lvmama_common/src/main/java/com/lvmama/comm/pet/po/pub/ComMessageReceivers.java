package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 消息分类发送指定人.
 * @author huangli
 *
 */
public class ComMessageReceivers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1694713778459496567L;
	private Long messageTypeId;
	private String messageName;
	private String messageCode;
	private String messageReceivers;//接收人使用逗号分隔
	public Long getMessageTypeId() {
		return messageTypeId;
	}
	public void setMessageTypeId(Long messageTypeId) {
		this.messageTypeId = messageTypeId;
	}
	public String getMessageName() {
		return messageName;
	}
	public void setMessageName(String messageName) {
		this.messageName = messageName;
	}
	public String getMessageCode() {
		return messageCode;
	}
	public void setMessageCode(String messageCode) {
		this.messageCode = messageCode;
	}
	public String getMessageReceivers() {
		return messageReceivers;
	}
	public void setMessageReceivers(String messageReceivers) {
		this.messageReceivers = messageReceivers;
	}
}