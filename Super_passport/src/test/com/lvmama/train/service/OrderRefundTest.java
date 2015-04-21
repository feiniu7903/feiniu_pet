package com.lvmama.train.service;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.HttpsUtil;

public class OrderRefundTest {
	private static final String URL = "http://180.169.51.94:5018/passport/v1/notice/ticket_refund_notify.do";
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
//		map.put("merchant_id", "600001");
//		map.put("sign", "3FvjcFqU6awEd3zlMrHdK");
//		map.put("refund_id", "90120130601010203123456");
//		map.put("order_id", "310000060");
//		map.put("refund_type", "502");
//		map.put("ticket_fee", "500");
		String response = HttpsUtil.requestPostForm(URL, map);
		System.out.println(response);
	}
}
