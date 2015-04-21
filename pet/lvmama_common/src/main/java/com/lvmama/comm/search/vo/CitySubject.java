package com.lvmama.comm.search.vo;

import java.io.Serializable;

public class CitySubject implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Long cityId;
	String subject;
	public Long getCityId() {
		return cityId;
	}
	public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
}
