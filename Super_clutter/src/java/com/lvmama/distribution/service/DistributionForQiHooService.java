package com.lvmama.distribution.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.distribution.DistributionPlaceProduct;

/**
 * 奇虎360 Service
 * @author gaoxin
 *
 */
public interface DistributionForQiHooService {
	/**
	 * 生成图片xml
	 * @param placeName
	 * @return
	 */
	public  String creatPlaceImageXml(String placeName);
	
	/**
	 * 获取产品名称 价格信息
	 * @param placeName
	 * @return
	 */
	public List<DistributionPlaceProduct> getProductList(String placeName);
	/**
	 * 生成攻略游记xml
	 * @param placeName
	 * @return
	 */
	public String getPlaceGuides(String placeName);
	
	/**
	 * 酒店线路
	 * @return
	 */
	public String createRouteProductXml(Map<String,Object> param);
	
	/**
	 * 酒店线路产品数量
	 * @return
	 */
	public Integer getRouteProductCount();

	public void createXmlFile(String routeProductXml, String fileName);
	/**
	 * 360更新产品接口
	 */
	public void updateRouteProduct();
}
