package com.lvmama.back.sweb.market.applycity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.market.ApplyCity;
import com.lvmama.comm.bee.service.market.ApplyCityService;

/**
 * 聚划算产品管理
 * @author zhushuying
 *
 */
@Results({
	@Result(name = "toCityList", location = "/WEB-INF/pages/back/market/applycity/applycity_list.jsp")
	})
public class ApplyCityListAction extends BaseAction{
	
 	/**
	 * 
	 */
	private static final long serialVersionUID = -1219012267482045364L;

	private ApplyCityService applyCityService;
	
	private Long applyCityId;
	private String cityName;
	private String cityPY;
	/**当前页**/
	private Integer currentPage = 1;
	
	private List<ApplyCity> applyCityList;
	
	
	@Action("/applycity/toCityList")
	public String toCityList(){
		pagination=super.initPagination();
        pagination.setPage(currentPage);
        Map<String, Object> params=new HashMap<String, Object>();
        pagination.setTotalRecords(applyCityService.getApplyCityPageCount());
        if(pagination.getTotalRecords()>0){
    		params.put("start",pagination.getFirstRow() );
    		params.put("end", pagination.getLastRow());
            applyCityList=applyCityService.getApplyCityByPage(params);
        }
		return "toCityList";
	}
	
	@Action("/applycity/addCity")
	public void addCity(){
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("cityName", cityName);
		map.put("cityPY", cityPY);
		ApplyCity city=applyCityService.selectApplyCityBy(map);
		//判断要添加的城市名称是否存在
		if(city!=null){
			sendAjaxMsg("EXIST");
			return;
		}
		ApplyCity city1=new ApplyCity();
		city1.setCityName(cityName);
		city1.setCityPY(cityPY);
		Long reg=applyCityService.addApplyCity(city1);
		if(reg>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	@Action("/applycity/delCityById")
	public void delCityById(){
		int reg=applyCityService.delApplyCity(applyCityId);
		if(reg>0){
			sendAjaxMsg("SUCCESS");
		}else{
			sendAjaxMsg("FAILED");
		}
	}
	
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public void setApplyCityService(ApplyCityService applyCityService) {
		this.applyCityService = applyCityService;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityPY() {
		return cityPY;
	}

	public void setCityPY(String cityPY) {
		this.cityPY = cityPY;
	}

	public Long getApplyCityId() {
		return applyCityId;
	}

	public void setApplyCityId(Long applyCityId) {
		this.applyCityId = applyCityId;
	}

	public List<ApplyCity> getApplyCityList() {
		return applyCityList;
	}

	public void setApplyCityList(List<ApplyCity> applyCityList) {
		this.applyCityList = applyCityList;
	}
}
