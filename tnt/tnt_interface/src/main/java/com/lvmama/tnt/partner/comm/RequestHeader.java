package com.lvmama.tnt.partner.comm;

import java.io.Serializable;

public class RequestHeader implements Serializable {

	private static final long serialVersionUID = -8323770878073947769L;

	private String version;
	private String deviceCode;
	private String signed;

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDeviceCode() {
		return deviceCode;
	}

	public void setDeviceCode(String deviceCode) {
		this.deviceCode = deviceCode;
	}

	public String getSigned() {
		return signed;
	}

	public void setSigned(String signed) {
		this.signed = signed;
	}

}
