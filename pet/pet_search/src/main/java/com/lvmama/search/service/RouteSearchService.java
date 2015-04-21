package com.lvmama.search.service;

import java.util.Map;

/**
 * 线路搜索(包括所有线路)
 * 
 * @author YangGan
 *
 */
public interface RouteSearchService extends SearchService{

	/**
	 * 查询出发地关联的出发地的产品信息
	 * 
	 * @param fromDest
	 *            出发地
	 * @param keyword
	 *            关键词
	 * @return
	 */
	public Map<String,Object> searchFromDestRelationProduct(String fromDest, String keyword);
}
