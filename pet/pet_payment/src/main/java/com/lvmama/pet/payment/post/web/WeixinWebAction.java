package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.http.ParseException;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.payment.post.data.WeixinWebPostData;

public class WeixinWebAction  extends PayAction {
	
	private static final long serialVersionUID = 805763256669669537L;
	private WeixinWebPostData weixinWebPostData;
	
	@Action("/pay/weixinWeb")
	public void weixinWeb() {
		if (payment()) {
			try {
				String payURL = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PAY_URL") + "?"+weixinWebPostData.getRequestData();
				log.info("payURL= "+payURL);
				getResponse().sendRedirect(payURL);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}else {
			log.info("weixinWeb payment() fail!");
		}
	}

	@Override
	PostData getPostData(PayPayment payPayment) {
		Map<String,Object> paramMap = new HashMap<String,Object>();
		paramMap.put("waitPayment", super.getWaitPayment());
		paramMap.put("visitTime", super.getVisitTime());
		paramMap.put("approveTime", super.getApproveTime());
		paramMap.put("spbill_create_ip", InternetProtocol.getRemoteAddr(this.getRequest()));
		weixinWebPostData=new WeixinWebPostData(payPayment,paramMap);
		return weixinWebPostData;
	}

	@Override
	String getGateway() {
		return Constant.PAYMENT_GATEWAY.WEIXIN_WEB.name();
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate24ByteSerialAttaObjectId(randomId);
	}
	
	public WeixinWebPostData getWeixinWebPostData() {
		return weixinWebPostData;
	}

	public void setWeixinWebPostData(WeixinWebPostData weixinWebPostData) {
		this.weixinWebPostData = weixinWebPostData;
	}

	@Action("/pay/testWeixinWeb")
	public void testWeixinWeb() {
		try {
			PayPayment payPayment = new PayPayment();
			payPayment.setAmount(1L);
			payPayment.setBizType("bizType");
			String paymentTradeNo="";
			for(int i=0; i<7; i++) {
				paymentTradeNo += new Random().nextInt(10);
			}
			payPayment.setPaymentTradeNo(paymentTradeNo);
			this.getPostData(payPayment);
			String payURL = PaymentConstant.getInstance().getProperty("WEIXIN_WEB_PAY_URL") + "?"+weixinWebPostData.getRequestData();
			log.info("payURL= "+payURL);
			getResponse().sendRedirect(payURL);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		String strReString;
		try {
			strReString = HttpsUtil.requestGet("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=1218430901&secret=8505543e01b2472a03233e6b40e5ca16");
			System.out.println(strReString);
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		
	}

}