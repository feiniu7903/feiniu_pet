package com.lvmama.pet.sweb.place;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;

public final class AjaxPlaceSearchAction extends BackBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8567264056134401216L;
	private PlaceService placeService;
	private String search;

	@Action("/place/queryPlace")
	public void queryVisaCountry() throws IOException {
		JSONArray json = new JSONArray();
		Map<String, Object> param =new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			param.put("name", search);
		}
		if (param.isEmpty()) {
			return;
		}
		
		param.put("isValid", "Y");
		param.put("stage", "2");
		List<Place> placeList = placeService.queryPlaceListByParam(param);
		for (Place place : placeList) {
			JSONObject object = new JSONObject();
			object.put("id", place.getPlaceId());
			object.put("text", place.getName());
			object.put("cmtTitle", place.getCmtTitle());
			json.add(object);
		}
		getResponse().getWriter().print(json.toString());
	}
	
	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

}

