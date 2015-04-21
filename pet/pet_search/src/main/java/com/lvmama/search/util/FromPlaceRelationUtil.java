package com.lvmama.search.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class FromPlaceRelationUtil implements Serializable {
	private static final long serialVersionUID = 8114369843479470479L;
	private static Map<String, String> fromPlaceMap;
	
	private static void getInstance(){
		if(fromPlaceMap == null){
			synchronized (FromPlaceRelationUtil.class) {
				if(fromPlaceMap == null){
					fromPlaceMap = new HashMap<String, String>();
					fromPlaceMap.put("杭州", "上海");
					fromPlaceMap.put("南京", "上海");
					fromPlaceMap.put("深圳", "广州");
					fromPlaceMap.put("香港", "广州");
					fromPlaceMap.put("重庆", "成都");
				}
			}
		}
	}
	
	public static String getRelationFrom(String fromPlaceName) {
		getInstance();
		if(fromPlaceMap!=null) {
			return fromPlaceMap.get(fromPlaceName);
		}
		return null;
	}
}