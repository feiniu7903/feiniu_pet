package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.COMMCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;


/**
 * 交通银行支付回调.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 * @see com.lvmama.pet.payment.callback.data.CallbackData
 * @see com.lvmama.pet.payment.callback.data.COMMCallbackData
 */
@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/comm_callback_async.ftl", type = "freemarker")
	})
public final class COMMCallbackAction extends CallbackBaseAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3127531583000424881L;

	/**
	 * 交通银行支付回调.
	 * 
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/commCallback")
	public String execute() {
		return callback(true);
	}

	@Action("/pay/commCallback/async")
	public String asyn(){
		return callback(false);
	}
	
	/**
	 * 获取回调数据.
	 * 
	 * @return 回调数据
	 */
	@Override
	CallbackData getCallbackData() {
		return new COMMCallbackData(getPureParaPair());
	}
}
