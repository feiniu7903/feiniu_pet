package com.lvmama.front.tools.ip;

import java.io.Serializable;

public class IpAddress implements Serializable{
	private static final long serialVersionUID = 3804836416975471830L;
	private int len;
	private String network;
	private String netmask;

	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	public String getNetmask() {
		return netmask;
	}

	public void setNetmask(String netmask) {
		this.netmask = netmask;
	}

	public int getLen() {
		return len;
	}

	public void setLen(int len) {
		this.len = len;
	}

	public boolean equals(Object obj) {
		IpAddress ip = (IpAddress) obj;
		return this.network.equals(ip.getNetmask()) && this.netmask.equals(ip.getNetmask());
	}

	@Override
	public String toString() {
		return network + "/" + len;
	}

}