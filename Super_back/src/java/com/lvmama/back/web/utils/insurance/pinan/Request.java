package com.lvmama.back.web.utils.insurance.pinan;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.ins.InsInsurant;


public class Request implements PinAnRequest {
	private AhsPolicy ahsPolicy;
	private PolicyInfo policyInfo;
	
	public Request(double totalPremium, Date insuranceBeginTime, Date insuranceEndTime, String applyTime, String productCode, List<InsInsurant> insurants) {
		ahsPolicy = new AhsPolicy(totalPremium, insuranceBeginTime, insuranceEndTime, applyTime, productCode, insurants);
	}
	
	public Request(String policyNo, String validateCode) {
		policyInfo = new PolicyInfo(policyNo, validateCode);
	}
	
	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<Request>");
		if (null != ahsPolicy) {
			buffer.append(ahsPolicy.toXMLString());
		}
		if (null != policyInfo) {
			buffer.append(policyInfo.toXMLString());
		}
		buffer.append("</Request>");
		return buffer.toString();
	}

}
