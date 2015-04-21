package com.lvmama.passport.yiyou.model;

public final class ResendSmsBean extends HeadBean {
	private String timeStamp;
	private String orderId;
	private String orderNo;
	
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
	@Override
	public String toString() {
		return "ResendSmsBean [sequenceId=" + sequenceId + ", partnerCode="
				+ partnerCode + ", signed=" + signed + ", timeStamp="
				+ timeStamp + ", orderId=" + orderId + ", orderNo=" + orderNo
				+ "]";
	}
}
