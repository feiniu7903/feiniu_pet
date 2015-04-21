package com.lvmama.comm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
/**
 * 配置管理对象
 * @author clj 2009-9-12
 *
 */
public class Configuration {

	private static final Log log = LogFactory.getLog(Configuration.class);
	private static Configuration configuration = null;
	private static Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

	private Configuration() {

	}

	/**
	 * 得到配置对象
	 * 
	 * @return
	 */
	public static Configuration getConfiguration() {
		if (configuration == null) {
			configuration=new Configuration();
		}
		return configuration;
	}

	/**
	 * 获取配置的资源文件
	 * 
	 * @param resource
	 * @return
	 */
	public Properties getConfig(String resource) {
		Properties config = new Properties();
		try {
			config.load(getResourceAsStream(resource));
		} catch (IOException e) {
			log.error("装载配置文件异常", e);
		}
		return config;
	}
	
	/**
	 * 读取配置属性值
	 * @param resource
	 * @param key
	 * @return
	 */
	public String getPropertyValue(String resource,String key){
		if(!propertiesMap.keySet().contains(resource)){
			Properties properties = new Properties();
			try {
				properties.load(getResourceAsStream(resource));
			} catch (IOException e) {
				log.error("加载配置文件异常。配置文件名称：" + resource, e);
			}
			propertiesMap.put(resource, properties);
		}
		Properties config = propertiesMap.get(resource);
		String value = null;
		try {
			value = new String(config.getProperty(key).getBytes("ISO8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error("读取配置属性值异常。配置属性：" + key, e);
		}
		return value;
	}

	/**
	 * 获取配置的资源文件数据流
	 * 
	 * @param resource
	 * @return
	 */
	public InputStream getResourceAsStream(String resource) {
		String realSource = resource.startsWith("/") ? resource.substring(1) : resource;
		return Thread.currentThread().getContextClassLoader().getResourceAsStream(realSource);
	}
}
