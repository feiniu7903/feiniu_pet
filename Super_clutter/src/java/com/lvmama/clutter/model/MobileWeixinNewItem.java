package com.lvmama.clutter.model;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.lang3.StringUtils;

public class MobileWeixinNewItem implements Serializable {
	private static final long serialVersionUID = 7158166988178420734L;

	/**
	 * 微信new item title
	 */
	private String title;
	
	/**
	 * 微信 new item description
	 */
	private String description;
	
	/**
	 * 微信 new item url
	 */
	private String url;
	
	/**
	 * 微信 new item picurl
	 */
	private String picurl;

	public MobileWeixinNewItem() {
		super();
	}

	public MobileWeixinNewItem(String title, String description, String url,
			String picurl) {
		super();
		this.title = title;
		if(!StringUtils.isEmpty(description)){
			this.description = description;
		}else{
			this.description = "welcome to lvmama!";
		}
		this.url = url;
		this.picurl = picurl;
		/*try {
			if(!StringUtils.isEmpty(title)){
				this.title = URLEncoder.encode(title, "UTF-8");
			}
			if(!StringUtils.isEmpty(description)){
				this.description = URLEncoder.encode(description, "UTF-8");
			}else{
				this.description = "description";
			}
			this.url = url;
			this.picurl = picurl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicurl() {
		return picurl;
	}

	public void setPicurl(String picurl) {
		this.picurl = picurl;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((picurl == null) ? 0 : picurl.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MobileWeixinNewItem other = (MobileWeixinNewItem) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (picurl == null) {
			if (other.picurl != null)
				return false;
		} else if (!picurl.equals(other.picurl))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "MobileWeixinNewItem [title=" + title + ", description="
				+ description + ", url=" + url + ", picurl=" + picurl + "]";
	}
	
}
