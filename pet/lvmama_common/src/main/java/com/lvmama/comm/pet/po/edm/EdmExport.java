package com.lvmama.comm.pet.po.edm;

import java.io.Serializable;
import java.util.Date;

/**
 * @author yangbin
 *
 */
public class EdmExport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -55270908950960960L;
	private Long edmId;
	private String loginName;
	private int count=0;
	private Date date;
	public Long getEdmId() {
		return edmId;
	}
	public void setEdmId(Long edmId) {
		this.edmId = edmId;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
