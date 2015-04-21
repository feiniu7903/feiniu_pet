package com.lvmama.comm.bee.po.prod;

import com.lvmama.comm.vo.Constant;

public class ProdOther  extends ProdProduct {
	private static final long serialVersionUID = -5645310160538785106L;
	private Long otherId;
	private String visaType;
	private String visaSelfSign;
    private Integer visaMaterialAheadDay;
    private String country;
    private String city;
	private String visaValidTime;
    public String getVisaType() {
		return visaType;
	}

	public void setVisaType(String visaType) {
		this.visaType = visaType;
	}

	public String getVisaSelfSign() {
		return visaSelfSign;
	}

	public void setVisaSelfSign(String visaSelfSign) {
		this.visaSelfSign = visaSelfSign;
	}

	public Integer getVisaMaterialAheadDay() {
		return visaMaterialAheadDay;
	}

	public void setVisaMaterialAheadDay(Integer visaMaterialAheadDay) {
		this.visaMaterialAheadDay = visaMaterialAheadDay;
	}

    public Long getOtherId() {
        return otherId;
    }

    public void setOtherId(Long otherId) {
        this.otherId = otherId;
    }

    public String getProductType() {
		return Constant.PRODUCT_TYPE.OTHER.name();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getVisaValidTime() {
		return visaValidTime;
	}

	public void setVisaValidTime(String visaValidTime) {
		this.visaValidTime = visaValidTime;
	}
}