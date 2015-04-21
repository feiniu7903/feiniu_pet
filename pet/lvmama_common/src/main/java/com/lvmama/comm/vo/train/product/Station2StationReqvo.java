package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

public class Station2StationReqvo extends ReqVo {
	/**
	 * all-获取全量数据；update-获取变化数据
	 */
	private String requestType;
	/**
	 * 查询数据的日期，例如2013-06-01
	 */
	private String requestDate;
	/**
	 * 出发车站（城市），例如上海
	 */
	private String departStation;
	/**
	 * 到达车站（城市），例如北京
	 */
	private String arriveStation;
	/**
	 * 车次ID，例如G102
	 */
	private String trainId;
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public String getDepartStation() {
		return departStation;
	}
	public void setDepartStation(String departStation) {
		this.departStation = departStation;
	}
	public String getArriveStation() {
		return arriveStation;
	}
	public void setArriveStation(String arriveStation) {
		this.arriveStation = arriveStation;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
}
