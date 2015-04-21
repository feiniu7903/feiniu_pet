package com.lvmama.passport.processor.impl.client.renwoyou.model;

import java.util.List;

public class OrderRequest {
	private String action;// 必填，固定为"PLACE_ORDER"
	private String outOrderNoString;// 外部订单号
	private String buyer;// 购买人姓名
	private String mobile;// 购买人手机
	private String idCardNo; // 购买人身份证，当景点idCardNeeded值为“1”时，必须录入一张游玩客人的身份证，以便验证。当景点idCardAccepted值为“1”时，则可选填。
	private String salePrice;// 此单的销售价格。
	private List<OrderItem> orderItemList;//产品列表
	private String payType;//支付方式  0日结  1 月结


	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getOutOrderNoString() {
		return outOrderNoString;
	}

	public void setOutOrderNoString(String outOrderNoString) {
		this.outOrderNoString = outOrderNoString;
	}

	public String getBuyer() {
		return buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getIdCardNo() {
		return idCardNo;
	}

	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(String salePrice) {
		this.salePrice = salePrice;
	}

	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}
}
