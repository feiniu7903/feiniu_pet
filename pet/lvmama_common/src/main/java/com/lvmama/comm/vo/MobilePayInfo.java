package com.lvmama.comm.vo;

import java.io.Serializable;

public class MobilePayInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6811092645640787552L;
	
	private String code;
	private String message;
	private String returnUrl;
	
	public MobilePayInfo(String code,String message){
		this.code = code;
		this.message = message;
	}
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getReturnUrl() {
		return returnUrl;
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	
	public boolean isSuccess(){
		return "1".equals(getCode());
	}
}
