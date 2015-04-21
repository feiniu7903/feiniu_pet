package com.lvmama.finance.common.ibatis.po;

import java.util.Date;


public class ComLog implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long logId;
	private Date createTime;
	private String content;
	private String memo;
	private String userid;
	private Long objectId;
	private String objectType;
	private String logType;
	private String logName;
	private String operatorName;
	private Long parentId;
	private String parentType;

	/** default constructor */
	public ComLog() {
	}

	/** minimal constructor */
	public ComLog(Long logId) {
		this.logId = logId;
	}

	/** full constructor */
	public ComLog(Long logId, Date createTime, String content, String memo, String userid, Long objectId, String objectType, String logType, String logName, String operatorName, Long parentId, String parentType) {
		this.logId = logId;
		this.createTime = createTime;
		this.content = content;
		this.memo = memo;
		this.userid = userid;
		this.objectId = objectId;
		this.objectType = objectType;
		this.logType = logType;
		this.logName = logName;
		this.operatorName = operatorName;
		this.parentId = parentId;
		this.parentType = parentType;
	}

	// Property accessors

	public Long getLogId() {
		return this.logId;
	}

	public void setLogId(Long logId) {
		this.logId = logId;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public Long getObjectId() {
		return this.objectId;
	}

	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}

	public String getObjectType() {
		return this.objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getLogType() {
		return this.logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getLogName() {
		return this.logName;
	}

	public void setLogName(String logName) {
		this.logName = logName;
	}

	public String getOperatorName() {
		return this.operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentType() {
		return this.parentType;
	}

	public void setParentType(String parentType) {
		this.parentType = parentType;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof ComLog))
			return false;
		ComLog castOther = (ComLog) other;

		return ((this.getLogId() == castOther.getLogId()) || (this.getLogId() != null && castOther.getLogId() != null && this.getLogId().equals(castOther.getLogId()))) && ((this.getCreateTime() == castOther.getCreateTime()) || (this.getCreateTime() != null && castOther.getCreateTime() != null && this.getCreateTime().equals(castOther.getCreateTime()))) && ((this.getContent() == castOther.getContent()) || (this.getContent() != null && castOther.getContent() != null && this.getContent().equals(castOther.getContent()))) && ((this.getMemo() == castOther.getMemo()) || (this.getMemo() != null && castOther.getMemo() != null && this.getMemo().equals(castOther.getMemo())))
				&& ((this.getUserid() == castOther.getUserid()) || (this.getUserid() != null && castOther.getUserid() != null && this.getUserid().equals(castOther.getUserid()))) && ((this.getObjectId() == castOther.getObjectId()) || (this.getObjectId() != null && castOther.getObjectId() != null && this.getObjectId().equals(castOther.getObjectId()))) && ((this.getObjectType() == castOther.getObjectType()) || (this.getObjectType() != null && castOther.getObjectType() != null && this.getObjectType().equals(castOther.getObjectType()))) && ((this.getLogType() == castOther.getLogType()) || (this.getLogType() != null && castOther.getLogType() != null && this.getLogType().equals(castOther.getLogType())))
				&& ((this.getLogName() == castOther.getLogName()) || (this.getLogName() != null && castOther.getLogName() != null && this.getLogName().equals(castOther.getLogName()))) && ((this.getOperatorName() == castOther.getOperatorName()) || (this.getOperatorName() != null && castOther.getOperatorName() != null && this.getOperatorName().equals(castOther.getOperatorName()))) && ((this.getParentId() == castOther.getParentId()) || (this.getParentId() != null && castOther.getParentId() != null && this.getParentId().equals(castOther.getParentId()))) && ((this.getParentType() == castOther.getParentType()) || (this.getParentType() != null && castOther.getParentType() != null && this.getParentType().equals(castOther.getParentType())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getLogId() == null ? 0 : this.getLogId().hashCode());
		result = 37 * result + (getCreateTime() == null ? 0 : this.getCreateTime().hashCode());
		result = 37 * result + (getContent() == null ? 0 : this.getContent().hashCode());
		result = 37 * result + (getMemo() == null ? 0 : this.getMemo().hashCode());
		result = 37 * result + (getUserid() == null ? 0 : this.getUserid().hashCode());
		result = 37 * result + (getObjectId() == null ? 0 : this.getObjectId().hashCode());
		result = 37 * result + (getObjectType() == null ? 0 : this.getObjectType().hashCode());
		result = 37 * result + (getLogType() == null ? 0 : this.getLogType().hashCode());
		result = 37 * result + (getLogName() == null ? 0 : this.getLogName().hashCode());
		result = 37 * result + (getOperatorName() == null ? 0 : this.getOperatorName().hashCode());
		result = 37 * result + (getParentId() == null ? 0 : this.getParentId().hashCode());
		result = 37 * result + (getParentType() == null ? 0 : this.getParentType().hashCode());
		return result;
	}

}