package com.lvmama.pet.payment.post.web;

import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.TenpayPostData;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/tenpay.ftl", type = "freemarker")
})
public class TenpayAction extends PayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331379248003367546L;
	
	private String bankType="";
	
	@Action("/pay/tenpay")
	public String pay() {
		this.bankType="DEFAULT";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	
	@Action("/pay/tenpayDirectpay")
	public String payDirectpay() {
		this.bankType="OC";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	
	
	
	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("spbill_create_ip", this.getRequest().getHeader("X-Real-IP"));
		paramMap.put("objectName", super.getObjectName());
		return new TenpayPostData(payPayment, paramMap,bankType);
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.TENPAY.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}

	
	
	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}
}
