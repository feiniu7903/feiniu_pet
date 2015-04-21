package com.lvmama.pet.place.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceHotel;

public class PlaceHotelDAO extends BaseIbatisDAO {

	public PlaceHotel searchPlaceHotel(Long placeId) {
		return (PlaceHotel) queryForObject("PLACE_HOTEL.searchPlaceHotel",
				placeId);
	}

	public void insert(PlaceHotel ph) {
		this.insert("PLACE_HOTEL.insert", ph);
	}

	public void update(PlaceHotel ph) {
		this.update("PLACE_HOTEL.update", ph);
	}
}
