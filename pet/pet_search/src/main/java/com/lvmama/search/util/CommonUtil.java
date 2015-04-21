package com.lvmama.search.util;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.SearchConstants.SEARCH_TYPE;
import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.VerHotelBean;
import com.lvmama.comm.search.vo.VerPlaceBean;
import com.lvmama.comm.search.vo.VerPlaceTypeVO;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.search.bigData.LogTask;
import com.lvmama.search.listener.SearchDataLoader;
import com.lvmama.search.lucene.score.LuceneCustomScoreQuery;
import com.lvmama.search.util.mail.SendMailLogTask;

public class CommonUtil {
	 Log loger = LogFactory.getLog(this.getClass());
	 static SimpleDateFormat fmt=new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 解码参数 把编码转换为汉字
	 * @param str 编码
	 * @return
	 */
	public static String transcodeParams(String str){
		Map<Long,String> transcodeMap = (Map<Long, String>) LocalCacheManager.get("COM_SEARCH_TRANSCODE_ID_KEYWORD");
		if(transcodeMap == null){
			SearchDataLoader.initTranscode();
		}
		if(CommonUtil.isNumeric(str)){
			String _str = transcodeMap.get(Long.parseLong(str));
			return _str == null ? str : _str;
		}else{
			return str; 
		}
	}
	/**
	 * 编码参数 把汉字转换为数字
	 * @param str 汉字
	 * @return
	 */
	public static String codeParams(String str){
		Map<String,Long> codeMap = (Map<String,Long>) LocalCacheManager.get("COM_SEARCH_TRANSCODE_KEYWORD_ID");
		if(codeMap == null){
			SearchDataLoader.initTranscode();
		}
		if(!StringUtil.isEmptyString(str) && !CommonUtil.isNumeric(str)){
			Long code = codeMap.get(str);
			return code == null ? str : code.toString();
		}else{
			return str;
		}
	}
	
	public static boolean isNumeric(String str){
		if(str == null){
			return false;
		}
	    Pattern pattern = Pattern.compile("[0-9]*"); 
	    return pattern.matcher(str).matches();    
	}
	
