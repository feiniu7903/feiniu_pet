package com.lvmama.com.service;

import java.util.List;

import com.lvmama.com.dao.ComPlaceDAO;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.pub.ComPlaceService;

public class ComPlaceServiceImpl implements ComPlaceService {
	
	private ComPlaceDAO comPlaceDAO;
	/**
	 * 根据主键加载一条记录
	 */
	public Place load(Long placeId) {
		return comPlaceDAO.load(placeId);
	}
	
	/**
	 * 根据主键集合获取记录集合
	 * @author ZHANG Nan
	 * @param placeIds
	 * @return
	 */
	public List<Place> selectByPlaceIds(List<Long> placeIds){
		return comPlaceDAO.selectByPlaceIds(placeIds);
	}
	
	
	public ComPlaceDAO getComPlaceDAO() {
		return comPlaceDAO;
	}
	public void setComPlaceDAO(ComPlaceDAO comPlaceDAO) {
		this.comPlaceDAO = comPlaceDAO;
	}
}
