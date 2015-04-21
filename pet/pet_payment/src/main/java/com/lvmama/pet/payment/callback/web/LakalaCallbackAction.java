package com.lvmama.pet.payment.callback.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.LakalaCallbackData;

/**
 * 拉卡拉支付网关回调.
 * 
 * <pre>
 * 用户在使用拉卡拉POS机时，拉卡拉系统首先调用驴妈妈查询接口，判断此时是否可以进行支付，
 * 如果可以，则拉卡拉系统进行扣款，扣款成功后再通知驴妈妈支付成功，如果此时网络中断，则不会再次通知！
 * </pre>
 * 
 * @author tom
 * @version Super二期 2011/03/20
 * @since Super二期
 * @see com.lvmama.common.ord.po.OrdOrder
 * @see com.lvmama.common.ord.service.OrderService
 * @see com.lvmama.payment.service.LakalaService
 * @see com.lvmama.payment.utils.LakalaPaymentCheck
 */
@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/lakala_inform.ftl", type = "freemarker")
	})
public final class LakalaCallbackAction extends CallbackBaseAction {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -7608532296152769383L;
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory.getLog(LakalaCallbackAction.class);

	/**
	 * 支付成功通知.
	 * 
	 * <pre>
	 * 通知驴妈妈支付成功，如果此时网络中断，则不会再次通知！
	 * </pre>
	 * 
	 * @return {@link PAY_INFORM}
	 */
	@Action("/lakala/payInform")
	public String payInform() {
		return callback(false);
	}

	public CallbackData getData() {
		return callbackData;
	}
	
	@Override
	CallbackData getCallbackData() {
		return new LakalaCallbackData(super.getPureParaPair());
	}

}
