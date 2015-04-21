package com.lvmama.back.web.utils.insurance.pinan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.vo.Constant.POLICY_PERSON;

public class SubjectInfo implements PinAnRequest {
	private static SimpleDateFormat DateSDF = new SimpleDateFormat("yyyy-MM-dd");

	private Date beginTime;
	private Date endTime;
	private String insuranceDay;
	private double totalPremium;
	private String productCode;
	private List<InsInsurant> insurants;
	
	public SubjectInfo(Date beginTime, Date endTime, String insuranceDay, double totalPremium, String productCode, List<InsInsurant> insurants) {
		this.beginTime = beginTime;
		this.endTime = endTime;
		this.insuranceDay = insuranceDay;
		this.totalPremium = totalPremium;
		this.productCode = productCode;
		this.insurants = insurants;
	}

	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<subjectInfo>");
		buffer.append("<subjectInfo>");
		buffer.append("<certType>" + ((insurants.size() - 1) == 1 ? 0 : 1)
				+ "</certType>");
		buffer.append("<applyPersonnelNum>" + (insurants.size() - 1)
				+ "</applyPersonnelNum>");
		buffer.append("<namedFlag>Y</namedFlag>");
		buffer.append("<subjectId>1</subjectId>");
		buffer.append("<subjectName>layer1</subjectName>");
		buffer.append("<insuranceBeginTime>" + DateSDF.format(beginTime)
				+ "</insuranceBeginTime>");
		buffer.append("<insuranceEndTime>" + DateSDF.format(endTime)
				+ "</insuranceEndTime>");
		buffer.append("<insuranceMonth>0</insuranceMonth>");
		buffer.append("<insuranceDay>" + insuranceDay + "</insuranceDay>");
		buffer.append("<totalStandardPremium>" + totalPremium
				+ "</totalStandardPremium>");
		buffer.append("<totalModalPremium>" + totalPremium
				+ "</totalModalPremium>");
		buffer.append("<discountProportion>1</discountProportion>");
		buffer.append("<averageModalPremium>" + totalPremium / (insurants.size() - 1)
				+ "</averageModalPremium>");
		buffer.append("<productInfo>");
		buffer.append("<productInfo>");
		buffer.append("<productCode>" + productCode + "</productCode>");
		//buffer.append("<applyNum>" + (insurants.size() - 1) + "</applyNum>");
		buffer.append("<applyNum>1</applyNum>");
		buffer.append("<discountProportion>1</discountProportion>");
		buffer.append("<insuranceMonth>0</insuranceMonth>");
		buffer.append("<insuranceDay>" + insuranceDay + "</insuranceDay>");
		buffer.append("<totalModalPremium>" + totalPremium
				+ "</totalModalPremium>");
		buffer.append("<totalStandardPremium>" + totalPremium
				+ "</totalStandardPremium>");
		buffer.append("<effectiveDate>" + DateSDF.format(beginTime)
				+ "</effectiveDate>");
		buffer.append("<invalidateDate>" + DateSDF.format(endTime)
				+ "</invalidateDate>");
		buffer.append("</productInfo>");
		buffer.append("</productInfo>");
		buffer.append("<planInfo>");
		buffer.append("<planInfo>");
		buffer.append("<planCode></planCode>");
		buffer.append("<applyNum></applyNum>");
		buffer.append("<totalAmount></totalAmount>");
		buffer.append("<discountProportion></discountProportion>");
		buffer.append("<totalStandardPremium></totalStandardPremium>");
		buffer.append("<totalModalPremium></totalModalPremium>");
		buffer.append("<premiumCalculateDirection></premiumCalculateDirection>");
		buffer.append("<planLevel/>");
		buffer.append("<applyMonth/>");
		buffer.append("<applyDay></applyDay>");
		buffer.append("<effectiveDate></effectiveDate>");
		buffer.append("<invalidateDate></invalidateDate>");
		buffer.append("<dutyInfo>");
		buffer.append("<dutyInfo>");
		buffer.append("<dutyCode></dutyCode>");
		buffer.append("<totalStandardPremium></totalStandardPremium>");
		buffer.append("<totalModalPremium></totalModalPremium>");
		buffer.append("<discountProportion></discountProportion>");
		buffer.append("<applyNum></applyNum>");
		buffer.append("<dutyAount></dutyAount>");
		buffer.append("<applyMonth/>");
		buffer.append("<applyDay></applyDay>");
		buffer.append("<effectiveDate></effectiveDate>");
		buffer.append("<invalidateDate></invalidateDate>");
		buffer.append("</dutyInfo>");
		buffer.append("</dutyInfo>");
		buffer.append("</planInfo>");
		buffer.append("</planInfo>");
		buffer.append("<insurantInfo>");
		for (InsInsurant insurant : insurants) {
			if (POLICY_PERSON.INSURANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				buffer.append("<insurantInfo>");
				buffer.append("<personnelAttribute>100</personnelAttribute>");
				buffer.append("<effectiveDate>" + DateSDF.format(beginTime) + "</effectiveDate>");
				buffer.append("<invalidateDate>" + DateSDF.format(endTime) + "</invalidateDate>");
				buffer.append("<destinationCountry/>");
				buffer.append("<overseasOccupation/>");
				buffer.append("<schoolOrCompany/>");
				buffer.append("<outgoingPurpose/>");
				buffer.append("<personnelType>1</personnelType>");
				buffer.append("<personnelName>" + insurant.getName() + "</personnelName>");
				buffer.append("<sexCode>" + insurant.getSex() + "</sexCode>");
				buffer.append("<certificateType>" + insurant.getCertTypeCode() + "</certificateType>");
				buffer.append("<certificateNo>" + insurant.getCertNo() + "</certificateNo>");
				buffer.append("<birthday>" + DateSDF.format(insurant.getBirthday()) + "</birthday>");
				buffer.append("<bankCode/>");
				buffer.append("<bancAccount/>");
				buffer.append("<bankCodeDetail/>");
				buffer.append("<bankProvince/>");
				buffer.append("<bankCity/>");
				buffer.append("<bankAccountType/>");
				buffer.append("<bankPayInfo/>");
				buffer.append("<mobileTelephone></mobileTelephone>");
				buffer.append("<homeAreaCode></homeAreaCode>");
				buffer.append("<homeTelephone></homeTelephone>");
				buffer.append("<officeAreaCode></officeAreaCode>");
				buffer.append("<officeTelephone></officeTelephone>");
				buffer.append("<address></address>");
				buffer.append("<postCode></postCode>");
				buffer.append("<email></email>");
				buffer.append("</insurantInfo>");
			}
		}

		buffer.append("</insurantInfo>");
		buffer.append("</subjectInfo>");
		buffer.append("</subjectInfo>");
		return buffer.toString();
	}
}
