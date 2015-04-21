package com.lvmama.pet.refundment.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.refundment.data.BaidupayRefundCallbackData;
import com.lvmama.pet.refundment.data.RefundCallbackData;

/**
 * 百度钱包退款回调Action.
 * @author zhangjie
 */
@Results( {
	@Result(name = "refund_success", location = "/WEB-INF/pages/refund/baidurefund_callback.ftl", type = "freemarker")
	})
public class BaidupayRefundCallbackAction extends RefundCallbackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7652649021955414934L;

	@Action("/refund/baidupayRefundCallback")
	public String execute(){
		return renfedCallback();
	}
	
	@Override
	protected RefundCallbackData getRefundCallbackData() {
		return new BaidupayRefundCallbackData(getPureParaPair());
	}
}
