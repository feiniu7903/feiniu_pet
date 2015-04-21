package com.lvmama.back.web.utils.insurance.pinan;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.vo.Constant;

public class AhsPolicy implements PinAnRequest {
	private PolicySaleInfo policySaleInfo;
	private PolicyPayInfo policyPayInfo;
	private PolicyBaseInfo policyBaseInfo;
	private PolicyExtendInfo policyExtendInfo;
	private InsuranceApplicantInfo insuranceApplicantInfo;
	private SubjectInfo subjectInfo;
	
	public AhsPolicy(double totalPremium, Date insuranceBeginTime, Date insuranceEndTime, String applyTime,String productCode, List<InsInsurant> insurants) {
		this.policySaleInfo = new PolicySaleInfo();
		this.policyPayInfo = new PolicyPayInfo();
		this.policyBaseInfo = new PolicyBaseInfo(insurants.size() - 1, totalPremium, insuranceBeginTime, insuranceEndTime);
		this.policyExtendInfo = new PolicyExtendInfo();
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.APPLICANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				this.insuranceApplicantInfo = new InsuranceApplicantInfo(insurant.getName(), insurant.getSex(), insurant.getCertTypeCode(), insurant.getCertNo(), insurant.getBirthday());
				break;
			}
		}
		if (null == insuranceApplicantInfo) {
			InsInsurant insurant = insurants.get(0);
			this.insuranceApplicantInfo = new InsuranceApplicantInfo(insurant.getName(), insurant.getSex(), insurant.getCertTypeCode(), insurant.getCertNo(), insurant.getBirthday());
		}
		
		this.subjectInfo = new SubjectInfo(insuranceBeginTime, insuranceEndTime, applyTime, totalPremium, productCode, insurants);
	}

	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<ahsPolicy>");
		buffer.append(policySaleInfo.toXMLString());
		buffer.append(policyPayInfo.toXMLString());
		buffer.append(policyBaseInfo.toXMLString());
		buffer.append(policyExtendInfo.toXMLString());
		buffer.append(insuranceApplicantInfo.toXMLString());
		buffer.append(subjectInfo.toXMLString());
		buffer.append("</ahsPolicy>");
		return buffer.toString();
	}
	


}
