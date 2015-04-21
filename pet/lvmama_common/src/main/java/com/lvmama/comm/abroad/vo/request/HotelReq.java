package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class HotelReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String countryId;//国家ID
	private String cityId;//城市ID
	private String hotelName;//酒店名
	
	public String getCountryId() {
		return countryId;
	}
	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getHotelName() {
		return hotelName;
	}
	public void setHotelName(String hotelName) {
		this.hotelName = hotelName;
	}
	

}
