package com.lvmama.hotel.client.longtengjielv;

import java.util.Date;
import java.util.List;

import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.longtengjielv.BookingInfo;

public interface LongtengjielvClient {
	/**
	 * 查询指定酒店所有上线房型
	 * 
	 * @param hotelCode 酒店编码
	 * @return 在线房型ID列表
	 */
	public List<String> simplifyRoomTypeInfo(String hotelCode) throws Exception;
	
	/**
	 * 查询指定房型每一天的售价
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 * @param currency 币种,CNY:人民币,HKD:港币,MOP:澳门元,TWD:台币,USD:美元
	 */
	public List<RoomType> simplifyRoomTypePriceInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate, String currency) throws Exception;
	
	/**
	 * 查询指定房型每一天的房态
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 */
	public List<RoomType> simplifyRoomStatusInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;
	
	/**
	 * 查询指定房型每一天的附加费用
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 * @param currency 币种,CNY:人民币,HKD:港币,MOP:澳门元,TWD:台币,USD:美元
	 */
	public List<Append> simplifyRoomPriceAppendInfo(String hotelCode, String roomTypeID, Date startDate, Date endDate, String currency) throws Exception;
	
	/**
	 * 查询指定酒店是否上线
	 * 
	 * @param hotelCode 酒店编码
	 */
	public boolean isHotelOnline(String hotelCode) throws Exception;
	
	/**
	 * 查询指定房型是否上线
	 * 
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 */
	public boolean isRoomTypeOnline(String hotelCode, String roomTypeID) throws Exception;
	
	/**
	 * 下单
	 */
	public String newHotelBooking(BookingInfo bookingInfo) throws Exception;
	
	/**
	 * 查询订单
	 */
	public OrderStatus newOrderSearch(String orderID) throws Exception;
	
	/**
	 * 取消订单
	 */
	public void newOnlineCancel_Orders(String orderID) throws Exception;
	
}
