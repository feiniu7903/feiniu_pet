package com.lvmama.clutter.model;

import java.io.Serializable;

public class MobileVersion implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	/**
     * 标题
     */
    private String title;

    /**
     * 更新内容
     */
    private String content;

    /**
     * 是否审核中  
     */
    private boolean isAuditing ;

	/**
     * 是否强制更新
     */
    private boolean forceUpdate;

    /**
     * 最新版本号
     */
    private String version;

    /**
     * 更新地址
     */
    private String updateUrl;
    
    /**
     * 是否有新版本. 
     */
    private boolean hasNewVersion; 
    
    /**
     * 日期 
     */
    private String createdTime;
    
    public String getCreateTime() {
		return createdTime;
	}

	public void setCreateTime(String createdTime) {
		this.createdTime = createdTime;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getUpdateUrl() {
		return updateUrl;
	}

	public void setUpdateUrl(String updateUrl) {
		this.updateUrl = updateUrl;
	}


    public boolean isAuditing() {
		return isAuditing;
	}

	public void setAuditing(boolean isAuditing) {
		this.isAuditing = isAuditing;
	}

	public boolean isForceUpdate() {
		return forceUpdate;
	}

	public void setForceUpdate(boolean forceUpdate) {
		this.forceUpdate = forceUpdate;
	}

	public boolean isHasNewVersion() {
		return hasNewVersion;
	}

	public void setHasNewVersion(boolean hasNewVersion) {
		this.hasNewVersion = hasNewVersion;
	}


}
