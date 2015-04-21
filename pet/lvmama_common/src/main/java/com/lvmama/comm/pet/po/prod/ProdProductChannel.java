package com.lvmama.comm.pet.po.prod;

import java.io.Serializable;

import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.vo.Constant;

public class ProdProductChannel implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1502342308250625376L;

	private Long productChannelId;
	
	private Long productId;
	
	private String productChannel;
	
	public String getChannelName(){
		 return CodeSet.getInstance().getCodeName(Constant.CODE_TYPE.CHANNEL.name(), productChannel);
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductChannelId() {
		return productChannelId;
	}
	public void setProductChannelId(Long productChannelId) {
		this.productChannelId = productChannelId;
	}
	public String getProductChannel() {
		return productChannel;
	}
	public void setProductChannel(String productChannel) {
		this.productChannel = productChannel;
	}

}