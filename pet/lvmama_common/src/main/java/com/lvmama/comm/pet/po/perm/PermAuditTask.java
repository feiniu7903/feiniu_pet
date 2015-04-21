package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.Date;

public class PermAuditTask implements Serializable {
	private static final long serialVersionUID = -5945654657451477637L;
	private Long taskId;
	private Long orgId;
	private Long finalTaskId;
	private Long objectId;
	private String objectType;
	private String createUserName;
	private String status;
	private Date createTime;
	private Date updateTime;
	private String auditUserName;
	private byte[] objectJava;
	private String type;
	private String description;
	private String productName;
	private String strStatus;
	private String valid;
	
	public String getValid() {
		return valid;
	}

	public void setValid(String valid) {
		this.valid = valid;
	}

	public String getStrValid() {
		if (valid == null) {
			valid = "";
		}
		if ("Y".equals(this.valid)) {
			return "开启";
		} else {
			return "关闭";
		}
	}
	
	public String getStrStatus() {
		if (this.status.equals("NO_OWNER")) {
			strStatus = "拒绝";
		} else if (this.status.equals("AUDITING_PASSED")) {
			strStatus = "通过";
		}
		return strStatus;
	}

	public void setStrStatus(String strStatus) {
		this.strStatus = strStatus;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getOrgId() {
		return orgId;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getFinalTaskId() {
		return finalTaskId;
	}

	public void setFinalTaskId(Long finalTaskId) {
		this.finalTaskId = finalTaskId;
	}

	public Long getObjectId() {
		return objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getAuditUserName() {
		return auditUserName;
	}

	public void setAuditUserName(String auditUserName) {
		this.auditUserName = auditUserName;
	}

	public byte[] getObjectJava() {
		return objectJava;
	}

	public void setObjectJava(byte[] objectJava) {
		this.objectJava = objectJava;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
