package com.lvmama.hotel.mock;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.hotel.model.OrderStatus;

public class LongtengjielvMock {
	private static final OrderStatus status1 = new OrderStatus("8", "已取消");
	private static final OrderStatus status2 = new OrderStatus("9", "新单 ，等待处理");
	private static final OrderStatus status3 = new OrderStatus("10", "房价 、房态已确认");
	private static final OrderStatus status4 = new OrderStatus("15/25", "订单处理中");
	private static final OrderStatus status5 = new OrderStatus("27", "可入住");
	
	private static final Map<String, OrderStatus> map = new HashMap<String, OrderStatus>();
	private static final Map<String, Boolean> map2 = new HashMap<String, Boolean>();
	private static final Map<String, Boolean> map3 = new HashMap<String, Boolean>();

	public OrderStatus queryOrderMock(String partnerOrderId) {
		OrderStatus status = map.get(partnerOrderId);
		if (status == null) {
			map.put(partnerOrderId, status2);
			status = status2;
		} else if (status.equals(status2)) {
			map.put(partnerOrderId, status3);
			status = status3;
		} else if (status.equals(status3)) {
			map.put(partnerOrderId, status4);
			status = status4;
		} else if (status.equals(status4)) {
			map.put(partnerOrderId, status5);
			status = status5;
		} else if (status.equals(status5)) {
			status = status5;
		}
		return status;
	}
	
	public OrderStatus cancelOrderMock() {
		return status1;
	}
	
	public boolean isHotelOnline(String hotelCode) {
		Boolean online = map2.get(hotelCode);
		if (online == null) {
			map2.put(hotelCode, true);
			online = true;
		} else if (online == true) {
			map2.put(hotelCode, false);
			online = false;
		} else if (online == false) {
			map2.put(hotelCode, true);
			online = true;
		}
		return online;
	}
	
	public boolean isRoomTypeOnline(String hotelCode, String roomTypeID) {
		String key = hotelCode + "_" + roomTypeID;
		Boolean online = map3.get(key);
		if (online == null) {
			map3.put(key, true);
			online = true;
		} else if (online == true) {
			map3.put(key, false);
			online = false;
		} else if (online == false) {
			map3.put(key, true);
			online = true;
		}
		return online;
	}
	
	public static void main(String[] args) {
		LongtengjielvMock mock = new LongtengjielvMock();
		System.out.println(mock.queryOrderMock("123456"));
		System.out.println(mock.queryOrderMock("123456"));
		System.out.println(mock.queryOrderMock("123456"));
		System.out.println(mock.queryOrderMock("123456"));
		System.out.println(mock.queryOrderMock("123456"));
		
		System.out.println(mock.queryOrderMock("654321"));
		System.out.println(mock.queryOrderMock("654321"));
		
		System.out.println(mock.isHotelOnline("654321"));
		System.out.println(mock.isHotelOnline("654321"));
		System.out.println(mock.isHotelOnline("654321"));
		
		System.out.println(mock.isRoomTypeOnline("654321", "1"));
		System.out.println(mock.isRoomTypeOnline("654321", "1"));
		System.out.println(mock.isRoomTypeOnline("654321", "1"));
		
		System.out.println(mock.isRoomTypeOnline("654321", "2"));
		System.out.println(mock.isRoomTypeOnline("654321", "2"));
	}
}
