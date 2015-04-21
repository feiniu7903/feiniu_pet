package com.lvmama.comm.pet.po.user;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 用户积分日志实体Bean
 * @author Brian
 *
 */
public class UserPointLog implements java.io.Serializable {
	/**
	 * 序列数
	 */
	private static final long serialVersionUID = 2190785900954933512L;
	/**
	 * 主键
	 */
	private Long pointLogId;
	/**
	 * 用户标识
	 */
	private Long userId;
	/**
	 * 规则标识
	 */
	private String ruleId;
	/**
	 * 创建日期
	 */
	private Date createdDate;
	/**
	 * 积分
	 */
	private Long point;
	/**
	 * 备注
	 */
	private String memo;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("pointLogId", pointLogId)
				.append("userId", userId).append("ruleId", ruleId)
				.append("createdDate", createdDate).append("point", point)
				.append("memo", memo).toString();
	}

	public Long getPointLogId() {
		return pointLogId;
	}
	public void setPointLogId(Long pointLogId) {
		this.pointLogId = pointLogId;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(final Long userId) {
		this.userId = userId;
	}
	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(final String ruleId) {
		this.ruleId = ruleId;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(final Date createdDate) {
		this.createdDate = createdDate;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(final Long point) {
		this.point = point;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(final String memo) {
		this.memo = memo;
	}

}
