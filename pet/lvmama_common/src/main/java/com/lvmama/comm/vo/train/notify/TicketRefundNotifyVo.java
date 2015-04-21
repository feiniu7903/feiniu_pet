package com.lvmama.comm.vo.train.notify;

import com.lvmama.comm.vo.train.ReqVo;

public class TicketRefundNotifyVo extends ReqVo{
	/**
	 * 退票流水号
	 */
	private String refundId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 退款类型，例如502（卧铺票退还差价）
	 */
	private String refundType;
	/**
	 * 需退票或者退款车票张数
	 */
	private String ticketNum;
	/**
	 * 退款金额
	 */
	private String ticketFee;
	/**
	 * 退款手续费
	 */
	private String ticketCharge;
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
	public String getRefundType() {
		return refundType;
	}
	public void setRefundType(String refundType) {
		this.refundType = refundType;
	}
	public String getTicketNum() {
		return ticketNum;
	}
	public void setTicketNum(String ticketNum) {
		this.ticketNum = ticketNum;
	}
	public String getTicketFee() {
		return ticketFee;
	}
	public void setTicketFee(String ticketFee) {
		this.ticketFee = ticketFee;
	}
	public String getTicketCharge() {
		return ticketCharge;
	}
	public void setTicketCharge(String ticketCharge) {
		this.ticketCharge = ticketCharge;
	}
	
	@Override
	public String toString(){
		return "退款通知参数：(merchant_id:"+this.getMerchantId()
			 + "|sign:" + this.getSign()
			 + "|order_id:" + this.getOrderId()
			 + "|refund_type:" + this.getRefundType()
			 + "|ticket_num:" + this.getTicketNum()
			 + "|ticket_fee:" + this.getTicketFee()
			 + "|ticket_charge:" + this.getTicketCharge() + ")";
	}
}
