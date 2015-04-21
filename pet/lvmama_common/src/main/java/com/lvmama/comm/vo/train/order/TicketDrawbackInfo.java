package com.lvmama.comm.vo.train.order;

public class TicketDrawbackInfo {
	/**
	 * 车票编号
	 */
	private int ticket_id;
	/**
	 * 车票价格
	 */
	private float ticket_price;
	public TicketDrawbackInfo(){}
	public TicketDrawbackInfo(int ticket_id, float ticket_price){
		this.ticket_id = ticket_id;
		this.ticket_price = ticket_price;
	}
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public float getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(float ticket_price) {
		this.ticket_price = ticket_price;
	}
}
