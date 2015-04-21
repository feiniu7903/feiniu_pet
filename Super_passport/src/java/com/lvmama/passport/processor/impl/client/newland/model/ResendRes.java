package com.lvmama.passport.processor.impl.client.newland.model;

/**
 * 重发短信响应对象
 * 
 * @author chenlinjun
 * 
 */
public class ResendRes {
	private String transactionId;
	private String is_sp_id;
	private String messageId;
	private String wbmp;
	private Status status;

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getIs_sp_id() {
		return is_sp_id;
	}

	public void setIs_sp_id(String is_sp_id) {
		this.is_sp_id = is_sp_id;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public String getWbmp() {
		return wbmp;
	}

	public void setWbmp(String wbmp) {
		this.wbmp = wbmp;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
