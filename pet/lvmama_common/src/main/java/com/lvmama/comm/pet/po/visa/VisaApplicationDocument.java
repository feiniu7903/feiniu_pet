package com.lvmama.comm.pet.po.visa;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.vo.Constant;

public class VisaApplicationDocument implements Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 6693788586308943671L;
	/**
	 * 标识
	 */
	private Long documentId;
	/**
	 * 国家
	 */
	private String country;
	/**
	 * 签证类型
	 */
	private String visaType;
	/**
	 * 出签城市
	 */
	private String city;
	/**
	 * 人群
	 */
	private String occupation;
	/**
	 * 创建日期
	 */
	private Date createTime;
	
	public String getCnVisaType() {
		if (null != visaType) {
			return Constant.VISA_TYPE.BUSINESS_VISA.getCnName(visaType);
		} else {
			return "";
		}
	}
	
	public String getCnCity() {
		if (null != city) {
			return Constant.VISA_CITY.SH_VISA_CITY.getCnName(city);
		} else {
			return "";
		}		
	}
	
	public String getCnOccupation() {
		if (null != occupation) {
			return Constant.VISA_OCCUPATION.VISA_FOR_EMPLOYEE.getCnName(occupation);
		} else {
			return "";
		}		
	}
	
	
	
	public Long getDocumentId() {
		return documentId;
	}
	public void setDocumentId(Long documentId) {
		this.documentId = documentId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getVisaType() {
		return visaType;
	}
	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}	
}
