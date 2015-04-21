package com.lvmama.comm.vo.train.product;

import java.io.Serializable;

public class LineStopsStationInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8451629578L;
	/**
	 * 车站序号，始发站为1，其他车站依次增加
	 */
	private int station_no;
	/**
	 * 车站名，中文
	 */
	private String station_name;
	/**
	 * 到站时间
	 */
	private String arrive_time;
	/**
	 * 出发时间
	 */
	private String depart_time;
	/**
	 * 旅行时间，从始发站到当前车站旅行时间
	 */
	private String cost_time;
	/**
	 * 停站时间
	 */
	private String rest_time;
	public int getStation_no() {
		return station_no;
	}
	public void setStation_no(int station_no) {
		this.station_no = station_no;
	}
	public String getStation_name() {
		return station_name;
	}
	public void setStation_name(String station_name) {
		this.station_name = station_name;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(String depart_time) {
		this.depart_time = depart_time;
	}
	public String getCost_time() {
		return cost_time;
	}
	public void setCost_time(String cost_time) {
		this.cost_time = cost_time;
	}
	public String getRest_time() {
		return rest_time;
	}
	public void setRest_time(String rest_time) {
		this.rest_time = rest_time;
	}
	
	@Override
	public String toString(){
		return "station_no:" + station_no + "|station_name:" + station_name
				+ "|arrive_time" + arrive_time + "|depart_time:" + depart_time
				+ "|cost_time:" + cost_time + "|rest_time:" + rest_time;
	}
}
