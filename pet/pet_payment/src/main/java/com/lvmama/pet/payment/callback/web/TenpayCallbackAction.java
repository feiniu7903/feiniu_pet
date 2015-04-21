package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.TenpayCallbackData;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/tenpay_callback_async.ftl", type = "freemarker")
	})
public class TenpayCallbackAction  extends CallbackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8014375222761957313L;

	@Action("/pay/tenpay_callback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/tenpay_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new TenpayCallbackData(getPureParaPair());
	}

}
