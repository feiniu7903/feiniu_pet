package com.lvmama.comm.vo.train.product;

import java.io.Serializable;

public class LineInfo implements Serializable{
	/**
	 * 车次，例如G102
	 */
	private String train_id;
	/**
	 * 车型，例如101（高铁）
	 */
	private int train_type;
	/**
	 * 始发车站名，中文
	 */
	private String origin_station;
	/**
	 * 终到车站名，中文
	 */
	private String terminal_station;
	/**
	 * 始发车站出发时间
	 */
	private String depart_time;
	/**
	 * 终到车站到达时间
	 */
	private String arrive_time;
	/**
	 * 全程旅行时间
	 */
	private String cost_time;
	/**
	 * 经停车站数
	 */
	private int park_station;
	/**
	 * 1-新增车次信息；2-更新车次信息；3-删除车次信息
	 */
	private int status;
	public String getTrain_id() {
		return train_id;
	}
	public void setTrain_id(String train_id) {
		this.train_id = train_id;
	}
	public int getTrain_type() {
		return train_type;
	}
	public void setTrain_type(int train_type) {
		this.train_type = train_type;
	}
	public String getOrigin_station() {
		return origin_station;
	}
	public void setOrigin_station(String origin_station) {
		this.origin_station = origin_station;
	}
	public String getTerminal_station() {
		return terminal_station;
	}
	public void setTerminal_station(String terminal_station) {
		this.terminal_station = terminal_station;
	}
	public String getDepart_time() {
		return depart_time;
	}
	public void setDepart_time(String depart_time) {
		this.depart_time = depart_time;
	}
	public String getArrive_time() {
		return arrive_time;
	}
	public void setArrive_time(String arrive_time) {
		this.arrive_time = arrive_time;
	}
	public String getCost_time() {
		return cost_time;
	}
	public void setCost_time(String cost_time) {
		this.cost_time = cost_time;
	}
	public int getPark_station() {
		return park_station;
	}
	public void setPark_station(int park_station) {
		this.park_station = park_station;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return "train_id:" + train_id + "|train_type:" + train_type
				+ "|origin_station:" + origin_station + "|terminal_station:" + terminal_station
				+ "|depart_time:" + depart_time + "|arrive_time:" + arrive_time
				+ "|cost_time:" + cost_time + "|park_station:" + park_station
				+ "|status:" + status;
	}
}
