package com.lvmama.hotel.model.xinghaiholiday;

import java.util.Date;
import java.util.List;

public class BookInfo {
	private String orderId;// 订单号
	private int hotelId;// 星海假期酒店唯一标识号
	private int roomId;// 星海假期酒店房型唯一标识号
	private Date checkinDate;// 入住日期
	private Date checkoutDate;// 离店日期
	private int roomCount; // 预订间数
	private String guestName;// 入住人姓名
	private int bedType; // 床型：1 大床；2 双床；4 套间
	private List<AdditionalProduct> addiProdList;// 附加产品

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public Date getCheckinDate() {
		return checkinDate;
	}

	public void setCheckinDate(Date checkinDate) {
		this.checkinDate = checkinDate;
	}

	public Date getCheckoutDate() {
		return checkoutDate;
	}

	public void setCheckoutDate(Date checkoutDate) {
		this.checkoutDate = checkoutDate;
	}

	public int getHotelId() {
		return hotelId;
	}

	public void setHotelId(int hotelId) {
		this.hotelId = hotelId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(int roomCount) {
		this.roomCount = roomCount;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public int getBedType() {
		return bedType;
	}

	public void setBedType(int bedType) {
		this.bedType = bedType;
	}

	public List<AdditionalProduct> getAddiProdList() {
		return addiProdList;
	}

	public void setAddiProdList(List<AdditionalProduct> addiProdList) {
		this.addiProdList = addiProdList;
	}

}
