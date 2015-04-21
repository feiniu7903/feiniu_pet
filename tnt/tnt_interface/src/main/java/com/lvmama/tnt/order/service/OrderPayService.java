package com.lvmama.tnt.order.service;

import java.util.List;

import com.lvmama.tnt.comm.vo.ResultGod;
import com.lvmama.tnt.order.po.TntOrder;

public interface OrderPayService {

	/**
	 * 支付前验证，验证支付密码和余额
	 * 
	 * @param userId
	 * @param paymentPassword
	 * @param orderId
	 * @return
	 */
	public String validateBeforePay(Long userId, String paymentPassword,
			List<Long> orderId);

	/**
	 * 周结，月结可以不验证余额
	 * 
	 * @param userId
	 * @return
	 */
	public boolean canIgnoreBalance(Long userId);

	public ResultGod<List<TntOrder>> orderPayment(Long userId,
			String paymentPassword, List<Long> orderIds);

}
