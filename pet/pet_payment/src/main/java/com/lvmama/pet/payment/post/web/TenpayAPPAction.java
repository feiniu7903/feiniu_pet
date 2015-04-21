package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.TenpayAPPPostData;
import com.lvmama.pet.utils.StringDom4jUtil;

public class TenpayAPPAction  extends PayAction {
	
	private static final long serialVersionUID = 4973403411212290998L;
	private TenpayAPPPostData tenpayAPPPostData;
	private String salePlat="";
	
	@Action("/pay/tenpayIphoneAPP")
	public void tenpayIphoneAPP() {
		this.salePlat="201";
		if (payment()) {
			try {
				LOG.info("tenpayWap request init data:"+tenpayAPPPostData.getRequestData());
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("TENPAY_APP_PAYINIT_URL"), tenpayAPPPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				//返回提交的字符串给驴妈妈客户端
				Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(strReString);
				this.sendAjaxMsg(resultMap.get("token_id"));
			} catch (IOException e) {
				this.sendAjaxMsg("error");
				e.printStackTrace();
			}
		} else {
			this.sendAjaxMsg("error");
		}
	}
	
	@Action("/pay/tenpayAndroidAPP")
	public void tenpayAndroidAPP() {
		this.salePlat="211";
		if (payment()) {
			try {
				String strReString = HttpsUtil.requestPostData(PaymentConstant.getInstance().getProperty("TENPAY_APP_PAYINIT_URL"), tenpayAPPPostData.getRequestData(), "application/x-www-form-urlencoded", "UTF-8").getResponseString("UTF-8");
				LOG.info("response data:"+strReString);
				//返回提交的字符串给驴妈妈客户端
				Map<String, String> resultMap=StringDom4jUtil.parseTenpayRefundResult(strReString);
				this.sendAjaxMsg(resultMap.get("token_id"));
			} catch (IOException e) {
				this.sendAjaxMsg("error");
				e.printStackTrace();
			}
		} else {
			this.sendAjaxMsg("error");
		}
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("salePlat", salePlat);
		paramMap.put("objectName", super.getObjectName());
		tenpayAPPPostData=new TenpayAPPPostData(payPayment,paramMap);
		return tenpayAPPPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.TENPAY_APP.name();
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}	
	
	public TenpayAPPPostData getTenpayAPPPostData() {
		return tenpayAPPPostData;
	}

	public void setTenpayAPPPostData(TenpayAPPPostData tenpayAPPPostData) {
		this.tenpayAPPPostData = tenpayAPPPostData;
	}

	public String getSalePlat() {
		return salePlat;
	}

	public void setSalePlat(String salePlat) {
		this.salePlat = salePlat;
	}

}