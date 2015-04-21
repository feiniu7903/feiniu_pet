package com.lvmama.passport.disney.model;

import java.util.List;

public class EventResponse {
	private String responseCode;
	private List<Event> events;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
}
