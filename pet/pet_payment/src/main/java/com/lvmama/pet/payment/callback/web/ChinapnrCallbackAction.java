package com.lvmama.pet.payment.callback.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.pet.payment.callback.data.CallbackData;
import com.lvmama.pet.payment.callback.data.ChinapnrCallbackData;
@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/chinapnr_asyn.ftl", type = "freemarker")
	})
public class ChinapnrCallbackAction extends CallbackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3525445612504421307L;
	private String asynchronousResult = "";
	private ChinapnrCallbackData callbackData;
	
	protected transient final Log log = LogFactory.getLog(getClass());
		
	@Action("/pay/chinapnrAsyn")
	public String execute() {
		log.info("SYNC: chinapnr CALL BACK INVOKED: " + this.getClass().getName());
		String returnPath=callback(false);
		asynchronousResult = "RECV_ORD_ID_" + callbackData.getPaymentTradeNo();
		return returnPath;
	}

	@Override
	CallbackData getCallbackData() {
		callbackData = new ChinapnrCallbackData(super.getPureParaPair());
		return callbackData;
	}

	public String getAsynchronousResult() {
		return asynchronousResult;
	}

	public void setAsynchronousResult(String asynchronousResult) {
		this.asynchronousResult = asynchronousResult;
	}
	
}
