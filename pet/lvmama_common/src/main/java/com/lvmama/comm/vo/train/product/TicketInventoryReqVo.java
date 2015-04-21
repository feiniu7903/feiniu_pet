package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketInventoryReqVo extends ReqVo {
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 出发车站
	 */
	private String departStation;
	/**
	 * 到达车站
	 */
	private String arriveStation;
	/**
	 * 出发日期
	 */
	private String departDate;
	/**
	 * 所选择车次
	 */
	private String trainId;
	/**
	 * 所选择坐席，例如204（二等座）
	 */
	private String ticketClass;
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
	public String getTicketClass() {
		return ticketClass;
	}
	public void setTicketClass(String ticketClass) {
		this.ticketClass = ticketClass;
	}
}
