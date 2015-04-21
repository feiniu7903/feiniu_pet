package com.lvmama.pet.payment.callback.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.CMBInstalmentCallbackData;
import com.lvmama.pet.payment.callback.data.CallbackData;

/**
 * 招行分期支付回调Action.
 * 
 * @author fengyu
 * @see com.lvmama.common.ord.po.OrdPayment
 * @see com.lvmama.pet.payment.callback.data.CMBInstalmentCallbackData
 * @see com.lvmama.pet.payment.callback.data.CallbackData
 */
@Results({ @Result(name = "order_success", location = "/WEB-INF/pages/common/order_pay_detail.ftl", type = "freemarker") })
public class CMBInstalmentCallbackAction extends CallbackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1784340564376915648L;

	/**
	 * 招行网上支付用户在页面点击"通知商户"按钮回调.
	 * 
	 * @return /WEB-INF/pages/pay/order_pay_detail.ftl
	 */
	@Action("/pay/cmbInstalmentCallback")
	public String execute() {
		return callback(true);
	}

	/**
	 * 获取回调数据.
	 * @return 回调数据
	 */
	@Override
	CallbackData getCallbackData() {
		/**
		 * 商户提供的通知参数.
		 */
		String notifyParamName = PaymentConstant.getInstance().getProperty(
				"CMB_INSTALMENT_NOTIFY_PARAM_NAME");
		String noticeXML = super.getPureParaPair().get(notifyParamName);
		try {
			noticeXML = URLDecoder.decode(noticeXML, "UTF-8");
			LOG.info("noticeXML = " + noticeXML);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace(System.out);
		}

		CMBInstalmentCallbackData callbackData = CMBInstalmentCallbackData
				.initCMBInstalmentCallbackData(noticeXML);

		return callbackData;
	}
}
