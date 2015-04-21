package com.lvmama.shholiday.vo.order;

import java.util.List;

public class OrderDetailInfo {

	private String orderStatus;
	private String bookDate;
	private String hadOutTicket;
	private String isExpress;
	private String externalOrderNo;
	private String uniqueID;
	private String orderModifyAfterAmount;
	private String priceArithmetic;
	
	private Contact contact;
	private List<OrderPassenger> passengers;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getBookDate() {
		return bookDate;
	}
	public void setBookDate(String bookDate) {
		this.bookDate = bookDate;
	}
	public String getHadOutTicket() {
		return hadOutTicket;
	}
	public void setHadOutTicket(String hadOutTicket) {
		this.hadOutTicket = hadOutTicket;
	}
	public String getIsExpress() {
		return isExpress;
	}
	public void setIsExpress(String isExpress) {
		this.isExpress = isExpress;
	}
	public String getExternalOrderNo() {
		return externalOrderNo;
	}
	public void setExternalOrderNo(String externalOrderNo) {
		this.externalOrderNo = externalOrderNo;
	}
	
	public String getUniqueID() {
		return uniqueID;
	}
	public void setUniqueID(String uniqueID) {
		this.uniqueID = uniqueID;
	}
	public String getOrderModifyAfterAmount() {
		return orderModifyAfterAmount;
	}
	public void setOrderModifyAfterAmount(String orderModifyAfterAmount) {
		this.orderModifyAfterAmount = orderModifyAfterAmount;
	}
	public String getPriceArithmetic() {
		return priceArithmetic;
	}
	public void setPriceArithmetic(String priceArithmetic) {
		this.priceArithmetic = priceArithmetic;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	public List<OrderPassenger> getPassengers() {
		return passengers;
	}
	public void setPassengers(List<OrderPassenger> passengers) {
		this.passengers = passengers;
	}
	
	public boolean isBooking(Long orderId){
		boolean isOrderNo = new String(""+orderId).equalsIgnoreCase(getExternalOrderNo());
		boolean isNormalOrder = "HK".equalsIgnoreCase(this.getOrderStatus())||"BRR".equalsIgnoreCase(this.getOrderStatus());
		 return isOrderNo&&isNormalOrder;
	}
}
