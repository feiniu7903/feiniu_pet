package com.lvmama.passport.beijinglyw.model;

public final class SubmitOrderBean extends HeadBean {
	private String orderId;
	private String agentOrderId;
	private String count;
	private String settlementPrice;
	private String isSendSms;
	private String productSn;
	private String payType;
	private String validTimeBegin;
	private String validTimeEnd;
	private String inDate;
	private String name;
	private String mobile;
	private String sex;
	private String idCard;
	private String feature;

	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getAgentOrderId() {
		return agentOrderId;
	}
	public void setAgentOrderId(String agentOrderId) {
		this.agentOrderId = agentOrderId;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
	public String getSettlementPrice() {
		return settlementPrice;
	}
	public void setSettlementPrice(String settlementPrice) {
		this.settlementPrice = settlementPrice;
	}
	public String getIsSendSms() {
		return isSendSms;
	}
	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}

	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getValidTimeBegin() {
		return validTimeBegin;
	}
	public void setValidTimeBegin(String validTimeBegin) {
		this.validTimeBegin = validTimeBegin;
	}
	public String getValidTimeEnd() {
		return validTimeEnd;
	}
	public void setValidTimeEnd(String validTimeEnd) {
		this.validTimeEnd = validTimeEnd;
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = inDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getFeature() {
		return feature;
	}
	public void setFeature(String feature) {
		this.feature = feature;
	}
	@Override
	public String toString() {
		return "SubmitOrderBean [version=" + version + ", messageId="
				+ messageId + ", partnerCode=" + partnerCode + ", proxyId="
				+ proxyId + ", timeStamp=" + timeStamp + ", signed=" + signed
				+ ", orderId=" + orderId + ", agentOrderId=" + agentOrderId
				+ ", count=" + count + ", settlementPrice=" + settlementPrice
				+ ", isSendSms=" + isSendSms + ", productId=" + productSn
				+ ", payType=" + payType + ", validTimeBegin=" + validTimeBegin
				+ ", validTimeEnd=" + validTimeEnd + ", inDate=" + inDate
				+ ", name=" + name + ", mobile=" + mobile + ", sex=" + sex
				+ ", idCard=" + idCard + ", feature=" + feature + "]";
	}
	public String getProductSn() {
		return productSn;
	}
	public void setProductSn(String productSn) {
		this.productSn = productSn;
	}
}
