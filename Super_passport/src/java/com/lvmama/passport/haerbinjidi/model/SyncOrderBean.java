package com.lvmama.passport.haerbinjidi.model;

public class SyncOrderBean extends HeaderBean{
	private String operateType;
	private String serialId;
	private String comfirmNumber;
	private String sceneryId;
	private String sceneryName;
	private String ticketTypeId;
	private String ticketTypeName;
	private String retailPrice;
	private String webPrice;
	private String costPrice;
	private String ticketCount;
	private String totalAmount;
	private String realPayAmount;
	private String payType;
	private String playDate;
	private String expiryDate;
	private String orderStatus;
	private String travelerName;
	private String travelerMobile;
	private String orderDate;
	private String identityCard;
	public String getOperateType() {
		return operateType;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getComfirmNumber() {
		return comfirmNumber;
	}
	public void setComfirmNumber(String comfirmNumber) {
		this.comfirmNumber = comfirmNumber;
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
	public String getSceneryName() {
		return sceneryName;
	}
	public void setSceneryName(String sceneryName) {
		this.sceneryName = sceneryName;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getTicketTypeName() {
		return ticketTypeName;
	}
	public void setTicketTypeName(String ticketTypeName) {
		this.ticketTypeName = ticketTypeName;
	}
	public String getWebPrice() {
		return webPrice;
	}
	public void setWebPrice(String webPrice) {
		this.webPrice = webPrice;
	}
	public String getCostPrice() {
		return costPrice;
	}
	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}
	public String getTicketCount() {
		return ticketCount;
	}
	public void setTicketCount(String ticketCount) {
		this.ticketCount = ticketCount;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRealPayAmount() {
		return realPayAmount;
	}
	public void setRealPayAmount(String realPayAmount) {
		this.realPayAmount = realPayAmount;
	}
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getPlayDate() {
		return playDate;
	}
	public void setPlayDate(String playDate) {
		this.playDate = playDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getTravelerName() {
		return travelerName;
	}
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	public String getTravelerMobile() {
		return travelerMobile;
	}
	public void setTravelerMobile(String travelerMobile) {
		this.travelerMobile = travelerMobile;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getRetailPrice() {
		return retailPrice;
	}
	public void setRetailPrice(String retailPrice) {
		this.retailPrice = retailPrice;
	}
	
	@Override
	public String toString() {
		return "operateType"+operateType+"serialId"+serialId+"comfirmNumber"+comfirmNumber+"sceneryId"+sceneryId+"sceneryName"+sceneryName+"ticketTypeId"+ticketTypeId+"ticketTypeName"+ticketTypeName+"retailPrice"+retailPrice+"webPrice"+webPrice+"costPrice"+costPrice+"ticketCount"+ticketCount+"totalAmount"+totalAmount+"realPayAmount"+realPayAmount+"payType"+ payType+"playDate"+ playDate+"expiryDate"+ expiryDate+"orderStatus"+orderStatus+"travelerName"+travelerName+"travelerMobile"+travelerMobile+"orderDate"+  orderDate+"identityCard"+identityCard;
	}
}
