package com.lvmama.train.service;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.HttpsUtil;

public class TicketIssueTest {
	private static final String URL = "http://180.169.51.94:5018/passport/v1/notice/ticket_issued_notify.do";
	
	
	public static void main(String[] args) {
		String json = "[{\"ticket_id\":901,\"passenger_name\":\"孙悟空\",\"ticket_class\":204,\"ticket_type\":301,\"ticket_price\":533,\"ticket_seat\":\"10车厢12A\",\"id_type\":401,\"id_num\":\"320123191201234567\"}," +
				"{\"ticket_id\":902,\"passenger_name\":\"孙尚香\",\"ticket_class\":203,\"ticket_type\":302,\"ticket_price\":933,\"ticket_seat\":\"07车厢21B\",\"id_type\":402,\"id_num\":\"HZ1234567890\"}]";
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", "600001");
		map.put("sign", "3FvjcFqU6awEd3zlMrHdK");
		map.put("order_id", "123456");
		map.put("order_status", "804");
		map.put("order_msg", "出票成功");
		map.put("json_param", json);
		String response = HttpsUtil.requestPostForm(URL, map);
		System.out.println(response);
	}
}	
