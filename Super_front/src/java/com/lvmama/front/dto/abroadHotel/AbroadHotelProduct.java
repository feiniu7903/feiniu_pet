package com.lvmama.front.dto.abroadHotel;

import java.io.Serializable;

public class AbroadHotelProduct implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 酒店ID编号
	 */
	private String id;
	
	/**
	 * 酒店名称
	 */
	private String hotelName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHotelName() {
		return hotelName;
	}

	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
}
