/**
 * 
 */
package com.lvmama.front.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.po.prod.LineInfo;
import com.lvmama.comm.bee.po.prod.LineStation;
import com.lvmama.comm.bee.po.prod.LineStops;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.front.web.BaseAction;

/**
 * @author yangbin
 *
 */
@Results({
	@Result(name="product",location="/WEB-INF/pages/product/product_info.ftl"),
	@Result(name="dest",location="/WEB-INF/pages/product/dest_info.ftl")
})
public class ProductInfoAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4988638413880366991L;
	private Long productId;
	private Long lineInfoId;
	private String search;
	private PageService pageService;
	private ViewPage viewPage;
	private PlaceService placeService;
	private Date visitTime;
	private ProdProductService prodProductService;
	private ProdProductPlaceService prodProductPlaceService;
	private ProdTrainService prodTrainService;
	private Map<String,Object> place;
	
	@Action("/product/productInfo")
	public String productInfo(){
			
		ProdProduct pp=prodProductService.getProdProductById(productId);
		if(pp!=null&&pp.isHotel()){//显示酒店dest信息
			Place comPlace=prodProductPlaceService.getToDestByProductId(productId);			
			if(comPlace!=null){
//				place=placeService.getPlaceMapByPlaceId(comPlace.getPlaceId());				
//				viewPlaceInfos = placeService.getPlaceInfoListByPlaceId(comPlace.getPlaceId());
			}
			return "dest";
		}else{
			viewPage=pageService.getViewPage(productId);	
		}
		return "product";
	}
	
	/**
	 * 读取一个车次的信息
	 */
	@Action("/product/train/lineStop")
	public void trainLineStop(){
		JSONResult result = new JSONResult();
		JSONArray array = new JSONArray();
		LineInfo lineInfo = prodTrainService.getLineInfo(lineInfoId);
		List<LineStops> list = prodTrainService.selectLineStopsByLineInfoId(lineInfoId,visitTime);
		List<LineStation> stationList = prodTrainService.selectLineStationByLineInfoId(lineInfoId);
		LineStation lineStation=null;
		int pos=0;
		for(LineStops stop:list){
			JSONObject obj = new JSONObject();
			obj.put("seq", stop.getStopStep());
			String stationName = null;
			if(lineStation!=null){
				if(lineStation.getStationId().equals(stop.getStationId())){
					stationName = lineStation.getStationName();
				}
			}
			if(StringUtils.isEmpty(stationName)){
				lineStation = getStation(stationList, stop);
				if(lineStation!=null){
					stationName = lineStation.getStationName();
				}
			}
			obj.put("stopName", stationName);
			obj.put("arrivalTime", TimePriceUtil.formatTime(stop.getArrivalTime()));
			obj.put("departureTime", TimePriceUtil.formatTime(stop.getDepartureTime()));
			if(pos==0||pos==list.size()-1){
				obj.put("takeTime", "-");
			}else{
				obj.put("takeTime", calcTakeTime(stop.getArrivalTime(),stop.getDepartureTime()));
			}
			pos++;
			array.add(obj);
		}
		result.put("stopList", array);
		result.put("takeDays", lineInfo.getTakeDays());
		result.put("takeTime", lineInfo.getTakeTime());
		result.output(getResponse());
	}
	
	private static long calcTakeTime(long arrivalTime,long departureTime){
		long departure=departureTime;
		long deSec=departure%100;
		long arSec=arrivalTime%100;
		long deHour=departure/100;
		long arHour=arrivalTime/100;
		if(deSec<arSec){
			deHour=deHour-1;
			
			deSec+=60;
		}
		//System.out.println(deSec+"   "+deHour);
		if(deHour==0 && deHour !=arHour&& arHour>0){
			deHour=24;
		}
		long sec = deSec-arSec;
		long hour= (deHour-arHour)%24;
		//System.out.println(hour+"   "+sec);
		return hour*60+sec;
	}
	public static void main(String[] args) {
		System.out.println(calcTakeTime(139,142));
	}
	/**
	 * 搜索城市
	 */
	@Action("/product/seachTrainPlace")
	public void searchLineStation(){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("citySearch", SecurityTool.preventSqlInjection(search));
		List<LineStation> list = prodTrainService.selectLineStationByParam(params);
		JSONArray array = new JSONArray();
		for(LineStation ls:list){
			JSONObject obj = new JSONObject();
			obj.put("name", ls.getStationName());
			obj.put("pinyin", ls.getStationPinyin());
			array.add(obj);
		}
		JSONOutput.writeJSONP(getResponse(), array,getRequest().getParameter("callback"));
	}
	
	private LineStation getStation(List<LineStation> stationList,LineStops stop){
		for(LineStation ls:stationList){
			if(ls.getStationId().equals(stop.getStationId())){
				return ls;
			}
		}
		return null;
	}
	
	/**
	 * @param productId the productId to set
	 */
	public void setProductId(Long productId) {
		this.productId = productId;
	}



	/**
	 * @return the viewPage
	 */
	public ViewPage getViewPage() {
		return viewPage;
	}



	/**
	 * @param pageService the pageService to set
	 */
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	/**
	 * @param placeService the placeService to set
	 */
	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}


	/**
	 * @return the place
	 */
	public Map<String, Object> getPlace() {
		return place;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setLineInfoId(Long lineInfoId) {
		this.lineInfoId = lineInfoId;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	
}
