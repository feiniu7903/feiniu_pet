package com.lvmama.passport.utils;

import java.util.Properties;

public class Md5KeyConfig {
	private static Properties config = null;
    private static void init() {
    	Configuration configuration=Configuration.getConfiguration();
    	config=configuration.getConfig("/passport-key.properties");
    }
    /**
	 * 获取加密信息
	 * 
	 * @param name
	 * @return
	 */
	public static String getMD5Key(String name) {
		if(config == null) {
			init();
		}
		if(config == null) {
			return "";
		}
		return config.getProperty(name);
	}
}