	public static String escapeString(String str){
		if (str == null) {
			return "";
		}
		return QueryParser.escape(str.trim());
	}
	/**
	 * 如果是默认的排序，则不要sort，不按照seq排序
	 * @param sorts
	 * @return
	 */
	public  static TopDocs toSearchIsHasSort(int max_search_size,
			IndexSearcher indexSearcher, Query customerQuery,
			Sort sort, SORT... sorts) throws IOException {
		TopDocs topDocs;
		if(sorts == null || sorts.length ==0 || sorts.length>1 ){
			 topDocs = indexSearcher.search(customerQuery, null,
						max_search_size);
		 }
		 else{
			 topDocs = indexSearcher.search(customerQuery, null,
						max_search_size,sort);
		 }
		return topDocs;
	}
	/**
	 * 获取hbase的数据
	 * @param sorts
	 * @return
	 */
	public static Map<String, String> getHbaseData(List<String> placeidList,
			String keyword) throws IOException {
		Map<String, String> returnMap=null;
		LogTask logtask=new LogTask(keyword,placeidList);
		//调用多线程来执行获取hbase数据方法
		ExecutorService exec = Executors.newSingleThreadExecutor();
		FutureTask<Map> futureTask = new FutureTask<Map>(logtask);
		exec.execute(futureTask);
		 try {
			//超过3秒就直接报错
			returnMap=futureTask.get(LuceneCommonDic.MAXCONNECTHBASETIME,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			returnMap=null;
			futureTask.cancel(true); 
		}
		 finally{
				exec.shutdown();
			}
		return returnMap;
	}
	
	/**
	 * 调用线程类去发送mail
	 * @param sorts
	 * @return
	 */
	public static void sendMail(String message) throws IOException {
		SendMailLogTask logtask=new SendMailLogTask(message);
		//调用多线程来执行获取hbase数据方法
		ExecutorService exec = Executors.newSingleThreadExecutor();
		FutureTask<Map> futureTask = new FutureTask<Map>(logtask);
		exec.execute(futureTask);
		 try {
			//超过3秒就直接报错
			futureTask.get(LuceneCommonDic.MAXCONNECTHBASETIME,TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			futureTask.cancel(true); 
			throw new RuntimeException(e);
		}
		 finally{
				exec.shutdown();
			}
	}
	/**
	 * 初始化排序字段
	 * @param sorts
	 * @return
	 */
	public static Sort initSortField(SORT... sorts){
		Sort sort = new Sort();
		SortField[] sortFields = null;
		if(sorts == null || sorts.length ==0 ){
			sortFields = new SortField[]{SORT.seq.getSortField()};
		}else{
			List<SortField> sortFieldList = new ArrayList<SortField>();
			for (int i = 0; i < sorts.length; i++) {
				SORT s = sorts[i];
				sortFieldList.add(sorts[i].getSortField());
				if(s.getType().equals("avgScore")){
					sortFieldList.add(SORT.cmtNumDown.getSortField());
				}else if(s.getType().equals("cmtNum")){
					sortFieldList.add(SORT.avgScoreDown.getSortField());
				}
			}
			sortFields = sortFieldList.toArray(new SortField[sortFieldList.size()]);
		}
		sort.setSort(sortFields);
		return sort;
	}
	
	/**
	 * 为二次搜索的结果中产品名称字段高亮显示加红
	 * 
	 */
	public static void productNameHighlight(PageConfig<ProductBean> pageConfig, String keyword) {
		if (pageConfig.getItems().size() > 0 && StringUtils.isNotEmpty(keyword.trim())) {
			List<ProductBean> productlist = pageConfig.getItems();
			for (int i = 0; i < productlist.size(); i++) {
				String productNameHighlightRed = highLightRedFont(keyword.trim(), productlist.get(i).getProductName());
				productlist.get(i).setProductName(productNameHighlightRed);
			}
		}
	}

	/**
	 * 为二次搜索的结果中产品名称字段高亮显示加红(酒店搜索)
	 * 
	 */
	public static void hotelNameHighlight(PageConfig<PlaceHotelBean> pageConfig, String keyword) {
		if (pageConfig.getItems().size() > 0 && StringUtils.isNotEmpty(keyword.trim())) {
			List<PlaceHotelBean> productlist = pageConfig.getItems();
			for (int i = 0; i < productlist.size(); i++) {
				String hotelNameHighlightRed = highLightRedFont(keyword.trim(), productlist.get(i).getName());
				productlist.get(i).setName(hotelNameHighlightRed);
				
				String enNameHighlightRed = highLightRedFont(keyword.trim(), productlist.get(i).getEnName());
				productlist.get(i).setEnName(enNameHighlightRed);
				
				String recommentContentHighlightRed  = highLightRedFont(keyword.trim(), productlist.get(i).getRecommendContent());
				productlist.get(i).setRecommendContent(recommentContentHighlightRed);
			}
		}
	}
	
	/**
	 * 给一段文字中全匹配关键字的部分在浏览器中显示为红色,无匹配则文字不变
	 */
	public static String highLightRedFont(String keyword, String Str) {
		String StrRed = "";
		if (StringUtils.isNotEmpty(Str) && StringUtils.isNotEmpty(keyword) && Str.indexOf(keyword) >= 0) {
			StrRed = Str.replace(keyword, "<font color=red>" + keyword + "</font>");
		} else {
			StrRed = Str;
		}
		return StrRed;
	}
	/**
	 * 初始化页面上的筛选条件——线路
	 * @param requset
	 * @param productlist
	 */
	private static void initFilterParam2Request_ROUTE(String keyword,HttpServletRequest requset,List<ProductBean> productlist){
		LinkedHashMap<String, Integer> scenicPlaceMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playFeatures = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playLines = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> subjects = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> visitDays = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> traffics = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> hotelTypes  = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> hotelLocations  = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playBrands = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playNums = new LinkedHashMap<String, Integer>();
	    //上岛交通
		LinkedHashMap<String, Integer> landTraffics = new LinkedHashMap<String, Integer>();
		//上岛特色
		LinkedHashMap<String, Integer> landFeatures = new LinkedHashMap<String, Integer>();
		if (productlist != null && productlist.size() > 0) {
			for (int i = 0; i < productlist.size(); i++) {
 				CommonUtil.collectParamList(subjects, productlist.get(i).getRouteTopic());
				CommonUtil.collectParamList(traffics, productlist.get(i).getTraffic());
				CommonUtil.collectParamList(playLines, productlist.get(i).getPlayLine());
				CommonUtil.collectParamList(playFeatures, productlist.get(i).getPlayFeatures());
				CommonUtil.collectParamList(hotelTypes, productlist.get(i).getHotelType());
				CommonUtil.collectParamList(hotelLocations, productlist.get(i).getHotelLocation());
				CommonUtil.collectParamList(playBrands, productlist.get(i).getPlayBrand());
				CommonUtil.collectParamList(playNums, productlist.get(i).getPlayNum());
				CommonUtil.collectParamList(scenicPlaceMap, productlist.get(i).getScenicPlace());
				CommonUtil.collectParamList(visitDays, Long.toString(productlist.get(i).getVisitDay()));
				CommonUtil.collectParamList(landTraffics, productlist.get(i).getLandTraffic());
				CommonUtil.collectParamList(landFeatures, productlist.get(i).getLandFeature());

				//set cities
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
			}
			//排序
			subjects = CommonUtil.sortMap(subjects);
			requset.setAttribute("subjects", subjects);
			traffics = CommonUtil.sortMap(traffics);
			requset.setAttribute("traffics", traffics);
			playLines = CommonUtil.sortMap(playLines);
			requset.setAttribute("playLines", playLines);
			playFeatures = CommonUtil.sortMap(playFeatures);
			requset.setAttribute("playFeatures", playFeatures);
			hotelTypes = CommonUtil.sortMap(hotelTypes);
			requset.setAttribute("hotelTypes", hotelTypes);
			hotelLocations = CommonUtil.sortMap(hotelLocations);
			requset.setAttribute("hotelLocations", hotelLocations);
			playBrands = CommonUtil.sortMap(playBrands);
			requset.setAttribute("playBrands", playBrands);
			playNums = CommonUtil.sortMap(playNums);
			requset.setAttribute("playNums", playNums);
			scenicPlaceMap = CommonUtil.sortMap(scenicPlaceMap);
			requset.setAttribute("scenicPlaceMap", scenicPlaceMap);
			landTraffics=CommonUtil.sortMap(landTraffics);
			requset.setAttribute("landTraffics", landTraffics);
			landFeatures=CommonUtil.sortMap(landFeatures);
			requset.setAttribute("landFeatures", landFeatures);
			/**visitday按 KEY排序**/
			visitDays =CommonUtil.sortMapByKey(visitDays);
			requset.setAttribute("visitDays", visitDays);
			
			//keyword同义词循环
			String[] arr = keyword.split(",");
			for (String strkeyword : arr) {
				if(cities.get(strkeyword)!=null && cities.get(strkeyword) == productlist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
					cities.remove(strkeyword);
				}
			}
//			if(cities.get(keyword)!=null && cities.get(keyword) == productlist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
//				cities.remove(keyword);
//			}
			if( cities.size()>0 ){
				cities =  CommonUtil.sortMap(cities);
				requset.setAttribute("cities", cities);
			}
		}
	}
	
	public static Map<String,LinkedHashMap<String, Integer>> initFilterParam_HOTEL(String keyword, List<PlaceHotelBean> placeHotellist,String type,boolean without_city){
		Map<String,LinkedHashMap<String, Integer>> map = new HashMap<String,LinkedHashMap<String, Integer>> ();
		/**页面地区筛选条件*/
		LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
		/**页面酒店主题筛选条件*/
		LinkedHashMap<String, Integer> allHotelTopics = new LinkedHashMap<String, Integer>();
		/**页面产品主题筛选条件*/
		LinkedHashMap<String, Integer> allProdTopics = new LinkedHashMap<String, Integer>();
		/**页面产品星级筛选条件*/
		LinkedHashMap<String, Integer> allStars = new LinkedHashMap<String, Integer>();
		
		// 页面地区先处理逗号分割
		for (PlaceHotelBean pb : placeHotellist) {
			if( ("ALL".equals(type) && !without_city ) || "CITY".equals(type) ){
				String cityArray = pb.getCity();
				if (cityArray != null && !"".equals(cityArray)) {
					StringTokenizer cityTokenizer = new StringTokenizer(cityArray, ",");
					while (cityTokenizer.hasMoreTokens()) {
						String cityCut = cityTokenizer.nextToken();
						if (!cityCut.matches("[0-9]+")) {// CITY和CITYID共用，去掉CITYID
							if (cities.get(cityCut) == null) {
								cities.put(cityCut, 1);
							} else {
								Integer sum = cities.get(cityCut) + 1;
								cities.put(cityCut, sum);
							}
						}
					}
				}
			}
			if("ALL".equals(type) || "HOTEL_TOPIC".equals(type)){
				// 处理酒店主题 (逗号隔开)
				String hotelTopic = pb.getHotelTopic();
				if (hotelTopic != null && !"".equals(hotelTopic)) {
					StringTokenizer st = new StringTokenizer(hotelTopic, ",");
					while (st.hasMoreTokens()) {
						String hotelTopic1 = st.nextToken();
						if (allHotelTopics.get(hotelTopic1) == null) {
							allHotelTopics.put(hotelTopic1, 1);
						} else {
							Integer sum = allHotelTopics.get(hotelTopic1) + 1;
							allHotelTopics.put(hotelTopic1, sum);
						}
					}
				}
			}
			if("ALL".equals(type)|| "PROD_TOPIC".equals(type)){
				// 处理产品主题 (逗号隔开)
				String prodTopics = pb.getProdTopic();
				if (StringUtils.isNotBlank(prodTopics)) {
					StringTokenizer st = new StringTokenizer(prodTopics, ",");
					while (st.hasMoreTokens()) {
						String topic = st.nextToken();
						if (allProdTopics.get(topic) == null) {
							allProdTopics.put(topic, 1);
						} else {
							Integer sum = allProdTopics.get(topic) + 1;
							allProdTopics.put(topic, sum);
						}
					}
				}
			}
			if("ALL".equals(type)|| "STAR".equals(type)){
				// 处理星级
				String hotelStar = pb.getHotelStarMerge();
				if (StringUtils.isNotBlank(hotelStar)) {
					hotelStar = hotelStar.trim();
					if (allStars.get(hotelStar) == null) {
						allStars.put(hotelStar, 1);
					} else {
						Integer sum = allStars.get(hotelStar) + 1;
						allStars.put(hotelStar, sum);
					}
				}
			}
		}
		if (cities.get(keyword) != null && cities.get(keyword) == placeHotellist.size()) {// 如果关键词和包含地区名称一致且数量一样不显示此包含地区
			cities.remove(keyword);
		}
		map.put("cities", CommonUtil.sortMap(cities));
		map.put("allHotelTopics", CommonUtil.sortMap(allHotelTopics));
		map.put("allProdTopics", CommonUtil.sortMap(allProdTopics));
		map.put("allStars",  CommonUtil.sortMapByKeyDesc(allStars));
		return map;
	}
	
	/**
	 * 初始化页面上的筛选条件——门票
	 * @param requset
	 * @param placelist
	 */
	private static void initFilterParam2Request_TICKET(String keyword, HttpServletRequest requset,List<PlaceBean> placelist){
		/**页面地区筛选条件*/
		LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
		/**页面主题筛选条件*/
		LinkedHashMap<String, Integer> subjects = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> placeActivities = new LinkedHashMap<String, Integer>();
		
			for (PlaceBean pb : placelist) {
				String cityArray = pb.getCity();
				if (cityArray != null && !"".equals(cityArray)) {
					StringTokenizer cityTokenizer = new StringTokenizer(cityArray, ",");
					while (cityTokenizer.hasMoreTokens()) {
						String cityCut = cityTokenizer.nextToken();
						if (!cityCut.matches("[0-9]+")) {//CITY和CITYID共用，去掉CITYID
							if (cities.get(cityCut)==null) {
								cities.put(cityCut, 1);
							}else{
								Integer sum = cities.get(cityCut)+1;
								cities.put(cityCut, sum);
							}
						}
					}
				}
				String DestSubject = pb.getDestSubjects();
				if (DestSubject != null && !"".equals(DestSubject)) {
					StringTokenizer st = new StringTokenizer(DestSubject, ",");
					while (st.hasMoreTokens()) {
						String subject1 = st.nextToken();
						if (subjects.get(subject1)==null) {
							subjects.put(subject1, 1);
						} else {
							Integer sum = subjects.get(subject1)+1;
							subjects.put(subject1, sum);
						}
					}
				}
				String placeActivity = pb.getPlaceActivity();
				placeActivity = placeActivity.trim();
				if(StringUtils.isNotBlank(placeActivity)) {
					if (placeActivities.get(placeActivity)==null) {
						placeActivities.put(placeActivity, 1);
					}else{
						Integer sum = placeActivities.get(placeActivity)+1;
						placeActivities.put(placeActivity, sum);
					}
				}
			}
			
			subjects = CommonUtil.sortMap(subjects);
			requset.setAttribute("subjects", subjects);
			//keyword同义词循环
			String[] arr = keyword.split(",");
			for (String strkeyword : arr) {
				if(cities.get(strkeyword)!=null && cities.get(strkeyword) == placelist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
					cities.remove(strkeyword);
				}
			}
//			if(cities.get(keyword)!=null && cities.get(strkeyword) == placelist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
//				cities.remove(keyword);
//			}
			if( cities.size()>0 ){
				cities =  CommonUtil.sortMap(cities);
				requset.setAttribute("cities", cities);
			}
			placeActivities = CommonUtil.sortMap(placeActivities);
			requset.setAttribute("placeActivities", placeActivities);
	}
	
	/**
	 * 初始化页面上的筛选条件——verHotel
	 * @param requset
	 * @param placelist
	 */
	
	
	public static void initFilterParam2Request_VERHOTEL(String keyword, HttpServletRequest requset,List<VerHotelBean> verhotellist){
		/**品牌筛选条件*/
		LinkedHashMap<String, VerHotelBean> brands = new LinkedHashMap<String, VerHotelBean>();
		/**设施筛选条件*/
		LinkedHashMap<String, String> facilities = new LinkedHashMap<String, String>();
		for (VerHotelBean pb : verhotellist) {

			String hotelbrand = pb.getHotelbrand();
			if (StringUtils.isNotBlank(hotelbrand)) {
				String [] tmp1=hotelbrand.split("（");
				hotelbrand=tmp1[0];
				hotelbrand = hotelbrand.trim();
				hotelbrand = hotelbrand.replaceAll("[\\pP|~|$|^|<|>|\\||\\+|=]*", "");
				hotelbrand = hotelbrand.replaceAll("[a-zA-Z]+", "");  
		
				if (brands.get(hotelbrand) == null) {
					pb.setHotelbrand(pb.getHotelbrandid());
					pb.setScore(1F);
					brands.put(hotelbrand, pb);
					
				}else{
					pb= brands.get(hotelbrand);
					float sum = pb.getScore()+1;
					pb.setScore(sum);
					brands.put(hotelbrand, pb);
				}
			}
			String facility = pb.getFacilities_name();
			String facilityId = pb.getFacilities();
			if (StringUtils.isNotBlank(facility) && StringUtils.isNotBlank(facilityId)) {
				facilityId=facilityId.substring(1, facilityId.length());
				String [] tmp1=facility.split(",");
				String [] tmp2=facilityId.split(",");
				for(int i=0;i<tmp1.length;i++){
					String tmpfacility=tmp1[i];
					String tmpfacilityId=tmp2[i];
					if(StringUtils.isNotBlank(tmpfacility) && StringUtils.isNotBlank(tmpfacilityId) && !tmpfacility.contains("收费")){
						
						if (facilities.get(tmpfacility) == null) {
							facilities.put(tmpfacility, tmpfacilityId);
						} 
					}
				}
			}
		}	
		brands = CommonUtil.sortMapVer(brands);
		requset.setAttribute("hotelbrands", brands);
		requset.setAttribute("facilities", facilities);

	}
	
	/**
	 * 初始化页面上的筛选条件——verHotelOther
	 * @param requset
	 * @param placelist
	 */
	
	
	public static void initFilterParam2Request_VERHOTELOTHER(String keyword, HttpServletRequest requset,List<VerPlaceBean> verplacelist,List<VerPlaceTypeVO> catageoryList){
		
		List<LinkedHashMap<String, VerPlaceBean>> tmplist1=new ArrayList<LinkedHashMap<String, VerPlaceBean>>();
		HashMap<Integer, String> tmpMap1=new HashMap<Integer, String>();
		for(int i=0;i<catageoryList.size();i++){
			VerPlaceTypeVO vo=catageoryList.get(i);
			vo.setIsHasTree(null);
			String placeType=vo.getDictId();
			LinkedHashMap<String, VerPlaceBean> temp1s = new LinkedHashMap<String, VerPlaceBean>();
			for (VerPlaceBean pb : verplacelist) {
				if(StringUtil.isNotEmptyString(pb.getPlaceType())){
					if(pb.getPlaceType().equals(placeType)){
						String buseinessArea=pb.getPlaceName();
						if(StringUtil.isNotEmptyString(buseinessArea)){
							if (temp1s.get(buseinessArea) == null) {
								pb.setScore(1F);
								temp1s.put(buseinessArea, pb);
								vo.setIsHasTree("1");
							} else {
								pb= temp1s.get(buseinessArea);
								float sum = pb.getScore()+1;
								pb.setScore(sum);
								temp1s.put(buseinessArea, pb);
							}
						}
					}
				}
			}
			tmplist1.add(temp1s);
			tmpMap1.put(i, placeType);
		}
		requset.setAttribute("catageoryList", catageoryList);
		requset.setAttribute("catageoryContentList", tmplist1);
		
//		/**商业区*/
//		LinkedHashMap<String, VerPlaceBean> businessAreas = new LinkedHashMap<String, VerPlaceBean>();
//		/**地标*/
//		LinkedHashMap<String, VerPlaceBean> landMarks = new LinkedHashMap<String, VerPlaceBean>();
//		
//		for (VerPlaceBean pb : verplacelist) {
//			if(StringUtil.isNotEmptyString(pb.getPlaceType())){
//				if(pb.getPlaceType().equals("BUSSINESS")){
//					String buseinessArea=pb.getPlaceName();
//					if(StringUtil.isNotEmptyString(buseinessArea)){
//						if (businessAreas.get(buseinessArea) == null) {
//							pb.setScore(1);
//							businessAreas.put(buseinessArea, pb);
//						} else {
//							pb= businessAreas.get(buseinessArea);
//							float sum = pb.getScore()+1;
//							pb.setScore(sum);
//							businessAreas.put(buseinessArea, pb);
//						}
//					}
//				}else if(pb.getPlaceType().equals("LANDMARK")){
//					String landmark=pb.getPlaceName();
//					if(StringUtil.isNotEmptyString(landmark)){
//						if (landMarks.get(landmark) == null) {
//							pb.setScore(1);
//							landMarks.put(landmark, pb);
//						} else {
//							
//							pb= landMarks.get(landmark);
//							float sum = pb.getScore()+1;
//							pb.setScore(sum);
//							landMarks.put(landmark, pb);
//							
//						}
//					}
//				}
//			}
//		}
//		businessAreas = CommonUtil.sortMapVerPlace(businessAreas);
//		landMarks = CommonUtil.sortMapVerPlace(landMarks);
//		requset.setAttribute("businessAreas", businessAreas);
//		requset.setAttribute("landMarks", landMarks);

	}
	
	/**
	 * 根据查询结果动态的初始化筛选条件
	 * @param keyword 关键词
	 * @param searchType 查询类型
	 * @param requset 
	 * @param pageConfig 查询结果
	 */
	public static void initFilterParam2Request(String keyword,String searchType,HttpServletRequest requset,List items){
		if(searchType.equalsIgnoreCase(SEARCH_TYPE.ROUTE.getCode())
				|| searchType.equalsIgnoreCase(SEARCH_TYPE.FREELONG.getCode())
				|| searchType.equalsIgnoreCase(SEARCH_TYPE.FREETOUR.getCode())
				|| searchType.equalsIgnoreCase(SEARCH_TYPE.AROUND.getCode())
				|| searchType.equalsIgnoreCase(SEARCH_TYPE.GROUP.getCode())){
			initFilterParam2Request_ROUTE(keyword,requset,items);
		}else if(searchType.equalsIgnoreCase(SEARCH_TYPE.TICKET.getCode())){
			initFilterParam2Request_TICKET(keyword,requset,items);
		}
//		else if(searchType.equalsIgnoreCase(SEARCH_TYPE.getCode())){
//			
//		}
	}
	
	/** 
	* 把map按value排序，降序 
	* **/ 
	public static LinkedHashMap<String, VerPlaceBean> sortMapVerPlace(Map<String, VerPlaceBean> map) {
		List<Map.Entry<String, VerPlaceBean>> mapList = new ArrayList<Map.Entry<String, VerPlaceBean>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, VerPlaceBean>>() {
			public int compare(Map.Entry<String, VerPlaceBean> o1, Map.Entry<String, VerPlaceBean> o2) {
				VerPlaceBean a2=o2.getValue();
				VerPlaceBean a1=o1.getValue();
				return (int)a2.getScore().floatValue() - (int)a1.getScore().floatValue();
			}
		});
		LinkedHashMap<String, VerPlaceBean> sortMap = new LinkedHashMap<String, VerPlaceBean>();
		for (Map.Entry<String, VerPlaceBean> mapping : mapList) {
			sortMap.put(mapping.getKey(), mapping.getValue());
		}
		return sortMap;
	}
	
	/** 
	* 把map按value排序，降序 
	* **/ 
	public static LinkedHashMap<String, VerHotelBean> sortMapVer(Map<String, VerHotelBean> map) {
		List<Map.Entry<String, VerHotelBean>> mapList = new ArrayList<Map.Entry<String, VerHotelBean>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, VerHotelBean>>() {
			public int compare(Map.Entry<String, VerHotelBean> o1, Map.Entry<String, VerHotelBean> o2) {
				VerHotelBean a2=o2.getValue();
				VerHotelBean a1=o1.getValue();
				return (int)a2.getScore().floatValue() - (int)a1.getScore().floatValue();
			}
		});
		LinkedHashMap<String, VerHotelBean> sortMap = new LinkedHashMap<String, VerHotelBean>();
		for (Map.Entry<String, VerHotelBean> mapping : mapList) {
			sortMap.put(mapping.getKey(), mapping.getValue());
		}
		return sortMap;
	}
	
