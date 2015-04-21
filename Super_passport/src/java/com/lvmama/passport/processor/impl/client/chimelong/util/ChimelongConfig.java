package com.lvmama.passport.processor.impl.client.chimelong.util;

import com.lvmama.passport.utils.WebServiceConstant;

public class ChimelongConfig {
	/**
	 * 返回长隆wsdl资源
	 * @return
	 */
	//广州长隆
	public static String getWebserviceUrl() {
		return WebServiceConstant.getProperties("chimelong");
	}
	public static String getUserId(){
		return WebServiceConstant.getProperties("chimelong_userid");
	}
	public static String getPassword(){
		return WebServiceConstant.getProperties("chimelong_password");
	}
	public static String getKey(){
		return WebServiceConstant.getProperties("chimelong_key");
	}
	//珠海长隆
	public static String getZhWebserviceUrl() {
		return WebServiceConstant.getProperties("zh_chimelong");
	}
	public static String getZhUserId(){
		return WebServiceConstant.getProperties("zh_chimelong_userid");
	}
	public static String getZhPassword(){
		return WebServiceConstant.getProperties("zh_chimelong_password");
	}
	public static String getZhKey(){
		return WebServiceConstant.getProperties("zh_chimelong_key");
	}
	public static String getVersion(){
		return "11";
	}
}
