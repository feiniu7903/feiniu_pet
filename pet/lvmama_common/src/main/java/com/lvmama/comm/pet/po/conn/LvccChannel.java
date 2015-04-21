package com.lvmama.comm.pet.po.conn;

public class LvccChannel  implements java.io.Serializable{

	private static final long serialVersionUID = -7560949372750445014L;
	
	private Long channelId;
	
	private String name;

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
