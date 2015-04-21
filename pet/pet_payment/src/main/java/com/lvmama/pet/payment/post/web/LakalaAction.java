package com.lvmama.pet.payment.post.web;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.LakalaPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.utils.LakalaUtil;

/**
 * 
 * @author Alex Wang
 */
public class LakalaAction extends PayAction {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 1279013425331761037L;
	private String lakalaUrl;

	@Action("/pay/lakala")
	public void pay() {
		if(payment()) {
			LakalaPostData postData = (LakalaPostData)getPayInfo();
			lakalaUrl = LakalaUtil.createLakalaURL(postData.getPaymentTradeNo(), postData.getAmountYuan());
			try{
				getResponse().sendRedirect(lakalaUrl);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getLakalaUrl() {
		return lakalaUrl;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.LAKALA.name();
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		return new LakalaPostData(payPayment);
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return PaymentConstant.getInstance().getProperty("LAKALA_BILLNO") +SerialUtil.generate10ByteSerial();
	}

}
