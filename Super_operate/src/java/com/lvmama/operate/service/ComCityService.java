package com.lvmama.operate.service;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;


public interface ComCityService {
	/**
	 *根据城市id查找城市记录
	 * @param cityId
	 * @return
	 */
	ComCity selectByPrimaryKey(String cityId);

	List<ComProvince> selectProvinceList();

	List<ComCity> selectCitiesByProvinceId(ComProvince province);

	ComCity getCity(ComCity dc);

	ComProvince getProvince(ComProvince province);
}
