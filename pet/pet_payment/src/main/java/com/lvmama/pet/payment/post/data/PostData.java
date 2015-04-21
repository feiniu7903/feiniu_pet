package com.lvmama.pet.payment.post.data;

public interface PostData {

	/**
	 * 进行数字签名
	 * @return 数字签名
	 */
	public String signature();
	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	String getPaymentTradeNo();

}
