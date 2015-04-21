package com.lvmama.comm.vo;

import java.util.Date;

/**
 * Place entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ViewComPcSubject implements java.io.Serializable {
	private static final long serialVersionUID = 7100885880714483558L;
	/**
	 * 主题
	 */
	private Long subjectId;
	private String subjectName;
	private String usedBy;
	private Integer usedByCount;
	private String ifBlod;
	private Date createTime;
	private Date updateTime;
	private String isValid;
	private Integer seq;

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getUsedBy() {
		return usedBy;
	}

	public void setUsedBy(String usedBy) {
		this.usedBy = usedBy;
	}

	public Integer getUsedByCount() {
		return usedByCount;
	}

	public void setUsedByCount(Integer usedByCount) {
		this.usedByCount = usedByCount;
	}

	public String getIfBlod() {
		return ifBlod;
	}

	public void setIfBlod(String ifBlod) {
		this.ifBlod = ifBlod;
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

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}