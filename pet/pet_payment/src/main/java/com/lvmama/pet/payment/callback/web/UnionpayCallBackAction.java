package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.UnionpayCallbackData;



/**
 * 银联支付回调.
 */
@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/unionpay_callback_async.ftl", type = "freemarker")
	})
public class UnionpayCallBackAction extends CallbackBaseAction {
	
	private static final long serialVersionUID = 7402387372007977509L;
	
	@Action("/pay/unionpayCallback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/unionpayCallback/asyn")
	public String asyn(){
		return callback(false);
	}
	@Override
	CallbackData getCallbackData() {
		return new UnionpayCallbackData(getPureParaPair());
	}
}
