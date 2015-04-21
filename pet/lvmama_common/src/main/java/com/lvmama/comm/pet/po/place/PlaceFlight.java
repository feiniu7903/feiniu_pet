package com.lvmama.comm.pet.po.place;

import java.io.Serializable;

public class PlaceFlight implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 4719156244295095646L;

	private Long placeFlightId;

    private Long startAirportId;
    private PlaceAirport startAirport;    

    private Long arriveAirportId;
    private PlaceAirport arriveAirport;

    private Long airplaneId;
    private PlacePlaneModel airplane;

    private Long airlineId;
    private PlaceAirline airline;

    private String flightNo;

    private String startTime;

    private String arriveTime;

    private Long flightTime;

    private Long startPlaceId;
    private Place startPlace;

    private Long arrivePlaceId;
    private Place arrivePlace;

    private String berthInfo;

    private String startTerminal;

    private String arriveTerminal;

    private Long stopTime;
    
    private String startPlaceName;
    private String arrivePlaceName;

    public Long getPlaceFlightId() {
        return placeFlightId;
    }

    public void setPlaceFlightId(Long placeFlightId) {
        this.placeFlightId = placeFlightId;
    }

    public Long getStartAirportId() {
        return startAirportId;
    }

    public void setStartAirportId(Long startAirportId) {
        this.startAirportId = startAirportId;
    }

    public Long getArriveAirportId() {
        return arriveAirportId;
    }

    public void setArriveAirportId(Long arriveAirportId) {
        this.arriveAirportId = arriveAirportId;
    }

    public Long getAirplaneId() {
        return airplaneId;
    }

    public void setAirplaneId(Long airplaneId) {
        this.airplaneId = airplaneId;
    }

    public Long getAirlineId() {
        return airlineId;
    }

    public void setAirlineId(Long airlineId) {
        this.airlineId = airlineId;
    }
   
    public String getFlightNo() {
        return flightNo;
    }

    public void setFlightNo(String flightNo) {
        this.flightNo = flightNo == null ? null : flightNo.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime == null ? null : startTime.trim();
    }

    public String getArriveTime() {
        return arriveTime;
    }

    public void setArriveTime(String arriveTime) {
        this.arriveTime = arriveTime == null ? null : arriveTime.trim();
    }

    public Long getFlightTime() {
        return flightTime;
    }

    public void setFlightTime(Long flightTime) {
        this.flightTime = flightTime;
    }

    public Long getStartPlaceId() {
        return startPlaceId;
    }

    public void setStartPlaceId(Long startPlaceId) {
        this.startPlaceId = startPlaceId;
    }

    public Long getArrivePlaceId() {
        return arrivePlaceId;
    }

    public void setArrivePlaceId(Long arrivePlaceId) {
        this.arrivePlaceId = arrivePlaceId;
    }

    public String getBerthInfo() {
        return berthInfo;
    }

    public void setBerthInfo(String berthInfo) {
        this.berthInfo = berthInfo == null ? null : berthInfo.trim();
    }

    public String getStartTerminal() {
        return startTerminal;
    }

    public void setStartTerminal(String startTerminal) {
        this.startTerminal = startTerminal == null ? null : startTerminal.trim();
    }

    public String getArriveTerminal() {
        return arriveTerminal;
    }

    public void setArriveTerminal(String arriveTerminal) {
        this.arriveTerminal = arriveTerminal == null ? null : arriveTerminal.trim();
    }

    public Long getStopTime() {
        return stopTime;
    }

    public void setStopTime(Long stopTime) {
        this.stopTime = stopTime;
    }

	public PlaceAirport getStartAirport() {
		return startAirport;
	}

	public void setStartAirport(PlaceAirport startAirport) {
		this.startAirport = startAirport;
	}

	public PlaceAirport getArriveAirport() {
		return arriveAirport;
	}

	public void setArriveAirport(PlaceAirport arriveAirport) {
		this.arriveAirport = arriveAirport;
	}

	
	public PlacePlaneModel getAirplane() {
		return airplane;
	}

	public void setAirplane(PlacePlaneModel airplane) {
		this.airplane = airplane;
	}

	public Place getStartPlace() {
		return startPlace;
	}

	public void setStartPlace(Place startPlace) {
		this.startPlace = startPlace;
	}

	public Place getArrivePlace() {
		return arrivePlace;
	}

	public void setArrivePlace(Place arrivePlace) {
		this.arrivePlace = arrivePlace;
	}

	public PlaceAirline getAirline() {
		return airline;
	}

	public void setAirline(PlaceAirline airline) {
		this.airline = airline;
	}
    
    public String getZhFlightTime(){
    	return flightTime+"时";
    }
//    private Pair<Integer, Integer> getSpecTime(){
//    	String[] starts=startTime.split(":");
//    	String[] arrives=arriveTime.split(":");    	
//		int hour1=NumberUtils.toInt(starts[0]);
//		int hour2=NumberUtils.toInt(arrives[0]);
//		int m1=NumberUtils.toInt(starts[1]);
//		int m2=NumberUtils.toInt(arrives[1]);
//    	int r1=hour2-hour1;
//    	int r2=m2-m1;
//    	if(r2<0){
//    		r2=60+r2%60;
//    		r1-=1;
//    	}
//    	if(r1<0){
//    		r1=flightTime.intValue()-r1;
//    	}else if(flightTime>24){
//    		r1=(int)((flightTime/24)*24L)+r1;
//    	}
//    	Pair<Integer, Integer> pair=Pair.make_pair(r1, r2);
//    	return pair;
//    }

	public String getArrivePlaceName() {
		return arrivePlaceName;
	}

	public void setArrivePlaceName(String arrivePlaceName) {
		this.arrivePlaceName = arrivePlaceName;
	}

	public String getStartPlaceName() {
		return startPlaceName;
	}

	public void setStartPlaceName(String startPlaceName) {
		this.startPlaceName = startPlaceName;
	}
	
	public String[] getBerthInfoOptions() {
		if (null == this.berthInfo) {
			return null;
		} else {
			return berthInfo.split(",");
		}
	}
}