package com.lvmama.report.po;

/**
 * 用户注册渠道
 * 
 * @author Brian
 *
 */
public class UserRegisterChannelMV {
	private Long channelId;
	private String channelName;
	private String channelCode;
	private Long fatherId;
	private Long layer;
	
	public UserRegisterChannelMV() {}
	
	public UserRegisterChannelMV(Long channelId, String channelName, String channelCode) {
		this.channelId = channelId;
		this.channelName = channelName;
		this.channelCode = channelCode;
	}
	
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}

	public Long getLayer() {
		return layer;
	}

	public void setLayer(Long layer) {
		this.layer = layer;
	}
	
}
