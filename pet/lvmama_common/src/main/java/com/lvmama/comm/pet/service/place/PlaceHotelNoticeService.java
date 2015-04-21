package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelNotice;

public interface PlaceHotelNoticeService {
	public List<PlaceHotelNotice> queryByHotelNotice(PlaceHotelNotice hotelNotice);
	public void addOrUpdatePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception;
	public void deletePlaceHotelNotice(PlaceHotelNotice hotelNotice) throws Exception;
	public PlaceHotelNotice queryPlacehotelNoticeByNoticeId(PlaceHotelNotice hotelNotice);
}
