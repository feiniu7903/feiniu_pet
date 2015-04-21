package com.lvmama.comm.search.vo;

import java.io.Serializable;

/**
 * 酒店对象(酒店页显示对象)
 * 
 */
public class VerPlaceBean implements Serializable {

	/**
	 * 序列
	 */
	private static final long serialVersionUID = 5225422785714122094L;

	private String placeId;

	private String placeType;

	private String placeName;

	private String placePinyin;

	private String longitude;

	private String latitude;

	private String stage;

	private String parentId;

	private Float score;

	private String aroundNum;
	
	private String placeUrlPinyin;
	
	//标识拼音
	private String placesignpinyin;
	
	//标识拼音简称
	private String placesignshortpinyin;

	public String getPlaceUrlPinyin() {
		return placeUrlPinyin;
	}

	public void setPlaceUrlPinyin(String placeUrlPinyin) {
		this.placeUrlPinyin = placeUrlPinyin;
	}

	public String getPlaceId() {
		if (null != placeId) {
			return placeId;
		} else {
			return "";
		}
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public String getPlaceType() {
		if (null != placeType) {
			return placeType;
		} else {
			return "";
		}
	}

	public void setPlaceType(String placeType) {
		this.placeType = placeType;
	}

	public String getPlaceName() {
		if (null != placeName) {
			return placeName;
		} else {
			return "";
		}
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlacePinyin() {
		if (null != placePinyin) {
			return placePinyin;
		} else {
			return "";
		}
	}

	public void setPlacePinyin(String placePinyin) {
		this.placePinyin = placePinyin;
	}

	public String getLongitude() {
		if (null != longitude) {
			return longitude;
		} else {
			return "";
		}
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		if (null != latitude) {
			return latitude;
		} else {
			return "";
		}
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStage() {
		if (null != stage) {
			return stage;
		} else {
			return "";
		}
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getParentId() {
		if (null != parentId) {
			return parentId;
		} else {
			return "";
		}
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Float getScore() {
		if (null != score) {
			return score;
		} else {
			return 0F;
		}
	}

	public void setScore(Float score) {
		this.score = score;
	}

	public String getAroundNum() {
		if (null != aroundNum) {
			return aroundNum;
		} else {
			return "";
		}
	}

	public void setAroundNum(String aroundNum) {
		this.aroundNum = aroundNum;
	}
    
	public String getPlacesignpinyin() {
		return placesignpinyin;
	}

	public void setPlacesignpinyin(String placesignpinyin) {
		this.placesignpinyin = placesignpinyin;
	}

	public String getPlacesignshortpinyin() {
		return placesignshortpinyin;
	}

	public void setPlacesignshortpinyin(String placesignshortpinyin) {
		this.placesignshortpinyin = placesignshortpinyin;
	}
	
}
