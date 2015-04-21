package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

/**
 * 车站列表请求VO
 * @author XuSemon
 * @date 2013-8-12
 */
public class StationReqVo extends ReqVo {
	/**
	 * all-获取全量数据；update-获取变化数据
	 */
	private String requestType;
	/**
	 * 查询数据的日期，例如2013-06-01
	 */
	private String requestDate;
	/**
	 * 车站名称
	 */
	private String stationName;
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
	public String getStationName() {
		return stationName;
	}
	public void setStationName(String stationName) {
		this.stationName = stationName;
	}
}
