package com.lvmama.train.service;

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.HttpsUtil;

public class ProductUpdateTest {
	private static final String URL = "http://super.lvmama.com/passport/v1/notice/interface_update_notify.do";
	
	public static void main(String[] args) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("merchant_id", "600001");
		map.put("sign", "3FvjcFqU6awEd3zlMrHdK");
		map.put("interface_id", "601");
		map.put("request_type", "all");
		map.put("request_date", "2013-08-01");
		map.put("oper_info", "更新车站列表信息");
		String response = HttpsUtil.requestPostForm(URL, map);
		System.out.println(response);
	}
}
