package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.BOCCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

/**
 * 中国银行网上支付回调数据.
 * @author sunruyi
 */

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/boc_callback_asyn.ftl", type = "freemarker")
	})
public class BOCCallbackAction extends CallbackBaseAction {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3796014103392224847L;

	@Action("/pay/boc_callback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/boc_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new BOCCallbackData(getPureParaPair(), getMethod());
	}
}
