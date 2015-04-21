package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.util.List;

public class Prod implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8942676497729648040L;
	private String description;
	private String smsContent;
	private Long aheadHour;
	private Integer minimum;
	private List<String> placeNameList;
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getSmsContent() {
		return smsContent;
	}
	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}
	public Long getAheadHour() {
		return aheadHour;
	}
	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public List<String> getPlaceNameList() {
		return placeNameList;
	}
	public void setPlaceNameList(List<String> placeNameList) {
		this.placeNameList = placeNameList;
	}
}
