package com.lvmama.comm.pet.service.place;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;


public interface PlaceHotelRecommendService {
	public List<PlaceHotelOtherRecommend> queryAllPlaceHotelRecommend(PlaceHotelOtherRecommend placeHotelRecommend);
	public void addOrUpdatePlaceHotelRecommend(PlaceHotelOtherRecommend placeHotelRecommend) throws Exception;
	public void deletePlaceHotelRecommend(PlaceHotelOtherRecommend placeHotelRecommend) throws Exception;
	public PlaceHotelOtherRecommend queryPlaceHotelRecommendByRecommendId(PlaceHotelOtherRecommend placeHotelRecommend);
}
