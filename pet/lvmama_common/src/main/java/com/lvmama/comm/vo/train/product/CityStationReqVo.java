package com.lvmama.comm.vo.train.product;

import com.lvmama.comm.vo.train.ReqVo;

public class CityStationReqVo extends ReqVo {
	/**
	 * all-获取全量数据；update-获取变化数据
	 */
	private String requestType;
	/**
	 * 查询数据的日期，例如2013-06-01s
	 */
	private String requestDate;
	/**
	 * 城市名称
	 */
	private String cityName;
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
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
}
