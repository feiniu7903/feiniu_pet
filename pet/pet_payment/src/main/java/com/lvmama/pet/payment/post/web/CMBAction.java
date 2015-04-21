package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.CMBPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 招商银行支付.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 * @see com.lvmama.payment.data.post.PostData;
 * @see com.lvmama.payment.data.post.CMBPostData;
 */
@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/pages/forms/cmb.ftl", type = "freemarker")
public final  class CMBAction extends PayAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8563186574539473090L;

	/**
	 * 招商银行执行form表单提交.
	 * 
	 * @return /WEB-INF/pages/forms/cmb.ftl
	 */
	@Action("/pay/cmb")
	public String execute() {
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	
	/**
	 * 获取支付网关名称.
	 * 
	 * @return 支付网关名称
	 */
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.CMB.name();
	}
 
	@Override
	PostData getPostData(PayPayment payPayment) {
		return new CMBPostData(payPayment);
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return getserialNoPayTimes();
	}
 
}
