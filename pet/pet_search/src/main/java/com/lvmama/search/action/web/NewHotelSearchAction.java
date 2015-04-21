package com.lvmama.search.action.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;

import com.lvmama.comm.pet.po.place.PlaceHistoryCookie;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.FilterParamUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.PageConfig;

/**
 * 特色酒店搜索
 * 
 * @author YangGan
 *
 */
@Namespace("/hotel")
public class NewHotelSearchAction extends SearchAction {
	
	private static final long serialVersionUID = 7035609711200606447L;
	
	public NewHotelSearchAction() {
		super("hotel",HotelSearchVO.class,"SEARCH_HOTEL");
	}
	
	private HotelSearchVO hotelSearchvo;
	
	private String sort;
	
	private List<String> allHotelTopic;//所有酒店主题
	
	private List<String> allProdTopic;//所有产品主题
	
	/**显示风格默认显示详情*/
	private String display="";
	
	private String fristPicStr="";//第一个酒店的第一图片
	
	private List<String> keyWordHistrory;
	
	private List<ProductBean> weekHotProd;
	
	private List<PlaceHistoryCookie> hotelCookie;
	/**
	 * 是否是首页
	 */
	private boolean index;
	@Override
	public void parseFilterStr() {
		hotelSearchvo = (HotelSearchVO) super.fillSearchvo();
		if(sorts!=null && sorts.length == 1){
			sort = String.valueOf(sorts[0].getVal());
		}
	}
	
