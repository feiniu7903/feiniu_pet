/**
 * 
 */
package com.lvmama.sso.vo;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.HttpsUtil.HttpResponseWrapper;
import com.lvmama.sso.utils.UnionLoginUtil;



/**
 * 腾讯QQ SYNC API
 * @author liuyi
 *
 */
public class QQSyncApi {

	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(QQSyncApi.class);
	/**
	 * APP_KEY_NAME
	 */
	private static final String APP_KEY_NAME = "tencentqq.consumer.key";
	/**
	 * APP_SECRET_NAME
	 */
	private static final String APP_SECRET_NAME = "tencentqq.consumer.secret";
	/**
	 * REQUEST_LOGIN_URL_NAME
	 */
	private static final String REQUEST_LOGIN_URL_NAME = "tencentqq.login.url";
	/**
	 * ACCESS_TOKEN_URL_NAME
	 */
	private static final String ACCESS_TOKEN_URL_NAME = "tencentqq.access.token.url";
	/**
	 * OPEN_ID_URL_NAME open id就是QQ第3方用户ID
	 */
	private static final String OPEN_ID_URL_NAME = "tencentqq.open.id.url";
	/**
	 * USER_INFO_URL_NAME
	 */
	private static final String USER_INFO_URL_NAME = "tencentqq.user.info.url";
	/**
	 * appKey
	 */
	private String appKey;
	/**
	 * appSecret
	 */
	private String appSecret;
	/**
	 * loginUrl
	 */
	private String loginUrl;
	/**
	 * accessTokenUrl
	 */
	private String accessTokenUrl;
	/**
	 * openIdUrl
	 */
	private String openIdUrl;
	/**
	 * userInfoUrl
	 */
	private String userInfoUrl;

	
	public QQSyncApi()
	{
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		this.appKey = util.getValue(APP_KEY_NAME);
		this.appSecret = util.getValue(APP_SECRET_NAME);
		this.loginUrl = util.getValue(REQUEST_LOGIN_URL_NAME);
		this.accessTokenUrl = util.getValue(ACCESS_TOKEN_URL_NAME);
		this.openIdUrl = util.getValue(OPEN_ID_URL_NAME);
		this.userInfoUrl = util.getValue(USER_INFO_URL_NAME);
	}


	/**
	 * 获得QQ登录URL，第一步流程
	 * @param callBackPage
	 * @return
	 */
	public String getQQLoginUrl(String callBackPage) {
		
		// 获取QQ登录页面的url
		StringBuilder qqLoginUrl = new StringBuilder();
		
		// QQ登陆地址 用于获取Authorization Code
		qqLoginUrl.append(this.loginUrl);
		
		// 授权类型，此值固定为“code”
		qqLoginUrl.append("?response_type=code");
		
		// 申请QQ登录成功后，分配给应用的appid
		qqLoginUrl.append("&client_id=" + this.appKey);
		
		// 成功授权后的回调地址，建议设置为网站首页或网站的用户中心。
		qqLoginUrl.append("&redirect_uri=" + callBackPage);
		
		// 请求用户授权时向用户显示的可进行授权的列表。如果要填写多个接口名称，请用逗号隔开。 不传则默认请求对接口get_user_info进行授权
		qqLoginUrl.append("&scope=");
		
		// client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。
		qqLoginUrl.append("&state=javaSdk-code");
		
		// 用于展示的样式。不传则默认展示为为PC下的样式。
		// 如果传入“mobile”，则展示为mobile端下的样式。
		qqLoginUrl.append("&display=");
		
		return qqLoginUrl.toString();
	}
	
