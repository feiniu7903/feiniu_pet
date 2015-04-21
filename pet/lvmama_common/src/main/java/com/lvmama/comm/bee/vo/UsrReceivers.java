package com.lvmama.comm.bee.vo;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class UsrReceivers implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4364597827265711756L;
	/**
	 * 是否有效:有效.
	 */
	public static final String VALID = "Y";
	/**
	 * 是否有效:无效.
	 */
	public static final String INVALID = "N";
    private String receiverId;

    private String userId;
    
    private String receiverName;

    private String mobileNumber;

    private Date createdDate;

    private String isValid;

    private String isMobileChecked;

    private String address;

    private String postCode;

    private String isTheOwner;

    private String cardType;

    private String cardNum;
    
    private String useOffen;
    private String phone;
    private String email;
    private String fax;
    private String faxContactor;
    private String receiversType;
    
    private String province;
    private String city;
    private Date brithday;
    
    private String gender;
    private String pinyin;
    
    private String receiverJsonData;
    
    public Date getBrithday() {
		return brithday;
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}
	public String getZhBrithday() {
		return DateUtil.getFormatDate(brithday, "yyyy-MM-dd");
	}
	public String getReceiversType() {
		return receiversType;
	}

	public void setReceiversType(String receiversType) {
		this.receiversType = receiversType;
	}

	public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId == null ? null : receiverId.trim();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId == null ? null : userId.trim();
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName == null ? null : receiverName.trim();
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber == null ? null : mobileNumber.trim();
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }

    public String getIsMobileChecked() {
        return isMobileChecked;
    }

    public void setIsMobileChecked(String isMobileChecked) {
        this.isMobileChecked = isMobileChecked == null ? null : isMobileChecked.trim();
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode == null ? null : postCode.trim();
    }

    public String getIsTheOwner() {
        return isTheOwner;
    }

    public void setIsTheOwner(String isTheOwner) {
        this.isTheOwner = isTheOwner == null ? null : isTheOwner.trim();
    }

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType == null ? null : cardType.trim();
    }
    
    public String getZhCardType() {
    	return Constant.CERT_TYPE.getCnName(cardType);
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum == null ? null : cardNum.trim();
    }

	public String getUseOffen() {
		return useOffen;
	}

	public void setUseOffen(String useOffen) {
		this.useOffen = useOffen;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getFaxContactor() {
		return faxContactor;
	}

	public void setFaxContactor(String faxContactor) {
		this.faxContactor = faxContactor;
	}

	/**
	 * @return the province
	 */
	public String getProvince() {
		return province;
	}

	/**
	 * @param province the province to set
	 */
	public void setProvince(String province) {
		this.province = province;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getZhGender() {
		if(StringUtils.isNotEmpty(this.gender)){
			return "M".equals(this.gender) ? "男" : "女";
		}
    	return "";
    }

	public String getReceiverJsonData() {
		return receiverJsonData;
	}

	public void setReceiverJsonData(String receiverJsonData) {
		this.receiverJsonData = receiverJsonData;
	}
	
	public boolean isCertIsNull() {
		if(StringUtil.isEmptyString(getCardType())||StringUtil.isEmptyString(getCardNum())){
			return true;
		}
		return false;
	}
	
	
	public String getReceiverMd5(){
		String content = this.getReceiverName()+(getMobileNumber()==null?"":getMobileNumber())+(getCardType()==null?"":getCardType())
				+(getCardNum()==null?"":getCardNum());
		try {
			return MD5.encode(content);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	public void setBrithday(String brithday) {
		try {
			if (StringUtils.isNotEmpty(brithday))
				this.brithday = DateUtil.getDateByStr(brithday, "yyyy-MM-dd");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}