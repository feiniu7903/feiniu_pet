package com.ejingtong.model;

import java.io.Serializable;
import java.util.List;

public class ResponseData implements Serializable{
	
	private static final long serialVersionUID = -3774488043040776441L;

	private int code;
	
	private String message;
	
	private String syncTime;
	
	private boolean hasNext;
	
	private List<Order> datas;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public List<Order> getDatas() {
		return datas;
	}

	public void setDatas(List<Order> datas) {
		this.datas = datas;
	}

	public String getSyncTime() {
		return syncTime;
	}

	public void setSyncTime(String syncTime) {
		this.syncTime = syncTime;
	}
	
	
}
