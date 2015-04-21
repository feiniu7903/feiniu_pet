package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientGroupOn implements Serializable{

	private static final long serialVersionUID = 419000676734589864L;
	
	private Long groupOnId;
	
	private String image;
	
	private String smsContent;

	public String getImage() {
		return "http://pic.lvmama.com"+image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public Long getGroupOnId() {
		return groupOnId;
	}

	public void setGroupOnId(Long groupOnId) {
		this.groupOnId = groupOnId;
	}
}
