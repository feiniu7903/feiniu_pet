/**
 * 
 */
package com.lvmama.pet.sweb.com;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.service.pub.PlaceCityService;

/**
 * 城市数据加载
 * @author yangbin
 *
 */
@ParentPackage("json-default")
public class CityLoadAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7700323909998896127L;
	/**
	 * 省市接口
	 */
	private PlaceCityService placeCityService;
	
	private String provinceId;
	private List<ComCity> cityList;
	
	@Action(value="/common/loadCitys",results=@Result(name="city",type="json",params={"includeProperties","cityList\\[\\d+\\]\\.cityId,cityList\\[\\d+\\]\\.cityName"}))
	public String loadCitys(){
		cityList=placeCityService.getCityListByProvinceId(provinceId);		
		return "city";
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public List<ComCity> getCityList() {
		return cityList;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	
	
}
