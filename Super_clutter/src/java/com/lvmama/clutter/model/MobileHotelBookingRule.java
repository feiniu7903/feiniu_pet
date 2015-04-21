package com.lvmama.clutter.model;

import java.io.Serializable;

import com.lvmama.elong.model.EnumBookingRule;

public class MobileHotelBookingRule implements Serializable {
	private static final long serialVersionUID = 2411073909119350516L;
	private EnumBookingRule typeCode;
	private int numberOfRooms;
	private int startHour;
	private int endHour;
	private String description;
	public MobileHotelBookingRule() {
		super();
	}
	public MobileHotelBookingRule(String description) {
		super();
		this.description = description;
	}
	public EnumBookingRule getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(EnumBookingRule typeCode) {
		this.typeCode = typeCode;
	}
	public int getNumberOfRooms() {
		return numberOfRooms;
	}
	public void setNumberOfRooms(int numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	
}
