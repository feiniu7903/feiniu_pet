package com.lvmama.passport.songcity.model;

public final class TicketOrderInfo {
	private String idCard;
	private String linkman;
	private String mobile;
	private String outOrderNo;//我方订单号
	private String orderNo;//合作方订单号
	private TicketOrderDetail ticketOrderDetail;
	
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getLinkman() {
		return linkman;
	}
	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getOutOrderNo() {
		return outOrderNo;
	}
	public void setOutOrderNo(String outOrderNo) {
		this.outOrderNo = outOrderNo;
	}
	public TicketOrderDetail getTicketOrderDetail() {
		return ticketOrderDetail;
	}
	public void setTicketOrderDetail(TicketOrderDetail ticketOrderDetail) {
		this.ticketOrderDetail = ticketOrderDetail;
	}

	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	@Override
	public String toString() {
		return "TicketOrderInfo [idCard=" + idCard + ", linkman=" + linkman
				+ ", mobile=" + mobile + ", outOrderNo=" + outOrderNo
				+ ", ticketOrderDetail=" + ticketOrderDetail + "]";
	}
}
