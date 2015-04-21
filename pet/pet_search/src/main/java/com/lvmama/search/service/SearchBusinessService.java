package com.lvmama.search.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.comm.search.vo.PlaceBean;
import com.lvmama.comm.search.vo.ProductBean;
import com.lvmama.comm.search.vo.TypeCount;
import com.lvmama.search.util.PageConfig;

/**
 * 搜索主要的业务逻辑Service
 * 
 * @author YangGan
 * 
 */
public interface SearchBusinessService  {

	/**
	 * 关键词是否为城市
	 * 
	 * @param keyword
	 *            关键词
	 * @return
	 */
	public PlaceBean KeywordIsCity(String fromDest,String keyword);

	/**
	 * 查询所有栏目的统计数
	 * 
	 * @param request
	 * @param response
	 * @param fromDest
	 * @param keyword
	 * @return
	 */
	public TypeCount getTypeCount(HttpServletRequest request, HttpServletResponse response, String fromDest, String keyword, String orikeyword);
	
	/**
	 * 当搜索不到结果时：
	 * 1.判断出发地是否有关联的出发地
	 * 2.判断关键字是否是出境城市，景点，酒店
	 * 如果同时满足上面一条，则算出关联出发地到关键字的出境产品,直接返回出境产品的列表
	 * 如果不满足返回null
	 * **/
	public PageConfig<ProductBean> foreignHits(String fromDest ,String keyword);

}
