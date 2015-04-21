package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.search.SearchConstants;
import com.lvmama.comm.search.vo.ProductBean;

public class MobileSearchUtils {
	
	/**
	 * 初始化页面上的筛选条件——线路
	 * @param requset
	 * @param productlist
	 */
	public static void initFilterParam2Request_ROUTE_forClient(String keyword,Map<String,Object> result,List<ProductBean> productlist){
		List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
		
		LinkedHashMap<String, Integer> scenicPlaceMap = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playFeatures = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playLines = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> cities = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> subjects = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> visitDays = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> tempVisitDays = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> traffics = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> hotelTypes  = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> hotelLocations  = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playBrands = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> playNums = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> landTraffics = new LinkedHashMap<String, Integer>();
		LinkedHashMap<String, Integer> landFeatures = new LinkedHashMap<String, Integer>();
		if (productlist != null && productlist.size() > 0) {
			for (int i = 0; i < productlist.size(); i++) {
				MobileSearchUtils.collectParamList(subjects, productlist.get(i).getRouteTopic());
				MobileSearchUtils.collectParamList(traffics, productlist.get(i).getTraffic());
				MobileSearchUtils.collectParamList(playLines, productlist.get(i).getPlayLine());
				MobileSearchUtils.collectParamList(playFeatures, productlist.get(i).getPlayFeatures());
				MobileSearchUtils.collectParamList(hotelTypes, productlist.get(i).getHotelType());
				MobileSearchUtils.collectParamList(hotelLocations, productlist.get(i).getHotelLocation());
				MobileSearchUtils.collectParamList(playBrands, productlist.get(i).getPlayBrand());
				MobileSearchUtils.collectParamList(playNums, productlist.get(i).getPlayNum());
				MobileSearchUtils.collectParamList(scenicPlaceMap, productlist.get(i).getScenicPlace());
				MobileSearchUtils.collectParamList(visitDays, Long.toString(productlist.get(i).getVisitDay()));
				MobileSearchUtils.collectParamList(landTraffics, productlist.get(i).getLandTraffic());
				MobileSearchUtils.collectParamList(landFeatures, productlist.get(i).getLandFeature());

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
			subjects = MobileSearchUtils.sortMap(subjects);

			MobileSearchUtils.addToList(resultList,"主题","subject", subjects);
			
			traffics = MobileSearchUtils.sortMap(traffics);
	
			MobileSearchUtils.addToList(resultList,"交通","traffic", traffics);
		
			playLines = MobileSearchUtils.sortMap(playLines);
			
			MobileSearchUtils.addToList(resultList,"游玩路线","playLine",playLines);
			
			playFeatures = MobileSearchUtils.sortMap(playFeatures);
			MobileSearchUtils.addToList(resultList,"游玩特色","playFeature", playFeatures);
			
			hotelTypes = MobileSearchUtils.sortMap(hotelTypes);
			MobileSearchUtils.addToList(resultList,"酒店类型","hotelType", hotelTypes);
			
			hotelLocations = MobileSearchUtils.sortMap(hotelLocations);
			MobileSearchUtils.addToList(resultList,"酒店位置","hotelLocation", hotelLocations);
			
			playBrands = MobileSearchUtils.sortMap(playBrands);
			MobileSearchUtils.addToList(resultList,"特色品牌","playBrand", playBrands);
			
			playNums = MobileSearchUtils.sortMap(playNums);
			MobileSearchUtils.addToList(resultList,"游玩人数","playNum", playNums);
			
			scenicPlaceMap = MobileSearchUtils.sortMap(scenicPlaceMap);
			
			MobileSearchUtils.addToList(resultList,"景点","scenicPlace", scenicPlaceMap);
			
			landTraffics=MobileSearchUtils.sortMap(landTraffics);
			MobileSearchUtils.addToList(resultList,"上岛交通","landTraffic", landTraffics);
			
			landFeatures=MobileSearchUtils.sortMap(landFeatures);
			MobileSearchUtils.addToList(resultList,"岛屿特色","landFeature", landFeatures);
			/**visitday按 KEY排序**/
			visitDays =MobileSearchUtils.sortMapByKey(visitDays);
			Iterator<Entry<String, Integer>> it = visitDays.entrySet().iterator();
			while(it.hasNext()){
				Entry<String, Integer> entry = it.next();
				tempVisitDays.put(entry.getKey()+"天", entry.getValue());
			
			}
			
			MobileSearchUtils.addToList(resultList,"游玩天数","visitDay", tempVisitDays);
			
			//keyword同义词循环
			if(StringUtil.isNotEmptyString(keyword)) {
			String[] arr = keyword.split(",");
			for (String strkeyword : arr) {
				if(cities.get(strkeyword)!=null && cities.get(strkeyword) == productlist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
					cities.remove(strkeyword);
				}
			}
			}
//			if(cities.get(keyword)!=null && cities.get(keyword) == productlist.size()){//如果关键词和包含地区名称一致且数量一样不显示此包含地区
//				cities.remove(keyword);
//			}
			if( cities.size()>0 ){
				cities =  MobileSearchUtils.sortMap(cities);
				MobileSearchUtils.addToList(resultList,"城市","city", cities);
			}
		}
		result.put("filterDatas", resultList);
	}
	
	private static void addToList(List<Map<String,Object>> resultList,String name,String argName,Map<String, Integer> datas) {
		if(!datas.isEmpty()){
			resultList.add(MobileSearchUtils.addToMap(name,argName, datas));
		}
	}
	
	private static Map<String,Object> addToMap(String name,String argName,Map<String, Integer> datas){
		
		Iterator<Entry<String, Integer>> it = datas.entrySet().iterator();
		List<Map<String,Object>> listResult = new ArrayList<Map<String,Object>>();
		Map<String,Object> mapAll = new LinkedHashMap<String, Object>();
		mapAll.put("key", "全部");
		mapAll.put("value", "");
		mapAll.put("size", "");
		listResult.add(mapAll);
		while(it.hasNext()){
			Entry<String, Integer> entry = it.next();
			Map<String,Object> map = new LinkedHashMap<String, Object>();
			map.put("key", entry.getKey());
			map.put("value", entry.getKey());
			map.put("size", entry.getValue());
			listResult.add(map);
		}
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", name);
		map.put("argName", argName);
		map.put("datas", listResult);
		return map;
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
	
	
	
}
