package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;

public interface PlaceCoordinateGoogleService {
	/**
	 * 根据参数查询景区、及其对应的景区信息
	 * @return 
	 */
	public List<PlaceCoordinateVo> getGoogleMapListByParams(Map<String, Object> params);
	
	/**
	 * 查询景区、及其对应的景区信息
	 * @param params
	 * @return
	 */
	public long getGoogleMapListByParamsCount(Map<String, Object> params);
	
	/**
	 * 插入新纪录到表
	 * @param params
	 * @return
	 */
	public void insertPlaceCoordinateGoogle(PlaceCoordinateGoogle placeCoordinateGoogle);
	
	/**
	 * 更新纪录到表
	 * @param params
	 * @return
	 */
	public void updatePlaceCoordinateGoogle(PlaceCoordinateGoogle placeCoordinateGoogle);	
	
	/**
	 * 根据参数获得对象LIST
	 * @param params
	 * @return
	 */
	public List<PlaceCoordinateGoogle> getPlaceCoordinateGoogleByParam(Map<String, Object> param);
	
	/**
	 * 根据景区ID获取坐标
	 * @param placeIds景区列表
	 * @return
	 */
	public List<ViewPlaceCoordinate> getViewPlacecoordinatesByPlaceIds(List<Long> placeIds);
	/**
	 * 根据景区ID获取单个坐标
	 * @param place
	 * @return
	 */
	public ViewPlaceCoordinate getViewPlacecoordinateByPlaceId(String placeId);
	
	/**
	 * 根据传入的经纬度获取偏差范围内所有的坐标点
	 * @param longitude 经度
	 * @param latitude 纬度
	 * @param windage 偏差
	 * @return 范围内所有坐标点对象
	 */
	public List<ViewPlaceCoordinate> getPlacesByCoordinate(float longitude,float latitude,float windage,String type);
	/**
	 * 周边景区
	 * 
	 * @param placeId
	 * @param stage
	 * @return
	 */
	public List<PlaceCoordinateGoogle> getCoordinateByPlace(Long placeId, String stage);
}
