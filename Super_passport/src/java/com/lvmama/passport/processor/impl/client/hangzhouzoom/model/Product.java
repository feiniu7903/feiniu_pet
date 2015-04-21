package com.lvmama.passport.processor.impl.client.hangzhouzoom.model;

public class Product {

	private String productId; // 对应合作方的票种类型Id
	private String customerUnitPrice; // 票种价格
	private String quantity;// 订购数量
	private String customerProductName;//产品名称

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getCustomerUnitPrice() {
		return customerUnitPrice;
	}

	public void setCustomerUnitPrice(String customerUnitPrice) {
		this.customerUnitPrice = customerUnitPrice;
	}

	public String getCustomerProductName() {
		return customerProductName;
	}

	public void setCustomerProductName(String customerProductName) {
		this.customerProductName = customerProductName;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}
