package com.lvmama.pet.refundment.service.impl;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 储值卡的原路退回 .
 * @author zhangjie
 *
 */
public class LvmamaCardRefundServiceImpl implements BankRefundmentService{

	private LvmamacardService lvmamacardService;
	
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		return lvmamacardService.refund2CardAccount(info.getPayRefundmentId(),info.getPaymentId(),info.getRefundAmount(), info.getPaymentTradeNo(),"SYSTEM");
	}

	public LvmamacardService getLvmamacardService() {
		return lvmamacardService;
	}

	public void setLvmamacardService(LvmamacardService lvmamacardService) {
		this.lvmamacardService = lvmamacardService;
	}
	
	
}
