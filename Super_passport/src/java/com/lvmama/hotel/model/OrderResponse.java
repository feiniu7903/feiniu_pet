package com.lvmama.hotel.model;

public class OrderResponse {
	private String partnerOrderID;
	private OrderStatus orderStatus;

	public OrderResponse(String partnerOrderID, OrderStatus orderStatus) {
		this.partnerOrderID = partnerOrderID;
		this.orderStatus = orderStatus;
	}

	public String getPartnerOrderID() {
		return partnerOrderID;
	}

	public void setPartnerOrderID(String partnerOrderID) {
		this.partnerOrderID = partnerOrderID;
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Override
	public String toString() {
		return "OrderResponse [partnerOrderID=" + partnerOrderID
				+ ", orderStatus=" + orderStatus + "]";
	}
}
