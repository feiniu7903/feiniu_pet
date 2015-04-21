package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

public class ComParttimeUser implements Serializable {
	private static final long serialVersionUID = -5066763354555416734L;
	
	private Long id; // 促销员ID
	private String userName; // 促销员名称
	private String passWord; // 密码
	private Long channelId; // 渠道标识
	private String isValid = "Y";  //是否有效
	private String cityId;   //城市标识
	private String confirmSms; //第一次下发的确认短信
	private String smsTemplate; //用户接受注册的注册短信
	private String mailTemplate; //注册的邮件模板
	
	private String cityName; //城市名称
	private String provinceId; //省份标识
	private String provinceName;  //省份名称
	private String channelName; // 渠道名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getConfirmSms() {
		return confirmSms;
	}

	public void setConfirmSms(String confirmSms) {
		this.confirmSms = confirmSms;
	}

	public String getSmsTemplate() {
		return smsTemplate;
	}

	public void setSmsTemplate(String smsTemplate) {
		this.smsTemplate = smsTemplate;
	}

	public String getMailTemplate() {
		return mailTemplate;
	}

	public void setMailTemplate(String mailTemplate) {
		this.mailTemplate = mailTemplate;
	}


	
}
