package com.lvmama.pet.place.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;

public class PlaceCoordinateBaiduDao extends BaseIbatisDAO {
	public List<PlaceCoordinateVo> getBaiduMapList(Map<String, Object> param) {
		if (param.get("_startRow") == null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow") == null) {
			param.put("_endRow", 19);
		}
		return super.queryForList("PLACE_COORDINATE_BAIDU.getBaiduMapList", param);
	}

	public Long getBaiduMapListCount(Map<String, Object> param) {
		Long count = 0L;
		count = (Long) super.queryForObject("PLACE_COORDINATE_BAIDU.getBaiduMapListCount", param);
		return count;
	}

	/**
	 * 根据placeId获取对象
	 * @param placeId
	 * @return
	 */
	public List<PlaceCoordinateBaidu> getPlaceCoordinateBaiduByParam(Map<String,Object> param){
		List<PlaceCoordinateBaidu> placeCoordinateBaiduList=(List<PlaceCoordinateBaidu>)super.queryForList("PLACE_COORDINATE_BAIDU.getPlaceCoordinateBaiduByParam",param);
		return placeCoordinateBaiduList;
	}
		
	public void insert(PlaceCoordinateBaidu placeCoordinateBaidu){
		super.insert("PLACE_COORDINATE_BAIDU.insert", placeCoordinateBaidu);
	}

	public int update(PlaceCoordinateBaidu placeCoordinateBaidu) {
		int rows = super.update("PLACE_COORDINATE_BAIDU.update", placeCoordinateBaidu);
		return rows;
	}
	/**
	 * 根据坐标范围和类型查询出周边的place
	 * 
	 * @param map
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<PlaceCoordinateBaidu> getPlacesByCoordinateBaiduAndStage(Map<String, Object> map) {
		return (List<PlaceCoordinateBaidu>)super.queryForList("PLACE_COORDINATE_BAIDU.getPlacesByCoordinateBaiduAndStage", map);
	}
	/**
	 * 根据景区ID获取单个坐标
	 * @param place
	 * @return
	 */
	public ViewPlaceCoordinate getViewPlacecoordinateByPlaceId(String placeId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("placeId", placeId);
		return (ViewPlaceCoordinate)super.queryForObject("PLACE_COORDINATE_BAIDU.getViewPlaceCoordinateById", map);
	}
}
