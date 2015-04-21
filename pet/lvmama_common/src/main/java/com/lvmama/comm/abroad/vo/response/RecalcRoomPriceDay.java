package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class RecalcRoomPriceDay implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6097616211254093613L;
	private String Date;
	private Long PricePerDay;
	private Long BoardPricePerDay;
	private String AvailStatus;
	private String AdultID;
	private String AdultDescription;
	private String ChildID;
	private String ChildDescription;
	
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public Long getPricePerDay() {
		return PricePerDay;
	}
	public void setPricePerDay(Long pricePerDay) {
		PricePerDay = pricePerDay;
	}
	public Long getBoardPricePerDay() {
		return BoardPricePerDay;
	}
	public void setBoardPricePerDay(Long boardPricePerDay) {
		BoardPricePerDay = boardPricePerDay;
	}
	public String getAvailStatus() {
		return AvailStatus;
	}
	public void setAvailStatus(String availStatus) {
		AvailStatus = availStatus;
	}
	public String getAdultID() {
		return AdultID;
	}
	public void setAdultID(String adultID) {
		AdultID = adultID;
	}
	public String getAdultDescription() {
		return AdultDescription;
	}
	public void setAdultDescription(String adultDescription) {
		AdultDescription = adultDescription;
	}
	public String getChildID() {
		return ChildID;
	}
	public void setChildID(String childID) {
		ChildID = childID;
	}
	public String getChildDescription() {
		return ChildDescription;
	}
	public void setChildDescription(String childDescription) {
		ChildDescription = childDescription;
	}
}
