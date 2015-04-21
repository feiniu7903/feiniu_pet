package com.lvmama.passport.processor.impl.client.gulangyu.model;

import java.util.List;

/**
 * 鼓浪屿返回的门票信息对象
 * 
 * @author lipengcheng
 * 
 */
public class TicketResponse {

	private List<Ticket> ticketList;

	public List<Ticket> getTicketList() {
		return ticketList;
	}

	public void setTicketList(List<Ticket> ticketList) {
		this.ticketList = ticketList;
	}

}
