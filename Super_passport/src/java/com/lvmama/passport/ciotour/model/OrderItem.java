package com.lvmama.passport.ciotour.model;

public class OrderItem {
	private String productId;// 商品在识途网上的ID
	private String unitDate;// 预订商品的消费日期，如果是票务，则是取票日期；如果是酒店，则是入住日期，格式：yyyy-MM-dd
	private String price;// 商品价格,注意：这个价格必须是识途网给第三方网站的商品价格
	private String subscriptionPrice;// 订金，只有订金模式下才有值
	private int quantity;// 商品数量
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUnitDate() {
		return unitDate;
	}
	public void setUnitDate(String unitDate) {
		this.unitDate = unitDate;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSubscriptionPrice() {
		return subscriptionPrice;
	}
	public void setSubscriptionPrice(String subscriptionPrice) {
		this.subscriptionPrice = subscriptionPrice;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
