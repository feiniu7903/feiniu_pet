package com.lvmama.sso.vo;

public class AxaxRtnUserBean extends AjaxRtnBaseBean{
	
	protected String userId;
	public AxaxRtnUserBean(final boolean success, final String errorText,final String userId) {
		this.success = success;
		this.errorText = errorText;
		this.userId=userId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	
}
