package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.AlipayDirectpayCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class AlipayDirectpayCallbackAction  extends CallbackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1994281514803597622L;

	@Action("/pay/alipayDirectpay_callback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/alipayDirectpay_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new AlipayDirectpayCallbackData(getPureParaPair(), getMethod());
	}

}
