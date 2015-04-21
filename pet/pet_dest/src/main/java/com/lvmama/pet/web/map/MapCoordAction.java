package com.lvmama.pet.web.map;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.service.place.PlaceCoordinateGoogleService;
import com.lvmama.comm.pet.vo.ViewPlaceCoordinate;

@Results( {
	@Result(name = "freenessMap", location = "/WEB-INF/pages/map/supermap.jsp"),
	@Result(name = "lvmamaMap2", location = "/WEB-INF/pages/map/lvmama_map2.jsp"),
	@Result(name = "lvmamaMap3", location = "/WEB-INF/pages/map/lvmama_map3.jsp")
})
public class MapCoordAction extends BaseAction {	
	private static final long serialVersionUID = -5834600035748246426L;
	private PlaceCoordinateGoogleService placeCoordinateGoogleService;
	private Float longitude;
	private Float latitude;
	private Float windage;
	private String id;
	//private PlaceCoordinate placeCoordinate;
	private String centerParam;
	private String aroundParam;
	private String width="300px";
	private String height="300px";
	private boolean infoFlag = false;
	private String flag = "1";
	private String type="1";
	private String mapZoom="15";
	private String icon="1";
	private String toId;
	private String placeParam;
	private List<ViewPlaceCoordinate> viewCoordinateList;
	private ViewPlaceCoordinate viewPlaceCoordinate;

	/**
	 * 超级自由行打包产品景区坐标
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Action("/googleMap/getCoordinateByPlaceIds")
	public String getCoordinateByPlaceIds() throws UnsupportedEncodingException {
		if (StringUtils.isNotEmpty(placeParam)) {
			placeParam = new String(placeParam.getBytes("iso-8859-1"), "utf-8");
			List<ViewPlaceCoordinate> placeCoord = new ArrayList<ViewPlaceCoordinate>();
			List<Long> placeIdList = new ArrayList<Long>();
			
			/**
			 * 参数封装成ViewPlaceCoordinate，存入列表
			 */
			String[] placeInfos = placeParam.split(";");
			for (String info : placeInfos) {
				boolean flag = true;
				String[] nfo = info.split(",");				
				try {
					Long placeId = Long.parseLong(nfo[0]);				
					for (ViewPlaceCoordinate vpc : placeCoord) {
						if (placeId.equals(vpc.getPlaceId())) {						
							if (!"true".equals(vpc.getInfoFlag()) && ("true".equals(nfo[3]) || "true".equals(nfo[4]))) {
								vpc.setInfoFlag("true");
							}
							flag = false;
							break;
						}
					}
					
					if (flag) {
						placeIdList.add(placeId);						
						ViewPlaceCoordinate vpcc = new ViewPlaceCoordinate();
						vpcc.setPlaceId(Long.parseLong(nfo[0]));				
						vpcc.setInfoFlag(("true".equals(nfo[3]) || "true".equals(nfo[4])) + "");				
						placeCoord.add(vpcc);
					}
				} catch (Exception e) {
				}		
			}			
			
			/**
			 * 填充景区坐标
			 */
			viewCoordinateList = placeCoordinateGoogleService.getViewPlacecoordinatesByPlaceIds(placeIdList);
			for (ViewPlaceCoordinate pc : viewCoordinateList) {
				for (ViewPlaceCoordinate vpc : placeCoord) {
					if (pc.getPlaceId().equals(vpc.getPlaceId())){								
						pc.setInfoFlag(vpc.getInfoFlag());
						break;
					}						
				}
			}			
			/**
			 * 目的地作为中心坐标
			 */
			if(StringUtils.isNotEmpty(toId) && NumberUtils.isNumber(toId)) {
				viewPlaceCoordinate = placeCoordinateGoogleService.getViewPlacecoordinateByPlaceId(toId);
			}
		} 
		return "freenessMap";
	}
	
	/**
	 * 获取景区地图(新地图)
	 */
	@Action("/googleMap/getMapCoordinate")
	public String getMapCoordinate() {	
		if(flag!=null && flag.equals("2")) {
			infoFlag =true;
		}
		
		if(StringUtils.isNotEmpty(id)) {
			// 中心
			viewPlaceCoordinate = placeCoordinateGoogleService.getViewPlacecoordinateByPlaceId(id);
			if (viewPlaceCoordinate!=null) {
				viewPlaceCoordinate.setInfoFlag(infoFlag + "");
			}
			
			// 周围
			if (viewPlaceCoordinate!=null && viewPlaceCoordinate.getLongitude()!=null && viewPlaceCoordinate.getLatitude()!=null) {
				viewCoordinateList = placeCoordinateGoogleService.getPlacesByCoordinate(
						viewPlaceCoordinate.getLongitude(), viewPlaceCoordinate.getLatitude(), windage, null);
			}
		}
		
		return "lvmamaMap2";
	}
	
	/**
	 * 获取简单景区地图
	 */
	@Action("/googleMap/getSimpleMapCoordinate")
	public String getSimpleMapCoordinate() {	
		if(flag!=null && flag.equals("2")) {
			infoFlag =true;
		}else{
			infoFlag=false;
		}
		
		if(StringUtils.isNotEmpty(id)) {
			// 中心
			viewPlaceCoordinate = placeCoordinateGoogleService.getViewPlacecoordinateByPlaceId(id);
			if (viewPlaceCoordinate!=null) {
				viewPlaceCoordinate.setInfoFlag(infoFlag + "");
			}
		}
		
		return "lvmamaMap3";
	}

	public PlaceCoordinateGoogleService getPlaceCoordinateGoogleService() {
		return placeCoordinateGoogleService;
	}

	public void setPlaceCoordinateGoogleService(PlaceCoordinateGoogleService placeCoordinateGoogleService) {
		this.placeCoordinateGoogleService = placeCoordinateGoogleService;
	}

	public Float getLongitude() {
		return longitude;
	}

	public void setLongitude(Float longitude) {
		this.longitude = longitude;
	}

	public Float getLatitude() {
		return latitude;
	}

	public void setLatitude(Float latitude) {
		this.latitude = latitude;
	}

	public Float getWindage() {
		return windage;
	}

	public void setWindage(Float windage) {
		this.windage = windage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCenterParam() {
		return centerParam;
	}

	public void setCenterParam(String centerParam) {
		this.centerParam = centerParam;
	}

	public String getAroundParam() {
		return aroundParam;
	}

	public void setAroundParam(String aroundParam) {
		this.aroundParam = aroundParam;
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

	public boolean isInfoFlag() {
		return infoFlag;
	}

	public void setInfoFlag(boolean infoFlag) {
		this.infoFlag = infoFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMapZoom() {
		return mapZoom;
	}

	public void setMapZoom(String mapZoom) {
		this.mapZoom = mapZoom;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getToId() {
		return toId;
	}

	public void setToId(String toId) {
		this.toId = toId;
	}

	public String getPlaceParam() {
		return placeParam;
	}

	public void setPlaceParam(String placeParam) {
		this.placeParam = placeParam;
	}

	public List<ViewPlaceCoordinate> getViewCoordinateList() {
		return viewCoordinateList;
	}

	public void setViewCoordinateList(List<ViewPlaceCoordinate> viewCoordinateList) {
		this.viewCoordinateList = viewCoordinateList;
	}

	public ViewPlaceCoordinate getViewPlaceCoordinate() {
		return viewPlaceCoordinate;
	}

	public void setViewPlaceCoordinate(ViewPlaceCoordinate viewPlaceCoordinate) {
		this.viewPlaceCoordinate = viewPlaceCoordinate;
	}
	
	

}
