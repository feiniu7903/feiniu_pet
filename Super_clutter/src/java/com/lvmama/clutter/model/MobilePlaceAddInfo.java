package com.lvmama.clutter.model;

import java.io.Serializable;
import java.util.Date;

public class MobilePlaceAddInfo implements Serializable {
	private static final long serialVersionUID = 7967743763476575813L;
	private String ticketType;
	private Date lastOrderTime;
	private String lastOrderTimeDesc = "";
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public Date getLastOrderTime() {
		return lastOrderTime;
	}
	public void setLastOrderTime(Date lastOrderTime) {
		this.lastOrderTime = lastOrderTime;
	}
	public String getLastOrderTimeDesc() {
		return lastOrderTimeDesc;
	}
	public void setLastOrderTimeDesc(String lastOrderTimeDesc) {
		this.lastOrderTimeDesc = lastOrderTimeDesc;
	}

}
