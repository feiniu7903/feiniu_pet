package com.lvmama.passport.processor.impl.client.newland.model;
/**
 * 申请码响应对象
 * @author chenlinjun
 *
 */
public class RegisterRes {
	private String is_sp_id;
	private String message_id;
	private Status status;

	public String getIs_sp_id() {
		return is_sp_id;
	}

	public void setIs_sp_id(String is_sp_id) {
		this.is_sp_id = is_sp_id;
	}

	public String getMessage_id() {
		return message_id;
	}

	public void setMessage_id(String message_id) {
		this.message_id = message_id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

}
