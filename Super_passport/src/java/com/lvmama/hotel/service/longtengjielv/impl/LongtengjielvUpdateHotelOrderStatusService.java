package com.lvmama.hotel.service.longtengjielv.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
import com.lvmama.hotel.model.OrderStatus;
import com.lvmama.hotel.service.HotelOrderService;
import com.lvmama.hotel.service.UpdateHotelOrderStatusService;
import com.lvmama.hotel.support.longtengjielv.impl.LongtengjielvHotelOrderServiceSupport;
import com.lvmama.passport.utils.WebServiceConstant;

public class LongtengjielvUpdateHotelOrderStatusService implements UpdateHotelOrderStatusService {
	private LongtengjielvHotelOrderServiceSupport longtengjielvHotelOrderServiceSupport;
	private OrderHotelService orderHotelService;
	private HotelOrderService hotelOrderService;

	public void updateOrderStatus() throws Exception {
		String supplierId = WebServiceConstant.getProperties("longtengjielv.supplierId");
		List<OrdOrderHotel> ordHotelList = orderHotelService.queryDistinctOrdOrderHotelListBySupplierId(supplierId);
		for (OrdOrderHotel ordHotel : ordHotelList) {
			OrderStatus orderStatus = longtengjielvHotelOrderServiceSupport.queryOrder(ordHotel.getPartnerOrderId());
			if (orderStatus != null && !StringUtils.equals(ordHotel.getStatusCode(), orderStatus.getStatusCode())) {
				orderHotelService.updateOrderStatusByPartnerOrderId(ordHotel.getPartnerOrderId(), orderStatus.getStatusCode(), orderStatus.getStatusName());
				hotelOrderService.addComLog(orderStatus.getStatusCode() + " " + orderStatus.getStatusName(), ordHotel.getLvmamaOrderId());
			}
		}
	}

	public void setLongtengjielvHotelOrderServiceSupport(LongtengjielvHotelOrderServiceSupport longtengjielvHotelOrderServiceSupport) {
		this.longtengjielvHotelOrderServiceSupport = longtengjielvHotelOrderServiceSupport;
	}

	public void setOrderHotelService(OrderHotelService orderHotelService) {
		this.orderHotelService = orderHotelService;
	}

	public void setHotelOrderService(HotelOrderService hotelOrderService) {
		this.hotelOrderService = hotelOrderService;
	}

}
