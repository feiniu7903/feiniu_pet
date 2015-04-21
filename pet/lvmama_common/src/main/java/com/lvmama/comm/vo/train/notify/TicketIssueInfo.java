package com.lvmama.comm.vo.train.notify;

import com.lvmama.comm.utils.PriceUtil;

/**
 * 出票通知车票信息
 * @author XuSemon
 * @date 2013-8-6
 */
public class TicketIssueInfo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 394713947132589234L;
	/**
	 * 车票编号
	 */
	private int ticket_id;
	/**
	 * 乘客姓名
	 */
	private String passenger_name;
	/**
	 * 车票坐席，例如204（二等座）
	 */
	private int ticket_class;
	/**
	 * 车票类型，例如301（成人票）
	 */
	private int ticket_type;
	/**
	 * 车票价格
	 */
	private String ticket_price;
	/**
	 * 座位号，例如10车厢12A
	 */
	private String ticket_seat;
	/**
	 * 证件类型，例如401（身份证）
	 */
	private int id_type;
	/**
	 * 证件号码，例如320123191201234567
	 */
	private String id_num;
	public int getTicket_id() {
		return ticket_id;
	}
	public void setTicket_id(int ticket_id) {
		this.ticket_id = ticket_id;
	}
	public String getPassenger_name() {
		return passenger_name;
	}
	public void setPassenger_name(String passenger_name) {
		this.passenger_name = passenger_name;
	}
	public int getTicket_class() {
		return ticket_class;
	}
	public void setTicket_class(int ticket_class) {
		this.ticket_class = ticket_class;
	}
	public int getTicket_type() {
		return ticket_type;
	}
	public void setTicket_type(int ticket_type) {
		this.ticket_type = ticket_type;
	}
	
	public String getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(String ticket_price) {
		this.ticket_price = ticket_price;
	}
	public String getTicket_seat() {
		return ticket_seat;
	}
	public void setTicket_seat(String ticket_seat) {
		this.ticket_seat = ticket_seat;
	}
	public int getId_type() {
		return id_type;
	}
	public void setId_type(int id_type) {
		this.id_type = id_type;
	}
	public String getId_num() {
		return id_num;
	}
	public void setId_num(String id_num) {
		this.id_num = id_num;
	}
	
	public long getPriceFen(){
		return PriceUtil.convertToFen(ticket_price);
	}
}
