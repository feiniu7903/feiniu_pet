package com.lvmama.comm.pet.vo;

import java.io.Serializable;

/**
 * 景区坐标VO
 */
public class ViewPlaceCoordinate implements Serializable {	
	private static final long serialVersionUID = 986233220569436986L;	
	
	private String address;
	private String stage;	
	private String infoFlag;	
	private String pinYinUrl;
	private Float  longitude;
	private Float  latitude;
	private String coordinateName;
	private Long   placeId;
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getInfoFlag() {
		return infoFlag;
	}
	public void setInfoFlag(String infoFlag) {
		this.infoFlag = infoFlag;
	}
	public String getPinYinUrl() {
		return pinYinUrl;
	}
	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}
	public Float getLongitude() {
		return longitude;
	}
	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public Float getLatitude() {
		return latitude;
	}
	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public String getCoordinateName() {
		return coordinateName;
	}
	public void setCoordinateName(String coordinateName) {
		this.coordinateName = coordinateName;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	
	
}