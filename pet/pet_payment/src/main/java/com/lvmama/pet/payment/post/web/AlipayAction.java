package com.lvmama.pet.payment.post.web;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.alipay.config.AlipayConfig;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.AlipayPostData;
import com.lvmama.pet.payment.post.data.PostData;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/alipay.ftl", type = "freemarker")
})
public class AlipayAction extends PayAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1331379248003367546L;
	private String token;
	/**
	 * 扫码支付方式 空=不使用，2=跳转模式扫码
	 */
	private String qr_pay_mode="";
	
	@Action("/pay/alipay")
	public String pay() {
		initToken();
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	@Action("/pay/alipayScannerCode")
	public String scannerCodePay() {
		initToken();
		qr_pay_mode="2";
		if(payment()){
			return SUCCESS;
		} else {
			return ERRORNO;
		}
	}
	/**
	 * 读取登录时保存的cookie
	 */
	private void initToken()
	{
		Cookie[] cookies=getCookies();
		if(!ArrayUtils.isEmpty(cookies))
		{
			for(Cookie ck:cookies)
			{
				if(StringUtils.equals(ck.getName(), AlipayConfig.ALIPAY_TOKEN_KEY))
				{
					token=ck.getValue();
					break;
				}
			}
		}
	}
	@Override
	PostData getPostData(PayPayment payPayment) {
		AlipayPostData data=null;
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("royaltyParameters", super.getRoyaltyParameters());
		paramMap.put("qr_pay_mode", qr_pay_mode);
		paramMap.put("objectName", super.getObjectName());
		if (bankid==null) {
			data=new AlipayPostData(payPayment,paramMap);
		}else{
			data=new AlipayPostData(payPayment,paramMap,bankid);
		}
		data.setToken(token);
		return data;
	}
	
	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		//充值标识
		String cashRecharge="";
		if(Constant.PAYMENT_BIZ_TYPE.CASH_ACCOUNT.name().equals(super.getBizType())){
			cashRecharge=Constant.ALIPAY_TRANSACTION_TYPE_PREFIX.R.name();
		}
		return cashRecharge+SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
	
	
	
}
