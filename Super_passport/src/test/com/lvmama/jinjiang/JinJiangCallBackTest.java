package com.lvmama.jinjiang;

import java.util.Date;

import org.junit.Test;

import com.lvmama.jinjiang.comm.JinjiangUtil;
import com.lvmama.jinjiang.model.request.LineCodesRequest;
import com.lvmama.passport.utils.HttpsUtil;

public class JinJiangCallBackTest {
	@Test
	public void testNotifyCancelOrder() {
		// String url = "http://super.lvmama.com/passport/jinjiang/notifyCancelOrder.do";
		// http://180.169.51.94/passport/jinjiang/notifyCancelOrder.do
		// http://114.80.83.165/passport/jinjiang/notifyCancelOrder.do
		
		String url = "http://180.169.51.94/passport/jinjiang/notifyCancelOrder.do";
		String json = "{\"channelCode\":\"LV\",\"ciphertext\":\"9244CD592D5C2CB4B790F22708B889DF\",\"lossAmount\":1000,\"orderNo\":\"1000140414000168\",\"orderStatus\":\"CANCELED\",\"payStatus\":\"PAY_REFUNDED\",\"refundRemark\":\"\",\"thirdOrderNo\":\"LVMAMA_TEST_003\",\"timestamp\":\"1398067448883\"}";
		String responseJson = HttpsUtil.requestPostJson(url, json.toString());
		
		System.out.println("responseJson = " +  responseJson);
	}
	
	@Test
	public void test() {
		String channelCode = "LV";
		String ciphertext2 = "9244CD592D5C2CB4B790F22708B889DF";
		String timestamp = "1398067448883";
		
		LineCodesRequest request = new LineCodesRequest(new Date(), new Date());
		request.setChannelCode(channelCode);
		request.setCiphertext(ciphertext2);
		request.setTimestamp(timestamp);
		
		// 根据通道 + 时间戳 + 密钥
		String ciphertext = JinjiangUtil.ciphertextEncode(request.getChannelCode(), request.getTimestamp());
		if(ciphertext.equals(request.getCiphertext())){
			System.out.println("true");
		} else {
			System.out.println("false");
		}
	}
}
