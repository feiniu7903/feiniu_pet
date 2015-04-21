package com.lvmama.comm.search.vo;

public class PlaceFromDestBean {
	private String id;
	private String placeId;
	private String placeName;
	private String placePinyin;
	private String fromDestId;
	private String fromDestName;
	private String stage;
	private String isValid;
	private String seq;
	private String destPeripheryNum;
	private String destAbroadNum;
	private String destInternalNum;

	public String getSeq() {
		if (null != seq) {
			return seq;
		} else {
			return "";
		}
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getId() {
		if (null != id) {
			return id;
		} else {
			return "";
		}
	}

	public void setId(String id) {
		this.id = id;
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

	public String getFromDestId() {
		if (null != fromDestId) {
			return fromDestId;
		} else {
			return "";
		}
	}

	public void setFromDestId(String fromDestId) {
		this.fromDestId = fromDestId;
	}

	public String getFromDestName() {
		if (null != fromDestName) {
			return fromDestName;
		} else {
			return "";
		}
	}

	public void setFromDestName(String fromDestName) {
		this.fromDestName = fromDestName;
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

	public String getIsValid() {
		if (null != isValid) {
			return isValid;
		} else {
			return "";
		}
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getDestPeripheryNum() {
		if (null != destPeripheryNum) {
			return destPeripheryNum;
		} else {
			return "";
		}
	}

	public void setDestPeripheryNum(String destPeripheryNum) {
		this.destPeripheryNum = destPeripheryNum;
	}

	public String getDestAbroadNum() {
		if (null != destAbroadNum) {
			return destAbroadNum;
		} else {
			return "";
		}
	}

	public void setDestAbroadNum(String destAbroadNum) {
		this.destAbroadNum = destAbroadNum;
	}

	public String getDestInternalNum() {
		if (null != destInternalNum) {
			return destInternalNum;
		} else {
			return "";
		}
	}

	public void setDestInternalNum(String destInternalNum) {
		this.destInternalNum = destInternalNum;
	}

}