   /**
    * 获取请求access token 的URL第二步流程
    * @param authorizationCode
    * @param callBackPage
    * @return
    */
	public String getQQAccessTokenUrl(String authorizationCode, String callBackPage) {
		
		StringBuilder accessTokenUrl = new StringBuilder();
		
		// 通过Authorization Code获取Access Token 的URl 
		accessTokenUrl.append(this.accessTokenUrl);
		
		// QQ登陆地址 用于获取Authorization Code
		accessTokenUrl.append("?grant_type=authorization_code");
		
		// 申请QQ登录成功后，分配给应用的appid
		accessTokenUrl.append("&client_id=" + this.appKey);
		
		// 申请QQ登录成功后，分配给网站的appkey
		accessTokenUrl.append("&client_secret=" + this.appSecret);
		
		// 登陆成功后返回的authorization code
		accessTokenUrl.append("&code=" + authorizationCode);
		
		// client端的状态值。用于第三方应用防止CSRF攻击，成功授权后回调时会原样带回。
		accessTokenUrl.append("&state=javaSdk-token");
		
		// 成功授权后的回调地址，建议设置为网站首页或网站的用户中心。
		accessTokenUrl.append("&redirect_uri=" + callBackPage);
		
		return accessTokenUrl.toString();
	}
	
	
	/**
	 * 获取请求open id的URL，open id就是QQ第3方用户ID，第3步流程
	 * @param accessToken
	 * @return
	 */
	public String getQQOpenIdUrl(String accessToken) {
		
		// 换取OpenId的URL
		StringBuilder openIdUrl = new StringBuilder();
		
		// 使用Access Token来获取用户的OpenID 的URl 
		openIdUrl.append(this.openIdUrl);
		
		// ACCESS_TOKEN 
		openIdUrl.append("?access_token=" + accessToken);
		
		return openIdUrl.toString();
	}
	
