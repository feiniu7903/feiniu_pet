package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientCity implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8468964327154298956L;
	private String cityId;
	private String cityName;
    private String cityPic;
    
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
	public String getCityPic() {
		return cityPic;
	}
	public void setCityPic(String cityPic) {
		this.cityPic = cityPic;
	}

    
}
