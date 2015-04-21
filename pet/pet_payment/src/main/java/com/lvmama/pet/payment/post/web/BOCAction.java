package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.BOCPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.opensymphony.xwork2.ActionSupport;


/**
 * 中国银行网上支付.
 * @author sunruyi
 */
@Result(name = ActionSupport.SUCCESS,location = "/WEB-INF/pages/forms/boc.ftl",type="freemarker")
public class BOCAction extends PayAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3352868105120391027L;
	/**
	 * 中国银行执行form表单提交.
	 */
	@Action("/pay/boc")
	public String execute(){
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	/**
	 * 获取支付网关名称.
	 * @return 支付网关名称
	 */
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.BOC.name();
	}
	
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		BOCPostData bocPostData = new BOCPostData(payPayment);
		return bocPostData;
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return getserialNoPayTimes();
	}
}
