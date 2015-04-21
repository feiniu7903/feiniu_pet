package com.lvmama.comm.booking.po;

import java.io.Serializable;
import java.util.Date;

public class BookingDetails implements Serializable {

	private String guest_city;
	private Integer id;
	private Date arrival_date;
	private Integer creditslip_id;
	private Date creation_datetime;
	private Double totalcost;
	private String guest_country;
	private Date fee_calculation_date;
	private Integer affiliate_id;
	private String status;
	private Double fee;
	private Integer pincode;
	private Integer fee_percentage;
	private String affiliate_label;
	private Integer hotel_id;
	private Date departure_date;
	private Double commission;
	private String language;
	private Double euro_fee;
	private String currencycode;
	public String getGuest_city() {
		return guest_city;
	}
	public void setGuest_city(String guest_city) {
		this.guest_city = guest_city;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getArrival_date() {
		return arrival_date;
	}
	public void setArrival_date(Date arrival_date) {
		this.arrival_date = arrival_date;
	}
	public Integer getCreditslip_id() {
		return creditslip_id;
	}
	public void setCreditslip_id(Integer creditslip_id) {
		this.creditslip_id = creditslip_id;
	}
	public Date getCreation_datetime() {
		return creation_datetime;
	}
	public void setCreation_datetime(Date creation_datetime) {
		this.creation_datetime = creation_datetime;
	}
	public Double getTotalcost() {
		return totalcost;
	}
	public void setTotalcost(Double totalcost) {
		this.totalcost = totalcost;
	}
	public String getGuest_country() {
		return guest_country;
	}
	public void setGuest_country(String guest_country) {
		this.guest_country = guest_country;
	}
	public Date getFee_calculation_date() {
		return fee_calculation_date;
	}
	public void setFee_calculation_date(Date fee_calculation_date) {
		this.fee_calculation_date = fee_calculation_date;
	}
	public Integer getAffiliate_id() {
		return affiliate_id;
	}
	public void setAffiliate_id(Integer affiliate_id) {
		this.affiliate_id = affiliate_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Integer getPincode() {
		return pincode;
	}
	public void setPincode(Integer pincode) {
		this.pincode = pincode;
	}
	public Integer getFee_percentage() {
		return fee_percentage;
	}
	public void setFee_percentage(Integer fee_percentage) {
		this.fee_percentage = fee_percentage;
	}
	public String getAffiliate_label() {
		return affiliate_label;
	}
	public void setAffiliate_label(String affiliate_label) {
		this.affiliate_label = affiliate_label;
	}
	public Integer getHotel_id() {
		return hotel_id;
	}
	public void setHotel_id(Integer hotel_id) {
		this.hotel_id = hotel_id;
	}
	public Date getDeparture_date() {
		return departure_date;
	}
	public void setDeparture_date(Date departure_date) {
		this.departure_date = departure_date;
	}
	public Double getCommission() {
		return commission;
	}
	public void setCommission(Double commission) {
		this.commission = commission;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public Double getEuro_fee() {
		return euro_fee;
	}
	public void setEuro_fee(Double euro_fee) {
		this.euro_fee = euro_fee;
	}
	public String getCurrencycode() {
		return currencycode;
	}
	public void setCurrencycode(String currencycode) {
		this.currencycode = currencycode;
	}

}
