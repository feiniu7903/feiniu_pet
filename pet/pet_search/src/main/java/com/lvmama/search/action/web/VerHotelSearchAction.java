package com.lvmama.search.action.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.place.PlaceHistoryCookie;
import com.lvmama.comm.pet.po.place.ProductHistoryCookie;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerHotelSearchVO;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.search.vo.VerPlaceTypeVO;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.search.util.CommonUtil;
import com.lvmama.search.util.FilterParamUtil;
import com.lvmama.search.util.LocalCacheManager;
import com.lvmama.search.util.PageConfig;
import com.lvmama.search.util.SORT;
import com.lvmama.search.util.SearchHttpsClient;

/**
 * 酒店搜索
 * 
 * @author YangGan
 *
 */
@Results({
	@Result(name="aaa",location="/autocomplete.ftl",type="freemarker")
})
@Namespace("/verhotel")
public class VerHotelSearchAction extends SearchAction {
	
	private static final long serialVersionUID = 7035609711200606447L;
	private static final String PLATFORM_HOTEL_PRODUCTID = "PLATFORM_HOTEL_PRODUCTID";
	private static Calendar calendar = new GregorianCalendar();
	private static Date date=new Date();
	
	private static  SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	
	public VerHotelSearchAction() {
		super("verhotel",VerHotelSearchVO.class,"CH_VER_HOTEL_SEARCH");
	}
	
	private VerHotelSearchVO verHotelSearchVO;
	
	private String sort;
	private String landMarkName;
	
	private List<String> allHotelTopic;//所有酒店主题
	
	private List<String> allProdTopic;//所有产品主题
	
	/**显示风格默认显示详情*/
	private String display="";
	
	private String fristPicStr="";//第一个酒店的第一图片
	
	private List<String> keyWordHistrory;
	
	private List<ProductBean> weekHotProd;
	
	private List<ProductHistoryCookie> hotelCookie;
	
	private Map<String,Object> seoFriendsLink;
	
	private String placeId;

