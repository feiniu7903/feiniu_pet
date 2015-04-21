package com.lvmama.sso.utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 联合登陆工具类
 *
 */
public class UnionLoginUtil {
	/**
	 * 配置文件
	 */
	private static final String CONFIG_FILE = "/union-login-config.properties";
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(UnionLoginUtil.class);
	/**
	 * Properties类
	 */
	private Properties properties;
	
	private static UnionLoginUtil instance;
	
	private static Object LOCK = new Object();

	/**
	 * 无参构造函数
	 */
	private UnionLoginUtil() {
		properties = new Properties();
		try {
			FileReader fileReader = new FileReader(this.getClass()
					.getResource(CONFIG_FILE).getFile());
			properties.load(fileReader);
			fileReader.close();
		} catch (IOException e) {
			LOG.error("Error for read union login properties:" + e.getMessage());
		}
	}
	
	public static UnionLoginUtil getInstance()
	{
		if(instance == null){
			synchronized(LOCK) {
				if (instance==null) {
					instance = new UnionLoginUtil();
				}
			}
		}
		return instance;
	}

	/**
	 * 根据key，获取value
	 * @param key key
	 * @return value
	 */
	public String getValue(final String key) {
		String value = properties.getProperty(key);
		if (null == value) {
			LOG.error("Error for read union login properties.key-->" + key);
		}
		return value;
	}
}
