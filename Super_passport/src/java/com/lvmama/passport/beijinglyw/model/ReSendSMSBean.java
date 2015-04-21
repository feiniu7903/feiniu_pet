package com.lvmama.passport.beijinglyw.model;

public final class ReSendSMSBean extends HeadBean {
	private String orderId;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Override
	public String toString() {
		return "ReSendSMSBean [version=" + version + ", messageId=" + messageId
				+ ", partnerCode=" + partnerCode + ", proxyId=" + proxyId
				+ ", timeStamp=" + timeStamp + ", signed=" + signed
				+ ", orderId=" + orderId + "]";
	}
}
