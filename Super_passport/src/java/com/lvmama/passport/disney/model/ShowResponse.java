package com.lvmama.passport.disney.model;

import java.util.List;

public class ShowResponse {
	private String responseCode;
	private List<Show> shows;
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public List<Show> getShows() {
		return shows;
	}
	public void setShows(List<Show> shows) {
		this.shows = shows;
	}
}
