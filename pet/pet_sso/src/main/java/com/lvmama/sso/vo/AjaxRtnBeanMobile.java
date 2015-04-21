package com.lvmama.sso.vo;

public class AjaxRtnBeanMobile  extends AjaxRtnBaseBean{
	
	private String code;
	private String message;
	private String errorMessage;
	private String alipayAccessToken;
	private String alipayRefreshToken;
	
	public String getAlipayAccessToken() {
		return alipayAccessToken;
	}

	public void setAlipayAccessToken(String alipayAccessToken) {
		this.alipayAccessToken = alipayAccessToken;
	}

	public String getAlipayRefreshToken() {
		return alipayRefreshToken;
	}

	public void setAlipayRefreshToken(String alipayRefreshToken) {
		this.alipayRefreshToken = alipayRefreshToken;
	}
	
	/**
	 * 构造函数
	 * @param success success
	 * @param errorText errorText
	 */
	public AjaxRtnBeanMobile(final boolean success, final String errorText) {
		this.success = success;
		this.errorText = errorText;
	}

	public AjaxRtnBeanMobile(String code,String errorMessage){
		this.code = code;
		this.errorMessage = errorMessage;
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
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}
