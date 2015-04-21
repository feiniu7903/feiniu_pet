package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

/**
 * 驴途移动端 from3.0 版本管理.
 * @author qinzubo
 *
 */
public class MobileVersion  implements Serializable {
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
    private String isAuditing = "true";

    /**
     * 是否强制更新
     */
    private String forceUpdate = "false";

    /**
     * 最新版本号
     */
    private String version;

    /**
     * 所属平台
     */
    private String platform;

    /**
     * 一级投放渠道
     */
    private String firstChannel;

    /**
     * 二级投放渠道
     */
    private String seconedChannel;

    /**
     * 更新地址
     */
    private String updateUrl;

    /**
     * 创建时间
     */
    private Date createdTime = new Date();

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
        this.title = title == null ? null : title.trim();
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getIsAuditing() {
        return isAuditing;
    }

    public void setIsAuditing(String isAuditing) {
        this.isAuditing = isAuditing == null ? null : isAuditing.trim();
    }

    public String getForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(String forceUpdate) {
        this.forceUpdate = forceUpdate == null ? null : forceUpdate.trim();
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version == null ? null : version.trim();
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public String getFirstChannel() {
        return firstChannel;
    }

    public void setFirstChannel(String firstChannel) {
        this.firstChannel = firstChannel == null ? null : firstChannel.trim();
    }

    public String getSeconedChannel() {
        return seconedChannel;
    }

    public void setSeconedChannel(String seconedChannel) {
        this.seconedChannel = seconedChannel == null ? null : seconedChannel.trim();
    }

    public String getUpdateUrl() {
        return updateUrl;
    }

    public void setUpdateUrl(String updateUrl) {
        this.updateUrl = updateUrl == null ? null : updateUrl.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }
}