package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-11<p/>
 * Time: 下午6:22<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivitySendLog implements Serializable {

    private static final long serialVersionUID = -8946829653800399528L;
    /**
     * 主键
     */
    private Long actSendId;
    /**
     * 活动明细ID
     */
    private Long actItemId;
    /**
     * 目标,邮件地址;手机号;
     */
    private String target;
    /**
     * 类型,邮件;短信;
     */
    private String type;
    /**
     * 发送批次
     */
    private Long sendTimes;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 创建时间
     */
    private Date createTime;

    public Long getActSendId() {
        return actSendId;
    }

    public void setActSendId(Long actSendId) {
        this.actSendId = actSendId;
    }

    public Long getActItemId() {
        return actItemId;
    }

    public void setActItemId(Long actItemId) {
        this.actItemId = actItemId;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(Long sendTimes) {
        this.sendTimes = sendTimes;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
