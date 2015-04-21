package com.lvmama.pet.payment.callback.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.UpompCallBackData;
import com.merPlus.PlusTools;

/**
 * 移动客户端支付回调Action.
 * @author fengyu
 * @see  com.lvmama.pet.payment.callback.data.CallbackData
 * @see  com.lvmama.pet.payment.callback.data.UpompCallBackData
 */
public class UpompCallBackAction extends CallbackBaseAction{
	private Logger LOG = Logger.getLogger(this.getClass());

	private static final long serialVersionUID = -8926915075581560944L;

	private String xml;
	@Action("/pay/upompClientCallback")
	public void upompClientCallback() throws UnsupportedEncodingException, IOException {
		LOG.info("invoke in ...............");
		LOG.info("begin notify upomp call back msg");
		
		xml= PlusTools.getXml(getRequest());
		String backStr = callback(true);
		//如果callback处理成功则处理通知返回
		if("order_success".equals(backStr)){
			// 获取百付支付后的通知数据
			// 验签通过后向百付发送callback后的返回信息 0000=成功、0001=失败
			boolean checkSign = PlusTools.checkSign(xml, PaymentConstant.getInstance().getProperty("UPOMP_CERT_PATH"));
			if (checkSign) {
				PlusTools.notifyXml(xml, "0000", getResponse());
				LOG.info("upomp checkSignature success");
			} else {
				PlusTools.notifyXml(xml, "0001", getResponse());
				LOG.error("upomp checkSignature failed");
			}
			LOG.info("upomp success");
		}
	}

	/**
	 * 获取回调数据.
	 * @return 回调数据
	 */
	CallbackData getCallbackData() {
		return new UpompCallBackData(xml);
	}
}
