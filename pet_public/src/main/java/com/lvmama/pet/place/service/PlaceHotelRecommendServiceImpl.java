package com.lvmama.pet.place.service;

import java.util.List;

import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.service.place.PlaceHotelRecommendService;
import com.lvmama.pet.place.dao.PlaceHotelRecommendDao;

public class PlaceHotelRecommendServiceImpl implements PlaceHotelRecommendService{
	private PlaceHotelRecommendDao placeHotelRecommendDao;
	public void setPlaceHotelRecommendDao(
			PlaceHotelRecommendDao placeHotelRecommendDao) {
		this.placeHotelRecommendDao = placeHotelRecommendDao;
	}

	@Override
	public List<PlaceHotelOtherRecommend> queryAllPlaceHotelRecommend(
			PlaceHotelOtherRecommend placeHotelRecommend) {
		return placeHotelRecommendDao.queryAllPlaceHotelRecommen(placeHotelRecommend);
	}

	@Override
	public void addOrUpdatePlaceHotelRecommend(
			PlaceHotelOtherRecommend placeHotelRecommend) throws Exception {
		placeHotelRecommendDao.addOrUpdatePlaceHotelRecommend(placeHotelRecommend);
	}

	@Override
	public void deletePlaceHotelRecommend(
			PlaceHotelOtherRecommend placeHotelRecommend) throws Exception {
		placeHotelRecommendDao.deletePlaceHotelRecommend(placeHotelRecommend);
	}

	@Override
	public PlaceHotelOtherRecommend queryPlaceHotelRecommendByRecommendId(
			PlaceHotelOtherRecommend placeHotelRecommend) {
		return placeHotelRecommendDao.queryPlaceHotelRecommendByRecommendId(placeHotelRecommend);
	}
}
