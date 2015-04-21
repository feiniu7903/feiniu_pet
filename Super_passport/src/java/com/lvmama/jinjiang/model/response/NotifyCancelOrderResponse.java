package com.lvmama.jinjiang.model.response;

public class NotifyCancelOrderResponse extends AbstractResponse{
	public NotifyCancelOrderResponse(String errorcode,String errormessage){
		this.setErrorcode(errorcode);
		this.setErrormessage(errormessage);
	}
	
}
