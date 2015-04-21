package com.lvmama.hotel.model;

import java.util.Date;
import java.util.List;

/**
 * 订单请求参数
 */
public class OrderRequest {
	/** 附加费用 */
	private List<Append> appendList;
	/** 酒店ID */
	private String hotelID;
	/** 房型ID */
	private String roomTypeID;
	/** 入住日期 */
	private Date checkInDate;
	/** 离店日期 */
	private Date checkOutDate;
	/** 订购数量 */
	private Long quantity;
	/** 币种 */
	private String currency;
	/** 游客姓名 */
	private String contactName;
	/** 游客姓名拼音*/
	private String contactPinyin;
	/** 游客电话 */
	private String contactPhone;
	/** 订单号 */
	private String orderId;
	/** 床型 */
	private String bedType;

	public List<Append> getAppendList() {
		return appendList;
	}

	public void setAppendList(List<Append> appendList) {
		this.appendList = appendList;
	}

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public String getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(String roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	public Date getCheckInDate() {
		return checkInDate;
	}

	public void setCheckInDate(Date checkInDate) {
		this.checkInDate = checkInDate;
	}

	public Date getCheckOutDate() {
		return checkOutDate;
	}

	public void setCheckOutDate(Date checkOutDate) {
		this.checkOutDate = checkOutDate;
	}

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String ordPersonName) {
		this.contactName = ordPersonName;
	}

	public String getContactPinyin() {
		return contactPinyin;
	}

	public void setContactPinyin(String contactPinyin) {
		this.contactPinyin = contactPinyin;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactMobile(String ordPersonMobile) {
		this.contactPhone = ordPersonMobile;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBedType() {
		return bedType;
	}

	public void setBedType(String bedType) {
		this.bedType = bedType;
	}

	@Override
	public String toString() {
		return "OrderRequest [appendList=" + appendList + ", hotelID="
				+ hotelID + ", roomTypeID=" + roomTypeID + ", checkInDate="
				+ checkInDate + ", checkOutDate=" + checkOutDate
				+ ", quantity=" + quantity + ", currency=" + currency
				+ ", contactName=" + contactName + ",contactPinyin="+contactPinyin+", contactPhone="
				+ contactPhone + ", orderId=" + orderId + ", bedType="
				+ bedType + "]";
	}
}
