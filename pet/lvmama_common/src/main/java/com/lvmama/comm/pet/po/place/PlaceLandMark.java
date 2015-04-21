package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;

/**
 * 地标对象
 * @author yuzhizeng
 *
 */
public class PlaceLandMark implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = -6692970370350188204L;

	private Long placeLandMarkId;
	
	//上级目的地
	private Long placeId;
	private String parentPlaceName;
	
	private String landMarkName;
	 
	private String pinYin;
	
	private String hfkw;
	
	private String coordinateAddress;//本地经纬度地址
	
	private Double  longitude;//经度
	private Double  latitude;//纬度
		
	private String isValid;
	private Date updateTime;
	
	private String landMarkAddress; //地标地址
	
	private Double distance;//距离
	
	public Long getPlaceLandMarkId() {
		return placeLandMarkId;
	}

	public void setPlaceLandMarkId(Long placeLandMarkId) {
		this.placeLandMarkId = placeLandMarkId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getLandMarkName() {
		return landMarkName;
	}

	public void setLandMarkName(String landMarkName) {
		this.landMarkName = landMarkName;
	}

	public String getPinYin() {
		return pinYin;
	}

	public void setPinYin(String pinYin) {
		this.pinYin = pinYin;
	}

	public String getHfkw() {
		return hfkw;
	}

	public void setHfkw(String hfkw) {
		this.hfkw = hfkw;
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

	public String getCoordinateAddress() {
		return coordinateAddress;
	}

	public void setCoordinateAddress(String coordinateAddress) {
		this.coordinateAddress = coordinateAddress;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getCnIsValid() {
		if("Y".equalsIgnoreCase(isValid)){
			return "有效";
		}else{
			return "无效";
		}
	}
	
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getLandMarkAddress() {
		return landMarkAddress;
	}

	public void setLandMarkAddress(String landMarkAddress) {
		this.landMarkAddress = landMarkAddress;
	}

	public String getParentPlaceName() {
		return parentPlaceName;
	}

	public void setParentPlaceName(String parentPlaceName) {
		this.parentPlaceName = parentPlaceName;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}
	
}
