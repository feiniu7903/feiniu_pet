package com.lvmama.hotel.service.xinghaiholiday;

import java.util.Date;
import java.util.List;

import com.lvmama.hotel.model.RoomType;

public interface XinghaiHolidayProductService {
	/**
	 * 更新所有酒店下的所有房型产品的时间价格及库存
	 */
	public void updateRoomTypes(Date startDate, Date endDate) throws Exception;

	/**
	 * 更新某个酒店下的所有房型产品的时间价格
	 */
	public void updateRoomTypeTimePrice(List<RoomType> roomTypeList) throws Exception;

	/**
	 * 更新某个酒店下的所有房型产品的时间库存
	 */
	public void updateRoomTypeTimeStock(List<RoomType> roomTypeList) throws Exception;

	/**
	 * 更新所有酒店下的所有附加产品的时间价格
	 */
	public void updateAdditionalTimePrice(Date startDate, Date endDate) throws Exception;

	/**
	 * 上下线所有酒店产品
	 */
	public void onOffLineHotels() throws Exception;

	/**
	 * 上下线所有酒店下的所有房型产品
	 */
	public void onOffLineRoomTypes() throws Exception;

}