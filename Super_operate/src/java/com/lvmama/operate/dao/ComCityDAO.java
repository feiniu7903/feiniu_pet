package com.lvmama.operate.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComCity;

public class ComCityDAO extends BaseIbatisDAO{

	public ComCity selectByPrimaryKey(String cityId) {
		ComCity key = new ComCity();
		key.setCityId(cityId);
		ComCity record = (ComCity) super.queryForObject("OPERATE_PLACE.selectByCityKey", key);
		return record;
	}

	public List<ComCity> selectCityByProvinceId(String provinceId){
		ComCity c = new ComCity();
		c.setProvinceId(provinceId);
		List<ComCity> cityList= (List<ComCity>)super.queryForList("OPERATE_PLACE.selectByProvinceId", c);
		ComCity city = new ComCity();
		city.setCityName("请选择");
		cityList.add(0, city);
		return cityList;
	}

}