package com.lvmama.jinjiang.model.request;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.GetOrderResponse;

/**
 * 查询订单
 * @author chenkeke
 *
 */
public class GetOrderRequest extends AbstractRequest{
	private String orderNo;
	private String thirdOrderNo;

	private REQUEST_TYPE type= REQUEST_TYPE.OTA_GetOrderRQ;
	public GetOrderRequest(String orderNo,String thirdOrderNo){
		this.orderNo=orderNo;
		this.thirdOrderNo=thirdOrderNo;
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
		return  GetOrderResponse.class;
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
