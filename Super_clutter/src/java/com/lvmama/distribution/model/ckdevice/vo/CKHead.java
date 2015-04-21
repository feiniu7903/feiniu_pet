package com.lvmama.distribution.model.ckdevice.vo;

import org.dom4j.DocumentException;

import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.TemplateUtils;

public class CKHead {

	/** 版本号*/
	private String version;
	/** 分销商外网出口IP*/
	private String deviceIp;
	/** 分销商帐号*/
	private String deviceCode;
	public CKHead(String version, String deviceIp, String deviceCode,
			String signed) {
		this.version = version;
		this.deviceIp = deviceIp;
		this.deviceCode = deviceCode;
		this.signed = signed;
	}

	/** 通信签证信息*/
	private String signed;
	
	public CKHead(){}
	
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
	
	public void init(String requestXml) throws DocumentException{
		deviceIp = TemplateUtils.getElementValue(requestXml, "//request/header/deviceIp");
		deviceCode = TemplateUtils.getElementValue(requestXml, "//request/header/deviceCode");
		signed = TemplateUtils.getElementValue(requestXml, "//request/header/signed");
	}
}
