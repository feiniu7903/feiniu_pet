package com.lvmama.comm.pet.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * 景区坐标 它是Place和PlaceCoordinate的组合体
 * 
 * @author huangzhi
 * 
 */
public class PlaceCoordinateVo implements Serializable {
	
	private static final long serialVersionUID = -2375481109312683636L;
	
	private Long placeId;
	
	/** 后台录入地址**/
	private String placeAddress;

	private String isValid;

	private String coordinateName;
	
	/**地图反向解析得到的地址**/
	private String coordinateAddress;

	private Double longitude;

	private Double latitude;

	private Date updateTime;

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getPlaceAddress() {
		return placeAddress;
	}

	public void setPlaceAddress(String placeAddress) {
		this.placeAddress = placeAddress;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getCoordinateName() {
		return coordinateName;
	}

	public void setCoordinateName(String coordinateName) {
		this.coordinateName = coordinateName;
	}

	public String getCoordinateAddress() {
		return coordinateAddress;
	}

	public void setCoordinateAddress(String coordinateAddress) {
		this.coordinateAddress = coordinateAddress;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
