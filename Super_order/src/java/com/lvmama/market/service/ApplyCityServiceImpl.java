package com.lvmama.market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.market.ApplyCity;
import com.lvmama.comm.bee.service.market.ApplyCityService;
import com.lvmama.market.dao.ApplyCityDAO;

public class ApplyCityServiceImpl implements ApplyCityService {
	private ApplyCityDAO applyCityDAO;

	@Override
	public List<ApplyCity> selectAllApplyCity() {
		return applyCityDAO.selectAllApplyCity();
	}

	@Override
	public ApplyCity selectApplyCityBy(Map<String, Object> map) {
		return applyCityDAO.selectApplyCityBy(map);
	}
	
	public Long getApplyCityPageCount(){
		return applyCityDAO.getApplyCityPageCount();
	}
	
	public List<ApplyCity> getApplyCityByPage(Map<String, Object> map) {
		return applyCityDAO.getApplyCityByPage(map);
	}
	
	public Long addApplyCity(ApplyCity city) {
		return applyCityDAO.addApplyCity(city);
	}

	public int delApplyCity(Long cityId) {
		return applyCityDAO.delApplyCity(cityId);
	}

	public void setApplyCityDAO(ApplyCityDAO applyCityDAO) {
		this.applyCityDAO = applyCityDAO;
	}
}
