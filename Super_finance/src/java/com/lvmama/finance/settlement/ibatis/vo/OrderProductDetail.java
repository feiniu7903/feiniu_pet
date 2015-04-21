package com.lvmama.finance.settlement.ibatis.vo;

import java.util.Date;

public class OrderProductDetail {

	private Long orderId;
	private Long productId;
	private String productName;
	private Long productQuantity;
	private Long quantity;
	private String pickTicketPerson;
	private Date visitDate;
	private Double itemPrice;
	private Double realItemPrice;
	private Double realItemPriceSum;
	private Double productPrice;
	private Long supplierId;
	private String supplierName;
	private String targetName;
	private String bankAccountName;
	private String bankName;
	private String bankAccount;
	private Double actualPay;
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getProductQuantity() {
		return productQuantity;
	}
	public void setProductQuantity(Long productQuantity) {
		this.productQuantity = productQuantity;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	public String getPickTicketPerson() {
		return pickTicketPerson;
	}
	public void setPickTicketPerson(String pickTicketPerson) {
		this.pickTicketPerson = pickTicketPerson;
	}
	public Date getVisitDate() {
		return visitDate;
	}
	public void setVisitDate(Date visitDate) {
		this.visitDate = visitDate;
	}
	public Double getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(Double itemPrice) {
		this.itemPrice = itemPrice;
	}
	public Double getRealItemPrice() {
		return realItemPrice;
	}
	public void setRealItemPrice(Double realItemPrice) {
		this.realItemPrice = realItemPrice;
	}
	public Double getRealItemPriceSum() {
		return realItemPriceSum;
	}
	public void setRealItemPriceSum(Double realItemPriceSum) {
		this.realItemPriceSum = realItemPriceSum;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getBankAccountName() {
		return bankAccountName;
	}
	public void setBankAccountName(String bankAccountName) {
		this.bankAccountName = bankAccountName;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public Double getActualPay() {
		return actualPay;
	}
	public void setActualPay(Double actualPay) {
		this.actualPay = actualPay;
	}
	
}
