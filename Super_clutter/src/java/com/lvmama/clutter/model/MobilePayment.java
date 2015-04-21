package com.lvmama.clutter.model;

import java.io.Serializable;

import com.lvmama.comm.vo.Constant;

public class MobilePayment implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7860495057734957616L;
	private String paymentCode;
	private boolean recommend;
	private String paymentName;
	private String paymentUrl;
	
	public MobilePayment(String paymentCode,String paymentUrl){
		this.paymentCode = paymentCode;
		this.paymentName = Constant.PAYMENT_GATEWAY.getCnName(paymentCode);
		this.paymentUrl = paymentUrl;
	}
	
	public String getPaymentName() {
		if(Constant.PAYMENT_GATEWAY.ALIPAY_APP.name().equals(paymentCode)){
			this.setRecommend(true);
			return "支付宝支付";
		} else if(Constant.PAYMENT_GATEWAY.ALIPAY_WAP.name().equals(paymentCode)){
			return "支付宝网页支付";
		}else if("UPOMP_OTHER".equals(paymentCode)){
			this.setRecommend(true);
			return "信用卡/储蓄卡支付";
		}  else if(Constant.PAYMENT_GATEWAY.UPOMP.name().equals(paymentCode)){
			return "银联支付";
		} else if(Constant.PAYMENT_GATEWAY.ALIPAY_WAP_CREDITCARD.name().equals(paymentCode)){
			return "信用卡支付";
		} else if(Constant.PAYMENT_GATEWAY.ALIPAY_WAP_DEBITCARD.name().equals(paymentCode)){
			return "储值卡支付";
		} else if("TENPAY_WAP".equals(paymentCode)){	
			return "财付通支付";
		} else if("UPOMP_TEST".equals(paymentCode)){
			this.setRecommend(true);
			return "信用卡/储蓄卡支付";
		}else if("WEIXIN_APP".equals(paymentCode)){
			this.setRecommend(true);
			return "微信支付";
		}

		return paymentName;
	}
	public void setPaymentName(String paymentName) {
		this.paymentName = paymentName;
	}
	public String getPaymentUrl() {
		return paymentUrl;
	}
	public void setPaymentUrl(String paymentUrl) {
		this.paymentUrl = paymentUrl;
	}
	public String getPaymentCode() {
		if(paymentCode.equals("UPOMP_TEST")){
			return Constant.PAYMENT_GATEWAY.UPOMP.name();
		}
		return paymentCode;
	}

	public void setPaymentCode(String paymentCode) {
		this.paymentCode = paymentCode;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}
}
