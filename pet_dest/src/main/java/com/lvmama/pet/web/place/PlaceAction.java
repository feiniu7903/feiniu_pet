package com.lvmama.pet.web.place; 

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.service.place.PlacePageService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.HttpRequestDeviceUtils;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.homePage.PlaceUtils;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.SeoIndexPageCodeEnum;
import com.lvmama.pet.web.place.weather.WeatherInfoDaily;
import com.lvmama.pet.web.place.weather.WeatherUtil;

/**
 * @ TODO
 *    1.需要支持分页吗？入口在哪里？
 *    2.需要这么多property吗？
 *    
 * 目的地页面
 * 
 * @author zuozhengpeng
 */
@Results({ 
	@Result(name = "destIndex", type="freemarker", location = "/WEB-INF/pages/place/placeIndex.ftl"),
	@Result(name = "forwardDestIndex", params={"status", "301", "headers.Location", "/dest/${currentPlace.pinYinUrl}"}, type="httpheader"),
	@Result(name = "forwardDestSurrounding", params={"status", "301", "headers.Location", "/dest/${currentPlace.pinYinUrl}/surrounding_tab_frm${fromDestId}"}, type="httpheader"   ),
	@Result(name = "ajaxDest2Dest_zhongguo", type="freemarker", location = "/WEB-INF/pages/place/template_zhongguo/dest2dest.ftl"),
	@Result(name = "ajaxDest2Dest_abroad", type="freemarker", location = "/WEB-INF/pages/place/template_abroad/dest2dest.ftl"),
	@Result(name = "dest2dest", type="dispatcher", location = "/dest/${currentPlace.pinYinUrl}/dest2dest_tab_frm${ip_from_place_id}"),
	@Result(name = "forwardNewDestIndex",params={"status", "301", "headers.Location", "/dest/${currentPlace.pinYinUrl}"}, type="httpheader"),
	@Result(name = "weather", type="freemarker", location = "/WEB-INF/pages/place/weather.ftl")
	})
