package com.lvmama.comm.pet.po.conn;

import java.io.Serializable;
import java.util.Date;

public class SensitiveKeys implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5413860875951407318L;
	
	private String keys;
	private Date createDate;
	
	public String getKeys() {
		return keys;
	}
	public void setKeys(String keys) {
		this.keys = keys;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
}
