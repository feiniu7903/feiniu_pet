package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

/**
 * 邮件模板
 * @author Brian
 *
 */
public class ComEmailTemplate  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1349161440591683690L;
	/**
	 * 模板标识
	 */
	private Long emailTemplateId;
	/**
	 * 模板类型标识
	 */
	private Long emailTemplateTypeId;
	/**
	 * 模板名称
	 */
	private String templateName;
	/**
	 * 模板内容
	 */
	private String contentTemplateFile;
	/**
	 * 主题模板
	 */
	private String subjectTemplate;
	/**
	 * 状态
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 创建人
	 */
	private String createUser;
	
	public final Long getEmailTemplateId() {
		return emailTemplateId;
	}
	public final void setEmailTemplateId(Long emailTemplateId) {
		this.emailTemplateId = emailTemplateId;
	}
	public final Long getEmailTemplateTypeId() {
		return emailTemplateTypeId;
	}
	public final void setEmailTemplateTypeId(Long emailTemplateTypeId) {
		this.emailTemplateTypeId = emailTemplateTypeId;
	}
	public final String getTemplateName() {
		return templateName;
	}
	public final void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public final String getContentTemplateFile() {
		return contentTemplateFile;
	}
	public final void setContentTemplateFile(String contentTemplateFile) {
		this.contentTemplateFile = contentTemplateFile;
	}
	public final String getSubjectTemplate() {
		return subjectTemplate;
	}
	public final void setSubjectTemplate(String subjectTemplate) {
		this.subjectTemplate = subjectTemplate;
	}
	public final String getStatus() {
		return status;
	}
	public final void setStatus(String status) {
		this.status = status;
	}
	public final Date getCreateTime() {
		return createTime;
	}
	public final void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public final String getCreateUser() {
		return createUser;
	}
	public final void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
}
