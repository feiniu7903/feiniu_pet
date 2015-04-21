package com.lvmama.pet.payment.callback.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.BaidupayWapCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/baiduwappay_callback_async.ftl", type = "freemarker")
	})
public class BaidupayWapCallbackAction  extends CallbackBaseAction {
	
	private static final long serialVersionUID = -6124498187547834673L;
	private String baidupayWAPaymentSuccessUrl=PaymentConstant.getInstance().getProperty("QING_LVMAMA_CALLBACK_PATH");

	@Action("/pay/baidupayWap_callback")
	public void baidupayWAPCallback(){
		try {
			if (ORDER_SUCCESS.equals(callback(true))) {
				this.getResponse().sendRedirect(baidupayWAPaymentSuccessUrl + "?status=success");
			} else {
				this.getResponse().sendRedirect(baidupayWAPaymentSuccessUrl + "?status=error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/pay/baidupayWap_notify")
	public String baiduWAPNotify(){
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new BaidupayWapCallbackData(getPureParaPair());
	}

}
