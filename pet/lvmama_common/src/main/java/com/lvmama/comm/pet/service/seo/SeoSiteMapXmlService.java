package com.lvmama.comm.pet.service.seo;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;


public interface SeoSiteMapXmlService {
	
	/**
	 * 查询网站所有的使用景点或目的地数量.
	 * 
	 * @return
	 */
	public Long queryPlaceAllCount(String stage);
	/**
	 * 查询place的信息（id,pinYinUrl）
	 * @param stage
	 * @return
	 */
	public Map<String, Object> queryPlaceAllMap(int totalResultSize,int totalWrite,int xmlCouter,int resultCounter,int countPage,String stage);
	
	/**
	 * 查询网站所有的在线销售产品数量.
	 * 
	 * @return
	 */
	public Long queryPlaceProductAllCount();
	/**
	 *查询网站所有的在线销售产品. return List<String> 返回景点编号,用于生成xml文件.
	 * 
	 * @return
	 */
	public Map<String, Object> queryPlaceProductAllMap(int totalResultSize,int totalWrite, int xmlCouter, int resultCounter,int countPage);
	/**
	 * 分页查询place(placeId,pinYinUrl)
	 * @param param
	 * @return
	 */
	public List<Place> queryPlacePage(Map<String, Object> param);
	/**
	 * 分页查询productSearchInfo(productId)
	 * @param param
	 * @return
	 */
	public List<ProductSearchInfo> queryProductPage(Map<String, Object> param);
}
