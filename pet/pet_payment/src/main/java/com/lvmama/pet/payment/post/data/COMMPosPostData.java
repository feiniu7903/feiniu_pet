package com.lvmama.pet.payment.post.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.pay.PayPayment;

public class COMMPosPostData implements PostData {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(COMMPosPostData.class);

	private String paymentTradeNo;

	public COMMPosPostData(PayPayment payment) {
		this.paymentTradeNo = payment.getPaymentTradeNo();
	}

	/**
	 * 获取支付对账流水号.
	 * @return
	 */
	@Override
	public String getPaymentTradeNo() {
		LOG.info("paymentTradeNo = " + paymentTradeNo);
		return this.paymentTradeNo;
	}

	@Override
	public String signature() {
		return null;
	}

}
