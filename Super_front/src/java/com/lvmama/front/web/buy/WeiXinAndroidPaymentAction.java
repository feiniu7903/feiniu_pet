package com.lvmama.front.web.buy;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.vo.MobilePayInfo;

public class WeiXinAndroidPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;
	

	@Action("/weiXinAndroid/toPay")
	public void toPay() throws Exception {
		MobilePayInfo pinfo = super.toPay("%s/pay/weixinAndroid.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&visitTime=%s&approveTime=%s&waitPayment=%s");
		String responeStr="";
		if(pinfo.isSuccess()){
			responeStr = HttpsUtil.proxyRequestGet(pinfo.getReturnUrl(),InternetProtocol.getRemoteAddr(getRequest()));
			this.sendAjaxMsg(responeStr);
		} else {
			JSONObject json = JSONObject.fromObject(pinfo);
			this.sendAjaxMsg(json.toString());
		}
	}
	

}
