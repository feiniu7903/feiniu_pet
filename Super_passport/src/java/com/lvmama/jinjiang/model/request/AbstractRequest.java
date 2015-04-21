package com.lvmama.jinjiang.model.request;


import java.util.Date;

import com.lvmama.jinjiang.Request;
import com.lvmama.jinjiang.comm.JinjiangUtil;
import com.lvmama.passport.utils.WebServiceConstant;
/**
 * 请求信息
 * @author chenkeke
 *
 */
public abstract class AbstractRequest implements Request{
	private String timestamp;
	private String requestURI;
	private String channelCode;
	private String ciphertext;
	@Override
	public String getRequestURI() {
		if(requestURI==null){
			requestURI =WebServiceConstant.getProperties("jinjiang.serviceUrl")+"/"+this.getTransactionMethod();
		}
		return requestURI;
	}

	@Override
	public String getChannelCode() {
		if(channelCode==null){
			channelCode = WebServiceConstant.getProperties("jinjiang.channelCode");
		}
		return channelCode;
	}
	@Override
	public String getTimestamp() {
		if(timestamp==null){
			timestamp= String.valueOf(new Date().getTime());
		}
		return timestamp;
		
	}
	@Override
	public String getCiphertext() {
		if (ciphertext == null) {
			ciphertext = JinjiangUtil.ciphertextEncode(this.getChannelCode(), this.getTimestamp());
		}
		return ciphertext;
	}

	@Override
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public void setRequestURI(String requestURI) {
		this.requestURI = requestURI;
	}

	@Override
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	@Override
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
}
