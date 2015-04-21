package com.lvmama.passport.processor.impl.client.zhiyoubao.model;

 
public class PWBResponse {
	private String transactionName;//交易
	private String code;//
	private String description;//描述
	private OrderResponse orderResponse;//订单响应
	private String img;
	
	public String getTransactionName() {
		return transactionName;
	}
	public void setTransactionName(String transactionName) {
		this.transactionName = transactionName;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public OrderResponse getOrderResponse() {
		return orderResponse;
	}
	public void setOrderResponse(OrderResponse orderResponse) {
		this.orderResponse = orderResponse;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	
}
