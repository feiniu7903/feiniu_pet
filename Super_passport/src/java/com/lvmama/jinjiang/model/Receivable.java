package com.lvmama.jinjiang.model;

import java.math.BigDecimal;
/**
 * 订单应收
 * @author chenkeke
 *
 */
public class Receivable {
	private String priceCode;
	private BigDecimal reservationPrice;
	private Integer copies;
	public String getPriceCode() {
		return priceCode;
	}
	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}
	public BigDecimal getReservationPrice() {
		return reservationPrice;
	}
	public void setReservationPrice(BigDecimal reservationPrice) {
		this.reservationPrice = reservationPrice;
	}
	public Integer getCopies() {
		return copies;
	}
	public void setCopies(Integer copies) {
		this.copies = copies;
	}
	public void setCopiesByLong(Long copies) {
		if(copies!=null){
			this.copies = Integer.parseInt(""+copies);
		}
	}
	public void setReservationPriceByLong(Long reservationPrice) {
		if(reservationPrice!=null)
		this.reservationPrice = new BigDecimal(reservationPrice).divide(new BigDecimal(100)).setScale(2);
	}
}
