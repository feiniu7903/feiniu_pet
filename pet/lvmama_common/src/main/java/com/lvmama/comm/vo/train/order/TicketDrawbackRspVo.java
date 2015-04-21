package com.lvmama.comm.vo.train.order;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class TicketDrawbackRspVo extends RspVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7854632156L;

	/**
	 * 退票流水号
	 */
	private String refund_id;
	/**
	 * 订单号
	 */
	private String order_id;
	/**
	 * 退票信息
	 */
	private List<TicketDrawbackResult> drawback_result;
	public String getRefund_id() {
		return refund_id;
	}
	public void setRefund_id(String refund_id) {
		this.refund_id = refund_id;
	}
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public List<TicketDrawbackResult> getDrawback_result() {
		return drawback_result;
	}
	public void setDrawback_result(List<TicketDrawbackResult> drawback_result) {
		this.drawback_result = drawback_result;
	}
	
	@Override
	public String toString(){
		for(TicketDrawbackResult ticket : drawback_result){
			System.out.println(ticket);
		}
		return "refund_id:" + refund_id
			+ "|order_id:" + order_id;
	}
}
