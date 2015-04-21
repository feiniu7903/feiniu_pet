package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.bee.po.ord.OrdOrderHotel;

public interface OrderHotelService {

	void saveOrdOrderHotel(OrdOrderHotel record);

	List<OrdOrderHotel> searchOrderHotelByParam(Map<String, Object> param);

	void updateOrderStatus(OrdOrderHotel record);

	void updateOrderStatusByPartnerOrderId(String partnerOrderId, String statusCode, String statusName);

	List<OrdOrderHotel> searchOrderHotelByOrderId(Long orderId);

	Long countOrdOrderHotelListByParam(Map<String, Object> param);

	List<OrdOrderHotel> queryOrdOrderHotelListByParam(Map<String, Object> param);

	List<OrdOrderHotel> queryDistinctOrdOrderHotelListBySupplierId(String supplierId);

}