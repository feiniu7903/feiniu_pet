package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.UnionpayPostData;

/**
 * 银联
 * 
 * @author fengyu
 * 
 */
@Results({ @Result(name = "success", location = "/WEB-INF/pages/forms/unionpay.ftl", type = "freemarker") })
public class UnionpayAction extends PayAction {
	
	private static final long serialVersionUID = 5400101847174107012L;
	private PaymentConstant pc = PaymentConstant.getInstance();
	/**
	 * 银联 默认的首选交易支付方式
	 */
	private String defaultPayType;
	
	@Action("/pay/unionpay")
	public String unionpay() {
		defaultPayType=pc.getProperty("UNIONPAY_DEFAULT_PAY_TYPE");
		if (payment()) {
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	@Action("/pay/unionpayDirect")
	public String unionpayDirect() {
		defaultPayType=pc.getProperty("UNIONPAY_DIRECT_PAY_TYPE");
		if (payment()) {
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.UNIONPAY.name();
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		return new UnionpayPostData(payPayment,InternetProtocol.getRemoteAddr(super.getRequest()),defaultPayType);
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}
