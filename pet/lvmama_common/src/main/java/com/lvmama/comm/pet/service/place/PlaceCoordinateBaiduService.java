package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;


public interface PlaceCoordinateBaiduService {
	/**
	 * 根据参数查询景区、及其对应的景区信息
	 * @return 
	 */
	public List<PlaceCoordinateVo> getBaiduMapListByParams(Map<String, Object> params);
	
	/**
	 * 查询景区、及其对应的景区信息
	 * @param params
	 * @return
	 */
	public long getBaiduMapListByParamsCount(Map<String, Object> params);
	
	/**
	 * 插入新纪录到表
	 * @param params
	 * @return
	 */
	public void insertPlaceCoordinateBaidu(PlaceCoordinateBaidu placeCoordinateBaidu);
	
	/**
	 * 更新纪录到表
	 * @param params
	 * @return
	 */
	public void updatePlaceCoordinateBaidu(PlaceCoordinateBaidu placeCoordinateBaidu);
	
	
	/**
	 * 根据参数获得对象LIST
	 * @param params
	 * @return
	 */
	public List<PlaceCoordinateBaidu> getPlaceCoordinateBaiduByParam(Map<String, Object> param);
	/**
	 * 周边place
	 * 
	 * @param placeId
	 * @param stage
	 * @return
	 */
	public List<PlaceCoordinateBaidu> getCoordinateByPlace(Long placeId, Long stage);
	
	

}
