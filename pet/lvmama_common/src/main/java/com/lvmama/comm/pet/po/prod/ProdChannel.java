package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

public class ProdChannel implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = -1698326535918815631L;

	private Long channelId;

    private String channelCode;

    private String channelName;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode == null ? null : channelCode.trim();
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName == null ? null : channelName.trim();
    }
}