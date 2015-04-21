package com.lvmama.back.sweb.common;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.service.pub.PlaceCityService;


@ParentPackage("json-default")
public class LoadCityProvinceAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4877995503888935754L;
	private PlaceCityService placeCityService;
	private List<ComProvince> provinceList;
	private List<ComCity> cityList;
	private String cityId;
	private String provinceId;
	private ComCity comCity;
	
	@Action(value="/common/loadProvince",results=@Result(type="json",name="provinceList",params={"includeProperties","provinceList\\[\\d+\\]\\.provinceName,provinceList\\[\\d+\\]\\.provinceId"}))
	public String loadProvince(){
		this.provinceList = placeCityService.getProvinceList();
		return "provinceList";
	}
	
	@Action(value="/common/loadCities",results=@Result(type="json",name="cityList",params={"includeProperties","cityList\\[\\d+\\]\\.cityName,cityList\\[\\d+\\]\\.cityId"}))
	public String loadCities(){
		this.cityList = this.placeCityService.getCityListByProvinceId(provinceId);
		return "cityList";
	}
	
	@Action(value="/common/loadCity",results=@Result(type="json",name="city",params={"includeProperties","comCity.*"}))
	public String loadCityProvince(){
		//this.placeCityService.
		comCity = this.placeCityService.selectCityByPrimaryKey(cityId);
		return "city";
	}

	public ComCity getComCity() {
		return comCity;
	}

	public void setComCity(ComCity comCity) {
		this.comCity = comCity;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setCityList(List<ComCity> cityList) {
		this.cityList = cityList;
	}

	public List<ComProvince> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<ComProvince> provinceList) {
		this.provinceList = provinceList;
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}
	
}
