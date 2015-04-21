package com.lvmama.pet.payment.callback.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.pet.payment.callback.data.CMBCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;


/**
 * 招商银行支付回调.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 * @see com.lvmama.pet.payment.callback.data.CallbackData
 * @see com.lvmama.pet.payment.callback.data.CMBCallbackData
 */
@Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker")
public final class CMBCallbackAction extends CallbackBaseAction {

	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -27667353727813945L;
	
	/**
	 * 招商银行支付回调.
	 * 
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/cmbCallback")
	public String execute() {
			return callback(true);
	}
	
	/**
	 * 获取回调数据.
	 * 
	 * @return 回调数据
	 */
	@Override
	CallbackData getCallbackData() {
		return new CMBCallbackData(getPureParaPair());
	}

	

}
