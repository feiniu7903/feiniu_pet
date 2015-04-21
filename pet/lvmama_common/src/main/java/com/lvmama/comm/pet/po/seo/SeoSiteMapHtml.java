package com.lvmama.comm.pet.po.seo;

import java.io.Serializable;
import java.util.Date;

public class SeoSiteMapHtml implements Serializable {
	private static final long serialVersionUID = 2435647701416923623L;

	private Long seoSiteMapHtmlId;

    private String htmlMenuName;

    private String htmlMenuUrl;

    private Long seq;

    private Long parentId;

    private Date createTime;

    private Date updateTime;

    public Long getSeoSiteMapHtmlId() {
        return seoSiteMapHtmlId;
    }

    public void setSeoSiteMapHtmlId(Long seoSiteMapHtmlId) {
        this.seoSiteMapHtmlId = seoSiteMapHtmlId;
    }

    public String getHtmlMenuName() {
        return htmlMenuName;
    }

    public void setHtmlMenuName(String htmlMenuName) {
        this.htmlMenuName = htmlMenuName;
    }

    public String getHtmlMenuUrl() {
        return htmlMenuUrl;
    }

    public void setHtmlMenuUrl(String htmlMenuUrl) {
        this.htmlMenuUrl = htmlMenuUrl;
    }

    public Long getSeq() {
        return seq;
    }

    public void setSeq(Long seq) {
        this.seq = seq;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}