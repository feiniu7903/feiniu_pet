/**
 * 
 */
package com.lvmama.train.service;

import java.util.Map;

/**
 * @author yangbin
 *
 */
public interface TrainRequest {

	/**
	 * 得到请求参数的列表
	 * @return
	 */
	Map<String, String> getParam();
	
	/**
	 * 得到响应的处理解析类
	 * @return
	 */
	Class<? extends TrainResponse> getClazz();
	
	/**
	 * 获取http请求url
	 * @return
	 */
	String getUrl();
	/**
	 * 获取不包括域名的请求url
	 * @return
	 */
	String getBaseUrl();
	/**
	 * 获取请求类型
	 * */
	String getRequestType();
	/**
	 * 获取需要记录的主键id
	 * */
	Long getObjectId();
}
