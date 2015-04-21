package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.NingboBankCallbackData;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class NingboBankCallbackAction  extends CallbackBaseAction {	
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 4493247390292778673L;
	//同步回调标志
	private boolean syncFlag=true; 
	
	@Action("/pay/ningboBankCallback")
	public String execute() {
		return callback(syncFlag);
	}

	@Action("/pay/ningboBankCallback/asyn")
	public String asyn(){
		syncFlag=!syncFlag;
		return callback(syncFlag);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new NingboBankCallbackData(getPureParaPair(),syncFlag);
	}

}
