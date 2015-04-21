package com.lvmama.push.model;

import java.io.Serializable;
import java.util.Map;

public class ClientCommand implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7661994180462647151L;
	private String command;
	private String udid;
	private String userId;
	private String network;
	private String version;
	private boolean isWifi;
	private String netWorkType;
	
	public   ClientCommand(Map<String,Object> map){
		this.command  = (String)map.get("command");
		this.udid  = (String)map.get("udid");
		this.userId  = (String)map.get("userId");
		this.command  = (String)map.get("command");
		this.version  = (String)map.get("version");
		this.isWifi = (Boolean)map.get("isWifi");
		this.netWorkType = (String)map.get("netWorkType");
	}
	
	public boolean isRegCommand(){
		if("REG".equals(command)){
			return true;
		}
		return false;
	}
	
	public boolean isSyncDataCommand(){
		if("SYNC_DATA".equals(command)){
			return true;
		}
		return false;
	}
	
	public boolean isSyncDataForNewDeviceCommand(){
		if("SYNC_DATA_NEW_DEVICE".equals(command)){
			return true;
		}
		return false;
	}
	
	
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
	public String getNetwork() {
		return network;
	}
	public void setNetwork(String network) {
		this.network = network;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public boolean isWifi() {
		return isWifi;
	}

	public void setWifi(boolean isWifi) {
		this.isWifi = isWifi;
	}

	public String getNetWorkType() {
		return netWorkType;
	}

	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}
}
