package com.lvmama.order.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;

public interface OrdOrderHotelDAO {
	void insert(OrdOrderHotel record);

	void updateOrderStatus(OrdOrderHotel record);

	void updateOrderStatusByPartnerOrderId(String partnerOrderId, String statusCode, String statusName);

	List<OrdOrderHotel> queryOrderHotelListByParam(Map<String, Object> param);

	List<OrdOrderHotel> queryHotelListByOrderId(Long orderId);

	Long countOrdOrderHotelListByParam(Map<String, Object> param);

	List<OrdOrderHotel> queryOrdOrderHotelListByParam(Map<String, Object> param);

	List<OrdOrderHotel> queryDistinctOrdOrderHotelListBySupplierId(String supplierId);
}