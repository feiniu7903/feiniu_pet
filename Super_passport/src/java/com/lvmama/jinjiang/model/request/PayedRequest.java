package com.lvmama.jinjiang.model.request;

import java.math.BigDecimal;

import com.lvmama.jinjiang.Response;
import com.lvmama.jinjiang.model.response.PayedResponse;
/**
 * 订单已支付请求
 * @author chenkeke
 *
 */
public class PayedRequest extends AbstractRequest{
	private String orderNo;
	private String thirdOrderNo;
	private BigDecimal actualAmount;//actualBalanceAmount-->actualAmount

	private REQUEST_TYPE type= REQUEST_TYPE.OTA_PayedRQ;
	public PayedRequest(){
		
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
		return  PayedResponse.class;
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
	public BigDecimal getActualAmount() {
		return actualAmount;
	}
	public void setActualAmount(BigDecimal actualAmount) {
		this.actualAmount = actualAmount;
	}
}