public class PlaceAction extends DestBaseAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 5080013665118603360L;
	/**
	 * 日志输出器
	 */
	private static Logger LOG = Logger.getLogger(PlaceAction.class);
	/**
	 * 目的地远程服务
	 */
	private PlacePageService placePageService;
	/**
	 * 出发地目的地
	 */
	private Place fromPlace;
	private Place parentPlace;
	private String pageChannel="dest";
	/**
	 * 跟团游产品类型数量判断
	 */
	private Boolean isGroupMore = true;
	/**
	 * 是否存在产品，默认为false
	 */
	private Boolean hasGlobalProducts = false;
	/**
	 * 显示地图
	 */
	private String maps;
	/**
	 * 判断页面上是国内目的地还是境外目的地
	 */
	private String tabType;

	private long pageSize = 10;
	/**
	 * 当前页
	 */
	private long currentPage = 1;
	/**
	 * 酒店、景点下获取产品数量
	 */
	private static int prdSize = 1;
	@SuppressWarnings("rawtypes")
	private Page pageConfig;
	private List<Place> seoDestList;
	private List<Place> seoPersonDestList;
	private List<Place> seoPlaceList;
	private List<RecommendInfo> hotRecommandPlaceList;
	private List<RecommendInfo> commonPlaceTopicList;
	/**
	 * 出发地
	 */
	private List<Place> fromPlaceList;
	  
	/**
	 * 天气预报
	 */
	private List<WeatherInfoDaily> weeklyWeatherList;
	/**
	 * 目的地tab列表
	 */
	private List<Map<String, String>> tabs = new ArrayList<Map<String, String>>();
	/**
	 * 超级自由行推荐产品
	 */
	private List<ProductSearchInfo> recFreenessSelfPackPrdList = new ArrayList<ProductSearchInfo>();
	/**
	 * 周边跟团游推荐产品
	 */
	private List<ProductSearchInfo> recSurroundingPrdSearchList = new ArrayList<ProductSearchInfo>();
	/**
	 * 当地自由行推荐产品
	 */
	private List<ProductSearchInfo> recFreenessPrdSearchList = new ArrayList<ProductSearchInfo>();
	/**
	 * 目的地到目的地推荐
	 */
	private List<ProductSearchInfo> recDest2destPrdSearchList = new ArrayList<ProductSearchInfo>();
	
	private List<ProductSearchInfo> recDest2destGroupSearchList = new ArrayList<ProductSearchInfo>();
	/**
	 * 酒店推荐
	 */
	private List<Map<String, Object>> recHotelPrdSearchList = new ArrayList<Map<String, Object>>();
	/**
	 * 景区推荐
	 */
	private List<Map<String, Object>> recPlacePrdSearchList = new ArrayList<Map<String, Object>>();
	/**
	 * 特推产品
	 */
	private List<ProductSearchInfo> specialRecommendationList=new ArrayList<ProductSearchInfo>();
	/**
	 * 景区门票
	 */
	private Map<String, Object> placeTicketMap;
	/**
	 * 特色酒店
	 */
	private Map<String, Object> placeHotelMap;
	/**
	 * parentPlaceIds
	 * 供php调用的接口参数,多个id之间用,分隔
	 */
	private String parentPlaceIds;
	/**
	 * 友情链接
	 */
  	private SeoLinksService seoLinksService;
  	private List<SeoLinks> seoLinkslist;

	/**
	 * 默认的目的地页面，即目的地产品推荐页面
	 */
	public String products() throws IOException {
		//是景点跳转wap

		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		
		if (Constant.PLACE_STAGE.PLACE_FOR_SCENIC.getCode().equalsIgnoreCase(place.getStage())
				|| Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode().equalsIgnoreCase(place.getStage())) {
			return "forwardNewDestIndex";
		}
		
		if (Place.PLACE_TEMPLATE.TEMPLATE_ABROAD.name().equalsIgnoreCase(place.getTemplate())) {
			return dest2dest();
		}
		
		if (Place.PLACE_TEMPLATE.TEMPLATE_ZHONGGUO.name().equalsIgnoreCase(place.getTemplate())) {
			return homeTemplateRecommendedProduct();
		}
		return ERROR;
	}
	
	/**
	 * 根据parentplaceids获取景点信息
	 * 供php用
	 */
	@Action("/ajax/getPlaceByParentIds")
	public void getPlaceByParentPlaceIds(){
		Map<String,List> param=new HashMap<String,List>();
		List<String> idsList=new ArrayList<String>();
		String[] idArrays;
		if(parentPlaceIds.indexOf(",")!=-1){
			idArrays=parentPlaceIds.split(",");
			idsList.addAll(Arrays.asList(idArrays));
		}else{
			idsList.add(parentPlaceIds);
		}
		int count=10;
		int sum=idsList.size();
		List<Place> placeList = new ArrayList<Place>();
		if(sum>10){
			int loop=sum%count==0?sum/count:(sum/count)+1;
			for(int i=1;i<=loop;i++){
				 int sub=i*count<sum?i*count:sum;
				 List<String> temp=idsList.subList((i-1)*count, sub);
				 param.put("parentPlaceIds", temp);
				 placeList.addAll(placeService.getPlaceListByParentIds(param));
				 param.clear();
			}
		}else{
			param.put("parentPlaceIds", idsList);
			placeList.addAll(placeService.getPlaceListByParentIds(param));
		}
		
		JSONArray array=buildJsonArray(placeList);
		JSONObject returnJSONObject = new JSONObject();
		returnJSONObject.put("data", array);
		sendAjaxResultByJson(returnJSONObject.toString());
	}
	
	/**
	 * 获取所有国家记录
	 * 供php用
	 */
	@Action("/ajax/getCountryRecord")
	public void getCountryRecord(){
		List<Place> placeList=placeService.getCountryRecord();
		JSONArray array=buildJsonArray(placeList);
		JSONObject returnJSONObject = new JSONObject();
		returnJSONObject.put("data", array);
	    sendAjaxResultByJson(returnJSONObject.toString());
	}
	
	/**
	 * 根据list构建JSON数组
	 * @param placeList
	 * @return
	 */
	public JSONArray buildJsonArray(List<Place> placeList){
		JSONArray array = new JSONArray();
		for(int i = 0; i < placeList.size(); i++){
			Place place = placeList.get(i);
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("place_id", place.getPlaceId());
			jsonObject.put("placename",  place.getName());
			jsonObject.put("parent_id", place.getParentPlaceId());
			jsonObject.put("pinyin", place.getPinYin());
			jsonObject.put("pinyin_url", place.getPinYinUrl());
			jsonObject.put("type", place.getStage());
			//酒店不需要同步
			if (!"3".equals(place.getStage())) {
				array.add(jsonObject);
			}
		}
		return array;
	}
	
	/**
	 * 设定友情链接 
	 * 
	 * @author nixianjun 2013-8-14
	 */
	private void addSeoLinks(){
		//seo友情链接
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("placeId",place.getPlaceId());
		map.put("location",Constant.PLACE_SEOLINKS.INDEX.getCode());
		seoLinkslist=PlaceUtils.removeRepeatData(seoLinksService.batchQuerySeoLinksByParam(map));
	}
	
	/**
	 * 目的地页面门票标签页
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String ticket() {
		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		
		String stage = "2";

		this.currentTab = "ticket";
		
		String key = "getPlaceAndPrd" + "_" + place.getPlaceId() + "_" + stage + "_" + 1 + "_" + 10 + "_" + pageSize + "_" + prdSize + "_" + currentPage;
		placeTicketMap = (Map<String, Object>) MemcachedUtil.getInstance().get(key);
		if (placeTicketMap == null) {
			placeTicketMap = placePageService.getDestInfoForTicketProducts(place.getPlaceId(),10);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,placeTicketMap);
			this.comKeyDescService.insert(key, "目的地门票产品列表："+ place.getPlaceId());
		}
		
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
		
		//获取目的地门票seoTkd规则
		if("template_abroad".equals(place.getTemplate())){
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_TICKET_ABROAD.getCode());
		}else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_TICKET.getCode());
		}

		
		this.recPlacePrdSearchList = (List<Map<String, Object>>) placeTicketMap.get("resultList");
		return "destIndex";
	}
	
	/**
	 * 目的地页面，自由行(景点+酒店)标签页
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String freeness() {
		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		
		this.currentTab = "freeness";
		
		String key="freeness_tab_" + place.getPlaceId();
		Map<String,Object> freenessMap = (Map<String,Object>) MemcachedUtil.getInstance().get(key);
		if(null == freenessMap){
			freenessMap = placePageService.getDestInfoForFreenessProducts(place.getPlaceId(),false);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, freenessMap);
			this.comKeyDescService.insert(key, "目的地页面-自由行tab:"+place.getPlaceId());
		}
		
		recFreenessSelfPackPrdList=(List<ProductSearchInfo>)freenessMap.get("recFreenessSelfPackPrdList");
		recFreenessPrdSearchList=(List<ProductSearchInfo>)freenessMap.get("recFreenessPrdSearchList");
	
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
				
		// 查询路线
		//this.setFromPlace(place, subProductTypes);
				
		//获取目的地自由行seoTkd规则
		super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_FREENESS.getCode());		
		return "destIndex";
	}
	
	/**
	 * 目的地页面，酒店标签页
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String hotel() {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}

		
		this.currentTab = "hotel";

		String key = "getPlaceAndPrd" + "_" + place.getPlaceId() + "_" + Constant.PLACE_STAGE.PLACE_FOR_HOTEL.getCode() + "_" + 2 + "_" + 10 + "_" + pageSize + "_" + prdSize + "_" + currentPage;
		placeHotelMap = (Map<String, Object>) MemcachedUtil.getInstance().get(key);
		if (null == placeHotelMap) {
			//this.placeHotelMap = placePageService.getPlaceAndPrd(place.getPlaceId(), stage, "2", 10, 10, prdSize, (int) this.currentPage);
			placeHotelMap = placePageService.getDestInfoForHotelProducts(place.getPlaceId(),10);
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,placeHotelMap);
			this.comKeyDescService.insert(key, "目的地酒店产品列表：" + place.getPlaceId());
		}
		
		this.recHotelPrdSearchList = (List<Map<String, Object>>) placeHotelMap.get("resultList");
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();

		if ("template_abroad".equals(place.getTemplate())) {
			// 获取目的地酒店seoTkd规则
			super.getSeoIndexPageRegular(place,
					SeoIndexPageCodeEnum.CH_DEST_PLACE_HOTEL_ABROAD.getCode());
		} else {
			super.getSeoIndexPageRegular(place,
					SeoIndexPageCodeEnum.CH_DEST_PLACE_HOTEL.getCode());
		}
		return "destIndex";

	}
	
	/**
	 * 目的地页面，跟团游标签页
	 * 
	 * @return
	 */
	@SuppressWarnings({"unchecked" })
	public String surrounding() {
		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		
		this.currentTab = "surrounding";
		
		// 国内取：'GROUP','GROUP_LONG','SELFHELP_BUS';省级取：'GROUP','SELFHELP_BUS';大洲级：'GROUP_FOREIGN';
		String[] subProductTypes = placePageService.getSubProductTypeForSurrounding(place);
		String status = this.setFromPlace(place, subProductTypes);
		if ("forward".equals(status)) {
			return "forwardDestIndex";
		}

		String key = "surrounding_fromDestId_" + fromDestId + "_id_" + place.getPlaceId();
		recSurroundingPrdSearchList = (List<ProductSearchInfo>)MemcachedUtil.getInstance().get(key);
		if(recSurroundingPrdSearchList==null){
			Map<String,Object> resultMap = placePageService.getDestInfoForSurroundProducts(fromDestId, place.getPlaceId(), subProductTypes,10);
			if(resultMap!=null&&!resultMap.isEmpty()){
				recSurroundingPrdSearchList=(List<ProductSearchInfo>)resultMap.get("returnList");
			}
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, recSurroundingPrdSearchList);
			this.comKeyDescService.insert(key, "目的地更团友产品列表：fromDestId_"+fromDestId+"_id_"+place.getPlaceId());
		}
		//显示更多链接逻辑：长途跟团游多则连接到长途跟团游搜索结果页，反之。
		if(recSurroundingPrdSearchList!=null){
			int groupCount = 0;
			int groupBusCount = 0;
			for (ProductSearchInfo vsi : recSurroundingPrdSearchList) {
				if ("GROUP_LONG".equals(vsi.getSubProductType())) {
					groupCount += 1;
				}
				if ("GROUP".equals(vsi.getSubProductType()) || "SELFHELP_BUS".equals(vsi.getSubProductType())) {
					groupBusCount += 1;
				}
			}
			if (groupCount < groupBusCount) {
				isGroupMore = false;
			}
		}
		
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
		
		//获取目的地跟团游seoTkd规则
		if(hasInitDestId){
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_SURROUNDING.getCode(), fromPlace);
		}
		else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_SURROUNDING_NFD.getCode());
		}		
		
		return "destIndex";
	}
	
	/**
	 * 目的地页面，自由行(含交通)标签页
	 * 
	 * @return
	 */
	public String dest2dest() {
		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		
		// 取所有目的地对应的出发地
		this.currentTab = "dest2dest";
		
		// 查询路线取自由行（含交通）：长途自由行;出境取（出境跟团游、短途跟团游和自助巴士班，出境自由行）;省份取:GROUP_LONG,FREENESS_LONG;跟团游：短途跟团游，自助巴士班'GROUP','SELFHELP_BUS'
		String[] subProductType = placePageService.getSubProductTypeForDest2Dest(place);
		String status = this.setFromPlace(place, subProductType);

		if ("template_abroad".equals(place.getTemplate())) {
			// 国外分别取跟团游和自由行模块中的前10条产品
			this.createDest2DestAbroadProduct(false, place, place.getPlaceId());
			if (hasGlobalProducts && !"0".equals(place.getStage())) {
				this.parentPlace = placeService.queryPlaceByPlaceId(this.getDefaultPlaceId());
				subProductType = placePageService.getSubProductTypeForDest2Dest(parentPlace);
				this.setFromPlace(parentPlace, subProductType);
				if(fromDestId != null)
				{
					LOG.debug("change2 fromDestId:"+fromDestId);
				}
				this.createDest2DestAbroadProduct(true, parentPlace, getDefaultPlaceId());
			}
			//获取目的地自由行(含交通)seoTkd规则
			if(hasInitDestId){
 				if(fromPlace==null){
					return ERROR;
				}
				
				super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTABROAD.getCode(), fromPlace);
			} else {
				super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTABROAD_NFD.getCode());
			}
		} else {
			if ("forward".equals(status)) {
				return "forwardDestSurrounding";
			}
			// 国内自由行(含交通) 和 省份级的各地到
			this.getCreateDest2DestProductFromMap(subProductType,place,fromDestId);
			
			//获取目的地自由行(含交通)seoTkd规则
			if(hasInitDestId){
				super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODEST.getCode(), fromPlace);
			} else {
				super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODEST_NFD.getCode());
			}
		}
		
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
		
		addSeoLinks();
		return "destIndex";
	}	
	
	/**
	 * 国内模板的目的地产品推荐
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private String homeTemplateRecommendedProduct() {
		if (null == this.place 
				|| !"Y".equalsIgnoreCase(place.getIsValid())
				|| !Place.PLACE_TEMPLATE.TEMPLATE_ZHONGGUO.name().equalsIgnoreCase(place.getTemplate())
				|| !Constant.PLACE_STAGE.PLACE_FOR_DEST.getCode().equalsIgnoreCase(place.getStage())) {
			return ERROR;
		}

		currentTab = "products";

		Long firtMatchFromDestId = getFirstFromDestId(place.getPlaceId());
		
		String key = "recommendProducts_" + firtMatchFromDestId + "_" + place.getPlaceId() + "_" + place.getPinYin();
		Map<String, Object> recommendProducts = (Map<String, Object>) MemcachedUtil.getInstance().get(key);
		recommendProducts = null;
		if (null == recommendProducts) {
			String[] subProductType = placePageService.getSubProductTypeForDest2Dest(place);
			setFromPlace(place,subProductType);
			// 获取当季推荐列表数据
			recommendProducts = placePageService.getDestInfoForRecommendProducts(firtMatchFromDestId, place,fromDestId);
			getRecommendProductsFromMap(recommendProducts);
			// 如果当季推荐列表为空，则取getDefaultPlaceId()目的地的推荐列表
			if (!isExistRecommendProducts()) {
				hasGlobalProducts = true;
				parentPlace = placeService.queryPlaceByPlaceId(getDefaultPlaceId());
				recommendProducts = placePageService.getDestInfoForRecommendProducts(firtMatchFromDestId, parentPlace,fromDestId);
			}
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, recommendProducts);
			this.comKeyDescService.insert(key, "目的地首页推荐产品列表");
		}
		
		getRecommendProductsFromMap(recommendProducts);
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		getPublicInfo();
		// 获取目的地首页seoTkd规则
		getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE.getCode());

		addSeoLinks();
		return "destIndex";
	}	
	
	/**
	 * 地图界面
	 */
	@Override
	@Action("/newplace/place")
	public String execute() throws Exception {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		getPublicInfo();
		if("template_abroad".equals(place.getTemplate())){
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_MAPS_ABROAD.getCode());
		}else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_MAPS.getCode());
		}
		return "destIndex";
	}

	/**
	 * 天气预报界面
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String weather() {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		getPublicInfo();

		if("template_abroad".equals(place.getTemplate())){
			//获取目的地地图seoTkd规则
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_WEATHER_ABROAD.getCode());
		}else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_WEATHER.getCode());
		}
		
		String key = "PlaceAction_weeklyWeatherList_" + place.getPinYin();
		weeklyWeatherList = (List<WeatherInfoDaily>) MemcachedUtil.getInstance().get(key);
		if (weeklyWeatherList == null) {
			weeklyWeatherList = WeatherUtil.getWeather(this.place.getName());
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,weeklyWeatherList);
			this.comKeyDescService.insert(key, "天气预报界面");
		}
		return "weather";
	}	





	/**
	 * 异步加载DEST页面
	 * 
	 * @return
	 */
	public String ajaxDest2Dest() {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}

		// 查询路线
		String[] subProductType = null;
		if ("template_abroad".equals(place.getTemplate())) {
			if ("dest2destGroup".equals(currentTab)) {
				subProductType = placePageService.getSubProductTypeForDest2destGroup(place);
				setFromPlace(place, subProductType);
				this.createDest2destGroup(subProductType);
			} else if ("dest2destFreeness".equals(currentTab)) {
				
				subProductType = new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()};
				setFromPlace(place, subProductType);
				this.createDest2destFreeness(subProductType);
			} else {
				// 查询路线
				this.currentTab = "dest2dest";
				
				setFromPlace(place, placePageService.getSubProductTypeForDest2Dest(place));
				this.createDest2DestAbroadProduct(false, place, place.getPlaceId());
				if (hasGlobalProducts && !"0".equals(place.getStage())) {
					this.parentPlace = placeService.queryPlaceByPlaceId(this.getDefaultPlaceId());
					subProductType = placePageService.getSubProductTypeForDest2Dest(parentPlace);
					fromDestId = null;
					setFromPlace(parentPlace, subProductType);
					this.createDest2DestAbroadProduct(true, parentPlace, this.parentPlace.getPlaceId());
				}
			}

			return "ajaxDest2Dest_abroad";
		} else {
			// 查询路线 国内的dest2dest
			this.currentTab = "dest2dest";
			subProductType = placePageService.getSubProductTypeForDest2Dest(place);
			setFromPlace(place, subProductType);
			this.getCreateDest2DestProductFromMap(subProductType,place,fromDestId);
		}
		return "ajaxDest2Dest_zhongguo";
	}



	/**
	 * 境外的跟团
	 * @return
	 */
	public String dest2destGroup() {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		// 取所有目的地对应的出发地
		this.currentTab = "dest2destGroup";
		
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
		
		// 查询路线 出境取：'GROUP_FOREIGN','GROUP','SELFHELP_BUS'
		String[] subProductType = placePageService.getSubProductTypeForDest2destGroup(place);
		this.setFromPlace(place, subProductType);
		
		//获取目的地出境seoTkd规则
		if(hasInitDestId){
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTGROUP.getCode(), fromPlace);
		}
		else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTGROUP_NFD.getCode());
		}

		
		this.createDest2destGroup(subProductType);
		
		return "destIndex";
	}
	
	/**
	 * 境外的自由行
	 * @return
	 */
	public String dest2destFreeness() {
		if (null == this.place || !"Y".equalsIgnoreCase(place.getIsValid())) {
			return ERROR;
		}
		// 取所有目的地对应的出发地
		this.currentTab = "dest2destFreeness";
		// 查询路线
		String[] subProductType = new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()};
		
		// 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
		this.getPublicInfo();
		
		this.setFromPlace(place, subProductType);
		
		//获取目的地出境seoTkd规则
		if(hasInitDestId){
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTFREE.getCode(), fromPlace);
		}else{
			super.getSeoIndexPageRegular(place, SeoIndexPageCodeEnum.CH_DEST_PLACE_DESTTODESTFREE_NFD.getCode());
		}	
		this.createDest2destFreeness(subProductType);
		return "destIndex";
	}

	/**
	 * 
	 * 当目的地无任何产品时，获取默认的目的地
	 * @return 默认目的地
	 * <p>默认的目的地获取步骤如下:
	 * <li>1. 目的地的上级目的地</li>
	 * <li>2. 上级目的地不存在时，取IP所在目的地</li>
	 * <li>3. IP所在目的地为空时，取系统默认目的地</li>
	 * </p>
	 */
	private Long getDefaultPlaceId() {
		if (null != place && null != place.getParentPlaceId()) {
			return place.getParentPlaceId();
		}
		
		if (null == getRequestAttribute(Constant.IP_FROM_PLACE_ID)) {
			return (Long) getRequestAttribute(Constant.IP_FROM_PLACE_ID);
		}
		
		return Constant.DEFAULT_IP_FROM_PLACE_ID;
	}

	/**
	 * 当前目的地下是否存在推荐的产品
	 * @return
	 */
	private boolean isExistRecommendProducts() {
		return (recSurroundingPrdSearchList != null && recSurroundingPrdSearchList.size() > 0) 
				|| (recFreenessPrdSearchList != null && recFreenessPrdSearchList.size() > 0)
				|| (recDest2destPrdSearchList != null && recDest2destPrdSearchList.size() > 0) 
				|| (recHotelPrdSearchList != null && recHotelPrdSearchList.size() > 0)
				|| (recPlacePrdSearchList != null && recPlacePrdSearchList.size() > 0);

	}

	@SuppressWarnings("unchecked")
	private void getRecommendProductsFromMap(Map<String, Object> recommendProducts) {
		// 周边跟团游推荐产品
		recSurroundingPrdSearchList = (List<ProductSearchInfo>) recommendProducts.get("recSurroundingPrdSearchList");
		// 当地自由行推荐产品
		recFreenessPrdSearchList = (List<ProductSearchInfo>) recommendProducts.get("recFreenessPrdSearchList");
		// 目的地到目的地推荐
		recDest2destPrdSearchList = (List<ProductSearchInfo>) recommendProducts.get("recDest2destPrdSearchList");
		// 酒店推荐
		recHotelPrdSearchList = (List<Map<String, Object>>) recommendProducts.get("recHotelPrdSearchList");
		// 景区推荐
		recPlacePrdSearchList = (List<Map<String, Object>>) recommendProducts.get("recPlacePrdSearchList");
	}

	/**
	 * 查询目的地信息,节庆专题,目的地旅游指南,热门景点 ,点评
	 */
	@SuppressWarnings("unchecked")
	private void getPublicInfo() {
		tabType = place.getTemplate();

		String specialKey="specialRecommendation_" + place.getPlaceId() + "_" + pageChannel + "_" +currentPage + "_" + prdSize;
		specialRecommendationList = (List<ProductSearchInfo>) MemcachedUtil.getInstance().get(specialKey);
		if(null == specialRecommendationList) {
			Long firtMatchFromDestId = getFirstFromDestId(place.getPlaceId());
			specialRecommendationList=placePageService.specialRecommendation(place.getPlaceId(),firtMatchFromDestId,pageChannel,(int) currentPage, prdSize);
			if (specialRecommendationList.size() == 0) {
				// 当前目的地无产品时取上级目的地焦点图
				Long defaultPlaceId=getDefaultPlaceId();
				firtMatchFromDestId = getFirstFromDestId(defaultPlaceId);
				specialRecommendationList=placePageService.specialRecommendation(defaultPlaceId,firtMatchFromDestId,pageChannel,(int)currentPage,prdSize);
			}
			MemcachedUtil.getInstance().set(specialKey, MemcachedUtil.ONE_HOUR ,specialRecommendationList);
			this.comKeyDescService.insert(specialKey, "目的地焦点图："+place.getPlaceId());
		}
		
		//计算页面是否显示tab
		this.tabs = this.calculateTabs();
		
		//获取页面显示的其他数据
		String aboutPlaceKey="aboutPlaceContent_"+place.getPlaceId();
		Map<String, Object> aboutPlaceContent=(Map<String, Object>)MemcachedUtil.getInstance().get(aboutPlaceKey);
		if(aboutPlaceContent==null){
			aboutPlaceContent=placePageService.getAboutPlaceContent(place);
			MemcachedUtil.getInstance().set(aboutPlaceKey, MemcachedUtil.ONE_HOUR,aboutPlaceContent);
			this.comKeyDescService.insert(aboutPlaceKey, "目的地页面运营信息："+place.getPlaceId());
		}
		hotRecommandPlaceList=(List<RecommendInfo>)aboutPlaceContent.get("hotRecommandPlaceList");
		commonPlaceTopicList=(List<RecommendInfo>)aboutPlaceContent.get("commonPlaceTopicList");
		seoDestList=(List<Place>)aboutPlaceContent.get("seoDestList");
		seoPersonDestList=(List<Place>)aboutPlaceContent.get("seoPersonDestList");
		seoPlaceList=(List<Place>)aboutPlaceContent.get("seoPlaceList");
		 

		//填充景点的点评信息
		listCmtsOfDest(place.getPlaceId());

	}
	
	/**
	 * 根据用户所在的ip地址以及所需访问的目的地，判断默认显示的出发地
	 * @param placeId 需要访问的目的地
	 * @return 默认的出发地
	 * <p>根据用户所在的ip地址，查看是否存在线路产品的出发地是ip地址所在地，目的地是所需访问的目的地的产品，如果存在，则返回ip地址所在的目的地，否则返回<code>null</code>.
	 */
	private Long getFirstFromDestId(Long placeId) {
		//先判断用户访问的目的地是否已经匹配了出发地
		if (null != getSession("LOGIN_USER_HAS_MATCHED_DEST_FOR_" + placeId) 
				&& (Boolean) getSession("LOGIN_USER_HAS_MATCHED_DEST_FOR_" + placeId)) {
			return (Long) this.getSession("LOGIN_USER_MATCHED_DEST_FOR_" + placeId);
		}
		this.putSession("LOGIN_USER_HAS_MATCHED_DEST_FOR_" + placeId, Boolean.TRUE);
		
		Long defaultFromDestId = (Long)getRequestAttribute(Constant.IP_FROM_PLACE_ID);
		if (null != defaultFromDestId) {
			//获取指定目的地的产品的出发地列表
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("placeId", place.getPlaceId());	
			paramMap.put("fromPlaceId", defaultFromDestId);
			List<Place> comPlaceList = placeService.getRouteFrom(paramMap);
			if (null != comPlaceList && !comPlaceList.isEmpty()) {
				putSession("LOGIN_USER_MATCHED_DEST_FOR_" + placeId, defaultFromDestId);
				return defaultFromDestId;
			}
		}
		return null;
	}
	

	/**
	 * 计算是否存在tab
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, String>> calculateTabs() {
		List<Map<String, String>> tabs = new ArrayList<Map<String, String>>();
		String key = "calculateTabs_"+ currentTab + "_" + place.getPlaceId();
		Object obj = MemcachedUtil.getInstance().get(key);
		String[] subProductType = null;
		if (obj == null) {

			
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("placeId", place.getPlaceId());
			
			
			Map<String, String> tabMap = new HashMap<String, String>();
			tabMap.put("tab", "products");
			tabs.add(tabMap);
			// 是否有景区
			if (currentTab == "ticket") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "ticket");
				tabs.add(tabMap);
			} else {
				long placeCount = placeService.selectSonPlaceCount(place.getPlaceId(), 2L);
				if (placeCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "ticket");
					tabs.add(tabMap);
				}
			}
			// 是否有自由行（景点+酒店）
			if (currentTab == "freeness") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "freeness");
				tabs.add(tabMap);
			} else {
				
				subProductType = new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name()};
				
				param.put("subProductTypes", subProductType);
				//productSearchInfo.setSubProductType(subProductType);
				long productCount = productSearchInfoService.countProductByFromPlaceIdAndDestId(param);
				if (productCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "freeness");
					tabs.add(tabMap);
				}
				LOG.info("目的地自由行tab-：id："+place.getPlaceId()+"-subProductType"+subProductType+"- productCount:"+productCount);
			}
			// 是否酒店
			if (currentTab == "hotel") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "hotel");
				tabs.add(tabMap);
			} else {
				long placeCount = placeService.selectSonPlaceCount(place.getPlaceId(), 3L);
				if (placeCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "hotel");
					tabs.add(tabMap);
				}
			}
			// 是否有跟团游
			if (currentTab == "surrounding") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "surrounding");
				tabs.add(tabMap);
			} else {
				subProductType = placePageService.getSubProductTypeForSurrounding(place);
//				productSearchInfo.setSubProductType(subProductType);
				param.put("subProductTypes", subProductType);
				long productCount = productSearchInfoService.countProductByFromPlaceIdAndDestId(param);
				if (productCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "surrounding");
					tabs.add(tabMap);
				}
			}
			// 是否有自由行（含交通）
			// 是否有各地到目的地产品
			if (currentTab == "dest2dest") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "dest2dest");
				tabs.add(tabMap);
			} else {
				Map<String, Object> paramMap = new HashMap<String, Object>();
				paramMap.put("placeId", place.getPlaceId());
				String[] subProductTypes = placePageService.getSubProductTypeForDest2Dest(place);
				paramMap.put("subProductTypes", subProductTypes);
				List<Place> fromPlaceList = placeService.getRouteFrom(paramMap);
				if (fromPlaceList.size() > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "dest2dest");
					tabs.add(tabMap);
				}
			}

			if (currentTab == "dest2destGroup") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "dest2destGroup");
				tabs.add(tabMap);
			} else {
				subProductType = placePageService.getSubProductTypeForDest2destGroup(place);
				//productSearchInfo.setSubProductType(subProductType);
				param.put("subProductTypes", subProductType);
				long productCount = productSearchInfoService.countProductByFromPlaceIdAndDestId(param);
				if (productCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "dest2destGroup");
					tabs.add(tabMap);
				}
			}

			if (currentTab == "dest2destFreeness") {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "dest2destFreeness");
				tabs.add(tabMap);
			} else {
				
				subProductType = new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name()};
				
				param.put("subProductTypes", subProductType);
				long productCount = productSearchInfoService.countProductByFromPlaceIdAndDestId(param);
				if (productCount > 0) {
					tabMap = new HashMap<String, String>();
					tabMap.put("tab", "dest2destFreeness");
					tabs.add(tabMap);
				}
			}

			if ("template_abroad".equals(place.getTemplate())) {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "ticket");
				if (tabs.contains(tabMap)) {
					tabs.remove(tabMap);
					tabs.add(tabMap);
				}
			}

			if ("template_abroad".equals(place.getTemplate())) {
				tabMap = new HashMap<String, String>();
				tabMap.put("tab", "hotel");
				if (tabs.contains(tabMap)) {
					tabs.remove(tabMap);
					tabs.add(tabMap);
				}
			}

			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR,tabs);
			this.comKeyDescService.insert(key, "目的地页面的Tab:"+place.getPlaceId());
		} else {
			tabs = (List<Map<String, String>>) obj;
		}
		return tabs;
	}
	
	/**
	 * 创建dest2destGroup产品
	 * 
	 * @param subProductType
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createDest2destGroup(String[] subProductTypes) {
		if (this.getFromDestId() != null) {
			this.fromPlace = this.placeService.queryPlaceByPlaceId(this.getFromDestId());
		}
		
		recDest2destGroupSearchList = new ArrayList<ProductSearchInfo>();

		// 取跟团游 10条记录
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("fromPlaceId", fromDestId);
		param.put("placeId", place.getPlaceId());
		param.put("subProductTypes", subProductTypes);
		param.put("startRows", currentPage);
		param.put("endRows", 10);
		//pageConfig = new Page();
		recDest2destGroupSearchList = productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
		//pageConfig.setItems(productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
	}

	/**
	 * 创建createDest2destFreeness(subProductType);
	 * 
	 * @param subProductType
	 */
	@SuppressWarnings("unchecked")
	private void createDest2destFreeness(String[] subProductTypes) {
		String key="createDest2destFreeness_fromDestId_"+fromDestId+"_id_"+place.getPlaceId();
		Map<String,Object> destToDestFreenessMap=(Map<String,Object>)MemcachedUtil.getInstance().get(key);
		if(destToDestFreenessMap==null){
			destToDestFreenessMap =new HashMap<String, Object>();
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("fromPlaceId", fromDestId);
			param.put("placeId", place.getPlaceId());
			param.put("subProductTypes", subProductTypes);

			// 超级自由行
			param.put("selfPack", "true");
			param.put("startRows", currentPage);
			param.put("endRows", 2);
			destToDestFreenessMap.put("recFreenessSelfPackPrdList", productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
	
			// 取自由行 10条记录
			param.put("selfPack", "false");
			param.put("startRows", 1);
			param.put("endRows", 10);
			destToDestFreenessMap.put("recDest2destPrdSearchList", productSearchInfoService.getProductByFromPlaceIdAndDestId(param));
			
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, destToDestFreenessMap);
			this.comKeyDescService.insert(key, "createDest2destFreeness_fromDestId_"+fromDestId+"_id_"+place.getPlaceId());
		}
		recFreenessSelfPackPrdList=(List<ProductSearchInfo>)destToDestFreenessMap.get("recFreenessSelfPackPrdList");
		recDest2destPrdSearchList=(List<ProductSearchInfo>)destToDestFreenessMap.get("recDest2destPrdSearchList");
	}
	
	/**
	 * 空的情况
	 * 			第一步：如果IP所在的出发地有数据，取IP出发地；
	 * 			第二步: else，默认出发地如果有数据取默认FROM_ID;
	 * 			第三步：else，取第一个出发地；
	 * @param subProductType
	 */
	private String setFromPlace(Place fromDataPlace, String[] subProductType) {
		boolean hasFromPlace = Boolean.FALSE;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("placeId", fromDataPlace.getPlaceId());
		if (null != subProductType) {
			paramMap.put("subProductTypes", subProductType);
		}
		this.fromPlaceList = placeService.getRouteFrom(paramMap);

		hasFromPlace = setDefaultFormPlace(hasFromPlace, fromPlaceList);
		if(fromDestId != null)
		{
			LOG.debug("change fromDestId:"+fromDestId);
		}
		// 各地到的外部传进来的fromDestId如果为空则先跳转到跟团游，如果跟团游没有该出发地跳转到当季推荐
		if ((!hasFromPlace && "dest2dest".equals(currentTab)) || (!hasFromPlace && "surrounding".equals(currentTab))) {
			return "forward";
		}

		fromPlace = placeService.queryPlaceByPlaceId(this.fromDestId);
		return null;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void getCreateDest2DestProductFromMap(String[] subProductType,Place place,Long fromDestId) {
		if (fromDestId != null) {
			this.fromPlace = this.placeService.queryPlaceByPlaceId(fromDestId);
		}
		String key="getCreateDest2DestProductFromMap_"+ subProductType+"_"+ place.getPlaceId()+"_"+fromDestId;
		Map<String,Object> dest2DestProductMap=(Map<String,Object>)MemcachedUtil.getInstance().get(key);
		if(dest2DestProductMap==null){
			dest2DestProductMap=placePageService.createDest2DestProduct(subProductType,place,fromDestId);
			MemcachedUtil.getInstance().set(key, dest2DestProductMap);
		}
		//超级自由行推荐产品
		 recFreenessSelfPackPrdList =(List<ProductSearchInfo>)dest2DestProductMap.get("recFreenessSelfPackPrdList");
		//目的地到目的地推荐
		recDest2destPrdSearchList = (List<ProductSearchInfo>)dest2DestProductMap.get("recDest2destPrdSearchList");
		recSurroundingPrdSearchList=(List<ProductSearchInfo>)dest2DestProductMap.get("recSurroundingPrdSearchList");
		//pageConfig=new Page();
		// 周边跟团游推荐产品
		//pageConfig.setItems();
	}
	
	/**
	 * 出境dest2dest产品创建
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void createDest2DestAbroadProduct(Boolean noProduct, Place defaultPlace, Long defaultPlaceId) {
		String key="createDest2DestAbroadProduct_"+noProduct+"_fromDestId_"+fromDestId+defaultPlace.getPlaceId()+"_defaultPlaceId_"+defaultPlaceId;
		Map<String,Object> dest2DestAbroadProductMap=(Map<String,Object>)MemcachedUtil.getInstance().get(key);
		if(dest2DestAbroadProductMap==null){
			dest2DestAbroadProductMap=new HashMap<String, Object>();
			// 取自由行 10条记录
			
			List<String> subProductTypeList=new ArrayList<String>();
			subProductTypeList.add("FREENESS_FOREIGN");
			Map<String,Object> param=new HashMap<String,Object>();
			param.put("fromPlaceId", fromDestId);
			param.put("placeId", defaultPlaceId);
			param.put("subProductTypes",subProductTypeList);
			param.put("startRows", 1);
			param.put("endRows", 10);
			List<ProductSearchInfo> list=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			dest2DestAbroadProductMap.put("recDest2destPrdSearchList", list);
	
			subProductTypeList.clear();
			subProductTypeList.add("GROUP");
			subProductTypeList.add("GROUP_FOREIGN");
			
			
			// 取跟团游 10条记录
			if ("0".equals(defaultPlace.getStage()) || noProduct) {
				subProductTypeList.add("GROUP_FOREIGN");
			}
			param.put("subProductTypes", subProductTypeList);
			list=productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
			dest2DestAbroadProductMap.put("recDest2destGroupSearchList",list );
			
			MemcachedUtil.getInstance().set(key, MemcachedUtil.ONE_HOUR, dest2DestAbroadProductMap);
			this.comKeyDescService.insert(key, "出境跟团游产品："+defaultPlaceId);
		}
		
		recDest2destPrdSearchList=(List<ProductSearchInfo>)dest2DestAbroadProductMap.get("recDest2destPrdSearchList");
		recDest2destGroupSearchList=(List<ProductSearchInfo>)dest2DestAbroadProductMap.get("recDest2destGroupSearchList");
		if (null!=recDest2destPrdSearchList&&null!=recDest2destGroupSearchList&&recDest2destPrdSearchList.size() <= 0 && recDest2destGroupSearchList.size() <= 0) {
			hasGlobalProducts = true;
			if (noProduct) {
				fromDestId = null;
			}
		}
	}
	
	public List<ProductSearchInfo> getRecDest2destGroupSearchList() {
		return recDest2destGroupSearchList;
	}

	public void setRecDest2destGroupSearchList(
			List<ProductSearchInfo> recDest2destGroupSearchList) {
		this.recDest2destGroupSearchList = recDest2destGroupSearchList;
	}

	/**
	 *  -----------------------------------  get and set property -------------------------------------------------
	 */
	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setPlacePageService(PlacePageService placePageService) {
		this.placePageService = placePageService;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public Place getCurrentPlace() {
		return place;
	}

	public List<ProductSearchInfo> getRecSurroundingPrdSearchList() {
		return recSurroundingPrdSearchList;
	}

	public List<ProductSearchInfo> getRecFreenessPrdSearchList() {
		return recFreenessPrdSearchList;
	}

	public List<ProductSearchInfo> getRecDest2destPrdSearchList() {
		return recDest2destPrdSearchList;
	}

	public List<Map<String, Object>> getRecHotelPrdSearchList() {
		return recHotelPrdSearchList;
	}

	public List<Map<String, Object>> getRecPlacePrdSearchList() {
		return recPlacePrdSearchList;
	}
	
	public List<Map<String, String>> getTabs() {
		return tabs;
	}
	
	public List<Place> getFromPlaceList() {
		return fromPlaceList;
	}

	public List<ProductSearchInfo> getSpecialRecommendationList() {
		return specialRecommendationList;
	}

	public Map<String, Object> getPlaceTicketMap() {
		return placeTicketMap;
	}

	public Map<String, Object> getPlaceHotelMap() {
		return placeHotelMap;
	}

	public List<Place> getSeoPlaceList() {
		return seoPlaceList;
	}

	public List<Place> getSeoDestList() {
		return seoDestList;
	}

	public List<Place> getSeoPersonDestList() {
		return seoPersonDestList;
	}

	public Place getFromPlace() {
		return fromPlace;
	}

	public List<ProductSearchInfo> getRecFreenessSelfPackPrdList() {
		return recFreenessSelfPackPrdList;
	}
	
	public String getTabType() {
		return tabType;
	}

	public void setTabType(String tabType) {
		this.tabType = tabType;
	}

	public List<RecommendInfo> getHotRecommandPlaceList() {
		return hotRecommandPlaceList;
	}

	public List<RecommendInfo> getCommonPlaceTopicList() {
		return commonPlaceTopicList;
	}

	public List<WeatherInfoDaily> getWeeklyWeatherList() {
		return weeklyWeatherList;
	}

	public Boolean getIsGroupMore() {
		return isGroupMore;
	}

	public Boolean getHasGlobalProducts() {
		return hasGlobalProducts;
	}

	public Place getParentPlace() {
		return parentPlace;
	}

	public String getMaps() {
		return maps;
	}

	public void setMaps(String maps) {
		this.maps = maps;
	}

	public String getParentPlaceIds() {
		return parentPlaceIds;
	}
	/**
	 * @param seoLinksService the seoLinksService to set
	 */
	public void setSeoLinksService(SeoLinksService seoLinksService) {
		this.seoLinksService = seoLinksService;
	}

	public void setParentPlaceIds(String parentPlaceIds) {
		this.parentPlaceIds = parentPlaceIds;
	}
	/**
	 * @return the seoLinkslist
	 */
	public List<SeoLinks> getSeoLinkslist() {
		return seoLinkslist;
	}

	
}
