package com.lvmama.push.model;

public class PushDevice {
	String udid;
	String isOnline;
	String remoteIp;
	String placeName;
	Long todayMstNum;
	String netWorkType;
	public Long getTodayMstNum() {
		return todayMstNum;
	}
	public void setTodayMstNum(Long todayMstNum) {
		this.todayMstNum = todayMstNum;
	}
	Long msgTimeOutNum;
	String adminName;
	
	public String getAdminName() {
		return adminName;
	}
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	
	public String getRemoteIp() {
		return remoteIp;
	}
	public void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}
	public String getPlaceName() {
		return placeName;
	}
	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}
	public Long getMsgTimeOutNum() {
		return msgTimeOutNum;
	}
	public void setMsgTimeOutNum(Long msgTimeOutNum) {
		this.msgTimeOutNum = msgTimeOutNum;
	}
	public Long getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(Long msgCount) {
		this.msgCount = msgCount;
	}
	Long msgCount;
	public String getNetWorkType() {
		return netWorkType;
	}
	public void setNetWorkType(String netWorkType) {
		this.netWorkType = netWorkType;
	}
}
