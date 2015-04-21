package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.TenpayWapPostData;
import com.lvmama.pet.utils.StringDom4jUtil;

public class TenpayWapAction  extends PayAction {
	
	private static final long serialVersionUID = 805763256669669537L;
	private TenpayWapPostData tenpayWapPostData;
	
	@Action("/pay/tenpayWap")
	public void tenpayWap() {
		if (payment()) {
			try {
				LOG.info("tenpayWap request init data:"+tenpayWapPostData.getRequestDate());
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("TENPAY_WAP_PAYINIT_URL"), tenpayWapPostData.getRequestDate(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				//返回提交的字符串给驴妈妈客户端
				Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(strReString);
				if(StringUtils.isNotBlank(resultMap.get("token_id"))){
//					this.sendAjaxMsg(resultMap.get("token_id"));	
					String redirectURL = PaymentConstant.getInstance().getProperty("TENPAY_WAP_PAY_URL") + "?token_id="+resultMap.get("token_id");
					getResponse().sendRedirect(redirectURL);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("objectName", super.getObjectName());
		tenpayWapPostData=new TenpayWapPostData(payPayment,paramMap);
		return tenpayWapPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.TENPAY_WAP.name();
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}

	public TenpayWapPostData getTenpayWapPostData() {
		return tenpayWapPostData;
	}

	public void setTenpayWapPostData(TenpayWapPostData tenpayWapPostData) {
		this.tenpayWapPostData = tenpayWapPostData;
	}	
	
}