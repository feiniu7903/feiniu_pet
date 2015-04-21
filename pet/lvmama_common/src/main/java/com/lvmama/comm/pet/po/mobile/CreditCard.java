package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

/**
 * 用户信用卡
 * 
 * @author likun
 * @date 2014/3/26
 * 
 */
public class CreditCard implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 信用卡编号，主键，自动增长
	 */
	private Long creditCardId;
	/**
	 * 用户编号
	 */
	private String userNo;
	/** 信用卡信息 **/
	private String cardNo;
	/** 信用卡验证码 **/
	private String cvv;
	/** 信用卡有效年份 **/
	private Long expirationYear;
	/** 信用卡有效月份 **/
	private Long expirationMonth;
	/** 持卡人名称 **/
	private String holderName;
	/** 证件类型 **/
	private String idType;
	/** 证件号码 **/
	private String idNo;
	/** 是否保存信用卡信息 **/
	private boolean saveCardFlag;

	public Long getCreditCardId() {
		return creditCardId;
	}

	public void setCreditCardId(Long creditCardId) {
		this.creditCardId = creditCardId;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCvv() {
		return cvv;
	}

	public void setCvv(String cvv) {
		this.cvv = cvv;
	}

	public Long getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(Long expirationYear) {
		this.expirationYear = expirationYear;
	}

	public Long getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(Long expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public String getHolderName() {
		return holderName;
	}

	public void setHolderName(String holderName) {
		this.holderName = holderName;
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

	public boolean isSaveCardFlag() {
		return saveCardFlag;
	}

	public void setSaveCardFlag(boolean saveCardFlag) {
		this.saveCardFlag = saveCardFlag;
	}

}
