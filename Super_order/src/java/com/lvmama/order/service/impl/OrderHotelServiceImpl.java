package com.lvmama.order.service.impl;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.comm.bee.service.ord.OrderHotelService;
import com.lvmama.order.dao.OrdOrderHotelDAO;

public class OrderHotelServiceImpl implements OrderHotelService {
	private OrdOrderHotelDAO ordOrderHotelDAO;

	@Override
	public void saveOrdOrderHotel(OrdOrderHotel record) {
		ordOrderHotelDAO.insert(record);
	}

	@Override
	public List<OrdOrderHotel> searchOrderHotelByParam(Map<String, Object> param) {
		return ordOrderHotelDAO.queryOrderHotelListByParam(param);
	}

	@Override
	public void updateOrderStatus(OrdOrderHotel record) {
		ordOrderHotelDAO.updateOrderStatus(record);
	}

	@Override
	public void updateOrderStatusByPartnerOrderId(String partnerOrderId, String statusCode, String statusName) {
		ordOrderHotelDAO.updateOrderStatusByPartnerOrderId(partnerOrderId, statusCode, statusName);
	}

	@Override
	public List<OrdOrderHotel> searchOrderHotelByOrderId(Long orderId) {
		return ordOrderHotelDAO.queryHotelListByOrderId(orderId);
	}

	@Override
	public Long countOrdOrderHotelListByParam(Map<String, Object> param) {
		return ordOrderHotelDAO.countOrdOrderHotelListByParam(param);
	}

	@Override
	public List<OrdOrderHotel> queryOrdOrderHotelListByParam(Map<String, Object> param) {
		return ordOrderHotelDAO.queryOrdOrderHotelListByParam(param);
	}

	@Override
	public List<OrdOrderHotel> queryDistinctOrdOrderHotelListBySupplierId(String supplierId) {
		return ordOrderHotelDAO.queryDistinctOrdOrderHotelListBySupplierId(supplierId);
	}

	public void setOrdOrderHotelDAO(OrdOrderHotelDAO ordOrderHotelDAO) {
		this.ordOrderHotelDAO = ordOrderHotelDAO;
	}
}
