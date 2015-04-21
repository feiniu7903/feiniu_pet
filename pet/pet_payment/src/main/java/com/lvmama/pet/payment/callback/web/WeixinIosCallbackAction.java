package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.WeixinIosCallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/weixin_app_callback_async.ftl", type = "freemarker")
	})
public class WeixinIosCallbackAction  extends CallbackBaseAction {
	
	private static final long serialVersionUID = 7420024609755152573L;

	@Action("/pay/weixin_ios_notify")
	public String weixinIosNotify(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new WeixinIosCallbackData(getPureParaPair());
	}

}
