package com.lvmama.comm.vo.train.notify;

import java.util.List;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketIssueNotifyVo extends ReqVo {
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 订单状态码，出票成功为804
	 */
	private String orderStatus;
	/**
	 * 订单状态信息
	 */
	private String orderMsg;
	/**
	 * 车票信息
	 */
	private List<TicketIssueInfo> issueInfos;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderMsg() {
		return orderMsg;
	}
	public void setOrderMsg(String orderMsg) {
		this.orderMsg = orderMsg;
	}
	public List<TicketIssueInfo> getIssueInfos() {
		return issueInfos;
	}
	public void setIssueInfos(List<TicketIssueInfo> issueInfos) {
		this.issueInfos = issueInfos;
	}
	
	@Override
	public String toString(){
		return "出票结果通知参数：(merchant_id:"+this.getMerchantId()
			 + "|sign:" + this.getSign()
			 + "|order_id:" + this.getOrderId()
			 + "|order_status:" + this.getOrderStatus()
			 + "|order_msg:" + this.getOrderMsg() + ")";
	}
}
