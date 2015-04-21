package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientNotice implements Serializable{

	private static final long serialVersionUID = -2717628750294362125L;
	
	private String title;
	
	private String content;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
