package com.lvmama.passport.utils;

import java.util.Properties;
/**
 * 通关系统解析工具类
 * @author chenlinjun
 *
 */
public class WebServiceConstant {

	private static Properties properties=null;

	/**
	 * 获取配置文件属性
	 * @param key
	 * @return
	 */
    public static String getProperties(String key){
    	init();
    	if(properties!=null){
    		String value=properties.getProperty(key);
    		if (value!=null) {
    			return value.trim();
    		}
       	}
    	return "";
    }
    
    private static void init() {
    	if(properties==null){
    		Configuration configuration = Configuration.getConfiguration();
    		properties=configuration.getConfig("/webService.properties");
    	}
    }
}
