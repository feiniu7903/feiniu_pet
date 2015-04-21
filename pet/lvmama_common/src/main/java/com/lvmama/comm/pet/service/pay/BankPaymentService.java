package com.lvmama.comm.pet.service.pay;


import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.po.pay.PaymentToBankInfo;

public interface BankPaymentService {


	/**
	 * 支付的处理.
	 * @param info
	 * @return
	 */
	BankReturnInfo pay(final PaymentToBankInfo info);
	
	
	
}
