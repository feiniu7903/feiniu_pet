package com.lvmama.comm.pet.po.user;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.MemberGradeUtil;

/**
 * 会员等级日志表
 * 
 * @author yangchen
 * 
 */
public class UserGradeLog implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 33246989817967860L;
	/** 用户Id **/
	private Long userId;
	/** 用户等级 **/
	private String grade;
	/** 创建时间 **/
	private Date createTime;
	/** 会员等级有效期 **/
	private Date validityDate;
	/** 后台操作人的名称 **/
	private String operateName;
	/** 备注 **/
	private String memo;

	/** get,set----------------- **/

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getOperateName() {
		return operateName;
	}

	public void setOperateName(String operateName) {
		this.operateName = operateName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public Date getValidityDate() {
		return validityDate;
	}

	public void setValidityDate(Date validityDate) {
		this.validityDate = validityDate;
	}

	/**
	 * 获取中文的日志
	 * @return
	 */
	public String getChGrade()
	{
		return MemberGradeUtil.getUserMemberGrade(grade)
		.getChGrade().toString();
	}
}
