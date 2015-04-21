package com.lvmama.pet.place.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.service.place.PlaceLandMarkService;
import com.lvmama.pet.place.dao.PlaceLandMarkDAO;

public class PlaceLandMarkServiceImpl implements PlaceLandMarkService {
	
	private PlaceLandMarkDAO placeLandMarkDAO;

	public PlaceLandMark selectByPrimaryKey(Long placeLandMarkId){
		return placeLandMarkDAO.selectByPrimaryKey(placeLandMarkId);
	}

	public void saveOrUpdatePlaceLandMark(PlaceLandMark placeLandMark) {
		placeLandMarkDAO.saveOrUpdatePlaceLandMark(placeLandMark);
	}

	public void deletePlaceLandMarkByPrimaryKey(Long placeLandMarkId){
		placeLandMarkDAO.deletePlaceLandMarkByPrimaryKey(placeLandMarkId);
	}

	public List<PlaceLandMark> selectByParams(Map<String,Object> param) {
		return placeLandMarkDAO.selectByParams(param);
	}
	
	public Long selectByParamsCount(Map<String,Object> param){
		return placeLandMarkDAO.selectByParamsCount(param);
	}
	
	public List<PlaceLandMark> selectByName(String name){
		 return 	this.placeLandMarkDAO.selectByName(name);
	 }
	public void setPlaceLandMarkDAO(PlaceLandMarkDAO PlaceLandMarkDAO) {
		this.placeLandMarkDAO = PlaceLandMarkDAO;
	}
}
