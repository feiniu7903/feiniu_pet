package com.lvmama.comm.abroad.vo.response;

import java.util.ArrayList;
import java.util.List;


public class RoomDetailsRes extends ErrorRes {
	private static final long serialVersionUID = 1L;
	private String ID;//房间id
	private String IDRoomType;//房间类型
	private String Description;//房间描述
	private String NumAdults;//可住成人数
	private String NumChildren;//可住儿童数
	private List<DayBean> days = new ArrayList<DayBean>();//房间每天的详细信息
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
	public List<DayBean> getDays() {
		return days;
	}
	public void setDays(List<DayBean> days) {
		this.days = days;
	}
	
}
