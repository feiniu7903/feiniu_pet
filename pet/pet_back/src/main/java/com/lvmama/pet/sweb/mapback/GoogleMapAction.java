package com.lvmama.pet.sweb.mapback;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceCoordinateGoogle;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;

/**
 * 百度地图后台Action
 *
 */
@Results( {
	@Result(name = "GoogleMapList", location = "/WEB-INF/pages/back/mapBack/googleMapList.jsp"),
	@Result(name = "modifyCoordinate", location = "/WEB-INF/pages/back/mapBack/googleModifyCoordinate.jsp")
	})
public class GoogleMapAction extends BackBaseAction {
	private static final long serialVersionUID = -1332794638465213372L;
	private PlaceCoordinateGoogleService placeCoordinateGoogleService;
	private PlaceService placeService;
	private String isValidRadio = "isMarkValid";
	private int pageSize = 19;
	private String placeName="";
	private String placeId="";
	private Double longitude;
	private Double latitude;
	String coordinateAddress;
	String isValid;

	private PlaceCoordinateVo placeCoordinateVo = new PlaceCoordinateVo();
	
	/**
	 * 查看百度地图列表
	 */
	@Action("/queryGoogleMapList")
	public String queryGoogleMapList() {
		Map<String, Object> params = initParams();
		pagination=initPage();
		pagination.setPageSize(pageSize);		
		pagination.setTotalResultSize(placeCoordinateGoogleService.getGoogleMapListByParamsCount(params));
		if(pagination.getTotalResultSize()>0){
			params.put("_startRow", pagination.getStartRows());
			params.put("_endRow", pagination.getEndRows());
			List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateGoogleService.getGoogleMapListByParams(params);			
			pagination.setItems(placeCoordinateVoList);
		}
		//通过REQUEST直接做分页URL拼接
		pagination.buildUrl(getRequest());
		return "GoogleMapList";
	}
	
	/**
	 * 更改景点/酒店的坐标有效性
	 * 
	 * @throws IOException
	 */
	@Action("/updatePlaceCoordinatGoogle")
	public void updatePlace() throws IOException {
		String id=this.getRequest().getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", id);
		List<PlaceCoordinateGoogle> placeCoordinateGoogleList = placeCoordinateGoogleService.getPlaceCoordinateGoogleByParam(params);
		if (placeCoordinateGoogleList.size()==0) {
			Place p = placeService.queryPlaceByPlaceId(Long.parseLong(id));
			PlaceCoordinateGoogle placeCoordinateGoogle = new PlaceCoordinateGoogle();
			placeCoordinateGoogle.setUpdateTime(p.getUpdateTime());
			placeCoordinateGoogle.setStage(p.getStage());
			placeCoordinateGoogle.setPlaceId(p.getPlaceId());
			placeCoordinateGoogle.setIsValid(this.getRequest().getParameter("isValid"));
			placeCoordinateGoogle.setCoordinateName(p.getName());
			placeCoordinateGoogleService.insertPlaceCoordinateGoogle(fillPlaceCoordinate(placeCoordinateGoogle));
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"Y\",\"id\":" + id +"}");
		} else {
			PlaceCoordinateGoogle placeCoordinateGoogle = placeCoordinateGoogleList.get(0);
			placeCoordinateGoogle.setIsValid(this.getRequest().getParameter("isValid"));
			placeCoordinateGoogleService.updatePlaceCoordinateGoogle(fillPlaceCoordinate(placeCoordinateGoogle));
			
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"N\",\"id\":" + id + ",\"msg\":\"此景区坐标已存在 您的本次提交已经覆盖之前的坐标\"}");
		}
	}	
	
	/**
	 * 标记为境外酒店/景点 境外酒店/景点 不会在百度地图列表页面显示
	 * 
	 * @throws IOException
	 */
	@Action("/updateGooglePlaceType")
	public void updatePlaceType() throws IOException {
		String id = this.getRequest().getParameter("id");
		Place p = this.placeService.queryPlaceByPlaceId(Long.parseLong(id));
		p.setPlaceType("ABROAD");
		this.placeService.savePlace(p);
		this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
		this.getResponse().getWriter().write("{\"flag\":\"Y\"}");
	}
	
	/**
	 * 根据placeId查坐标展示地图
	 * 
	 * @return
	 */
	@Action("/modifyGoogleCoordinate")
	public String queryCoordinateByPlaceId() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		params.put("_startRow", 0);
		params.put("_endRow", 1);
		List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateGoogleService.getGoogleMapListByParams(params);
		if (placeCoordinateVoList.size() > 0) {
			placeCoordinateVo = placeCoordinateVoList.get(0);
		}
		return "modifyCoordinate";
	}
	
	/**
	 * 更新为百度解析的坐标和百度反解析的地址
	 * 
	 * @return
	 */
	private PlaceCoordinateGoogle fillPlaceCoordinate(PlaceCoordinateGoogle p) {		
		if (longitude != null) {
			p.setLongitude(longitude);
		}
		if (latitude != null) {
			p.setLatitude(latitude);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			p.setIsValid(isValid);
		}
		if (StringUtils.isNotEmpty(coordinateAddress)) {
			p.setCoordinateAddress(coordinateAddress);
		}
		p.setUpdateTime(new Date());

		return p;
	}

	private Map<String, Object> initParams() {
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(placeName)) {
            params.put("placeName", placeName);
        }
		if (StringUtils.isNotEmpty(placeId)) {
            params.put("placeId", placeId);
        }
		if (StringUtils.isNotEmpty(isValidRadio)) {
			if ("isCoordinateEmpty".equals(isValidRadio)) {
				params.put("isCoordinateEmpty", "isCoordinateEmpty");
			}
			if ("isMarkValid".equals(isValidRadio)) {
				params.put("isMarkValid", "isMarkValid");
			}
			
        }
		
		return params;
	}

	public PlaceCoordinateGoogleService getPlaceCoordinateGoogleService() {
		return placeCoordinateGoogleService;
	}

	public void setPlaceCoordinateGoogleService(PlaceCoordinateGoogleService placeCoordinateGoogleService) {
		this.placeCoordinateGoogleService = placeCoordinateGoogleService;
	}

	public PlaceService getPlaceService() {
		return placeService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public String getIsValidRadio() {
		return isValidRadio;
	}

	public void setIsValidRadio(String isValidRadio) {
		this.isValidRadio = isValidRadio;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public String getPlaceId() {
		return placeId;
	}

	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}

	public PlaceCoordinateVo getPlaceCoordinateVo() {
		return placeCoordinateVo;
	}

	public void setPlaceCoordinateVo(PlaceCoordinateVo placeCoordinateVo) {
		this.placeCoordinateVo = placeCoordinateVo;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public String getCoordinateAddress() {
		return coordinateAddress;
	}

	public void setCoordinateAddress(String coordinateAddress) {
		this.coordinateAddress = coordinateAddress;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

}
