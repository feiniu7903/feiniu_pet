package com.lvmama.comm.abroad.vo.response;

import java.io.Serializable;

public class RecalcRoomPriceReservation implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1281402781045919023L;
	/**
	 * 
	 */
	private String AvailStatus;
	private Long TotalPrice;
	private Long Supplements;
	private String Currency;
	public String getAvailStatus() {
		return AvailStatus;
	}
	public void setAvailStatus(String availStatus) {
		AvailStatus = availStatus;
	}
	public Long getTotalPrice() {
		return TotalPrice;
	}
	public void setTotalPrice(Long totalPrice) {
		TotalPrice = totalPrice;
	}
	public Long getSupplements() {
		return Supplements;
	}
	public void setSupplements(Long supplements) {
		Supplements = supplements;
	}
	public String getCurrency() {
		return Currency;
	}
	public void setCurrency(String currency) {
		Currency = currency;
	}
}
