package com.lvmama.pet.payment.post.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.PaymentConstant;
import com.lvmama.pet.payment.post.data.BaidupayWapPostData;
import com.lvmama.pet.payment.post.data.PostData;
import com.lvmama.pet.utils.COMMUtil;

public class BaidupayWapAction  extends PayAction {
	
	private static final long serialVersionUID = 627930525268242054L;
	private BaidupayWapPostData baidupayWapPostData;
	
	private String name;
	private String desc;
	private String url;
	
	@Action("/pay/baidupayWap")
	public void baidupayWap() {
		this.name = super.getObjectName();
		this.desc = super.getObjectDesc();
		this.url = super.getObjectPageUrl();
		if (payment()) {
			try {
				getResponse().setCharacterEncoding("GBK");
			    getResponse().setContentType("text/html;charset=GBK");
			    getResponse().setHeader("content-type", "text/html;charset=GBK");
			    getResponse().sendRedirect(PaymentConstant.getInstance().getProperty("BAIDUPAY_WAP_PAYINIT_URL") + "?" + this.baidupayWapPostData.getRequestDate());
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
		if(StringUtils.isNotBlank(name)){
			paramMap.put("goodsName",name);
		}else{
			paramMap.put("goodsName","LVMAMA");
		}
		if(StringUtils.isNotBlank(desc)){
			paramMap.put("goodsDesc", desc);
		}else{
			paramMap.put("goodsDesc", "");
		}
		if(StringUtils.isNotBlank(url)){
			paramMap.put("goodsUrl", url);
		}else{
			paramMap.put("goodsUrl", "");
		}
		baidupayWapPostData=new BaidupayWapPostData(payPayment,paramMap);
		return baidupayWapPostData;
	}

	@Override
	String getGateway() {
		if(StringUtils.isNotBlank(desc)&&("半价".equalsIgnoreCase(desc)||"直减".equalsIgnoreCase(desc))){
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP_ACTIVITIES.name();
		}else{
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_WAP.name();
		}
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate20ByteSerialAttaObjectId(randomId);
	}

	public BaidupayWapPostData getBaidupayWapPostData() {
		return baidupayWapPostData;
	}

	public void setBaidupayWapPostData(BaidupayWapPostData baidupayWapPostData) {
		this.baidupayWapPostData = baidupayWapPostData;
	}

	public static void main(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		map.put("service_code","1");
		map.put("sp_no","9000100005");
		map.put("order_create_time","20140324000000");
		map.put("order_no","111111");
		map.put("goods_name","LVMAMA");
		map.put("goods_desc","");
		map.put("unit_amount","");
		map.put("unit_count","");
		map.put("transport_amount","");
		map.put("total_amount","1");
		map.put("currency","1");
		map.put("buyer_sp_username","");
		map.put("return_url","http://180.169.51.94:8244/payment/pay/baidupayWap_notify.do");
		map.put("page_url","http://180.169.51.94:8244/payment/pay/baidupayWap_callback.do");
		map.put("pay_type","1");
		map.put("bank_no","");
		map.put("expire_time","20140325000000");
		map.put("input_charset","1");
		map.put("version","2");
		map.put("sign_method","1");
		String sign=COMMUtil.getSignature(map,"EPY8umfs9f7sJhtcv2tdLYjK9xHX3BsF");

		StringBuffer params = new StringBuffer();
		params.append("service_code=1");
		params.append("&sp_no=9000100005");
		params.append("&order_create_time=20140324000000");
		params.append("&order_no=111111");
		params.append("&goods_name=LVMAMA");
		params.append("&total_amount=1");
		params.append("&currency=1");
		params.append("&return_url=http://180.169.51.94:8244/payment/pay/baidupayWap_notify.do");
		params.append("&page_url=http://180.169.51.94:8244/payment/pay/baidupayWap_callback.do");
		params.append("&pay_type=1");
		params.append("&expire_time=20140325000000");
		params.append("&input_charset=1");
		params.append("&version=2");
		params.append("&sign_method=1");
		params.append("&sign="+sign);

		System.out.println("https://www.baifubao.com/api/0/pay/0/wapdirect/0?"+params.toString());
	
	}

}