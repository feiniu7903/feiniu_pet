package com.lvmama.hotel.model;

import java.util.Date;

/**
 * 房型
 */
public class RoomType {
	/** 房型ID */
	private String roomTypeID;
	/** 酒店ID */
	private String hotelID;
	/** 价格时间 */
	private Date timePriceDate;
	/** 结算价 */
	private Long settlementPrice;
	/** 是否资源需确认 */
	private boolean resourceConfirm;
	/** 日库存量 */
	private Long dayStock;
	/** 提前预定小时数 */
	private Long aheadHour = 1440L;
	/** 最晚取消小时数 */
	private Long cancelHour = 0L;

	public String getRoomTypeID() {
		return roomTypeID;
	}

	public void setRoomTypeID(String roomTypeID) {
		this.roomTypeID = roomTypeID;
	}

	public String getHotelID() {
		return hotelID;
	}

	public void setHotelID(String hotelID) {
		this.hotelID = hotelID;
	}

	public Date getTimePriceDate() {
		return timePriceDate;
	}

	public void setTimePriceDate(Date timePriceDate) {
		this.timePriceDate = timePriceDate;
	}

	public Long getSettlementPrice() {
		return settlementPrice;
	}

	public void setSettlementPrice(Long settlementPrice) {
		this.settlementPrice = settlementPrice;
	}

	public boolean isResourceConfirm() {
		if (dayStock != null && (dayStock > 0 || dayStock == -1)) {
			resourceConfirm = false;
		} else {
			resourceConfirm = true;
		}
		return resourceConfirm;
	}

	public Long getDayStock() {
		return dayStock;
	}

	public void setDayStock(Long dayStock) {
		this.dayStock = dayStock;
	}

	public Long getAheadHour() {
		return (aheadHour == 0) ? 1440L : aheadHour;
	}

	public void setAheadHour(Long aheadHour) {
		this.aheadHour = aheadHour;
	}

	public Long getCancelHour() {
		return cancelHour;
	}

	public void setCancelHour(Long cancelHour) {
		this.cancelHour = cancelHour;
	}

	@Override
	public String toString() {
		return "RoomType [roomTypeID=" + roomTypeID + ", hotelID=" + hotelID
				+ ", timePriceDate=" + timePriceDate + ", settlementPrice="
				+ settlementPrice + ", resourceConfirm=" + resourceConfirm
				+ ", dayStock=" + dayStock + ", aheadHour=" + aheadHour
				+ ", cancelHour=" + cancelHour + "]";
	}
}
