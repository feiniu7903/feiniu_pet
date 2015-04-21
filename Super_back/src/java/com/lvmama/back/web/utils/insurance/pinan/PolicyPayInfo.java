package com.lvmama.back.web.utils.insurance.pinan;

public class PolicyPayInfo implements PinAnRequest {

	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<policyPayInfo>");
		buffer.append("<paymentPath/>");
		buffer.append("<totalModalPremium/>");
		buffer.append("<currecyCode/>");
		buffer.append("<paymentPersonName/>");
		buffer.append("<certificateType/>");
		buffer.append("<certificateNo/>");
		buffer.append("<bankCode/>");
		buffer.append("<openingBank/>");
		buffer.append("<bankAccountNo/>");
		buffer.append("<bankAttribute/>");
		buffer.append("<paymentDate/>");
		buffer.append("<cardValid/>");
		buffer.append("<cardType/>");
		buffer.append("<bankOrderNo/>");
		buffer.append("<bankTradeDate/>");
		buffer.append("<reinforceFlag/>");
		buffer.append("<paymentBeginDate/>");
		buffer.append("<paymentEndDate/>");
		buffer.append("</policyPayInfo>");
		return buffer.toString();
	}

}
