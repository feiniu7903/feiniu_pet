package com.lvmama.pet.place.service;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.pet.place.dao.PlaceActivityDAO;
import com.lvmama.pet.place.dao.PlaceDAO;

public class PlaceActivityServiceImpl implements PlaceActivityService {
	private PlaceActivityDAO placeActivityDAO;
	private PlaceDAO placeDAO;

	public List<PlaceActivity> queryPlaceActivityListByPlaceId(Long placeId) {
		return placeActivityDAO.queryPlaceActivityListByPlaceId(placeId);
	}
 	public List<PlaceActivity> queryPlaceActivityListByParam(Map map) {
		return placeActivityDAO.queryPlaceActivityListByParam(map);
	}
	public void deletePlaceActivityByPlaceActivityId(Long placeActivityId){
		PlaceActivity placeActivity=placeActivityDAO.selectByPlaceActivityId(placeActivityId);
		Long placeId=null;
		if(placeActivity!=null){
			placeId=placeActivity.getPlaceId();
			placeActivityDAO.deletePlaceActivityByPrimaryKey(placeActivityId);
			List<PlaceActivity> list=queryPlaceActivityListByPlaceId(placeId);
			if(list==null||list.size()==0){
				Place p=new Place();
				p.setPlaceId(placeId);
				p.setIsHasActivity("N");
				placeDAO.updatePlace(p);
			}
		}
		
	}
	public PlaceActivity queryPlaceActivityByPlaceActivityId(
			Long placeActivityId) {
		return placeActivityDAO.selectByPlaceActivityId(placeActivityId);
	}

	public void savePlaceActivity(PlaceActivity placeActivity) {
		if(placeActivity!=null){
			if(placeActivity.getPlaceActivityId()!=null){
				placeActivityDAO.updatePlaceActivity(placeActivity);
			}else{
				placeActivity.setIsValid("Y");
				placeActivity.setSeq(0L);
				placeActivityDAO.insert(placeActivity);
				Place p=new Place();
				p.setPlaceId(placeActivity.getPlaceId());
				p.setIsHasActivity("Y");
				placeDAO.updatePlace(p);
			}
		}
	}

	public void setPlaceActivityDAO(PlaceActivityDAO placeActivityDAO) {
		this.placeActivityDAO = placeActivityDAO;
	}
	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}

}
