package com.lvmama.comm.businesses.po.review;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;

public class KeyWord implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private long kId;
    private String kContent;
    private Date kDate;
    
    public long getkId() {
        return kId;
    }
    public void setkId(long kId) {
        this.kId = kId;
    }
    public String getkContent() {
        return kContent;
    }
    public void setkContent(String kContent) {
        this.kContent = kContent;
    }
    public String getkDate() {
        return DateUtil.getFormatDate(kDate, "yyyy-MM-dd HH:mm:ss");
    }
    public void setkDate(Date kDate) {
        this.kDate = kDate;
    }
}
