package com.lvmama.comm.pet.service.pay;


import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;

public interface PaymentQueryService {
	
	/**
	 * 支付状态查询
	 * @param info
	 * @return
	 */
	PaymentQueryReturnInfo paymentStateQuery(final PayPayment info);
	
}
