package com.lvmama.pet.payment.phonepay;


public interface PhonepayClient {
	/**
	 * 发送数据.
	 * @param caoo
	 * @param paymentTradeNo 对账流水号
	 * @param transType 交易类型
	 */
	void sendData(CardAndOrderObject caoo, String paymentTradeNo);
}
