package com.lvmama.passport.overseaschinatown.model;

public class ConfirmOrderBean {
	private String orderId;//OTA订单编号
	private String dealTime;//下单时间
	private String name;//客户姓名
	private String mobile;//客户电话
	private String quantity;//商品数量
	private String goodsId;//商品编号
	private String salePrice;//销售单价
	private String isSendSms;//是否发送短信
	private String certificateType;//证件类型
	private String certificateNum;//证件号
	private String appointTripDate;//指定出行时间
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getDealTime() {
		return dealTime;
	}
	public void setDealTime(String dealTime) {
		this.dealTime = dealTime;
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
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}
	public String getIsSendSms() {
		return isSendSms;
	}
	public void setIsSendSms(String isSendSms) {
		this.isSendSms = isSendSms;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	public String getAppointTripDate() {
		return appointTripDate;
	}
	public void setAppointTripDate(String appointTripDate) {
		this.appointTripDate = appointTripDate;
	}

}
