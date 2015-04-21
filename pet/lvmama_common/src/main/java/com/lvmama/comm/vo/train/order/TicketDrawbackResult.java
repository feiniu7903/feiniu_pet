package com.lvmama.comm.vo.train.order;

public class TicketDrawbackResult {
	/**
	 * 车票编号
	 */
	private int ticket_id;
	/**
	 * 车票处理状态
	 */
	private int ticket_status;
	/**
	 * 车票处理信息
	 */
	private String ticket_msg;
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
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
	
	@Override
	public String toString(){
		return "ticket_id:" + ticket_id
			+ "|ticket_status:" + ticket_status
			+ "|ticket_msg:" + ticket_msg;
	}
}
