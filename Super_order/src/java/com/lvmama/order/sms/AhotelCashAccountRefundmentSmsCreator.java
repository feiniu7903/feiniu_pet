package com.lvmama.order.sms;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class AhotelCashAccountRefundmentSmsCreator extends AbstractSmsCreator
		implements SingleSmsCreator {

	private Long refundmentId;
	private Long amount;
	
	public AhotelCashAccountRefundmentSmsCreator(Long refundmentId, String mobile,Long amount) {
		this.refundmentId = refundmentId;
		this.mobile = mobile;
		this.amount = amount;
	}
	
	@Override
	Map<String, Object> getContentData() {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("cash", PriceUtil.convertToYuan(amount));
		return data;
	}
	
	@Override
	String getSmsTemplateId() {
		return Constant.SMS_TEMPLATE.AHOTEL_CASH_ACCOUNT_REFUNDMENT.name();
	}

}
