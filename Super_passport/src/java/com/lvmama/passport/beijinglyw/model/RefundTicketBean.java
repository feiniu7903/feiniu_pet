package com.lvmama.passport.beijinglyw.model;

public final class RefundTicketBean extends HeadBean {
	private String orderId;
	private String refundCount;
	private String feature;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getRefundCount() {
		return refundCount;
	}
	public void setRefundCount(String refundCount) {
		this.refundCount = refundCount;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	@Override
	public String toString() {
		return "RefundTicketBean [version=" + version + ", messageId="
				+ messageId + ", partnerCode=" + partnerCode + ", proxyId="
				+ proxyId + ", timeStamp=" + timeStamp + ", signed=" + signed
				+ ", orderId=" + orderId + ", refundCount=" + refundCount
				+ ", feature=" + feature + "]";
	}
}
