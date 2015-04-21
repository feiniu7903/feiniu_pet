package com.lvmama.pet.payment.callback.web;

import java.io.IOException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.AlipayWAPCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class AlipayWAPCallbackAction  extends CallbackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1994281514803597622L;
	
	private boolean sync;
	private String alipayWAPaymentSuccessUrl=PaymentConstant.getInstance().getProperty("ALIPAY_WAP_PAYMENT_SUCCESS_URL");
	@Action("/pay/alipayWAP_callback")
	public void alipayWAPCallBack() {
		this.sync=true;
		try {
			if (ORDER_SUCCESS.equals(callback(true))) {
				this.getResponse().sendRedirect(alipayWAPaymentSuccessUrl + "?status=success");
			} else {
				this.getResponse().sendRedirect(alipayWAPaymentSuccessUrl + "?status=error");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	@Action("/pay/alipayWAP_callback/asyn")
	public String asyn(){
		this.sync=false;
		return callback(false);
	}
	
	@Override
	CallbackData getCallbackData() {
		return new AlipayWAPCallbackData(getPureParaPair(), getMethod(),this.sync);
	}
	
	//用来让客户端截获此url 的状态 来失败支付状态 并跳转到相应的app 页面
	@Action("/pay/alipayPaymentSuccess")
	public void alipayWAPSuccess() {
		try {
			this.getResponse().sendRedirect("http://m.lvmama.com/clutter/ticketorder/order_pay_callback.htm");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean isSync() {
		return sync;
	}

	public void setSync(boolean sync) {
		this.sync = sync;
	}
}
