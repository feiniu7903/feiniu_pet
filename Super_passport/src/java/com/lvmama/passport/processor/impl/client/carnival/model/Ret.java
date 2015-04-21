package com.lvmama.passport.processor.impl.client.carnival.model;

public class Ret {
	private String code;
	private String type;
	private String reqid;
	private String time;
	private Data data;
	private String dsa;
	
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReqid() {
		return reqid;
	}
	public void setReqid(String reqid) {
		this.reqid = reqid;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
	public String getDsa() {
		return dsa;
	}
	public void setDsa(String dsa) {
		this.dsa = dsa;
	}
}
