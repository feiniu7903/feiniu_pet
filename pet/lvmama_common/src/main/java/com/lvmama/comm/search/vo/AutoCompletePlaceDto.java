package com.lvmama.comm.search.vo;

import java.io.Serializable;

/**
 * 自动补全传输对象,中间转换对象
 * 
 * @author zhangzhenhua
 * 
 */

public class AutoCompletePlaceDto implements Serializable {
	private static final long serialVersionUID = 6245354189529365960L;
	/** place_search_info **/
	private String stage;
	private String shortId;
	private String pinyin;
	private String name;
	private String hfkw;// 线上运营自定义关键字(高频关键字 high frequence keyword)
	private String destTagsName;
	private String destSubjects;
	private String destPersentStr;
	/** product_search_info **/
	private String topic;
	private String tagsName;
	private String productAlltoPlaceContent;
	private String placeKeywordBind; // place的高频关键字
	private String departArea; // 出境大区域关键字,大洋洲,北美..etc.
	// 地标的地址
	private String landmarkAddress;
	// 地标的百度地图地址
	private String coordinateAddress;
	/** 经纬度 */
	protected Double longitude;
	/** 经纬度 */
	protected Double latitude;
	/** 排序SEQ **/
	private Long seq = new Long("0");

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

	public String getShortId() {
		if (null != shortId) {
			return shortId;
		} else {
			return "";
		}
	}

	public void setShortId(String shortId) {
		this.shortId = shortId;
	}

	public String getPinyin() {
		if (null != pinyin) {
			return pinyin;
		} else {
			return "";
		}
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getName() {
		if (null != name) {
			return name;
		} else {
			return "";
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getHfkw() {
		if (null != hfkw) {
			return hfkw;
		} else {
			return "";
		}
	}

	public void setHfkw(String hfkw) {
		this.hfkw = hfkw;
	}

	public String getDestTagsName() {
		if (null != destTagsName) {
			return destTagsName;
		} else {
			return "";
		}
	}

	public void setDestTagsName(String destTagsName) {
		this.destTagsName = destTagsName;
	}

	public String getDestSubjects() {
		if (null != destSubjects) {
			return destSubjects;
		} else {
			return "0";
		}
	}

	public void setDestSubjects(String destSubjects) {
		this.destSubjects = destSubjects;
	}

	public String getDestPersentStr() {
		if (null != destPersentStr) {
			return destPersentStr;
		} else {
			return "";
		}
	}

	public void setDestPersentStr(String destPersentStr) {
		this.destPersentStr = destPersentStr;
	}

	public String getTopic() {
		if (null != topic) {
			return topic;
		} else {
			return "";
		}
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public String getTagsName() {
		if (null != tagsName) {
			return tagsName;
		} else {
			return "";
		}
	}

	public void setTagsName(String tagsName) {
		this.tagsName = tagsName;
	}

	public String getProductAlltoPlaceContent() {
		if (null != productAlltoPlaceContent) {
			return productAlltoPlaceContent;
		} else {
			return "";
		}
	}

	public void setProductAlltoPlaceContent(String productAlltoPlaceContent) {
		this.productAlltoPlaceContent = productAlltoPlaceContent;
	}

	public String getPlaceKeywordBind() {
		if (null != placeKeywordBind) {
			return placeKeywordBind;
		} else {
			return "";
		}
	}

	public void setPlaceKeywordBind(String placeKeywordBind) {
		this.placeKeywordBind = placeKeywordBind;
	}

	public String getDepartArea() {
		if (null != departArea) {
			return departArea;
		} else {
			return "";
		}
	}

	public void setDepartArea(String departArea) {
		this.departArea = departArea;
	}

	public Long getSeq() {
		if (null != seq) {
			return seq;
		} else {
			return 0L;
		}
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public Double getLongitude() {
		if (null != longitude) {
			return longitude;
		} else {
			return 0D;
		}
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		if (null != latitude) {
			return latitude;
		} else {
			return 0D;
		}
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getLandmarkAddress() {
		if (null != landmarkAddress) {
			return landmarkAddress;
		} else {
			return "";
		}
	}

	public void setLandmarkAddress(String landmarkAddress) {
		this.landmarkAddress = landmarkAddress;
	}

	public String getCoordinateAddress() {
		if (null != coordinateAddress) {
			return coordinateAddress;
		} else {
			return "";
		}
	}

	public void setCoordinateAddress(String coordinateAddress) {
		this.coordinateAddress = coordinateAddress;
	}

}
