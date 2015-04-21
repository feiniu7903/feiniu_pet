package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
import java.util.Date;

public class IDSession implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1399499219531234902L;
	private String value;
	private Date createdTime;
	private Date modifyTime;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public Date getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}
	public Date getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}
}
