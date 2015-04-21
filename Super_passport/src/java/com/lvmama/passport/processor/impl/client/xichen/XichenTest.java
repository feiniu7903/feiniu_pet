package com.lvmama.passport.processor.impl.client.xichen;
import java.util.LinkedHashMap;
import java.util.Map;

import com.lvmama.passport.processor.impl.client.xichen.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.xichen.model.SubmitOrderBean;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

public class XichenTest {
	public static void main(String[] args)  {
		try{
		//获取景区信息
//		String unixTimestamp1 =String.valueOf(System.currentTimeMillis()/1000);
//		Map<String, String> data = new LinkedHashMap<String, String>();
//		data.put("username","驴妈妈");
//		data.put("time",unixTimestamp1);
//		String md5key1=XiChenClient.makeSign(data);
//		data.put("key",md5key1);
//		String targetUrl1 ="http://www.028xcpw.com/dlapi/getSpotList?username=驴妈妈"
//				+ "&time="+ unixTimestamp1 
//				+ "&key=" + md5key1;
//		String result1= HttpsUtil.requestGet(targetUrl1,"utf-8",10000,180000);
//		System.out.println(result1);
		
		//获取门票列表
//		String unixTimestamp1 =String.valueOf(System.currentTimeMillis()/1000);
//		Map<String, String> data = new LinkedHashMap<String, String>();
//		data.put("username","驴妈妈");
//		data.put("time",unixTimestamp1);
//		data.put("sid", "8");
//		String md5key1=XiChenClient.makeSign(data);
//		data.put("key",md5key1);
//		String targetUrl1 ="http://www.028xcpw.com/dlapi/getTicketList?username=驴妈妈"
//				+ "&time="+ unixTimestamp1 
//				+"&sid=8"
//				+ "&key=" + md5key1;
//		String result1= HttpsUtil.requestGet(targetUrl1,"utf-8",10000,180000);
//		System.out.println(result1);	
		
		//下单测试
//		String unixTimestamp =String.valueOf(System.currentTimeMillis()/1000);
//		SubmitOrderBean bean=new SubmitOrderBean();
//		bean.setName("测试单");
//		bean.setUsername("驴妈妈1");
//		bean.setNum("1");
//		bean.setPhone("15026847838");
//		bean.setTid("57");//101 //57
//		bean.setIdcard("420621198810141243");
//		Map<String, String> params = new LinkedHashMap<String, String>();
//		params.put("username",bean.getUsername());
//		params.put("time",String.valueOf(unixTimestamp));
//		params.put("tid",bean.getTid());
//		params.put("num",bean.getNum());
//		params.put("name",bean.getName());
//		params.put("idcard",bean.getIdcard());
//		params.put("phone",bean.getPhone());
//		params.put("needmsg","0");//是否需要返回短信内容默认是0不需要
//		String md5key=XiChenClient.makeSign(params);
//		String targetUrl =WebServiceConstant.getProperties("xichen.order.send.url")
//		+ "?username=" +bean.getUsername()
//		+ "&time=" + unixTimestamp 
//		+ "&tid=" +bean.getTid()
//		+ "&num=" + bean.getNum()
//		+ "&name=" +bean.getName() 
//		+ "&idcard="+bean.getIdcard()
//		+ "&phone=" +bean.getPhone()
//		+ "&needmsg=0"
//		+ "&key=" + md5key;
//		System.out.println(targetUrl);
//		String result = HttpsUtil.requestGet(targetUrl,"utf-8",10000,180000);
//		String result="{\"errorNo\":\"0\",\"msg\":\"操作成功!\",\"code\":\"8000736422444293\",\"orderid\":\"13850013414512\"}";
//		String test="{\"errorNo\":\"0\",\"msg\":\"操作成功!\",\"code\":\"8000736422444293\",\"orderid\":\"13850013414512\"}";
//		//﻿{"errorNo":"0","msg":"操作成功!","code":"8000206943969925","orderid":"13850036253958"}
//		result=result.substring(result.indexOf("{"));
//		OrderResponse order=XiChenClient.parseOrderResponse(result);
//		System.out.println("===code:=="+order.getCode());
//		System.out.println("===errorMsg===="+order.getErrorMsg());
//		System.out.println("===errorno===="+order.getErrorNo());
//		System.out.println("----orderId-----"+order.getOrderId());
		//1384766087571
//		//获取订单信息
//		String unixTimestamp2 =String.valueOf(System.currentTimeMillis()/1000);
//		Map<String, String> data2 = new LinkedHashMap<String, String>();
//		data2.put("username","驴妈妈");
//		data2.put("time",unixTimestamp2);
//		data2.put("orderid", "1384766087571");
//		String md5key1=XiChenClient.makeSign(data2);
//		data2.put("key",md5key1);
//		String targetUrl1 ="http://www.028xcpw.com/dlapi/getOrder?username=驴妈妈"
//				+ "&time="+ unixTimestamp2 
//				+"&orderid=1384766087571"
//				+ "&key=" + md5key1;
//		String result1= HttpsUtil.requestGet(targetUrl1,"utf-8",10000,180000);
//		System.out.println(result1);	
		
//		String str1="欢迎订购欢乐谷实体票，订单号：13848398884355。请于9-13点至欢乐谷星巴克咖啡门口联系02886060895取票【西晨票务】";
//		String str2="欢迎订购万达电子票2D，效验码:8000727728898324。凭短信于万达院线移动POS机打印凭条换票，打印后当天有效。有效期至12月31日，3D需柜台加10元【西晨票务】";
//		int index1=str1.indexOf("订单号：")+4;
//		int index2=str2.indexOf("效验码:")+4;
//		System.out.println(str1.substring(index1,index1+14));
//		System.out.println(str2.substring(index2,index2+16));
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
