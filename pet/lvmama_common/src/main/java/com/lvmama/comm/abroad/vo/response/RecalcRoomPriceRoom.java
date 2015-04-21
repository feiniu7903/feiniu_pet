package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;
import java.util.List;

public class RecalcRoomPriceRoom implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2512195417301065422L;
	private String ID;
	private String IDRoomType;
	private String Description;
	private String NumAdults;
	private String NumChildren;
	private String Quantity;
	private List<RecalcRoomPriceDay> days;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getIDRoomType() {
		return IDRoomType;
	}
	public void setIDRoomType(String iDRoomType) {
		IDRoomType = iDRoomType;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getNumAdults() {
		return NumAdults;
	}
	public void setNumAdults(String numAdults) {
		NumAdults = numAdults;
	}
	public String getNumChildren() {
		return NumChildren;
	}
	public void setNumChildren(String numChildren) {
		NumChildren = numChildren;
	}
	public String getQuantity() {
		return Quantity;
	}
	public void setQuantity(String quantity) {
		Quantity = quantity;
	}
	public List<RecalcRoomPriceDay> getDays() {
		return days;
	}
	public void setDays(List<RecalcRoomPriceDay> days) {
		this.days = days;
	}
}
