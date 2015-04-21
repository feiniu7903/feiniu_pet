package com.lvmama.pet.sweb.place;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlacePlaneModel;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.utils.json.JSONResult;
/**
 * 机型管理
 *
 */
@Results( {
	@Result(name = "input", location = "/WEB-INF/pages/back/place/place_plane_model.jsp", type = "dispatcher"),
	@Result(name = "planModelList", location = "/WEB-INF/pages/back/place/place_plane_model_list.jsp", type = "dispatcher")
	})
public class PlacePlaneModelAction extends BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	
	private PlaceFlightService placeFlightService;
	
	public PlacePlaneModel placePlaneModel;
	
    private String planeName;

    private String planeCode;
    
	private Long placeModelId;
    
    @Action("/place/toplacePlaneModelList")
	public String tolist(){
		return "planModelList";
	}
	
	@Action("/place/placePlaneModelList")
	public String placeAirlineList() throws Exception {
		Map<String,Object> param = initParam();
		pagination = initPage();
		param.put("startRows", pagination.getStartRows());
		param.put("endRows", pagination.getEndRows());
		pagination.setTotalResultSize(placeFlightService.countPlacePlaneModelListByParam(param));
		pagination.setItems(placeFlightService.queryPlacePlaneModelListByParam(param));
		pagination.buildUrl(getRequest());
		return "planModelList";
	}
	
	@Action("/place/placePlaneModelAdd")
	public String placeAirlineAdd(){
		return "input";
	}
	
	@Action("/place/placePlaneModelview")
	public String placeAirlineview(){
		if (null !=placeModelId ) {
		   placePlaneModel=placeFlightService.queryPlacePlaneModel(placeModelId);
		}
		return "input";
	}
	
	@Action("/place/placePlaneModelSave")
	public void placePlaneModelSave(){
		JSONResult result=new JSONResult(getResponse());
		try {
			if (null == placePlaneModel.getPlaceModelId()) {
				Long count = placeFlightService
						.countPlaneModelByCode(placePlaneModel.getPlaneCode());
				if (count > 0) {
					result.raise("机型编号已存在").output();
                    return;
				} else {
				   placeFlightService.savePlaneModel(placePlaneModel);
				}
			} else {
				   placeFlightService.updateByPrimaryKey(placePlaneModel);
			}
		} catch (Exception e) {
			result.raise(e);
		} 
		result.output(getResponse());
	}
	
	/**
	 * 初始化查询参数
	 * @return
	 */
	private Map<String, Object> initParam() {
		Map<String, Object> param = new HashMap<String, Object>();
		if (StringUtils.isNotBlank(planeCode)) {
			param.put("planeCode", planeCode.trim());
		}
		if (StringUtils.isNotBlank(planeName)) {
			param.put("planeName",planeName.trim());
		}
		return param;
	}
    
    

	public PlacePlaneModel getPlacePlaneModel() {
		return placePlaneModel;
	}

	public String getPlaneName() {
		return planeName;
	}

	public String getPlaneCode() {
		return planeCode;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public Long getPlaceModelId() {
		return placeModelId;
	}

	public void setPlaneName(String planeName) {
		this.planeName = planeName;
	}

	public void setPlaneCode(String planeCode) {
		this.planeCode = planeCode;
	}

	public void setPlaceModelId(Long placeModelId) {
		this.placeModelId = placeModelId;
	}

	public void setPlacePlaneModel(PlacePlaneModel placePlaneModel) {
		this.placePlaneModel = placePlaneModel;
	}
}
