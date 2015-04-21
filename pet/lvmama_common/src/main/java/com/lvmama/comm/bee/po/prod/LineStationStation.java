package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class LineStationStation implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 5841971664865248777L;

	private Long stationStationId;

    private Long departureStationId;
    
    private LineStation departureStation;

    private Long arrivalStationId;
    
    private LineStation arrivalStation;
    
    private String lineName;

    private Long departureTime;

    private Long arrivalTime;

    private String stationKey;
    
    private String cityKey;

    private Long takenTime;

    private String direct;
    
    private Long lineInfoId;
    
    private Long startStationId;
    private Long endStationId;
    private String startStationName;
    private String endStationName;

    public Long getStationStationId() {
        return stationStationId;
    }

    public void setStationStationId(Long stationStationId) {
        this.stationStationId = stationStationId;
    }

    public Long getDepartureStationId() {
        return departureStationId;
    }

    public void setDepartureStationId(Long departureStationId) {
        this.departureStationId = departureStationId;
    }

    public Long getArrivalStationId() {
        return arrivalStationId;
    }

    public void setArrivalStationId(Long arrivalStationId) {
        this.arrivalStationId = arrivalStationId;
    }

    public Long getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Long departureTime) {
        this.departureTime = departureTime;
    }

    public Long getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Long arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getStationKey() {
        return stationKey;
    }

    public void setStationKey(String stationKey) {
        this.stationKey = stationKey == null ? null : stationKey.trim();
    }

    public Long getTakenTime() {
        return takenTime;
    }

    public void setTakenTime(Long takenTime) {
        this.takenTime = takenTime;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct == null ? null : direct.trim();
    }

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public LineStation getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(LineStation departureStation) {
		this.departureStation = departureStation;
	}

	public LineStation getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(LineStation arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public Long getLineInfoId() {
		return lineInfoId;
	}

	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}

	public String getCityKey() {
		return cityKey;
	}

	public void setCityKey(String cityKey) {
		this.cityKey = cityKey;
	}
	
	public Long getStartStationId() {
		return startStationId;
	}

	public void setStartStationId(Long startStationId) {
		this.startStationId = startStationId;
	}

	public Long getEndStationId() {
		return endStationId;
	}

	public void setEndStationId(Long endStationId) {
		this.endStationId = endStationId;
	}

	public String getStartStationName() {
		return startStationName;
	}

	public void setStartStationName(String startStationName) {
		this.startStationName = startStationName;
	}

	public String getEndStationName() {
		return endStationName;
	}

	public void setEndStationName(String endStationName) {
		this.endStationName = endStationName;
	}

	public void makeCityKey(){
		StringBuffer sb = new StringBuffer();
		if (departureStation != null){
			sb.append(departureStation.getCityPinyin());
		}
		sb.append("-");
		if(arrivalStation!=null){
			sb.append(arrivalStation.getCityPinyin());
		}
		cityKey = sb.toString();
	}
}