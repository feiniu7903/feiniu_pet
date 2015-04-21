package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class TicketBookRspVo extends RspVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4587962154L;
	
	public TicketBookRspVo(){}
	public TicketBookRspVo(List<TicketBookInfo> ticketBookInfos){
		this.ticketBookInfos = ticketBookInfos;
	}

	/**
	 * 预订车票信息
	 */
	private List<TicketBookInfo> ticketBookInfos;
	public List<TicketBookInfo> getTicketBookInfos() {
		return ticketBookInfos;
	}
	public void setTicketBookInfos(List<TicketBookInfo> ticketBookInfos) {
		this.ticketBookInfos = ticketBookInfos;
	}
}
