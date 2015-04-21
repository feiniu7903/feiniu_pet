package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.TenpayAPPCallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/tenpay_callback_async.ftl", type = "freemarker")
	})
public class TenpayAPPCallbackAction  extends CallbackBaseAction {
	
	/**
	 * @author ZHANG JIE
	 */
	private static final long serialVersionUID = -3452463531584571147L;


	@Action("/pay/tenpayAPP_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new TenpayAPPCallbackData(getPureParaPair());
	}

}
