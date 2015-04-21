package com.lvmama.comm.pet.po.usertags;

import java.io.Serializable;
import java.util.Date;

public class UserTagsType implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private long typeId;
    private String typeFirstType;
    private String typeSecondType;
    private Date typeCreateDate;
    
    
    public long getTypeId() {
        return typeId;
    }
    public void setTypeId(long typeId) {
        this.typeId = typeId;
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
    public Date getTypeCreateDate() {
        return typeCreateDate;
    }
    public void setTypeCreateDate(Date typeCreateDate) {
        this.typeCreateDate = typeCreateDate;
    }
}
