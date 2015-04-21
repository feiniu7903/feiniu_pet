package com.lvmama.hotel.model.longtengjielv;

import java.util.List;

public class BookingInfo {
	/** 附加费用 */
	private List<AddInfo> addInfoList;
	/** 酒店 ID */
	private String hotelid;
	/** 房型 ID */
	private String roomID;
	/** 入住时间 */
	private String inDate;
	/** 离店时间 */
	private String outDate;
	/** 房间数量 */
	private String roomCount;
	/** 币种 */
	private String currency;
	/** 预订人电话 */
	private String bookPhone;
	/** 预订人名称 */
	private String bookName;
	/** 宾客名称 */
	private String guestName;
	/** 宾客电话 */
	private String guestPhoneFax;
	/** 宾客类型 */
	private String guestType;
	/** 最早到店时间 */
	private String earlyDate;
	/** 最晚到店时间 */
	private String lateDate;
	/** 特殊要求 */
	private String specialRemark;

	public List<AddInfo> getAddInfoList() {
		return addInfoList;
	}

	public void setAddInfoList(List<AddInfo> addInfoList) {
		this.addInfoList = addInfoList;
	}

	public String getHotelid() {
		return hotelid;
	}

	public void setHotelid(String hotelid) {
		this.hotelid = hotelid;
	}

	public String getRoomID() {
		return roomID;
	}

	public void setRoomID(String roomID) {
		this.roomID = roomID;
	}

	public String getInDate() {
		return inDate;
	}

	public void setInDate(String inDate) {
		this.inDate = inDate;
	}

	public String getOutDate() {
		return outDate;
	}

	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}

	public String getRoomCount() {
		return roomCount;
	}

	public void setRoomCount(String roomCount) {
		this.roomCount = roomCount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getBookPhone() {
		return bookPhone;
	}

	public void setBookPhone(String bookPhone) {
		this.bookPhone = bookPhone;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getGuestName() {
		return guestName;
	}

	public void setGuestName(String guestName) {
		this.guestName = guestName;
	}

	public String getGuestPhoneFax() {
		return guestPhoneFax;
	}

	public void setGuestPhoneFax(String guestPhoneFax) {
		this.guestPhoneFax = guestPhoneFax;
	}

	public String getGuestType() {
		return guestType;
	}

	public void setGuestType(String guestType) {
		this.guestType = guestType;
	}

	public String getEarlyDate() {
		return earlyDate;
	}

	public void setEarlyDate(String earlyDate) {
		this.earlyDate = earlyDate;
	}

	public String getLateDate() {
		return lateDate;
	}

	public void setLateDate(String lateDate) {
		this.lateDate = lateDate;
	}

	public String getSpecialRemark() {
		return specialRemark;
	}

	public void setSpecialRemark(String specialRemark) {
		this.specialRemark = specialRemark;
	}
}