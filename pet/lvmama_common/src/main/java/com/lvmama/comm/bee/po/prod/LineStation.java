package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class LineStation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 7369826830261840847L;
	private Long stationId;
    // 车站名
    private String stationName;
    // 车站名拼音
    private String stationPinyin;
    // 车站简拼
    private String stationPy;

    private Long placeId;
    // 所属城市名
    private String cityName;
    // 所属城市拼音
    private String cityPinyin;
    
    private String oldStationPinyin;

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName == null ? null : stationName.trim();
    }

    public String getStationPinyin() {
        return stationPinyin;
    }

    public void setStationPinyin(String stationPinyin) {
        this.stationPinyin = stationPinyin == null ? null : stationPinyin.trim();
    }

    public String getStationPy() {
        return stationPy;
    }

    public void setStationPy(String stationPy) {
        this.stationPy = stationPy == null ? null : stationPy.trim();
    }

    public Long getPlaceId() {
        return placeId;
    }

    public void setPlaceId(Long placeId) {
        this.placeId = placeId;
    }

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityPinyin() {
		return cityPinyin;
	}

	public void setCityPinyin(String cityPinyin) {
		this.cityPinyin = cityPinyin;
	}

	public String getOldStationPinyin() {
		return oldStationPinyin;
	}

	public void setOldStationPinyin(String oldStationPinyin) {
		this.oldStationPinyin = oldStationPinyin;
	}
    
    
}