/**
 * 
 */
/**
 * @author yangbin
 *
 */
package com.lvmama.train.service.ws;

import com.lvmama.train.service.ws.handle.OrderRefundRequest;
import com.lvmama.train.service.ws.handle.ProductUpdateRequest;
import com.lvmama.train.service.ws.handle.TicketDrawbackRequest;
import com.lvmama.train.service.ws.handle.TicketIssueResultRequest;

enum TRAIN_INTERFACE{
	/**
	 * 1.1.	车站列表查询接口
	 */
	STATION_QUERY(911, "/v1/product/station_query", TicketIssueResultRequest.class),
	/**
	 * 1.2.	城市车站对应关系查询接口
	 */
	CITY_STATION_QUERY(912, "/v1/product/city_station_query", TicketIssueResultRequest.class),
	/**
	 * 1.3.	车次基本信息查询接口
	 */
	TRAIN_INFO_QUERY(913, "/v1/product/train_info_query", TicketIssueResultRequest.class),
	/**
	 * 1.4.	车次经停信息查询接口
	 */
	TRAIN_PASS_QUERY(914, "/v1/product/train_pass_query", TicketIssueResultRequest.class),
	/**
	 * 1.5.	票价信息查询接口
	 */
	TRAIN_PRICE_QUERY(915, "/v1/product/train_price_query", TicketIssueResultRequest.class),
	/**
	 * 1.6.	订票信息查询接口
	 */
	TRAIN_TICKET_QUERY(916, "/v1/product/train_ticket_query", TicketIssueResultRequest.class),
	/**
	 * 1.7.	车票库存验证接口
	 */
	TRAIN_TICKET_VERIFY(917, "/v1/product/train_ticket_verify", TicketIssueResultRequest.class),
	/**
	 * 2.1.	创建订单接口
	 */
	TICKET_ORDER_CREATE(921, "/v1/order/ticket_order_create", TicketIssueResultRequest.class),
	/**
	 * 2.2.	取消订单接口
	 */
	TICKET_ORDER_CANCEL(922, "/v1/order/ticket_order_cancel", TicketIssueResultRequest.class),
	/**
	 * 2.3.	订单支付成功接口
	 */
	TICKET_ORDER_PAID(923, "/v1/order/ticket_order_paid", TicketIssueResultRequest.class),
	/**
	 * 2.4.	订单状态查询接口
	 */
	TICKET_ORDER_QUERY(924, "/v1/order/ticket_order_query", TicketIssueResultRequest.class),
	/**
	 * 2.5.	退票请求接口
	 */
	TICKET_ORDER_DRAWBACK(925, "/v1/order/ticket_order_drawback", TicketIssueResultRequest.class),
	/**
	 * 2.6.	退款成功请求接口
	 */
	TICKET_ORDER_REFUND(926, "/v1/order/ticket_order_refund", TicketIssueResultRequest.class),
	/**
	 * 3.1.	出票结果通知接口
	 */
	TICKET_ISSUED_NOTIFY(931, "/v1/notice/ticket_issued_notify", TicketIssueResultRequest.class),
	/**
	 * 3.2.	退票结果通知
	 */
	TICKET_DRAWBACK_NOTIFY(932, "/v1/notice/ticket_drawback_notify", TicketDrawbackRequest.class),
	/**
	 * 3.3.	退款通知接口
	 */
	TICKET_REFUND_NOTIFY(933, "v1/notice/ticket_refund_notify", OrderRefundRequest.class),
	/**
	 * 3.4.	产品数据更新通知接口
	 */
	INTERFACE_UPDATE_NOTIFY(934, "/v1/notice/interface_update_notify", ProductUpdateRequest.class);
	
	
	final int iCode;
	String iName;
	Class iClass;
	TRAIN_INTERFACE(int iCode, String iName, Class iClass){
		this.iCode = iCode;
		this.iName = iName;
		this.iClass = iClass;
	}
	public int getICode(){
		return this.iCode;
	}
	public String getIName(){
		return this.iName;
	}
	public Class getIClass(){
		return this.iClass;
	}
}

