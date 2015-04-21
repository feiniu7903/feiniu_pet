package com.lvmama.comm.vo.train.order;

import com.lvmama.comm.vo.train.ReqVo;

public class OrderPaidReqVo extends ReqVo{
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 支付结果，0-成功，其他-失败
	 */
	private int paidResult;
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
	public int getPaidResult() {
		return paidResult;
	}
	public void setPaidResult(int paidResult) {
		this.paidResult = paidResult;
	}
}
