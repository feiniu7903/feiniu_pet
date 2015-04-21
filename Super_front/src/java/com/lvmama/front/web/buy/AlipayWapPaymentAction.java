package com.lvmama.front.web.buy;


import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.vo.MobilePayInfo;
import com.tenpay.api.common.util.HttpClientUtil;


public class AlipayWapPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;

	private String cashierCode;


	@Action("/alipayWap/toPay")
	public void toPay() throws Exception {
		MobilePayInfo pinfo = super.toPay("%s/pay/alipayWAP.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&visitTime=%s&approveTime=%s&waitPayment=%s&cashierCode="+cashierCode);
		pinfo.setMessage("<h3>"+pinfo.getMessage()+"</h3>");
		if(pinfo.isSuccess()){
			this.getResponse().sendRedirect(pinfo.getReturnUrl());
		} else {
			this.sendAjaxMsg(pinfo.getMessage());
		}
	}
	
	/**
	 * 现金账号充值 
	 * @throws Exception
	 */
	@Action("/alipayWap/toPay4Recharge")
	public void toPay4Recharge() throws Exception {
		// http://www.alipay.com/pay/alipay.do?objectId=19721&amount=500&objectType=CASH_RECHARGE&paymentType=PAY&bizType=CASH_ACCOUNT
		// &waitPayment=1&approveTime=20140116105434&visitTime=20140116105434&signature=3DF2F775B0E42512CA63C0EE6375D087"
		MobilePayInfo pinfo = super.toPay4Recharge("%s/pay/alipayWAP.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s" +
				"&waitPayment=1&approveTime=%s&visitTime=%s&cashierCode="+cashierCode);
		pinfo.setMessage("<h3>"+pinfo.getMessage()+"</h3>");
		if(pinfo.isSuccess()){
			this.getResponse().sendRedirect(pinfo.getReturnUrl());
		} else {
			this.sendAjaxMsg(pinfo.getMessage());
		}
	}


	public String getCashierCode() {
		return cashierCode;
	}

	public void setCashierCode(String cashierCode) {
		this.cashierCode = cashierCode;
	}

}
