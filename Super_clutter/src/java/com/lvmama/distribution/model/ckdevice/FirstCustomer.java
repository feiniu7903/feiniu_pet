package com.lvmama.distribution.model.ckdevice;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.lvmama.comm.utils.StringUtil;


/**
 * 第一游玩人信息
 * @author gaoxin
 *
 */
@XmlRootElement
public class FirstCustomer {
	
	/** 联系人姓名*/
	private String name;
	
	private String pinyin;
	/** 联系人手机号*/
	private String mobile;
	/** 联系人证件*/
	private String credentials;
	/** 证件类型*/
	private String credentialsType;
	public FirstCustomer(){}
	
	
	public FirstCustomer(String name, String pinyin, String mobile,
			String credentials, String credentialsType) {
		this.name = name;
		this.pinyin = pinyin;
		this.mobile = mobile;
		this.credentials = credentials;
		this.credentialsType = credentialsType;
	}

	@XmlElement
	public String getName() {
		return StringUtil.replaceNullStr(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	@XmlElement
	public String getMobile() {
		return StringUtil.replaceNullStr(mobile);
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@XmlElement
	public String getCredentials() {
		return StringUtil.replaceNullStr(credentials);
	}
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	@XmlElement
	public String getCredentialsType() {
		return StringUtil.replaceNullStr(credentialsType);
	}
	public void setCredentialsType(String credentialsType) {
		this.credentialsType = credentialsType;
	}
	@XmlElement
	public String getPinyin() {
		return StringUtil.replaceNullStr(pinyin);
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
