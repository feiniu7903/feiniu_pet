package com.lvmama.comm.pet.service.place;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceLandMark;

public interface PlaceLandMarkService {

	 PlaceLandMark selectByPrimaryKey(Long placeLandMarkId);

	 void saveOrUpdatePlaceLandMark(PlaceLandMark placeLandMark);

	 void deletePlaceLandMarkByPrimaryKey(Long placeLandMarkId);

	 List<PlaceLandMark> selectByParams(Map<String,Object> param);
		
	 Long selectByParamsCount(Map<String,Object> param);
	 
	 
	 List<PlaceLandMark> selectByName(String name);
		
}
