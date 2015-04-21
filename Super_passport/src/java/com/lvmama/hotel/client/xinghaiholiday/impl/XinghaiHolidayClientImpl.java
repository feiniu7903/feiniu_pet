package com.lvmama.hotel.client.xinghaiholiday.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;

import com.lvmama.comm.utils.MD5;
import com.lvmama.hotel.client.xinghaiholiday.XinghaiHolidayClient;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.xinghaiholiday.AdditionalProduct;
import com.lvmama.hotel.model.xinghaiholiday.Authorization;
import com.lvmama.hotel.model.xinghaiholiday.BookInfo;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

public class XinghaiHolidayClientImpl implements XinghaiHolidayClient {
	private static final Log log = LogFactory.getLog(XinghaiHolidayClientImpl.class);
	private static final String SUFFICIENT = "5+";
	private static final String INQUIRY = "O";
	private static final String FULL = "C";
	private static Map<String, OrderStatus> orderStatusMap;

	/**
	 * 获取指定酒店价格
	 */
	public List<RoomType> getHotelPrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("HotelID", Integer.valueOf(hotelCode));
		param.put("RoomID", roomTypeID);
		param.put("StartDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
		param.put("EndDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
		log.info("getHotelPrice request:" + param.toString());
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Hotel_Price");
		log.info("getHotelPrice response:" + response);
		return parseRoomTypeResponse(response);
	}

	/**
	 * 获取指定酒店房态
	 */
	public List<RoomType> getHotelRoomState(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("HotelID", Integer.valueOf(hotelCode));
		param.put("RoomID", roomTypeID);
		param.put("StartDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
		param.put("EndDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
		log.info("getHotelRoomState request:" + param.toString());
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Hotel_RoomState");
		log.info("getHotelRoomState response:" + response);
		return parseRoomStatusResponse(response);
	}

	/**
	 * 提交订单
	 */
	public OrderResponse bookAdd(BookInfo bookInfo) throws Exception {
		Authorization auth = Authorization.getInstance();
		String jsonBookInfo = toJSONBookInfo(bookInfo);
		String veryfyStr = MD5.encode16(auth.getCustomerID() + auth.getContactUser() + auth.getContactName() + auth.getBookType() + jsonBookInfo + auth.getCustomerKey());
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("ContactUser", auth.getContactUser());
		param.put("ContactName", auth.getContactName());
		param.put("BookType", Integer.valueOf(auth.getBookType()));
		param.put("BookInfo", jsonBookInfo);
		param.put("VeryfyStr", veryfyStr);
		log.info("bookAdd request:" + param);
		String WEBSERVICE_URL = WebServiceConstant.getProperties("xinghaiholiday.url");
		String response = WebServiceClient.call(WEBSERVICE_URL, param.values().toArray(), "Book_Add");
		log.info("bookAdd response:" + response);
		List<OrderResponse> orderResponse = parseOrderResponse(response);
		if (orderResponse != null) {
			return orderResponse.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取指定订单号订单明细[订单类型1：客户订单号，2 星海订单号]
	 */
	public OrderStatus getOrder(String orderID) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("OrderType", 1);
		param.put("orderID", orderID);
		log.info("getOrder request:" + param);
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Order");
		log.info("getOrder response:" + response);
		List<OrderStatus> orderStatus = parseOrderStatusResponse(response);
		if (orderStatus != null) {
			return orderStatus.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 订单申请取消[申请的类型:2 申请取消]
	 */
	public OrderStatus bookApply(String orderID) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("Action", 2);
		param.put("OrderID", orderID);
		param.put("ApplyContent", "取消订单");
		log.info("bookApply request:" + param);
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Book_Apply");
		log.info("bookApply response:" + response);
		List<OrderStatus> orderStatus = parseOrderStatusResponse(response);
		if (orderStatus != null) {
			return orderStatus.get(0);
		} else {
			return null;
		}
	}

	/**
	 * 获取指定酒店的所有附加产品
	 */
	public List<Append> getHotelInfo(String hotelId) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("HotelID", Integer.valueOf(hotelId));
		log.info("getHotelInfo request:" + param);
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Hotel_Info");
		log.info("getHotelInfo response:" + response);
		JSONObject additional = new JSONObject(response);
		if (additional.has("AdditionalProduct")) {
			List<Append> appendList = new ArrayList<Append>();
			JSONArray jsonArray = new JSONArray(additional.getString("AdditionalProduct"));
			for (int i = 0; i < jsonArray.length(); i++) {
				Append append = new Append();
				JSONObject addi = jsonArray.getJSONObject(i);
				append.setProductIdSupplier(addi.getString("productid"));// 附加产品Id
				append.setSettlementPrice(addi.getLong("price") * 100);// 产品单价
				append.setTimePriceDate(DateUtils.parseDate(addi.getString("startdate"), new String[] { "yyyy-MM-dd" }));// 开始时间
				append.setTimePriceDateEnd(DateUtils.parseDate(addi.getString("enddate"), new String[] { "yyyy-MM-dd" }));// 结束时间
				append.setHotelID(hotelId);
				appendList.add(append);
			}
			return appendList;
		}

		return null;
	}

	/**
	 * 获取指定供应商全部酒店信息
	 */
	public List<String> getHotelList() throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("LastAccessDate", "");
		log.info("getHotelList request:" + param);
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Hotel_List");
		JSONObject rsList = new JSONObject(response);
		if (rsList.has("HotelList")) {
			List<String> hotelIdList = new ArrayList<String>();
			JSONArray jsonArray = new JSONArray(rsList.getString("HotelList"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject hl = jsonArray.getJSONObject(i);
				if (hl.has("hotelid")) {
					String hotelId = hl.getString("hotelid");
					hotelIdList.add(hotelId);
				}
			}
			return hotelIdList;
		}
		return null;
	}

	/**
	 * 房型是否下线
	 */
	public boolean isRoomTypeOnline(String hotelCode, String roomTypeID) throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, Object> param = new LinkedHashMap<String, Object>();
		param.put("CustomerID", auth.getCustomerID());
		param.put("SignStr", auth.getSignStr());
		param.put("HotelID", Integer.valueOf(hotelCode));
		param.put("RoomID", roomTypeID);
		param.put("StartDate", "");
		param.put("EndDate", "");
		log.info("isRoomTypeOnline request:" + param);
		String response = WebServiceClient.call(WebServiceConstant.getProperties("xinghaiholiday.url"), param.values().toArray(), "Get_Hotel_RoomState");
		log.info("isRoomTypeOnline response:" + response);
		if (parseRoomStatusResponse(response) != null) {
			return true;
		}
		return false;
	}

	/**
	 * 解析查询订单返回信息
	 */
	public List<OrderResponse> parseOrderResponse(String response) throws Exception {
		JSONObject orderResponse = new JSONObject(response);
		if (orderResponse.has("OrderList")) {
			List<OrderResponse> orderResponseList = new ArrayList<OrderResponse>();
			JSONArray jsonArray = new JSONArray(orderResponse.getString("OrderList"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject order = jsonArray.getJSONObject(i);
				if (order.has("orderstate") && order.has("xhorderid")) {
					OrderResponse res = new OrderResponse(order.getString("xhorderid"), toOrderStatus(order.getString("orderstate")));
					orderResponseList.add(res);
				}
			}
			return orderResponseList;
		}
		return null;
	}

	/**
	 * 解析查询指定房型每一天的售价
	 */
	public List<RoomType> parseRoomTypeResponse(String response) throws Exception {
		JSONObject roomTypePriceInfo = new JSONObject(response);
		int RoomTypeCount = 0;
		if (roomTypePriceInfo.has("hotelid")) {
			String hotelId = (String) roomTypePriceInfo.get("hotelid");
			if (roomTypePriceInfo.has("RoomTypeCount")) {
				RoomTypeCount = roomTypePriceInfo.getInt("RoomTypeCount");
			}
			if (RoomTypeCount > 0) {
				List<RoomType> roomTypeList = null;
				String roomId = "";
				if (roomTypePriceInfo.has("RoomTypeList")) {
					JSONArray jsonArray = new JSONArray(roomTypePriceInfo.getString("RoomTypeList"));
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject rooType = jsonArray.getJSONObject(i);
						if (rooType.has("roomid")) {
							roomId = rooType.getString("roomid");
						}
						if (rooType.has("RoomPrice")) {
							JSONArray roomPrices = new JSONArray(rooType.getString("RoomPrice"));
							if (roomPrices.length() > 0) {
								roomTypeList = new ArrayList<RoomType>();
								for (int j = 0; j < roomPrices.length(); j++) {
									JSONObject roomPrice = roomPrices.getJSONObject(j);
									RoomType roomType = new RoomType();
									roomType.setHotelID(hotelId);
									roomType.setRoomTypeID(roomId);
									if (roomPrice.has("pfprice")) {
										roomType.setSettlementPrice(roomPrice.getLong("pfprice") * 100);
									}
									if (roomPrice.has("date")) {
										roomType.setTimePriceDate(DateUtils.parseDate(roomPrice.getString("date"), new String[] { "yyyy-MM-dd" }));
									}
									roomTypeList.add(roomType);
								}
							}
						}

					}
				}
				return roomTypeList;
			}
		}
		return null;
	}

	/**
	 * 将对象解析为String
	 */
	private String toJSONBookInfo(BookInfo bookInfo) throws Exception {
		JSONObject book = new JSONObject();
		JSONObject hotel = new JSONObject();
		JSONArray array = new JSONArray();
		hotel.put("orderid", bookInfo.getOrderId());
		hotel.put("hotelid", bookInfo.getHotelId());
		hotel.put("roomcount", bookInfo.getRoomCount());
		hotel.put("roomid", bookInfo.getRoomId());
		hotel.put("guestname", bookInfo.getGuestName());
		hotel.put("bedtype", bookInfo.getBedType());
		hotel.put("checkindate", bookInfo.getCheckinDate());
		hotel.put("checkoutdate", bookInfo.getCheckoutDate());
		for (AdditionalProduct addi : bookInfo.getAddiProdList()) {
			JSONObject obj = new JSONObject();
			obj.put("addiorderid", addi.getAddiOrderId());
			obj.put("addiproductid", addi.getAddiProductId());
			obj.put("addicount", addi.getAddiCount());
			obj.put("addisdate", addi.getAddieDate());
			obj.put("addiedate", addi.getAddisDate());
			array.put(obj);
		}
		book.put("Hotel", hotel);
		book.put("AdditionalProduct", array);
		return book.toString();
	}

	/**
	 * 解析查询订单状态返回信息
	 */
	public List<OrderStatus> parseOrderStatusResponse(String response) throws Exception {
		JSONObject orderList = new JSONObject(response);
		if (orderList.has("OrderList")) {
			List<OrderStatus> orderStatusList = new ArrayList<OrderStatus>();
			JSONArray jsonArray = new JSONArray(orderList.getString("OrderList"));
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject order = jsonArray.getJSONObject(i);
				if (order.has("orderstate")) {
					OrderStatus orderStatus = toOrderStatus(order.getString("orderstate"));
					orderStatusList.add(orderStatus);
				}
			}
			return orderStatusList;
		}
		return null;
	}

	/**
	 * 解析指定酒店的房态信息
	 */
	public List<RoomType> parseRoomStatusResponse(String response) throws Exception {
		JSONObject roomStatusInfo = new JSONObject(response);
		int RoomTypeCount = 0;
		if (roomStatusInfo.has("hotelid")) {
			String hotelId = (String) roomStatusInfo.get("hotelid");
			if (roomStatusInfo.has("RoomTypeCount")) {
				RoomTypeCount = roomStatusInfo.getInt("RoomTypeCount");
			}
			if (RoomTypeCount > 0) {
				if (roomStatusInfo.has("RoomTypeList")) {
					List<RoomType> roomTypeList = new ArrayList<RoomType>();
					JSONArray jsonArray = new JSONArray(roomStatusInfo.getString("RoomTypeList"));
					String roomId = "";
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject rooType = jsonArray.getJSONObject(i);
						if (rooType.has("roomid")) {
							roomId = rooType.getString("roomid");
						}
						if (rooType.has("RoomStates")) {
							JSONArray roomstates = new JSONArray(rooType.getString("RoomStates"));
							for (int j = 0; j < roomstates.length(); j++) {
								JSONObject roomPrice = roomstates.getJSONObject(j);
								RoomType roomType = new RoomType();
								roomType.setHotelID(hotelId);
								roomType.setRoomTypeID(roomId);
								if (roomPrice.has("roomstate")) {
									roomType.setDayStock(toDayStock(roomPrice.getString("roomstate")));
								}
								if (roomPrice.has("date")) {
									roomType.setTimePriceDate(DateUtils.parseDate(roomPrice.getString("date"), new String[] { "yyyy-MM-dd" }));
								}
								roomTypeList.add(roomType);
							}
						}
					}
					return roomTypeList;
				}
			}
		}
		return null;
	}

	private static OrderStatus toOrderStatus(String code) {
		if (orderStatusMap == null) {
			orderStatusMap = new HashMap<String, OrderStatus>();
			orderStatusMap.put("1", new OrderStatus("1", "已取消"));
			orderStatusMap.put("2", new OrderStatus("2", "待确认"));
			orderStatusMap.put("3", new OrderStatus("3", "已担保"));
			orderStatusMap.put("4", new OrderStatus("4", "可担保"));
			orderStatusMap.put("5", new OrderStatus("5", "已完成"));
		}
		return orderStatusMap.get(code);
	}

	private static Long toDayStock(String code) {
		if (SUFFICIENT.equals(code)) {
			return -1L;
		} else if (INQUIRY.equals(code)) {
			return 0L;
		} else if (FULL.equals(code)) {
			return 0L;
		} else {
			return Long.valueOf(code);
		}
	}
}
