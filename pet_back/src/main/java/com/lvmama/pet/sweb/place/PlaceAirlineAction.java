package com.lvmama.pet.sweb.place;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.place.PlaceAirline;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.utils.json.JSONResult;

@Results( {
	@Result(name = "input", location = "/WEB-INF/pages/back/place/place_airline.jsp", type = "dispatcher"),
	@Result(name = "placeAirlineList", location = "/WEB-INF/pages/back/place/place_airline_list.jsp", type = "dispatcher")
	})
public class PlaceAirlineAction extends BackBaseAction{
	private static final long serialVersionUID = -1933262201783089923L;
	
	public PlaceAirline placeAirline;
	
	private PlaceFlightService placeFlightService;
	
	private String airlineCode;
	
	private String airlineName;
	
	private Long placeAirlineId;
	
	@Action("/place/placeAirlineList")
	public String placeAirlineList() throws Exception {
		Map<String,Object> param = initParam();
		pagination = initPage();
		param.put("startRows", pagination.getStartRows());
		param.put("endRows", pagination.getEndRows());
		pagination.setTotalResultSize(placeFlightService.countPlaceAirLinetListByParam(param));
		pagination.setItems(placeFlightService.queryPlaceAirLineListByParam(param));
		pagination.buildUrl(getRequest());
		return "placeAirlineList";
	}

	@Action("/place/placeAirlineAdd")
	public String placeAirlineAdd(){
		return "input";
	}
	
	@Action("/place/placeAirlineview")
	public String placeAirlineview(){
		if (null != placeAirlineId) {
		 placeAirline=placeFlightService.queryPlaceAirline(placeAirlineId);
		}
		return "input";
	}
	
	@Action("/place/placeAirlineSave")
	public void placeAirlineSave(){
		JSONResult result = new JSONResult(getResponse());
		try {
			if (null == placeAirline.getPlaceAirlineId()) {
				Long count = placeFlightService.countAirlineByCode(placeAirline
						.getAirlineCode());
				if (count > 0) {
					result.raise("代码已存在！").output();
					return;
				} else {
					placeFlightService.saveAirline(placeAirline);
				}
			} else {
				placeFlightService.updateByPrimaryKey(placeAirline);
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
		if (StringUtils.isNotBlank(airlineCode)) {
			param.put("airlineCode", airlineCode.trim());
		}
		if (StringUtils.isNotBlank(airlineName)) {
			param.put("airlineName", airlineName.trim());
		}
		return param;
	}


	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}
	
	public PlaceAirline getPlaceAirline() {
		return placeAirline;
	}

	public void setPlaceAirline(PlaceAirline placeAirline) {
		this.placeAirline = placeAirline;
	}

	public String getAirlineCode() {
		return airlineCode;
	}

	public void setAirlineCode(String airlineCode) {
		this.airlineCode = airlineCode;
	}

	public String getAirlineName() {
		return airlineName;
	}

	public void setAirlineName(String airlineName) {
		this.airlineName = airlineName;
	}

	public void setPlaceAirlineId(Long placeAirlineId) {
		this.placeAirlineId = placeAirlineId;
	}
}
