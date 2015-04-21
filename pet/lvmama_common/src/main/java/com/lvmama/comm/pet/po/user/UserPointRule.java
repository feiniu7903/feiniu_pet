package com.lvmama.comm.pet.po.user;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;
/**
 * 用户积分规则实体Bean
 * @author Brian
 *
 */
public class UserPointRule  implements Serializable {
	/**
	 * 序列数
	 */
	private static final long serialVersionUID = 2634959016673267024L;
	/**
	 * 积分规则标识
	 */
	private String ruleId;
	/**
	 * 积分规则描述
	 */
	private String ruleDescription;
	/**
	 * 积分
	 */
	private Long point;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("ruleId", ruleId)
				.append("ruleDesciption", ruleDescription).append("point", point)
				.toString();
	}

	public String getRuleId() {
		return ruleId;
	}
	public void setRuleId(final String ruleId) {
		this.ruleId = ruleId;
	}
	public String getRuleDescription() {
		return ruleDescription;
	}
	public void setRuleDescription(final String ruleDescription) {
		this.ruleDescription = ruleDescription;
	}
	public Long getPoint() {
		return point;
	}
	public void setPoint(final Long point) {
		this.point = point;
	}
}
