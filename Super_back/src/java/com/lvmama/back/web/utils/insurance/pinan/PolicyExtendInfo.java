package com.lvmama.back.web.utils.insurance.pinan;

public class PolicyExtendInfo implements PinAnRequest {

	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<policyExtendInfo>");
		buffer.append("<businessKind>8</businessKind>");
		buffer.append("<partnerName>LVMAMA</partnerName>");
		buffer.append("<partnerSystemSeriesNo>" + System.currentTimeMillis() * 1000 + (int) Math.ceil((Math.random() * 1000)) + "</partnerSystemSeriesNo>");
		buffer.append("</policyExtendInfo>");
		return buffer.toString();
	}
}
