package com.lvmama.comm.pet.service.pay;

/**
 * 支付结果由支付系统通知业务系统
 * @author Alex Wang
 *
 */
public interface PaymentCallbackService {

	/**
	 * 通知业务系统支付成功
	 * @param orderId
	 * @return
	 */
	public boolean paymentSuccess(Long orderId);
	
	/**
	 * 通知业务系统支付失败
	 * @param orderId
	 * @return
	 */
	public boolean paymentFailed(Long orderId);

}
