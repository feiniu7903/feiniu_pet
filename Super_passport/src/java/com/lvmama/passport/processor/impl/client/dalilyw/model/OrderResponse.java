package com.lvmama.passport.processor.impl.client.dalilyw.model;

public class OrderResponse {
	private String status;
	
	private String state;//成功或失败 的中文状态返回
	
	private String order_code;//验证码
	
	private String order_no;//对方订单号
	
	private String  msg; //fail时MSG
	
	private String statusName; //消费信息

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOrder_code() {
		return order_code;
	}

	public void setOrder_code(String order_code) {
		this.order_code = order_code;
	}

	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
