package com.lvmama.jinjiang.model;

import java.util.List;
/**
 * 行程
 * @author chenkeke
 *
 */
public class Journey {
	private Integer dayNumber; // 第几天
	private String departurePlace; // 出发地
	private String destination; // 目的地
	private String subject; // 主题
	private String tourDescription; // 行程描述
	private String trafficDescription; // 交通描述
	private String repastDescription; // 就餐描述
	private String accomodationDescription; // 住宿描述
	private String planDescription; // 行程计划描述
	private Integer stationNumber; // 第几站
	private String departureTime; // 出发时间
	private String arriveTime; // 抵达时间
	private String carrierName; // 航空公司
	private String groundAgent; // 地接
	private String portOfTransit; // 中转站
	private String transitFlag; // 中转标志
	private String flightNo; // 航班号
	private Integer nightsNum; // 第几晚
	private List<String> attractions; // 景点列表

	public Integer getDayNumber() {
		return dayNumber;
	}

	public void setDayNumber(Integer dayNumber) {
		this.dayNumber = dayNumber;
	}

	public String getDeparturePlace() {
		return departurePlace;
	}

	public void setDeparturePlace(String departurePlace) {
		this.departurePlace = departurePlace;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getTourDescription() {
		return tourDescription;
	}

	public void setTourDescription(String tourDescription) {
		this.tourDescription = tourDescription;
	}

	public String getTrafficDescription() {
		return trafficDescription;
	}

	public void setTrafficDescription(String trafficDescription) {
		this.trafficDescription = trafficDescription;
	}

	public String getRepastDescription() {
		return repastDescription;
	}

	public void setRepastDescription(String repastDescription) {
		this.repastDescription = repastDescription;
	}

	public String getAccomodationDescription() {
		return accomodationDescription;
	}

	public void setAccomodationDescription(String accomodationDescription) {
		this.accomodationDescription = accomodationDescription;
	}

	public String getPlanDescription() {
		return planDescription;
	}

	public void setPlanDescription(String planDescription) {
		this.planDescription = planDescription;
	}

	public Integer getStationNumber() {
		return stationNumber;
	}

	public void setStationNumber(Integer stationNumber) {
		this.stationNumber = stationNumber;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String getCarrierName() {
		return carrierName;
	}

	public void setCarrierName(String carrierName) {
		this.carrierName = carrierName;
	}

	public String getGroundAgent() {
		return groundAgent;
	}

	public void setGroundAgent(String groundAgent) {
		this.groundAgent = groundAgent;
	}

	public String getPortOfTransit() {
		return portOfTransit;
	}

	public void setPortOfTransit(String portOfTransit) {
		this.portOfTransit = portOfTransit;
	}

	public String getTransitFlag() {
		return transitFlag;
	}

	public void setTransitFlag(String transitFlag) {
		this.transitFlag = transitFlag;
	}

	public String getFlightNo() {
		return flightNo;
	}

	public void setFlightNo(String flightNo) {
		this.flightNo = flightNo;
	}

	public Integer getNightsNum() {
		return nightsNum;
	}

	public void setNightsNum(Integer nightsNum) {
		this.nightsNum = nightsNum;
	}

	public List<String> getAttractions() {
		return attractions;
	}

	public void setAttractions(List<String> attractions) {
		this.attractions = attractions;
	}

}
