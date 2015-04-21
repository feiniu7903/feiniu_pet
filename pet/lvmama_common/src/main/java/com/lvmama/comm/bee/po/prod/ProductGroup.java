package com.lvmama.comm.bee.po.prod;

import java.io.Serializable;

public class ProductGroup implements Serializable{
    private Long groupId;

    private String groupName;

    private String necessary;

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName == null ? null : groupName.trim();
    }

    public String getNecessary() {
        return necessary;
    }

    public void setNecessary(String necessary) {
        this.necessary = necessary == null ? null : necessary.trim();
    }
}