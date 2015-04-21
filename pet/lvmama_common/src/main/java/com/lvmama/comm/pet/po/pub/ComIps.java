/**
 * 
 */
package com.lvmama.comm.pet.po.pub;

import java.io.Serializable;

/**
 * 
 * IP库所有
 * 
 * @author 张振华
 * 
 */
public class ComIps implements Serializable {
	
	private static final long serialVersionUID = -7985247225908109105L;

	private Long ipStart;
	private Long ipEnd;
	private String provinceId;
	private String provinceName;
	private String cityId;
	private String cityName;
	private String areaLocation;
	private Long placeId;

	public Long getIpStart() {
		return ipStart;
	}

	public void setIpStart(Long ipStart) {
		this.ipStart = ipStart;
	}

	public Long getIpEnd() {
		return ipEnd;
	}

	public void setIpEnd(Long ipEnd) {
		this.ipEnd = ipEnd;
	}
	
	public String getCapitalId() {
		return provinceId;
	}
	
	public void setCapitalId(String capitalId) {
		this.provinceId = capitalId;
	}
	
	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getProvinceName() {
		return provinceName;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCapitalName() {
		return provinceName;
	}

	public void setCapitalName(String provinceName) {
		this.provinceName = provinceName;
	}
	
	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getAreaLocation() {
		return areaLocation;
	}

	public void setAreaLocation(String areaLocation) {
		this.areaLocation = areaLocation;
	}

	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
	@Override
	public boolean equals(Object o){ 
		if(o instanceof ComIps){
			ComIps cip = (ComIps)o;
			if(cip.getIpEnd() == this.ipEnd && cip.getIpStart() == this.ipStart){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	} 
}
