package com.lvmama.tnt.user.po;

import com.lvmama.tnt.prod.po.TntProdPolicy;

/**
 * 分销商企业类型
 * 
 * @author gaoxin
 * 
 */
public class TntChannel implements java.io.Serializable {
	private static final long serialVersionUID = 5454155825314635342L;

	/**
	 * 渠道类型
	 */
	private Long channelId;

	private String channelName;
	
	private TntProdPolicy tntProdPolicy;
	
	

	public TntChannel() {}

	public TntChannel(String channelCode) {
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

	private String channelCode;

	@Override
	public String toString() {
		return "TntChannel [channelId=" + channelId + ",channelName="
				+ channelName + ", channelCode=" + channelCode + "]";
	}

	public TntProdPolicy getTntProdPolicy() {
		return tntProdPolicy;
	}

	public void setTntProdPolicy(TntProdPolicy tntProdPolicy) {
		this.tntProdPolicy = tntProdPolicy;
	}
}
