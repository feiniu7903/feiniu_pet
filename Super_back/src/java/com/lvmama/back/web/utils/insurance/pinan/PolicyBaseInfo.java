package com.lvmama.back.web.utils.insurance.pinan;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PolicyBaseInfo implements PinAnRequest {
	private static SimpleDateFormat DateSDF = new SimpleDateFormat("yyyy-MM-dd");
	
	private int personnelNum;
	private double totalPremium;
	private Date beginTime;
	private Date endTime;
	
	public PolicyBaseInfo(int personnelNum, double totalPremium, Date insuranceBeginTime, Date insuranceEndTime) {
		this.personnelNum = personnelNum;
		this.totalPremium = totalPremium;
		this.beginTime = insuranceBeginTime;
		this.endTime = insuranceEndTime;
	}
	
	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<policyBaseInfo>");
		buffer.append("<createdBy>LVMAMA</createdBy>");
		buffer.append("<applyPersonnelNum>" + personnelNum + "</applyPersonnelNum>");
		buffer.append("<totalPersonnelNum>" + personnelNum + "</totalPersonnelNum>");
		buffer.append("<relationshipWithInsured>9</relationshipWithInsured>");
		buffer.append("<totalStandardPremium>" + totalPremium + "</totalStandardPremium>");
		buffer.append("<totalModalPremium>" + totalPremium + "</totalModalPremium>");
		buffer.append("<currecyCode>01</currecyCode>");
		buffer.append("<insuranceBeginTime>" + DateSDF.format(beginTime) + "</insuranceBeginTime>");
		buffer.append("<insuranceEndTime>" + DateSDF.format(endTime) + "</insuranceEndTime>");
		buffer.append("<businessType>1</businessType>");
		buffer.append("<applyTime>" + DateSDF.format(new Date()) + "</applyTime>");
		buffer.append("<departmentCode>2020314</departmentCode>");
		buffer.append("<commissionChargeProportion>0.4</commissionChargeProportion>");
		buffer.append("<agentCode>02000608</agentCode>");
		buffer.append("<agentAgreementNo>0200060810002</agentAgreementNo>");
		buffer.append("<supplementAgreementNo>0</supplementAgreementNo>");
		buffer.append("</policyBaseInfo>");
		return buffer.toString();
	}

}
