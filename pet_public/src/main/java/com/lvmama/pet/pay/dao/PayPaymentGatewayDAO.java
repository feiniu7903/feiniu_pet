package com.lvmama.pet.pay.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;


public class PayPaymentGatewayDAO extends BaseIbatisDAO {

	public Long savePayPaymentGateway(PayPaymentGateway payPaymentGateway){
		return (Long)super.insert("PAY_PAYMENT_GATEWAY.insert", payPaymentGateway);
	}
	public int updatePayPaymentGateway(PayPaymentGateway payPaymentGateway){
		return super.update("PAY_PAYMENT_GATEWAY.update", payPaymentGateway);
	}
	public PayPaymentGateway selectPaymentGatewayByPK(Long paymentGatewayId) {
		return (PayPaymentGateway)super.queryForObject("PAY_PAYMENT_GATEWAY.selectPaymentGatewayByPK", paymentGatewayId);
	}
	public PayPaymentGateway selectPaymentGatewayByGateway(String gateway) {
		return (PayPaymentGateway)super.queryForObject("PAY_PAYMENT_GATEWAY.selectPaymentGatewayByGateway", gateway);
	}
	
	@SuppressWarnings("unchecked")
	public List<PayPaymentGateway> selectPayPaymentGatewayByParamMap(Map<String, String> paramMap){
		return super.queryForList("PAY_PAYMENT_GATEWAY.selectPayPaymentGatewayByParamMap", paramMap);
	}	
	public Long selectPayPaymentGatewayByParamMapCount(Map<String, String> paramMap){
		return (Long) super.queryForObject("PAY_PAYMENT_GATEWAY.selectPayPaymentGatewayByParamMapCount", paramMap);
	}
}
