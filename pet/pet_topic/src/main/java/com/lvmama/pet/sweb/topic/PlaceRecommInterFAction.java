package com.lvmama.pet.sweb.topic;

import java.io.IOException;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.vo.Constant;


public class PlaceRecommInterFAction extends BaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -63921717994038866L;
	private Long placeId;
  	
 	private PlaceService placeService;
    @Action("/placeRecomm/placeRecommendInfoJson")
	public void getRecommendInfoJson() {
	    Place p=placeService.queryPlaceByPlaceId(placeId);
	    String json = null;
		if (p != null) {
			if (p.getStage().equals(
					Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode())) {
				List<Place> result = placeService
						.getPlaceInfoBySameParentPlaceId(
								p.getPlaceId(),
								Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode(),
								6L,p.getPlaceId().toString());
				json = net.sf.json.JSONArray.fromObject(result).toString();

			}else if(p.getStage().equals(
					Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode())){
				List<Place> result = placeService
						.getPlaceInfoBySameParentPlaceId(
								p.getParentPlaceId(),
								Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode(),
								6L,p.getPlaceId().toString());
				json = net.sf.json.JSONArray.fromObject(result).toString();
			}
		}
	    outputJsonToClient("callback",json);
	}
    /**
	 * 输出json数据到客户端
	 * 
	 * @param param
	 * @param jsonMsg
	 */
	private void outputJsonToClient(String param, String jsonMsg) {
		String jsoncallback = this.getRequest().getParameter(param);// 必要
		getResponse().setContentType("application/json; charset=UTF-8");
		try {
			getResponse().getWriter().write(jsoncallback + "(" + jsonMsg + ")");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @return the placeId
	 */
	public Long getPlaceId() {
		return placeId;
	}
	/**
	 * @param placeId the placeId to set
	 */
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	/**
	 * @return the placeService
	 */
	public PlaceService getPlaceService() {
		return placeService;
	}
	/**
	 * @param placeService the placeService to set
	 */
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}
 
}
