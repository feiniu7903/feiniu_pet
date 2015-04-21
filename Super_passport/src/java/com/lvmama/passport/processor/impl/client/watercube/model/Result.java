package com.lvmama.passport.processor.impl.client.watercube.model;
/**
 * 水魔方--报文元素--result结点
 * @author lipengcheng
 *
 */
public class Result {
	private String id;//结果ID
	private String comment;//结果描述

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
