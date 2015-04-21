package com.lvmama.clutter.model;

import java.io.Serializable;

public class MobileHotelGeo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6429105970468687353L;
	private String LocationName;
	private String locationId;
	private String locationType;
	private String pinyin;
	public String getLocationName() {
		return LocationName;
	}
	public void setLocationName(String locationName) {
		LocationName = locationName;
	}
	public String getLocationId() {
		return locationId;
	}
	public void setLocationId(String locationId) {
		this.locationId = locationId;
	}
	public String getLocationType() {
		return locationType;
	}
	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}
	public String getPinyin() {
		return pinyin;
	}
	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}
	
}
