package com.lvmama.passport.processor.impl.client.carnival.model;

import java.util.List;

public class Data {
	private String text;
	private String sid;
	private String semiB;
	private String stance;
	private List<Coupon> newCoupons;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getSemiB() {
		return semiB;
	}
	public void setSemiB(String semiB) {
		this.semiB = semiB;
	}
	public String getStance() {
		return stance;
	}
	public void setStance(String stance) {
		this.stance = stance;
	}
	public List<Coupon> getNewCoupons() {
		return newCoupons;
	}
	public void setNewCoupons(List<Coupon> newCoupons) {
		this.newCoupons = newCoupons;
	}
	
	
	
	
	
}
