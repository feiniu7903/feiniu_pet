package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientFeedBack implements Serializable{

	private static final long serialVersionUID = 7457408138733157413L;
	
	private String content;
	
	private String email;
	
	private String userId;
	
	private String channel;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
