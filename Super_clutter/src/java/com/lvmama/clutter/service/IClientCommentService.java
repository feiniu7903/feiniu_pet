package com.lvmama.clutter.service;

import java.util.Map;

public interface IClientCommentService {

	/**
	 * 景点点评.
	 * @param param
	 * @return
	 */
	Map<String,Object> getPlaceComment(Map<String, Object> param);
	
	/**
	 * 产品详情.
	 * @param param productId 产品id
	 * @return
	 */
	Map<String,Object> getProductComment(Map<String,Object> param);
}
