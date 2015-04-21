package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceAirport implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5059265995949211844L;
	
    private Long placeAirportId;
    private Long placeId;

    private String enName;

    private String airportCode;

    private String airportName;
    //城市名称
    private String cityName;
    //
	private String cityCode;
 
    public Long getPlaceAirportId() {
		return placeAirportId;
	}

	public void setPlaceAirportId(Long placeAirportId) {
		this.placeAirportId = placeAirportId;
	}

	public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

    public String getEnName() {
        return enName;
    }

    public void setEnName(String enName) {
        this.enName = enName == null ? null : enName.trim();
    }

    public String getAirportCode() {
        return airportCode;
    }

    public void setAirportCode(String airportCode) {
        this.airportCode = airportCode == null ? null : airportCode.trim();
    }

    public String getAirportName() {
        return airportName;
    }

    public void setAirportName(String airportName) {
        this.airportName = airportName == null ? null : airportName.trim();
    }


	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}