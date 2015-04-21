package com.lvmama.back.utils;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;

public class MacorsCity extends BaseAction {
	
	private PlaceCityService placeCityService;
	
	private List<ComProvince> provinceList;
	
	private List<ComCity> cityList = new ArrayList<ComCity>();
	
	private String provinceId;
	
	private String cityId;
	
	protected void doAfter() throws Exception {
		if (cityId!=null) {
			provinceList = placeCityService.getProvinceListSelected(cityId);
			cityList = placeCityService.getCityListSelected(cityId);
		}else{
			provinceList = placeCityService.getProvinceList();
		}
	}

	public List<ComProvince> getProvinceList() {
		return provinceList;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}
	
	public void changeProvince(String provinceId) {
		this.provinceId = provinceId;
		if (provinceId!=null) {
			cityList = placeCityService.getCityListByProvinceId(provinceId);
		}
	} 
	
	protected String getComponetName() {
		return "pcityAction";
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	
	


}
