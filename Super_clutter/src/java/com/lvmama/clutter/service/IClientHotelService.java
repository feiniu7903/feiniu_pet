package com.lvmama.clutter.service;

import java.util.Map;

public interface IClientHotelService {
	Map<String, Object> search(Map<String, Object> param) throws Exception;

	Map<String, Object> getHotel(Map<String, Object> param) throws Exception;

	Map<String, Object> orderValidate(Map<String, Object> param)
			throws Exception;

	Map<String, Object> orderCreate(Map<String, Object> param) throws Exception;

	Map<String, Object> orderList(Map<String, Object> param) throws Exception;

	Map<String, Object> orderDetail(Map<String, Object> param) throws Exception;

	Map<String, Object> orderCancel(Map<String, Object> param) throws Exception;

	Map<String, Object> orderFill(Map<String, Object> param) throws Exception;

	Map<String, Object> getCities(Map<String, Object> param) throws Exception;

	Map<String, Object> getLandMarks(Map<String, Object> param)
			throws Exception;

	Map<String, Object> getHotelBrandList(Map<String, Object> params)
			throws Exception;

	Map<String, Object> hotelSearchFilterData(Map<String, Object> param)
			throws Exception;

	Map<String, Object> getHotelRooms(Map<String, Object> param)
			throws Exception;

	Map<String, Object> getUserCreditCards(Map<String, Object> param)
			throws Exception;

	Map<String, Object> getComments(Map<String, Object> param) throws Exception;
	
	Map<String, Object> getOrderRelation(Map<String, Object> param) throws Exception;

	Map<String, Object> issueHotel(Map<String, Object> param) throws Exception;
	

}
