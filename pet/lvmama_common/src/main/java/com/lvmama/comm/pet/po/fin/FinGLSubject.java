package com.lvmama.comm.pet.po.fin;

import java.util.Date;

/**
 * 科目
 * 
 * @author taiqichao
 * 
 */
public class FinGLSubject implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	private Long subjectId;

	/**
	 * 科目CODE
	 */
	private String subjectCode;

	/**
	 * 科目名称
	 */
	private String subjectName;

	/**
	 * 父科目CODE
	 */
	private String parentSubjectCode;

	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 备注
	 */
	private String memo;

	public FinGLSubject() {
	}

	public Long getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getParentSubjectCode() {
		return parentSubjectCode;
	}

	public void setParentSubjectCode(String parentSubjectCode) {
		this.parentSubjectCode = parentSubjectCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

}
