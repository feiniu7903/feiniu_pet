package com.lvmama.hotel.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 附加费用
 */
public class Append {
	/** 房型ID */
	private String roomTypeID;
	/** 酒店ID */
	private String hotelID;
	/** 附加费用ID */
	private String productIdSupplier;
	/** 价格开始时间或适用时间 */
	private Date timePriceDate;
	/** 结算价 */
	private Long settlementPrice;
	/** 份数 */
	private Long quantity;
	/** 价格结束时间 */
	private Date timePriceDateEnd;

	private List<String> tokens = new ArrayList<String>();

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

	public void setProductIdSupplier(String productIdSupplier) {
		this.productIdSupplier = productIdSupplier;
	}

	public String getProductIdSupplier() {
		if (tokens.size() > 0) {
			productIdSupplier = tokens.get(0);
			if (tokens.size() > 1) {
				StringBuffer sb = new StringBuffer();
				sb.append(productIdSupplier);
				for (int i = 1; i < tokens.size(); i++) {
					sb.append(",").append(tokens.get(i));
				}
				productIdSupplier = sb.toString();
			}
		}
		return productIdSupplier;
	}

	public String getProductTypeSupplier() {
		return hotelID + "," + roomTypeID;
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

	public Long getQuantity() {
		return quantity;
	}

	public void setQuantity(Long quantity) {
		this.quantity = quantity;
	}

	public Date getTimePriceDateEnd() {
		return timePriceDateEnd;
	}

	public void setTimePriceDateEnd(Date timePriceDateEnd) {
		this.timePriceDateEnd = timePriceDateEnd;
	}

	public void addToken(String token) {
		tokens.add(token);
	}

	@Override
	public String toString() {
		return "Append [roomTypeID=" + roomTypeID + ", hotelID=" + hotelID
				+ ", productIdSupplier=" + getProductIdSupplier()
				+ ", timePriceDate=" + timePriceDate + ", settlementPrice="
				+ settlementPrice + ", quantity=" + quantity
				+ ", timePriceDateEnd=" + timePriceDateEnd + "]";
	}
}
