package com.lvmama.pet.place.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.place.dao.PlaceCoordinateGoogleDao;

public class PlaceCoordinateGoogleServiceImpl implements PlaceCoordinateGoogleService {
	private PlaceCoordinateGoogleDao placeCoordinateGoogleDao;
	
	@Override
	public List<PlaceCoordinateVo> getGoogleMapListByParams(Map<String, Object> params) {
		return placeCoordinateGoogleDao.getGoogleMapList(params);
	}

	@Override
	public long getGoogleMapListByParamsCount(Map<String, Object> params) {
		return placeCoordinateGoogleDao.getGoogleMapListCount(params);
	}

	@Override
	public void insertPlaceCoordinateGoogle(PlaceCoordinateGoogle placeCoordinateGoogle) {
		this.placeCoordinateGoogleDao.insert(placeCoordinateGoogle);
		
	}

	@Override
	public void updatePlaceCoordinateGoogle(PlaceCoordinateGoogle placeCoordinateGoogle) {
		this.placeCoordinateGoogleDao.update(placeCoordinateGoogle);
		
	}
	
	@Override
	public List<PlaceCoordinateGoogle> getPlaceCoordinateGoogleByParam(Map<String, Object> param) {
		return this.placeCoordinateGoogleDao.getPlaceCoordinateGoogleByParam(param);
	}

	public void setPlaceCoordinateGoogleDao(PlaceCoordinateGoogleDao placeCoordinateGoogleDao) {
		this.placeCoordinateGoogleDao = placeCoordinateGoogleDao;
	}

	/**
	 * 根据景区ID集合获取坐标列表
	 * @param placeIds景区列表
	 * @return
	 */
	public List<ViewPlaceCoordinate> getViewPlacecoordinatesByPlaceIds(
			List<Long> placeIds) {
		return placeCoordinateGoogleDao.getViewPlacecoordinatesByPlaceIds(placeIds);
	}
	
	/**
	 * 根据景区ID获取单个坐标
	 * @param place
	 * @return
	 */
	public ViewPlaceCoordinate getViewPlacecoordinateByPlaceId(String placeId) {
		return placeCoordinateGoogleDao.getViewPlacecoordinateByPlaceId(placeId);
	}

	/**
	 * 根据传入的经纬度获取偏差范围内所有的坐标点
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param windage 偏差
	 * @return 范围内所有坐标点对象
	 */
	public List<ViewPlaceCoordinate> getPlacesByCoordinate(float longitude, float latitude, float windage, String type) {
		if(windage==0)
		{
			return placeCoordinateGoogleDao.getPlacesByCoordinate(longitude, latitude,type);
		}
		else
		{
			float maxLongitude = longitude + windage;
			float minLongitude = longitude - windage;
			float maxLatitude = latitude + windage;
			float minLatitude = latitude - windage;
			return placeCoordinateGoogleDao.getPlacesByCoordinate(maxLongitude, minLongitude, maxLatitude, minLatitude,type);
		}
	}
	/**
	 * 周边景区
	 * 
	 * @param placeId
	 * @param stage
	 * @return
	 */
	public List<PlaceCoordinateGoogle> getCoordinateByPlace(Long placeId, String stage) {
		List<PlaceCoordinateGoogle> coordList = new ArrayList<PlaceCoordinateGoogle>();
			ViewPlaceCoordinate coord = placeCoordinateGoogleDao.getViewPlacecoordinateByPlaceId(String.valueOf(placeId));
			if (coord != null) {
				Float windage = Float.valueOf(Constant.getMapPlacecoordinateWindage().trim());
				Map<String, Object> map = new HashMap<String, Object>();
				if (coord.getLongitude() != null && coord.getLatitude() != null) {
					map.put("maxLongitude", coord.getLongitude() + windage);
					map.put("minLongitude", coord.getLongitude() - windage);
					map.put("maxLatitude", coord.getLatitude() + windage);
					map.put("minLatitude", coord.getLatitude() - windage);
					map.put("stage", stage);
					coordList = placeCoordinateGoogleDao.getPlacesByCoordinateGoogleAndStage(map);
				}
			}
		return coordList;
	}
}
