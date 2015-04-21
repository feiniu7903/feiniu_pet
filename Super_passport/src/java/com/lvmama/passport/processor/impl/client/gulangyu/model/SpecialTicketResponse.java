package com.lvmama.passport.processor.impl.client.gulangyu.model;

import java.util.List;

/**
 * 访问鼓浪屿查询特殊门票接口返回对象(节假日特价票)
 * 
 * @author lipengcheng
 * 
 */
public class SpecialTicketResponse {

	/** 特殊门票列表 */
	private List<SpecialTicket> specialTicketList;

	public List<SpecialTicket> getSpecialTicketList() {
		return specialTicketList;
	}

	public void setSpecialTicketList(List<SpecialTicket> specialTicketList) {
		this.specialTicketList = specialTicketList;
	}
}
