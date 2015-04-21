package com.lvmama.train.service;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.HttpsUtil;

public class TicketDrawbackTest {
	private static final String URL = "http://super.lvmama.com/passport/v1/notice/ticket_drawback_notify.do";
	
	
	public static void main(String[] args) {
		String json = "[{\"ticket_id\":901,\"ticket_price\":533,\"ticket_status\":0,\"ticket_msg\":\"退票成功\"}," +
				"{\"ticket_id\":902,\"ticket_price\":933,\"ticket_status\":1,\"ticket_msg\":\"退票失败\"}]";
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", "600001");
		map.put("sign", "3FvjcFqU6awEd3zlMrHdK");
		map.put("refund_id", "90120130601010203123456");
		map.put("order_id", "310000060");
		map.put("ticket_num", "2");
		map.put("json_param", json);
		String response = HttpsUtil.requestPostForm(URL, map);
		System.out.println(response);
	}
}
