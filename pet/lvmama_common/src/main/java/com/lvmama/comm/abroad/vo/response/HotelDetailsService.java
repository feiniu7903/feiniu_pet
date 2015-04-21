package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class HotelDetailsService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4795420183340270684L;
	private String ID;
	private String Name;
	private String Description;
	private String IDType;

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

	public String getIDType() {
		return IDType;
	}

	public void setIDType(String iDType) {
		IDType = iDType;
	}
}
