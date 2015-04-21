package com.lvmama.comm.pet.vo;

import com.lvmama.comm.pet.po.search.ProductSearchInfo;


public class ProductSearchInfoHotel extends ProductSearchInfo {
	
	private static final long serialVersionUID = 1504382843225477617L;

	private Long hotelId;

	private Long productId;

	private String hotelRoom; // 房型

	private String nonSmokingRoom = "true"; // 是否是无烟房

	private String floor;// 楼层

	private String roomArea; // 房间面积

	private String roomGround;// 地面

	private String services;// 服务设施
	
	public Long getHotelId() {
		return hotelId;
	}

	public void setHotelId(Long hotelId) {
		this.hotelId = hotelId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getHotelRoom() {
		return hotelRoom;
	}

	public void setHotelRoom(String hotelRoom) {
		this.hotelRoom = hotelRoom;
	}

	public String getNonSmokingRoom() {
		return nonSmokingRoom;
	}

	public void setNonSmokingRoom(String nonSmokingRoom) {
		this.nonSmokingRoom = nonSmokingRoom;
	}

	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public String getRoomArea() {
		return roomArea;
	}

	public void setRoomArea(String roomArea) {
		this.roomArea = roomArea;
	}

	public String getRoomGround() {
		return roomGround;
	}

	public void setRoomGround(String roomGround) {
		this.roomGround = roomGround;
	}

	public String getServices() {
		return services;
	}

	public void setServices(String services) {
		this.services = services;
	}
}
