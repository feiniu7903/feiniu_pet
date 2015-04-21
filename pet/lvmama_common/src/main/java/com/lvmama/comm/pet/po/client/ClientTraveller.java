package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientTraveller implements Serializable{

	private static final long serialVersionUID = -6038322545906598292L;

	private String travellertitle;
	
	private String name;
	
	private String mobile;



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTravellertitle() {
		return travellertitle;
	}

	public void setTravellertitle(String travellertitle) {
		this.travellertitle = travellertitle;
	}

}