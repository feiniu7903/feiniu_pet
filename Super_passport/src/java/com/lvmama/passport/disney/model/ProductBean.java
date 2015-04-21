package com.lvmama.passport.disney.model;


public class ProductBean {
	private String eventId;
	private String name;
	private String remark;
	private String showId;
	private String showDateTime;
	private String cutoffDateTime;
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getShowId() {
		return showId;
	}
	public void setShowId(String showId) {
		this.showId = showId;
	}
	public String getShowDateTime() {
		return showDateTime;
	}
	public void setShowDateTime(String showDateTime) {
		this.showDateTime = showDateTime;
	}
	public String getCutoffDateTime() {
		return cutoffDateTime;
	}
	public void setCutoffDateTime(String cutoffDateTime) {
		this.cutoffDateTime = cutoffDateTime;
	}
}
