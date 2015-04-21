package com.lvmama.comm.pet.po.mark;

import java.io.*;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.<p/>
 * User: troy-kou<p/>
 * Date: 13-12-11<p/>
 * Time: 下午6:09<p/>
 * Email:kouhongyu@163.com<p/>
 */
public class MarkActivityItem implements Serializable {


    private static final long serialVersionUID = -4225863040368104771L;

    /**
     * 主键
     */
    private Long actItemId;
    /**
     * 主表主键
     */
    private Long actId;
    /**
     * 发送方式,自动;手工;
     */
    private String sendWay;
    /**
     * 发送时间
     */
    private Date sendTime;
    /**
     * 数据模型
     */
    private String dataModel;
    /**
     * 内容
     */
    private String content;
    /**
     * 排除范围,月;周;
     */
    private String excludeScope;
    /**
     * 排除符号,大于;小于;大于等于;小于等于;
     */
    private String excludeSymbol;
    /**
     * 排除次数
     */
    private Long excludeTimes;
    /**
     * 营销渠道,邮件;短信;
     */
    private String channel;
    /**
     * 已发送次数(批次)
     */
    private Long sendOffTimes;
    /**
     * 发送周期
     */
    private String cycle;
    /**
     * 每周几发送
     */
    private String week;

    /**
     * 最后发送时间
     * @return
     */
    private Date lastSendTime;


    public Long getActItemId() {
        return actItemId;
    }

    public void setActItemId(Long actItemId) {
        this.actItemId = actItemId;
    }

    public Long getActId() {
        return actId;
    }

    public void setActId(Long actId) {
        this.actId = actId;
    }

    public String getSendWay() {
        return sendWay;
    }

    public void setSendWay(String sendWay) {
        this.sendWay = sendWay;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDataModel() {
        return dataModel;
    }

    public void setDataModel(String dataModel) {
        this.dataModel = dataModel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExcludeScope() {
        return excludeScope;
    }

    public void setExcludeScope(String excludeScope) {
        this.excludeScope = excludeScope;
    }

    public String getExcludeSymbol() {
        return excludeSymbol;
    }

    public void setExcludeSymbol(String excludeSymbol) {
        this.excludeSymbol = excludeSymbol;
    }

    public Long getExcludeTimes() {
        return excludeTimes;
    }

    public void setExcludeTimes(Long excludeTimes) {
        this.excludeTimes = excludeTimes;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public Long getSendOffTimes() {
        return sendOffTimes;
    }

    public void setSendOffTimes(Long sendOffTimes) {
        this.sendOffTimes = sendOffTimes;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public Date getLastSendTime() {
        return lastSendTime;
    }

    public void setLastSendTime(Date lastSendTime) {
        this.lastSendTime = lastSendTime;
    }
}
