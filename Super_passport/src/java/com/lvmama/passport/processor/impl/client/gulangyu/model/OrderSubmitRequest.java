package com.lvmama.passport.processor.impl.client.gulangyu.model;

/**
 * 申码请求对象
 * @author lipengcheng
 *
 */
public class OrderSubmitRequest {
	
	/**景区ID号*/
	private String uuLid;
	
	/** 门票ID号*/
	private String uuTid;
	
	/** 订单号*/
	private String orderId;
	
	/** 游玩时间,格式：2012-03-16)*/
	private String visitTime;
	
	/** 门票数*/
	private String ticketNum;
	
	/** 单张门票价格*/
	private String ticketPrice;
	
	/** 取票人姓名*/
	private String customerName;
	
	/** 取票人手机*/
	private String mobile;
	
	/** 下单时间*/
	private String createTime;

	/** 支付方式*/
	private String paymentType;
	
	/** 支付状态*/
	private String paymentStatus;
	
	/** 订单总金额*/
	private String totalAmount;
	
	/** 联票类型0（非联票），1（联票）*/
	private String unionType;
	
	/** */
	private String unionId;
	
	/** 会员ID*/
	private String memberId;

	public String getUuLid() {
		return uuLid;
	}

	public void setUuLid(String uuLid) {
		this.uuLid = uuLid;
	}

	public String getUuTid() {
		return uuTid;
	}

	public void setUuTid(String uuTid) {
		this.uuTid = uuTid;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getTicketNum() {
		return ticketNum;
	}

	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}

	public String getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(String ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getUnionType() {
		return unionType;
	}

	public void setUnionType(String unionType) {
		this.unionType = unionType;
	}

	public String getUnionId() {
		return unionId;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	
	
}
