package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.COMMPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 交通银行支付.
 * 
 * <pre>
 * 详情请参考接口文档
 * </pre>
 * 
 * @author 李文战
 * @version Super 一期 2011/06/29
 * @since Super一期
 */
@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/pages/forms/comm.ftl", type = "freemarker")
public final class COMMAction extends PayAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4562273517973512835L;

	/**
	 * 交通银行执行form表单提交.
	 * 
	 * @return /WEB-INF/pages/forms/comm.ftl
	 */
	@Action("/pay/comm")
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
		return Constant.PAYMENT_GATEWAY.COMM.name();
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		COMMPostData commPostData = new COMMPostData(payPayment);
		return commPostData;
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return getserialNoPayTimes();
	}
}
