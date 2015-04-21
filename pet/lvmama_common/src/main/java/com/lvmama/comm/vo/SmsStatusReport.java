package com.lvmama.comm.vo;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class SmsStatusReport implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 4052151786478253427L;
	/**
	 * 发送记录标识
	 */
	private Long serialId;
	/**
	 * 发送结果
	 */
	private Integer result;
	/**
	 * 状态报告日期
	 */
	private Date date;
	/**
	 * 备注
	 */
	private String memo;
	/**
	 * 当serialId无法存放唯一标识时的替代品
	 */
	private String beforeMemo;
	
	public SmsStatusReport() {}
	
	public SmsStatusReport(Long serialId, Integer result, Date date, String memo) {
		this.serialId = serialId;
		this.result = result;
		this.date = date;
		this.memo = memo;
	}
	
	public SmsStatusReport(String beforeMemo, Integer result, Date date, String memo) {
		this.beforeMemo = beforeMemo;
		this.result = result;
		this.date = date;
		this.memo = memo;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}	

	public Long getSerialId() {
		return serialId;
	}

	public void setSerialId(final Long serialId) {
		this.serialId = serialId;
	}

	public Integer getResult() {
		return result;
	}

	public void setResult(final Integer result) {
		this.result = result;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public final String getMemo() {
		return memo;
	}

	public void setMemo(final String memo) {
		this.memo = memo;
	}

	public String getBeforeMemo() {
		return beforeMemo;
	}

	public void setBeforeMemo(String beforeMemo) {
		this.beforeMemo = beforeMemo;
	}
	
	
}
