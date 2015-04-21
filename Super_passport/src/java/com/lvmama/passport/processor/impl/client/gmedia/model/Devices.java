package com.lvmama.passport.processor.impl.client.gmedia.model;

import java.util.List;

public class Devices {
   private List<String> deviceId;
	public String toResponseDevicesXml() {
		StringBuilder buf = new StringBuilder();
		for (String str : this.deviceId) {
			buf.append("<DeviceId>");
			buf.append(str);
			buf.append("</DeviceId>");
		}
		return buf.toString();
	}
	public List<String> getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(List<String> deviceId) {
		this.deviceId = deviceId;
	}
	
}
