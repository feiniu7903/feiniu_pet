package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;
import java.util.Date;

public class SmsContentSubLog implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2536636503632433044L;
	private Long id;
	private String mobile;
	private String content;
	private Date actualSendDate;
	
	/**
	 * Constructor
	 */
	protected SmsContentSubLog(){}
	
	/**
	 * Constructor
	 * @param id  关联的log的标识
	 * @param content 短信内容
	 * @param mobile 目标手机
	 */
	public SmsContentSubLog(final Long id, final String content, final String mobile) {
		this.id = id;
		this.content = content;
		this.mobile = mobile;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getActualSendDate() {
		return actualSendDate;
	}
	public void setActualSendDate(Date actualSendDate) {
		this.actualSendDate = actualSendDate;
	}	
}
