package com.lvmama.search.action.web;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.PlaceFromDestBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.search.lucene.document.ProductDocument;
import com.lvmama.search.lucene.service.autocomplete.AutoCompleteService;
import com.lvmama.search.lucene.service.search.NewBaseSearchService;
import com.lvmama.search.util.PageConfig;
import com.opensymphony.xwork2.ActionSupport;

public class SearchBaseAction extends ActionSupport{
	private static final long serialVersionUID = 2634590822032403408L;
	@Resource
	private UserActionCollectionService userActionCollectionService;
	@Resource
	protected NewBaseSearchService newPlaceSearchService;
	@Resource
	protected NewBaseSearchService newProductSearchService;
	@Resource
	protected NewBaseSearchService newProductBranchSearchService;
	@Resource
	protected AutoCompleteService autoCompleteService;
	
	public static String FRONTEND = "FRONTEND";
	public static String TENCENT = "TENCENT";
	public static String WAP = "WAP";
	/** 起始价格 */
	protected String startPrice="";
	/** 结束价格 */
	protected String endPrice="";
	/**按产品价格排序**/
	protected String sortPrice="";
	/** 景点 **/
	protected String scenicPlace="";
	/** 游玩人数**/
	protected String playNum="";
	/** 包含地区 **/
	protected String city="";
	/** 主题 **/
	protected String subject="";
	/** 酒店类型 **/
	protected String hotelType="";
	/** 特色品牌 **/
	protected String playBrand="";
	/** 游玩区域 */
	protected String playArea="";
	/** 游玩线路*/
	protected String playLine="";
	/** 游玩特色 */
	protected String playFeature="";
	/** 往返交通*/
	protected String traffic="";
	/** 酒店位置 */
	protected String hotelLocation="";
	/** 线路主题 */
	protected String routeTopic="";
	/** 出发班期 */
	protected String travelTime="";
	/**页面筛选条件封装**/
	protected LinkedHashMap<String, Integer> scenicPlaceMap = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> playFeatures = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> playLines = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> subjects = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> visitDays = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> traffics = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> hotelTypes  = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> hotelLocations  = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> playBrands = new LinkedHashMap<String, Integer>();
	protected LinkedHashMap<String, Integer> playNums = new LinkedHashMap<String, Integer>();
	

