package com.lvmama.pet.place.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.place.dao.PlaceCoordinateBaiduDao;

public class PlaceCoordinateBaiduServiceImpl implements PlaceCoordinateBaiduService {
	private PlaceCoordinateBaiduDao placeCoordinateBaiduDao;	
	
	/**
	 * 周边Place
	 * @param placeId
	 * @param stage
	 * @return
	 * 
	 * @deprecated 会被getVicinityByPlace方法替代  
	 */
	@Override
	public List<PlaceCoordinateBaidu> getCoordinateByPlace(Long placeId, Long stage) {
		List<PlaceCoordinateBaidu> coordList = new ArrayList<PlaceCoordinateBaidu>();
			ViewPlaceCoordinate coord = placeCoordinateBaiduDao.getViewPlacecoordinateByPlaceId(String.valueOf(placeId));
			if (coord != null) {
				Float windage = Float.valueOf(Constant.getMapPlacecoordinateWindage().trim());
				Map<String, Object> map = new HashMap<String, Object>();
				if (coord.getLongitude() != null && coord.getLatitude() != null) {
					map.put("maxLongitude", coord.getLongitude() + windage);
					map.put("minLongitude", coord.getLongitude() - windage);
					map.put("maxLatitude", coord.getLatitude() + windage);
					map.put("minLatitude", coord.getLatitude() - windage);
					map.put("stage", stage);
					coordList = placeCoordinateBaiduDao.getPlacesByCoordinateBaiduAndStage(map);
				}
			}
		return coordList;
	}

	@Override
	public List<PlaceCoordinateVo> getBaiduMapListByParams(Map<String, Object> params) {
		return placeCoordinateBaiduDao.getBaiduMapList(params);
	}

	@Override
	public long getBaiduMapListByParamsCount(Map<String, Object> params) {
		return placeCoordinateBaiduDao.getBaiduMapListCount(params);
	}

	@Override
	public void insertPlaceCoordinateBaidu(PlaceCoordinateBaidu placeCoordinateBaidu) {
		this.placeCoordinateBaiduDao.insert(placeCoordinateBaidu);
		
	}

	@Override
	public void updatePlaceCoordinateBaidu(PlaceCoordinateBaidu placeCoordinateBaidu) {
		this.placeCoordinateBaiduDao.update(placeCoordinateBaidu);
		
	}
	
	@Override
	public List<PlaceCoordinateBaidu> getPlaceCoordinateBaiduByParam(Map<String, Object> param) {
		return this.placeCoordinateBaiduDao.getPlaceCoordinateBaiduByParam(param);
	}
	
	public void setPlaceCoordinateBaiduDao(PlaceCoordinateBaiduDao placeCoordinateBaiduDao) {
		this.placeCoordinateBaiduDao = placeCoordinateBaiduDao;
	}	
}
