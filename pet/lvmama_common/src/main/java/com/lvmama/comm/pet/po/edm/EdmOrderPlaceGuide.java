package com.lvmama.comm.pet.po.edm;

import java.io.Serializable;

public class EdmOrderPlaceGuide implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8345066381476119058L;
	/**
	 * 目的地ID
	 */
	private String placeId;
	/**
	 * 目的地名称
	 */
	private String placeName;
	/**
	 * 订单下单人，联系人EMAIL
	 */
	private String email;
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
}
