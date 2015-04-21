/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;

/**
 * @author yangbin
 *
 */
public class StopInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6405803297866547904L;
	private String lineName;
	private Station station;
	private long seq;
	private String arrivalTime;
	private String departureTime;
	private long takeTime;
	public String getLineName() {
		return lineName;
	}
	public void setLineName(String lineName) {
		this.lineName = lineName;
	}
	public Station getStation() {
		return station;
	}
	public void setStation(Station station) {
		this.station = station;
	}
	public long getSeq() {
		return seq;
	}
	public void setSeq(long seq) {
		this.seq = seq;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public long getTakeTime() {
		return takeTime;
	}
	public void setTakeTime(long takeTime) {
		this.takeTime = takeTime;
	}
}