	/**
	 * 是否是首页
	 */
	private boolean index;
	@Override
	public void parseFilterStr() {

		verHotelSearchVO = (VerHotelSearchVO) super.fillSearchvo();
		if(sorts!=null && sorts.length == 1){
			sort = String.valueOf(sorts[0].getVal());
		}
		
		//seo解析标地，品牌，星级
		//转化parms中文
		if(Pattern.matches("[a-zA-Z]*", params) && StringUtil.isNotEmptyString(params)){
			List<VerPlaceBean> verPlaceBeanList = verHotelSearchService.getPlaceChineseName(params);
			
			if(verPlaceBeanList != null && verPlaceBeanList.size() > 0){
				VerPlaceBean verPlaceBean = verPlaceBeanList.get(0);
				
				if(StringUtil.isNotEmptyString(verPlaceBean.getPlaceName())){
					this.request_keyword = verPlaceBean.getPlaceName();
				}
				
				if(StringUtil.isNotEmptyString(verPlaceBean.getPlaceId())){
					placeId = verPlaceBean.getPlaceId();
				}
			}
		}
		
		String tempStar = "";
		if(StringUtil.isNotEmptyString(src) && src.equals("seo") && StringUtil.isNotEmptyString(hotel_star)){
			tempStar = hotel_star;
			if(hotel_star.equals("5")){
				this.hotel_star = "100-101";
			}else if(hotel_star.equals("4")){
				this.hotel_star = "102-103";
			}else if(hotel_star.equals("3")){
				this.hotel_star = "104-105";
			}else{
				this.hotel_star = "106-107";
			}
		}
		if(StringUtil.isNotEmptyString(landMark)){
			VerPlaceBean placeBean = verHotelSearchService.getPlaceBean(landMark);
			
			if(placeBean != null){
				landMarkName = placeBean.getPlaceName();
				this.request_keyword = landMarkName;
				
				parentID = placeBean.getParentId();
				hotel_longitude = placeBean.getLongitude();
				hotel_latitude = placeBean.getLatitude();
				loger.info(this.keyword);
				loger.info(parentID);
				loger.info(hotel_longitude);
				loger.info(hotel_latitude);
			}
		}
		
		//action url
		String actionUrl = "http://hotels.lvmama.com/";
		if(StringUtil.isNotEmptyString(params) && StringUtil.isNotEmptyString(src) && src.equals("seo")){
			actionUrl += params+ "/";
			if(StringUtil.isNotEmptyString(landMark)){
				actionUrl += "place"+ landMark + "/";
			}
			if(StringUtil.isNotEmptyString(tempStar)){
				actionUrl += "star" + tempStar + "/";
			}
			if(StringUtil.isNotEmptyString(hotelBrand)){
				actionUrl += "brand" + hotelBrand + "/";
			}
		}else{
			actionUrl = "";
		}
		
		verHotelSearchVO.setActionUrl(actionUrl);
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
			String []tmp1=params.split(",");
			params=tmp1.length==2?tmp1[0]:params;
			index = false;
		}
		return super.search();
		
	}
	@Action("search111")
	public String search111(){
		
		return "aaa";
	}
	
	@Override
	protected void initTitle(){
		if(index){
			String index_code =this.seoPageCode;
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
			pageConfig.setUrl("http://www.lvmama.com/search/"+type+"/"+this.searchvo.getKeyword());
		}
		try {
			setKeyWordHistrory();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	protected void searchData() {
		
		verHotelSearchVO=papareVerHotelSearchvo(verHotelSearchVO,getRequest());
		verHotelSearchVO.setPageSize(25);
		verHotelSearchVO=verHotelSearchService.getDistrictId(verHotelSearchVO);
		String ranktype=verHotelSearchVO.getRanktype();
		if(StringUtil.isNotEmptyString(verHotelSearchVO.getRanktype())){
			if(verHotelSearchVO.getRanktype().equals("3") ){
				sorts = new SORT[]{SORT.getSort(13)};
			}else if(verHotelSearchVO.getRanktype().equals("4")){
				sorts = new SORT[]{SORT.getSort(14)};
			}
		}
		else {
			if (StringUtil.isEmptyString(verHotelSearchVO.getLatitude()) || "0.0".equals(verHotelSearchVO.getLatitude())) {
				ranktype = "1";
			} else {
				ranktype = "2";
			}
		}
		pageConfig = verHotelSearchService.search(verHotelSearchVO, sorts);
		//TDK star
		if(StringUtil.isNotEmptyString(verHotelSearchVO.getHotelStar())){
			String[] hotelStars = verHotelSearchVO.getHotelStar().split(",");
			StringBuffer hotelStarName = new StringBuffer();
			String hotelStar = "";
			for (int i = 0; i < hotelStars.length; i++) {
				hotelStar = hotelStars[i];
				if(i == hotelStars.length-1){
					if("100-101".contains(hotelStar)){
						hotelStarName.append("五星级/豪华型");
					}else if("102-103".contains(hotelStar)){
						hotelStarName.append("四星级/高档型");
					}else if("104-105".contains(hotelStar)){
						hotelStarName.append("三星级/舒适型");
					}else{
						hotelStarName.append("二星级/简约型");
					}
				}else{
					if("100-101".contains(hotelStar)){
						hotelStarName.append("五星级/豪华型" + ",");
					}else if("102-103".contains(hotelStar)){
						hotelStarName.append("四星级/高档型" + ",");
					}else if("104-105".contains(hotelStar)){
						hotelStarName.append("三星级/舒适型" + ",");
					}else{
						hotelStarName.append("二星级/简约型" + ",");
					}
				}
			}
			verHotelSearchVO.setHotelStarName(hotelStarName.toString());
			this.filedValueMap.put("hotelStarName", hotelStarName.toString());
		}
		//TDK landMark
		if(StringUtil.isNotEmptyString(landMarkName)){
			verHotelSearchVO.setLandMarkName(landMarkName);
			this.filedValueMap.put("landMarkName", landMarkName);
		}else{
			verHotelSearchVO.setLandMarkName("");
			this.filedValueMap.put("landMarkName", "");
		}
		
		//TDK brand
		if(StringUtil.isNotEmptyString(verHotelSearchVO.getHotel_brand())){
			StringBuffer hotelBrandName = new StringBuffer();
			List<String> hotelBrands = new ArrayList<String>();
			
			if(pageConfig.getAllItems().size() > 0){
				
				String brand = "";
				for (int i = 0; i < pageConfig.getAllItems().size(); i++) {
					VerHotelBean verHotelBean = (VerHotelBean)pageConfig.getAllItems().get(i);
					brand = verHotelBean.getHotelbrand();
					String [] tmp1=brand.split("（");
					brand=tmp1[0];
					brand = brand.trim();
					brand = brand.replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "");
					brand = brand.replaceAll("[a-zA-Z]+", ""); 
					if(!hotelBrands.contains(brand)){
						hotelBrands.add(brand);
						if(i != pageConfig.getAllItems().size()-1){
							hotelBrandName.append(brand + ",");
						}else{
							hotelBrandName.append(brand);
						}
					}
				}
				
				verHotelSearchVO.setHotel_brand_name(hotelBrandName.toString());
				this.filedValueMap.put("hotelBrandName", hotelBrandName.toString());
			}
		}
		
		this.filedValueMap.put("districtName", verHotelSearchVO.getDistrictName());
		this.filedValueMap.put("totalResultSize", pageConfig.getTotalResultSize());
		
		
		//友情链接
		friendLinkList();
		
		initFilterParam2Request_VEROHOTELTHER(verHotelSearchVO,getRequest());
		hotelCookie = getHistoryCookie(getRequest(),getResponse());
		verHotelSearchVO.setRanktype(ranktype);
		
	}
	
	//seo 静态地址友情链接
	private void friendLinkList(){
		String objectId = "";
		seoFriendsLink = new HashMap<String, Object>();
		HttpMethodParams seoParms = new HttpMethodParams();
		Map<String, Object> parms = new HashMap<String, Object>();
		if(StringUtil.isNotEmptyString(src) && src.equals("seo")){
			if(StringUtil.isNotEmptyString(landMark)){
				objectId = landMark;
				seoParms.setParameter("seoType", "AREA");
				parms.put("seoType", "AREA");
			}else if(StringUtil.isNotEmptyString(params) && StringUtil.isNotEmptyString(this.placeId)){
				objectId = this.placeId;
				seoParms.setParameter("seoType", "DISTRICT");
				parms.put("seoType", "DISTRICT");
			}else if(StringUtil.isNotEmptyString(params) && StringUtil.isNotEmptyString(verHotelSearchVO.getParentId())){
				objectId = verHotelSearchVO.getParentId();
				seoParms.setParameter("seoType", "DISTRICT");
				parms.put("seoType", "DISTRICT");
			}
		}
		
		if(StringUtil.isNotEmptyString(objectId)){
			HttpsUtil httpsClient = SearchHttpsClient.getSingle(); 
			parms.put("objectId", objectId);
			String responseHtml = httpsClient.requestGet("http://hotels.lvmama.com/prod/hotel/getSeoLinkList.do", parms);

			try {
				JSONArray jsonarray = JSONArray.fromObject(responseHtml);
				
				JSONObject jsonObject = null;
				seoFriendsLink = new HashMap<String, Object>();
				for (int i = 0; i < jsonarray.toArray().length; i++) {
					jsonObject = jsonarray.getJSONObject(i);
					seoFriendsLink.put(jsonObject.getString("linkName"), jsonObject.getString("linkUrl"));
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error(e.getMessage());
			}
			
		}
		
		getRequest().setAttribute("seoFriendsLink", seoFriendsLink);
	}
	 
	private void initFilterParam2Request_VEROHOTELTHER(
			VerHotelSearchVO verHotelSearchVO,HttpServletRequest request) {
		//先整体排好序，在去分类，分好类也是排好序的
		List<VerPlaceBean> placeList=verHotelSearchService.searchPlace(verHotelSearchVO);
		//获取地标类别
		 List<VerPlaceTypeVO> catageoryList=verHotelSearchService.getPlaceCatageory(new HashMap());
		CommonUtil.initFilterParam2Request_VERHOTELOTHER(keyword,request,placeList,catageoryList);
		VerHotelSearchVO vo=new VerHotelSearchVO();
		//品牌和设置的取 是通过取得当前搜索城市下所有的酒店品牌和设施
		vo.setParentId(verHotelSearchVO.getParentId());
		vo.setKeyword(verHotelSearchVO.getKeyword());
		vo.setLatitude("1");
		vo.setLongitude("1");
		CommonUtil.initFilterParam2Request_VERHOTEL(keyword, request, verHotelSearchService.search(vo, sorts).getAllItems());
	}

	private VerHotelSearchVO papareVerHotelSearchvo(VerHotelSearchVO vo,
			HttpServletRequest request) {
		
		 String searchId=request.getParameter("searchId");
		 String longitude=request.getParameter("longitude");
		 String latitude=request.getParameter("latitude");
		 String searchType=request.getParameter("searchType");
		 String parentId=request.getParameter("parentId");
		 String hotelStar=request.getParameter("hotelStar");
		 String hotelprice=request.getParameter("hotelprice");
		 String beginBookTime=request.getParameter("beginBookTime");
		 String endBookTime=request.getParameter("endBookTime");
		 String currentPage=request.getParameter("currentPage");
		 String hotel_brand=request.getParameter("hotel_brand");
		 String facilities=request.getParameter("facilities");
		 String room_type=request.getParameter("room_type");
		 String ranktype=request.getParameter("ranktype");
		 String minproductsprice=request.getParameter("minproductsprice");
		 String maxproductsprice=request.getParameter("maxproductsprice");
		 String issale=request.getParameter("issale");
		 String activeFlag=request.getParameter("activeFlag");
		 if(StringUtil.isNotEmptyString(facilities)){
			 try {
					facilities=new String(facilities.getBytes("iso-8859-1"),"utf-8");
				} catch (UnsupportedEncodingException e) {
					log.error("乱码转换错误",e);
					e.printStackTrace();
				}
		 }
		
		 if(StringUtil.isNotEmptyString(hotelprice)){
			 if(hotelprice.equals("1")){
				 vo.setMinproductsprice("600");
			 }
			 else if(hotelprice.equals("2")){
				 vo.setMinproductsprice("300");
				 vo.setMaxproductsprice("600");
			 }
			 else if(hotelprice.equals("3")){
				 vo.setMinproductsprice("150");
				 vo.setMaxproductsprice("300");
			 }
			 else if(hotelprice.equals("4")){
				 vo.setMinproductsprice("0");
				 vo.setMaxproductsprice("150");
			 }else {
				 vo.setMinproductsprice(minproductsprice);
				 vo.setMaxproductsprice(maxproductsprice);
			 }
		 }
		 
		 if(StringUtil.isNotEmptyString(room_type)){
			 if(room_type.equals("1")){
				 vo.setRoom_type("大床房");
			 }
			 else if(room_type.equals("2")){
				 vo.setRoom_type("双床房");
			 }
			 else if(room_type.equals("3")){
				 vo.setRoom_type("三人间");
			 }
			 else if(room_type.equals("4")){
				 vo.setRoom_type("家庭房");
			 }
			 else if(room_type.equals("5")){
				 vo.setRoom_type("套房");
			 }
		 }
		 if(StringUtil.isNotEmptyString(beginBookTime)){
			 vo.setBeginBookTimeDate(getWeekOfDate(beginBookTime));
		 }else{
			 calendar.setTime(date);
			 calendar.add(calendar.DATE,1);
			 beginBookTime=fmt.format(calendar.getTime());
			 vo.setBeginBookTimeDate(getWeekOfDate(beginBookTime));
		 }
		 if(StringUtil.isNotEmptyString(endBookTime)){
			 vo.setEndBookTimeDate(getWeekOfDate(endBookTime));
		 }else{
			 calendar.setTime(date);
			 calendar.add(calendar.DATE,2);
			 endBookTime=fmt.format(calendar.getTime());
			 vo.setEndBookTimeDate(getWeekOfDate(endBookTime));
		 }
		 
		 //seo星级
		 if(StringUtil.isNotEmptyString(hotelStar)){
			 vo.setHotelStar(hotelStar);
		 }else if(StringUtil.isNotEmptyString(this.hotel_star)){
			 vo.setHotelStar(this.hotel_star);
		 }else{
			 vo.setHotelStar("");
		 }
		 
		 //seo品牌
		 if(StringUtil.isNotEmptyString(hotel_brand)){
			 vo.setHotel_brand(hotel_brand);
		 }else if(StringUtil.isNotEmptyString(this.hotelBrand)){
			 vo.setHotel_brand(this.hotelBrand);
		 }else{
			 vo.setHotel_brand("");
		 }
		 //seo标地
		 if(StringUtil.isNotEmptyString(hotel_longitude)){
			 vo.setLongitude(hotel_longitude);
		 }else{
			 vo.setLongitude(longitude);
		 }
		 if(StringUtil.isNotEmptyString(hotel_latitude)){
			 vo.setLatitude(hotel_latitude);
		 }else{
			 vo.setLatitude(latitude);
		 }
		 if(StringUtil.isNotEmptyString(parentID)){
			 vo.setParentId(parentID);
		 }else{
			 vo.setParentId(parentId);
		 }
		 
		 vo.setQueryTimes(this.dealwithTimes(beginBookTime, endBookTime));
		 vo.setSearchId(searchId);
		 vo.setSearchType(searchType);
		 vo.setHotelprice(hotelprice);
		 vo.setBeginBookTime(beginBookTime);
		 vo.setEndBookTime(endBookTime);
		 vo.setFacilities(facilities);
		 vo.setRoom_type(room_type);
		 vo.setRanktype(ranktype);
		 vo.setIssale(issale);
		 vo.setActiveFlag(activeFlag);
		 if(StringUtil.isNotEmptyString(currentPage))
		 vo.setPage(Integer.parseInt(currentPage));
		 
		 return vo;
		
	}
	private static String dealwithTimes(String beginBookTime,String endBookTime) {
		   Date date1=null;
		   Date date2=null;
		   String querytimes="";
		try {
			date1=fmt.parse(beginBookTime);
			date2=fmt.parse(endBookTime);
			date=fmt.parse(fmt.format(date));
			
		} catch (ParseException e) {
			date1=new Date();
			date2=new Date();
			e.printStackTrace();
		}
		calendar.setTime(date);
		long time0=calendar.getTimeInMillis();
		calendar.setTime(date1);
		long time1=calendar.getTimeInMillis();
		calendar.setTime(date2);
		long time2=calendar.getTimeInMillis();
		long between_days=(time2-time1)/(1000*3600*24);  
		long firstDay=(time1-time0)/(1000*3600*24);  
		int days=Integer.parseInt(String.valueOf(between_days));
		int first=Integer.parseInt(String.valueOf(firstDay)); 
		for(int i=first;i<=days+first;i++){
			querytimes=i+","+querytimes;
		}
		
		return querytimes;
	}
	
	public static String getWeekOfDate(String date1) { 
		  Date date=null;;
		try {
			date = fmt.parse(date1);
		} catch (ParseException e) {
			date=new Date();
		}
		  String[] weekDaysName = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" }; 
		  String[] weekDaysCode = { "0", "1", "2", "3", "4", "5", "6" }; 
		  Calendar calendar = Calendar.getInstance(); 
		  calendar.setTime(date); 
		  int intWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; 
		  return weekDaysName[intWeek]; 
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
					if(fieldName.equals("landMarkName")){
						String landMark_val = val.toString();
						if(StringUtil.isNotEmptyString(landMark_val)){
							str = str.replaceAll("\\{"+fieldName+"\\}",landMark_val+"附近");
						}
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
	private List<ProductHistoryCookie> getHistoryCookie(HttpServletRequest req, HttpServletResponse Resp){
		Cookie cookie = ServletUtil.getCookie(req, PLATFORM_HOTEL_PRODUCTID);
		List<ProductHistoryCookie> historyCookieList = new ArrayList<ProductHistoryCookie>();
		try {
			//查询cookie中的己有浏览记录
			if(cookie != null){
				JSONArray cookieList = JSONArray.fromObject(URLDecoder.decode(cookie.getValue(),"UTF-8"));
				for (int i = 0; i < cookieList.size(); i++) {
					historyCookieList.add((ProductHistoryCookie)JSONObject.toBean(cookieList.getJSONObject(i),ProductHistoryCookie.class));
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

	
	private void setKeyWordHistrory() throws UnsupportedEncodingException{
		// 获取cookieKey
		String[] cookieKey = null;
		String searchHistrory ="";
		
		Cookie cookie = ServletUtil.getCookie(getRequest(), "HOLIDAY_HOTEL_KEY");
		keyWordHistrory = new ArrayList<String>();
		if (cookie == null) {
			searchHistrory = verHotelSearchVO.getKeyword();
			keyWordHistrory.add(searchHistrory);
		}else{
			keyWordHistrory.add(verHotelSearchVO.getKeyword());
			searchHistrory = URLDecoder.decode(cookie.getValue(), "UTF-8");;
			cookieKey = searchHistrory.split(",");
			for(String s : cookieKey){
				if(!verHotelSearchVO.getKeyword().equals(s)){
					keyWordHistrory.add(s);
				}
			}
			int size = keyWordHistrory.size()>10 ? 10 : keyWordHistrory.size();
			searchHistrory = StringUtils.join(keyWordHistrory.subList(0, size),",");
		 } 
		
		ServletUtil.addCookie(getResponse(), "HOLIDAY_HOTEL_KEY", URLEncoder.encode(searchHistrory, "UTF-8"));
	}
	



	public VerHotelSearchVO getVerHotelSearchVO() {
		return verHotelSearchVO;
	}

	public void setVerHotelSearchVO(VerHotelSearchVO verHotelSearchVO) {
		this.verHotelSearchVO = verHotelSearchVO;
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


	public List<ProductHistoryCookie> getHotelCookie() {
		return hotelCookie;
	}

	public void setHotelCookie(List<ProductHistoryCookie> hotelCookie) {
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
	
	public String getLandMarkName() {
		return landMarkName;
	}
	public void setLandMarkName(String landMarkName) {
		this.landMarkName = landMarkName;
	}
	
	public String getPlaceId() {
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
}
