package com.lvmama.pet.payment.service;


/**
 * 交通银行POS机的登录,查询订单,支付通知 处理,登出 .
 * @author liwenzhan
 *
 */
public interface CommPosSocketService {

	/**
	 * 交通银行POS机的用户的登录验证.
	 * @param message
	 * @return
	 */
	String posUserLogin(String message);

	/**
	 * 订单支付通知.
	 * @param message
	 * @return
	 */
	String orderPayNotice(String message);

	/**
	 * 订单查询.
	 * @param message
	 * @return
	 */
	String queryOrderAmountByOrderId(String message);

}