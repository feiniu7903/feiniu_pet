package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketBookReqVo extends ReqVo {
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 出发车站（城市），例如上海
	 */
	private String departStation;
	/**
	 * 到达车站（城市），例如北京
	 */
	private String arriveStation;
	/**
	 * 出发日期，例如2013-06-01
	 */
	private String departDate;
	/**
	 * 车次ID，例如G102
	 */
	private String trainId;
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
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
	public String getDepartDate() {
		return departDate;
	}
	public void setDepartDate(String departDate) {
		this.departDate = departDate;
	}
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
}
