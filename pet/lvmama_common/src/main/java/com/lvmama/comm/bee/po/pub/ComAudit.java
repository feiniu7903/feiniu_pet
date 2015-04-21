package com.lvmama.comm.bee.po.pub;

import java.io.Serializable;
import java.util.Date;

public class ComAudit implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6686903784691793359L;
	private Long auditId;
	private String operatorName;
	private Long objectId;
	private String objectType;
	private String auditStatus;
	private Date auditTime;
	private String memo;
	private Date createTime;
	//分单人
	private String assignUserId;
	//是否可回收(true可回收 false或null不可回收，可回收的订单不能退单)
	private String isRecycle;
	
	public Long getAuditId() {
		return auditId;
	}
	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
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
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}
	public Date getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(Date auditTime) {
		this.auditTime = auditTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getAssignUserId() {
		return assignUserId;
	}
	public void setAssignUserId(String assignUserId) {
		this.assignUserId = assignUserId;
	}
	public String getIsRecycle() {
		return isRecycle;
	}
	public void setIsRecycle(String isRecycle) {
		this.isRecycle = isRecycle;
	}
}