package com.lvmama.hotel.support;

import java.util.List;

import com.lvmama.hotel.model.Append;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderRequest;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.model.RoomType;
import com.lvmama.hotel.model.TimePriceRequest;

public interface HotelOrderServiceSupport {
	/**
	 * 下单
	 */
	public OrderResponse submitOrder(OrderRequest orderRequest) throws Exception;

	/**
	 * 查询订单
	 */
	public OrderStatus queryOrder(String partnerOrderID) throws Exception;

	/**
	 * 取消订单
	 */
	public OrderStatus cancelOrder(String partnerOrderID) throws Exception;

	/**
	 * 查询指定房型每一天的售价
	 */
	public List<RoomType> queryRoomTypeTimePrice(TimePriceRequest timePriceRequest) throws Exception;

	/**
	 * 查询指定房型每一天的附加费用
	 */
	public List<Append> queryAppendTimePrice(TimePriceRequest timePriceRequest) throws Exception;
}
