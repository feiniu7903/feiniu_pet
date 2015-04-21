package org.sendinfo.piaowubao.utils;

import junit.framework.TestCase;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

public class OrderInfoUtils extends TestCase{
	/**
	 * xml属性解析,单元测试可以通过，但是Web应用解析时出错
	 */
	@Test
	public void testString() throws Exception {
		String xml="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root msgId=\"0\" msg=\"订单成功\"><orderList wayType=\"1\" status=\"新建订单\" sellerCode=\"0000000163\" outOrderNo=\"1201668\" orderNo=\"0000000465\" mobile=\"13735875217\" linkman=\"庄俊安\" idCard=\"330721198204221052\"><ticketDetail travelDate=\"2011-01-12\" ticketCode=\"0000000314\" price=\"300.0\" amount=\"3\"/></orderList></root>";
		String xml2="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?><root msgId=\"1\" msg=\"购票协议过期!\"/>";
		String xml3="<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" +
				"<root msgId=\"0\" msg=\"订单成功\">" +
				"<order wayType=\"1\" status=\"新建订单\" sellerCode=\"0000000163\" outOrderNo=\"20110112120\" orderNo=\"0000000496\" mobile=\"13816681075\" linkman=\"ceshi\" idCard=\"\">" +
				"<ticketDetail travelDate=\"2011-01-13\" ticketCode=\"0000000314\" price=\"300.0\" amount=\"1\"/>" +
				"</order>" +
				"</root>";
//		assertEquals("0000000496", SzParkStringUtils.getAttributeValue(xml3,"orderNo"));
//		assertEquals("1", SzParkStringUtils.getAttributeValue(xml2,"msgId"));
		String jsion="[{\"$id\":\"1\",\"ClassID\":1,\"ClassName\":\"家庭卡\",\"AddTime\":\"fdgret\",\"Content\":\"适用于两名成人和一名1.4以下的儿童\",\"UpDateBy\":\"admin\",\"UpDateTime\":\"hjhj\",\"IsDelete\":null,\"Price\":780.00,\"DiscountPrice\":740.00,\"Type\":\"年卡\",\"EntityKey\":{\"$id\":\"2\",\"EntitySetName\":\"HappyCard_Class\",\"EntityContainerName\":\"hzspEntities\",\"EntityKeyValues\":[{\"Key\":\"ClassID\",\"Type\":\"System.Int32\",\"Value\":\"1\"}]}},{\"$id\":\"3\",\"ClassID\":2,\"ClassName\":\"成人卡\",\"AddTime\":\"dfssf\",\"Content\":\"适用于一名成人或1.4米以上儿童\",\"UpDateBy\":\"admin\",\"UpDateTime\":\"rrrr\",\"IsDelete\":null,\"Price\":325.00,\"DiscountPrice\":308.00,\"Type\":\"年卡\",\"EntityKey\":{\"$id\":\"4\",\"EntitySetName\":\"HappyCard_Class\",\"EntityContainerName\":\"hzspEntities\",\"EntityKeyValues\":[{\"Key\":\"ClassID\",\"Type\":\"System.Int32\",\"Value\":\"2\"}]}},{\"$id\":\"5\",\"ClassID\":3,\"ClassName\":\"半价卡\",\"AddTime\":\"ggg\",\"Content\":\"适用于一名1.1米以上1.4米以下的儿童\",\"UpDateBy\":\"admin\",\"UpDateTime\":\"\",\"IsDelete\":null,\"Price\":160.00,\"DiscountPrice\":160.00,\"Type\":\"年卡\",\"EntityKey\":{\"$id\":\"6\",\"EntitySetName\":\"HappyCard_Class\",\"EntityContainerName\":\"hzspEntities\",\"EntityKeyValues\":[{\"Key\":\"ClassID\",\"Type\":\"System.Int32\",\"Value\":\"3\"}]}}]";
		
		JSONArray jSONArray=new JSONArray(jsion);
		for(int i=0;i<jSONArray.length();i++){
			JSONObject obj=jSONArray.getJSONObject(i);
			System.out.print(obj.toString());
		}
	}
	public void testJoin(){

		
	}

}
