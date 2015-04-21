package com.lvmama.train.web;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;
import org.junit.Before;
import org.junit.Test;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.train.TrainTicketSign;

public class TicketIssueActionTest {
	
	private String order_id="80120130922175100383860";
	private String order_status="806";
	private String merchant_id="600001";
	private String order_msg="";
	private String json_param="";
	
	@Before
	public void doBefore(){
		StringBuffer sb = new StringBuffer();
		sb.append("[");
		//json_param=[{"ticket_id":"900","passenger_name":"徐跟成","ticket_class":"0","ticket_type":"301","ticket_price":"0.00","ticket_seat":"","id_type":"401","id_num":"370725198510271237"}]&merchant_id=600001&order_id=80120130922151349224894&order_msg=null&order_status=809
		//json_param=[{"id_num":"370725198510271237","id_type":401,"passenger_name":"徐跟成","phone_num":"18817683516","ticket_class":216,"ticket_price":278.5,"ticket_type":301}
//		sb.append("{\"ticket_id\":901,\"passenger_name\":\"孙悟空\",\"ticket_class\":204,\"ticket_type\":301,\"ticket_price\":533,\"ticket_seat\":\"10车厢12A\",\"id_type\":401,\"id_num\":\"320123191201234567\"}");
		sb.append("{\"ticket_id\":901,\"id_num\":\"370725198510271237\",\"id_type\":401,\"passenger_name\":\"徐跟成\",\"phone_num\":\"18817683516\",\"ticket_class\":216,\"ticket_price\":278.5,\"ticket_type\":301,\"ticket_seat\":\"10车厢12A\"}");
		//{"ticket_id":902,"passenger_name":"孙尚香","ticket_class":203,"ticket_type":302,"ticket_price":933,"ticket_seat":"07车厢21B","id_type":402,"id_num":"HZ1234567890"}
		sb.append("]");
		json_param=sb.toString();
	}

	@Test
	public void testIssue() {
		Map<String,String> map = new HashMap<String, String>();
		map.put("merchant_id", merchant_id);
		map.put("order_id", order_id);
		map.put("order_status", order_status);
		map.put("order_msg", order_msg);
		map.put("json_param", json_param);
		try {
			TrainTicketSign sign = new TrainTicketSign();
			String value=sign.sign("6ecd6d6d326eb22b9ead5fa986d6d9bf", "POST", "/v1/notice/ticket_issued_notify.do", map);
			map.put("sign", value);
			HttpResponseWrapper wrap = HttpsUtil.requestPostFormResponse2("http://192.168.0.243/passport/v1/notice/ticket_issued_notify.do", map,60);
			System.out.println(wrap.getResponseString());
		} catch (HttpException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
