package com.lvmama.comm.bee.po.prod;

import java.util.Date;

public class LineStopVersion {
    private Long lineStopVersionId;

    private Long lineInfoId;

    private Date startValidTime;

    private Date createTime;

    public Long getLineStopVersionId() {
        return lineStopVersionId;
    }

    public void setLineStopVersionId(Long lineStopVersionId) {
        this.lineStopVersionId = lineStopVersionId;
    }

    public Long getLineInfoId() {
        return lineInfoId;
    }

    public void setLineInfoId(Long lineInfoId) {
        this.lineInfoId = lineInfoId;
    }

    public Date getStartValidTime() {
        return startValidTime;
    }

    public void setStartValidTime(Date startValidTime) {
        this.startValidTime = startValidTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}