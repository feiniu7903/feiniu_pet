package com.lvmama.back.sweb.prod;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.prod.TravelTips;
import com.lvmama.comm.bee.po.prod.ViewTravelTips;
import com.lvmama.comm.bee.service.view.ViewTravelTipsService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE;

@Results({
	@Result(name = "selectTravelTipsList", location = "/WEB-INF/pages/back/prod/traveltips/selectTravelTipsList.jsp"),
	@Result(name = "editTravelTips", location = "/WEB-INF/pages/back/prod/traveltips/saveOrUpdateTravelTips.jsp"),
	@Result(name = "searchTravelTipsToRouteProd", location = "/WEB-INF/pages/back/prod/traveltips/searchTravelTipsToRouteProd.jsp")
})
@ParentPackage("json-default")
public class TravelTipsAction extends BackBaseAction{

	private static final long serialVersionUID = -487096944534589010L;
	
	private Long productId;
	private Long viewTravelTipsId;
	private String travelTipsIds;
	private TravelTips travelTips = new TravelTips();
	private List<TravelTips> travelTipsList;
	private Page<TravelTips> travelTipsPage = new Page<TravelTips>();
	private VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE[] continentList;
	
	private ViewTravelTipsService viewTravelTipsService;
	
	/**
	 * 查询旅行须知
	 * @return
	 */
	@Action(value = "/prod/selectTravelTipsList")
	public String selectTravelTipsList(){
		Map<String,Object> param = new HashMap<String,Object>();
		
		if(!StringUtil.isEmptyString(travelTips.getContinent())){
			param.put("continent", travelTips.getContinent().trim());
		}
		if(!StringUtil.isEmptyString(travelTips.getCountry())){
			param.put("country", travelTips.getCountry().trim());
		}
		
		if(!StringUtil.isEmptyString(travelTips.getTipsName())){
			param.put("tipsName", travelTips.getTipsName().trim());
		}
		
		continentList = Constant.VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE.values();
		travelTipsPage.setTotalResultSize(viewTravelTipsService.selectByParamCount(param));
		travelTipsPage.buildUrl(getRequest());
		travelTipsPage.setCurrentPage(super.page);
		param.put("start", travelTipsPage.getStartRows());
		param.put("end", travelTipsPage.getEndRows());
		if(travelTipsPage.getTotalResultSize() > 0){
			travelTipsPage.setItems(this.viewTravelTipsService.selectByParam(param));
		}
		return "selectTravelTipsList";
	}
	
	/**
	 * 新增或修改旅行须知 
	 */
	@Action("/prod/saveOrUpdateTravelTips")
	public void saveOrUpdateTravelTips(){
		ResultHandle result = new ResultHandle();
		if(this.travelTips.getTravelTipsId() == null){
			this.viewTravelTipsService.insertTravelTips(travelTips);
		}else{
			this.viewTravelTipsService.updateTravelTips(travelTips);
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/** 修改旅行须知的跳转页面逻辑*/
	@Action("/prod/editTravelTips")
	public String editTravelTips(){
		continentList = Constant.VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE.values();
		if(this.travelTips.getTravelTipsId() != null){
			travelTips = this.viewTravelTipsService.selectByTravelTipsId(travelTips.getTravelTipsId());
		}
		return "editTravelTips";
	}
	
	/**
	 * 删除旅行须知
	 */
	@Action("/prod/deleteTravelTips")
	public void deleteTravelTips(){
		ResultHandle result = new ResultHandle();
		try{
			this.viewTravelTipsService.deleteTravelTips(travelTips.getTravelTipsId());
		}catch(Exception ex){
			result.setMsg("删除操作发生异常！");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 添加产品的旅行须知
	 */
	@Action("/prod/addViewTravelTips")
	public void addViewTravelTips(){
		ResultHandle result = new ResultHandle();
		String[] travelTipsArray = travelTipsIds.split(",");
		ViewTravelTips view = new ViewTravelTips();
		try{
			for(String item : travelTipsArray){
				view.setProductId(productId);
				view.setTravelTipsId(Long.parseLong(item));
				this.viewTravelTipsService.insertViewTravelTips(view);
			}
		}catch(Exception ex){
			result.setMsg("不能重复添加!");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 查询旅行须知，专门给线路产品中查询用
	 */
	@Action(value = "/prod/searchTravelTipsToRouteProd")
	public String searchTravelTipsToRouteProd(){
		Map<String,Object> param = new HashMap<String,Object>();
		
		if(!StringUtil.isEmptyString(travelTips.getContinent())){
			param.put("continent", travelTips.getContinent().trim());
		}
		if(!StringUtil.isEmptyString(travelTips.getCountry())){
			param.put("country", travelTips.getCountry().trim());
		}
		
		if(!StringUtil.isEmptyString(travelTips.getTipsName())){
			param.put("tipsName", travelTips.getTipsName().trim());
		}
		
		continentList = Constant.VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE.values();
		travelTipsPage.setTotalResultSize(viewTravelTipsService.selectByParamCount(param));
		travelTipsPage.buildUrl(getRequest());
		travelTipsPage.setCurrentPage(super.page);
		param.put("start", travelTipsPage.getStartRows());
		param.put("end", travelTipsPage.getEndRows());
		if(travelTipsPage.getTotalResultSize() > 0){
			travelTipsPage.setItems(this.viewTravelTipsService.selectByParam(param));
		}
		return "searchTravelTipsToRouteProd";
	}
	
	/**
	 * 删除绑定产品的旅游须知
	 */
	@Action(value = "/prod/deleteViewTravelTips")
	public void deleteViewTravelTips(){
		ResultHandle result = new ResultHandle();
		try{
			this.viewTravelTipsService.deleteViewTravelTips(viewTravelTipsId);
		}catch(Exception ex){
			result.setMsg("产生异常,请重新删除!");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	public VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE[] getContinentList() {
		return continentList;
	}
	
	public void setContinentList(VIEW_TRAVEL_TOKNOWN_CONTINENT_TYPE[] continentList) {
		this.continentList = continentList;
	}

	public Page<TravelTips> getTravelTipsPage() {
		return travelTipsPage;
	}

	public void setTravelTipsPage(Page<TravelTips> travelTipsPage) {
		this.travelTipsPage = travelTipsPage;
	}

	public void setViewTravelTipsService(ViewTravelTipsService viewTravelTipsService) {
		this.viewTravelTipsService = viewTravelTipsService;
	}

	public TravelTips getTravelTips() {
		return travelTips;
	}

	public void setTravelTips(TravelTips travelTips) {
		this.travelTips = travelTips;
	}

	public List<TravelTips> getTravelTipsList() {
		return travelTipsList;
	}

	public void setTravelTipsList(List<TravelTips> travelTipsList) {
		this.travelTipsList = travelTipsList;
	}
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getTravelTipsIds() {
		return travelTipsIds;
	}

	public void setTravelTipsIds(String travelTipsIds) {
		this.travelTipsIds = travelTipsIds;
	}

	public Long getViewTravelTipsId() {
		return viewTravelTipsId;
	}

	public void setViewTravelTipsId(Long viewTravelTipsId) {
		this.viewTravelTipsId = viewTravelTipsId;
	}
	
}
