package com.lvmama.jinjiang.model.request;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.CancelOrderResponse;

/**
 * 取消订单请求
 * @author chenkeke
 *
 */
public class CancelOrderRequest extends AbstractRequest{
	private String orderNo;
	private String thirdOrderNo;
	private REQUEST_TYPE type= REQUEST_TYPE.OTA_CancelOrderRQ;
	public CancelOrderRequest(){
		
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
		return  CancelOrderResponse.class;
	}
	
	public CancelOrderRequest(String orderNo,String thirdOrderNo){
		this.orderNo = orderNo;
		this.thirdOrderNo = thirdOrderNo;
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
}
