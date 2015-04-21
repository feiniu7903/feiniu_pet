package com.lvmama.clutter.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.json.JSONArray;

public class MobileHotelRule {
	private Date arrivalDate;
	private Date departureDate;
	private int countDays;
	private String guaranteeDescription;
	private String tips;
	private Double totalPrice;
	private Double guaranteePrice;
	private String guaranteeType;
	private String title;
	private String customerType;
	private JSONArray visitorJsonArray;
	private List<MobileHotelBookingRule> bookingRules = new ArrayList<MobileHotelBookingRule>();
	private List<MobileHotelGuaranteeRule> guaranteeRules = new ArrayList<MobileHotelGuaranteeRule>();

	public Date getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(Date arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public Date getDepartureDate() {
		return departureDate;
	}

	public void setDepartureDate(Date departureDate) {
		this.departureDate = departureDate;
	}

	public int getCountDays() {
		return countDays;
	}

	public void setCountDays(int countDays) {
		this.countDays = countDays;
	}

	public String getGuaranteeDescription() {
		return guaranteeDescription;
	}

	public void setGuaranteeDescription(String guaranteeDescription) {
		this.guaranteeDescription = guaranteeDescription;
	}

	public String getTips() {
		return tips;
	}

	public void setTips(String tips) {
		this.tips = tips;
	}

	public List<MobileHotelBookingRule> getBookingRules() {
		return bookingRules;
	}

	public void setBookingRules(List<MobileHotelBookingRule> bookingRules) {
		this.bookingRules = bookingRules;
	}

	public List<MobileHotelGuaranteeRule> getGuaranteeRules() {
		return guaranteeRules;
	}

	public void setGuaranteeRules(List<MobileHotelGuaranteeRule> guaranteeRules) {
		this.guaranteeRules = guaranteeRules;
	}

	public Double getGuaranteePrice() {
		return guaranteePrice;
	}

	public void setGuaranteePrice(Double guaranteePrice) {
		this.guaranteePrice = guaranteePrice;
	}

	public String getGuaranteeType() {
		return guaranteeType;
	}

	public void setGuaranteeType(String guaranteeType) {
		this.guaranteeType = guaranteeType;
	}

	public Double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCustomerType() {
		return customerType;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public JSONArray getVisitorJsonArray() {
		return visitorJsonArray;
	}

	public void setVisitorJsonArray(JSONArray visitorJsonArray) {
		this.visitorJsonArray = visitorJsonArray;
	}

}
