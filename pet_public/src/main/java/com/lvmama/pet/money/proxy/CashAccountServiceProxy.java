package com.lvmama.pet.money.proxy;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentRefundment;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.pay.NotifierService;
import com.lvmama.comm.utils.SynchronizedLock;

public class CashAccountServiceProxy implements NotifierService{
	private Log LOG = LogFactory.getLog(getClass());
	private CashAccountService cashAccountService;
	@Override
	public boolean paymentSuccess(PayPayment payPayment) {
		String key = "PAYMENT_CALL_BACK_ACTION_" + payPayment.getPaymentTradeNo();
		if (SynchronizedLock.isOnDoingMemCached(key)) {
			LOG.error("ERROR: key is exists! key="+key);
			return false;
		}
		try{
			boolean update = cashAccountService.rechargeToCashAccount(payPayment.getObjectId(), payPayment.getAmount());
			return update;
		}finally{
			SynchronizedLock.releaseMemCached(key);
		}
	}
	
	@Override
	public boolean refundSuccess(PayPaymentRefundment payRefundment) {
		return false;
	}
	
	@Override
	public boolean transferPaymentSuccess(List<PayPayment> payPayments) {
		return false;
	}	

	public CashAccountService getCashAccountService() {
		return cashAccountService;
	}
	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}
}
