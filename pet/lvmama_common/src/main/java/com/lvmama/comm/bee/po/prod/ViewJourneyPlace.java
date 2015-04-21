package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ViewJourneyPlace implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long journeyPlaceId;
	
	private String placeName;
	
    private Long placeId;

    private Long journeyId;

	public Long getJourneyPlaceId() {
		return journeyPlaceId;
	}

	public void setJourneyPlaceId(Long journeyPlaceId) {
		this.journeyPlaceId = journeyPlaceId;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}

	public Long getJourneyId() {
		return journeyId;
	}

	public void setJourneyId(Long journeyId) {
		this.journeyId = journeyId;
	}
    public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
}