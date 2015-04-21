package com.lvmama.pet.sweb.place;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceAirport;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.json.JSONOutput;

/**
 * 机场管理
 * 
 * @author
 * 
 */
@Results({
		@Result(name = "airportList", location = "/WEB-INF/pages/back/place/airport/airportList.jsp"),
		@Result(name = "airportAdd", location = "/WEB-INF/pages/back/place/airport/airportAdd.jsp"),
		@Result(name = "airportEdit", location = "/WEB-INF/pages/back/place/airport/airportEdit.jsp")})
public class PlaceAirportAction extends BackBaseAction {
	private static final long serialVersionUID = -1933262201783089923L;

	// 查询条件
	private PlaceAirport placeAirport;
	// 结果集
	private List<PlaceAirport> placeAirportList;

	private PlaceFlightService placeFlightService;
	
	private PlaceService placeService;
	

	private String enName;

	private String airportCode;

	private String airportName;
	// 城市名称
	private String search;
	//城市编码 
	private String cityCode;
	private String placeId;

	private Long placeAirportId;

	/**
	 * 初始化查询参数
	 * 
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(this.cityCode)){
			param.put("cityCode", cityCode.trim());
		}
		if(StringUtils.isNotEmpty(this.placeId)){
			param.put("placeId", placeId.trim());
		}
		if(StringUtils.isNotEmpty(this.airportName)){
			param.put("airportName", airportName.trim());
		}
		if(StringUtils.isNotEmpty(this.enName)){
			param.put("enName", enName.trim());
		}
		return param;
	}

	@Action("/place/airportList")
	public String airportList() {
		Map<String, Object> param = initParam();
		pagination = initPage();
 		param.put("startRows", pagination.getStartRows());
		param.put("endRows", pagination.getEndRows());
		pagination.setTotalResultSize(this.placeFlightService
				.countPlaceAirportListByParam(param));
		placeAirportList = this.placeFlightService
				.queryPlaceAirportListByParam(param);
 		pagination.buildUrl(getRequest());
		return "airportList";
	}

	@Action("/place/airportAdd")
	public String airportAdd() {
		return "airportAdd";
	}
	
	@Action("/place/airportEdit")
	public String airportEdit() {
		if(this.placeAirportId!=null){
			this.placeAirport = placeFlightService.getPlaceAirportByKey(placeAirportId);
			if(this.placeAirport !=null){
				Place place= placeService.queryPlaceByPlaceId(this.placeAirport.getPlaceId());
				this.placeAirport.setCityCode(place.getAirportCode());
				this.placeAirport.setCityName(place.getName());
				this.search=this.placeAirport.getCityName();
			}
		}
		return "airportEdit";
	}
	@Action("/place/airportSave")
	public void airportSave() {
		Map<String,Object> map=new HashMap<String, Object>();
		try{
			placeFlightService.savePlaceAirport(placeAirport);
			Place place= placeService.queryPlaceByPlaceId(placeAirport.getPlaceId());
			if(place.getAirportCode()==null||!place.getAirportCode().equals(placeAirport.getCityCode())){
				place.setAirportCode(placeAirport.getCityCode());
				placeService.savePlace(place);
			}
			map.put("success", true);
			map.put("message", "添加成功！");
		}catch(Exception e){
			map.put("success", false);
			map.put("message", "添加失败！");
		}finally{
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}
	@Action("/place/airportUpdate")
	public void airportUpdate() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlaceAirport p = placeFlightService
					.getPlaceAirportByKey(placeAirport.getPlaceAirportId());
			if (p != null) {
				p.setAirportCode(placeAirport.getAirportCode());
				p.setAirportName(placeAirport.getAirportName());
				p.setEnName(placeAirport.getEnName());
				p.setPlaceId(placeAirport.getPlaceId());
				placeFlightService.updatePlaceAirport(p);
				Place place= placeService.queryPlaceByPlaceId(placeAirport.getPlaceId());
				if(place.getAirportCode()==null||!place.getAirportCode().equals(placeAirport.getCityCode())){
					place.setAirportCode(placeAirport.getCityCode());
					placeService.savePlace(place);
				}
				map.put("success", true);
				map.put("message", "更新成功！");
			} else {
				map.put("success", false);
				map.put("message", "不存在该机场！");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "更新失败！");
		} finally {
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}

	@Action("/place/airportDel")
	public void airportDel() {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			PlaceAirport p = placeFlightService.getPlaceAirportByKey(this.placeAirportId);
			if (p != null) {
				placeFlightService.deletePlaceAirportByPlaceAirportId(p.getPlaceAirportId());
				map.put("success", true);
				map.put("message", "删除成功！");
			}else{
				map.put("success", false);
				map.put("message", "不存在该机场！");
			}
		} catch (Exception e) {
			map.put("success", false);
			map.put("message", "删除失败！");
		} finally {
			this.sendAjaxResultByJson(JSONObject.fromObject(map).toString());
		}
	}

	@Action("/place/searchAirportTableList")
	public String searchAirportList() throws Exception {
		return "searchAirportTableList";
	}
	
	@Action("/place/searchPlace")
	public void searchPlace(){		
		Map<String,Object> param=new HashMap<String, Object>();
		param.put("name", search);
		param.put("stage", 1);
		List<Place> list = this.placeService.queryPlaceListByParam(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(Place cp:list){
				JSONObject obj=new JSONObject();
				obj.put("id", cp.getPlaceId());
				obj.put("text", cp.getName());
				obj.put("airportCode", cp.getAirportCode());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	
	@Action("/place/placeAirportBysearch")
	public void placeAirportBysearch(){
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("airportName", search);
		}
		param.put("_startRow",0);
		param.put("_endRow", ""+10);
		List<PlaceAirport> list =placeFlightService.queryPlaceAirportListByParam(param);
		JSONArray array=new JSONArray();
		if(CollectionUtils.isNotEmpty(list)){
			for(PlaceAirport ss:list){
				JSONObject obj=new JSONObject();
				obj.put("id", ss.getPlaceAirportId());
				obj.put("text", ss.getAirportName());
				obj.put("placeId",ss.getPlaceId());
				array.add(obj);
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	public PlaceAirport getPlaceAirport() {
		return placeAirport;
	}

	public void setPlaceAirport(PlaceAirport placeAirport) {
		this.placeAirport = placeAirport;
	}

	public List<PlaceAirport> getPlaceAirportList() {
		return placeAirportList;
	}

	public void setPlaceAirportList(List<PlaceAirport> placeAirportList) {
		this.placeAirportList = placeAirportList;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public String getEnName() {
		return enName;
	}

	public void setEnName(String enName) {
		this.enName = enName;
	}

	public String getAirportCode() {
		return airportCode;
	}

	public void setAirportCode(String airportCode) {
		this.airportCode = airportCode;
	}

	public String getAirportName() {
		return airportName;
	}

	public void setAirportName(String airportName) {
		this.airportName = airportName;
	}


	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public Long getPlaceAirportId() {
		return placeAirportId;
	}

	public void setPlaceAirportId(Long placeAirportId) {
		this.placeAirportId = placeAirportId;
	}

}
