package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

public class LineReqVo extends ReqVo {
	/**
	 * all-获取全量数据；update-获取变化数据
	 */
	private String requestType;
	/**
	 * 查询数据的日期，例如2013-06-01
	 */
	private String requestDate;
	/**
	 * 车次ID
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
	public String getTrainId() {
		return trainId;
	}
	public void setTrainId(String trainId) {
		this.trainId = trainId;
	}
}
