package com.lvmama.passport.processor.impl.client.fangte.model;

public class Order {

	/** 公园编号 */
	private String parkid;
	/** 取票码,游客凭此代码到窗口取票 , 由合作方提供 */
	private String ticketcode;
	/** 预订入园日期,如 2012-04-01 */
	private String plandate;
	/** 带队类型id(使用网上自驾游类别 9) */
	private String exetypeid;
	/** 票号*/
	private String ticketTypeId;
	/** 票数量*/
	private String num;
	/** 订单ID*/
	private String orderId;
	/** 产品价格,取驴妈妈售价*/
	private String price;
	
	private String comboTypeId;
	
	private String nameList;
	
	private String cardIdList;
	
	private String phone;
	
	/** 订票列表,如:全价票2人每张175元,儿童票3人每张123元 写成 "T0*2*175,T1*3*123" */
	public String getTicketlist() {
		return this.ticketTypeId + "*" + this.num + "*" + this.price+ "*" + this.cardIdList + "*" +nameList;
	}
	
	/** 票餐同订 */
	public String getTicketAndCombo() {
		return this.ticketTypeId + "*" + this.num + "*" + this.price + "*" + this.cardIdList + "*" +this.nameList + "," + this.comboTypeId + "*" + this.num + "*" + this.price + "*" +this.cardIdList + "*" + this.nameList;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getTicketTypeId() {
		return ticketTypeId;
	}
	public void setTicketTypeId(String ticketTypeId) {
		this.ticketTypeId = ticketTypeId;
	}
	public String getParkid() {
		return parkid;
	}
	public void setParkid(String parkid) {
		this.parkid = parkid;
	}
	public String getTicketcode() {
		return ticketcode;
	}
	public void setTicketcode(String ticketcode) {
		this.ticketcode = ticketcode;
	}
	public String getPlandate() {
		return plandate;
	}
	public void setPlandate(String plandate) {
		this.plandate = plandate;
	}
	public String getExetypeid() {
		return exetypeid;
	}
	public void setExetypeid(String exetypeid) {
		this.exetypeid = exetypeid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getComboTypeId() {
		return comboTypeId;
	}

	public void setComboTypeId(String comboTypeId) {
		this.comboTypeId = comboTypeId;
	}

	public String getNameList() {
		return nameList;
	}

	public void setNameList(String nameList) {
		this.nameList = nameList;
	}

	public String getCardIdList() {
		return cardIdList;
	}

	public void setCardIdList(String cardIdList) {
		this.cardIdList = cardIdList;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	

}
