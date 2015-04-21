package com.lvmama.clutter.service;

import java.util.Map;

/**
 * 订单相关服务
 * @author dengcheng
 *
 */
public interface IClientOrderService {
	
	Map<String,Object> commitOrder(Map<String,Object> param);
	
	Map<String,Object> queryOrderWaitPayTimeSecond(Map<String,Object> param);
	
	Map<String,Object> cancelOrder(Map<String,Object> param);
	
	Map<String,Object> cancellOrder4Wap(Map<String,Object> param);
	
	Map<String,Object>  getEContractInfo(Map<String,Object> param);
	
	/**
	 * 在线签约. 
	 * @param param
	 * @return 
	 * @throws Exception 
	 */
	String onlineSign(Map<String,Object> param) throws Exception;
	Map<String,Object> validateCoupon(Map<String,Object> param);

	Map<String, Object> cashAccountValidateAndPay(Map<String, Object> param);
	
}
