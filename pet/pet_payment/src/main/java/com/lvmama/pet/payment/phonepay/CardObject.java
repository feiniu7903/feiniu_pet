package com.lvmama.pet.payment.phonepay;

public class CardObject {
	private String cardNo;// 卡号
	private String validDate;// 有效期(有效期 MMyy)
	private String cvv2;// CVV2 码
	private String idType;// 00－身份证（通过沟通直接写00
	private String idNo;// 卡对应的身份证号码
	private String name;// 持卡人姓名

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getValidDate() {
		return validDate;
	}

	public void setValidDate(String validDate) {
		this.validDate = validDate;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getIdType() {
		return idType;
	}

	public void setIdType(String idType) {
		this.idType = idType;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}