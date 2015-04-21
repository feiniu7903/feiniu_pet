package com.lvmama.pet.payment.post.web;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.utils.SerialUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.payment.post.data.BaidupayAppPostData;
import com.lvmama.pet.payment.post.data.PostData;

public class BaidupayAppAction  extends PayAction {
	
	private static final long serialVersionUID = 4549406337252276542L;
	private BaidupayAppPostData baidupayAppPostData;
	
	private String name;
	private String desc;
	private String url;
	
	@Action("/pay/baidupayApp")
	public void baidupayApp() {
		this.name = super.getObjectName();
		this.desc = super.getObjectDesc();
		this.url = super.getObjectPageUrl();
		if (payment()) {
			try {
				LOG.info("BaidupayAppAction send data:"+baidupayAppPostData.getRequestDate());
				this.sendAjaxMsg(baidupayAppPostData.getRequestDate());
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			//返回提交的字符串给驴妈妈客户端
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
		baidupayAppPostData=new BaidupayAppPostData(payPayment,paramMap);
		return baidupayAppPostData;
	}

	@Override
	String getGateway() {
		if(StringUtils.isNotBlank(desc)&&("半价".equalsIgnoreCase(desc)||"直减".equalsIgnoreCase(desc))){
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_APP_ACTIVITIES.name();
		}else{
			return Constant.PAYMENT_GATEWAY.BAIDUPAY_APP.name();
		}
	}

	@Override
	String getPaymentTradeNo(Long randomId) {
		return SerialUtil.generate20ByteSerialAttaObjectId(randomId);
	}


	public BaidupayAppPostData getBaidupayAppPostData() {
		return baidupayAppPostData;
	}

	public void setBaidupayAppPostData(BaidupayAppPostData baidupayAppPostData) {
		this.baidupayAppPostData = baidupayAppPostData;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}