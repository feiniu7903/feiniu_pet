package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CERT_TYPE;
import com.lvmama.comm.vo.Constant.CERT_TYPE_2;

public class OrdPerson implements Serializable {
	private String email;

    private Long personId;
    
    private String province;//省
    private String city;//市

    private String name;

    private String tel;

    private String mobile;

    private String qq;

    private String address;

    private String postcode;

    private String memo;

    private String fax;

    private String faxTo;

    private Long objectId;

    private String objectType;

    private String personType;

    private String certNo;

    private String certType;
    
    private String receiverId;
    
    //出生年月
    private Date brithday;
    
    private String needInsure;
    
    private String gender;
    
    private String pinyin;

    public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String getZhGender() {
		if(StringUtils.isNotEmpty(this.gender)) {
			return "F".equalsIgnoreCase(this.gender) ? "女" : "男";
		}
		return "";
	}

	public String getNeedInsure() {
		return needInsure;
	}

	public void setNeedInsure(String needInsure) {
		this.needInsure = needInsure;
	}

	public Date getBrithday() {
		return brithday;
	}
	
	public String getZhBrithday() {
		return DateUtil.getFormatDate(brithday, "yyyy-MM-dd");
	}

	public void setBrithday(Date brithday) {
		this.brithday = brithday;
	}

	public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode == null ? null : postcode.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax == null ? null : fax.trim();
    }

    public String getFaxTo() {
        return faxTo;
    }

    public void setFaxTo(String faxTo) {
        this.faxTo = faxTo == null ? null : faxTo.trim();
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public String getPersonType() {
        return personType;
    }

    public void setPersonType(String personType) {
        this.personType = personType == null ? null : personType.trim();
    }

    public String getCertNo() {
        return certNo;
    }

    public void setCertNo(String certNo) {
        this.certNo = certNo == null ? null : certNo.trim();
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType == null ? null : certType.trim();
    }

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getReceiverId() {
		return receiverId;
	}

	public void setReceiverId(String receiverId) {
		this.receiverId = receiverId;
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
	
	public String getPinyin() {
		return pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	public String getFullAddress(){
		StringBuffer sb=new StringBuffer();
		if(StringUtils.isNotEmpty(province)){
			sb.append(province);
		}
		if(StringUtils.isNotEmpty(city)){
			sb.append(city);
		}
		if(StringUtils.isNotEmpty(address)){
			sb.append(address);
		}
		
		return sb.toString();
	}

	public String getZhCertType(){
		if(this.certType!=null){
			return Constant.CERT_TYPE.getCnName(certType);
		}
		return "";
	}
	
	public String getCatalog(){
		return CERT_TYPE_2.getCnName(certType);
	}
}