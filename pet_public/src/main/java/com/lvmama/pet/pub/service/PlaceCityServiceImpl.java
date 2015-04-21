/**
 * 
 */
package com.lvmama.pet.pub.service;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.pet.pub.dao.ComCityDAO;
import com.lvmama.pet.pub.dao.ComProvinceDAO;


public class PlaceCityServiceImpl  implements PlaceCityService{
	
	private ComProvinceDAO comProvinceDAO; 
	
	private ComCityDAO comCityDAO;
	
	public List<ComProvince> getProvinceList() {
		List<ComProvince> provinceList = comProvinceDAO.getAllProvince();
		provinceList.get(0).setChecked("true");
		return provinceList;
	}
	
	public List<ComProvince> getProvinceListSelected(String cityId) {
		List<ComProvince> provinceList = comProvinceDAO.getAllProvince();
		if (cityId!=null) {
			ComCity city = comCityDAO.selectByPrimaryKey(cityId);
			for(int i=1;i<provinceList.size();i++) {
				if (city.getProvinceId().equals(provinceList.get(i).getProvinceId()))
					provinceList.get(i).setChecked("true");
			}
		}
		return provinceList;
	}

	public ComProvince selectByPrimaryKey(String provinceId) {
		return comProvinceDAO.selectByPrimaryKey(provinceId);
	}

	public List<ComCity> getCityListByProvinceId(String provinceId) {
		List<ComCity> cityList = comCityDAO.selectCityByProvinceId(provinceId);
		cityList.get(0).setChecked("true");
		return cityList;
	}

	public ComCity selectCityByPrimaryKey(String cityId)  {
		return comCityDAO.selectByPrimaryKey(cityId);
	}

	public List<ComCity> getCityListSelected(String cityId) {
		if (cityId!=null) {
			ComCity city = comCityDAO.selectByPrimaryKey(cityId);
			List<ComCity> cityList = comCityDAO.selectCityByProvinceId(city.getProvinceId());
			for(int i=1;i<cityList.size();i++) {
				if (cityId.equals(cityList.get(i).getCityId()))
					cityList.get(i).setChecked("true");
			}
			return cityList;
		}
		return new ArrayList<ComCity>();
	}
	
	public String getCapitalOrCityIdByName(String name) {
		ComProvince comProvince = comProvinceDAO.selectByProvinceName(name);
		if (comProvince!=null) {
			return comProvince.getProvinceId();
		} else{//省没找到找城市
			List<ComCity> cityList=comCityDAO.selectByCityName(name);
			if (cityList.size() == 1) {
				return cityList.get(0).getCityId();
			}
		}
		return "";
	}

	public void setComProvinceDAO(ComProvinceDAO comProvinceDAO) {
		this.comProvinceDAO = comProvinceDAO;
	}



	@Override
	public ComProvince selectByProvinceName(String name) {
		return comProvinceDAO.selectByProvinceName(name);
	}
	
	public List<ComCity> selectCityByName(String cityName) {
		return comCityDAO.selectByCityName(cityName);
	}

	@Override
	public ComCity selectCityByNameAndCity(String provinceId, String cityName) {
		return comCityDAO.selectCityByNameAndCity(provinceId, cityName);
	}

	@Override
	public ComCity selectByProvinceAndName(String provinceId, String name) {
		return comCityDAO.selectCityByNameAndCity(provinceId, name);
	}

	public void setComCityDAO(ComCityDAO comCityDAO) {
		this.comCityDAO = comCityDAO;
	}

}
