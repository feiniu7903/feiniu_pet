package com.lvmama.back.web.utils.insurance.pinan;

import java.util.Date;

public class InsuranceApplicantInfo extends BasePersonnelInfo implements PinAnRequest {

	public InsuranceApplicantInfo(String name, String sex, String cType, String cNo, Date birthday) {
		super(name,sex,cType,cNo,birthday);
	}
	
	@Override
	public String toXMLString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("<insuranceApplicantInfo>");
		buffer.append("<individualPersonnelInfo>");
		buffer.append("<personnelType>1</personnelType>");
		buffer.append("<personnelName>" + personnelName + "</personnelName>");
		buffer.append("<sexCode>" + sexCode + "</sexCode>");
		buffer.append("<certificateType>" + certificateType + "</certificateType>");
		buffer.append("<certificateNo>" + certificateNo + "</certificateNo>");
		buffer.append("<birthday>" + (null != birthday ? SDF.format(birthday) : "") + "</birthday>");
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
		buffer.append("</individualPersonnelInfo>");
		buffer.append("</insuranceApplicantInfo>");
		return buffer.toString();
	}

}
