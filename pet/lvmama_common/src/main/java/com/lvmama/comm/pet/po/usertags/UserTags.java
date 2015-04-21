package com.lvmama.comm.pet.po.usertags;

import java.io.Serializable;
import java.util.Date;


public class UserTags implements Serializable{
    
    private static final long serialVersionUID = 1L;

    private long tagsId;
    private String tagsName;
    private String tagsPinYin;
    private long tagsTypeId;
    private long tagsStatus=1;//状态（1，可用；2不可用；）
    private Date tagsDate;
    private long dealStatus=0;
 
    private String typeFirstType;
    private String typeSecondType;//添加两个get
    
    
    public long getTagsId() {
        return tagsId;
    }
    public void setTagsId(long tagsId) {
        this.tagsId = tagsId;
    }
    public String getTagsName() {
        return tagsName;
    }
    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }
    public String getTagsPinYin() {
        return tagsPinYin;
    }
    public void setTagsPinYin(String tagsPinYin) {
        this.tagsPinYin = tagsPinYin;
    }
    public long getTagsTypeId() {
        return tagsTypeId;
    }
    public void setTagsTypeId(long tagsTypeId) {
        this.tagsTypeId = tagsTypeId;
    }
    public long getTagsStatus() {
        return tagsStatus;
    }
    public void setTagsStatus(long tagsStatus) {
        this.tagsStatus = tagsStatus;
    }
    public Date getTagsDate() {
        return tagsDate;
    }
    public void setTagsDate(Date tagsDate) {
        this.tagsDate = tagsDate;
    }
    public String getTypeFirstType() {
        return typeFirstType;
    }
    public void setTypeFirstType(String typeFirstType) {
        this.typeFirstType = typeFirstType;
    }
    public String getTypeSecondType() {
        return typeSecondType;
    }
    public void setTypeSecondType(String typeSecondType) {
        this.typeSecondType = typeSecondType;
    }
    public long getDealStatus() {
        return dealStatus;
    }
    public void setDealStatus(long dealStatus) {
        this.dealStatus = dealStatus;
    }  
    
}
