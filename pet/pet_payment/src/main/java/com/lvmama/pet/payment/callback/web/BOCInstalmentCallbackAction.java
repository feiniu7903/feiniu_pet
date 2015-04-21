package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.BOCInstalmentCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

/**
 * 中行分期支付回调Action.
 * @author sunruyi
 */
@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker")
	})
public class BOCInstalmentCallbackAction extends CallbackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1784340564376915648L;
	/**
	 * 中行网上支付用户在页面点击"通知商户"按钮回调.
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/bocInstalmentCallback")
	public String execute(){
		return callback(true);
	}
	/**
	 * 获取回调数据.
	 * @return 回调数据
	 */
	@Override
	CallbackData getCallbackData() {
		String responseData = super.getPureParaPair().get("reqData");
		BOCInstalmentCallbackData callbackData = BOCInstalmentCallbackData.initBOCInstalmentCallbackData(responseData);
		return callbackData;
	}
}
