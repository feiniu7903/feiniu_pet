package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.CMBInstalmentPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 招商银行网上分期支付.
 * @author fengyu
 */
@Result(name = ActionSupport.SUCCESS, location = "/WEB-INF/pages/forms/cmbInstalment.ftl", type = "freemarker")
public class CMBInstalmentAction extends PayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3331441902438620793L;

	/**
	 * 招商银行执行form表单提交.
	 */
	@Action("/pay/cmbInstalment")
	public String execute() {
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}


	/**
	 * 获取form表单post数据.
	 * 
	 * @param ordOrder
	 *            订单记录
	 * @param ordPayment
	 *            订单支付记录
	 * @return PostData 包装CMBPostData数据
	 */
	PostData getPostData(PayPayment payPayment) {
		CMBInstalmentPostData cmbInstalmentPostData = new CMBInstalmentPostData(payPayment,super.getInstalmentNum());
		return cmbInstalmentPostData;
	}

	/**
	 * 获取支付网关名称.
	 * 
	 * @return 支付网关名称
	 */
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.CMB_INSTALMENT.name();
	}


	@Override
	String getPaymentTradeNo(Long randomId) {
		return getserialNoPayTimes();
	}
}
