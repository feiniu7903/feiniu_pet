package com.lvmama.hotel.action;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
import com.lvmama.hotel.service.HotelOrderService;

public class HotelOrderAction extends BaseAction {
	private static final long serialVersionUID = 6350289068604922781L;

	private HotelOrderService hotelOrderService;
	private OrderHotelService orderHotelService;
	
	@Action("/hotel/submitOrder")
	public void submitOrder() throws Exception {
		String orderId = this.getRequest().getParameter("orderId");
		if (StringUtils.isNotBlank(orderId)) {
			List<OrdOrderHotel> orderHotels = orderHotelService.searchOrderHotelByOrderId(Long.valueOf(orderId));
			if (orderHotels.isEmpty()) {
				String result = hotelOrderService.submitOrder(Long.valueOf(orderId));
				sendAjaxMsg(result);
			} else {
				sendAjaxMsg("该订单已存在，不可重复下单");
			}
		} else {
			sendAjaxMsg("请提供订单号(orderId=?)");
		}
	}

	@Action("/hotel/cancelOrder")
	public void cancelOrder() {
		String orderId = this.getRequest().getParameter("orderId");
		if (StringUtils.isNotBlank(orderId)) {
			List<OrdOrderHotel> orderHotels = orderHotelService.searchOrderHotelByOrderId(Long.valueOf(orderId));
			if (!orderHotels.isEmpty()) {
				String result = hotelOrderService.cancelOrder(Long.valueOf(orderId));
				sendAjaxMsg(result);
			} else {
				sendAjaxMsg("订单不存在，不可废单");
			}
		} else {
			sendAjaxMsg("请提供订单号(orderId=?)");
		}
	}

	public void setHotelOrderService(HotelOrderService hotelOrderService) {
		this.hotelOrderService = hotelOrderService;
	}

	public void setOrderHotelService(OrderHotelService orderHotelService) {
		this.orderHotelService = orderHotelService;
	}
}
