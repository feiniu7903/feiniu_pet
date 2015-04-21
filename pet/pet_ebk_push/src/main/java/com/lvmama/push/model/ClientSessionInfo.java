package com.lvmama.push.model;

import java.util.Observable;

import org.apache.mina.core.session.IoSession;


public class ClientSessionInfo extends Observable{
	
	private String remoteIp;
	private String udid;
	private String userId;
	private String State; 
	private boolean isWifi;
	private String netWorkType;
	private IoSession session;
	
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
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
	public String getState() {
		return State;
	}
	public void setState(String state) {
		State = state;
		
		setChanged();
		notifyObservers(state);

	}
	
	public boolean isOnline(){
		if(session==null||session.isClosing()) {
			return false;
		}
		return true;
	}
	
	public IoSession getSession() {
		return session;
	}
	public void setSession(IoSession session) {
		this.session = session;
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
