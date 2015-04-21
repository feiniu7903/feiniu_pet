package com.lvmama.pet.refundment.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.refundment.data.UnionpayPreRefundCallbackData;

/**
 * 银联在线预授权退款回调Action.
 */
@Results({ @Result(name = "refund_success", location = "/WEB-INF/pages/refund/refund_success.ftl", type = "freemarker") })
public class UnionpayPreRefundCallbackAction extends RefundCallbackBaseAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 7896410699525916949L;

	@Action("/refund/unionpayCallback")
	public String execute() {
		String result = renfedCallback();
		return result;
	}

	@Override
	protected UnionpayPreRefundCallbackData getRefundCallbackData() {
		return new UnionpayPreRefundCallbackData(getPureParaPair());
	}
}