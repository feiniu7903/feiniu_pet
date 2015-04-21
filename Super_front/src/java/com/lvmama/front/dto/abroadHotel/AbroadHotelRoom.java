package com.lvmama.front.dto.abroadHotel;

public class AbroadHotelRoom {

	/**
	 * 房间ID
	 */
	private String id;
	
	/**
	 * 酒店ID
	 */
	private String hotelId;
	/**
	 * 入住时间
	 */
	private String checkIn;
	
	/**
	 * 离店时间
	 */
	private String checkOut;
	
	/**
	 * 几晚
	 */
	private Integer days;
	
	/**
	 * 房型
	 */
	private String roomType;
	
	/**
	 * 售价
	 */
	private String price;
	
	/**
	 * 几间
	 */
	private Integer quantity;
	
	/**
	 * 小计
	 */
	private Integer countPrice;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHotelId() {
		return hotelId;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
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

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getCountPrice() {
		return countPrice;
	}

	public void setCountPrice(Integer countPrice) {
		this.countPrice = countPrice;
	}
}
