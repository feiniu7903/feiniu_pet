package com.lvmama.passport.overseaschinatown.model;

public class CheckTicketBuyBean {
	private String goodsId;//商品Id
	private String quantity;//购买数量
	private String retailPrice;//商品售价
	private String appointTripDate;//指定出行日期
	private String mobile;//客户电话
	private String certificateNum;//证件号
	public String getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(String goodsId) {
		this.goodsId = goodsId;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	public String getAppointTripDate() {
		return appointTripDate;
	}
	public void setAppointTripDate(String appointTripDate) {
		this.appointTripDate = appointTripDate;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getCertificateNum() {
		return certificateNum;
	}
	public void setCertificateNum(String certificateNum) {
		this.certificateNum = certificateNum;
	}
	
}
