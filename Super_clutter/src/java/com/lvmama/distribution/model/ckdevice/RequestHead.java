package com.lvmama.distribution.model.ckdevice;

import com.lvmama.comm.utils.StringUtil;

/**
 * CK分销请求头
 * @author gaoxin
 *
 */
public class RequestHead {

	/** 版本号*/
	private String version;
	/** 分销商外网出口IP*/
	private String deviceIp;
	/** 分销商帐号*/
	private String deviceCode;
	public RequestHead(String version, String deviceIp, String deviceCode,
			String signed) {
		this.version = version;
		this.deviceIp = deviceIp;
		this.deviceCode = deviceCode;
		this.signed = signed;
	}

	/** 通信签证信息*/
	private String signed;
	
	public RequestHead(){}
	
	public String getVersion() {
		return StringUtil.replaceNullStr(version);
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSigned() {
		return StringUtil.replaceNullStr(signed);
	}
	public void setSigned(String signed) {
		this.signed = signed;
	}

	public String getDeviceIp() {
		return StringUtil.replaceNullStr(deviceIp);
	}

	public void setDeviceIp(String deviceIp) {
		this.deviceIp = deviceIp;
	}

	public String getDeviceCode() {
		return StringUtil.replaceNullStr(deviceCode);
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

}
