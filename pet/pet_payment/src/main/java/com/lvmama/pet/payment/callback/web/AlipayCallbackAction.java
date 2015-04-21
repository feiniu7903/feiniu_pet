package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.AlipayCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class AlipayCallbackAction  extends CallbackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8934347005856995434L;
	
	
	@Action("/pay/alipay_callback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/alipay_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new AlipayCallbackData(getPureParaPair(), getMethod());
	}

}
