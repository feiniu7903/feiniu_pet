package com.lvmama.pet.refundment.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.refundment.data.ChinapnrRefundCallbackData;
import com.lvmama.pet.refundment.data.RefundCallbackData;

/**
 * 汇付天下退款回调Action.
 * @author sunruyi
 */
@Results( {
	@Result(name = "refund_success", location = "/WEB-INF/pages/refund/refund_success.ftl", type = "freemarker")
	})
public class ChinapnrRefundCallbackAction extends RefundCallbackBaseAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7652649021955414934L;

	@Action("/refund/chinapnrRefundCallback")
	public String execute(){
		return renfedCallback();
	}
	
	@Override
	protected RefundCallbackData getRefundCallbackData() {
		return new ChinapnrRefundCallbackData(getPureParaPair());
	}
}
