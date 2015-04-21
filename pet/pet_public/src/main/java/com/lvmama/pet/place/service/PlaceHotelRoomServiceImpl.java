package com.lvmama.pet.place.service;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.service.place.PlaceHotelRoomService;
import com.lvmama.pet.place.dao.PlaceHotelRoomDao;

public class PlaceHotelRoomServiceImpl implements PlaceHotelRoomService{
	private PlaceHotelRoomDao placeHotelRoomDao;

	public void setPlaceHotelRoomDao(PlaceHotelRoomDao placeHotelRoomDao) {
		this.placeHotelRoomDao = placeHotelRoomDao;
	}

	@Override
	public void deletePlaceHotelRoom(PlaceHotelRoom placeHotelRoom)
			throws Exception {
		placeHotelRoomDao.deletePlaceHotelRoom(placeHotelRoom);
	}

	@Override
	public PlaceHotelRoom queryPlaceHotelRoomByRoomId(
			PlaceHotelRoom placeHotelRoom) {
		return placeHotelRoomDao.queryPlacehotelRoomByRoomId(placeHotelRoom);
	}

	@Override
	public List<PlaceHotelRoom> queryAllPlaceHotelRoom(
			PlaceHotelRoom placeHotelRoom) {
		return placeHotelRoomDao.queryAllPlaceHotelRoom(placeHotelRoom);
	}

	@Override
	public void addOrUpdatePlaceHotelRoom(PlaceHotelRoom placeHotelRoom)
			throws Exception {
		placeHotelRoomDao.addOrUpdatePlaceHotelRoom(placeHotelRoom);
	}

}
