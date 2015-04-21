package com.lvmama.passport.processor.impl.client.hangzhouzoom;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.mock.HangzhouZoomMock;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.AccessToken;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.Authorization;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.OrderInfo;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.OrderResponse;
import com.lvmama.passport.processor.impl.client.hangzhouzoom.model.Product;
import com.lvmama.passport.utils.HttpsUtil;
import com.lvmama.passport.utils.WebServiceConstant;

public class HangzhouZoomClient {
	private static final Log log = LogFactory.getLog(HangzhouZoomClient.class);
	private static final String key="CACHE_KEY_AUTHORIZATION_CODE_HZZOOM";
	/**
	 * acessToken缓存设置
	 */
	public static String getAccessToken() throws Exception {
		Object obj = MemcachedUtil.getInstance().get(key);
		if (obj == null) {
			AccessToken acesstoken = authorizeRequest();
			int second  = acesstoken.getExpiresIn(); // 缓存时间,对方传给的参数又改为以秒为单位
			//将对方给的有效期值缩短5分钟，避免acessToken值出现过期没及时重新取值的问题
			MemcachedUtil.getInstance().set(key,second-5*60,acesstoken.getAccessToken());
		} 
		return (String)MemcachedUtil.getInstance().get(key);
	}
	

	/**
	 * 授权验证method
	 */
	public static AccessToken authorizeRequest() throws Exception {
		Authorization auth = Authorization.getInstance();
		Map<String, String> authreq = new HashMap<String, String>();
		authreq.put("scope", auth.getScope());
		authreq.put("grant_type", auth.getGrantType());
		authreq.put("client_id", auth.getClientId());
		authreq.put("client_secret", auth.getClientSecret());
		String result = HttpsUtil.requestPostForm("http://oauth.hzsp.com/oauth2/authorize", authreq);
		log.info("authorize Response:"+result);
		if (result.startsWith(HttpsUtil.HTTP_ERROR_PREFIX)) {
			throw new Exception(result.substring(HttpsUtil.HTTP_ERROR_PREFIX.length()));
		}
		return parseAuthorizeResponse(result);
	}

	/**
	 * 申码请求
	 * @param order
	 */
	public static String applyCodeRequest(OrderInfo order) throws Exception {
		HangzhouZoomMock mock=new HangzhouZoomMock();
		if(mock.isMockEnabled()){
			return mock.applyCodeRequest();
		}
	    String accesstoken =getAccessToken();
	    log.info("accesstoken："+accesstoken);
	    Map<String, String> param = new LinkedHashMap<String, String>();
		String url = WebServiceConstant.getProperties("hangzhouzoom") + "/order/CreateOrder?accesstoken="+accesstoken;
		String sendBySelf=WebServiceConstant.getProperties("hanghzouzoom_sendbyself");//是否在自身平台上发送短信消息(1或0)
		String isTest=WebServiceConstant.getProperties("hanghzouzoom_istest");//此订单是否作测试（1或0）
		log.info("applycode Request isTest:"+isTest);
		log.info("applycode Request sendBySelf:"+sendBySelf);
		param.put("customerOrderId", order.getCustomerOrderId());
		param.put("userName", order.getUserName());
		param.put("identityCard", order.getIdentityCard());
		param.put("email", order.getEmail());
		param.put("mobileTel", order.getMobile());
		param.put("amount",order.getAmount());
		param.put("sendbyself",sendBySelf);
		param.put("istest",isTest);
		param.put("orderProducts[0].enterTime", order.getEnterTime());
		for (int i = 0; i < order.getProductList().size(); i++) {
			Product product = order.getProductList().get(i);
			param.put("orderProducts[0].products[" + i + "].productId", product.getProductId());
			param.put("orderProducts[0].products[" + i + "].customerUnitPrice",product.getCustomerUnitPrice());
			param.put("orderProducts[0].products[" + i + "].quantity",product.getQuantity());
			param.put("orderProducts[0].products[" + i + "].customerProductName",product.getCustomerProductName());
		}
		return HttpsUtil.requestPostForm(url,param);
		
	}

	/**
	 * 废码请求
	 * @param orderId
	 */
	public static String destroyRequest(String orderId) throws Exception {
		HangzhouZoomMock mock=new HangzhouZoomMock();
		if(mock.isMockEnabled()){
			return mock.destoryCodeRequest();
		}
		String accesstoken =getAccessToken();
		log.info("hangzhouzoom destroyRequest: orderId-"+orderId+"-accesstoken"+accesstoken);
		String url = WebServiceConstant.getProperties("hangzhouzoom") + "/order/cancelorder/"+orderId+"?accesstoken=" + accesstoken;
		String result = HttpsUtil.requestPostForm(url, null);
		return result;
	}

	/**
	 * 解析授权验证信息
	 * @param result
	 */
	private static AccessToken parseAuthorizeResponse(String result) throws Exception {
		if(result!=null){
			AccessToken token=new AccessToken();
			JSONObject obj = new JSONObject(result);
			if (obj.has("access_token")) {
				token.setAccessToken(obj.getString("access_token"));
			}
			if(obj.has("expires_in")){
				token.setExpiresIn(obj.getInt("expires_in"));
			}
			return token;
		}
		return null;
	}

	/**
	 * 解析申码返回信息
	 * @param result
	 */
	public static OrderResponse parseApplyCodeResponse(String result) throws Exception {
		if(result!=null){
			OrderResponse msg = new OrderResponse();
			JSONObject obj = new JSONObject(result);
			if (obj.has("OrderId")) {
				msg.setOrderStatus("1");
				msg.setOrderId(obj.getString("OrderId"));
			}
			if (obj.has("Done")) {
				if (StringUtils.equals(obj.getString("Done"), "0")) {
					msg.setOrderStatus("0");
					msg.setErrorInfo(obj.getString("Message"));
				}
			}
			return msg;
		}
		return null;
	}

	/**
	 * 解析废码返回信息
	 * @param result
	 */
	public static OrderResponse parseDestoryCodeResponse(String result) throws Exception {
		if(result!=null){
			OrderResponse msg = new OrderResponse();
			JSONObject obj = new JSONObject(result);
			if (obj.has("Done")) {
				if (StringUtils.equals(obj.getString("Done"), "0")) {
					msg.setOrderStatus("0");
					msg.setErrorInfo(obj.getString("Message"));
				} else {
					msg.setOrderStatus("1");
				}
			}
			return msg;
		}
		return null;
	}
}
