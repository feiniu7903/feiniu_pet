package com.lvmama.comm.pet.po.sms;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 上行短信内容的指令集
 * @author Brian
 */
public class SmsInstruction implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -1948616071759749085L;
	/**
	 * 指令码
	 */
	private String instructionCode;
	/**
	 * 含有优惠券的短信内容
	 */
	private String couponCode;
	/**
	 * 描述
	 */
	private String description;
	/**
	 * 生成优惠券的标识
	 */
	private String couponId;

	/**
	 * 构造器
	 */
	public SmsInstruction() {

	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("instructionCode", instructionCode)
				.append("couponCode", couponCode)
				.append("couponId", couponId).toString();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public String getInstructionCode() {
		return instructionCode;
	}

	public void setInstructionCode(final String instructionCode) {
		this.instructionCode = instructionCode;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(final String couponCode) {
		this.couponCode = couponCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(final String couponId) {
		this.couponId = couponId;
	}

}
