package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class ProductHistoryCookie implements Serializable{
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 780621372008836793L;
	 
	private String productId;
	private String name;
	private String productsPrice;
	private String imageUrl;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getProductsPrice() {
		return productsPrice;
	}
	public void setProductsPrice(String productsPrice) {
		this.productsPrice = productsPrice;
	}
	
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
}
