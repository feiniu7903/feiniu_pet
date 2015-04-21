package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelRoom;


public interface PlaceHotelRoomService {
	public List<PlaceHotelRoom> queryAllPlaceHotelRoom(PlaceHotelRoom placeHotelRoom);
	public void addOrUpdatePlaceHotelRoom(PlaceHotelRoom placeHotelRoom) throws Exception;
	public void deletePlaceHotelRoom(PlaceHotelRoom placeHotelRoom) throws Exception;
	public PlaceHotelRoom queryPlaceHotelRoomByRoomId(PlaceHotelRoom placeHotelRoom);
}
