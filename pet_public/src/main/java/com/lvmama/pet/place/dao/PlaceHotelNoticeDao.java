package com.lvmama.pet.place.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;

public class PlaceHotelNoticeDao extends BaseIbatisDAO{
	public List<PlaceHotelNotice> queryByHotelNotice(PlaceHotelNotice hotelNotice) {
		return super.queryForList("PLACE_HOTEL_NOTICE.queryAllPlaceHoteNotice", hotelNotice);
	}
	public void addOrUpdatePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception{
		if(hotelNotice.getNoticeId() == null){
			super.insert("PLACE_HOTEL_NOTICE.insert",hotelNotice);
		}else{
			super.update("PLACE_HOTEL_NOTICE.update",hotelNotice);
		}
	}
	public void deletePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception{
		super.delete("PLACE_HOTEL_NOTICE.delete",hotelNotice);
	}
	public PlaceHotelNotice queryPlacehotelNoticeByNoticeId(PlaceHotelNotice hotelNotice){
		return (PlaceHotelNotice)super.queryForList("PLACE_HOTEL_NOTICE.queryPlacehotelNoticeByNoticeId",hotelNotice).get(0);
	}
}
