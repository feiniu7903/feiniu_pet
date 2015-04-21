package com.lvmama.comm.pet.service.pay;


import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.vo.RefundmentToBankInfo;

public interface BankRefundmentService {
	
	/**
	 * 退款的处理.
	 * @param info
	 * @return
	 */
	BankReturnInfo refund(final RefundmentToBankInfo info);
	
}