	@Action("search")
	@Override
	public String search(){
		if(StringUtils.isEmpty(params) && "hotel".equals(type)){
			params = (String) getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
			if(StringUtils.isEmpty(params)){
				params = "上海";
			}
			index = true;
		}else{
			index = false;
		}
		return super.search();
	}
	@Override
	protected void initTitle(){
		if(index){
			String index_code =this.seoPageCode +"_INDEX";
			SeoIndexPage sip =  (SeoIndexPage) LocalCacheManager.get("SIP_"+index_code);
			if(sip == null){
				sip = seoIndexPageService.getSeoIndexPageByPageCode(index_code);
				if(sip == null){
					throw new RuntimeException("Can not find SEO_INDEX_PAGE. The PAGE_CODE is:"+index_code);
				}
				LocalCacheManager.put("SIP_"+index_code, sip);
			}
			pageTitle = sip.getSeoTitle();
			pageKeyword = sip.getSeoKeyword();
			pageDescription = sip.getSeoDescription();
		}else{
			super.initTitle();
		}
	}
	@Override
	protected void initPageURL(){
		if("simple".equals(display)){
			pageConfig.setUrl("http://www.lvmama.com/search/"+type+"/"+display+"/"+this.keyword +FilterParamUtil.initPageURL(filterStr));
		}else{
			pageConfig.setUrl("http://www.lvmama.com/search/"+type+"/"+this.keyword +FilterParamUtil.initPageURL(filterStr));
		}
		try {
			setKeyWordHistrory();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void searchData() {
		hotelSearchvo.setPageSize(30);
		//依据查询和排序对象，构造Query查询条件，查询酒店Place
		String tempkeyword = hotelSearchvo.getKeyword();//保存拆分完的keyword
		hotelSearchvo.setKeyword(hotelSearchvo.getOrikeyword());
		
		
		pageConfig = hotelSearchService.search(hotelSearchvo, sorts);
		//关键词是否本地
		if(hotelSearchvo.getLongitude()!= null && hotelSearchvo.getLocal() == null){
			String localName = (String)getRequest().getAttribute(Constant.IP_FROM_PLACE_NAME);
			if(localName.equalsIgnoreCase(hotelSearchvo.getKeyword())){
				//xx本地查询
				hotelSearchvo.setLocal(0);
				filterStr=FilterParamUtil.initURL(filterStr,"N",0,true,false,false,false);
			}else{
				//XX周边查询
				hotelSearchvo.setLocal(1);
				filterStr=FilterParamUtil.initURL(filterStr,"N",1,true,false,false,false);
				filterStr=FilterParamUtil.initURL(filterStr,"Z",hotelSearchvo.getDistance(),true,false,false,false);
			}
		}
		
		//一周自由行套餐热榜
//		weekHotProd = hotelSearchService.getProduct(hotelSearchvo.getKeyword(), 5);
		weekHotProd = hotelSearchService.getProduct(tempkeyword, 5);
		hotelCookie = getViewProdHistoryCookie();
		setSearchFilterParams();
		
		//当使用了二次搜索时 高亮搜索关键词
		if (hotelSearchvo!=null && StringUtils.isNotEmpty(hotelSearchvo.getKeyword2().trim())) {
			CommonUtil.hotelNameHighlight(pageConfig,hotelSearchvo.getKeyword2().trim());
		}
		
		List<PlaceHotelBean> productList = pageConfig.getAllItems();
		if(productList.size() > 0){
			if(productList.get(0).getHotelImageList().size() > 0){
				fristPicStr = productList.get(0).getHotelImageList().get(0).get("url");
			}
		}
	}
	 
	/**
	 * 利用TDK模版生成页面TDK信息
	 * @param str TDK模版
	 * @return
	 */
	@Override
	public String replacePageInfo(String str){
		if(!StringUtil.isEmptyString(str)){
			String regex = "\\{([a-zA-Z]+)\\}";
			Pattern p = Pattern.compile(regex);
			Matcher m=p.matcher(str);
			while(m.find()){
				String fieldName = m.group(1);//属性
				Object val = filedValueMap.get(fieldName);//获取属性值
				if(val != null){
					if(fieldName.equals("local")){
						String local_val = val.toString();
						if("1".equals(local_val)){
							str = str.replaceAll("\\{"+fieldName+"\\}","本地");
						}else{
							str = str.replaceAll("\\{"+fieldName+"\\}","周边");
						}
					}else if(fieldName.equals("star")){
						List<String> stars = (List)val;
						String starStr = "";
						for(String s : stars){
							if("5".equals(s)){
								starStr = starStr + "五星级/豪华型 ";
							}else if("4".equals(s)){
								starStr = starStr + "四星级/高档型 ";
							}else if("3".equals(s)){
								starStr = starStr + "三星级/舒适型 ";
							}else if("2".equals(s)){
								starStr = starStr + "二星级/简约型 ";
							}
						}
						str = str.replaceAll("\\{"+fieldName+"\\}", starStr);
					}else if(fieldName.equals("startPrice")){
						str = str.replaceAll("\\{"+fieldName+"\\}", val + "元以上");
					}else if(fieldName.equals("endPrice")){
						str = str.replaceAll("\\{"+fieldName+"\\}", val + "元以下");
					}
					if(val.getClass().equals(ArrayList.class)){
						String val_str = StringUtils.join((List<String>)val, " ");
						str = str.replaceAll("\\{"+fieldName+"\\}",val_str);
					}else{
						str = str.replaceAll("\\{"+fieldName+"\\}",val.toString());
					}
				}else{
					str = str.replaceAll("\\{"+fieldName+"\\}","");
				}
			}
		}
		return str;
	}
	private List<PlaceHistoryCookie> getViewProdHistoryCookie(){
		Cookie cookie = ServletUtil.getCookie(getRequest(),Constant.HOLIDAY_HOTEL_PLACEID);
		List<PlaceHistoryCookie> historyCookieList = new ArrayList<PlaceHistoryCookie>();
		try {
			//查询cookie中的己有浏览记录
			if(cookie != null){
				JSONArray cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
				for (int i = 0; i < cookieList.size(); i++) {
					historyCookieList.add((PlaceHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),PlaceHistoryCookie.class));
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return historyCookieList;
	}
	
	private void putBaseFilterParams2Request(Map<String,LinkedHashMap<String, Integer>> map){
		HttpServletRequest req = getRequest();
		/**页面地区筛选条件*/
		req.setAttribute("cities_base", map.get("cities"));
		/**页面酒店主题筛选条件*/
		req.setAttribute("allHotelTopics_base", map.get("allHotelTopics"));
		/**页面产品主题筛选条件*/
		req.setAttribute("allProdTopics_base", map.get("allProdTopics"));
		/**页面产品星级筛选条件*/
		req.setAttribute("allStars_base", map.get("allStars"));
	}
	
	private void setSearchFilterParams(){
		HttpServletRequest req = getRequest();
		/**页面地区筛选条件*/
		LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
		/**页面酒店主题筛选条件*/
		LinkedHashMap<String, Integer> allHotelTopics = new LinkedHashMap<String, Integer>();
		/**页面产品主题筛选条件*/
		LinkedHashMap<String, Integer> allProdTopics = new LinkedHashMap<String, Integer>();
		/**页面产品星级筛选条件*/
		LinkedHashMap<String, Integer> allStars = new LinkedHashMap<String, Integer>();
		
		//设置缓存中的默认筛选条件Pattern p = Pattern.compile("^([N][0-9]+)(Z[0-9]+)([Q][0-9]+)?$"); 
		Pattern p = Pattern.compile("^([N][0-9]+)(Z[0-9]+)?$"); 
		Matcher m = p.matcher(filterStr); 
		if(m.find() || StringUtils.isEmpty(filterStr)){//变更地区条件，变更刷选条件
			boolean without_city = false;
			if( hotelSearchvo.getLongitude() != null || hotelSearchvo.getLocal() !=null ){
				without_city = true;
			}
			//生成基础的筛选条件
			Map<String,LinkedHashMap<String, Integer>> map = CommonUtil.initFilterParam_HOTEL(searchvo.getKeyword(), pageConfig.getAllItems(),"ALL",without_city);
			//把基础筛选条件放入request中
			putBaseFilterParams2Request(map);
			//放入缓存中
			ServletUtil.putSession(getRequest(), getResponse(), "HOTEL_SEARCH_FILTER_PARAMS", map);
			cities = map.get("cities");
			allHotelTopics = map.get("allHotelTopics");
			allProdTopics = map.get("allProdTopics");
			allStars = map.get("allStars");
		}else{
			Map<String,LinkedHashMap<String, Integer>> map = (Map<String,LinkedHashMap<String, Integer>>) ServletUtil.getSession(getRequest(), getResponse(), "HOTEL_SEARCH_FILTER_PARAMS");
			
			if(map != null){
				log.info("缓存中读取到基础筛选条件Map!");
				//把基础筛选条件放入request中
				putBaseFilterParams2Request(map);
			}else{
				HotelSearchVO hsv = new HotelSearchVO();
				hsv.setKeyword(this.hotelSearchvo.getKeyword());
				hsv.setLocal(this.hotelSearchvo.getLocal());
				hsv.setDistance(this.hotelSearchvo.getDistance());
				PageConfig tmp_pageConfig = hotelSearchService.search(hsv, sorts);
				log.info("缓存中未读取到基础筛选条件Map,重新查询后的结果数量:"+tmp_pageConfig.getTotalResultSize());
				boolean without_city = false;
				if( hotelSearchvo.getLongitude() != null || hotelSearchvo.getLocal() !=null ){
					without_city = true;
				}
				//生成基础的筛选条件
				map = CommonUtil.initFilterParam_HOTEL(keyword, tmp_pageConfig.getAllItems(),"ALL", without_city);
				//把基础筛选条件放入request中
				putBaseFilterParams2Request(map);
				//放入缓存中
				ServletUtil.putSession(getRequest(), getResponse(), "HOTEL_SEARCH_FILTER_PARAMS", map);
			}
			
			HotelSearchVO hsv = new HotelSearchVO();
			try {
				PropertyUtils.copyProperties(hsv, hotelSearchvo);
			} catch (Exception e) {
				throw new RuntimeException(e);
			} 
			PageConfig tmp_pageConfig = null;
			
			List<String> list_tmp= hsv.getStar();
			if(list_tmp == null || list_tmp.size() ==0 ){
				tmp_pageConfig = pageConfig;
			}else{
				hsv.setStar(null);
				tmp_pageConfig = hotelSearchService.search(hsv, sorts);
				hsv.setStar(list_tmp);
			}
			allStars = CommonUtil.initFilterParam_HOTEL(keyword, tmp_pageConfig.getAllItems(),"STAR",true).get("allStars");
			
			list_tmp= hsv.getHotelTopics();
			if(list_tmp == null || list_tmp.size() ==0 ){
				tmp_pageConfig = pageConfig;
			}else{
				hsv.setHotelTopics(null);
				tmp_pageConfig = hotelSearchService.search(hsv, sorts);
				hsv.setHotelTopics(list_tmp);
			}
			allHotelTopics = CommonUtil.initFilterParam_HOTEL(keyword, tmp_pageConfig.getAllItems(),"HOTEL_TOPIC",true).get("allHotelTopics");
		
			
			list_tmp= hsv.getProdTopics();
			if(list_tmp == null || list_tmp.size() ==0 ){
				tmp_pageConfig = pageConfig;
			}else{
				hsv.setProdTopics(null);
				tmp_pageConfig = hotelSearchService.search(hsv, sorts);
				hsv.setProdTopics(list_tmp);
			}
			allProdTopics = CommonUtil.initFilterParam_HOTEL(keyword, tmp_pageConfig.getAllItems(),"PROD_TOPIC",true).get("allProdTopics");

			if( hotelSearchvo.getLongitude() == null ){
				String city= hsv.getCity();
				if(StringUtils.isEmpty(city)){
					tmp_pageConfig = pageConfig;
				}else{
					hsv.setCity(null);
					tmp_pageConfig = hotelSearchService.search(hsv, sorts);
					hsv.setCity(city);
				}
				cities = CommonUtil.initFilterParam_HOTEL(keyword, tmp_pageConfig.getAllItems(),"CITY",false).get("cities");
			}
		}
		
		/**页面地区筛选条件*/
		req.setAttribute("cities", cities);
		/**页面酒店主题筛选条件*/
		req.setAttribute("allHotelTopics", allHotelTopics);
		/**页面产品主题筛选条件*/
		req.setAttribute("allProdTopics", allProdTopics);
		/**页面产品星级筛选条件*/
		req.setAttribute("allStars", allStars);
	}
	
	private void setKeyWordHistrory() throws UnsupportedEncodingException{
		// 获取cookieKey
		String[] cookieKey = null;
		String searchHistrory ="";
		
		Cookie cookie = ServletUtil.getCookie(getRequest(), "HOLIDAY_HOTEL_KEY");
		keyWordHistrory = new ArrayList<String>();
		if (cookie == null) {
			searchHistrory = hotelSearchvo.getKeyword();
			keyWordHistrory.add(searchHistrory);
		}else{
			keyWordHistrory.add(hotelSearchvo.getKeyword());
			searchHistrory = URLDecoder.decode(cookie.getValue(), "UTF-8");;
			cookieKey = searchHistrory.split(",");
			for(String s : cookieKey){
				if(!hotelSearchvo.getKeyword().equals(s)){
					keyWordHistrory.add(s);
				}
			}
			int size = keyWordHistrory.size()>10 ? 10 : keyWordHistrory.size();
			searchHistrory = StringUtils.join(keyWordHistrory.subList(0, size),",");
		 } 
		
		ServletUtil.addCookie(getResponse(), "HOLIDAY_HOTEL_KEY", URLEncoder.encode(searchHistrory, "UTF-8"));
	}
	
	public HotelSearchVO getHotelSearchvo() {
		return hotelSearchvo;
	}

	public String getSort() {
		return sort;
	}

	public String getDisplay() {
		return display;
	}

	public void setDisplay(String display) {
		this.display = display;
	}

	public List<String> getAllHotelTopic() {
		return allHotelTopic;
	}

	public void setAllHotelTopic(List<String> allHotelTopic) {
		this.allHotelTopic = allHotelTopic;
	}

	public List<String> getAllProdTopic() {
		return allProdTopic;
	}

	public void setAllProdTopic(List<String> allProdTopic) {
		this.allProdTopic = allProdTopic;
	}

	public List<ProductBean> getWeekHotProd() {
		return weekHotProd;
	}

	public void setWeekHotProd(List<ProductBean> weekHotProd) {
		this.weekHotProd = weekHotProd;
	}

	public List<PlaceHistoryCookie> getHotelCookie() {
		return hotelCookie;
	}

	public void setHotelCookie(List<PlaceHistoryCookie> hotelCookie) {
		this.hotelCookie = hotelCookie;
	}

	public List<String> getKeyWordHistrory() {
		return keyWordHistrory;
	}

	public void setKeyWordHistrory(List<String> keyWordHistrory) {
		this.keyWordHistrory = keyWordHistrory;
	}

	public String getFristPicStr() {
		return fristPicStr;
	}

	public void setFristPicStr(String fristPicStr) {
		this.fristPicStr = fristPicStr;
	}
	
}
