package com.lvmama.comm.bee.po.market;

import java.io.Serializable;
import java.util.Date;

public class ApplyCity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Long applyCityId;
	private String cityName;
	private String cityPY;
	
	public Long getApplyCityId() {
		return applyCityId;
	}
	public void setApplyCityId(Long applyCityId) {
		this.applyCityId = applyCityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public String getCityPY() {
		return cityPY;
	}
	public void setCityPY(String cityPY) {
		this.cityPY = cityPY;
	}
}
