package com.lvmama.comm.pet.po.client;

import java.io.Serializable;

public class ClientComment implements Serializable {

	private static final long serialVersionUID = -6719318154091341907L;

	/**
	 * 发布人姓名
	 * */
	private String publisher;
	/**
	 * 发布时间
	 * */
	private String publishTime;
	/**
	 * 发布内容
	 * */
	private String content;

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
