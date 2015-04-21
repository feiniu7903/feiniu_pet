package com.lvmama.clutter.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.JSONTokener;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class JSONUtil {
	private static final Log log = LogFactory.getLog(JSONUtil.class);

	public static JSONArray getArray(String resStr, String param) {
		JSONObject jsonObject = getObject(resStr);
		JSONArray jsonArray = new JSONArray();
		try {
			jsonArray = jsonObject.getJSONArray(param);
		} catch (Exception e) {
			jsonArray = null;
			log.error("JSON Exception", e);
		}

		return jsonArray;
	}

	public static JSONObject getObject(String resStr) {
		JSONTokener jsonParser = new JSONTokener(resStr);
		JSONObject jsonObject = (JSONObject) jsonParser.nextValue();
		return jsonObject;
	}

	public static JSONObject getObject(String resStr, String param) {
		JSONObject jsonObject = getObject(resStr);
		try {
			jsonObject = jsonObject.getJSONObject(param);
		} catch (Exception e) {
			jsonObject = null;
			log.error("JSON Exception", e);
		}
		return jsonObject;
	}

	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * 
	 * @param jsons
	 * @param clazz
	 *            转为的目标class
	 * @param key
	 *            json中包含的集合的KEY
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection(String jsons, Class<T> clazz,
			String key) {
		List<T> objs = null;
		JSONObject jo = JSONUtil.getObject(jsons);
		JSONArray jsonArray = jo.getJSONArray(key);
		objs = getJavaCollection(jsonArray, clazz);
		return objs;
	}

	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * 
	 * @param jsons
	 * @param clazz
	 *            转为的目标class
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection(String jsons, Class<T> clazz) {
		List<T> objs = null;
		JSONArray jsonArray = (JSONArray) JSONSerializer.toJSON(jsons);
		objs = getJavaCollection(jsonArray, clazz);
		return objs;
	}

	/**
	 * 把JSON转为指定类型的JAVA对象集合
	 * 
	 * @param jsonArray
	 * @param clazz
	 *            转为的目标class
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection(JSONArray jsonArray,
			Class<T> clazz) {
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
	 * 
	 * @param jsonArray
	 * @param clazz
	 *            转为的目标class
	 * @param clazzMap
	 *            转为的目标class中包含的子JAVA类型
	 * @author YangGan
	 * @return
	 */
	public static <T> List<T> getJavaCollection(JSONArray jsonArray,
			Class<T> clazz, Map<String, Class> clazzMap) {
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
	 * 
	 * @param jsons
	 * @param clazz
	 *            转为的目标class
	 * @author YangGan
	 * @return
	 */
	public static <T> T getJava(String jsons, Class<T> clazz) {
		JSON json = (JSON) JSONSerializer.toJSON(jsons);
		JSONObject jsonObject = JSONObject.fromObject(JSONSerializer
				.toJava(json));
		T obj = (T) JSONObject.toBean(jsonObject, clazz);
		return obj;
	}

	public static <T> T getJava(JSONObject jsonObject, Class<T> clazz) {
		T obj = (T) JSONObject.toBean(jsonObject, clazz);
		return obj;
	}

	/**
	 * 把JSON转为指定类型的JAVA对象(对象中包含其他类型的JAVA对象)
	 * 
	 * @param jsons
	 * @param clazz
	 *            转为的目标class
	 * @param clazzMap
	 *            key-属性名 value-属性对应的JAVA类
	 * @author YangGan
	 * @return
	 */
	public static <T> T getJava(String jsons, Class<T> clazz,
			Map<String, Class> clazzMap) {
		JSON json = (JSON) JSONSerializer.toJSON(jsons);
		JSONObject jsonObject = JSONObject.fromObject(JSONSerializer
				.toJava(json));
		T obj = (T) JSONObject.toBean(jsonObject, clazz, clazzMap);
		return obj;
	}

	/**
	 * json 过滤属性
	 * 
	 * @param obj
	 *            过滤的对象
	 * @param excludeProperties
	 *            需要过滤的属性数组
	 * @return
	 */
	public static JSONObject jsonPropertyIgnore(Object obj,
			String... excludeProperties) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setExcludes(excludeProperties);
		return JSONObject.fromObject(obj, jsonConfig);
	}

	/**
	 * json 过滤属性
	 * 
	 * @param obj
	 *            过滤的对象
	 * @param excludeProperties
	 *            需要过滤的属性数组
	 * @return
	 */
	public static JSONArray jsonArrayPropertyIgnore(Object obj,
			String... excludeProperties) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setIgnoreDefaultExcludes(false);
		jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
		jsonConfig.setExcludes(excludeProperties);
		return JSONArray.fromObject(obj, jsonConfig);
	}

	/**
	 * JSON 格式化日期
	 * 
	 * @param obj
	 *            json转化对象
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static JSONObject jsonDateFormat(Object obj, final String pattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						if (value instanceof java.util.Date) {
							String str = DateFormatUtils.format((Date) value,
									pattern);
							return str;
						}
						return value.toString();
					}

					@Override
					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						String[] obj = {};
						if (value instanceof Date[]) {
							Date[] dates = (Date[]) value;
							obj = new String[dates.length];
							for (int i = 0; i < dates.length; i++) {
								String str = DateFormatUtils.format(dates[i],
										pattern);
								obj[i] = str;
							}
						}
						return obj;
					}
				});
		return JSONObject.fromObject(obj, jsonConfig);
	}

	/**
	 * JSON 格式化日期
	 * 
	 * @param obj
	 *            json转化对象
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static JSONArray jsonArrayDateFormat(Object obj, final String pattern) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						if (value instanceof java.util.Date) {
							String str = DateFormatUtils.format((Date) value,
									pattern);
							return str;
						}
						return value.toString();
					}

					@Override
					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						String[] obj = {};
						if (value instanceof Date[]) {
							Date[] dates = (Date[]) value;
							obj = new String[dates.length];
							for (int i = 0; i < dates.length; i++) {
								String str = DateFormatUtils.format(dates[i],
										pattern);
								obj[i] = str;
							}
						}
						return obj;
					}
				});
		return JSONArray.fromObject(obj, jsonConfig);
	}

	/**
	 * 
	 * JSON 格式化日期
	 * 
	 * @param obj
	 *            json转化对象
	 * @param pattern
	 *            格式
	 * @param excludeProperties
	 *            该日期字段不需要格式化
	 * @return
	 */
	public static JSONObject jsonDateFormat(Object obj, final String pattern,
			final String... excludeProperties) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(java.util.Date.class,
				new JsonValueProcessor() {

					@Override
					public Object processObjectValue(String key, Object value,
							JsonConfig jsonConfig) {
						if (value instanceof java.util.Date) {
							List<String> excludePropertyList = Arrays
									.asList(excludeProperties);
							if (excludePropertyList.contains(key)) {
								return value.toString();//
							}
							String str = DateFormatUtils.format((Date) value,
									pattern);
							return str;
						}
						return value.toString();
					}

					@Override
					public Object processArrayValue(Object value,
							JsonConfig jsonConfig) {
						String[] obj = {};
						if (value instanceof Date[]) {
							Date[] dates = (Date[]) value;
							obj = new String[dates.length];
							for (int i = 0; i < dates.length; i++) {
								String str = DateFormatUtils.format(dates[i],
										pattern);
								obj[i] = str;
							}
						}
						return obj;
					}
				});
		return JSONObject.fromObject(obj, jsonConfig);
	}

	public static JSONObject jsonPropertyFilter(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter() {
			public boolean apply(Object source, String name, Object value) {
				if (null == value) {
					return true;
				} else if (StringUtils.isEmpty(value.toString())) {
					return true;
				} else {
					return false;
				}
			}
		});
		return JSONObject.fromObject(obj, jsonConfig);
	}

}
