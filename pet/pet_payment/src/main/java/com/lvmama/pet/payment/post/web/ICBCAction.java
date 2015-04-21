package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.ICBCPostData;
import com.lvmama.pet.payment.post.data.PostData;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/icbc.ftl", type = "freemarker")
})
/**
 * 工行直连支付
 */
public class ICBCAction extends PayAction {
	
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = -1220042682271545588L;
	
	@Action("/pay/icbc")
	public String pay() {
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		return new ICBCPostData(payPayment);
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.ICBC.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}
