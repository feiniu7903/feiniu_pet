package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceAirline implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4761242668908316674L;

	private Long placeAirlineId;

    private String airlineName;

    private String airlineIcon;

    private String airlineCode;

    public Long getPlaceAirlineId() {
        return placeAirlineId;
    }

    public void setPlaceAirlineId(Long placeAirlineId) {
        this.placeAirlineId = placeAirlineId;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName == null ? null : airlineName.trim();
    }

    public String getAirlineIcon() {
        return airlineIcon;
    }

    public void setAirlineIcon(String airlineIcon) {
        this.airlineIcon = airlineIcon == null ? null : airlineIcon.trim();
    }

    public String getAirlineCode() {
        return airlineCode;
    }

    public void setAirlineCode(String airlineCode) {
        this.airlineCode = airlineCode == null ? null : airlineCode.trim();
    }
}