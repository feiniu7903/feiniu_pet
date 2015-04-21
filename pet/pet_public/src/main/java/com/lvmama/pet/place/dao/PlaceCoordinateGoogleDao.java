package com.lvmama.pet.place.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;

public class PlaceCoordinateGoogleDao extends BaseIbatisDAO {
	public List<PlaceCoordinateVo> getGoogleMapList(Map<String, Object> param) {
		if (param.get("_startRow") == null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow") == null) {
			param.put("_endRow", 19);
		}
		return super.queryForList("PLACE_COORDINATE_GOOGLE.getGoogleMapList", param);
	}

	public Long getGoogleMapListCount(Map<String, Object> param) {
		Long count = 0L;
		count = (Long) super.queryForObject("PLACE_COORDINATE_GOOGLE.getGoogleMapListCount", param);
		return count;
	}

	/**
	 * 根据placeId获取对象
	 * @param placeId
	 * @return
	 */
	public List<PlaceCoordinateGoogle> getPlaceCoordinateGoogleByParam(Map<String,Object> param){
		List<PlaceCoordinateGoogle> placeCoordinateGoogleList=(List<PlaceCoordinateGoogle>)super.queryForList("PLACE_COORDINATE_GOOGLE.getPlaceCoordinateGoogleByParam",param);
		return placeCoordinateGoogleList;
	}
		
	public void insert(PlaceCoordinateGoogle placeCoordinateGoogle){
		super.insert("PLACE_COORDINATE_GOOGLE.insert", placeCoordinateGoogle);
	}

	public int update(PlaceCoordinateGoogle placeCoordinateGoogle) {
		int rows = super.update("PLACE_COORDINATE_GOOGLE.update", placeCoordinateGoogle);
		return rows;
	}
	
	public List<ViewPlaceCoordinate> getViewPlacecoordinatesByPlaceIds(
			List<Long> placeIds) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("placeIds", placeIds);
		return super.queryForList("PLACE_COORDINATE_GOOGLE.getViewPlaceCoordinates", map);
	}
	
	public ViewPlaceCoordinate getViewPlacecoordinateByPlaceId(String placeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("placeId", placeId);
		return (ViewPlaceCoordinate)super.queryForObject("PLACE_COORDINATE_GOOGLE.getViewPlaceCoordinateById", map);
	}
	
	public List<ViewPlaceCoordinate> getPlacesByCoordinate(float maxLongitude,float minLongitude,float maxLatitude,float minLatitude,String type)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("maxLongitude", maxLongitude+"");
		map.put("minLongitude", minLongitude+"");
		map.put("maxLatitude", maxLatitude+"");
		map.put("minLatitude", minLatitude+"");
		return super.queryForList("PLACE_COORDINATE_GOOGLE.getPlacesByCoordinate",map);
	}
	
	public List<ViewPlaceCoordinate> getPlacesByCoordinate(float longitude,float latitude,String type)
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("longitude", longitude+"");
		map.put("latitude", latitude+"");
		return super.queryForList("PLACE_COORDINATE_GOOGLE.getPlacesByCoordinateOnce",map);
	}

	/**
	 * 根据坐标范围和类型查询出周边的景区
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceCoordinateGoogle> getPlacesByCoordinateGoogleAndStage(Map<String, Object> map) {
		return (List<PlaceCoordinateGoogle>)super.queryForList("PLACE_COORDINATE_GOOGLE.getPlacesByCoordinateGoogleAndStage", map);
	}

}
