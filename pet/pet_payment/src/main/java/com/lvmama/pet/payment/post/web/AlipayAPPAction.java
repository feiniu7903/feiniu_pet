package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.AlipayAPPPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.utils.AlipayUtil;

public class AlipayAPPAction  extends PayAction {
	
	private static final long serialVersionUID = 4749702919428788667L;
	
	private AlipayAPPPostData alipayAPPPostData;
	
	@Action("/pay/alipayAPP")
	public void alipayAPP() {
		if (payment()) {
			try {
				LOG.info("alipayAPP request data:"+StringUtil.printParam(alipayAPPPostData));
				String signData=alipayAPPPostData.getSignData();
				String sign = AlipayUtil.signRSA(signData,alipayAPPPostData.getPrivateKey());
				signData+="&sign_type=\""+alipayAPPPostData.getSignType()+"\"";
				String strReString =signData+"&sign=\""+URLEncoder.encode(sign,alipayAPPPostData.getCharset())+"\"";
				LOG.info("response data:"+strReString);
				//返回提交的字符串给驴妈妈客户端
				this.sendAjaxMsg(strReString);
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
		paramMap.put("objectName", super.getObjectName());
		alipayAPPPostData = new AlipayAPPPostData(payPayment,paramMap,this.getRequestParameter("extern_token"));
		return alipayAPPPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.ALIPAY_APP.name();
	}




	public AlipayAPPPostData getAlipayAPPPostData() {
		return alipayAPPPostData;
	}
	public void setAlipayAPPPostData(AlipayAPPPostData alipayAPPPostData) {
		this.alipayAPPPostData = alipayAPPPostData;
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