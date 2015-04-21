package com.lvmama.pet.refundment.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.refundment.data.RefundCallbackData;
import com.lvmama.pet.refundment.data.UpompRefundCallbackData;
import com.merPlus.PlusTools;

/**
 * 百付-手机支付退款.异步回调
 * 百付的退款异步回调和同步回调的返回信息都是一样的
 * @author ZHANG Nan
 *
 */
@Results({ @Result(name = "refund_success", location = "/WEB-INF/pages/refund/refund_success.ftl", type = "freemarker") })
public class UpompRefundCallbackAction extends RefundCallbackBaseAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 6672319440100381425L;
	
	private String xml;
	
	/**
	 * 百付-手机支付退款回调处理
	 */
	@Action("/refund/upompRefundCallback")
	public String execute() {
		try {
			xml = PlusTools.getXml(this.getRequest());
			LOG.info("com.lvmama.pet.refundment.web.UpompRefundCallbackAction.execute() xml:" + xml);
			
			String result = renfedCallback();
			if (REFUND_SUCCESS.equalsIgnoreCase(result)) {
				boolean checkSign = PlusTools.checkSign(xml, PaymentConstant.getInstance().getProperty("UPOMP_CERT_PATH"));
				if (checkSign) {
					PlusTools.notifyXml(xml, "0000", getResponse());
					LOG.info("upomp com.lvmama.pet.refundment.web.UpompRefundCallbackAction.execute():success...");
				} else {
					PlusTools.notifyXml(xml, "0001", getResponse());
					LOG.info("upomp com.lvmama.pet.refundment.web.UpompRefundCallbackAction.execute():failed...");
				}
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "refund_success";
	}

	@Override
	protected RefundCallbackData getRefundCallbackData() {
		return new UpompRefundCallbackData(xml);
	}
}