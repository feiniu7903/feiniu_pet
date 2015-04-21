package com.lvmama.comm.pet.po.perm;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class PermFinalAuditTask implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 294417620009673131L;
	private Long finalTaskId;
	private String status;
	private Date createTime;
	private Date updateTime;
	private String auditUserName;
	private Long objectId;
	private String objectType;
	private String description;
	private String taskLevel;
	
	private String createUserName;
	
	private String productName;
	private String firstAuditUserName;	//一审人
	
	public String getZhStatus() {
		return Constant.PERM_FINAL_AUDIT_STATE_ENUM.EFFECTED.name().equals(getStatus()) ? "生效" : "不生效";
	}
	
	public String getFirstAuditUserName() {
		return firstAuditUserName;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public void setFirstAuditUserName(String firstAuditUserName) {
		this.firstAuditUserName = firstAuditUserName;
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

	public Long getFinalTaskId() {
		return finalTaskId;
	}

	public void setFinalTaskId(Long finalTaskId) {
		this.finalTaskId = finalTaskId;
	}

	public String getTaskLevel() {
		return taskLevel;
	}

	public void setTaskLevel(String taskLevel) {
		this.taskLevel = taskLevel;
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
	
	public String getZhTaskLevel() {
		return this.taskLevel != null ? (this.taskLevel.equals("Y") ? "紧急" : "普通") : "普通";
	}

}
