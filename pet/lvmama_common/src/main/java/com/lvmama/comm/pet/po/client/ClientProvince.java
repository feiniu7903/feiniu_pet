package com.lvmama.comm.pet.po.client;

import java.io.Serializable;
import java.util.List;

public class ClientProvince implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2344523244911312407L;
	private String provinceId;
    private String provinceName;
    private List<ClientCity> clientCity;
    
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
	public List<ClientCity> getClientCity() {
		return clientCity;
	}
	public void setClientCity(List<ClientCity> clientCity) {
		this.clientCity = clientCity;
	}



}
