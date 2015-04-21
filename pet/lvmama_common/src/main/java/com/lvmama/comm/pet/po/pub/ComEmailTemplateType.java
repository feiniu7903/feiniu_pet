package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

public class ComEmailTemplateType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1762582193598931079L;
	private Long emailTemplateTypeId;
	private String parentId;
	private Date createTime;
	private String createUser;
	
	
	public final Long getEmailTemplateTypeId() {
		return emailTemplateTypeId;
	}
	public final void setEmailTemplateTypeId(Long emailTemplateTypeId) {
		this.emailTemplateTypeId = emailTemplateTypeId;
	}
	public final String getParentId() {
		return parentId;
	}
	public final void setParentId(String parentId) {
		this.parentId = parentId;
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
