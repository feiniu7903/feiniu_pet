package com.lvmama.comm.vo.train.order;

import com.lvmama.comm.vo.train.ReqVo;

public class OrderRefundSuccessReqVo extends ReqVo {
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 退票流水号
	 */
	private String refundId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 退款金额
	 */
	private float ticketFee;
	/**
	 * 退款手续费
	 */
	private float ticketCharge;
	/**
	 * 退款类型，例如502（卧铺票退还差价）
	 */
	private int refundType;
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public float getTicketFee() {
		return ticketFee;
	}
	public void setTicketFee(float ticketFee) {
		this.ticketFee = ticketFee;
	}
	public float getTicketCharge() {
		return ticketCharge;
	}
	public void setTicketCharge(float ticketCharge) {
		this.ticketCharge = ticketCharge;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
}
