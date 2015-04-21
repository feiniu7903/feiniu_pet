package com.lvmama.front.web.buy;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.vo.MobilePayInfo;
import com.tenpay.api.common.util.HttpClientUtil;

public class UpompPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;

	@Action("/upompPay/toPay")
	public void toPay() throws Exception {
		MobilePayInfo pinfo = super.toPay("%s/pay/upompPay.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&visitTime=%s&approveTime=%s&waitPayment=%s");
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
	
}
