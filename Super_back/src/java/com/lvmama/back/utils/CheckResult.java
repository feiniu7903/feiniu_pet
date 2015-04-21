package com.lvmama.back.utils;

public class CheckResult {

	private boolean success;
	private String message;
	
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isSuccess() {
		return success;
	}
	public String getMessage() {
		return message;
	}
}
