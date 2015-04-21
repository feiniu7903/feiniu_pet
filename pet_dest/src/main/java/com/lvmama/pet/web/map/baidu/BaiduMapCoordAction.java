package com.lvmama.pet.web.map.baidu;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;

@Results( {
	@Result(name = "freenessMap", location = "/WEB-INF/pages/map/supermap.jsp"),
	@Result(name = "scenicMap", location = "/WEB-INF/pages/baiduMap/baidu_map.jsp"),
	@Result(name = "baiduSimpleMapCoordinate", location = "/WEB-INF/pages/baiduMap/simple_baidu_map.jsp")
})
public class BaiduMapCoordAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private String id;
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
	private PlaceCoordinateVo placeCoordinateVo = new PlaceCoordinateVo();
	private String width="300px";
	private String height="300px";
	private String navigationControlFlag = "true";
	
	
	/**
	 * 获取景区地图(新地图)
	 */
	@Action("/baiduMap/getBaiduMapCoordinate")
	public String getBaiduMapCoordinate() {	
		if(StringUtils.isNotEmpty(id)) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("placeId", id);
			params.put("_startRow", 0);
			params.put("_endRow", 1);
			List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateBaiduService.getBaiduMapListByParams(params);
			if (placeCoordinateVoList!=null && placeCoordinateVoList.size() > 0) {
				placeCoordinateVo = placeCoordinateVoList.get(0);
			}
		}
		return "scenicMap";
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public PlaceCoordinateVo getPlaceCoordinateVo() {
		return placeCoordinateVo;
	}


	public void setPlaceCoordinateVo(PlaceCoordinateVo placeCoordinateVo) {
		this.placeCoordinateVo = placeCoordinateVo;
	}


	public void setPlaceCoordinateBaiduService(
			PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
	}


	public String getWidth() {
		return width;
	}


	public void setWidth(String width) {
		this.width = width;
	}


	public String getHeight() {
		return height;
	}


	public void setHeight(String height) {
		this.height = height;
	}


	public String getNavigationControlFlag() {
		return navigationControlFlag;
	}


	public void setNavigationControlFlag(String navigationControlFlag) {
		this.navigationControlFlag = navigationControlFlag;
	}

}
