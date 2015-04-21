package com.lvmama.back.web.utils.insurance.pinan;

public class PolicyInfo implements PinAnRequest {
	private String policyNo;
	private String validateCode;
	
	public PolicyInfo(String policyNo, String validateCode) {
		this.policyNo = policyNo;
		this.validateCode = validateCode;
	}
	
	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<policyInfo>");
		buffer.append("<policyInfo>");
		buffer.append("<policyNo>" + policyNo + "</policyNo>");
		buffer.append("<agentCode>02000608</agentCode>");
		buffer.append("<validateCode>" + validateCode + "</validateCode>");
		buffer.append("<partnerName>LVMAMA</partnerName>");
		buffer.append("<partnerSystemSeriesNo/>");
		buffer.append("</policyInfo>");
		buffer.append("</policyInfo>");
		return buffer.toString();
	}

}
