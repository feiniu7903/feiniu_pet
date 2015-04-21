package com.lvmama.tnt.order.service;


/**
 * 分销平台创建订单
 * 
 * @author gaoxin
 * 
 */
public interface TntTimePriceService {

	/**
	 * 获取产品时间价格json
	 */

	public String getJSONTimePrice(Long productId, Long userId);

}
