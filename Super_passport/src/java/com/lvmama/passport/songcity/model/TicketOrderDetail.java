package com.lvmama.passport.songcity.model;

public final class TicketOrderDetail {
	private String amount;
	private String ticketCode;
	private String travelDate;
	
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
	public String getTravelDate() {
		return travelDate;
	}
	public void setTravelDate(String travelDate) {
		this.travelDate = travelDate;
	}
	@Override
	public String toString() {
		return "TicketOrderDetail [amount=" + amount
				+ ", ticketCode=" + ticketCode + ", travelDate=" + travelDate
				+ "]";
	}
}
