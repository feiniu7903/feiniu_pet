package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PaymentQueryReturnInfo;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.NingboBankPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.service.PaymentQueryServiceFactory;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/ningboBank.ftl", type = "freemarker")
})
public class NingboBankAction extends PayAction {
	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 7999045749706997771L;
	
	private String paymethod="";
	
	
	@Action("/pay/ningboBank")
	public String pay() {
		 paymethod="bankPay";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	@Action("/pay/ningboBankDirectpay")
	public String directpayPay() {
		paymethod="directPay";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	@Action("/pay/ningboBankExpressGatewayCredit")
	public String payExpressGatewayCredit() {
		paymethod="expressGatewayCredit";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	@Action("/pay/ningboBankQuery")
	public String ningboBankQuery() {
		PaymentQueryServiceFactory paymentQueryServiceFactory = (PaymentQueryServiceFactory)SpringBeanProxy.getBean("paymentQueryServiceFactory");
		PayPayment info = new PayPayment();
		info.setPaymentGateway(Constant.PAYMENT_GATEWAY.NING_BO_BANK.name());
		info.setGatewayTradeNo("");
		info.setPaymentTradeNo(this.getObjectIds());	//临时测试
		PaymentQueryReturnInfo paymentQueryReturnInfo = paymentQueryServiceFactory.query(info);
		log.info(paymentQueryReturnInfo);
		return null;
	}
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		return new NingboBankPostData(payPayment,paymethod,bankid);
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.NING_BO_BANK.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
	
	
	public String getPaymethod() {
		return paymethod;
	}
	public void setPaymethod(String paymethod) {
		this.paymethod = paymethod;
	}
}
