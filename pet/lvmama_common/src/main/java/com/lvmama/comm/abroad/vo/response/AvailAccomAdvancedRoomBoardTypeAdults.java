package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class AvailAccomAdvancedRoomBoardTypeAdults implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1799698603944422942L;
	private String ID;
	private String Description;
	private String Days;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getDays() {
		return Days;
	}
	public void setDays(String days) {
		Days = days;
	}

}
