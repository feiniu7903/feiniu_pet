package com.lvmama.comm.vo.train.notify;

import java.util.List;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketDrawbackNotifyVo extends ReqVo {
	/**
	 * 退票流水号
	 */
	private String refundId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 退票张数
	 */
	private String ticketNum;
	/**
	 * 退票详细信息
	 */
	private List<TicketDrawbackInfo> infos;
	
	public String getRefundId() {
		return refundId;
	}
	public void setRefundId(String refundId) {
		this.refundId = refundId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public List<TicketDrawbackInfo> getInfos() {
		return infos;
	}
	public void setInfos(List<TicketDrawbackInfo> infos) {
		this.infos = infos;
	}
	
	@Override
	public String toString(){
		return "退票结果通知参数：(merchant_id:"+this.getMerchantId()
			 + "|sign:" + this.getSign()
			 + "|order_id:" + this.getOrderId()
			 + "|ticket_um:" + this.getTicketNum() + ")";
	}
}
