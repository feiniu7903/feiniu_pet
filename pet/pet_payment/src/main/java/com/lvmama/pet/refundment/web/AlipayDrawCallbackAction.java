package com.lvmama.pet.refundment.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.vo.Constant;


@Results( {
	@Result(name = "asyn", location = "/WEB-INF/pages/pay/alipay_callback_asyn.ftl", type = "freemarker")
	})
public class AlipayDrawCallbackAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	private String asynchronousResult;
	
	private String notifyType;
	
	@Action("/pay/alipay_draw")
	public String draw() {
		try{
			if (notifyType==null) {
				asynchronousResult = "fail";
				return "error";
			}
			log.info("===============================================");
			log.info("CALL BACK INVOKED: " + this.getClass().getName());
			log.info("notifyType = " + notifyType);
			
			NotifyDrawMoney notifyDrawMoney = null;
			// 向支付宝打款的异步通知 batch_trans_notify
			if(Constant.ALIPAY_NOTIFY_TYPE.batch_trans_notify.name().equalsIgnoreCase(notifyType)) { 
				notifyDrawMoney = new NotifyFromAlipayToAlipay(super.getParameters());
			} else {
				// 向银行打款的异步通知  bptb_result_file
				notifyDrawMoney = new NotifyFromAlipayToBank(super.getParameters());
			}
			if (notifyDrawMoney.isComefromAlipay() && notifyDrawMoney.checkSignature()) {
				if(notifyDrawMoney.process()) {
					asynchronousResult = "success";
					return "asyn";
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		asynchronousResult = "fail";
		return "error";
	}

		
	public void setNotify_type(String notifyType) {
		this.notifyType = notifyType;
	}
	
	public String getAsynchronousResult() {
		return asynchronousResult;
	}

}
