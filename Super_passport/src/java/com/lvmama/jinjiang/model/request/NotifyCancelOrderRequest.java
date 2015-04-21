package com.lvmama.jinjiang.model.request;

import java.math.BigDecimal;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.NotifyCancelOrderResponse;

public class NotifyCancelOrderRequest extends AbstractRequest{
	private String orderNo;
	private String thirdOrderNo;
	private String orderStatus;
	private BigDecimal lossAmount;
	private String payStatus;
	private String refundRemark;

	private REQUEST_TYPE type= REQUEST_TYPE.OTA_NotifyCancelOrderRQ;
	public NotifyCancelOrderRequest(){
		
	}
	@Override
	public String getTransactionName() {
		return type.name();
	}
	@Override
	public String getTransactionMethod() {
		return type.getMethod();
	};

	@Override
	public Class<? extends Response> getResponseClazz() {
		return  NotifyCancelOrderResponse.class;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getThirdOrderNo() {
		return thirdOrderNo;
	}
	public void setThirdOrderNo(String thirdOrderNo) {
		this.thirdOrderNo = thirdOrderNo;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public BigDecimal getLossAmount() {
		return lossAmount;
	}
	public void setLossAmount(BigDecimal lossAmount) {
		this.lossAmount = lossAmount;
	}
	public String getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}
	public String getRefundRemark() {
		return refundRemark;
	}
	public void setRefundRemark(String refundRemark) {
		this.refundRemark = refundRemark;
	}
}
