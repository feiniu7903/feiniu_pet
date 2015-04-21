package com.lvmama.hotel.service.longtengjielv;

import java.util.Date;

public interface LongtengjielvProductService {
	/**
	 * 更新所有酒店下的所有房型及其下所有附加产品的时间价格及库存
	 */
	public void updateRoomTypesAndAdditionals(Date startDate, Date endDate) throws Exception;

	/**
	 * 更新指定酒店及其房型产品的时间价格
	 */
	public void updateRoomTypeTimePrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;

	/**
	 * 更新指定酒店及其房型产品的时间库存
	 */
	public void updateRoomTypeTimeStock(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;

	/**
	 * 更新指定酒店及其房型下的所有附加产品的时间价格
	 */
	public void updateAdditionalTimePrice(String hotelCode, String roomTypeID, Date startDate, Date endDate) throws Exception;

	/**
	 * 上下线指定的酒店产品
	 */
	public void onOfflineHotel(String hotelCode) throws Exception;

	/**
	 * 上下线指定酒店下的指定房型产品
	 */
	public void onOfflineRoomType(String hotelCode, String roomTypeID) throws Exception;

}