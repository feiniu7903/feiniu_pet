package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProductProductPlace implements Serializable {
	private static final long serialVersionUID = 4531846443573026993L;
	private Long id;

	private Long productId;

	private Long placeId;

	private Long parentPlaceId;

	private Long fromPlaceId;
	/**
	 * 是否是原始数据
	 */
	private String isOriginal;
	/**
	 * 是否是目的地地表为true
	 */
	private String isToPlace;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getFromPlaceId() {
		return fromPlaceId;
	}

	public void setFromPlaceId(Long fromPlaceId) {
		this.fromPlaceId = fromPlaceId;
	}

	public Long getParentPlaceId() {
		return parentPlaceId;
	}

	public void setParentPlaceId(Long parentPlaceId) {
		this.parentPlaceId = parentPlaceId;
	}

	public String getIsOriginal() {
		return isOriginal;
	}

	public void setIsOriginal(String isOriginal) {
		this.isOriginal = isOriginal;
	}

	public String getIsToPlace() {
		return isToPlace;
	}

	public void setIsToPlace(String isToPlace) {
		this.isToPlace = isToPlace;
	}

	@Override
	public String toString() {
		return "ProductProductPlace [id=" + id + ", productId=" + productId
				+ ", placeId=" + placeId + ",parentPlaceId=" + parentPlaceId
				+ ", fromPlaceId=" + fromPlaceId + "]";
	}
}
