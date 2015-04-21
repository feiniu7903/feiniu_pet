package com.lvmama.comm.vo.train.order;

public class OrderPassengerInfo {
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
	 * 车票价格，单位元，例如89.5
	 */
	private float ticket_price;
	/**
	 * 证件类型，例如401（身份证）
	 */
	private int id_type;
	/**
	 * 证件号码，例如320123191201234567
	 */
	private String id_num;
	/**
	 * 电话号码，例如13812345678
	 */
	private String phone_num;
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
	public float getTicket_price() {
		return ticket_price;
	}
	public void setTicket_price(float ticket_price) {
		this.ticket_price = ticket_price;
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
	public String getPhone_num() {
		return phone_num;
	}
	public void setPhone_num(String phone_num) {
		this.phone_num = phone_num;
	}
}
