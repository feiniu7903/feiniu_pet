package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class HotelDetailsDistance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8547764455908722841L;
	/***/
	private String DistanceType;
	private String Distance;
	private String DistanceToType;
	private String DistanceToDescription;
	private String Description;

	public String getDistanceType() {
		return DistanceType;
	}

	public void setDistanceType(String distanceType) {
		DistanceType = distanceType;
	}

	public String getDistance() {
		return Distance;
	}

	public void setDistance(String distance) {
		Distance = distance;
	}

	public String getDistanceToType() {
		return DistanceToType;
	}

	public void setDistanceToType(String distanceToType) {
		DistanceToType = distanceToType;
	}

	public String getDistanceToDescription() {
		return DistanceToDescription;
	}

	public void setDistanceToDescription(String distanceToDescription) {
		DistanceToDescription = distanceToDescription;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}
}