	/**
	 * 动态拼接获取用户数据的url 第4部流程，获取用户的昵称
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public String getQQUserInfoUrl(String accessToken, String openId) {
		
		// 动态拼接获取用户数据的url
		StringBuilder userInfoUrl = new StringBuilder();
		
		// 获取用户数据接口的URl 
		userInfoUrl.append(this.userInfoUrl);
		
		// 应用的appid
		userInfoUrl.append("?oauth_consumer_key=" + this.appKey);
		
		// 应用的accessToken
		userInfoUrl.append("&access_token=" + accessToken);
		
		// 应用的OpenID
		userInfoUrl.append("&openid=" + openId);
		
		return userInfoUrl.toString();
	}
	
	
	
	/**
	 * 用 authorizationCode 换取 AccessToken
	 * 
	 * @param accessTokenUrl 换取accessToken的URL 
	 * @return AccessToken
	 * @throws IOException 
	 */
	public String getQQAccessToken(String accessTokenUrl) throws IOException {
		
		String accessToken = "";
		
		// 接受返回的字符串 包含accessToken
		String tempStr = "";
		
		// 请求QQ接口，回去返回数据
		tempStr = doGet(accessTokenUrl);
		
		// 获取accessToken失败的场合
		if (tempStr.indexOf("access_token") >= 0) {
			accessToken = tempStr.split("&")[0].split("=")[1];
			LOG.info("access_token:" + accessToken);
			return accessToken;
		} else {
			LOG.info("access_token get fail return：" + tempStr);
			return "";
		}
	}
	
	
	/**
	 * 根据AccessToken获取OpenId
	 * 
	 * @param accessToken AccessToken
	 * @return OpenId
	 * @throws IOException 
	 * @throws JSONException 
	 */
	public String getQQOpenId(String accessToken) throws IOException {
		
		// 获取OpenId
		String openId = null;
		
		// 请求QQ接口，回去返回数据
		String interfaceData = doGet(this.getQQOpenIdUrl(accessToken));
		
		// 接口返回的数据json
		JSONObject jsonObjRoot;
		try {
			// 去掉多余的字符串
			String jsonStr = interfaceData.substring(interfaceData.indexOf("{"),interfaceData.indexOf("}") + 1);
			
			// 获取json对象
			jsonObjRoot = new JSONObject(jsonStr);
			Object openIdObj =  null;
			if(!jsonObjRoot.isNull("openid")){
				openIdObj =  jsonObjRoot.get("openid");
			}
			if(openIdObj != null){
				// 获取OpenId
				openId = openIdObj.toString();
			}
			
			// 日志
			LOG.info("openid:" + openId);
		} catch (JSONException e) {
			e.printStackTrace();
			LOG.error("get OpenId fail return" + interfaceData);
		}
		catch(Exception ex)
		{
			LOG.error(ex,ex);
		}
		return openId;
	}
	
	
	public String getUserNickName(String accessToken, String openId) throws IOException {

		// 获取接口返回的数据
		String interfaceData = doGet(this.getQQUserInfoUrl(accessToken, openId));
		String nickName = "";
		
		// 接口返回的数据json
		JSONObject jsonObjRoot;
		try {
			jsonObjRoot = new JSONObject(interfaceData);
			
			// 接口返回错误的场合
			if (jsonObjRoot.getInt("ret") != 0) {
//				// 设置错误标识为真
//				resultBean.setErrorFlg(true);
//				
//				// 设置错误编号
//				resultBean.setErrorCode(jsonObjRoot.get("ret").toString());
//				
//				// 设置错误信息
//				resultBean.setErrorMes(jsonObjRoot.getString("msg"));
				
				// 日志
				LOG.error("get user info error, error code：" + jsonObjRoot.get("ret").toString());
			} else {
				
				nickName = jsonObjRoot.getString("nickname");
				
				// 昵称
//				resultBean.setNickName(jsonObjRoot.getString("nickname"));
				
//				// 头像URL
//				resultBean.setFigureUrl(jsonObjRoot.getString("figureurl"));
//				
//				// 头像URL
//				resultBean.setFigureUrl1(jsonObjRoot.getString("figureurl_1"));
//				
//				// 头像URL
//				resultBean.setFigureUrl2(jsonObjRoot.getString("figureurl_2"));
//				
//				// 性别
//				resultBean.setGender(jsonObjRoot.getString("gender"));
//				
//				// 是否为黄钻
//				resultBean.setIsVip(jsonObjRoot.getString("vip"));
//				
//				// 黄钻等级
//				resultBean.setLevel(jsonObjRoot.getString("level"));
			}
		} catch (JSONException e) {
			e.printStackTrace();
			// 日志
			LOG.error("get user info error: " + interfaceData);
		}
		
		return nickName;
	}
	
	
	/**
	 * 链接QQ服务接口
	 * 
	 * @param interfaceUrl
	 *            接口URL
	 * 
	 * @return 接口返回的数据
	 * @throws IOException
	 */
	public String doGet(String interfaceUrl) throws IOException {
		// 打印日志
		LOG.info("doGet:" + interfaceUrl);
		
		String interfaceData = "";
		try
		{
//			HttpClient httpClient = new HttpClient();
//			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(10000);
//			httpClient.getHttpConnectionManager().getParams().setSoTimeout(10000);
//			LOG.info("prepare:" + interfaceUrl);
//			GetMethod getMethod = new GetMethod(interfaceUrl);
//			LOG.info("get:" + interfaceUrl);
//			int state = httpClient.executeMethod(getMethod);
			HttpResponseWrapper httpResponseWrapper = HttpsUtil.requestGetResponse(interfaceUrl);
			int state = httpResponseWrapper.getStatusCode();
			
			// 打印日志
			LOG.info("doGet request Code:" + state);
			
			// 是否请求正常
			if (HttpStatus.SC_OK == state) {

				//InputStream stream = new ByteArrayInputStream(getMethod.getResponseBody());

				// 获取返回的数据流
				BufferedReader input = new BufferedReader(new InputStreamReader(httpResponseWrapper.getResponseStream(),"UTF-8"));
				String tempStr = "";

				// 获取返回的内容
				while ((tempStr = input.readLine()) != null) {
					interfaceData += tempStr.replace("\t", "");
				}
			}
			// 关闭连接
			//getMethod.releaseConnection();
			httpResponseWrapper.close();
		}
		catch(Exception ex)
		{
			LOG.error(ex,ex);
		}
		// 打印日志
		LOG.info("doGet return json：" + interfaceData);
		
		return interfaceData;
	}

}
