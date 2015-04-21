package com.lvmama.pet.pub.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComCity;

public class ComCityDAO extends BaseIbatisDAO{

	public ComCity selectByPrimaryKey(String cityId) {
		ComCity key = new ComCity();
		key.setCityId(cityId);
		ComCity record = (ComCity) super.queryForObject("COM_CITY.selectByPrimaryKey", key);
		return record;
	}

	public List<ComCity> selectCityByProvinceId(String provinceId){
		ComCity c = new ComCity();
		c.setProvinceId(provinceId);
		List<ComCity> cityList= super.queryForList("COM_CITY.selectByProvinceId", c);
		ComCity city = new ComCity();
		city.setCityName("请选择");
		cityList.add(0, city);
		return cityList;
	}
	
	
	public ComCity selectCityByNameAndCity(String provinceId,String cityName){
		Map<String, String> map=new HashMap<String, String>();
		map.put("provinceId", provinceId);
		map.put("cityName", cityName);
		List<ComCity> list=super.queryForList("COM_CITY.selectCityByNameAndCity",map);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0);
		}
		return null;
	}
	public List<ComCity> selectByCityName(String cityName){
		Map<String, String> map=new HashMap<String, String>();
		map.put("cityName", cityName);
		List<ComCity> list=super.queryForList("COM_CITY.selectCityByNameAndCity",map);
		return list;		
	}	

}