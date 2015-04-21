package com.lvmama.pet.payment.callback.web;

import java.io.IOException;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.WeixinWebCallbackData;
import com.lvmama.pet.payment.callback.data.WeixinWebCallbackNotifyData;
import com.lvmama.pet.payment.post.data.WeixinWebVerifyNotifyIdPostData;
import com.lvmama.pet.utils.COMMUtil;
import com.lvmama.pet.utils.StringDom4jUtil;

@Results( {
	@Result(name = "order_success", location = "/WEB-INF/pages/forms/weixinWebCallback.ftl", type = "freemarker"),
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/tenpay_callback_async.ftl", type = "freemarker")
	})
/**
 * @author heyuxing
 */
public class WeixinWebCallbackAction  extends CallbackBaseAction {
	
	private static final long serialVersionUID = 6336971493540954335L;
	protected WeixinWebCallbackData weixinWebCallbackData;	//微信web扫码“支付通知查询”回调数据.

	@Action("/pay/weixin_web_notify")
	public String weixinWebNotify(){
		LOG.info("weixinWebNotify call!");
		return weixinWebCallback(false);
	}
	
	@Action("/pay/weixin_web_callback")
	public String weixinWebCallback(){
		LOG.info("weixinWebCallback call!");
		return weixinWebCallback(true);
	}
	
	private String weixinWebCallback(boolean sync) {
		//商户接收到财付通的支付成功通知后，可以通过此接口查询通知的具体内容，以确保通知是从财付通发起的，没有被伪造或篡改过。
		Map<String, String> callbackParamMap = getPureParaPair();
		WeixinWebCallbackNotifyData weixinWebCallbackNotifyData = new WeixinWebCallbackNotifyData(callbackParamMap);
		//检查通知签名
		String sign = COMMUtil.getSignature(callbackParamMap,weixinWebCallbackNotifyData.getKey());
		if(sign!=null && sign.equals(weixinWebCallbackNotifyData.getSign())) {	
			//依据notify_id查询支付结果
			WeixinWebVerifyNotifyIdPostData weixinWebVerifyNotifyIdPostData = new WeixinWebVerifyNotifyIdPostData(weixinWebCallbackNotifyData.getNotify_id());
			try{
				System.out.println("requst=="+weixinWebVerifyNotifyIdPostData.getRequestData());
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_WEB_VERIFYNOTIFYID_URL"), weixinWebVerifyNotifyIdPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(strReString);
				weixinWebCallbackData = new WeixinWebCallbackData(resultMap);
				//检查通知签名
				String verifyNotifyIdQuerySign = COMMUtil.getSignature(resultMap,weixinWebCallbackData.getKey());
				if(verifyNotifyIdQuerySign!=null && verifyNotifyIdQuerySign.equals(weixinWebCallbackData.getSign()) && "0".equals(weixinWebCallbackData.getRetcode())) {
					return callback(sync);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "fail";	//TODO
	}
	
	@Override
	CallbackData getCallbackData() {
		return weixinWebCallbackData;
	}

}
