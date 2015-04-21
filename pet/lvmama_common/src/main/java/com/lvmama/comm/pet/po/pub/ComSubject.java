package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.enums.ComSubjectTypeEnum;

public class ComSubject implements Serializable {
	
	private static final long serialVersionUID = 131799826688397020L;

	private Long comSubjectId;

	private String subjectName;

	private String subjectPinyin;

	private String subjectType;

	private Long usedByCount;//引用次数

	private String ifBold;//是否标红

	private Date createTime;

	private Date updateTime;

	private Long seq;

	private String isValid;//状态

	public Long getComSubjectId() {
		return comSubjectId;
	}

	public void setComSubjectId(Long comSubjectId) {
		this.comSubjectId = comSubjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getSubjectPinyin() {
		return subjectPinyin;
	}

	public void setSubjectPinyin(String subjectPinyin) {
		this.subjectPinyin = subjectPinyin;
	}

	public String getSubjectType() {
		return subjectType;
	}

	public String getSubjectTypeStr() {
		return ComSubjectTypeEnum.getName(this.subjectType);
	}

	public void setSubjectType(String subjectType) {
		this.subjectType = subjectType;
	}

	public Long getUsedByCount() {
		return usedByCount;
	}

	public void setUsedByCount(Long usedByCount) {
		this.usedByCount = usedByCount;
	}

	public String getIfBold() {
		return ifBold;
	}

	public void setIfBold(String ifBold) {
		this.ifBold = ifBold;
	}
	
	public String getIfBoldStr() {
		return "Y".equals(ifBold)? "是":"否"; 
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

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getIsValid() {
		return isValid;
	}
	
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
}