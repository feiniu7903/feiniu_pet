package com.ejingtong.model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 7201527457121256344L;
	private String userId;  
	private String userName;
	private String supplyName;   //供应商名字
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getSupplyName() {
		return supplyName;
	}
	public void setSupplyName(String supplyName) {
		this.supplyName = supplyName;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
