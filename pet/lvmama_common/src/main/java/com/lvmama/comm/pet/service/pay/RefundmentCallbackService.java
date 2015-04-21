package com.lvmama.comm.pet.service.pay;

/**
 * 退款结果由支付系统通知业务系统的接口
 * @author Alex Wang
 *
 */
public interface RefundmentCallbackService {
	/**
	 * 通知业务系统退款成功
	 * @param orderId
	 * @return
	 */
	public boolean refundmentSuccess(Long orderId);
	
	/**
	 * 通知业务系统退款失败
	 * @param orderId
	 * @return
	 */
	public boolean refundmentFailed(Long orderId);
}
