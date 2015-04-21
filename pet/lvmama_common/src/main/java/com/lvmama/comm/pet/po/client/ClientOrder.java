package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

public class ClientOrder implements Serializable{

	private static final long serialVersionUID = -1610481509175761897L;
	
	private Long orderId;
	
	private String productName;
	
	private String paymentTarget;
	
	private String createTime;
	
	private String orderViewStatus;
	
	private ClientPayment clientPayment;
	
	private String titleName;
	
	private ClientTraveller clientTraveller;
	
	private ClientOrderCost clientOrderCost;
	
	private ClientContainProd clientContainProd;
	
	private String button;
	
	private String payUrl;
	
	private String visitTime;
	
	private Long mainProductId;
	
	private List<ClientProduct> productsList;
	
	private String userId;
	
	private String actualPay;
	
	private String message;
	private String coupon;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getActualPay() {
		return actualPay;
	}

	public void setActualPay(String actualPay) {
		this.actualPay = actualPay;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getPayUrl() {
		return payUrl;
	}

	public void setPayUrl(String payUrl) {
		this.payUrl = payUrl;
	}

	public ClientPayment getClientPayment() {
		return clientPayment;
	}

	public void setClientPayment(ClientPayment clientPayment) {
		this.clientPayment = clientPayment;
	}

	public String getTitleName() {
		return titleName;
	}

	public void setTitleName(String titleName) {
		this.titleName = titleName;
	}

	public ClientTraveller getClientTraveller() {
		return clientTraveller;
	}

	public void setClientTraveller(ClientTraveller clientTraveller) {
		this.clientTraveller = clientTraveller;
	}

	public ClientOrderCost getClientOrderCost() {
		return clientOrderCost;
	}

	public void setClientOrderCost(ClientOrderCost clientOrderCost) {
		this.clientOrderCost = clientOrderCost;
	}

	public String getButton() {
		return button;
	}

	public void setButton(String button) {
		this.button = button;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPaymentTarget() {
		return paymentTarget;
	}

	public void setPaymentTarget(String paymentTarget) {
		this.paymentTarget = paymentTarget;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}

	public ClientContainProd getClientContainProd() {
		return clientContainProd;
	}

	public void setClientContainProd(ClientContainProd clientContainProd) {
		this.clientContainProd = clientContainProd;
	}

	public List<ClientProduct> getProductsList() {
		return productsList;
	}

	public void setProductsList(List<ClientProduct> productsList) {
		this.productsList = productsList;
	}

	public Long getMainProductId() {
		return mainProductId;
	}

	public void setMainProductId(Long mainProductId) {
		this.mainProductId = mainProductId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCoupon() {
		return coupon;
	}

	public void setCoupon(String coupon) {
		this.coupon = coupon;
	}
}
