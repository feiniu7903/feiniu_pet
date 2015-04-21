package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileMarkActivityLog implements Serializable {
    private Long mobileMarkActivityLogId;

    private Long mobileMarkActivityId;

    private String objectId;

    private String platform;

    private Date createdTime;

    private String objectType;

    private Long userId;

    private String objectVavlue;

    public Long getMobileMarkActivityLogId() {
        return mobileMarkActivityLogId;
    }

    public void setMobileMarkActivityLogId(Long mobileMarkActivityLogId) {
        this.mobileMarkActivityLogId = mobileMarkActivityLogId;
    }

    public Long getMobileMarkActivityId() {
        return mobileMarkActivityId;
    }

    public void setMobileMarkActivityId(Long mobileMarkActivityId) {
        this.mobileMarkActivityId = mobileMarkActivityId;
    }

    public String getObjectId() {
		return objectId;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform == null ? null : platform.trim();
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getObjectVavlue() {
        return objectVavlue;
    }

    public void setObjectVavlue(String objectVavlue) {
        this.objectVavlue = objectVavlue == null ? null : objectVavlue.trim();
    }
}