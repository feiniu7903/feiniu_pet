package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;


public class HotelBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;
	private String Name;
	private String IDAccomCategory;
	private String IDAccomType;
	private String IDCity;
	private String CityName;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getIDAccomCategory() {
		return IDAccomCategory;
	}
	public void setIDAccomCategory(String iDAccomCategory) {
		IDAccomCategory = iDAccomCategory;
	}
	public String getIDAccomType() {
		return IDAccomType;
	}
	public void setIDAccomType(String iDAccomType) {
		IDAccomType = iDAccomType;
	}
	public String getIDCity() {
		return IDCity;
	}
	public void setIDCity(String iDCity) {
		IDCity = iDCity;
	}
	public String getCityName() {
		return CityName;
	}
	public void setCityName(String cityName) {
		CityName = cityName;
	}

	
}
