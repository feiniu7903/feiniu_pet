package com.lvmama.front.web.ajax;

import java.io.Serializable;

/**
 * IP访问下的响应有用数
 * @author Brian
 */
public class AjaxUsefulCountOfCommentRtn implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -1784000628883429116L;
	/**
	 * 客户端IP
	 */
	private String ip;
	/**
	 * 点评有用数
	 */
	private long count;
	/**
	 * 是否可再次响应
	 */
	private boolean result = false;

	public String getIp() {
		return ip;
	}
	public void setIp(final String ip) {
		this.ip = ip;
	}
	public long getCount() {
		return count;
	}
	public void setCount(final long count) {
		this.count = count;
	}
	public boolean isResult() {
		return result;
	}
	public void setResult(final boolean result) {
		this.result = result;
	}

}
