/**
 * 
 */
package com.lvmama.comm.bee.vo.train;

import java.io.Serializable;
import java.util.List;

/**
 * @author yangbin
 *
 */
public class LineInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7893965942332423701L;
	private String fullName;
	private String category;
	private Station startStation;
	private Station endStation;
	private String startTime;
	private String endTime;
	private long takeDays;
	private String isDirect;
	
	private List<StopInfo> lineStops;
	
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
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
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	
	public long getTakeDays() {
		return takeDays;
	}
	public void setTakeDays(long takeDays) {
		this.takeDays = takeDays;
	}
	public List<StopInfo> getLineStops() {
		return lineStops;
	}
	public void setLineStops(List<StopInfo> lineStops) {
		this.lineStops = lineStops;
	}
	public String getIsDirect() {
		return isDirect;
	}
	public void setIsDirect(String isDirect) {
		this.isDirect = isDirect;
	}
	
	
}
