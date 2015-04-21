package com.lvmama.front.web.buy;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.MobilePayInfo;
import com.tenpay.api.common.util.HttpClientUtil;

public class AlipayAppPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;

	public String getAccessToken(){
		if(!isAlipayQuickPay()){
			LOG.info("refreshToken is null");
			return "";
		}
		String refreshToken = getRequestParameter("refreshToken");
		AlipayClient client = new DefaultAlipayClient(ALIPAY_OPEN_URL, ALIPAY_OPEN_APPID, ALIPAY_OPEN_PRIVATE_KEY);
		AlipaySystemOauthTokenRequest req = new AlipaySystemOauthTokenRequest();
		req.setGrantType("refresh_token");
		req.setRefreshToken(refreshToken);
		try {
			AlipaySystemOauthTokenResponse res = client.execute(req);
			LOG.info("getAccessToken is :"+res.getAccessToken());
			return  res.getAccessToken();
			
		} catch (AlipayApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public boolean isAlipayQuickPay(){
		LOG.info("refreshToken : "+getRequestParameter("refreshToken"));
		if(!StringUtil.isEmptyString(getRequestParameter("refreshToken"))){
			return true;
		}
		return false;
	}

	@Action("/alipayApp/toPay")
	public void toPay() throws Exception {
		MobilePayInfo pinfo = super.toPay("%s/pay/alipayAPP.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&visitTime=%s&approveTime=%s&waitPayment=%s&extern_token="+getAccessToken());
		String responeStr="";
		if(pinfo.isSuccess()){
			responeStr = HttpClientUtil.httpCall(pinfo.getReturnUrl(),
					30 * 1000, 30 * 1000, "utf-8");
			this.sendAjaxMsg(responeStr);
		} else {
			JSONObject json = JSONObject.fromObject(pinfo);
			this.sendAjaxMsg(json.toString());
		}
	}
	
	/**
	 * 现金账号充值 
	 * @throws Exception
	 */
	@Action("/alipayApp/toPay4Recharge")
	public void toPay4Recharge() throws Exception {
		// http://www.alipay.com/pay/alipay.do?objectId=19721&amount=500&objectType=CASH_RECHARGE&paymentType=PAY&bizType=CASH_ACCOUNT
		// &waitPayment=1&approveTime=20140116105434&visitTime=20140116105434&signature=3DF2F775B0E42512CA63C0EE6375D087"
		MobilePayInfo pinfo = super.toPay4Recharge("%s/pay/alipayAPP.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s" +
				"&waitPayment=1&approveTime=%s&visitTime=%s&extern_token="+getAccessToken());
		String responeStr="";
		if(pinfo.isSuccess()){
			responeStr = HttpClientUtil.httpCall(pinfo.getReturnUrl(),30 * 1000, 30 * 1000, "utf-8");
			this.sendAjaxMsg(responeStr);
		} else {
			JSONObject json = JSONObject.fromObject(pinfo);
			this.sendAjaxMsg(json.toString());
		}
	}
	
}
