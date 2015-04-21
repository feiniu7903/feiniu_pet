package com.lvmama.pet.place.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.place.PlaceActivity;

public class PlaceActivityDAO extends BaseIbatisDAO{

    public int deletePlaceActivityByPrimaryKey(Long placeActivityId){
        int rows = super.delete("PLACE_ACTIVITY.deletePlaceActivityByPrimaryKey", placeActivityId);
        return rows;
    }

    public void insert(PlaceActivity placeActivity)  {
        super.insert("PLACE_ACTIVITY.insert", placeActivity);
    }

    public List<PlaceActivity> queryPlaceActivityListByPlaceId(Long placeId){
    	List<PlaceActivity> placeActivityList=super.queryForList("PLACE_ACTIVITY.queryPlaceActivityListByPlaceId",placeId);
    	return placeActivityList;
    }
    public PlaceActivity selectByPlaceActivityId(Long placeActivityId)  {
        PlaceActivity record = (PlaceActivity) super.queryForObject("PLACE_ACTIVITY.selectByPlaceActivityId", placeActivityId);
        return record;
    }


    public void updatePlaceActivity(PlaceActivity placeActivity) {
        super.update("PLACE_ACTIVITY.updatePlaceActivity", placeActivity);
    }

	public List<PlaceActivity> queryPlaceActivityListByParam(Map map) {
		List<PlaceActivity> placeActivityList=super.queryForList("PLACE_ACTIVITY.queryPlaceActivityListByParam",map);
    	return placeActivityList;
	}
}