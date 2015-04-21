package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;


public class CityBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ID;//城市id
	private String Name;//城市名
	private String IDCountry;//国家id
	private String IDProvince;//省id
	private String IDArea;//区id
	private String cityNameCh;//城市中文名
	private String countryNameCh;//国家中文名
	private String country;//国家
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
	public String getIDCountry() {
		return IDCountry;
	}
	public void setIDCountry(String iDCountry) {
		IDCountry = iDCountry;
	}
	public String getIDProvince() {
		return IDProvince;
	}
	public void setIDProvince(String iDProvince) {
		IDProvince = iDProvince;
	}
	public String getIDArea() {
		return IDArea;
	}
	public void setIDArea(String iDArea) {
		IDArea = iDArea;
	}
	public String getCityNameCh() {
		return cityNameCh;
	}
	public void setCityNameCh(String cityNameCh) {
		this.cityNameCh = cityNameCh;
	}
	public String getCountryNameCh() {
		return countryNameCh;
	}
	public void setCountryNameCh(String countryNameCh) {
		this.countryNameCh = countryNameCh;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	

}
