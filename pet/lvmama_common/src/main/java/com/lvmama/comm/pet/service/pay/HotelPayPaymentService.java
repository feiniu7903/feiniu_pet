package com.lvmama.comm.pet.service.pay;

import com.lvmama.comm.pet.po.pay.PayPayment;

/**
 * 海外酒店订单支付成功后的处理.
 * @author liwenzhan
 *
 */
public interface HotelPayPaymentService {

	/**
	 * 支付成功更新订单
	 * @param payPayment
	 * @return
	 */
	boolean paymentSuccess(PayPayment payPayment);
	
}
