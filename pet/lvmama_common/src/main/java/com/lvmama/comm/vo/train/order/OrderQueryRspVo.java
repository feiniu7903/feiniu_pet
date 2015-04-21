package com.lvmama.comm.vo.train.order;

import com.lvmama.comm.vo.train.RspVo;

public class OrderQueryRspVo extends RspVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2765388462L;
	
	/**
	 * 订单号
	 */
	private String order_id;
	/**
	 * 订单状态码
	 */
	private int order_status;
	/**
	 * 订单状态信息
	 */
	private String order_msg;
	public String getOrder_id() {
		return order_id;
	}
	public void setOrder_id(String order_id) {
		this.order_id = order_id;
	}
	public int getOrder_status() {
		return order_status;
	}
	public void setOrder_status(int order_status) {
		this.order_status = order_status;
	}
	public String getOrder_msg() {
		return order_msg;
	}
	public void setOrder_msg(String order_msg) {
		this.order_msg = order_msg;
	}
	
	@Override
	public String toString(){
		return "order_id:" + order_id
			+ "|order_status:" + order_status
			+ "|order_msg:" + order_msg;
	}
}
