package com.lvmama.comm.pet.po.place;

import java.io.Serializable;
import java.util.Date;

/**
 * 标的 ,景点,酒店百度地图坐标
 * 
 * @author huangzhi
 * 
 */
public class PlaceCoordinateBaidu implements Serializable {

	private static final long serialVersionUID = -4340718837482523436L;

	private Long placeCoordinateBaiduId;

	private Long placeId;

	private String stage;

	private String isValid;

	private String coordinateName;

	private String coordinateAddress;

	private Double longitude;

	private Double latitude;

	private Date updateTime;
	
	private String name;
	
	private Long parentPlaceId;
	
	private String pinYinUrl;
	
	private String smallImage;
	
	private Long sellPrice;
	
	private String remarkes;

	public Long getPlaceCoordinateBaiduId() {
		return placeCoordinateBaiduId;
	}

	public void setPlaceCoordinateBaiduId(Long placeCoordinateBaiduId) {
		this.placeCoordinateBaiduId = placeCoordinateBaiduId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
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
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentPlaceId() {
		return parentPlaceId;
	}

	public void setParentPlaceId(Long parentPlaceId) {
		this.parentPlaceId = parentPlaceId;
	}

	public String getPinYinUrl() {
		return pinYinUrl;
	}

	public void setPinYinUrl(String pinYinUrl) {
		this.pinYinUrl = pinYinUrl;
	}

	public String getSmallImage() {
		return smallImage;
	}

	public void setSmallImage(String smallImage) {
		this.smallImage = smallImage;
	}

	public Long getSellPrice() {
		return sellPrice;
	}
	
	public Integer getSellPriceInteger() {
		Integer price = 0;
		if (this.sellPrice != null) {
			price = Integer.valueOf(sellPrice.toString());
		}
		return price / 100;
	}

	public void setSellPrice(Long sellPrice) {
		this.sellPrice = sellPrice;
	}

	public String getRemarkes() {
		return remarkes;
	}

	public void setRemarkes(String remarkes) {
		this.remarkes = remarkes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}
