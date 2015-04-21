package com.lvmama.pet.refundment.service.impl;

import com.lvmama.comm.pet.po.pay.BankReturnInfo;
import com.lvmama.comm.pet.service.money.StoredCardService;
import com.lvmama.comm.pet.service.pay.BankRefundmentService;
import com.lvmama.comm.vo.RefundmentToBankInfo;

/**
 * 储值卡的原路退回 .
 * @author liwenzhan是高手
 *
 */
public class StoredCardRefundServiceImpl implements BankRefundmentService{

	private StoredCardService storedCardService;
	@Override
	public BankReturnInfo refund(RefundmentToBankInfo info) {
		return storedCardService.refund2CardAccount(info.getPayRefundmentId(),info.getPaymentId(),info.getRefundAmount(), info.getPaymentTradeNo(),"SYSTEM");
	}
	public StoredCardService getStoredCardService() {
		return storedCardService;
	}
	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}
}
