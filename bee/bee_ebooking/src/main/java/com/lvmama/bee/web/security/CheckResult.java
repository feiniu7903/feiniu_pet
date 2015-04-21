package com.lvmama.bee.web.security;

public enum CheckResult {
	SUCCESS(0,"权限校验成功",""),
	FAIL_LOGIN(1,"登录校验失败","/ebooking/login.do"),
	FAIL_URL(2,"URL权限校验失败","/ebooking/error/error_security_url.jsp"),
	FAIL_EPLACE_PRODUCT(3,"EPLACE产品权限校验失败","/ebooking/error/error_security_eplace_product.jsp");
	
	private int code;
	private String name;
	private String redirectUrl;
	
	CheckResult(int code,String name,String redirectUrl){
		this.code = code;
		this.name = name;
		this.redirectUrl = redirectUrl;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRedirectUrl() {
		return redirectUrl;
	}
	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}
}
