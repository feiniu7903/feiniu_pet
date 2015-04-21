package com.lvmama.pet.payment.service;

import java.util.Map;

import com.lvmama.comm.pet.service.pay.PayPosCommercialService;

public interface SandPosService {

	/**
	 * 交通银行POS机的用户的登录验证.
	 * @param message
	 * @return
	 */
	String posUserLogin(Map<String, String> posMap);
	/**
	 * POS机订单查询.
	 * @param message
	 * @return
	 */
	String queryOrderAmountByOrderId(Map<String, String> posMap);
	
	/**
	 * POS机支付通知.
	 * @param message
	 * @return
	 */
	String orderPayNotice(Map<String, String> posMap);
	
	void setPayPosCommercialService(
			PayPosCommercialService payPosCommercialService);

}