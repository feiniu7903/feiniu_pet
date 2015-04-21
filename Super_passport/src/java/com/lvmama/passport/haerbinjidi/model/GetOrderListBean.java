package com.lvmama.passport.haerbinjidi.model;

public class GetOrderListBean extends HeaderBean{
	private String startPlayDate;
	private String endPlayDate;
	private String ticketTypeId;
	private String serialId;
	private String travelerName;
	private String travelerMobile;
	private String identityCard;
	private String page;
	private String pageSize;
	public String getStartPlayDate() {
		return startPlayDate;
	}
	public void setStartPlayDate(String startPlayDate) {
		this.startPlayDate = startPlayDate;
	}
	public String getEndPlayDate() {
		return endPlayDate;
	}
	public void setEndPlayDate(String endPlayDate) {
		this.endPlayDate = endPlayDate;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getSerialId() {
		return serialId;
	}
	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}
	public String getTravelerName() {
		return travelerName;
	}
	public void setTravelerName(String travelerName) {
		this.travelerName = travelerName;
	}
	public String getTravelerMobile() {
		return travelerMobile;
	}
	public void setTravelerMobile(String travelerMobile) {
		this.travelerMobile = travelerMobile;
	}
	public String getIdentityCard() {
		return identityCard;
	}
	public void setIdentityCard(String identityCard) {
		this.identityCard = identityCard;
	}
	public String getPage() {
		return page;
	}
	public void setPage(String page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	
}
