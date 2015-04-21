package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class P implements Serializable {
	private static final long serialVersionUID = 1297890600490259663L;
	private String banner;
	private String link;
	private ValidUntil validUntil;
	public String getBanner() {
		return banner;
	}
	public void setBanner(String banner) {
		this.banner = banner;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public ValidUntil getValidUntil() {
		return validUntil;
	}
	public void setValidUntil(ValidUntil validUntil) {
		this.validUntil = validUntil;
	}
	@Override
	public String toString() {
		return "Partner [banner=" + banner + ", link=" + link + ", validUntil="
				+ validUntil + "]";
	}
	
}
