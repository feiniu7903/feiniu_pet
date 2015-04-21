package com.lvmama.clutter.web.main;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobilePlaceHotel;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.model.MobileTree;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.StringUtil;

/**
 * WAPV5搜索ACTION
 * @author jswangqian
 *
 */
@Results({ 
	@Result(name = "index", location = "/WEB-INF/pages/wapv5/index_search.html", type="freemarker"),
	@Result(name = "keyword_search", location = "/WEB-INF/pages/wapv5/ticket_route_search.html", type="freemarker"),
	@Result(name = "index", location = "/WEB-INF/pages/wapv5/index_search.html", type="freemarker"),
	@Result(name = "ids_search_list", location = "/WEB-INF/pages/wapv5/ids_search_list.html", type="freemarker"),
	@Result(name = "ids_search_list_ajax", location = "/WEB-INF/pages/wapv5/ids_search_list_ajax.html", type="freemarker"),
	@Result(name = "rote_search_list", location = "/WEB-INF/pages/wapv5/rote_search_list.html", type="freemarker"),
	@Result(name = "rote_search_list_ajax", location = "/WEB-INF/pages/wapv5/rote_search_list_ajax.html", type="freemarker"),
	@Result(name = "index", location = "/WEB-INF/pages/wapv5/index_search.html", type="freemarker"),
	@Result(name = "driving_travel", location = "/WEB-INF/pages/wapv5/driving_travel.html", type="freemarker"),
	@Result(name = "ticket_search_list", location = "/WEB-INF/pages/wapv5/ticket_search_list.html", type="freemarker"),
	@Result(name = "ticket_search_list_ajax", location = "/WEB-INF/pages/wapv5/ticket_search_list_ajax.html", type="freemarker"),
	@Result(name = "error", location = "/WEB-INF/error.html", type = "freemarker")
})
@Namespace("/mobile/search")
public class SearchV5Action extends BaseAction{
	private static final long serialVersionUID = -6164959037367008915L;
	
	
	/**
	 * 搜索服务. 
	 */
	IClientSearchService mobileSearchService;
	
	/**
	 * 推荐服务. 
	 */
	IClientRecommendService mobileRecommendService;
	
	private String searchStatus;//搜索类型-门票和线路
	
	/**
	 *是否是AJAX请求 
	 */
	private boolean ajax;
	
	private String fromDest;//出发地
	
	private String toDest;//目的地
	
	private int page=1;//分页
	
	private String pageSize;// 一页显示多少条
	
	private String subProductType;//
	
	private String sort;
	
	private String ids;//ID 列表
	
	private String keyword;//关键字
	
	private String latitude;//纬度
	
	private String longitude;//经度
	
	private String subject;//主题
	
	private String day;
	
	private String visitDay;//游玩天数
	
	private String traffic;//交通
	
	private String playLine;//游玩路线
	
	private String playFeature;//游玩特色
	
	private String hotelType;//酒店类型
	
	private String hotelLocation;//酒店位置
	
	private String playBrand;//特色品牌
	
	private String playNum;//游玩人数
	
	private String scenicPlace;//景点
	
	private String landTraffic;//landTraffic
	
	private String landFeature;//landFeature
	
	private String city;//城市
	
	private String subProductTypeTitle;//类型TITLE
	
	private String sortTitle;//排序TITLE
	
	private String firstPost;//是否是第一次
	
	//门票参数
	private String stage;//
	
	private String subjects;//主题类型
	
	private String initKeyword;//门票初始关键字
	
	
	/**
	 * 首页搜索页
	 * @return
	 */
	@Action("index")
	public String searchIndex(){
		return "index";
	}
	
	/**
	 * CMS门票，线路搜索框搜索关键字
	 * @return
	 */
	@Action("keyword_search")
	public String ticketSearch(){
		return "keyword_search";
	}
	
