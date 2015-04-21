package com.lvmama.hotel.client.xinghaiholiday;
import java.util.Date;
import java.util.List;

import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.xinghaiholiday.BookInfo;

public interface XinghaiHolidayClient {
	/**
	 * 获取指定酒店价格
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 */
	public List<RoomType> getHotelPrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;
	
	/**
	 * 获取指定酒店房态
	 * @param hotelCode 酒店编码
	 * @param roomTypeID 房型编码
	 * @param startDate 价格起始日期yyyy-MM-dd
	 * @param endDate 价格终止日期yyyy-MM-dd
	 */
	public List<RoomType> getHotelRoomState(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;
	
    /**
     * 提交订单
     * @param bookInfo
     * @return
     * @throws Exception
     */
	public OrderResponse bookAdd(BookInfo bookInfo) throws Exception;
	
	/**
	 *
	 * 获取指定订单号订单明细
	 * @param orderID 对方订单号
	 * @return
	 * @throws Exception
	 */
	public OrderStatus getOrder(String orderID) throws Exception;
	
	/**
	 * 订单申请申请的类型1 申请更改；2 申请取消
	 * @param orderID 对方订单号
	 * @return
	 * @throws Exception
	 */
	public OrderStatus bookApply(String orderID) throws Exception;
	
	/**
	 * 获取指定酒店基本信息对应的附加产品
	 * @param hotelId
	 * @return
	 * @throws Exception
	 */
	public List<Append> getHotelInfo(String hotelId)throws Exception;
	
	/**
	 * 获取指定供应商对应的全部酒店
	 * @return
	 * @throws Exception
	 */
	public List<String> getHotelList()throws Exception;
	
	/**
	 * 验证指定酒店指定房型是否上线
	 * @param hotelCode
	 * @param roomTypeID
	 * @return
	 * @throws Exception
	 */
	public boolean isRoomTypeOnline(String hotelCode, String roomTypeID) throws Exception;
	
	public List<RoomType> parseRoomTypeResponse(String result) throws Exception;
	
	public List<RoomType> parseRoomStatusResponse(String result) throws Exception;
	
	public List<OrderResponse> parseOrderResponse(String result) throws Exception;
}
