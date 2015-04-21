package com.lvmama.hotel.service.xinghaiholiday.impl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
import com.lvmama.hotel.model.OrderResponse;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.service.HotelOrderService;
import com.lvmama.hotel.service.xinghaiholiday.XinghaiHolidayUpdateHotelOrderStatusService;
import com.lvmama.passport.utils.WebServiceConstant;

public class XinghaiHolidayUpdateHotelOrderStatusServiceImpl implements XinghaiHolidayUpdateHotelOrderStatusService {
	private OrderHotelService orderHotelService;
	private HotelOrderService hotelOrderService;
	@Override
	public void updateOrderStatus(List<OrderResponse> orderList) throws Exception {
		String supplierId = WebServiceConstant.getProperties("xinghaiholiday.supplierId");
		for (OrderResponse order : orderList) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("hotelSupplierId", supplierId);
			params.put("partnerOrderId", order.getPartnerOrderID());
			params.put("excludedStatusCodes", new String[] { "1", "5" });
			List<OrdOrderHotel> ordHotelList = orderHotelService.searchOrderHotelByParam(params);// 1已取消,5已完成
			OrderStatus orderStatus = order.getOrderStatus();
			for (OrdOrderHotel ordHotel : ordHotelList) {
				if (orderStatus != null && !StringUtils.equals(ordHotel.getStatusCode(), orderStatus.getStatusCode())) {
					ordHotel.setStatusCode(orderStatus.getStatusCode());
					ordHotel.setStatusName(orderStatus.getStatusName());
					orderHotelService.updateOrderStatus(ordHotel);
					hotelOrderService.addComLog(orderStatus.getStatusCode() + " " + orderStatus.getStatusName(), ordHotel.getLvmamaOrderId());
				}
			}
		}
	}

	public void setOrderHotelService(OrderHotelService orderHotelService) {
		this.orderHotelService = orderHotelService;
	}

	public void setHotelOrderService(HotelOrderService hotelOrderService) {
		this.hotelOrderService = hotelOrderService;
	}
	
}
