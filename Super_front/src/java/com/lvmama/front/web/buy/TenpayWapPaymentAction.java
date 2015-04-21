package com.lvmama.front.web.buy;

import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.vo.MobilePayInfo;

public class TenpayWapPaymentAction extends BaseAppPayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8015190126629356698L;
	

	@Action("/tenpayWap/toPay")
	public void toPay() throws Exception {
		MobilePayInfo pinfo = super.toPay("%s/pay/tenpayWap.do?objectId=%s&amount=%s&objectType=%s&paymentType=%s&bizType=%s&signature=%s&objectName=%s&visitTime=%s&approveTime=%s&waitPayment=%s");
		pinfo.setMessage("<h3>"+pinfo.getMessage()+"</h3>");
		if(pinfo.isSuccess()){
			this.getResponse().sendRedirect(pinfo.getReturnUrl());
		} else {
			this.sendAjaxMsg(pinfo.getMessage());
		}
	}
	

}
