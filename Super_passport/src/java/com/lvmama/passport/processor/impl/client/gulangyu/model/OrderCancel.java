package com.lvmama.passport.processor.impl.client.gulangyu.model;

/**
 * 取消订单信息
 * @author lipengcheng
 *
 */
public class OrderCancel {
	/** 订单取消状态 */
	private String uuStatus;

	/** 16U订单号 */
	private String uuOrdernum;

	public String getUuStatus() {
		return uuStatus;
	}

	public void setUuStatus(String uuStatus) {
		this.uuStatus = uuStatus;
	}

	public String getUuOrdernum() {
		return uuOrdernum;
	}

	public void setUuOrdernum(String uuOrdernum) {
		this.uuOrdernum = uuOrdernum;
	}
}
