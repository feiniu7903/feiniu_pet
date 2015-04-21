package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobileMarkActivity implements Serializable {
    private Long mobileMarkActivityId;

    private String name;

    private Long operatorNum;

    private String activityTarget;

    private String activityScope;

    private Long totalOperatorNum;

    private Date createdTime;

    private String isValid;

    public Long getMobileMarkActivityId() {
        return mobileMarkActivityId;
    }

    public void setMobileMarkActivityId(Long mobileMarkActivityId) {
        this.mobileMarkActivityId = mobileMarkActivityId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Long getOperatorNum() {
        return operatorNum;
    }

    public void setOperatorNum(Long operatorNum) {
        this.operatorNum = operatorNum;
    }

    public String getActivityTarget() {
        return activityTarget;
    }

    public void setActivityTarget(String activityTarget) {
        this.activityTarget = activityTarget == null ? null : activityTarget.trim();
    }

    public String getActivityScope() {
        return activityScope;
    }

    public void setActivityScope(String activityScope) {
        this.activityScope = activityScope == null ? null : activityScope.trim();
    }

    public Long getTotalOperatorNum() {
        return totalOperatorNum;
    }

    public void setTotalOperatorNum(Long totalOperatorNum) {
        this.totalOperatorNum = totalOperatorNum;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid == null ? null : isValid.trim();
    }
}