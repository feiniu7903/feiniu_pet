package com.lvmama.hotel.service.xinghaiholiday;

import java.util.List;

import com.lvmama.hotel.model.OrderResponse;

public interface XinghaiHolidayUpdateHotelOrderStatusService {

	public void updateOrderStatus(List<OrderResponse> orderlist) throws Exception;

}
