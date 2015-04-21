package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;

public class TravelFlight implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String flightDate;
	private String flightNo;
	private String airport;
	private String terminal;
	private String takeOffTime;
	private String arriveTime;
	private String travelInfo;
	private String remarks;
	private String place;

	public String getFlightDate() {
		return flightDate;
	}

	public void setFlightDate(String flightDate) {
		this.flightDate = flightDate;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public String getAirport() {
		return airport;
	}

	public void setAirport(String airport) {
		this.airport = airport;
	}

	public String getTerminal() {
		return terminal;
	}

	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}

	public String getTakeOffTime() {
		return takeOffTime;
	}

	public void setTakeOffTime(String takeOffTime) {
		this.takeOffTime = takeOffTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(String travelInfo) {
		this.travelInfo = travelInfo;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

}
