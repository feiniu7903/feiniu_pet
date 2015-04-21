/**
 * 
 */
package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * 行程当中使用.包含天与晚的数量
 * @author yangbin
 * 
 */
public class Time implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2848827929487134969L;
	private Long days;
	private Long nights;

	/**
	 * @return the days
	 */
	public Long getDays() {
		return days;
	}

	/**
	 * @param days
	 *            the days to set
	 */
	public void setDays(Long days) {
		this.days = days;
	}

	/**
	 * @return the nights
	 */
	public Long getNights() {
		return nights;
	}

	/**
	 * @param nights
	 *            the nights to set
	 */
	public void setNights(Long nights) {
		this.nights = nights;
	}
	
	boolean isEmptyTime(){
		return (nights==null||nights<1)&&(days==null||days<1);
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(days==null?0:days);
		sb.append("-");
		sb.append(nights==null?0:nights);
		return sb.toString();
	}
	
	/**
	 * 返回带中文的天晚
	 * @return
	 */
	public String toZhString(){
		StringBuffer sb = new StringBuffer();
		sb.append(days==null?0:days);
		sb.append("天");
		sb.append(nights==null?0:nights);
		sb.append("晚");
		return sb.toString();
	}

	public static Time create(String t) {
		Time tt = new Time();
		if (StringUtils.isNotEmpty(t)) {
			String arr[] = StringUtils.split(t, "-");
			if (!ArrayUtils.isEmpty(arr)&&arr.length>=2) {
				tt.days = NumberUtils.toLong(arr[0]);
				tt.nights = NumberUtils.toLong(arr[1]);
			}

		}
		return tt;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((days == null) ? 0 : days.hashCode());
		result = prime * result + ((nights == null) ? 0 : nights.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Time other = (Time) obj;
		if (days == null) {
			if (other.days != null)
				return false;
		} else if (!days.equals(other.days))
			return false;
		if (nights == null) {
			if (other.nights != null)
				return false;
		} else if (!nights.equals(other.nights))
			return false;
		return true;
	}
	
	
}
