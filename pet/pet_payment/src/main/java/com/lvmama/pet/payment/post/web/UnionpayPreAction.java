package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.UnionpayPrePostData;


/**
 * 银联在线预授权支付. 
 * @author liwenzhan
 *
 */
@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/unionpay.ftl", type = "freemarker")
})
public class UnionpayPreAction extends PayAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5400101847174107012L;
	/**
	 * 
	 */
	@Action("/pay/chinaPre")
	public String execute(){
		if (payment()) {
			return SUCCESS;
		}else{
			return ERRORNO;
		}
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.CHINAPAY_PRE.name();
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		return new UnionpayPrePostData(payPayment,
				InternetProtocol.getRemoteAddr(super.getRequest()));
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
	
}
