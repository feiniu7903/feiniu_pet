package com.lvmama.passport.processor.impl.client.xichen.model;
public class SubmitOrderBean {
	private String username;//用户名
	private String tid;//票的Id
	private String num;//售票张数
	private String name;//顾客姓名
	private String idcard;//身份证号
	private String phone;//手机号
	private String tickType;//区分电子票还是预售票
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTickType() {
		return tickType;
	}
	public void setTickType(String tickType) {
		this.tickType = tickType;
	}
}
