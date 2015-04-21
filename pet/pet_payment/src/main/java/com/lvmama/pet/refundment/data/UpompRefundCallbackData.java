package com.lvmama.pet.refundment.data;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.vo.PaymentConstant;
import com.merPlus.PlusTools;

public class UpompRefundCallbackData implements RefundCallbackData {

	private String xml;
	private Map<String, String> dataMap = new HashMap<String, String>();
	public UpompRefundCallbackData(String xml){
		this.xml=xml;
		dataMap=PlusTools.parseXml(xml);
	}
	@Override
	public boolean isSuccess() {
		String respCode=dataMap.get("cupsRespCode");
		return PaymentConstant.BYPAY_SUCCESS_KEY.equalsIgnoreCase(respCode) || "00".equalsIgnoreCase(respCode);
	}

	@Override
	public boolean checkSignature() {
		return PlusTools.checkSign(xml, PaymentConstant.getInstance().getProperty("UPOMP_CERT_PATH"));
	}

	@Override
	public String getSerial() {
		return dataMap.get("merchantOrderId");
	}

	@Override
	public String getCallbackInfo() {
		return dataMap.get("cupsRespDesc");
	}
}
