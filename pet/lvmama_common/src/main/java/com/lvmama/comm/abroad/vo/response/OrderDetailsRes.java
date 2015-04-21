package com.lvmama.comm.abroad.vo.response;

public class OrderDetailsRes extends ErrorRes {
	private static final long serialVersionUID = 1L;
	private String IDorder;//订单ID
	private String ProductName;//预订产品
	private String TotalAmount;//应付金额
	private String CheckIn;//下单时间
	private String PayWay;//支付方式
	private String OrderStatus;//订单状态
	public String getIDorder() {
		return IDorder;
	}
	public void setIDorder(String iDorder) {
		IDorder = iDorder;
	}
	public String getProductName() {
		return ProductName;
	}
	public void setProductName(String productName) {
		ProductName = productName;
	}
	public String getTotalAmount() {
		return TotalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		TotalAmount = totalAmount;
	}
	public String getCheckIn() {
		return CheckIn;
	}
	public void setCheckIn(String checkIn) {
		CheckIn = checkIn;
	}
	public String getPayWay() {
		return PayWay;
	}
	public void setPayWay(String payWay) {
		PayWay = payWay;
	}
	public String getOrderStatus() {
		return OrderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		OrderStatus = orderStatus;
	}
	
	

}
