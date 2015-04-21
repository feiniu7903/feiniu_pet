package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

/**
 * 2014世界杯活动
 * @author qinzubo
 *
 */
public class MobileActivityFifaLuckycode  implements Serializable{
    private Long id;

    private String luckyCode;

    private Date sendTime;

    private String deviceId;

    private String channel;

    private String lvversion;

    private Long userid;

    private String mobile;

    private String memo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLuckyCode() {
        return luckyCode;
    }

    public void setLuckyCode(String luckyCode) {
        this.luckyCode = luckyCode == null ? null : luckyCode.trim();
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId == null ? null : deviceId.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public String getLvversion() {
        return lvversion;
    }

    public void setLvversion(String lvversion) {
        this.lvversion = lvversion == null ? null : lvversion.trim();
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile == null ? null : mobile.trim();
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo == null ? null : memo.trim();
    }
}