/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.shholiday.request;




enum REQUEST_TYPE{
	OTA_TourBookRQ,//预定
	OTA_TourBookCheckRQ,//检查
	OTA_TourOrderUpdateRQ,//订单修改
	OTA_TourOrderCancelRQ,//订单取消
	OTA_TourOrderDetailRQ,//订单查询
	OTA_TourOrderPayRQ,//订单支付
	OTA_TourBookModifyRQ,//订单退改申请
	OTA_TourTeamProductSearchRQ,//产品列表查询
	OTA_TourProductDetailRQ,//产品详情查询
}