package com.lvmama.pet.job;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.PayPaymentNotifier;

public class PaymentSuccessNotifyJob implements Runnable  {
	
	protected final Log LOG =LogFactory.getLog(this.getClass());
	
	private PayPaymentService payPaymentService;
	
	private PayPaymentNotifier payPaymentNotifier;
	
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			try{
				List<PayPayment> list = payPaymentService.selectUnNotifiedPayment();
				LOG.info("PaymentSuccessNotifyJob listSize:"+list.size());
				for (PayPayment payment : list) {
					LOG.info(StringUtil.printParam(payment));
					payPaymentNotifier.notify(payment);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public void setPayPaymentNotifier(PayPaymentNotifier payPaymentNotifier) {
		this.payPaymentNotifier = payPaymentNotifier;
	}

}
