package com.lvmama.pet.fin.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 基于JVM的科目缓存类
 * 
 * @author taiqichao
 * 
 */
public class FinGLSubjectCodeCache {

	private static FinGLSubjectCodeCache subjectCodeCache = null;

	private static Map<String, String> cacheMap=null;

	private FinGLSubjectCodeCache() {
	}

	/**
	 * 获得实例
	 * 
	 * @return 缓存类实例对象
	 */
	public static FinGLSubjectCodeCache getInstance() {
		if (null == subjectCodeCache) {
			subjectCodeCache = new FinGLSubjectCodeCache();
			cacheMap = new ConcurrentHashMap<String, String>();
		}
		return subjectCodeCache;
	}

	/**
	 * 获得科目缓存
	 * 
	 * @param paramMap
	 *            查询参数
	 * @return 科目代码
	 */
	public String get(Map<String, Object> paramMap) {
		return cacheMap.get(keyBuilder(paramMap));
	}

	/**
	 * 将科目放入缓存
	 * 
	 * @param paramMap
	 *            查询参数
	 * @param value
	 *            科目代码
	 */
	public void put(Map<String, Object> paramMap, String value) {
		cacheMap.put(keyBuilder(paramMap), value);
	}

	/**
	 * 构建科目缓存key
	 * 
	 * @param paramMap
	 *            查询参数
	 * @return 科目缓存key
	 */
	public String keyBuilder(Map<String, Object> paramMap) {
		StringBuilder keyBuilder = new StringBuilder();
		for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
			keyBuilder.append(entry.getKey() + "-" + entry.getValue() + "--");
		}
		return keyBuilder.toString();
	}

	public static void clearConfig() {
		cacheMap = new ConcurrentHashMap<String, String>();
	}
}
