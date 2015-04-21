package com.lvmama.clutter.model;

import java.io.Serializable;

/**
 * @Title: MobileGrouponContents.java
 * @Package com.lvmama.clutter.model
 * @Description: TODO
 * @author jiangzhihu
 * @date 2014-4-2 下午4:02:39
 * @version V1.0.0
 */
public class MobileGrouponContent implements Comparable, Serializable {
	private static final long serialVersionUID = -4524181666677768702L;

	/** 排序字段 **/
	private int sort;
	/** 内容ID **/
	private Long contentId;
	/** 内容类型 **/
	private String contentType;
	/** 内容Tag **/
	private String contentTag;
	/** 内容 **/
	private String content;
	/** 内容url **/
	private String contentUrl;

	public void setSort(int sort) {
		this.sort = sort;
	}

	public int getSort() {
		return sort;
	}

	public Long getContentId() {
		return contentId;
	}

	public void setContentId(Long contentId) {
		this.contentId = contentId;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public String getContentTag() {
		return contentTag;
	}

	public void setContentTag(String contentTag) {
		this.contentTag = contentTag;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContentUrl() {
		return contentUrl;
	}

	public void setContentUrl(String contentUrl) {
		this.contentUrl = contentUrl;
	}

	@Override
	public int compareTo(Object o) {
		if (o instanceof MobileGrouponContent) {
			MobileGrouponContent content = (MobileGrouponContent) o;
			if (this.sort > content.getSort())
				return 1;
			if (this.sort < content.getSort())
				return -1;
		}
		return 0;
	}
}
