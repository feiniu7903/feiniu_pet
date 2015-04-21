package com.lvmama.comm.pet.po.mobile;

import java.io.Serializable;

public class MobilePushSendTaskLog implements Serializable {
	private static final long serialVersionUID = 1303442180743975526L;

	private Long mobilePushSendTaskLogId;

    private String pushObjectId;

    private String pushStatu;

    private Long mobilePushSendTaskId;

    private Long mobilePushJogTaskId;

    public Long getMobilePushSendTaskLogId() {
        return mobilePushSendTaskLogId;
    }

    public void setMobilePushSendTaskLogId(Long mobilePushSendTaskLogId) {
        this.mobilePushSendTaskLogId = mobilePushSendTaskLogId;
    }

    public String getPushObjectId() {
        return pushObjectId;
    }

    public void setPushObjectId(String pushObjectId) {
        this.pushObjectId = pushObjectId == null ? null : pushObjectId.trim();
    }

    public String getPushStatu() {
        return pushStatu;
    }

    public void setPushStatu(String pushStatu) {
        this.pushStatu = pushStatu == null ? null : pushStatu.trim();
    }

    public Long getMobilePushSendTaskId() {
        return mobilePushSendTaskId;
    }

    public void setMobilePushSendTaskId(Long mobilePushSendTaskId) {
        this.mobilePushSendTaskId = mobilePushSendTaskId;
    }

    public Long getMobilePushJogTaskId() {
        return mobilePushJogTaskId;
    }

    public void setMobilePushJogTaskId(Long mobilePushJogTaskId) {
        this.mobilePushJogTaskId = mobilePushJogTaskId;
    }

	@Override
	public String toString() {
		return "MobilePushSendTaskLog [mobilePushSendTaskLogId="
				+ mobilePushSendTaskLogId + ", pushObjectId=" + pushObjectId
				+ ", pushStatu=" + pushStatu + ", mobilePushSendTaskId="
				+ mobilePushSendTaskId + ", mobilePushJogTaskId="
				+ mobilePushJogTaskId + "]";
	}
    
}