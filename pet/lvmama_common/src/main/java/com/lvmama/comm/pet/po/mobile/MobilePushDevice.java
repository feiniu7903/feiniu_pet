package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;
import java.util.Date;

public class MobilePushDevice implements Serializable {
    private Long mobilePushDeviceId;

    private String objectId;

    private String plaform;

    private Date createTime;

    private Long userId;

    private String pushStatu;
    
    private String udid;

    private String mqttClientId;
    
    public Long getMobilePushDeviceId() {
        return mobilePushDeviceId;
    }

    public void setMobilePushDeviceId(Long mobilePushDeviceId) {
        this.mobilePushDeviceId = mobilePushDeviceId;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId == null ? null : objectId.trim();
    }

    public String getPlaform() {
        return plaform;
    }

    public void setPlaform(String plaform) {
        this.plaform = plaform == null ? null : plaform.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getPushStatu() {
        return pushStatu;
    }

    public void setPushStatu(String pushStatu) {
        this.pushStatu = pushStatu == null ? null : pushStatu.trim();
    }

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getMqttClientId() {
		return mqttClientId;
	}

	public void setMqttClientId(String mqttClientId) {
		this.mqttClientId = mqttClientId;
	}
}