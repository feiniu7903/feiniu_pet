package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.WeixinAndroidPostData;
import com.lvmama.pet.utils.TenpayUtil;

public class WeixinAndroidAction  extends PayAction {
	
	private static final long serialVersionUID = 913050247873471545L;
	private WeixinAndroidPostData weixinAndroidPostData;
	
	@Action("/pay/weixinAndroid")
	public void weixinAndroid() {
		if (payment()) {
			try {
				String token = TenpayUtil.getWeixinPhoneAccessToken();
				LOG.info("tenpayWeixinAndroid  response token:"+token);
				LOG.info("tenpayWeixinAndroid  request data:"+weixinAndroidPostData.getRequestData());
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PAYINIT_URL")+token, weixinAndroidPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				
				//返回提交的字符串给驴妈妈客户端
				JSONObject resultJson = JSONObject.fromObject(strReString);
				if(resultJson.getInt("errcode")==0) {	
					JSONObject json = new JSONObject();
					json.put("appid", weixinAndroidPostData.getAppid());
					json.put("appkey", weixinAndroidPostData.getAppkey());
					json.put("noncestr", weixinAndroidPostData.getNoncestr());
					json.put("package", "Sign=WXPay");
					json.put("partnerid", weixinAndroidPostData.getPartner());
					json.put("prepayid", resultJson.getString("prepayid"));
					json.put("timestamp", weixinAndroidPostData.getTimestamp());
					this.sendAjaxMsg(json.toString());
				}else {
					this.sendAjaxMsg("");
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("objectName", super.getObjectName());
		paramMap.put("spbill_create_ip", InternetProtocol.getRemoteAddr(getRequest()));
		weixinAndroidPostData = new WeixinAndroidPostData(payPayment,paramMap);
		return weixinAndroidPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.WEIXIN_ANDROID.name();
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}

	public WeixinAndroidPostData getWeixinAndroidPostData() {
		return weixinAndroidPostData;
	}

	public void setWeixinAndroidPostData(
			WeixinAndroidPostData weixinAndroidPostData) {
		this.weixinAndroidPostData = weixinAndroidPostData;
	}
	
	@Action("/pay/testWeixinAndroid")
	public void testWeixinAndroid() {
		PayPayment payPayment = new PayPayment();
		payPayment.setAmount(1L);
		payPayment.setBizType("bizType");
		String paymentTradeNo="";
		for(int i=0; i<7; i++) {
			paymentTradeNo += new Random().nextInt(10);
		}
		payPayment.setPaymentTradeNo(paymentTradeNo);
		this.getPostData(payPayment);
		
		try {
			String token = TenpayUtil.getWeixinPhoneAccessToken();
			LOG.info("testWeixinIOS  response token:"+token);
			LOG.info("testWeixinIOS  request data:"+weixinAndroidPostData.getRequestData());
			String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PAYINIT_URL")+token, weixinAndroidPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
			LOG.info("response data:"+strReString);
			
			//返回提交的字符串给驴妈妈客户端
			JSONObject resultJson = JSONObject.fromObject(strReString);
			if(resultJson.getInt("errcode")==0) {	
				this.sendAjaxMsg(resultJson.getString("prepayid"));
			}else {
				this.sendAjaxMsg("");
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}