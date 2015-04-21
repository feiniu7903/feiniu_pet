package com.lvmama.distribution.model.lv;

import com.lvmama.comm.utils.StringUtil;

/**
 * 单个游玩人信息
 * @author lipengcheng
 *
 */
public class Person {
	/** 游玩人姓名 */
	private String name;
	
	private String pinyin;
	/** 游玩人手机号 */
	private String mobile;
	/** 游玩人证件 */
	private String credentials;
	/** 证件类型 */
	private String credentialsType;
	
	/**
	 * 获得签证信息
	 * @return
	 */
	public String getLocalSigned() {
		return this.getName() + this.getPinyin() + this.getMobile() + this.getCredentials() + this.getCredentialsType();
	}
	
	//setter and getter
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

	public String getPinyin() {
		return StringUtil.replaceNullStr(pinyin);
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
