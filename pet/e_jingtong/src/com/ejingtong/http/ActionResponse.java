package com.ejingtong.http;

/**
 * 报文包装类
 * @date 2011-6-18
 */
public class ActionResponse {
	private int code = 0;
	private String message;
	private Object data;
	
	public ActionResponse(int code, String message, Object data){
		this.code = code;
		this.message = message;
		this.data = data;
	}
	
	public ActionResponse(int code, String message){
		this.code = code;
		this.message = message;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
