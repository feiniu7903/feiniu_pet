package com.lvmama.comm.edm;

import java.io.Serializable;

/**
 * EDM邮件模板对象
 * @author likun
 *
 */
public class TemplateDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 发送者中文名称
	 */
	private String fromName;
	/**
	 * 发送者邮箱
	 */
	private String senderEmail;
	/**
	 * 主题
	 */
	private String subject;
	/**
	 * 模板名称
	 */
	private String tempName;
	/**
	 * 模板内容
	 */
	private String templateContent;

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTempName() {
		return tempName;
	}

	public void setTempName(String tempName) {
		this.tempName = tempName;
	}

	public String getTemplateContent() {
		return templateContent;
	}

	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
}
