package com.lvmama.comm.pet.po.usertags;

import java.io.Serializable;
import java.util.Date;

public class UserTagsSearchLogs implements Serializable{
    private static final long serialVersionUID = 1L;
        
    private long searchLogsId;
    private long searchLogsFrequence;
    private String searchLogsName;
    private String searchLogsFrom;
    private Date searchLogsDate;
    private long isHide = 1 ;
    
    private String searchLogsPinYin;//添加一条临时变量
    
    public long getSearchLogsId() {
        return searchLogsId;
    }
    public void setSearchLogsId(long searchLogsId) {
        this.searchLogsId = searchLogsId;
    }
    public long getSearchLogsFrequence() {
        return searchLogsFrequence;
    }
    public void setSearchLogsFrequence(long searchLogsFrequence) {
        this.searchLogsFrequence = searchLogsFrequence;
    }
    public String getSearchLogsName() {
        return searchLogsName;
    }
    public void setSearchLogsName(String searchLogsName) {
        this.searchLogsName = searchLogsName;
    }
    public String getSearchLogsFrom() {
        return searchLogsFrom;
    }
    public void setSearchLogsFrom(String searchLogsFrom) {
        this.searchLogsFrom = searchLogsFrom;
    }
    public Date getSearchLogsDate() {
        return searchLogsDate;
    }
    public void setSearchLogsDate(Date searchLogsDate) {
        this.searchLogsDate = searchLogsDate;
    }
    public long getIsHide() {
        return isHide;
    }
    public void setIsHide(long isHide) {
        this.isHide = isHide;
    }
    public String getSearchLogsPinYin() {
        return searchLogsPinYin;
    }
    public void setSearchLogsPinYin(String searchLogsPinYin) {
        this.searchLogsPinYin = searchLogsPinYin;
    }
    
}
