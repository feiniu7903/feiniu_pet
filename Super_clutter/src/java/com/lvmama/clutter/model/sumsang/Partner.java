package com.lvmama.clutter.model.sumsang;

import java.io.Serializable;

public class Partner implements Serializable {
	private static final long serialVersionUID = -903195655470469938L;
	private String id;
	private P partner;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public P getPartner() {
		return partner;
	}
	public void setPartner(P partner) {
		this.partner = partner;
	}
	@Override
	public String toString() {
		return "Partner [id=" + id + ", partner=" + partner + "]";
	}
}
