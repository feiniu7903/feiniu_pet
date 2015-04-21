package com.lvmama.comm.vo;

/**
 * SeoIndexPage entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class ComSeoIndexPage implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1321408133181988228L;
	private long seoId;
	private String pageName;
	private String pageUrl;
	private String seoTitle;
	private String seoKeyword;
	private String seoDescription;
	private String channelId;
	// Constructors

	/** default constructor */
	public ComSeoIndexPage() {
	}

	/** minimal constructor */
	public ComSeoIndexPage(long seoId) {
		this.seoId = seoId;
	}

	/** full constructor */
	public ComSeoIndexPage(long seoId, String pageName, String pageUrl,
			String seoTitle, String seoKeyword, String seoDescription) {
		this.seoId = seoId;
		this.pageName = pageName;
		this.pageUrl = pageUrl;
		this.seoTitle = seoTitle;
		this.seoKeyword = seoKeyword;
		this.seoDescription = seoDescription;
	}

	// Property accessors

	public long getSeoId() {
		return this.seoId;
	}

	public void setSeoId(long seoId) {
		this.seoId = seoId;
	}

	public String getPageName() {
		return this.pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getPageUrl() {
		return this.pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public String getSeoTitle() {
		return this.seoTitle;
	}

	public void setSeoTitle(String seoTitle) {
		this.seoTitle = seoTitle;
	}

	public String getSeoKeyword() {
		return this.seoKeyword;
	}

	public void setSeoKeyword(String seoKeyword) {
		this.seoKeyword = seoKeyword;
	}

	public String getSeoDescription() {
		return this.seoDescription;
	}

	public void setSeoDescription(String seoDescription) {
		this.seoDescription = seoDescription;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

}