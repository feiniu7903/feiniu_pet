package com.lvmama.passport.haerbinjidi.model;

public class CancelOrderBean extends HeaderBean{
	private String serialId;
	private String sceneryId;
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getSceneryId() {
		return sceneryId;
	}
	public void setSceneryId(String sceneryId) {
		this.sceneryId = sceneryId;
	}
}
