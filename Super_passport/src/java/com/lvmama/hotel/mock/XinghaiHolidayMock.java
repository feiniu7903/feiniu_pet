package com.lvmama.hotel.mock;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;

public class XinghaiHolidayMock {
	private static final OrderStatus status1 = new OrderStatus("1", "已取消");
	private static final OrderStatus status2 = new OrderStatus("2", "待确认");
	private static final OrderStatus status3 = new OrderStatus("3", "已担保");
	private static final OrderStatus status4 = new OrderStatus("4", "可担保");
	private static final OrderStatus status5 = new OrderStatus("5", "已完成");

	private static final Map<String, OrderStatus> map = new HashMap<String, OrderStatus>();
	private static final Map<String, Boolean> map2 = new HashMap<String, Boolean>();
	private static final Map<String, Boolean> map3 = new HashMap<String, Boolean>();
	
	private XinghaiHolidayClient xinghaiHolidayClient;
	
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

	public List<RoomType> getRoomStatusJson(String hotelId,String roomId) throws Exception{
		String response = "{\"hotelid\": \""+hotelId+"\",\"RoomTypeList\": [{\"roomid\": \""+roomId+"\",\"RoomStates\": [{\"date\": \"2013-04-10\",\"roomstate\": \"5+\"},{\"date\": \"2013-04-11\",\"roomstate\": \"5+\"},{\"date\": \"2013-04-12\",\"roomstate\": \"5+\"},{\"date\": \"2013-04-13\",\"roomstate\": \"5+\"},{\"date\": \"2013-04-14\",\"roomstate\": \"5+\"},{\"date\": \"2013-04-15\",\"roomstate\": \"5+\"}]}],\"RoomTypeCount\": \"1\"}";
		return xinghaiHolidayClient.parseRoomStatusResponse(response);
	}

	public List<RoomType> getRoomTypePriceJson(String hotelId,String roomId)  throws Exception{
		String response = "{\"hotelid\": \""+hotelId+"\",\"RoomTypeList\": [{\"roomid\": \""+roomId+"\",\"RoomPrice\": [{\"date\": \"2013-04-10\",\"pfprice\": \"100\"}, {\"date\": \"2013-04-11\",\"pfprice\": \"100\"}, {\"date\": \"2013-04-12\",\"pfprice\": \"100\"}, {\"date\": \"2013-04-13\",\"pfprice\": \"100\"}, {\"date\": \"2013-04-14\",\"pfprice\": \"100\"}, {\"date\": \"2013-04-15\",\"pfprice\": \"100\"}]}],\"RoomTypeCount\": \"1\"}";
		return xinghaiHolidayClient.parseRoomTypeResponse(response);
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

	public void setXinghaiHolidayClient(XinghaiHolidayClient xinghaiHolidayClient) {
		this.xinghaiHolidayClient = xinghaiHolidayClient;
	}
}
