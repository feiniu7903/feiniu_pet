package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-31<p/>
 * Time: 下午2:13<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityDataModel implements Serializable {

    private static final long serialVersionUID = 123200706825574798L;

    private String guid;
    private String segmentPath;
    private Date dateTime;
    private Long srsCount;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getSegmentPath() {
        return segmentPath;
    }

    public void setSegmentPath(String segmentPath) {
        this.segmentPath = segmentPath;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long getSrsCount() {
        return srsCount;
    }

    public void setSrsCount(Long srsCount) {
        this.srsCount = srsCount;
    }
}
