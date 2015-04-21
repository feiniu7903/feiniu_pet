package com.lvmama.comm.pet.vo;

public class CoordinateUserPlaceModifyParam {

	private String placeIsValid;
	private String placeName;
	private String placeShortName;
	private Integer placeStage;
	private String upmState;
	private Integer skipResults;
	private Integer maxResults;

	public String getPlaceIsValid() {
		return placeIsValid;
	}

	public void setPlaceIsValid(String placeIsValid) {
		this.placeIsValid = placeIsValid;
	}

	public Integer getPlaceStage() {
		return placeStage;
	}

	public void setPlaceStage(Integer placeStage) {
		this.placeStage = placeStage;
	}

	public Integer getSkipResults() {
		return skipResults;
	}

	public void setSkipResults(Integer skipResults) {
		this.skipResults = skipResults;
	}

	public Integer getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Integer maxResults) {
		this.maxResults = maxResults;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getUpmState() {
		return upmState;
	}

	public void setUpmState(String upmState) {
		this.upmState = upmState;
	}

	public String getPlaceShortName() {
		return placeShortName;
	}

	public void setPlaceShortName(String placeShortName) {
		this.placeShortName = placeShortName;
	}

}
