package com.lvmama.comm.vo.train.notify;

public class TicketDrawbackInfo {
	/**
	 * 车票编号
	 */
	private int ticket_id;
	/**
	 * 车票价格
	 */
	private float ticket_price;
	/**
	 * 退票结果：0-退票成功；其他-退票失败
	 */
	private int ticket_status;
	/**
	 * 退票结果说明
	 */
	private String ticket_msg;
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
	public int getTicket_status() {
		return ticket_status;
	}
	public void setTicket_status(int ticket_status) {
		this.ticket_status = ticket_status;
	}
	public String getTicket_msg() {
		return ticket_msg;
	}
	public void setTicket_msg(String ticket_msg) {
		this.ticket_msg = ticket_msg;
	}
}
