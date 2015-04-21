package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-11<p/>
 * Time: 下午5:52<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivity implements Serializable {
    private static final long serialVersionUID = 4152365950778348500L;
    /**
     * 主键
     */
    private Long actId;
    /**
     * 活动名称
     */
    private String activityName;
    /**
     * 活动状态:激活;完成;取消;
     */
    private String status;
    /**
     * 负责人
     */
    private String personCharge;
    /**
     * 创建时间
     */
    private Date createTime;

    private MarkActivityItem markActivityItemEmail;


    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPersonCharge() {
        return personCharge;
    }

    public void setPersonCharge(String personCharge) {
        this.personCharge = personCharge;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MarkActivityItem getMarkActivityItemEmail() {
        return markActivityItemEmail;
    }

    public void setMarkActivityItemEmail(MarkActivityItem markActivityItemEmail) {
        this.markActivityItemEmail = markActivityItemEmail;
    }

    public String toString() {
        return "MarkActivity{" +
                "actId=" + actId +
                ", activityName='" + activityName + '\'' +
                ", status='" + status + '\'' +
                ", personCharge='" + personCharge + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
