package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupTravelInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String groupType;
	private String contactTel;
	private String contactTel2;
	private String localTel;

	private String gatherTime;
	private String gatherPlace;
	private String takeFlag;
	private String foreignGroupName;
	private String foreignGroupAddress;
	private String foreignGroupContact;
	
	private String[] flightDate;
	private String[] flightNo;
	private String[] airport;
	private String[] terminal;
	private String[] takeOffTime;
	private String[] arriveTime;
	private String[] remarks;
	private String[] travelInfo;
	private String[] place;
	
	public List<TravelFlight> getTravelFlightList() {
		List<TravelFlight> list = new ArrayList<TravelFlight>();
		if (null == flightDate) {
			return null;
		}
		for (int i = 0; i < flightDate.length; i++) {
			TravelFlight tf = new TravelFlight();
			tf.setFlightDate(flightDate[i]);
			if (null != flightNo) {
				tf.setFlightNo(flightNo[i]);
			}
			if (null != airport) {
				tf.setAirport(airport[i]);
			}
			if (null != terminal) {
				tf.setTerminal(terminal[i]);
			}
			if (null != takeOffTime) {
				tf.setTakeOffTime(takeOffTime[i]);
			}
			if (null != arriveTime) {
				tf.setArriveTime(arriveTime[i]);
			}
			if (null != remarks) {
				tf.setRemarks(remarks[i]);
			}
			if (null != travelInfo) {
				tf.setTravelInfo(travelInfo[i]);
			}
			if (null != place) {
				tf.setPlace(place[i]);
			}
			list.add(tf);
		}
		return list;
	}

	public String[] getFlightDate() {
		return flightDate;
	}
	public void setFlightDate(String[] flightDate) {
		this.flightDate = flightDate;
	}
	public String[] getFlightNo() {
		return flightNo;
	}
	public void setFlightNo(String[] flightNo) {
		this.flightNo = flightNo;
	}
	public String[] getAirport() {
		return airport;
	}
	public void setAirport(String[] airport) {
		this.airport = airport;
	}
	public String[] getTerminal() {
		return terminal;
	}
	public void setTerminal(String[] terminal) {
		this.terminal = terminal;
	}
	public String[] getTakeOffTime() {
		return takeOffTime;
	}
	public void setTakeOffTime(String[] takeOffTime) {
		this.takeOffTime = takeOffTime;
	}
	public String[] getRemarks() {
		return remarks;
	}
	public void setRemarks(String[] remarks) {
		this.remarks = remarks;
	}
	public String getGroupType() {
		return groupType;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public String getContactTel() {
		return contactTel;
	}
	public void setContactTel(String contactTel) {
		this.contactTel = contactTel;
	}
	public String getContactTel2() {
		return contactTel2;
	}
	public void setContactTel2(String contactTel2) {
		this.contactTel2 = contactTel2;
	}
	public String getLocalTel() {
		return localTel;
	}
	public void setLocalTel(String localTel) {
		this.localTel = localTel;
	}
	public String getGatherTime() {
		return gatherTime;
	}
	public void setGatherTime(String gatherTime) {
		this.gatherTime = gatherTime;
	}
	public String getGatherPlace() {
		return gatherPlace;
	}
	public void setGatherPlace(String gatherPlace) {
		this.gatherPlace = gatherPlace;
	}
	public String getTakeFlag() {
		return takeFlag;
	}
	public void setTakeFlag(String takeFlag) {
		this.takeFlag = takeFlag;
	}
	public String getForeignGroupName() {
		return foreignGroupName;
	}
	public void setForeignGroupName(String foreignGroupName) {
		this.foreignGroupName = foreignGroupName;
	}
	public String getForeignGroupAddress() {
		return foreignGroupAddress;
	}
	public void setForeignGroupAddress(String foreignGroupAddress) {
		this.foreignGroupAddress = foreignGroupAddress;
	}
	public String getForeignGroupContact() {
		return foreignGroupContact;
	}
	public void setForeignGroupContact(String foreignGroupContact) {
		this.foreignGroupContact = foreignGroupContact;
	}

	public String[] getTravelInfo() {
		return travelInfo;
	}

	public void setTravelInfo(String[] travelInfo) {
		this.travelInfo = travelInfo;
	}

	public String[] getArriveTime() {
		return arriveTime;
	}

	public void setArriveTime(String[] arriveTime) {
		this.arriveTime = arriveTime;
	}

	public String[] getPlace() {
		return place;
	}

	public void setPlace(String[] place) {
		this.place = place;
	}
	
}
