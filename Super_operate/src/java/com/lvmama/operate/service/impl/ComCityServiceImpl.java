package com.lvmama.operate.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.operate.dao.ComCityDAO;
import com.lvmama.operate.dao.ComProvinceDAO;
import com.lvmama.operate.service.ComCityService;

public class ComCityServiceImpl implements ComCityService {
	@Autowired
	private ComCityDAO comCityDAO;
	
	private ComProvinceDAO comProvinceDAO;

	@Override
	public ComCity selectByPrimaryKey(String cityId) {
		return comCityDAO.selectByPrimaryKey(cityId);
	}

	@Override
	public List<ComProvince> selectProvinceList() {
		return comProvinceDAO.getAllProvince();
	}

	@Override
	public List<ComCity> selectCitiesByProvinceId(ComProvince province) {
		if(null == province || (null!=province && null == province.getProvinceId())){
			return null;
		}
		return comCityDAO.selectCityByProvinceId(province.getProvinceId());
	}

	@Override
	public ComCity getCity(ComCity dc) {
		if(null == dc || (null!=dc && null == dc.getCityId())){
			return null;
		}
		return comCityDAO.selectByPrimaryKey(dc.getCityId());
	}

	@Override
	public ComProvince getProvince(ComProvince province) {
		if(null == province || (null!=province && null == province.getProvinceId())){
			return null;
		}
		return comProvinceDAO.selectByPrimaryKey(province.getProvinceId());
	}

	public ComCityDAO getComCityDAO() {
		return comCityDAO;
	}

	public void setComCityDAO(ComCityDAO comCityDAO) {
		this.comCityDAO = comCityDAO;
	}

	public ComProvinceDAO getComProvinceDAO() {
		return comProvinceDAO;
	}

	public void setComProvinceDAO(ComProvinceDAO comProvinceDAO) {
		this.comProvinceDAO = comProvinceDAO;
	}
}
