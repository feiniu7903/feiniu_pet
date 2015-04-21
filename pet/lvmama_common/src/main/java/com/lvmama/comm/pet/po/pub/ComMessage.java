package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 提醒与消息
 * @author huangli
 *
 */
public class ComMessage implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5447560960379380439L;
	private Long messageId;
	private String sender;//发送者
	private String receiver;//接收者
	private String content;
	private Date createTime;
	private Date beginTime;//开始显示时间
	private String status;//新建、完成(CREATE，FINISHED)
	private String zkStatus;//新建、完成(CREATE，FINISHED)
	public Long getMessageId() {
		return messageId;
	}
	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public String getZkStatus() {
		this.zkStatus="新建";
		if("FINISHED".equals(this.status)){
			this.zkStatus="完成";
		}
		return zkStatus;
	}
	public String getSub20Content() {
		if(content!=null&&content.length()>=CONTENT_LENGTH){
			return content.substring(0, CONTENT_LENGTH)+"...";
		}else{
			return content;
		}
	}
	/**
	 * 内容显示长度
	 */
	final static int CONTENT_LENGTH=25;	
}