package com.lvmama.operate.mail.util;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSON;

public class HanQiResources {

	private static Logger logger = Logger.getLogger(HanQiResources.class);
	
	private HanQiResources() {
		init();
	}

	public final static HanQiResources instance = new HanQiResources();
	private Map<String, String> propertiesMap = new HashMap<String, String>();

	@SuppressWarnings("rawtypes")
	private void init() {
		try {
			Properties properties = new Properties();
			properties.load(Thread.currentThread().getContextClassLoader()
					.getResourceAsStream("edm_config.properties"));
			Enumeration enumeration = properties.propertyNames();
			while (enumeration.hasMoreElements()) {
				Object object = (Object) enumeration.nextElement();
				propertiesMap.put(object.toString(),
						properties.getProperty(object.toString()));
			}
		} catch (Exception e) {
			logger.error("加载汉启资源配置文件(hanqi.properties)出错:" + e.getMessage(), e);
		}
	}

	public Map<String, String> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, String> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}

	public static String get(String key) {
		return instance.propertiesMap.get(key);
	}

	public static void main(String[] args) {
		System.out.println(JSON.toJSONString(HanQiResources.instance.getPropertiesMap(),true));
	}
}
