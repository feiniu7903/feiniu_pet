package com.lvmama.comm.abroad.vo.response;

import java.util.Date;


public class ReservationsOrderRoomDetailRes  extends ErrorRes{
    /**
	 * 
	 */
	private static final long serialVersionUID = -2897610477903506417L;
	private Long id;
    private Long abroadhotelId;
    private String roomId;
    private int roomNumber;
    private String roomType;
    private String roomtypeDesc;
    private Long price;
    private Long adults;
    private Long children;
    private Date createTime;
    private Long salePrice;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getAbroadhotelId() {
		return abroadhotelId;
	}
	public void setAbroadhotelId(Long abroadhotelId) {
		this.abroadhotelId = abroadhotelId;
	}
	public String getRoomId() {
		return roomId;
	}
	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}
	public String getRoomType() {
		return roomType;
	}
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}
	public String getRoomtypeDesc() {
		return roomtypeDesc;
	}
	public void setRoomtypeDesc(String roomtypeDesc) {
		this.roomtypeDesc = roomtypeDesc;
	}
	public Long getPrice() {
		return price;
	}
	public void setPrice(Long price) {
		this.price = price;
	}
	public Long getAdults() {
		return adults;
	}
	public void setAdults(Long adults) {
		this.adults = adults;
	}
	public Long getChildren() {
		return children;
	}
	public void setChildren(Long children) {
		this.children = children;
	}
	public int getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(int roomNumber) {
		this.roomNumber = roomNumber;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getSalePrice() {
		return salePrice;
	}
	public void setSalePrice(Long salePrice) {
		this.salePrice = salePrice;
	}
}
