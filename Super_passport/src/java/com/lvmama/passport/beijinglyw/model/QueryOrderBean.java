package com.lvmama.passport.beijinglyw.model;

public final class QueryOrderBean extends HeadBean {
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "QueryOrderBean [version=" + version + ", messageId="
				+ messageId + ", partnerCode=" + partnerCode + ", proxyId="
				+ proxyId + ", timeStamp=" + timeStamp + ", signed=" + signed
				+ ", orderId=" + orderId + "]";
	}
}
