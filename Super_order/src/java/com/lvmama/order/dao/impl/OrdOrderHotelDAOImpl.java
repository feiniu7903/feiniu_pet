package com.lvmama.order.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;
import com.lvmama.order.dao.OrdOrderHotelDAO;

public class OrdOrderHotelDAOImpl extends BaseIbatisDAO implements OrdOrderHotelDAO {
	@Override
	public void insert(OrdOrderHotel record) {
		super.insert("ORD_ORDER_HOTEL.insert", record);
	}

	@Override
	public void updateOrderStatus(OrdOrderHotel record) {
		super.update("ORD_ORDER_HOTEL.updateOrderStatus", record);
	}

	@Override
	public void updateOrderStatusByPartnerOrderId(String partnerOrderId, String statusCode, String statusName) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("partnerOrderId", partnerOrderId);
		params.put("statusCode", statusCode);
		params.put("statusName", statusName);
		super.update("ORD_ORDER_HOTEL.updateOrderStatusByPartnerOrderId", params);
	}

	@Override
	public List<OrdOrderHotel> queryOrderHotelListByParam(Map<String, Object> param) {
		return super.queryForList("ORD_ORDER_HOTEL.queryOrderHotelListByParam", param);
	}

	@Override
	public List<OrdOrderHotel> queryDistinctOrdOrderHotelListBySupplierId(String supplierId) {
		return super.queryForList("ORD_ORDER_HOTEL.queryDistinctOrdOrderHotelListBySupplierId", supplierId);
	}

	@Override
	public List<OrdOrderHotel> queryHotelListByOrderId(Long orderId) {
		return super.queryForList("ORD_ORDER_HOTEL.queryHotelListByOrderId", orderId);
	}

	public Long countOrdOrderHotelListByParam(Map<String, Object> param) {
		return (Long) super.queryForObject("ORD_ORDER_HOTEL.countOrdOrderHotelListByParam", param);
	}

	public List<OrdOrderHotel> queryOrdOrderHotelListByParam(Map<String, Object> param) {
		return (List<OrdOrderHotel>) super.queryForList("ORD_ORDER_HOTEL.queryOrdOrderHotelListByParam", param);
	}

}