package com.lvmama.clutter.model;

import java.io.Serializable;

public class MobileOrderItem implements Serializable{
	String productName;
	String shortName;
	Long  quantity;
	Long amount; // 总价
	Float famount; // 总价float类型
	Float price; // 单价
	boolean additional;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public Long getQuantity() {
		return quantity;
	}
	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}
	
	public boolean isAdditional() {
		return additional;
	}
	public void setAdditional(boolean additional) {
		this.additional = additional;
	}
	public String getShortName() {
		return shortName;
	}
	public void setShortName(String shortName) {
		this.shortName = shortName;
	}
	public Long getAmount() {
		return amount;
	}
	public void setAmount(Long amount) {
		this.amount = amount;
	}
	
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}
	
	public Float getFamount() {
		return famount;
	}
	public void setFamount(Float famount) {
		this.famount = famount;
	}
}
