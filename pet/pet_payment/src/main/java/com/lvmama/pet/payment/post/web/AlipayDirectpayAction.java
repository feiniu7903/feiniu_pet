package com.lvmama.pet.payment.post.web;

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
import com.lvmama.pet.payment.post.data.AlipayDirectpayPostData;
import com.lvmama.pet.payment.post.data.PostData;

@Results({
	@Result(name = "success", location = "/WEB-INF/pages/forms/alipayDirectpay.ftl", type = "freemarker")
})
public class AlipayDirectpayAction  extends PayAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180581231180895031L;
	private String token;
	
	@Action("/pay/alipayDirectpay")
	public String execute() {	
		initToken();
		if (payment()) {
			return SUCCESS;
		}else{
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
		AlipayDirectpayPostData data = new AlipayDirectpayPostData(payPayment,super.getApproveTime(),super.getVisitTime(),super.getWaitPayment(),super.getObjectName());
		data.setToken(token);
		return data;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_DIRECTPAY.name();
	}
	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
}