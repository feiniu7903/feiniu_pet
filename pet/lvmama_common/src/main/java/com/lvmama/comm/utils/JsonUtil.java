package com.lvmama.comm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonUtil {

	private static final Pattern P_QUOTE_AFTER = Pattern.compile("&quot;");
	private static final Pattern P_QUOTE_ORGINAL = Pattern.compile("\"");

	private static String regexReplace(final Pattern regex_pattern, final String replacement,
			final String s) {
		Matcher m = regex_pattern.matcher(s);
		return m.replaceAll(replacement);
	}
	
	public static String convertQuoteBack(final String s){
		return regexReplace(P_QUOTE_AFTER, "\"", s);
	}

	public static void main(String args[]) {
		String s = "[{\"bonus\":20,\"coupon\":0,\"marketPrice\":1000,\"msg\":\"\",\"orderQuantity\":10,\"oughtPay\":854,\"oughtPayFen\":85400,\"price\":854,\"success\":true}]";		
		
		String s2 = regexReplace(P_QUOTE_ORGINAL, "&quot;", s);

		String s3 = regexReplace(P_QUOTE_AFTER, "\"", s2);

		System.out.println(s);
		System.out.println(s2);
		System.out.println(s3);
	}
	
	/** 
	  * 从一个JSON 对象字符格式中得到一个java对象
	  * @param jsonString
	  * @param pojoCalss
	  * @param childMap 子类，比如List中类的class
	  * @return
	  */
	@SuppressWarnings("rawtypes")
	public static Object getObject4JsonString(String jsonString, Class pojoCalss, Map<String, Class> childMap) {
		Object pojo;
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		if(childMap == null || childMap.isEmpty())
			pojo = JSONObject.toBean(jsonObject, pojoCalss);
		else
			pojo = JSONObject.toBean(jsonObject, pojoCalss, childMap);
		return pojo;
	}
	
	/**
	 * 从json HASH表达式中获取一个map，改map支持嵌套功能
	 * @param jsonString
	 * @return
	 */
   @SuppressWarnings({ "rawtypes", "unchecked" })
	public static Map getMap4Json(String jsonString){
       JSONObject jsonObject = JSONObject.fromObject( jsonString );  
       Iterator  keyIter = jsonObject.keys();
       String key;
       Object value;
       Map valueMap = new HashMap();
       while( keyIter.hasNext())
       {
       	key = (String)keyIter.next();
       	value = jsonObject.get(key);
           valueMap.put(key, value);
       }
       return valueMap;
	}
   
   /**
    * 从json数组中得到相应java数组
    * @param jsonString
    * @return
    */
   public static Object[] getObjectArray4Json(String jsonString){
   	JSONArray jsonArray = JSONArray.fromObject(jsonString);
   	return jsonArray.toArray();
   }

   /**
    * 从json对象集合表达式中得到一个java对象列表
    * @param jsonString
    * @param pojoClass
    * @return
    */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List getList4Json(String jsonString, Class pojoClass, Map<String, Class> childMap) {
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		JSONObject jsonObject;
		Object pojoValue;
		List list = new ArrayList();
		for (int i = 0; i < jsonArray.size(); i++) {
			jsonObject = jsonArray.getJSONObject(i);
			if(childMap == null || childMap.isEmpty())
				pojoValue = JSONObject.toBean(jsonObject, pojoClass);
			else
				pojoValue = JSONObject.toBean(jsonObject, pojoClass, childMap);
			list.add(pojoValue);
		}
		return list;
	}
	
	/**
	 * 从json数组中解析出java字符串数组
	 * @param jsonString
	 * @return
	 */
	public static String[] getStringArray4Json(String jsonString){
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		String[] stringArray = new String[jsonArray.size()];
		for( int i = 0 ; i<jsonArray.size() ; i++ ){
			stringArray[i] = jsonArray.getString(i);
		}
		return stringArray;
	}
	
	/**
	 * 从json数组中解析出javaLong型对象数组
	 * @param jsonString
	 * @return
	 */
	public static Long[] getLongArray4Json(String jsonString){
		JSONArray jsonArray = JSONArray.fromObject(jsonString);
		Long[] longArray = new Long[jsonArray.size()];
		for( int i = 0 ; i<jsonArray.size() ; i++ ){
			longArray[i] = jsonArray.getLong(i);
		}
		return longArray;
	}
	
	/**
	 * 将java对象转换成json字符串
	 * @param javaObj
	 * @return
	 */
	public static String getJsonString4JavaPOJO(Object javaObj){
		JSONObject json;
		json = JSONObject.fromObject(javaObj);
		return json.toString();
	}
	
	 /** 
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * 
     * @param list 
     *             列表对象 
     * @return java.lang.String 
     */    
   public static String getJsonString4List(List<?> list) {     
        StringBuilder json = new StringBuilder();     
        json.append("[");     
       if (list != null && list.size() > 0) {     
           for (Object obj : list) {     
                json.append(getJsonString4JavaPOJO(obj));     
                json.append(",");     
            }     
            json.setCharAt(json.length() - 1, ']');     
        } else {     
            json.append("]");     
        }     
       return json.toString();     
    }  

}
