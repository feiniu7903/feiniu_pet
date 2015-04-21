package com.lvmama.comm.pet.onlineLetter;
/**
 * @author shangzhengyuan
 * 用户对应的站内信
 */
import java.io.Serializable;
import java.util.Date;

public class LetterUserMessage implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;
	private Long userId;
	private Long templateId;
	private String messageContent;
	private String messageType;
	private Date createdTime;
	private Date readedTime;
	private String title;//站内信标题
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getTemplateId() {
		return templateId;
	}
	public void setTemplateId(Long templateId) {
		this.templateId = templateId;
	}
	public String getMessageContent() {
		return messageContent;
	}
	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getReadedTime() {
		return readedTime;
	}
	public void setReadedTime(Date readedTime) {
		this.readedTime = readedTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
