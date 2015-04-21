package com.ejingtong.model;

import java.io.Serializable;

public class UpdateInfo implements Serializable{

	private static final long serialVersionUID = -6482063708200418957L;
	private String version;
	private String info;
	private String url;
	
	
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}
