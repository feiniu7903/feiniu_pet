package com.lvmama.comm.booking.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BookingOrder implements Serializable {
	
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
	private String hotel_name;
	private String hotel_countrycode;
	private String hotel_city;
	private Date departure_date;
	private Double commission;
	private String language;
	private Double euro_fee;
	private String currencycode;
	
	SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	
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
	public String getArrival_date_str() {
		String arrival_date_str = format1.format(arrival_date);
		return arrival_date_str;
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
	public String getCreation_datetime_str() {
		String creation_datetime_str = format2.format(creation_datetime);
		return creation_datetime_str;
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
	public String getFee_calculation_date_str() {
		String fee_calculation_date_str = format1.format(fee_calculation_date);
		return fee_calculation_date_str;
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
		if("F".equals(status)){
			status = "完成"; 
		}else if("C".equals(status)){
			status = "取消"; 
		}
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
	public String getHotel_name() {
		return hotel_name;
	}
	public void setHotel_name(String hotel_name) {
		this.hotel_name = hotel_name;
	}
	public String getHotel_countrycode() {
		return hotel_countrycode;
	}
	public void setHotel_countrycode(String hotel_countrycode) {
		this.hotel_countrycode = hotel_countrycode;
	}
	public String getHotel_city() {
		return hotel_city;
	}
	public void setHotel_city(String hotel_city) {
		this.hotel_city = hotel_city;
	}
	public Date getDeparture_date() {
		return departure_date;
	}
	public String getDeparture_date_str() {
		String departure_date_str = format1.format(departure_date);
		return departure_date_str;
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
