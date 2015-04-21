package com.lvmama.pet.place.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;

public class PlaceHotelRoomDao extends BaseIbatisDAO{
	public List<PlaceHotelRoom> queryAllPlaceHotelRoom(
			PlaceHotelRoom placeHotelRoom) {
		return super.queryForList("PLACE_HOTEL_ROOM.queryAllPlaceHoteRoom",placeHotelRoom);
	}
	
	public void addOrUpdatePlaceHotelRoom(PlaceHotelRoom placeHotelRoom)
			throws Exception {
		if(placeHotelRoom.getRoomId() == null){
			super.insert("PLACE_HOTEL_ROOM.insert",placeHotelRoom);
		}else{
			super.update("PLACE_HOTEL_ROOM.update",placeHotelRoom);
		}
	}

	public void deletePlaceHotelRoom(PlaceHotelRoom placeHotelRoom)
			throws Exception {
		super.delete("PLACE_HOTEL_ROOM.delete",placeHotelRoom);
	}

	
	public PlaceHotelRoom queryPlacehotelRoomByRoomId(
			PlaceHotelRoom placeHotelRoom) {
		return (PlaceHotelRoom)super.queryForList("PLACE_HOTEL_ROOM.queryPlaceHotelRoomByRoomId",placeHotelRoom).get(0);
	}
}
