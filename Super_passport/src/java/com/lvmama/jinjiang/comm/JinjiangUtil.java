package com.lvmama.jinjiang.comm;

import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

import com.lvmama.passport.utils.Md5;
import com.lvmama.passport.utils.WebServiceConstant;

public class JinjiangUtil {
	private static final String PASSWORD = WebServiceConstant.getProperties("jinjiang.password");
	/**
	 * 加密方式
	 * @param channelCode
	 * @param timestamp
	 * @return
	 */
	public static String ciphertextEncode(String channelCode,String timestamp){
		return Md5.encode(channelCode+timestamp+PASSWORD).toUpperCase();
	}
	/**
	 * json配置
	 * @return
	 */
	public static JsonConfig getJsonConfig(){
		JsonConfig config = new JsonConfig();
		config.setExcludes(new String[]{"transactionName","transactionMethod","responseClazz","requestURI","success"});
		//日期格式Date to long Time   
		config.registerJsonValueProcessor(Date.class	, new JsonValueProcessor() {
			@Override
			public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
				return this.process(value);
			}
			@Override
			public Object processArrayValue(Object value, JsonConfig jsonConfig) {
				return this.process(value);
			}
			private Object process(Object value) {  
				if(value!=null){
				    return ((Date)value).getTime();  
				}
				return value;
			  
			}  
		});
		return config;
	}
}
