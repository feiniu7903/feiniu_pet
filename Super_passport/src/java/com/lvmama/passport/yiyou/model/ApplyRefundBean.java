package com.lvmama.passport.yiyou.model;

public final class ApplyRefundBean extends HeadBean {
	private String timeStamp;
	private String orderId;
	private String orderNo;
	private String refundReason;
	
	public String getTimeStamp() {
		return timeStamp;
	}
	public void setTimeStamp(String timeStamp) {
		this.timeStamp = timeStamp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getRefundReason() {
		return refundReason;
	}
	public void setRefundReason(String refundReason) {
		this.refundReason = refundReason;
	}
	@Override
	public String toString() {
		return "ApplyRefundBean [sequenceId=" + sequenceId + ", partnerCode="
				+ partnerCode + ", signed=" + signed + ", timeStamp="
				+ timeStamp + ", orderId=" + orderId + ", orderNo=" + orderNo
				+ ", refundReason=" + refundReason + "]";
	}
}
