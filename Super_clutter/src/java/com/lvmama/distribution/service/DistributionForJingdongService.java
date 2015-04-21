package com.lvmama.distribution.service;

import com.lvmama.distribution.model.jd.Request;
import com.lvmama.comm.bee.po.distribution.DistributionOrderRefund;
/**
 * 京东调用Service
 * @author gaoxin
 *
 */
public interface DistributionForJingdongService {
	
	/**
	 * 京东提供服务   添加景区
	 */
	public void addResources();
	
	/**
	 * 京东提供服务    更新景区
	 */
	public void updateResources();
	
	/**
	 * 京东提供服务    新增产品
	 */
	public void addProducts();
	
	/**
	 * 京东提供服务    更新产品
	 */
	public void updateProducts();
	
	/**
	 * 京东提供服务    上、下架产品
	 */
	public void onOffLineProduct();
	
	/**
	 * 京东提供服务    新增每日价格
	 */
	public void addDailyPrices();
	
	/**
	 * 京东提供服务    修改每日价格
	 */
	public void updateDailyPrice();
	
	/**
	 * 驴妈妈发起退款
	 * @throws Exception 
	 */
	public boolean applyOrderRefund(DistributionOrderRefund refendHistory) ;
	
	/**
	 * 获取重发短信xml
	 * @param request
	 * @return
	 */
	public String getReSendSMSXml(Request request);
	
	/**
	 * 京东下单
	 * @param request
	 * @return
	 */
	public String getSumbitOrderResXml(Request request);
	/**
	 * 查询订单
	 * @param request
	 * @return
	 */
	public String getQueryOrderResXml(Request request);
	/**
	 * 查询每日价格
	 * @param request
	 * @return
	 */
	public String getDailyPrices(Request request);
	
}
