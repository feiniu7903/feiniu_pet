package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
public class HotelDetailsRoomType implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8643678578185318626L;
	private String ID;
	private String Name;
	private String Description;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
}
