package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;

public interface ComCityService {
	/**
	 *根据城市id查找城市记录
	 * @param cityId
	 * @return
	 */
	ComCity selectByPrimaryKey(String cityId);
	
	List<ComCity> selectCityByProvinceId(String provinceId);
	
	ComCity selectCityByNameAndCity(String provinceId,String cityName);
	
	List<ComCity> selectByCityName(String city);
}
