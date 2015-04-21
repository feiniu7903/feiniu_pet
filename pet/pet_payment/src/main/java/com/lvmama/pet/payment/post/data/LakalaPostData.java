package com.lvmama.pet.payment.post.data;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.PriceUtil;

public class LakalaPostData implements PostData {

	private String paymentTradeNo;
	
	private String amount;
	
	public LakalaPostData(PayPayment payment) {
		this.amount = PriceUtil.trans2YuanStr(payment.getAmount());
		this.paymentTradeNo = payment.getPaymentTradeNo();
	}
	
	@Override
	public String signature() {
		return "";
	}

	@Override
	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public String getAmountYuan() {
		return amount;
	}
}
