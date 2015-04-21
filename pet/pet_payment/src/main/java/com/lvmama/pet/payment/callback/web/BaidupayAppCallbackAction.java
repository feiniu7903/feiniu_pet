package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.BaidupayAppCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/baiduapppay_callback_async.ftl", type = "freemarker")
	})
public class BaidupayAppCallbackAction  extends CallbackBaseAction {
	
	private static final long serialVersionUID = 1904788302973135211L;

	@Action("/pay/baidupayApp_notify")
	public String baiduAppNotify(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new BaidupayAppCallbackData(getPureParaPair());
	}

}
