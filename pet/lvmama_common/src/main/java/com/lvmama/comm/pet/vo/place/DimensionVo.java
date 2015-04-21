package com.lvmama.comm.pet.vo.place;

import java.io.Serializable;

public class DimensionVo implements Serializable{
	
	private Double latitude;
	private Double longitude;
	private Double minLatitude;
	private Double maxLatitude;
	private Double minLongitude;
	private Double maxinLongitude;
	private Long stage;
	private String placeFirstSubject;
	private Long limit;
	/**
	 * @return the longitude
	 */
	public Double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the minLatitude
	 */
	public Double getMinLatitude() {
		return minLatitude;
	}
	/**
	 * @param minLatitude the minLatitude to set
	 */
	public void setMinLatitude(Double minLatitude) {
		this.minLatitude = minLatitude;
	}
	/**
	 * @return the maxLatitude
	 */
	public Double getMaxLatitude() {
		return maxLatitude;
	}
	/**
	 * @param maxLatitude the maxLatitude to set
	 */
	public void setMaxLatitude(Double maxLatitude) {
		this.maxLatitude = maxLatitude;
	}
	/**
	 * @return the minLongitude
	 */
	public Double getMinLongitude() {
		return minLongitude;
	}
	/**
	 * @param minLongitude the minLongitude to set
	 */
	public void setMinLongitude(Double minLongitude) {
		this.minLongitude = minLongitude;
	}
	/**
	 * @return the maxinLongitude
	 */
	public Double getMaxinLongitude() {
		return maxinLongitude;
	}
	/**
	 * @param maxinLongitude the maxinLongitude to set
	 */
	public void setMaxinLongitude(Double maxinLongitude) {
		this.maxinLongitude = maxinLongitude;
	}
	/**
	 * @return the stage
	 */
	public Long getStage() {
		return stage;
	}
	/**
	 * @param stage the stage to set
	 */
	public void setStage(Long stage) {
		this.stage = stage;
	}
	/**
	 * @return the placeFirstSubject
	 */
	public String getPlaceFirstSubject() {
		return placeFirstSubject;
	}
	/**
	 * @param placeFirstSubject the placeFirstSubject to set
	 */
	public void setPlaceFirstSubject(String placeFirstSubject) {
		this.placeFirstSubject = placeFirstSubject;
	}
	/**
	 * @return the limit
	 */
	public Long getLimit() {
		return limit;
	}
	/**
	 * @param limit the limit to set
	 */
	public void setLimit(Long limit) {
		this.limit = limit;
	}
	/**
	 * @return the latitude
	 */
	public Double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

}
