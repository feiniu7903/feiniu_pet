package com.lvmama.comm.pet.po.client;

public class ClientUserCouponInfo {
	private String name;
	private String expiredDate;
	//
	private String expiredData;
	private String code;
	private String price;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getExpiredDate() {
		return expiredDate;
	}
	public void setExpiredDate(String expiredDate) {
		this.expiredDate = expiredDate;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getExpiredData() {
		return expiredData;
	}
	public void setExpiredData(String expiredData) {
		this.expiredData = expiredData;
	}
}
