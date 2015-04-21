package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;


public class PayPaymentGatewayElementDAO extends BaseIbatisDAO {

	public Long savePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement){
		return (Long)super.insert("PAY_PAYMENT_GATEWAY_ELEMENT.insert", payPaymentGatewayElement);
	}
	public int updatePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement){
		return super.update("PAY_PAYMENT_GATEWAY_ELEMENT.update", payPaymentGatewayElement);
	}
	public PayPaymentGatewayElement selectPaymentGatewayElementByPK(Long paymentGatewayElementId) {
		return (PayPaymentGatewayElement)super.queryForObject("PAY_PAYMENT_GATEWAY_ELEMENT.selectPaymentGatewayElementByPK", paymentGatewayElementId);
	}
	public PayPaymentGatewayElement selectPaymentGatewayElementByGateway(String gateway) {
		return (PayPaymentGatewayElement)super.queryForObject("PAY_PAYMENT_GATEWAY_ELEMENT.selectPaymentGatewayElementByGateway", gateway);
	}
	
	@SuppressWarnings("unchecked")
	public List<PayPaymentGatewayElement> selectPayPaymentGatewayElementByParamMap(Map<String, String> paramMap){
		return super.queryForList("PAY_PAYMENT_GATEWAY_ELEMENT.selectPayPaymentGatewayElementByParamMap", paramMap);
	}	
	public Long selectPayPaymentGatewayElementByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject("PAY_PAYMENT_GATEWAY_ELEMENT.selectPayPaymentGatewayElementByParamMapCount", paramMap);
	}
}
