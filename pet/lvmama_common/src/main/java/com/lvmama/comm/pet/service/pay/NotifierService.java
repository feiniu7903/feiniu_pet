package com.lvmama.comm.pet.service.pay;

import java.util.List;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;

/**
 * 此接口定义了业务系统（BEE、ANT）需要实现的支付成功更新业务
 * @author Alex Wang
 */
public interface NotifierService {

	/**
	 * 支付成功更新订单
	 * @param payPayment
	 * @return
	 */
	boolean paymentSuccess(PayPayment payPayment);
	
	/**
	 * 退款成功更新业务系统
	 * @param payRefundment
	 * @return
	 */
	boolean refundSuccess(PayPaymentRefundment payRefundment);
	
	/**
	 * 支付转移成功更新业务系统
	 * @param payPayments 被转移的支付记录
	 * @return
	 */
	boolean transferPaymentSuccess(List<PayPayment> payPayments);

}
