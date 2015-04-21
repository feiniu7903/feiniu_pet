/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.jinjiang.model.request;




enum REQUEST_TYPE{
	OTA_LineCodesRQ("getLineCodesByUpdateTime"),//线路编号
	OTA_GetLineRQ("getLineByCode"),//取的线路
	OTA_GetVisasRQ("getVisasByVisaCode"),//取的签证
	OTA_GetGroupRQ("realTimeGetGroup"),//实时获取团信息
	OTA_AddOrderRQ("addOrder"),//创建订单
	OTA_PayedRQ("notifyPayed"),//支付订单
	OTA_CancelOrderRQ("cancelOrder"),//取消订单
	OTA_GetOrderRQ("getOrder"),//查询订单
	OTA_NotifyCancelOrderRQ("notifyCancelOrder");//通知取消订单
	
	private String method;
	REQUEST_TYPE(String method) {
		this.method = method;
	}
	public String getMethod(){
		return this.method;
	}
	
}