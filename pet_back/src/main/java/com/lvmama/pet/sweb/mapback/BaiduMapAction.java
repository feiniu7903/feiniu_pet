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
import com.lvmama.comm.pet.po.place.PlaceCoordinateBaidu;
import com.lvmama.comm.pet.service.place.PlaceCoordinateBaiduService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.vo.PlaceCoordinateVo;

/**
 * 百度地图后台Action
 *
 */
@Results( {
	@Result(name = "baiduMapList", location = "/WEB-INF/pages/back/mapBack/baiduMapList.jsp"),
	@Result(name = "modifyCoordinate", location = "/WEB-INF/pages/back/mapBack/baiduModifyCoordinate.jsp")
	})
public class BaiduMapAction extends BackBaseAction {
	private static final long serialVersionUID = -1332794638465213372L;
	private PlaceCoordinateBaiduService placeCoordinateBaiduService;
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
	@Action("/queryBaiduMapList")
	public String queryBaiduMapList() {
		Map<String, Object> params = initParams();
		pagination=initPage();
		pagination.setPageSize(pageSize);		
		pagination.setTotalResultSize(placeCoordinateBaiduService.getBaiduMapListByParamsCount(params));
		if(pagination.getTotalResultSize()>0){
			params.put("_startRow", pagination.getStartRows());
			params.put("_endRow", pagination.getEndRows());
			List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateBaiduService.getBaiduMapListByParams(params);			
			pagination.setItems(placeCoordinateVoList);
		}
		//通过REQUEST直接做分页URL拼接
		pagination.buildUrl(getRequest());
		return "baiduMapList";
	}
	
	/**
	 * 更改景点/酒店的坐标有效性
	 * 
	 * @throws IOException
	 */
	@Action("/updatePlaceCoordinatBaidu")
	public void updatePlace() throws IOException {
		String id=this.getRequest().getParameter("id");
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", id);
		List<PlaceCoordinateBaidu> placeCoordinateBaiduList = placeCoordinateBaiduService.getPlaceCoordinateBaiduByParam(params);
		if (placeCoordinateBaiduList.size()==0) {
			Place p = placeService.queryPlaceByPlaceId(Long.parseLong(id));
			PlaceCoordinateBaidu placeCoordinateBaidu = new PlaceCoordinateBaidu();
			placeCoordinateBaidu.setUpdateTime(p.getUpdateTime());
			placeCoordinateBaidu.setStage(p.getStage());
			placeCoordinateBaidu.setPlaceId(p.getPlaceId());
			placeCoordinateBaidu.setIsValid(this.getRequest().getParameter("isValid"));
			placeCoordinateBaidu.setCoordinateName(p.getName());
			placeCoordinateBaiduService.insertPlaceCoordinateBaidu(fillPlaceCoordinate(placeCoordinateBaidu));
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"Y\",\"id\":" + id +"}");
		} else {
			PlaceCoordinateBaidu placeCoordinateBaidu = placeCoordinateBaiduList.get(0);
			placeCoordinateBaidu.setIsValid(this.getRequest().getParameter("isValid"));
			placeCoordinateBaiduService.updatePlaceCoordinateBaidu(fillPlaceCoordinate(placeCoordinateBaidu));
			
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"N\",\"id\":" + id + ",\"msg\":\"此景区坐标已存在 您的本次提交已经覆盖之前的坐标\"}");
		}		
	}	
	
	/**
	 * 标记为境外酒店/景点 境外酒店/景点 不会在百度地图列表页面显示
	 * 
	 * @throws IOException
	 */
	@Action("/updatePlaceType")
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
	@Action("/modifyCoordinate")
	public String queryCoordinateByPlaceId() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("placeId", placeId);
		params.put("_startRow", 0);
		params.put("_endRow", 1);
		List<PlaceCoordinateVo> placeCoordinateVoList = placeCoordinateBaiduService.getBaiduMapListByParams(params);
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
	private PlaceCoordinateBaidu fillPlaceCoordinate(PlaceCoordinateBaidu p) {		
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

	public PlaceCoordinateBaiduService getPlaceCoordinateBaiduService() {
		return placeCoordinateBaiduService;
	}

	public void setPlaceCoordinateBaiduService(PlaceCoordinateBaiduService placeCoordinateBaiduService) {
		this.placeCoordinateBaiduService = placeCoordinateBaiduService;
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
