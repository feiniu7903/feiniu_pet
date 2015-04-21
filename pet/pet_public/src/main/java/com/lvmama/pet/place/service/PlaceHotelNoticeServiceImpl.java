package com.lvmama.pet.place.service;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.service.place.PlaceHotelNoticeService;
import com.lvmama.pet.place.dao.PlaceHotelNoticeDao;

public class PlaceHotelNoticeServiceImpl implements PlaceHotelNoticeService{
	private PlaceHotelNoticeDao placeHotelNoticeDao ;

	@Override
	public List<PlaceHotelNotice> queryByHotelNotice(PlaceHotelNotice hotelNotice) {
		return  placeHotelNoticeDao.queryByHotelNotice(hotelNotice);
	}
	@Override
	public void addOrUpdatePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception{
		placeHotelNoticeDao.addOrUpdatePlaceHotelNotice(hotelNotice);
	}
	@Override
	public void deletePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception{
		placeHotelNoticeDao.deletePlaceHotelNotice(hotelNotice);
	}
	public void setPlaceHotelNoticeDao(PlaceHotelNoticeDao placeHotelNoticeDao) {
		this.placeHotelNoticeDao = placeHotelNoticeDao;
	}
	@Override
	public PlaceHotelNotice queryPlacehotelNoticeByNoticeId(
			PlaceHotelNotice hotelNotice){
		return placeHotelNoticeDao.queryPlacehotelNoticeByNoticeId(hotelNotice);
	}
}
