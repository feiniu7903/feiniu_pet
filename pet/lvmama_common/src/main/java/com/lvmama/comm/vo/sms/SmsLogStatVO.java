package com.lvmama.comm.vo.sms;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 短信统计类
 * @author Brian
 *
 */
public class SmsLogStatVO implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8843156058919202828L;
	/**
	 * 计算条数
	 */
	private Integer r;
	/**
	 * 条数
	 */
	private Long total;
	
	/**
	 * 获取中文的字数描述
	 * @return 字数描述
	 */
	public String getCharasters() {
		switch (r) {
			case 0: return "无字数";
			case 1: return "65个字以下";
			case 2: return "65-130";
			case 3: return "130-195";
			case 4: return "195-260";
			case 5: return "260-325";
			case 6: return "325-390";
			case 7: return "390-455";
			default: return "455个字以上";
		}
	}

	/**
	 * 获取合计
	 * @return 合计值
	 */
	public long getTotalCount() {
		return r * total;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this)
				.append("r", r)
				.append("total", total).toString();
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(final Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public Integer getR() {
		return r;
	}
	public void setR(final Integer r) {
		this.r = r;
	}
	public Long getTotal() {
		return total;
	}
	public void setTotal(final Long total) {
		this.total = total;
	}
}
