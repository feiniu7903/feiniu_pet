package com.lvmama.passport.processor.impl.client.renwoyou.model;
import java.util.List;

public class OrderItem {
	private String id;// 购买景点门票的ID，
	private String qty;// 购买产品的数量，
	private String ticketType;// 取值为“GROUP”和“PERSONAL”，分别代表团体票和个人票，团体票购买多张，
	private String startDate;// 出发时间，当景点startDateFlag的值为“1”时，必须制定，格式yyyy-MM-dd，且必须门票的要求，提前预定。
	private List<Guest> gustList;//游客信息
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getQty() {
		return qty;
	}
	public void setQty(String qty) {
		this.qty = qty;
	}
	public String getTicketType() {
		return ticketType;
	}
	public void setTicketType(String ticketType) {
		this.ticketType = ticketType;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public List<Guest> getGustList() {
		return gustList;
	}
	public void setGustList(List<Guest> gustList) {
		this.gustList = gustList;
	}

}
