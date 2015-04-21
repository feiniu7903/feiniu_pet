package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class UserCreditCard implements Serializable {
	private static final long serialVersionUID = 7780657736185278256L;

	private Long userCreditCardId;

    private String creditCardNo;

    private Date creditCardEffectiveDate;

    private String creditCardCvv;

    private String userId;

    private String creditCardHolder;

    private String identifyCardNo;

    private String identifyCardType;

    public Long getUserCreditCardId() {
        return userCreditCardId;
    }

    public void setUserCreditCardId(Long userCreditCardId) {
        this.userCreditCardId = userCreditCardId;
    }

    public String getCreditCardNo() {
        return creditCardNo;
    }

    public void setCreditCardNo(String creditCardNo) {
        this.creditCardNo = creditCardNo == null ? null : creditCardNo.trim();
    }

    public Date getCreditCardEffectiveDate() {
        return creditCardEffectiveDate;
    }

    public void setCreditCardEffectiveDate(Date creditCardEffectiveDate) {
        this.creditCardEffectiveDate = creditCardEffectiveDate;
    }

    public String getCreditCardCvv() {
        return creditCardCvv;
    }

    public void setCreditCardCvv(String creditCardCvv) {
        this.creditCardCvv = creditCardCvv == null ? null : creditCardCvv.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getCreditCardHolder() {
        return creditCardHolder;
    }

    public void setCreditCardHolder(String creditCardHolder) {
        this.creditCardHolder = creditCardHolder == null ? null : creditCardHolder.trim();
    }

    public String getIdentifyCardNo() {
        return identifyCardNo;
    }

    public void setIdentifyCardNo(String identifyCardNo) {
        this.identifyCardNo = identifyCardNo == null ? null : identifyCardNo.trim();
    }

    public String getIdentifyCardType() {
        return identifyCardType;
    }

    public void setIdentifyCardType(String identifyCardType) {
        this.identifyCardType = identifyCardType == null ? null : identifyCardType.trim();
    }
}