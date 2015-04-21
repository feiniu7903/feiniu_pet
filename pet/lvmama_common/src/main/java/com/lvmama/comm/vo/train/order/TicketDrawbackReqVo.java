package com.lvmama.comm.vo.train.order;

import java.util.List;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketDrawbackReqVo extends ReqVo {
	/**
	 * 用户IP，例如101.102.103.104
	 */
	private String userIp;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 退款类型，例如505（用户向商户申请退票）
	 */
	private int refundType;
	/**
	 * 退票张数
	 */
	private int ticketNum;
	/**
	 * 退票信息
	 */
	private List<TicketDrawbackInfo> ticketDrawbackInfos;
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public int getRefundType() {
		return refundType;
	}
	public void setRefundType(int refundType) {
		this.refundType = refundType;
	}
	public int getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(int ticketNum) {
		this.ticketNum = ticketNum;
	}
	public List<TicketDrawbackInfo> getTicketDrawbackInfos() {
		return ticketDrawbackInfos;
	}
	public void setTicketDrawbackInfos(List<TicketDrawbackInfo> ticketDrawbackInfos) {
		this.ticketDrawbackInfos = ticketDrawbackInfos;
	}
}
