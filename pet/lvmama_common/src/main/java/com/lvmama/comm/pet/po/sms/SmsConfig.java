package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;

/**
 * 短信全局配置
 * 
 * @author ready
 * @date 2014/4/25
 */
public class SmsConfig implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 全局默认通道
	 */
	private String defaultChannel;
	/**
	 * 重发首选通道
	 */
	private String resendFirstChannel;
	/**
	 * 重发次选通道
	 */
	private String resendSecondaryChannel;

	public String getDefaultChannel() {
		return defaultChannel;
	}

	public void setDefaultChannel(String defaultChannel) {
		this.defaultChannel = defaultChannel;
	}

	public String getResendFirstChannel() {
		return resendFirstChannel;
	}

	public void setResendFirstChannel(String resendFirstChannel) {
		this.resendFirstChannel = resendFirstChannel;
	}

	public String getResendSecondaryChannel() {
		return resendSecondaryChannel;
	}

	public void setResendSecondaryChannel(String resendSecondaryChannel) {
		this.resendSecondaryChannel = resendSecondaryChannel;
	}
}
