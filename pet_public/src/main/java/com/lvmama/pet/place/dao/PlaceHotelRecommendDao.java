package com.lvmama.pet.place.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;

public class PlaceHotelRecommendDao extends BaseIbatisDAO{
	public List<PlaceHotelOtherRecommend> queryAllPlaceHotelRecommen(
			PlaceHotelOtherRecommend placeHotelRecommend) {
		return super.queryForList("PLACE_HOTEL_RECOMMEND.queryAllPlaceHoteRecommend",placeHotelRecommend);
	}
	public void addOrUpdatePlaceHotelRecommend(
			PlaceHotelOtherRecommend placeHotelRecommend) throws Exception {
		if(placeHotelRecommend.getRecommendId() == null){
			super.insert("PLACE_HOTEL_RECOMMEND.insert",placeHotelRecommend);		
		}else{
			super.update("PLACE_HOTEL_RECOMMEND.update",placeHotelRecommend);
		}
	}
	public void deletePlaceHotelRecommend(
			PlaceHotelOtherRecommend placeHotelRecommend) throws Exception {
		super.delete("PLACE_HOTEL_RECOMMEND.delete",placeHotelRecommend);
	}
	public PlaceHotelOtherRecommend queryPlaceHotelRecommendByRecommendId(
			PlaceHotelOtherRecommend placeHotelRecommend) {
		return (PlaceHotelOtherRecommend)super.queryForList("PLACE_HOTEL_RECOMMEND.queryPlacehotelNoticeByNoticeId",placeHotelRecommend).get(0);
	}
}
