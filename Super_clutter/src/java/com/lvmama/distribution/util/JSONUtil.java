package com.lvmama.distribution.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.util.JSONTokener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSONUtil {
	private static final Log log = LogFactory
			.getLog(JSONUtil.class);
	public static JSONArray getArray(String resStr,String param){
		JSONObject jsonObject=getObject(resStr);
		JSONArray jsonArray=new JSONArray();
		try {
			jsonArray = jsonObject.getJSONArray(param);
		} catch (Exception e) {
			jsonArray=null;
			log.error("JSON Exception",e);
		}
		
		return jsonArray;
	}
	public static JSONObject getObject(String resStr){
		JSONTokener jsonParser = new JSONTokener(resStr);
		JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
		return jsonObject;
	}
	public static JSONObject getObject(String resStr,String param){
		JSONObject jsonObject=getObject(resStr);
		try {
			jsonObject = jsonObject.getJSONObject(param);
		} catch (Exception e) {
			jsonObject=null;
			log.error("JSON Exception",e);
		}
		return jsonObject;
	}
	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * @param jsons 
	 * @param clazz 转为的目标class
	 * @param key json中包含的集合的KEY
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection( String jsons, Class<T> clazz,String key) {
		List<T> objs = null;
		JSONObject jo = JSONUtil.getObject(jsons);
		JSONArray jsonArray = jo.getJSONArray(key);
		objs = getJavaCollection(jsonArray,clazz);
		return objs;
	}
	
	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * @param jsons 
	 * @param clazz 转为的目标class
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection( String jsons,Class<T> clazz) {
		List<T> objs = null;
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsons);
		objs = getJavaCollection(jsonArray,clazz);
		return objs;
	}
	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * @param jsonArray 
	 * @param clazz 转为的目标class
	 * @author YangGan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getJavaCollection(JSONArray jsonArray, Class<T> clazz) {
		List<T> objs = null;
		if (jsonArray != null) {
			objs = new ArrayList<T>();
			List<T> list = (List<T>) JSONSerializer.toJava(jsonArray);
			for (Object o : list) {
				JSONObject jsonObject = JSONObject.fromObject(o);
				T obj = (T) JSONObject.toBean(jsonObject, clazz);
				objs.add(obj);
			}
		}
		return objs;
	}
	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * @param jsonArray 
	 * @param clazz 转为的目标class
	 * @param clazzMap 转为的目标class中包含的子JAVA类型
	 * @author YangGan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getJavaCollection(JSONArray jsonArray, Class<T> clazz,Map<String,Class> clazzMap) {
		List<T> objs = null;
		if (jsonArray != null) {
			objs = new ArrayList<T>();
			List<T> list = (List<T>) JSONSerializer.toJava(jsonArray);
			for (Object o : list) {
				JSONObject jsonObject = JSONObject.fromObject(o);
				T obj = (T) JSONObject.toBean(jsonObject, clazz, clazzMap);
				objs.add(obj);
			}
		}
		return objs;
	}
	/**
	 * 把JSON转为指定类型的JAVA对象
	 * @param jsons 
	 * @param clazz 转为的目标class
	 * @author YangGan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getJava(String jsons,Class<T> clazz) {
		JSON json = (JSON) JSONSerializer.toJSON(jsons);
		JSONObject jsonObject =  JSONObject.fromObject(JSONSerializer.toJava(json));
		T obj = (T) JSONObject.toBean(jsonObject, clazz);
		return obj;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getJava(JSONObject jsonObject,Class<T> clazz) {
		T obj = (T) JSONObject.toBean(jsonObject, clazz);
		return obj;
	}
	/**
	 * 把JSON转为指定类型的JAVA对象(对象中包含其他类型的JAVA对象)
	 * @param jsons 
	 * @param clazz 转为的目标class
	 * @param clazzMap key-属性名 value-属性对应的JAVA类
	 * @author YangGan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getJava(String jsons, Class<T> clazz,  Map<String,Class> clazzMap) {
		JSON json = (JSON) JSONSerializer.toJSON(jsons);
		JSONObject jsonObject =  JSONObject.fromObject(JSONSerializer.toJava(json));
		T obj = (T) JSONObject.toBean(jsonObject, clazz, clazzMap);
		return obj;
	}
	
}
