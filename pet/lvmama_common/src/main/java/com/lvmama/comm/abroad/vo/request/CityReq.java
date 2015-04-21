package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;

public class CityReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String cityName;

	public String getCityName() {
		return cityName;
	}
	/**
	 * 
	 * @param cityName 请求查询的城市信息
	 */
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

}
