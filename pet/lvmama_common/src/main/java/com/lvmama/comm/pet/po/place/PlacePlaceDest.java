package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlacePlaceDest implements Serializable {
	private static final long serialVersionUID = 8858712705336352857L;

	private Long placePlaceDestId;

	private Long placeId;

	private Long parentPlaceId;

	private String parentPlaceName;

	private String isMaster;

	public Long getPlacePlaceDestId() {
		return placePlaceDestId;
	}

	public String getParentPlaceName() {
		return parentPlaceName;
	}

	public void setParentPlaceName(String parentPlaceName) {
		this.parentPlaceName = parentPlaceName;
	}

	public void setPlacePlaceDestId(Long placePlaceDestId) {
		this.placePlaceDestId = placePlaceDestId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getParentPlaceId() {
		return parentPlaceId;
	}

	public void setParentPlaceId(Long parentPlaceId) {
		this.parentPlaceId = parentPlaceId;
	}

	public String getIsMaster() {
		return isMaster;
	}

	public void setIsMaster(String isMaster) {
		this.isMaster = isMaster;
	}
}