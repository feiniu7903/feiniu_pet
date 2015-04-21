package com.ejingtong.model;

import java.io.Serializable;

import com.ejingtong.common.Constans;

/**
 * 日志对象
 * @author xuqun
 *
 */
public class Logger implements Serializable {

	private static final long serialVersionUID = -7657763246316383086L;

	private String addCode; //附加码
	private String time; //时间
	private String udid = Constans.IMEI; //唯一标识码
	private String operator = Constans.userInfo.getUserId(); //操作员
	private String action; //操作 同步/取消/通关
	
	public Logger(){
		
	}
	
	public String getAddCode() {
		return addCode;
	}
	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUdid() {
		return udid;
	}
	public void setUdid(String udid) {
		this.udid = udid;
	}
	public String getOperator() {
		return operator;
	}
	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		String str = addCode + "_" + time + "_" + action + "_" + udid + "_" + operator;
		
		return str;
	}
	
	
}
