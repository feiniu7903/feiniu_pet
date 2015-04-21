package com.lvmama.comm.vo.train.order;

import com.lvmama.comm.vo.train.ReqVo;

public class OrderCancelReqVo extends ReqVo {
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 订单号
	 */
	private String orderId;
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
