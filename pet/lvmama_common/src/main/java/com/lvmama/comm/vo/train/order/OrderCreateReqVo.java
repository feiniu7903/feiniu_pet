package com.lvmama.comm.vo.train.order;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.vo.train.ReqVo;

public class OrderCreateReqVo extends ReqVo {
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
	 * 驴妈妈订单子子项ID
	 * */
	private String orderItemMetaId;
	/**
	 * 乘客信息
	 */
	private List<OrderPassengerInfo> passengers = new ArrayList<OrderPassengerInfo>();
	private Long objectId;
	private String requestType;
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
	public List<OrderPassengerInfo> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<OrderPassengerInfo> passengers) {
		this.passengers = passengers;
	}
	public String getOrderItemMetaId() {
		return orderItemMetaId;
	}
	public void setOrderItemMetaId(String orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public Long getObjectId() {
		return objectId;
	}
	public void setObjectId(Long objectId) {
		this.objectId = objectId;
	}
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
}
