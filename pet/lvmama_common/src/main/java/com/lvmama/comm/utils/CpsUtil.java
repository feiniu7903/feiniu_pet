/**
 * 
 */
package com.lvmama.comm.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.vo.Constant;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * CPS UTIL类  目前只用于QQ彩贝，未来如果要支持其他CPS，需要继续重构
 * @author liuyi
 *
 */
public class CpsUtil {
	private static final Log LOG = LogFactory.getLog(CpsUtil.class);
	public static final String ORDER_CREATE = "10";
	public static final String ORDER_PAYED = "11";
	public static final String ORDER_FINISH = "20";
	public static final String ORDER_CANCEL = "21";
	private static CpsUtil instance;
	private static Object LOCK = new Object();
	private String qqCbPushUrl;
	private String qqCbKey1;
	private String qqCbKey2;
	
	public static CpsUtil getInstance() {
		if(instance == null){
			synchronized(LOCK) {
				if (instance==null) {
					instance=new CpsUtil();
				}
			}
		}
		return instance;
	}
	
	
	private CpsUtil()
	{
		try{
			qqCbPushUrl = Constant.getInstance().getProperty("cps.tencentqqcb.pushurl");
			if(StringUtils.isEmpty(qqCbPushUrl)){
				qqCbPushUrl = "http://order.cb.qq.com/caibei_order_accept.php";
			}
			qqCbKey1 = Constant.getInstance().getProperty("cps.tencentqqcb.key1");
			qqCbKey2 = Constant.getInstance().getProperty("cps.tencentqqcb.key2");
		}catch(Exception e){
			LOG.error(e, e);
		}

	}
	
	
	/**
	 * @param order
	 * @param uid
	 * @param trackingCode
	 */
	public void sendQQCbCpsRequest(final OrdOrder order, String cbOrderStatus, String channel,  String uid,String trackingCode) {
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
		httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
		//发送CPS请求
		PostMethod postMethod = new PostMethod(qqCbPushUrl);
		postMethod.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"utf-8");  
		postMethod.addParameters(getQQCbCpsRequestParameter(order, cbOrderStatus, channel, uid, trackingCode));
		LOG.info("qq cb cb order status send: "+cbOrderStatus+","+uid+","+order.getOrderId());
		try {
			int state = httpClient.executeMethod(postMethod);
			if(state == HttpStatus.SC_OK){
				String qqCbStatus = new String(postMethod.getResponseBody(),"UTF-8");
				LOG.info("qq cb status "+qqCbStatus);
			}else{
				LOG.error("qq cb http client status error "+state);
			}
		} catch (HttpException e) {
			//LOG.error(e, e);
			LOG.error(e.getMessage());
		} catch (IOException e) {
			//LOG.error(e, e);
			LOG.error(e.getMessage());
		}
	}

	private  NameValuePair[] getQQCbCpsRequestParameter(final OrdOrder order, String cbOrderStatus, String channel, String openId, String trackingCode)
	{
		//初始化QQ CPS数据
		String orderTotalAmount = String.valueOf(order.getOughtPay());
		String orderPayAmount = ""; 
		String orderCommAmount = "";
		if ("qqcb".endsWith(channel)) {
			if(cbOrderStatus.equals(CpsUtil.ORDER_CREATE)){ 
				orderPayAmount = String.valueOf(order.getOughtPay()); 
				orderCommAmount = String.valueOf((long)(order.getOughtPay()*0.03)); 
			} else { 
				orderPayAmount = String.valueOf(order.getActualPay()); 
				orderCommAmount = String.valueOf((long)(order.getActualPay()*0.03)); 
			}			
		} 
		if ("TENCENTQQ".endsWith(channel)) {
			orderPayAmount = String.valueOf(order.getOughtPay()); 
			orderCommAmount = String.valueOf(10);			
		}

		SimpleDateFormat simpleDateFormat = new  SimpleDateFormat("yyyyMMddHHmmss");
		String orderCreateTime = simpleDateFormat.format(order.getCreateTime());
		String orderModifyTime = simpleDateFormat.format(order.getCreateTime());
		
//		String orderCreateTime = simpleDateFormat.format(new Date());
//		String orderModifyTime = simpleDateFormat.format(new Date());
		String orderPushTime = simpleDateFormat.format(new Date());
		String paymentType = "bankpay";
		String province = "";
		String merchantId = "lvmama";
		
		//计算腾讯Vkey，用以校验
		String vkey = trackingCode+merchantId+openId+orderCommAmount+orderCreateTime+order.getOrderId()
				+orderModifyTime+orderPayAmount+orderPushTime+cbOrderStatus+orderTotalAmount+paymentType+province;
		vkey = calculateQQCbVkey(vkey, qqCbKey1, qqCbKey2);
		
		//获得产品列表JSON
		String productListJson = getQQCbCpsProductListJson(order);
		
		NameValuePair[] nameValuePairList = new NameValuePair[17];
		nameValuePairList[0] = new NameValuePair();nameValuePairList[1] = new NameValuePair();nameValuePairList[2] = new NameValuePair();
		nameValuePairList[3] = new NameValuePair();nameValuePairList[4] = new NameValuePair();nameValuePairList[5] = new NameValuePair();
		nameValuePairList[6] = new NameValuePair();nameValuePairList[7] = new NameValuePair();nameValuePairList[8] = new NameValuePair();
		nameValuePairList[9] = new NameValuePair();nameValuePairList[10] = new NameValuePair();nameValuePairList[11] = new NameValuePair();
		nameValuePairList[12] = new NameValuePair();nameValuePairList[13] = new NameValuePair();nameValuePairList[14] = new NameValuePair();
		nameValuePairList[15] = new NameValuePair();nameValuePairList[16] = new NameValuePair();
		nameValuePairList[0].setName("MerchantId");nameValuePairList[0].setValue(merchantId);
		nameValuePairList[1].setName("OpenId");nameValuePairList[1].setValue(openId);
		nameValuePairList[2].setName("OrderId");nameValuePairList[2].setValue(String.valueOf(order.getOrderId()));
		nameValuePairList[3].setName("OrderStatus");nameValuePairList[3].setValue(cbOrderStatus);
		String orderDesc = "旅游产品";
//		try {
//			orderDesc = URLEncoder.encode("旅游产品", "utf-8");
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		nameValuePairList[4].setName("OrderDesc");nameValuePairList[4].setValue(orderDesc);
		nameValuePairList[5].setName("OrderTotalAmount");nameValuePairList[5].setValue(orderTotalAmount);
		nameValuePairList[6].setName("OrderPayAmount");nameValuePairList[6].setValue(orderPayAmount);
		nameValuePairList[7].setName("OrderCommAmount");nameValuePairList[7].setValue(orderCommAmount);
		nameValuePairList[8].setName("PaymentType");nameValuePairList[8].setValue(paymentType);
		nameValuePairList[9].setName("Products");nameValuePairList[9].setValue(productListJson);
		nameValuePairList[10].setName("OrderCreateTime");nameValuePairList[10].setValue(orderCreateTime);
		nameValuePairList[11].setName("OrderModifyTime");nameValuePairList[11].setValue(orderModifyTime);
		nameValuePairList[12].setName("OrderPushTime");nameValuePairList[12].setValue(orderPushTime);
		nameValuePairList[13].setName("Attach");nameValuePairList[13].setValue(trackingCode);
		nameValuePairList[14].setName("Vkey");nameValuePairList[14].setValue(vkey);
		nameValuePairList[15].setName("Province");nameValuePairList[15].setValue(province);
		nameValuePairList[16].setName("FeedBack");nameValuePairList[16].setValue("");
		
		String logValue = "";
		for(int i = 0; i < nameValuePairList.length; i++){
			logValue += nameValuePairList[i].getName()+"="+nameValuePairList[i].getValue()+"&";
		}
		LOG.info(logValue);
		return nameValuePairList;
	}
	
	/**
	 * 检测QQ CB原始KEY MD5加密后和MD5 KEY是否相等
	 * @param originalKey
	 * @param md5Key
	 * @return
	 */
	public boolean checkQQCbVkey(String originalKey, String md5Key)
	{
		originalKey = calculateQQCbVkey(originalKey, qqCbKey1, qqCbKey2);
		if(originalKey.equals(md5Key)){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 计算QQ彩贝加密Vkey
	 * @param originalkey
	 * @param key1
	 * @param key2
	 * @return
	 */
	private  String calculateQQCbVkey(String originalkey, String key1, String key2)
	{
		
		String originalkey2 = "";
		try {
			originalkey2 = new MD5().code(new MD5().code(originalkey+key1).toLowerCase()+key2).toLowerCase();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return originalkey2;
	}
	
	
	/**
	 * 获得QQ彩贝产品列表JSON
	 * @param order
	 */
	private  String getQQCbCpsProductListJson(final OrdOrder order) {
		StringBuffer allProductsInfoJson = new StringBuffer();
		List<OrdOrderItemProd>  productList =  order.getOrdOrderItemProds();
		
		StringBuffer hotelProductsInfoJson = new StringBuffer();
		StringBuffer otherProductsInfoJson = new StringBuffer();
		for(int i = 0; i < productList.size(); i++){
			OrdOrderItemProd product = productList.get(i);
			String productName = product.getProductName();
			productName= productName.replaceAll("\'", "\\\\'");
			productName= productName.replaceAll("\\]", "\\\\u005D");
			productName= productName.replaceAll("\\[", "\\\\u005B");
			productName= productName.replaceAll("\\}", "\\\\u007D");
			productName= productName.replaceAll("\\{'", "\\\\u007B'");
			
//			try {
//				productName= URLEncoder.encode(productName, "utf-8");
//			} catch (UnsupportedEncodingException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
			
			if(Constant.PRODUCT_TYPE.HOTEL.equals(product.getProductType()))
			{
				if(hotelProductsInfoJson.length() > 0)
				{
					hotelProductsInfoJson.append(",");
				}
				hotelProductsInfoJson.append("{Id:'"+product.getProductId()+"',Name:'"+productName+"',Count:"+product.getQuantity()+",PerPrice:"+product.getPrice()+"}");
			}
			else
			{
				if(otherProductsInfoJson.length() > 0)
				{
					otherProductsInfoJson.append(",");
				}
				otherProductsInfoJson.append("{Id:'"+product.getProductId()+"',Name:'"+productName+"',Count:"+product.getQuantity()+",PerPrice:"+product.getPrice()+"}");
			}
		}
		if(hotelProductsInfoJson.length() > 0)
		{
			allProductsInfoJson.append("'hotels':["+hotelProductsInfoJson.toString()+"]");
		}
		if(otherProductsInfoJson.length() > 0)
		{
			if(allProductsInfoJson.length() > 0)
			{
				allProductsInfoJson.append(",");
			}
			allProductsInfoJson.append("'others':["+otherProductsInfoJson.toString()+"]");
		}
		allProductsInfoJson.insert(0, "{");
		allProductsInfoJson.append("}");
		return allProductsInfoJson.toString();
	}
	
	public static void main(String args[])
	{
		String value = "'[]{}";
		value= value.replaceAll("\'", "\\\\'");
		value= value.replaceAll("\\]", "\\\\u005D");
		value= value.replaceAll("\\[", "\\\\u005B");
		value= value.replaceAll("\\}", "\\\\u007D");
		value= value.replaceAll("\\{'", "\\\\u007B'");
	}

}
