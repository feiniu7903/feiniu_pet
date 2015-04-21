package com.lvmama.sso.vo;

import java.util.Date;

/**
 * 验证短信记录
 * @author Brian
 *
 */
public class ValidateSmsHistory {
	/**
	 * 创建时间
	 */
	private Date date;
	/**
	 * 次数
	 */
	private int count = 0;

	/**
	 * 构造器
	 */
	public ValidateSmsHistory() {
		this.date = new Date(System.currentTimeMillis());
		this.count = 0;
	}

	/**
	 *  累加
	 */
	public void addCount() {
		this.count  += 1;
	}

	public Date getDate() {
		return date;
	}

	public int getCount() {
		return count;
	}

}
