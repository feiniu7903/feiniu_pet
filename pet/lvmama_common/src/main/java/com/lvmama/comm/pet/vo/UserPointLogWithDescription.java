package com.lvmama.comm.pet.vo;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.pet.po.user.UserPointLog;

/**
 * 带描述的用户积分记录
 * 
 * @author Brian
 *
 */
public class UserPointLogWithDescription extends UserPointLog {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -4611546971049480685L;
	/**
	 * 格式化日期
	 */
	private static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	/**
	 * 积分获取的描述
	 */
	private String description;

	@Override
	public String toString() {
		return new ToStringBuilder(this).append("userId", getUserId())
				.append("ruleId", getRuleId()).append("createdDate", getCreatedDate())
				.append("point", getPoint()).append("memo", getMemo())
				.append("description", description).toString();
	}

	/**
	 * 返回格式化后的创建日期
	 * @return 格式化后的日期
	 */
	public String getZhCreatedDate() {
		return SDF.format(this.getCreatedDate());
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}

