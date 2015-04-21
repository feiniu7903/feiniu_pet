package com.lvmama.pet.pub.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.service.pub.ComCityService;
import com.lvmama.pet.pub.dao.ComCityDAO;

public class ComCityServiceImpl implements ComCityService {
	@Autowired
	private ComCityDAO comCityDAO;
	
	@Override
	public ComCity selectByPrimaryKey(String cityId) {
		if (StringUtils.isNotBlank(cityId)) {
			return comCityDAO.selectByPrimaryKey(cityId);
		} else {
			return null;
		}
	}
	
	@Override
	public List<ComCity> selectCityByProvinceId(String provinceId) {
		if (StringUtils.isNotBlank(provinceId)) {
			return comCityDAO.selectCityByProvinceId(provinceId);
		} else {
			return null;
		}	
	}
	
	@Override
	public ComCity selectCityByNameAndCity(String provinceId,String cityName) {
		return comCityDAO.selectCityByNameAndCity(provinceId, cityName);
	}
	
	@Override
	public List<ComCity> selectByCityName(String city) {
		return comCityDAO.selectByCityName(city);
	}

	public ComCityDAO getComCityDAO() {
		return comCityDAO;
	}

	public void setComCityDAO(ComCityDAO comCityDAO) {
		this.comCityDAO = comCityDAO;
	}
}
