package com.lvmama.comm.abroad.vo.request;

import java.io.Serializable;
import java.util.List;


public class DownOrderReq implements Serializable {
	private static final long serialVersionUID = 1L;
	private String userID;//用户ID
	private String checkIn;//入住日期
	private String checkOut;//离开日期
	private String stayNights;//住的晚数
	private String hotelId; //酒店ID
	private List<Room> rooms;//订的房间信息
	private long totalPrice;//下单总价
	private String user_memo;//用户订单备注
	private List<Adult> adults;//成人用户信息
	private List<Child> child;//小孩用户信息
	public String getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getCheckIn() {
		return checkIn;
	}
	public void setCheckIn(String checkIn) {
		this.checkIn = checkIn;
	}
	public String getCheckOut() {
		return checkOut;
	}
	public void setCheckOut(String checkOut) {
		this.checkOut = checkOut;
	}
	public String getStayNights() {
		return stayNights;
	}
	public void setStayNights(String stayNights) {
		this.stayNights = stayNights;
	}
	
	
	
	public long getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getUser_memo() {
		return user_memo;
	}
	public void setUser_memo(String user_memo) {
		this.user_memo = user_memo;
	}
	public List<Adult> getAdults() {
		return adults;
	}
	public void setAdults(List<Adult> adults) {
		this.adults = adults;
	}
	public List<Child> getChild() {
		return child;
	}
	public void setChild(List<Child> child) {
		this.child = child;
	}
	public List<Room> getRooms() {
		return rooms;
	}
	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}
	public String getHotelId() {
		return hotelId;
	}
	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}
	
	

}
