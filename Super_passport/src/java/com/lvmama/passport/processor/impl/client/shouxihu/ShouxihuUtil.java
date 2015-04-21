package com.lvmama.passport.processor.impl.client.shouxihu;

import java.util.List;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHOrderInfo;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHProduct;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHProductList;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestBody;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestHead;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHRequestInfo;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHResponseBody;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHResponseHead;
import com.lvmama.passport.processor.impl.client.shouxihu.model.SXHResponseInfo;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * 瘦西湖对接--工具类
 * @author lipengcheng
 *
 */
public class ShouxihuUtil {
	
	/**
	 * 访问请求方法
	 * @param requestXml
	 * @return
	 * @throws Exception 
	 */
	public static String request(String requestXml) throws Exception {
		String result = HttpsUtil.requestPostXml(WebServiceConstant.getProperties("shouxihu.url"), requestXml);
		if(result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)){
			throw new Exception(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		return result;
	}
	
	/**
	 * XML元素构造器
	 * @param element
	 * @param value
	 * @return
	 */
	public static String buildXmlElement(String element, String value) {
		value = value == null ? "" : value;
		StringBuilder xmlStr = new StringBuilder();
		xmlStr.append("<").append(element).append(">").append(value).append("</").append(element).append(">");
		return xmlStr.toString();
	}
	
	/**
	 * 解析存储订单响应报文
	 * @param responseXml
	 * @return
	 */
	public static SXHResponseInfo getSaveOrderResponse (String responseXml){
		XStream xStream = getHead();
		xStream.alias("body", SXHResponseBody.class);
		xStream.aliasField("order", SXHResponseBody.class, "orderInfo");
		xStream.aliasField("product", SXHOrderInfo.class, "product");
		return (SXHResponseInfo)xStream.fromXML(responseXml);
	}
	
	/**
	 * 解析获取产品列表接口
	 * @param responseXml
	 * @return
	 */
	public static SXHResponseInfo getProductListResponse(String responseXml) {
		XStream xStream = getHead();
		xStream.alias("body", SXHResponseBody.class);
//		xStream.alias("productList", SXHProductList.class);
		xStream.alias("product", SXHProduct.class);

		xStream.addImplicitCollection(SXHProductList.class, "productList");
		xStream.aliasField("product", SXHProduct.class, "product");
		xStream.aliasField("sceneryId", SXHProduct.class, "sceneryId");
		xStream.aliasField("sceneryName", SXHProduct.class, "sceneryName");
		xStream.aliasField("ticketTypeId", SXHProduct.class, "ticketTypeId");
		xStream.aliasField("ticketTypeName", SXHProduct.class, "ticketTypeName");
		xStream.aliasField("parPrice", SXHProduct.class, "parPrice");
		xStream.aliasField("webPrice", SXHProduct.class, "webPrice");
		xStream.aliasField("settlementPrice", SXHProduct.class, "settlementPrice");
		return (SXHResponseInfo) xStream.fromXML(responseXml);
	}
	
	/**
	 * 解析重发二维码响应报文接口
	 * @param responseXml
	 * @return
	 */
	public static SXHResponseInfo reSendDimensionalCode(String responseXml){
		XStream xStream = getHead();
		return (SXHResponseInfo) xStream.fromXML(responseXml);
	}
	
	/**
	 * 解析响应报文头
	 * @return
	 */
	public static XStream getHead (){
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("response", SXHResponseInfo.class);
		xStream.alias("header", SXHResponseHead.class);
		xStream.aliasField("header", SXHResponseInfo.class, "head");
		xStream.aliasField("rspCode", SXHResponseHead.class, "rspCode");
		xStream.aliasField("rspDesc", SXHResponseHead.class, "rspDesc");
		xStream.aliasField("rspTime", SXHResponseHead.class, "rspTime");
		return xStream;
	}
	
	/**
	 * 解析回调报文
	 * @return
	 */
	public static SXHRequestInfo callBackInfo(String requestXml) {
		XStream xStream = new XStream(new DomDriver());
		xStream.alias("request", SXHRequestInfo.class);
		xStream.alias("header", SXHRequestHead.class);
		xStream.alias("body", SXHRequestBody.class);
		xStream.aliasField("header", SXHRequestInfo.class, "header");
		xStream.aliasField("accountID", SXHRequestHead.class, "accountID");
		xStream.aliasField("serviceName", SXHRequestHead.class, "serviceName");
		xStream.aliasField("digitalSign", SXHRequestHead.class, "digitalSign");
		xStream.aliasField("reqTime", SXHRequestHead.class, "reqTime");
		xStream.aliasField("body", SXHRequestInfo.class, "body");
		xStream.aliasField("serialId", SXHRequestBody.class, "serialId");
		xStream.aliasField("orderStatus", SXHRequestBody.class, "orderStatus");
		xStream.aliasField("realTicketCount", SXHRequestBody.class, "realTicketCount");
		xStream.aliasField("realPayAmount", SXHRequestBody.class, "realPayAmount");
		xStream.aliasField("ticketDate", SXHRequestBody.class, "ticketDate");
		return (SXHRequestInfo) xStream.fromXML(requestXml);
	}
	
	public static void main(String[] args) {
		//##############################获取产品
		/*String req = SXHRequestInfo.buildGetProductList(100L, 5L);
		String result = request(req);
		SXHResponseInfo response = getProductListResponse(result);*/
		for (int i = 1; i <= 1000; i++) {
			String req = SXHRequestInfo.buildGetProductList(Long.valueOf(i), 10L);
			String result = null;
			try {
				result = request(req);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			SXHResponseInfo response = getProductListResponse(result);
			List<SXHProduct> list = response.getBody().getProductList().getProductList();
			if (list != null) {
				for (SXHProduct product : list) {
					product.printProductInfo();
				}
			}else{
				break;
			}
		}
		
		//#############################下订单
		/*SXHOrderInfo orderInfo = new SXHOrderInfo();
		orderInfo.setComfirmNumber("");
		orderInfo.setSerialId("111222333");
		orderInfo.setSceneryId("68");
		orderInfo.setSceneryName("景区");//扬州瘦西湖
		orderInfo.setTicketTypeId("1");
		orderInfo.setTicketTypeName("门票");//成人票
		orderInfo.setUnitPrice("80.00");
		orderInfo.setTicketCount("1");
		orderInfo.setTotalAmount("80.00");
		orderInfo.setRealPayAmount("80.00");
		orderInfo.setPayType("onlinepayment");
		orderInfo.setPlayDate("2012-09-10");
		orderInfo.setExpiryDate("");
		orderInfo.setOrderStatus("P");
		orderInfo.setTravelerName("李朋成");
		orderInfo.setTravelerMobile("18221076042");
		orderInfo.setOrderDate(DateUtil.getDateTime("yyyy-MM-dd", new Date()));
		orderInfo.setIdentityCard("");
		
		String req = SXHRequestInfo.buildCreateOrder(orderInfo);
		System.out.println(req);
		String result = ShouxihuUtil.request(req);
		System.out.println(result);
//		String result = "<?xml version=\"1.0\" encoding=\"utf-8\"?><response><header><rspCode>1</rspCode><rspDesc><![CDATA[ 执行成功[短信/彩信发送成功] ]]></rspDesc><rspTime>2012-09-06 15:56:24</rspTime></header><body><order><serialId>111222333</serialId></order></body></response>";
		SXHResponseInfo response = getSaveOrderResponse(result);
		System.out.println(response);*/
	}
}
