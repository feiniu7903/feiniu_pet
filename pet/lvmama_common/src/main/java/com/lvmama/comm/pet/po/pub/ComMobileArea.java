/**
 * 
 */
package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 手机号所在地PO
 * @author yangbin
 *
 */
public class ComMobileArea implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4609125587994140538L;
	public String mobileNumber;
	public String cityId;
	public String cityName;
	public String provinceId;
	public String provinceName;
	
	
	
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getProvinceName() {
		return provinceName;
	}
	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	
	
}
