package com.lvmama.pet.payment.callback.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.TenpayWapCallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/tenpay_callback_async.ftl", type = "freemarker")
	})
public class TenpayWapCallbackAction  extends CallbackBaseAction {
	
	private static final long serialVersionUID = 6336971493540954335L;
	private String tenpayWAPaymentSuccessUrl=PaymentConstant.getInstance().getProperty("QING_LVMAMA_CALLBACK_PATH");


	@Action("/pay/tenpayWap_notify")
	public String tenpayWAPNotify(){
		return callback(false);
	}
	
	@Action("/pay/tenpayWap_callback")
	public void tenpayWAPCallback(){
		try {
			if (ORDER_SUCCESS.equals(callback(true))) {
				this.getResponse().sendRedirect(tenpayWAPaymentSuccessUrl + "?status=success");
			} else {
				this.getResponse().sendRedirect(tenpayWAPaymentSuccessUrl + "?status=error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	CallbackData getCallbackData() {
		return new TenpayWapCallbackData(getPureParaPair());
	}

}
