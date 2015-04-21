package com.lvmama.pet.sweb.mapback;

import java.io.IOException;
import java.sql.SQLException;
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
import com.lvmama.comm.pet.po.place.PlaceLandMark;
import com.lvmama.comm.pet.service.place.PlaceLandMarkService;
import com.lvmama.comm.pet.service.place.PlaceService;
 
/**
 * 地标百度地图后台Action
 * @yuzhizeng 
 */
@Results( {
	@Result(name = "landMarkMapList", location = "/WEB-INF/pages/back/place/landMark/land_mark_map_list.jsp"),
	@Result(name = "modifyPlaceLandMarkCoordinate", location = "/WEB-INF/pages/back/place/landMark/land_mark_modify_coordinate.jsp")
	})
public class LandMarkMapAction extends BackBaseAction {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = -1636907708012744700L;
	
	private PlaceLandMarkService placeLandMarkService;
	private PlaceService placeService;
	
	private String isValidRadio = "isMarkValid";
	private int pageSize = 20;
	private String landMarkName="";
	private long placeLandMarkId;
	private Double longitude;
	private Double latitude;
	private String coordinateAddress;
	private String isValid;

	private PlaceLandMark placeLandMark = new PlaceLandMark();
	
	/**
	 * 查看百度地图列表
	 * @throws SQLException 
	 */
	@Action("/place/queryLandMarkMapList")
	public String queryBaiduMapList() throws SQLException {
		Map<String, Object> params = initParams();
		pagination = initPage();
		pagination.setPageSize(pageSize);
		pagination.setTotalResultSize(placeLandMarkService.selectByParamsCount(params));
		
		if (pagination.getTotalResultSize() > 0) {
			params.put("_startRow", pagination.getStartRows());
			params.put("_endRow", pagination.getEndRows());
			List<PlaceLandMark> placeLandMarkList = placeLandMarkService.selectByParams(params);
			pagination.setItems(placeLandMarkList);
		}
		//通过REQUEST直接做分页URL拼接
		pagination.buildUrl(getRequest());
		return "landMarkMapList";
	}
	
	/**
	 * 更改地标纬度有效性
	 * 
	 * @throws IOException
	 * @throws SQLException 
	 */
	@Action("/place/updatePlaceLandMark")
	public void updatePlace() throws IOException, SQLException {
		PlaceLandMark oldPlaceLandMark = placeLandMarkService.selectByPrimaryKey(placeLandMarkId);
		
		if (oldPlaceLandMark == null) {
			Place place = placeService.queryPlaceByPlaceId(placeLandMarkId);
			PlaceLandMark newPlaceLandMark = new PlaceLandMark();
			newPlaceLandMark.setUpdateTime(place.getUpdateTime());
			newPlaceLandMark.setPlaceId(place.getPlaceId());
			newPlaceLandMark.setIsValid(this.getRequest().getParameter("isValid"));
			newPlaceLandMark.setLandMarkName(place.getName());
			placeLandMarkService.saveOrUpdatePlaceLandMark(fillPlaceCoordinate(newPlaceLandMark));
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"Y\",\"placeLandMarkId\":" + placeLandMarkId +"}");
		} else {
			oldPlaceLandMark.setIsValid(this.getRequest().getParameter("isValid"));
			placeLandMarkService.saveOrUpdatePlaceLandMark(fillPlaceCoordinate(oldPlaceLandMark));
			
			this.getResponse().setContentType("application/json; charset=utf-8"); // 设置RESPONSE
			this.getResponse().getWriter().write("{\"flag\":\"N\",\"placeLandMarkId\":" + placeLandMarkId + ",\"msg\":\"此地标坐标已存在 您的本次提交已经覆盖之前的坐标\"}");
		}		
	}	
	 
	/**
	 * 根据placeLandMarkId查坐标展示地图
	 * 
	 * @return
	 */
	@Action("/place/modifyPlaceLandMarkCoordinate")
	public String queryCoordinateByPlaceId() {
		 placeLandMark = placeLandMarkService.selectByPrimaryKey(placeLandMarkId);
		return "modifyPlaceLandMarkCoordinate";
	}
	
	/**
	 * 更新为百度解析的坐标和百度反解析的地址
	 * 
	 * @return
	 */
	private PlaceLandMark fillPlaceCoordinate(PlaceLandMark placeLandMark) {		
		if (longitude != null) {
			placeLandMark.setLongitude(longitude);
		}
		if (latitude != null) {
			placeLandMark.setLatitude(latitude);
		}
		if (StringUtils.isNotEmpty(isValid)) {
			placeLandMark.setIsValid(isValid);
		}
		if (StringUtils.isNotEmpty(coordinateAddress)) {
			placeLandMark.setCoordinateAddress(coordinateAddress);
		}
		placeLandMark.setUpdateTime(new Date());

		return placeLandMark;
	}

	private Map<String, Object> initParams() {
		Map<String, Object> params = new HashMap<String, Object>();

		if (StringUtils.isNotEmpty(landMarkName)) {
            params.put("landMarkName", landMarkName);
        }
            params.put("placeLandMarkId", placeLandMarkId);
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

	public PlaceLandMark getPlaceLandMark() {
		return placeLandMark;
	}

	public void setPlaceLandMark(PlaceLandMark placeLandMark) {
		this.placeLandMark = placeLandMark;
	}

	public void setPlaceLandMarkService(PlaceLandMarkService placeLandMarkService) {
		this.placeLandMarkService = placeLandMarkService;
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

	public String getLandMarkName() {
		return landMarkName;
	}

	public void setLandMarkName(String landMarkName) {
		this.landMarkName = landMarkName;
	}

	public long getPlaceLandMarkId() {
		return placeLandMarkId;
	}

	public void setPlaceLandMarkId(long placeLandMarkId) {
		this.placeLandMarkId = placeLandMarkId;
	}

}