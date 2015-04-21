package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class GeofenceAlert implements Serializable {
	private static final long serialVersionUID = 5663503989037721559L;
	private Double latitude;
	private Double longitude;
	private Double altitude;
	private Integer rangeinmeter;
	
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}
	public Double getAltitude() {
		return altitude;
	}
	public void setAltitude(Double altitude) {
		this.altitude = altitude;
	}
	public Integer getRangeinmeter() {
		return rangeinmeter;
	}
	public void setRangeinmeter(Integer rangeinmeter) {
		this.rangeinmeter = rangeinmeter;
	}
	@Override
	public String toString() {
		return "GeofenceAlert [latitude=" + latitude + ", longitude="
				+ longitude + ", altitude=" + altitude + ", rangeinmeter="
				+ rangeinmeter + "]";
	}
	
}
