package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.COMMPosPostData;
import com.lvmama.pet.payment.post.data.PostData;

/**
 * 显示Pos机代码的Action
 * @author fengyu
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/pay/payPosCode.ftl", type = "freemarker")
})
public class COMMPosAction extends PayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331379248003367546L;

	private String posPaymentType;
	@Action("/pay/payPos")
	public String query() {
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		COMMPosPostData data = new COMMPosPostData(payPayment);
		return data;
	}

	@Override
	String getGateway() {
		if(Constant.PAYMENT_GATEWAY.COMM_POS.name().equals(posPaymentType)){
			return Constant.PAYMENT_GATEWAY.COMM_POS.name();	
		}
		else{
			return Constant.PAYMENT_GATEWAY.COMM_POS_CASH.name();
		}
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return getserialNoPayTimes();
	}

	public String getPosPaymentType() {
		return posPaymentType;
	}

	public void setPosPaymentType(String posPaymentType) {
		this.posPaymentType = posPaymentType;
	}
}
