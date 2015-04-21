package com.lvmama.comm.pet.po.ins;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CERTIFICATE_TYPE;

public class InsInsurant implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2061761318902046749L;

	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	private Long insurantId;
	private Long policyId;
	private Long orderId;
	private String name;
	private String sex;
	private Date birthday;
	private String certType;
	private String certNo;
	private String mobileNumber;
	private String personType;
	
	public Long getInsurantId() {
		return insurantId;
	}
	public void setInsurantId(Long insurantId) {
		this.insurantId = insurantId;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getCertNo() {
		return certNo;
	}
	public void setCertNo(String certNo) {
		this.certNo = certNo;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}	
	public String getPersonType() {
		return personType;
	}
	public void setPersonType(String personType) {
		this.personType = personType;
	}
	
	
	public String getCertTypeCode() {
		if (Constant.CERTIFICATE_TYPE.ID_CARD.name().equals(this.certType)) {
			return "01";
		}
		if (Constant.CERTIFICATE_TYPE.HUZHAO.name().equals(this.certType)) {
			return "02";
		}
		if (Constant.CERTIFICATE_TYPE.OFFICERS_IDENTITY_CARD.name().equals(this.certType)) {
			return "03";
		}
		if (Constant.CERTIFICATE_TYPE.HOME_GOING_CARD.name().equals(this.certType)) {
			return "06";
		}
		return "99";
	}
	
	public String getFormatBirthday() {
		if (null != birthday) {
			return sdf.format(birthday);
		} else {
			return "";
		}
	}
	
	public String getChPersonType() {
		if (Constant.POLICY_PERSON.APPLICANT.name().equalsIgnoreCase(personType)) {
			return "投保人";
		} 
		if (Constant.POLICY_PERSON.INSURANT.name().equalsIgnoreCase(personType)) {
			return "被保险人";
		} 
		return "";
	}
	
	public String getChCertType() {
		for (CERTIFICATE_TYPE type : CERTIFICATE_TYPE.values()) {
			if (type.name().equals(certType)) {
				return type.getCnName();
			}
		}
		return "";
	}
	
	public String getChSex() {
		if (Constant.SEX_CODE.MALE.getCode().equalsIgnoreCase(sex)) {
			return Constant.SEX_CODE.MALE.getChName();
		}
		if (Constant.SEX_CODE.FEMALE.getCode().equalsIgnoreCase(sex)) {
			return Constant.SEX_CODE.FEMALE.getChName();
		}
		return "";
	}
	
}
