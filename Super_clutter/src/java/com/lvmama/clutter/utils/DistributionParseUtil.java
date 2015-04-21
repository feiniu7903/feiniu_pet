package com.lvmama.clutter.utils;

import java.util.Properties;

import com.lvmama.comm.utils.Configuration;
/**
 * 分销文件解析类
 * 
 * @author gaoxin
 * 
 */
public class DistributionParseUtil {

	private static Properties properties = null;

	/**
	 * 获取配置文件属性
	 * 
	 * @param key
	 * @return
	 */
	public static String getPropertiesByKey(String key) {
		String value = "";
		if (properties == null) {
			Configuration configuration = Configuration.getConfiguration();
			properties = configuration.getConfig("/distribution.properties");
		}
		value = properties.getProperty(key);
		return value.trim();
	}
}
