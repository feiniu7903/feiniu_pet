/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yangbin
 *
 */
public class Station2StationInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 217722984902777336L;
	private String lineName;
	private Station departureStation;
	private Station arrivalStation;
	private String departureTime;
	private String arrivalTime;
	private long takeTime;
	
	private List<SeatInfo> seats;
	
	private Station startStation;
	private Station endStation;
	private String catalog;
	private String bookable;
	private String isDirect;

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public Station getDepartureStation() {
		return departureStation;
	}

	public void setDepartureStation(Station departureStation) {
		this.departureStation = departureStation;
	}

	public Station getArrivalStation() {
		return arrivalStation;
	}

	public void setArrivalStation(Station arrivalStation) {
		this.arrivalStation = arrivalStation;
	}

	public String getDepartureTime() {
		return departureTime;
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}

	public String getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public long getTakeTime() {
		return takeTime;
	}

	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}

	public List<SeatInfo> getSeats() {
		return seats;
	}

	public void setSeats(List<SeatInfo> seats) {
		this.seats = seats;
	}

	public Station getStartStation() {
		return startStation;
	}

	public void setStartStation(Station startStation) {
		this.startStation = startStation;
	}

	public Station getEndStation() {
		return endStation;
	}

	public void setEndStation(Station endStation) {
		this.endStation = endStation;
	}

	public String getBookable() {
		return bookable;
	}

	public void setBookable(String bookable) {
		this.bookable = bookable;
	}

	public String getIsDirect() {
		return isDirect;
	}

	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
	
	public String getStationKey(){
		StringBuffer sb = new StringBuffer();
		sb.append(departureStation.getPinyin());
		sb.append("-");
		sb.append(arrivalStation.getPinyin());
		return sb.toString();
	}
	
	public String getLineStationKey(){
		StringBuffer sb = new StringBuffer();
		sb.append(startStation.getPinyin());
		sb.append("-");
		sb.append(endStation.getPinyin());
		sb.append("_");
		sb.append(lineName);
		return sb.toString();
	}
	
	public boolean hasBookable(){
		return StringUtils.equalsIgnoreCase(bookable, "true");
	}
}
