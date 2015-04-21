package com.lvmama.pet.sweb.seo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.vo.PlaceVo;
@Results({ 
	@Result(name = "placeTkd", location = "/WEB-INF/pages/back/seo/place_tkd.jsp")
	})
public class SeoPlaceTkdAction extends com.lvmama.comm.BackBaseAction{
	private static final long serialVersionUID = 5202830331787074256L;
	private PlaceService placeService;
	private String seoIsEdit = "";
	private PlaceVo placeVo;
	private Place place;
	private String autoPlaceName;

	/**
	 * 景区目的地tkd
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Action("/seo/placeTkd")
	public String placeTkd() {
		pagination=initPage();
		pagination.setPageSize(20);
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("isValid", "Y");
		if(place!=null){
	  		param.put("stage", place.getStage());
			param.put("placeId", place.getPlaceId());
			param.put("headPlaceId", place.getParentPlaceId());
			param.put("name", place.getName());
		}
		if (StringUtils.isNotEmpty(seoIsEdit)) {
			param.put("seoIsEdit", seoIsEdit);
		}
		
		pagination.setTotalResultSize(placeService.countPlaceListByParam(param));
		if(pagination.getTotalResultSize()>0){
			param.put("startRows", pagination.getStartRows());
			param.put("endRows", pagination.getEndRows());
			List<PlaceVo> list=placeService.queryPlaceAndParent(param);
			pagination.setItems(list);
		}
		pagination.buildUrl(getRequest());
		return "placeTkd";
	}

	public void placeDetail() {
			place = placeService.queryPlaceByPlaceId(place.getPlaceId());
			JSONArray jsonArray=JSONArray.fromObject(place);
			this.responseWrite(jsonArray.toString());
	}

	public void updateSeoTkd() {
		    placeService.savePlace(place);
		    this.responseWrite("{\"flag\":\"true\"}");
	}

	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}
	
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public String getSeoIsEdit() {
		return seoIsEdit;
	}

	public void setSeoIsEdit(String seoIsEdit) {
		this.seoIsEdit = seoIsEdit;
	}

	public PlaceVo getPlaceVo() {
		return placeVo;
	}

	public void setPlaceVo(PlaceVo placeVo) {
		this.placeVo = placeVo;
	}

	public String getAutoPlaceName() {
		return autoPlaceName;
	}

	public void setAutoPlaceName(String autoPlaceName) {
		this.autoPlaceName = autoPlaceName;
	}
	
}
