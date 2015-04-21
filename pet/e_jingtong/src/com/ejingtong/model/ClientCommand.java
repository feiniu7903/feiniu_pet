package com.ejingtong.model;

import java.io.Serializable;

public class ClientCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1991201814849969568L;
	private String command;
	private String udid;
	private String userId;
	private String version;
	private boolean isWifi;
	private String netWorkType;
	
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isWifi() {
		return isWifi;
	}
	public void setWifi(boolean isWifi) {
		this.isWifi = isWifi;
	}
	public String isNetWorkType() {
		return netWorkType;
	}
	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}

}
