package com.lvmama.hotel.service;

public interface HotelOrderService {

	public String submitOrder(Long orderId);

	public String cancelOrder(Long orderId);
	
	public void addComLog(String content, Long orderId);
}
