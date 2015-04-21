package com.lvmama.pet.pay.service;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayElementService;
import com.lvmama.pet.pay.dao.PayPaymentGatewayElementDAO;

public class PayPaymentGatewayElementServiceImpl implements PayPaymentGatewayElementService {
	
	
	private PayPaymentGatewayElementDAO payPaymentGatewayElementDAO;

	@Override
	public Long savePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement) {
		return payPaymentGatewayElementDAO.savePayPaymentGatewayElement(payPaymentGatewayElement);
	}

	@Override
	public int updatePayPaymentGatewayElement(PayPaymentGatewayElement payPaymentGatewayElement) {
		return payPaymentGatewayElementDAO.updatePayPaymentGatewayElement(payPaymentGatewayElement);
	}

	@Override
	public PayPaymentGatewayElement selectPaymentGatewayElementByPK(Long paymentGatewayElementId) {
		return payPaymentGatewayElementDAO.selectPaymentGatewayElementByPK(paymentGatewayElementId);
	}

	@Override
	public PayPaymentGatewayElement selectPaymentGatewayElementByGateway(String gateway) {
		return payPaymentGatewayElementDAO.selectPaymentGatewayElementByGateway(gateway);
	}

	@Override
	public List<PayPaymentGatewayElement> selectPayPaymentGatewayElementByParamMap(Map<String, String> paramMap) {
		return payPaymentGatewayElementDAO.selectPayPaymentGatewayElementByParamMap(paramMap);
	}

	@Override
	public Long selectPayPaymentGatewayElementByParamMapCount(Map<String, String> paramMap) {
		return payPaymentGatewayElementDAO.selectPayPaymentGatewayElementByParamMapCount(paramMap);
	}

	
	public PayPaymentGatewayElementDAO getPayPaymentGatewayElementDAO() {
		return payPaymentGatewayElementDAO;
	}

	public void setPayPaymentGatewayElementDAO(PayPaymentGatewayElementDAO payPaymentGatewayElementDAO) {
		this.payPaymentGatewayElementDAO = payPaymentGatewayElementDAO;
	}
}
