package com.lvmama.passport.processor.impl.client.renwoyou.model;

public class OrderResponse {
	private String status; //标识是否成功
	private String no; //订单编号
	private String state;//订单状态  NOT_USED  未消费   PARTLY_USED 部分消费  USED  已消费
	private String errorMsg;//错误信息
	private int qty;//库存
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public int getQty() {
		return qty;
	}
	public void setQty(int qty) {
		this.qty = qty;
	}
	
}
