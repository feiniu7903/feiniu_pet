package com.lvmama.pet.place.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceLandMark;

public class PlaceLandMarkDAO  extends BaseIbatisDAO {

	@SuppressWarnings("unchecked")
	public List<PlaceLandMark> selectByParams(Map<String,Object> param){
		return super.queryForList("PLACE_LAND_MARK.selectByParamsSql", param);
	}
	
	public Long selectByParamsCount(Map<String,Object> param){
		return (Long) super.queryForObject("PLACE_LAND_MARK.selectByParamsCount", param);
	}
	
	public int deletePlaceLandMarkByPrimaryKey(Long placeLandMarkId){
		int rows = super.delete("PLACE_LAND_MARK.deleteByPrimaryKey", placeLandMarkId);
		return rows;
	}

	public PlaceLandMark selectByPrimaryKey(Long placeLandMarkId){
		PlaceLandMark record = (PlaceLandMark) super.queryForObject("PLACE_LAND_MARK.selectByPlaceLandMarkId", placeLandMarkId);
		return record;
	}

	public void saveOrUpdatePlaceLandMark(PlaceLandMark placeLandMark) {
		if(placeLandMark.getPlaceLandMarkId() == null) {
			placeLandMark.setIsValid("Y");
			super.insert("PLACE_LAND_MARK.insert", placeLandMark);
		} else {
			super.update("PLACE_LAND_MARK.update", placeLandMark);
		}
	}

	public List<PlaceLandMark> selectByName(String name) {
		return this.queryForList("PLACE_LAND_MARK.selectByName",name);
	}
	
	@SuppressWarnings("unchecked")
	public List<PlaceLandMark> queryPlaceLandMarkByGeoLocation(Map<String,Double> mapGeoLocation){
		return this.queryForList("PLACE_LAND_MARK.queryPlaceLandMarkByGeoLocation",mapGeoLocation);
	}
}
