package com.lvmama.comm.pet.service.pub;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;

public interface PlaceCityService {
	
	List<ComProvince> getProvinceList();
	
	List<ComProvince> getProvinceListSelected(String cityId);
	
	ComProvince selectByPrimaryKey(String provinceId) ;
	
	List<ComCity> getCityListByProvinceId(String provinceId);
	
	List<ComCity> getCityListSelected(String cityId);
	
	ComCity selectCityByPrimaryKey(String cityId) ;
	
	ComProvince selectByProvinceName(String name);
	
	ComCity selectCityByNameAndCity(String provinceId,String cityName);
	
	String getCapitalOrCityIdByName(String cityName);
	
	ComCity selectByProvinceAndName(String provinceId, String name);
	List<ComCity> selectCityByName(String cityName);
}
