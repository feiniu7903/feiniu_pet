package com.lvmama.pet.sweb.place;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceSearchPinyin;
import com.lvmama.comm.pet.service.place.PlaceSearchPinyinService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComSearchInfoUpdateService;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

@Results( {
	@Result(name = "addPlacePinyin", location = "/WEB-INF/pages/back/place/comm/place_pinyin_add.jsp"),
	@Result(name = "addPlaceNamePinyin", location = "/WEB-INF/pages/back/place/comm/place_name_pinyin_add.jsp")
	})
public class PlaceSearchPinyinAction extends BackBaseAction {
	
	/**
	 * 每次修改place信息之后需要向COM_SEARCH_INFO_UPDATE表中增加一条记录，用于同步PLACE表与PLACE_SEARCH_INFO的信息
	 * add by yanggan 
	 */
	private ComSearchInfoUpdateService comSearchInfoUpdateService;
	
	private static final long serialVersionUID = -1933262201783089923L;
	private PlaceSearchPinyin placeSearchPinyin = new PlaceSearchPinyin();
	private List<PlaceSearchPinyin> pinyinList = new ArrayList<PlaceSearchPinyin>();
	private PlaceSearchPinyinService placeSearchPinyinService;
	private PlaceService placeService;
	private ComLogService comLogService;
	private Place place;
	private String content;

	@Action("/place/toAddPlacePinyin")
	public String toAddHFKWPlacePinyin() {
		place=placeService.queryPlaceByPlaceId(placeSearchPinyin.getObjectId());
		pinyinList = placeSearchPinyinService.queryPlacePinyinList(placeSearchPinyin);
		
		if (placeSearchPinyin.getObjectType() !=null && placeSearchPinyin.getObjectType().indexOf("HFKW") > 0) {
			return "addPlacePinyin";
		} else {
			return "addPlaceNamePinyin";
		}
	}
	
	@Action("/place/showObjectPinyinList")
	public String showObjectPinyinList() {
		pinyinList = placeSearchPinyinService.queryPinyinListByName(placeSearchPinyin.getObjectName());
		
		JSONArray array = JSONArray.fromObject(pinyinList);
		JSONResult result=new JSONResult();
		result.put("list", array);
		result.output(ServletActionContext.getResponse());
		
		return null;
	}
	
	@Action("/place/saveOrUpdatePlacePinYinName")
	public void saveOrUpdatePlacePinYinName() throws IOException {
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		
		placeSearchPinyinService.saveOrUpdate(placeSearchPinyin);	
		content="操作保存或修改名称及拼音";
		Place place = placeService.queryPlaceByPlaceId(placeSearchPinyin.getObjectId());
		if (null != place) {
			json.put("success", true);
			json.put("name", place.getName());
			json.put("pinYin", placeSearchPinyin.getPinYin());
			json.put("pinYinUrl", place.getPinYinUrl());
			
			comSearchInfoUpdateService.placeUpdated(place.getPlaceId(),place.getStage());
		}
		getResponse().getWriter().print(json.toString());
	}
	
	@Action("/place/saveOrUpdateHFKWPlacePinYin")
	public void saveOrUpdateHFKWPlacePinYin() throws IOException {
		placeSearchPinyinService.saveOrUpdate(placeSearchPinyin);
		content = "操作保存或修改高频关键字";
		getJsonPlaceSearchPinyinList();
	}	
	
	@Action("/place/deletePlaceSearchPinyin")
	public void deleteHFKWPlaceSearchPinyin() throws IOException {
		if (null != placeSearchPinyin 
				&& null != placeSearchPinyin.getPlaceSearchPinyinId()) {
			placeSearchPinyinService.deleteHFKWPlaceSearchPinyin(placeSearchPinyin);
		}
		content = "删除高频关键字";
		getJsonPlaceSearchPinyinList();
	}
	
	/**
	 * 获得json对象的拼音列表
	 */
	private void getJsonPlaceSearchPinyinList() throws IOException {		
		JSONObject json = new JSONObject();
		json.put("success", false);
		json.put("message", "");
		
		PlaceSearchPinyin param = new PlaceSearchPinyin();
		param.setObjectId(placeSearchPinyin.getObjectId());
		param.setObjectType(placeSearchPinyin.getObjectType());	
		json.put("list", JSONArray.fromObject(placeSearchPinyinService.queryPlacePinyinList(param)));
		
		Place place = this.placeService.queryPlaceByPlaceId(placeSearchPinyin.getObjectId());
		if (null != place) {
			json.put("HFKW", place.getHfkw());
		} else {
			json.put("HFKW", "");
		}
		comSearchInfoUpdateService.placeUpdated(placeSearchPinyin.getObjectId(),place.getStage());
		json.put("success", true);
		getResponse().getWriter().print(json.toString());
		if("操作保存或修改高频关键字".equals(content)){
			comLogService.insert("SCENIC_LOG_PLACE", null,placeSearchPinyin.getObjectId(), super.getSessionUserName(),
					Constant.SCENIC_LOG_PLACE.saveORupdateHFKWbyPinyin.name(),"操作高频关键字", content+"值为【"+place.getHfkw()+"】", "");
		}
		
		
		if("删除高频关键字".equals(content)){
			comLogService.insert("SCENIC_LOG_PLACE", null,placeSearchPinyin.getObjectId(), super.getSessionUserName(),
					Constant.SCENIC_LOG_PLACE.saveORupdateHFKWbyPinyin.name(),"操作高频关键字", content+"值为【"+place.getHfkw()+"】", "");
		}
		
		if("操作保存或修改名称及拼音".equals(content)){
			comLogService.insert("SCENIC_LOG_PLACE", null,placeSearchPinyin.getObjectId(), super.getSessionUserName(),
					Constant.SCENIC_LOG_PLACE.updateNamePinyin.name(),"操作保存或修改名称及拼音", "操作保存或修改名称及拼音"+"值为【"+place.getName()+"】,拼音为【"+ placeSearchPinyin.getPinYin()+"】;", "");
		
		}
		
	}
	
	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}
	
	public void setPlaceSearchPinyinService(
			PlaceSearchPinyinService placeSearchPinyinService) {
		this.placeSearchPinyinService = placeSearchPinyinService;
	}

	public PlaceSearchPinyin getPlaceSearchPinyin() {
		return placeSearchPinyin;
	}

	public void setPlaceSearchPinyin(PlaceSearchPinyin placeSearchPinyin) {
		this.placeSearchPinyin = placeSearchPinyin;
	}

	public List<PlaceSearchPinyin> getPinyinList() {
		return pinyinList;
	} 
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public String getPlaceNamePinYin() {
		if (null != pinyinList && !pinyinList.isEmpty()) {
			return pinyinList.get(0).getPinYin();
		} else {
			return null;
		}
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setComSearchInfoUpdateService(ComSearchInfoUpdateService comSearchInfoUpdateService) {
		this.comSearchInfoUpdateService = comSearchInfoUpdateService;
	}
}
