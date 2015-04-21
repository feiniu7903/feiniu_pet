package com.lvmama.comm.vo.train.product;

import java.io.Serializable;
import java.util.List;

public class LineStopsInfo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5462135987L;
	/**
	 * 车次，例如G102
	 */
	private String train_id;
	/**
	 * 1-新增车次经停数据；2-更新车次经停数据；3-删除车次经停数据
	 */
	private int status;
	/**
	 * 停站信息，是一个JSON数组
	 */
	private List<LineStopsStationInfo> park_station;
	public String getTrain_id() {
		return train_id;
	}
	public void setTrain_id(String train_id) {
		this.train_id = train_id;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public List<LineStopsStationInfo> getPark_station() {
		return park_station;
	}
	public void setPark_station(List<LineStopsStationInfo> park_station) {
		this.park_station = park_station;
	}
	
	@Override
	public String toString(){
		for(LineStopsStationInfo lineStopsStationInfo : park_station){
			System.out.println(lineStopsStationInfo);
		}
		return "Train_id:" + train_id;
	}
}