	/**
	 * 门票，线路IDS 搜索结果列表
	 * @return
	 */
	@Action("ids_search_list")
	public String idsSearchList(){
		// 设置图片前缀 
		this.setImagePrefix();
		Map<String,Object> param = new HashMap<String,Object>();
		Map<String,Object> resultMap=null;
		try {
			if(!StringUtils.isEmpty(ids)) {
				ids = URLDecoder.decode(ids,"UTF-8");
			} 
			param.put("ids", ids);
			param.put("page",page);
			//区分门票还是线路
			if (!StringUtils.isEmpty(searchStatus) && "TICKET".equals(searchStatus)) {
				resultMap = mobileSearchService.placeSearch(param);
			}else{
				resultMap = mobileSearchService.routeSearch(param);
			}
			//结果列表数据
			if(null != resultMap ){ // List<MobileProductTitle> mpList
				getRequest().setAttribute("mobileProductList", resultMap.get("datas"));
				getRequest().setAttribute("isLastPage", resultMap.get("isLastPage"));
			}
			if(ajax) {
				return "ids_search_list_ajax";
			}
		} catch (Exception e) {
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		return "ids_search_list";
	}
	
	/**
	 *度假新搜索5.0
	 * @return
	 */
	@Action("rote_search_list")
	public String roteSearchList(){
		// 设置图片前缀 
		log.info("度假列表请求进来");
		this.setImagePrefix();
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			initParams(param);
			Map<String,Object> resultMap = mobileSearchService.routeSearch(param);
			log.info("度假列表请求接口后");
			if(null != resultMap ){ // List<MobileProductTitle> mpList
				List<Map<String, Object>> filterSubProductTypeDatas = (List<Map<String, Object>>) resultMap.get("filterSubProductTypeDatas");//主题搜索
				List<Map<String, Object>> filterDatas = (List<Map<String, Object>>) resultMap.get("filterDatas");//综合筛选
				List<Map<String, Object>> sorts = (List<Map<String, Object>>) resultMap.get("sorts");//排序
				
				List<MobileProductTitle> datas = (List<MobileProductTitle>) resultMap.get("datas");//商品列表
				
				getRequest().setAttribute("filterSubProductTypeDatas", filterSubProductTypeDatas);
				if(filterDatas!=null && filterDatas.size()>0){
					for(Map<String, Object> o : filterDatas){
						List<Map<String, Object>> oList = (List<Map<String, Object>>) o.get("datas");//
						if(oList!=null && oList.size()>0){
							for(Map<String, Object> o1 : oList){
								o1.put("size1", o1.get("size"));
							}
						}
					}
				}
				getRequest().setAttribute("filterDatas", filterDatas);
				getRequest().setAttribute("sorts", sorts);
				getRequest().setAttribute("datas", datas);
				getRequest().setAttribute("isLastPage",resultMap.get("isLastPage"));
			}
			
			if(ajax) {
				return "rote_search_list_ajax";
			}
		} catch (Exception e) {
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
				
		return "rote_search_list";
	}
	
	/**
	 * 门票5.0搜索
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@Action("ticket_search_list")
	public String ticketSearchList() throws UnsupportedEncodingException{
		log.info("门票列表请求进来");
		Map<String,Object> param = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(keyword)) {
			keyword = URLDecoder.decode(keyword,"UTF-8");
			param.put("keyword", keyword);
		} else {
			keyword="上海";
			param.put("keyword", keyword);
		}
		if(!StringUtils.isEmpty(subjects)) {
			subjects = URLDecoder.decode(subjects,"UTF-8");
			param.put("subject", subjects);
		}
		if(!StringUtils.isEmpty(sort)) {
			sort = URLDecoder.decode(sort,"UTF-8");
			param.put("sort", sort);
		}
		if (!StringUtils.isEmpty(sortTitle)) {//排序TITLE
			sortTitle = URLDecoder.decode(sortTitle, "UTF-8");
		}
		if(StringUtils.isEmpty(initKeyword)){
			initKeyword=keyword;
		}
		if (!StringUtils.isEmpty(initKeyword)) {//初始关键字
			initKeyword = URLDecoder.decode(initKeyword, "UTF-8");
		}
		param.put("stage", StringUtil.isEmptyString(stage)?"2":stage); // 默认stage=2； 
		param.put("page", page);
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		// 景区 
		map = mobileSearchService.placeSearch(param);
		log.info("门票列表请求接口后");
		if(null != map) {
			List<MobilePlace> mplaceList=(List<MobilePlace>) map.get("datas");
			List<MobileTree> cities=(List<MobileTree>) map.get("cities");
			List<String> initSubjects=(List<String>) map.get("subjects");
			List<Map<String, Object>> sorts=(List<Map<String, Object>>) map.get("sortTypes");
			
			getRequest().setAttribute("mplaceList", mplaceList);
			getRequest().setAttribute("cities", cities);
			getRequest().setAttribute("initSubjects", initSubjects);
			getRequest().setAttribute("sorts", sorts);
			getRequest().setAttribute("isLastPage",map.get("isLastPage"));
		}
		
		// 设置图片前缀 
		this.setImagePrefix();
		
		
		
		if(ajax){
			return "ticket_search_list_ajax";
		}
		return "ticket_search_list";
	}
	/**
	 *周末自驾游
	 * @return
	 */
	@Action("driving_travel")
	public String drivingTravel(){
		Map<String,Object> param = new HashMap<String,Object>();
		try {
			if(!ajax){//由于CMS 不能定位,CMS点击周末自驾游到JAVA页面，从页面定位后再发请求获取数据
				return "driving_travel";
			}
			//参数验证
			if (!StringUtils.isEmpty(latitude) && !StringUtils.isEmpty(longitude)){
				param.put("latitude",latitude);
				param.put("longitude",longitude);
			}
			param.put("method", "api.com.recommend.getFreetripRecommend4Map");//方法
			
			Map<String,Object> resultMap = mobileRecommendService.getFreetripRecommend4Map(param);//周末自驾游数据
			
			if(null != resultMap ){ // List<MobileProductTitle> mpList
				List<Map<String, Object>> dataList = (List<Map<String, Object>>) resultMap.get("datas");//
				if(dataList!=null && dataList.size()>0){
					Map<String,Object> resultData=dataList.get(0);
					List<MobileRecommend> mobileProductList = (List<MobileRecommend>)resultData.get("data");//周末自驾游商品列表数据
					getRequest().setAttribute("mobileProductList", mobileProductList);
				}
			}
			firstPost="alreadyPost";
		} catch (Exception e) {
			getRequest().setAttribute("msg", e.getMessage());
			return "error";
		}
		
		return "driving_travel";
	}
	/**
	 * 初始化参数.
	 * 
	 * @param param
	 * @throws UnsupportedEncodingException
	 */
	public void initParams(Map<String, Object> param)
			throws UnsupportedEncodingException {
		if (StringUtils.isEmpty(fromDest)) {
			fromDest = "上海";
		} else {
			fromDest = URLDecoder.decode(fromDest, "UTF-8");
		}
		param.put("fromDest", fromDest);

		if (!StringUtils.isEmpty(toDest)) {
			toDest = URLDecoder.decode(toDest, "UTF-8");
			param.put("toDest", toDest);
		} 
		
		if (!StringUtils.isEmpty(keyword)) {
			keyword = URLDecoder.decode(keyword, "UTF-8");
			param.put("keyword", keyword);
		}else{
			keyword="上海";
			param.put("keyword", keyword);
		}
		
		if (subject!=null) {
			subject = URLDecoder.decode(subject, "UTF-8");
			param.put("subject", subject);
		}
		
		if (day!=null) {
			day = URLDecoder.decode(day, "UTF-8");
			param.put("day", day);
		}
		
		if (visitDay!=null) {
			visitDay = URLDecoder.decode(visitDay, "UTF-8");
			param.put("visitDay", visitDay);
		}
		
		if (traffic!=null) {
			traffic = URLDecoder.decode(traffic, "UTF-8");
			param.put("traffic", traffic);
		}
		
		if (playLine!=null) {
			playLine = URLDecoder.decode(playLine, "UTF-8");
			param.put("playLine", playLine);
		}
		
		if (playFeature!=null) {
			playFeature = URLDecoder.decode(playFeature, "UTF-8");
			param.put("playFeature", playFeature);
		}
		
		if (hotelType!=null) {
			hotelType = URLDecoder.decode(hotelType, "UTF-8");
			param.put("hotelType", hotelType);
		}
		
		if (hotelLocation!=null) {
			hotelLocation = URLDecoder.decode(hotelLocation, "UTF-8");
			param.put("hotelLocation", hotelLocation);
		}
		
		if (playBrand!=null) {
			playBrand = URLDecoder.decode(playBrand, "UTF-8");
			param.put("playBrand", playBrand);
		}
		
		if (playNum!=null) {
			playNum = URLDecoder.decode(playNum, "UTF-8");
			param.put("playNum", playNum);
		}
		
		if (scenicPlace!=null) {
			scenicPlace = URLDecoder.decode(scenicPlace, "UTF-8");
			param.put("scenicPlace", scenicPlace);
		}
		
		if (landTraffic!=null) {
			landTraffic = URLDecoder.decode(landTraffic, "UTF-8");
			param.put("landTraffic", landTraffic);
		}
		
		if (landFeature!=null) {
			landFeature = URLDecoder.decode(landFeature, "UTF-8");
			param.put("landFeature", landFeature);
		}
		
		if (city!=null) {
			city = URLDecoder.decode(city, "UTF-8");
			param.put("city", city);
		}
		
		if (sort!=null) {
			sort = URLDecoder.decode(sort, "UTF-8");
			param.put("sort", sort);
		}
		
		if (!StringUtils.isEmpty(subProductType)) {
			subProductType = URLDecoder.decode(subProductType, "UTF-8");
			param.put("subProductType", subProductType);
		}
		
		if (!StringUtils.isEmpty(subProductTypeTitle)) {//类型TITLE
			subProductTypeTitle = URLDecoder.decode(subProductTypeTitle, "UTF-8");
		}
		if (!StringUtils.isEmpty(sortTitle)) {//排序TITLE
			sortTitle = URLDecoder.decode(sortTitle, "UTF-8");
		}
		
		param.put("page", page);

	}
	public String getSearchStatus() {
		return searchStatus;
	}
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	public String getIds() {
		return ids;
	}
	public void setIds(String ids) {
		this.ids = ids;
	}
	public IClientSearchService getMobileSearchService() {
		return mobileSearchService;
	}
	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}
	public boolean isAjax() {
		return ajax;
	}
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	public String getFromDest() {
		return fromDest;
	}
	public void setFromDest(String fromDest) {
		this.fromDest = fromDest;
	}
	public String getToDest() {
		return toDest;
	}
	public void setToDest(String toDest) {
		this.toDest = toDest;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getSubProductType() {
		return subProductType;
	}
	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public IClientRecommendService getMobileRecommendService() {
		return mobileRecommendService;
	}
	public void setMobileRecommendService(
			IClientRecommendService mobileRecommendService) {
		this.mobileRecommendService = mobileRecommendService;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getVisitDay() {
		return visitDay;
	}

	public void setVisitDay(String visitDay) {
		this.visitDay = visitDay;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getPlayLine() {
		return playLine;
	}

	public void setPlayLine(String playLine) {
		this.playLine = playLine;
	}

	public String getPlayFeature() {
		return playFeature;
	}

	public void setPlayFeature(String playFeature) {
		this.playFeature = playFeature;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getHotelLocation() {
		return hotelLocation;
	}

	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}

	public String getPlayBrand() {
		return playBrand;
	}

	public void setPlayBrand(String playBrand) {
		this.playBrand = playBrand;
	}

	public String getPlayNum() {
		return playNum;
	}

	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}

	public String getScenicPlace() {
		return scenicPlace;
	}

	public void setScenicPlace(String scenicPlace) {
		this.scenicPlace = scenicPlace;
	}

	public String getLandTraffic() {
		return landTraffic;
	}

	public void setLandTraffic(String landTraffic) {
		this.landTraffic = landTraffic;
	}

	public String getLandFeature() {
		return landFeature;
	}

	public void setLandFeature(String landFeature) {
		this.landFeature = landFeature;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubProductTypeTitle() {
		return subProductTypeTitle;
	}

	public void setSubProductTypeTitle(String subProductTypeTitle) {
		this.subProductTypeTitle = subProductTypeTitle;
	}

	public String getSortTitle() {
		return sortTitle;
	}

	public void setSortTitle(String sortTitle) {
		this.sortTitle = sortTitle;
	}

	public String getFirstPost() {
		return firstPost;
	}

	public void setFirstPost(String firstPost) {
		this.firstPost = firstPost;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getSubjects() {
		return subjects;
	}

	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}

	public String getInitKeyword() {
		return initKeyword;
	}

	public void setInitKeyword(String initKeyword) {
		this.initKeyword = initKeyword;
	}
	
}
