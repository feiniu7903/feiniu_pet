package com.lvmama.comm.pet.po.seo;

import java.util.Date;

public class SeoIndexPage implements java.io.Serializable{
	private static final long serialVersionUID = -3655658886762650995L;

	private Long seoIndexPageId;

    private String pageName;

    private String pageCode;

    private String seoTitle;

    private String seoDescription;

    private String seoKeyword;

    private String seoContent;

    private Date updateTime;

    private String footerFileName;

    public Long getSeoIndexPageId() {
        return seoIndexPageId;
    }

    public void setSeoIndexPageId(Long seoIndexPageId) {
        this.seoIndexPageId = seoIndexPageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageCode() {
        return pageCode;
    }

    public void setPageCode(String pageCode) {
        this.pageCode = pageCode;
    }

    public String getSeoTitle() {
        return seoTitle;
    }

    public void setSeoTitle(String seoTitle) {
        this.seoTitle = seoTitle;
    }

    public String getSeoDescription() {
        return seoDescription;
    }

    public void setSeoDescription(String seoDescription) {
        this.seoDescription = seoDescription;
    }

    public String getSeoKeyword() {
        return seoKeyword;
    }

    public void setSeoKeyword(String seoKeyword) {
        this.seoKeyword = seoKeyword;
    }

    public String getSeoContent() {
        return seoContent;
    }

    public void setSeoContent(String seoContent) {
        this.seoContent = seoContent;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getFooterFileName() {
        return footerFileName;
    }

    public void setFooterFileName(String footerFileName) {
        this.footerFileName = footerFileName;
    }
}