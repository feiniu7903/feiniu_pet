package com.lvmama.hotel.client.longtengjielv.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.utils.TemplateUtils;
import com.lvmama.hotel.client.longtengjielv.LongtengjielvClient;
import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.longtengjielv.Authorization;
import com.lvmama.hotel.model.longtengjielv.BookingInfo;
import com.lvmama.passport.utils.WebServiceClient;
import com.lvmama.passport.utils.WebServiceConstant;

public class LongtengjielvClientImpl implements LongtengjielvClient {
	private static final Log log = LogFactory.getLog(LongtengjielvClientImpl.class);
	
	private static final String WEBSERVICE_URL = WebServiceConstant.getProperties("longtengjielv.url");
	private static final String WEBSERVICE_METHOD = "GetXmlData";
	private static final String BASE_TEMPLATE_DIR = "/com/lvmama/hotel/template/longtengjielv/";
	private static final String SUCCESS = "30000";
	private static final String NO_DATA_FOUND = "S_010";
	
	/**
	 * 查询指定酒店所有上线房型
	 * 
	 * @param hotelCode 酒店编码
	 * @return 在线房型ID列表
	 */
	public List<String> simplifyRoomTypeInfo(String hotelCode) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("orderField", "HotelCode");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "simplifyRoomTypeInfo.xml", model);
		log.info("simplifyRoomTypeInfo reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("simplifyRoomTypeInfo resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			return TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypeInfo/RoomTypeList/RoomTypeID");
		}
		
		return null;
	}
	
	/**
	 * 查询指定房型每一天的售价
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 * @param currency 币种,CNY:人民币,HKD:港币,MOP:澳门元,TWD:台币,USD:美元
	 */
	public List<RoomType> simplifyRoomTypePriceInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate, String currency) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("roomTypeID", roomTypeID);
		model.put("startPriceDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
		model.put("endPriceDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
		model.put("currency", currency);
		model.put("orderField", "weightNum");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "simplifyRoomTypePriceInfo.xml", model);
		log.info("simplifyRoomTypePriceInfo reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("simplifyRoomTypePriceInfo resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			List<String> roomTypeIDList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypePriceInfo/RoomTypePriceList/RoomTypeID");
			List<String> priceDateList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypePriceInfo/RoomTypePriceList/PriceDate");
			List<String> salesPriceList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypePriceInfo/RoomTypePriceList/SalesPrice");
			List<String> inAdvanceDayNumList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypePriceInfo/RoomTypePriceList/InAdvanceDayNum");
			List<String> cancelDayList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomTypePriceInfo/RoomTypePriceList/CancelDay");
			
			List<RoomType> roomTypeList = new ArrayList<RoomType>();
			for (int i = 0; i < roomTypeIDList.size(); i++) {
				RoomType roomType = new RoomType();
				roomType.setRoomTypeID(roomTypeIDList.get(i));
				roomType.setHotelID(hotelCode);
				roomType.setTimePriceDate(DateUtils.parseDate(priceDateList.get(i), new String[]{"yyyy-MM-dd"}));
				roomType.setSettlementPrice(Long.valueOf(salesPriceList.get(i)) * 100);
				if (StringUtils.isNotBlank(inAdvanceDayNumList.get(i))) {
					roomType.setAheadHour(Long.valueOf(inAdvanceDayNumList.get(i)) * 24*60);
				}
				if (StringUtils.isNotBlank(cancelDayList.get(i))) {
					roomType.setCancelHour(Long.valueOf(cancelDayList.get(i)) * 24*60);
				}
				roomTypeList.add(roomType);
			}
			return roomTypeList;
		}
		
		return null;
	}
	
	/**
	 * 查询指定房型每一天的房态
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 */
	public List<RoomType> simplifyRoomStatusInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("roomTypeID", roomTypeID);
		model.put("startPriceDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
		model.put("endPriceDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
		model.put("orderField", "weightNum");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "simplifyRoomStatusInfo.xml", model);
		log.info("simplifyRoomStatusInfo reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("simplifyRoomStatusInfo resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			List<String> roomTypeIDList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomStatusInfo/RoomStatusList/RoomTypeID");
			List<String> roomStatusDateList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomStatusInfo/RoomStatusList/RoomStatusDate");
			List<String> roomStatusList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/RoomStatusInfo/RoomStatusList/RoomStatus");
			
			List<RoomType> roomTypeList = new ArrayList<RoomType>();
			for (int i = 0; i < roomTypeIDList.size(); i++) {
				RoomType roomType = new RoomType();
				roomType.setRoomTypeID(roomTypeIDList.get(i));
				roomType.setHotelID(hotelCode);
				roomType.setTimePriceDate(DateUtils.parseDate(roomStatusDateList.get(i), new String[]{"yyyy-MM-dd"}));
				roomType.setDayStock(toDayStock(roomStatusList.get(i)));
				roomTypeList.add(roomType);
			}
			return roomTypeList;
		}
		
		return null;
	}
	
	private static final String SUFFICIENT = "F";
	private static final String INQUIRY = "R";
	private static final String INSUFFICIENT = "T";
	private static final String FULL = "C";
	
	private Long toDayStock(String code) {
		if (SUFFICIENT.equals(code)) {
			return -1L;
		} else if (INQUIRY.equals(code)) {
			return 0L;
		} else if (INSUFFICIENT.equals(code)) {
			return 0L;
		} else if (FULL.equals(code)) {
			return 0L;
		} else {
			return Long.valueOf(code);
		}
	}
	
	/**
	 * 查询指定房型每一天的附加费用
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 * @param currency 币种,CNY:人民币,HKD:港币,MOP:澳门元,TWD:台币,USD:美元
	 */
	public List<Append> simplifyRoomPriceAppendInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate, String currency) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("roomTypeID", roomTypeID);
		model.put("startPriceDate", DateFormatUtils.format(startDate, "yyyy-MM-dd"));
		model.put("endPriceDate", DateFormatUtils.format(endDate, "yyyy-MM-dd"));
		model.put("currency", currency);
		model.put("orderField", "weightNum");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "simplifyRoomPriceAppendInfo.xml", model);
		log.info("simplifyRoomPriceAppendInfo reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("simplifyRoomPriceAppendInfo resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			List<String> categoryCodeList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/PriceAppendInfo/PriceAppendList/CategoryCode");//附加费用类型
			List<String> nameList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/PriceAppendInfo/PriceAppendList/Name");//早餐类型
			List<String> appendPriceList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/PriceAppendInfo/PriceAppendList/AppendPrice");
			List<String> appendDateList = TemplateUtils.getElementValues(resXml, "//CNResponse/Data/PriceAppendInfo/PriceAppendList/AppendDate");
			
			List<Append> appendList = new ArrayList<Append>();
			for (int i = 0; i < categoryCodeList.size(); i++) {
				Append append = new Append();
				append.setHotelID(hotelCode);
				append.setRoomTypeID(roomTypeID);
				append.addToken(categoryCodeList.get(i));
				if ("28001".equals(categoryCodeList.get(i))) {// 28001：加餐
					append.addToken(nameList.get(i));
				}
				append.setSettlementPrice(Long.valueOf(appendPriceList.get(i)) * 100);
				append.setTimePriceDate(DateUtils.parseDate(appendDateList.get(i), new String[]{"yyyy-MM-dd"}));
				appendList.add(append);
			}
			return appendList;
		}
		
		return null;
	}

	/**
	 * 查询指定酒店是否上线
	 * 
	 * @param hotelCode 酒店编码
	 */
	public boolean isHotelOnline(String hotelCode) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("orderField", "ID");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "singleSearchHotelsInfo.xml", model);
		log.info("singleSearchHotelsInfo reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("singleSearchHotelsInfo resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (NO_DATA_FOUND.equals(code)) {
			return false;
		}
		
		return true;
	}

	/**
	 * 查询指定房型是否上线
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 */
	public boolean isRoomTypeOnline(String hotelCode, String roomTypeID) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("hotelCode", hotelCode);
		model.put("roomTypeCode", roomTypeID);
		model.put("orderField", "ID");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "singleHotelRoomTypeSearch.xml", model);
		log.info("singleHotelRoomTypeSearch.xml reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("singleHotelRoomTypeSearch.xml resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (NO_DATA_FOUND.equals(code)) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * 下单
	 */
	public String newHotelBooking(BookingInfo bookingInfo) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("bookingInfo", bookingInfo);
		model.put("orderField", "ID");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "newHotelBooking.xml", model);
		log.info("newHotelBooking reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("newHotelBooking resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			return TemplateUtils.getElementValue(resXml, "//CNResponse/Data/OrderID");
		}
		
		return null;
	}

	/**
	 * 查询订单
	 */
	public OrderStatus newOrderSearch(String orderID) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("orderID", orderID);
		model.put("orderField", "orderDate");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "newOrderSearch.xml", model);
		log.info("newOrderSearch reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("newOrderSearch resXml: " + resXml);
		
		String code = TemplateUtils.getElementValue(resXml, "//CNResponse/MessageInfo/Code");
		if (SUCCESS.equals(code)) {
			String orderStates = TemplateUtils.getElementValue(resXml, "//CNResponse/Data/OrderInfoList/OrderInfo/OrderStates");
			return toOrderStatus(orderStates);
		}
		
		return null;
	}
	
	private static Map<String, OrderStatus> orderStatusMap;
	
	
	private OrderStatus toOrderStatus(String code) {
		if (orderStatusMap == null) {
			orderStatusMap = new HashMap<String, OrderStatus>();
			orderStatusMap.put("8", new OrderStatus("8", "已取消"));
			orderStatusMap.put("9", new OrderStatus("9", "新单 ，等待处理"));
			orderStatusMap.put("10", new OrderStatus("10", "房价、房态已确认"));
			orderStatusMap.put("15/25", new OrderStatus("15/25", "订单处理中"));
			orderStatusMap.put("15", new OrderStatus("15", "订单处理中"));
			orderStatusMap.put("25", new OrderStatus("25", "订单处理中"));
			orderStatusMap.put("27", new OrderStatus("27", "可入住"));
		}
		return orderStatusMap.get(code);
	}
	
	/**
	 * 取消订单
	 */
	public void newOnlineCancel_Orders(String orderID) throws Exception {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("authorization", Authorization.getInstance());
		model.put("orderID", orderID);
		model.put("orderField", "ID");
		
		String reqXml = TemplateUtils.fillFileTemplate(BASE_TEMPLATE_DIR, "newOnlineCancel_Orders.xml", model);
		log.info("newOnlineCancel_Orders reqXml: " + reqXml);
		
		String resXml = WebServiceClient.call(WEBSERVICE_URL, new Object[]{reqXml}, WEBSERVICE_METHOD);
		log.info("newOnlineCancel_Orders resXml: " + resXml);
	}
}
