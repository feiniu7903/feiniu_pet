package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class Alert implements Serializable {
	private static final long serialVersionUID = 5663503989037721559L;
	private String id;
	private DateAlert date;
	private GeofenceAlert geofence;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public DateAlert getDate() {
		return date;
	}
	public void setDate(DateAlert date) {
		this.date = date;
	}
	public GeofenceAlert getGeofence() {
		return geofence;
	}
	public void setGeofence(GeofenceAlert geofence) {
		this.geofence = geofence;
	}
	@Override
	public String toString() {
		return "Alert [id=" + id + ", date=" + date + ", geofence=" + geofence
				+ "]";
	}
	
}
