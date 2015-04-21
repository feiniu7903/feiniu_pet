package com.lvmama.comm.vo.comment;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class NewsReplyVO implements Serializable {
	/**
	 * 序列号
	 */
	private static final long serialVersionUID = -6150755814340862407L;
	/***
	 * 小驴的本期的ID
	 * (历史值：currentPeriodId)
	 */
	private Long cmtNewsId;
	/**
	 * 回复编号
	 * (历史值：replyId)
	 */
	private Long id;
	/**
	 * 用户Id
	 */
	private Long userId;
	/**
	 * 用户名
	 */
	private String userName;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 发表时间
	 */
	private Date createTime;
	/**
	 * 审核状态
	 */
	private String isAudit = Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name();

	/**
	 * yyyy-MM-dd HH:mm 格式化时间
	 * 
	 * @return 时间
	 */
	public String getFormattedCreateTime() {
		if (null != createTime) {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(createTime);
		}
		return null;
	}
	
	public String getChIsAudit() {
		if (Constant.CMT_AUDIT_STATUS.AUDIT_GOING.name().equals(this.isAudit)) {
			return "待审核";
		} 
		if (Constant.CMT_AUDIT_STATUS.AUDIT_FAILED.name().equals(this.isAudit)) {
			return "审核未通过";
		}
		if (Constant.CMT_AUDIT_STATUS.AUDIT_SUCCESS.name().equals(this.isAudit)) {
			return "审核通过";
		}
		return isAudit;
	}
	
	/**
	 *  ----------------------  get and set property -------------------------------
	 */
	public Long getCmtNewsId() {
		return cmtNewsId;
	}

	public void setCmtNewsId(final Long cmtNewsId) {
		this.cmtNewsId = cmtNewsId;
	}

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getContent() {
		return content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(final Date createTime) {
		this.createTime = createTime;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(final String isAudit) {
		this.isAudit = isAudit;
	}
	
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
}
