package com.lvmama.clutter.xml.lv.po;

import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

/**
 * xml请求头信息
 * @author dengcheng
 *
 */
public class RequestHead {
	/**
	 * 版本
	 */
	private String version;
	/**
	 * 一级渠道
	 */
	private String firstChannel;
	/**
	 * 二级渠道
	 */
	private String secondChannel;
	/**  
	 * 设备ID，确定唯一一部设备
	 */
	private String deviceId;
	/**
	 * 设备名称，例如ipad,iphone 或者moto me811
	 */
	private String deviceName;
	/**
	 * 操作系统名称
	 */
	private String osName;
	/**
	 * 操作系统版本
	 */
	private String osVersion;
	/**
	 * 请求的接口方法
	 */
	private String method;
	/**
	 * 页面名称
	 */
	private String pageName;
	/**
	 * userId
	 */
	private String userId;
	/**
	 * 电话详细信息
	 */
	private String phoneInfo;
	/**
	 * 客户端版本
	 */
	private String lvVersion;
	/***
	 * 所在城市
	 */
	private String cityName;
	/**
	 * 访问时间
	 */
	private String visitTime;
	
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}

	public String getSecondChannel() {
		return secondChannel;
	}
	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getOsName() {
		return osName;
	}
	public void setOsName(String osName) {
		this.osName = osName;
	}
	public String getOsVersion() {
		return osVersion;
	}
	public void setOsVersion(String osVersion) {
		this.osVersion = osVersion;
	}
	
	public String getPageName() {
		return pageName;
	}
	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getFirstChannel() {
		return firstChannel;
	}
	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhoneInfo() {
		return phoneInfo;
	}
	public void setPhoneInfo(String phoneInfo) {
		this.phoneInfo = phoneInfo;
	}
	public String getLvVersion() {
		return lvVersion;
	}
	public void setLvVersion(String lvVersion) {
		this.lvVersion = lvVersion;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	
	public String getChannel(){
		return this.firstChannel+"_"+this.secondChannel;
	}
	public String getVisitTime() {
		return visitTime;
	}
	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}
	
	public Date getVisitTimeDate(){
		if(this.visitTime!=null && !"".equals(this.visitTime)){
			return DateUtil.toDate(visitTime, "yyyy-MM-dd-HH:mm:ss");
		}
		return new Date();
	}

	

}