	/**
	 * 初始化目的地,防止从主题，或是标签入口进来的链接异常
	 * 
	 * @param toDest
	 * @param subject
	 * @param tag
	 */
	protected String initToDest(String toDest,String subject,String tag) {
		if(StringUtils.isNotBlank(toDest)){
			return toDest;
		}else{
			String destResult="";
			if(StringUtils.isNotBlank(tag)){
				destResult=tag;
			}
			if(StringUtils.isNotBlank(subject)){
				destResult=subject;
			}
			return destResult;
		}
	}
	/**
	 * Convenience method to get the request
	 * 
	 * @return current request
	 */
	protected HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}
	protected HttpSession getSession() {
		return ServletActionContext.getRequest().getSession();
	}
	public String getStationValue(){
		String station = getCookieValue("CLOCATION");
		if("www".equals(station)){
			return "main";
		}
		return station;
	}
	public String getCookieValue(String cookieName){
		Cookie[] cookies = this.getRequest().getCookies(); 
		if(cookies != null && cookies.length>0){
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if(cookieName.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return "";
	}
	
    /**
     * 发送查询行为的消息，用于行为收集器(NSSO)收集用户的查询行为
     * @param keyword 关键词
     */
	protected void sendSearchActionMessage(String keyword) {
		String userId = getCookieValue("UN");
		if (userId.indexOf("%5E%21%5E") != -1) {
			userId = userId.substring(userId.indexOf("%5E%21%5E") + "%5E%21%5E".length(), userId.length());
		} else {
			userId = null;
		}	
		userActionCollectionService.save(userId, InternetProtocol.getRemoteAddr(getRequest()), "SEARCH", keyword);
	}
	
	public String convertPlaceListToJsonString(List<PlaceFromDestBean> placeList ,String toDest) {
		StringBuffer sb = new StringBuffer("[");
		for (PlaceFromDestBean placeFromDestBean : placeList) {
			sb.append("{");
	    	sb.append("\"name\":\"" + placeFromDestBean.getPlaceName() + "\",");
	    	sb.append("\"pinYin\":\"" + placeFromDestBean.getPlacePinyin() + "\"");
	    	sb.append("},");
			}
		if(placeList.size()>0){
			sb.deleteCharAt(sb.length() - 1).append("]");		
		}else{
			sb.append("]");	
		}
		sb.append(","+"\"totalResultSize\":\"" + placeList.size()+"\"");
		sb.append(","+"\"toDest\":\"" + toDest+"\"");
		return sb.toString();
	}

	/**
	 * 根据来源不同显示不同的热线电话
	 * @return
	 */
	public String getShowDifferntHotLine() {
		String tele = this.getRequest().getParameter("tele");
		if (null != tele) {
			Cookie cookie = new Cookie("tele", tele);
			cookie.setDomain(".lvmama.com");
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			getResponse().addCookie(cookie);				
		} else {
			tele =getCookieValue("tele");
		}
		return tele;
	}	
	/**
	 * 解析param（格式：str,str,str,str）并放入map中
	 * 
	 * @param map
	 * @param param
	 */
	public void collectParamList(Map<String,Integer> map, String param) {
		if (StringUtils.isNotBlank(param)) {
			String[] paramArray = param.split(SearchConstants.DELIM);
			if (paramArray != null) {
				for (String paramValue : paramArray) {
					if(StringUtils.isNotBlank(paramValue)) {
						if (map.get(paramValue)==null) {
							map.put(paramValue, 1);
						}else{
							Integer sum = map.get(paramValue)+1;
							map.put(paramValue, sum);
						}
					}
				}
			}
		}
		//排序
		map = sortMap(map);
	}
	
	
	/**
	 * 把map按value排序，降序
	 * **/
	public LinkedHashMap<String,Integer> sortMap(Map<String,Integer> map){
		List<Map.Entry<String,Integer>> mapList = new ArrayList<Map.Entry<String,Integer>>(map.entrySet());		
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() { 
		    public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
		        return o2.getValue() - o1.getValue();
		    }
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for(Map.Entry<String,Integer> mapping:mapList){
			  sortMap.put(mapping.getKey(), mapping.getValue());
			  } 
		return sortMap; 
	}	
	
	/**
	 * 收集,排重城市cites,主题subjects,游玩日期days
	 * 
	 * @param placelist
	 */
	protected void paramCollection(PageConfig<ProductBean> pageConfigCollectTop100) {
		if (pageConfigCollectTop100 != null && pageConfigCollectTop100.getItems().size() > 0) {
			List<ProductBean> productlist = pageConfigCollectTop100.getItems();			
			for (int i = 0; i < productlist.size(); i++) {
				List<Integer> tmpList = new ArrayList<Integer>();
				this.collectParamList(subjects, productlist.get(i).getRouteTopic());
				this.collectParamList(traffics, productlist.get(i).getTraffic());
				this.collectParamList(playLines, productlist.get(i).getPlayLine());
				this.collectParamList(playFeatures, productlist.get(i).getPlayFeatures());
				this.collectParamList(hotelTypes, productlist.get(i).getHotelType());
				this.collectParamList(hotelLocations, productlist.get(i).getHotelLocation());
				this.collectParamList(playBrands, productlist.get(i).getPlayBrand());
				this.collectParamList(playNums, productlist.get(i).getPlayNum());
				this.collectParamList(scenicPlaceMap, productlist.get(i).getScenicPlace());
				this.collectParamList(visitDays, Long.toString(productlist.get(i).getVisitDay()));				
				String allDest = productlist.get(i).getProductAllToPlace();
				if (allDest != null && !"".equals(allDest)) {
					StringTokenizer st = new StringTokenizer(allDest, ",");
					while (st.hasMoreTokens()) {
						String city1 = st.nextToken();
						if (city1 != null && !"".equals(city1) && !city1.matches("[0-9]+")) {// CITY和CITYID共用，去掉CITYID
							if (cities.get(city1)==null) {
								cities.put(city1, 1);
							}else{
								Integer sum = cities.get(city1)+1;
								cities.put(city1, sum);
							}
						}
					}
				}
				//排序
				subjects = sortMap(subjects);
				traffics = sortMap(traffics);
				playLines = sortMap(playLines);
				playFeatures = sortMap(playFeatures);
				hotelTypes = sortMap(hotelTypes);
				hotelLocations = sortMap(hotelLocations);
				playBrands = sortMap(playBrands);
				playNums = sortMap(playNums);
				scenicPlaceMap = sortMap(scenicPlaceMap);
				visitDays = sortMap(visitDays);
				cities = sortMap(cities);
			}

		}
	}

	/**
	 * 获取目的地自由行筛选参数
	 * 
	 * @return
	 */
	protected void assemblyFreetourParam(Map<String, String> map){
		map.put(ProductDocument.SCENIC_PLACE, scenicPlace);
		map.put(ProductDocument.PLAY_BRAND, playBrand);
		map.put(ProductDocument.HOTEL_TYPE, hotelType);
		map.put(ProductDocument.PLAY_NUM, playNum);
	}
	public String getStartPrice() {
		return startPrice;
	}

	public void setStartPrice(String startPrice) {
		this.startPrice = startPrice;
	}

	public String getEndPrice() {
		return endPrice;
	}

	public void setEndPrice(String endPrice) {
		this.endPrice = endPrice;
	}

	public String getSortPrice() {
		return sortPrice;
	}

	public void setSortPrice(String sortPrice) {
		this.sortPrice = sortPrice;
	}

	public String getScenicPlace() {
		return scenicPlace;
	}

	public void setScenicPlace(String scenicPlace) {
		this.scenicPlace = scenicPlace;
	}

	public String getPlayNum() {
		return playNum;
	}

	public void setPlayNum(String playNum) {
		this.playNum = playNum;
	}

	public String getHotelType() {
		return hotelType;
	}

	public void setHotelType(String hotelType) {
		this.hotelType = hotelType;
	}

	public String getPlayBrand() {
		return playBrand;
	}

	public void setPlayBrand(String playBrand) {
		this.playBrand = playBrand;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getPlayArea() {
		return playArea;
	}

	public void setPlayArea(String playArea) {
		this.playArea = playArea;
	}

	public String getPlayLine() {
		return playLine;
	}

	public void setPlayLine(String playLine) {
		this.playLine = playLine;
	}

	public String getTraffic() {
		return traffic;
	}

	public void setTraffic(String traffic) {
		this.traffic = traffic;
	}

	public String getHotelLocation() {
		return hotelLocation;
	}

	public void setHotelLocation(String hotelLocation) {
		this.hotelLocation = hotelLocation;
	}

	public String getRouteTopic() {
		return routeTopic;
	}

	public void setRouteTopic(String routeTopic) {
		this.routeTopic = routeTopic;
	}

	public String getTravelTime() {
		return travelTime;
	}

	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}

	public String getPlayFeature() {
		return playFeature;
	}

	public void setPlayFeature(String playFeature) {
		this.playFeature = playFeature;
	}
	public Map<String, Integer> getCities() {
		return cities;
	}
	
	public LinkedHashMap<String, Integer> getSubjects() {
		return subjects;
	}
	public void setSubjects(LinkedHashMap<String, Integer> subjects) {
		this.subjects = subjects;
	}
	public LinkedHashMap<String, Integer> getVisitDays() {
		return visitDays;
	}
	public void setVisitDays(LinkedHashMap<String, Integer> visitDays) {
		this.visitDays = visitDays;
	}
	public LinkedHashMap<String, Integer> getTraffics() {
		return traffics;
	}
	public void setTraffics(LinkedHashMap<String, Integer> traffics) {
		this.traffics = traffics;
	}
	public LinkedHashMap<String, Integer> getHotelTypes() {
		return hotelTypes;
	}
	public void setHotelTypes(LinkedHashMap<String, Integer> hotelTypes) {
		this.hotelTypes = hotelTypes;
	}
	public LinkedHashMap<String, Integer> getHotelLocations() {
		return hotelLocations;
	}
	public void setHotelLocations(LinkedHashMap<String, Integer> hotelLocations) {
		this.hotelLocations = hotelLocations;
	}
	public LinkedHashMap<String, Integer> getPlayBrands() {
		return playBrands;
	}
	public void setPlayBrands(LinkedHashMap<String, Integer> playBrands) {
		this.playBrands = playBrands;
	}
	public LinkedHashMap<String, Integer> getPlayNums() {
		return playNums;
	}
	public void setPlayNums(LinkedHashMap<String, Integer> playNums) {
		this.playNums = playNums;
	}
	public void setScenicPlaceMap(LinkedHashMap<String, Integer> scenicPlaceMap) {
		this.scenicPlaceMap = scenicPlaceMap;
	}
	public void setPlayFeatures(LinkedHashMap<String, Integer> playFeatures) {
		this.playFeatures = playFeatures;
	}
	public void setPlayLines(LinkedHashMap<String, Integer> playLines) {
		this.playLines = playLines;
	}
	public void setCities(LinkedHashMap<String, Integer> cities) {
		this.cities = cities;
	}
	public Map<String, Integer> getScenicPlaceMap() {
		return scenicPlaceMap;
	}
	public Map<String, Integer> getPlayFeatures() {
		return playFeatures;
	}
	public Map<String, Integer> getPlayLines() {
		return playLines;
	}
	protected HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}
}