	/** 
	* 把map按value排序，降序 
	* **/ 
	public static LinkedHashMap<String, Integer> sortMap(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return o2.getValue() - o1.getValue();
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> mapping : mapList) {
			sortMap.put(mapping.getKey(), mapping.getValue());
		}
		return sortMap;
	}
	
	/** 
	* 把map按Key排序，升序
	* **/ 
	public static LinkedHashMap<String, Integer> sortMapByKey(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return Integer.parseInt(o1.getKey()) - Integer.parseInt(o2.getKey());
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> mapping : mapList) {
			sortMap.put(mapping.getKey(), mapping.getValue());
		}
		return sortMap;
	}
	/** 
	* 把map按Key排序，降序
	* **/ 
	public static LinkedHashMap<String, Integer> sortMapByKeyDesc(Map<String, Integer> map) {
		List<Map.Entry<String, Integer>> mapList = new ArrayList<Map.Entry<String, Integer>>(map.entrySet());
		Collections.sort(mapList, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return  o2.getKey().compareTo(o1.getKey());
			}
		});
		LinkedHashMap<String, Integer> sortMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> mapping : mapList) {
			sortMap.put(mapping.getKey(), mapping.getValue());
		}
		return sortMap;
	}
	
	/**
	 * 解析param（格式：str,str,str,str）并放入map中
	 * 
	 * @param map
	 * @param param
	 */
	public static void collectParamList(Map<String,Integer> map, String param) {
		if (StringUtils.isNotBlank(param)) {
			String[] paramArray = param.split(SearchConstants.DELIM);
			List<String> distinctCheck = new ArrayList<String>();
			if (paramArray != null) {
				for (String paramValue : paramArray) {
					paramValue = paramValue.trim();
					if(!distinctCheck.contains(paramValue)){
						if(StringUtils.isNotBlank(paramValue)) {
							if (map.get(paramValue)==null) {
								map.put(paramValue, 1);
							}else{
								Integer sum = map.get(paramValue)+1;
								map.put(paramValue, sum);
							}
						}
						distinctCheck.add(paramValue);
					}
				}
			}
		}
	}
	 /** 
     * 拼接某属性的 get方法 
     * @param fieldName 
     * @return String 
     */  
    public static String parGetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        return "get" + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
    }  
  
    /** 
     * 拼接在某属性的 set方法 
     * @param fieldName 
     * @return String 
     */  
    public static String parSetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        return "set" + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
    }
    public static void printTest(String doc) {
		Date date=new Date();
		String indexPath = ConfigHelper.getString("LOGOUT_PATH");
		String fileName=indexPath+fmt.format(date)+".txt";
		formatFlie(fileName,doc);
		
	}
	/** 
     * 正则过滤字母
     * @param fieldName 
     * @return String 
     */  
	public   static   String StringFilter(String   str)   throws   PatternSyntaxException   {        
	    String regEx="[a-zA-Z]";     
	    Pattern   p   =   Pattern.compile(regEx);        
	    Matcher   m   =   p.matcher(str);        
	    return   m.replaceAll("").trim();        
    } 
	
	/**
	 * 促销产品
	 * @param productId
	 * @param promValid
	 * @param promEndDate
	 * @return Boolean
	 * @throws ParseException
	 */
	public static boolean promotion(String productId,String promEndDate) throws ParseException
	{
		
		if(promEndDate != null || !"".equals(promEndDate))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Long currentTime = new Date().getTime();
			Long promEndTime = (sdf.parse(promEndDate)).getTime();
			
			if(promEndTime - currentTime >= 0)
			{
				return Boolean.TRUE; 
			}
		}
		
		return Boolean.FALSE;
	}
	
	public static void formatFlie(String fileName, String context) { 
		File f = new File(fileName); 
		if (!f.exists()) { 
		try { 
		f.createNewFile(); 
		} catch (IOException e) { 
		// TODO Auto-generated catch block 
		e.printStackTrace(); 
		} 
		} 
		BufferedWriter output = null; 
		try { 
		output = new BufferedWriter(new OutputStreamWriter( 
		new FileOutputStream(fileName, true))); 
		output.write(context); 
		} catch (Exception e) { 
		e.printStackTrace(); 
		} finally { 
		try { 
		output.close(); 
		} catch (IOException e) { 
		e.printStackTrace(); 
		} 
		} 
		} 

		public static void main(String[] args) { 
		formatFlie("D:\\aaa.txt", "fenshu:10000\n"); 
		formatFlie("D:\\aaa.txt", "fenshu:10001\n"); 
		formatFlie("D:\\aaa.txt", "fenshu:10002\n"); 
		} 

}
