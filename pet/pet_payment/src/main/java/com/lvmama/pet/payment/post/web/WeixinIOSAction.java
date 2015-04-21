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
import com.lvmama.pet.payment.post.data.WeixinIOSPostData;
import com.lvmama.pet.utils.TenpayUtil;

public class WeixinIOSAction  extends PayAction {
	
	private static final long serialVersionUID = 805763256669669537L;
	private WeixinIOSPostData weixinIOSPostData;
	
	@Action("/pay/weixinIOS")
	public void weixinIOS() {
		if (payment()) {
			try {
				String token = TenpayUtil.getWeixinPhoneAccessToken();
				LOG.info("weixinIOS  response token:"+token);
				LOG.info("weixinIOS  request data:"+weixinIOSPostData.getRequestData());
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PAYINIT_URL")+token, weixinIOSPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				
				//返回提交的字符串给驴妈妈客户端
				JSONObject resultJson = JSONObject.fromObject(strReString);
				if(resultJson.getInt("errcode")==0) {	
					JSONObject json = new JSONObject();
					json.put("appid", weixinIOSPostData.getAppid());
					json.put("appkey", weixinIOSPostData.getAppkey());
					json.put("noncestr", weixinIOSPostData.getNoncestr());
					json.put("package", "Sign=WXPay");
					json.put("partnerid", weixinIOSPostData.getPartner());
					json.put("prepayid", resultJson.getString("prepayid"));
					json.put("timestamp", weixinIOSPostData.getTimestamp());
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
		weixinIOSPostData=new WeixinIOSPostData(payPayment,paramMap);
		return weixinIOSPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.WEIXIN_IOS.name();
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}

	public WeixinIOSPostData getweixinIOSPostData() {
		return weixinIOSPostData;
	}

	public void setweixinIOSPostData(WeixinIOSPostData weixinIOSPostData) {
		this.weixinIOSPostData = weixinIOSPostData;
	}	
	
	@Action("/pay/testWeixinIOS")
	public void testWeixinIOS() {
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
			LOG.info("testWeixinIOS  request data:"+weixinIOSPostData.getRequestData());
			String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("WEIXIN_PHONE_PAYINIT_URL")+token, weixinIOSPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
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