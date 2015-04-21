package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.AlipayAPPCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/alipay_callback_asyn.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class AlipayAPPCallbackAction  extends CallbackBaseAction {
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 6939029070402955917L;

	@Action("/pay/alipayAPP_callback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/alipayAPP_callback/asyn")
	public String asyn(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new AlipayAPPCallbackData(getPureParaPair(), getMethod());
	}

}
