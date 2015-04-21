package com.lvmama.distribution.model.lv.common;

import com.lvmama.comm.utils.StringUtil;


/**
 * 邮寄人
 * @author lipengcheng
 *
 */
public class PostPerson {
	private String name;
	private String mobile;
	private String credentials;
	private String credentialsType;
	private String address;
	
	public String getName() {
		return StringUtil.replaceNullStr(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return StringUtil.replaceNullStr(mobile);
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCredentials() {
		return StringUtil.replaceNullStr(credentials);
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	public String getCredentialsType() {
		return StringUtil.replaceNullStr(credentialsType);
	}
	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}
	public String getAddress() {
		return StringUtil.replaceNullStr(address);
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
