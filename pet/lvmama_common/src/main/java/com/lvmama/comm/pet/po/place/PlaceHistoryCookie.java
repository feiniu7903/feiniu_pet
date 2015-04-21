package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceHistoryCookie implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String placeId;
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
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	
}
