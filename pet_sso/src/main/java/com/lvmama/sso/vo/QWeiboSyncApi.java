package com.lvmama.sso.vo;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.lvmama.sso.utils.UnionLoginUtil;
import com.mime.qweibo.OauthKey;
import com.mime.qweibo.QAsyncHandler;
import com.mime.qweibo.QParameter;
import com.mime.qweibo.QWeiboRequest;

/**
 * 腾讯微博
 *
 */
public class QWeiboSyncApi {
	/**
	 * APP_KEY_NAME
	 */
	private static final String APP_KEY_NAME = "tencent.consumer.key";
	/**
	 * APP_SECRET_NAME
	 */
	private static final String APP_SECRET_NAME = "tencent.consumer.secret";
	/**
	 * REQUEST_TOKEN_URL_NAME
	 */
	private static final String REQUEST_TOKEN_URL_NAME = "tencent.request.token.url";
	/**
	 * URER_INFO_URL_NAME
	 */
	private static final String URER_INFO_URL_NAME = "tencent.user.info.url";
	/**
	 * ACCESS_TOKEN_URL_NAME
	 */
	private static final String ACCESS_TOKEN_URL_NAME = "tencent.access.token.url";
	/**
	 * REDIRECT_URL_NAME
	 */
	private static final String REDIRECT_URL_NAME = "tencent.redirect.url";
	/**
	 * appKey
	 */
	private String appKey;
	/**
	 * appSecret
	 */
	private String appSecret;
	/**
	 * requestTokenUrl
	 */
	private String requestTokenUrl;
	/**
	 * userInfoUrl
	 */
	private String userInfoUrl;
	/**
	 * accessTokenUrl
	 */
	private String accessTokenUrl;
	/**
	 * redirectUrl
	 */
	private String redirectUrl;

	/**
	 * 无参构造函数
	 */
	public QWeiboSyncApi() {
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		this.appKey = util.getValue(APP_KEY_NAME);
		this.appSecret = util.getValue(APP_SECRET_NAME);
		this.requestTokenUrl = util.getValue(REQUEST_TOKEN_URL_NAME);
		this.userInfoUrl = util.getValue(URER_INFO_URL_NAME);
		this.accessTokenUrl = util.getValue(ACCESS_TOKEN_URL_NAME);
		this.redirectUrl = util.getValue(REDIRECT_URL_NAME);
	}

	/**
	 * 结果类型
	 *
	 */
	public enum ResultType {
		/**
		 * XML类型
		 */
		ResultType_Xml,
		/**
		 * Json类型
		 */
		ResultType_Json
	}
	/**
	 * The page flag to fetch messages.
	 *
	 */
	public enum PageFlag {
		/**
		 * 第一页
		 */
		PageFlag_First,
		/**
		 * 下一页
		 */
		PageFlag_Next,
		/**
		 * 最后一页
		 */
		PageFlag_Last
	}

	/**
	 * Get request token.
	 * @param url URL
	 * @param callbackUrl callbackUrl
	 * @return 加密URL
	 */
	public String getRequestToken(final String url, final String callbackUrl) {
		//String url = "https://open.t.qq.com/cgi-bin/request_token";
		List<QParameter> parameters = new ArrayList<QParameter>();
		OauthKey oauthKey = new OauthKey();
		oauthKey.customKey = appKey;
		oauthKey.customSecrect = appSecret;
		oauthKey.callbackUrl = callbackUrl;
		QWeiboRequest request = new QWeiboRequest();
		String res = null;
		try {
			res = request.syncRequest(url, "GET", oauthKey, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Get access token.
	 * @param requestToken requestToken
	 * @param requestTokenSecrect requestTokenSecrect
	 * @param verify verify
	 * @param url url
	 * @param param param
	 * @return 加密URL
	 */
	public String getAccessToken(final String requestToken, final String requestTokenSecrect,
			final String verify, final String url, final QParameter param) {
		List<QParameter> parameters = new ArrayList<QParameter>();
		if (param != null) {
			parameters.add(param);
		}
		OauthKey oauthKey = new OauthKey();
		oauthKey.customKey = appKey;
		oauthKey.customSecrect = appSecret;
		oauthKey.tokenKey = requestToken;
		oauthKey.tokenSecrect = requestTokenSecrect;
		oauthKey.verify = verify;
		QWeiboRequest request = new QWeiboRequest();
		String res = null;
		try {
			res = request.syncRequest(url, "GET", oauthKey, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Get home page messages.
	 *
	 * @param requestToken
	 *            The access token
	 * @param requestTokenSecrect
	 *            The access token secret
	 * @param format
	 *            Response format, xml or json
	 * @param pageFlag
	 *            Page number.
	 * @param nReqNum
	 *            Number of messages you want.
	 * @return Response messages based on the specified format.
	 */
	public String getHomeMsg(final String requestToken, final String requestTokenSecrect, final ResultType format,
			final PageFlag pageFlag, final int nReqNum) {

		String url = "http://open.t.qq.com/api/statuses/home_timeline";
		List<QParameter> parameters = new ArrayList<QParameter>();
		OauthKey oauthKey = new OauthKey();
		oauthKey.customKey = appKey;
		oauthKey.customSecrect = appSecret;
		oauthKey.tokenKey = requestToken;
		oauthKey.tokenSecrect = requestTokenSecrect;

		String strFormat = null;
		if (format == ResultType.ResultType_Xml) {
			strFormat = "xml";
		} else if (format == ResultType.ResultType_Json) {
			strFormat = "json";
		} else {
			return "";
		}

		parameters.add(new QParameter("format", strFormat));
		parameters.add(new QParameter("pageflag", String.valueOf(pageFlag
				.ordinal())));
		parameters.add(new QParameter("reqnum", String.valueOf(nReqNum)));

		QWeiboRequest request = new QWeiboRequest();
		String res = null;
		try {
			res = request.syncRequest(url, "GET", oauthKey, parameters, null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * Publish a Weibo message.
	 * @param requestToken
	 *            The access token
	 * @param requestTokenSecrect
	 *            The access token secret
	 * @param content
	 *            The content of your message
	 * @param pic
	 *            The files of your images.
	 * @param format
	 *            Response format, xml or json(Default).
	 * @return Result info based on the specified format.
	 */
	public String publishMsg(final String requestToken, final String requestTokenSecrect, final String content,
			final String pic, final ResultType format) {

		List<QParameter> files = new ArrayList<QParameter>();
		String url = null;
		String httpMethod = "POST";

		if (pic == null || pic.trim().equals("")) {
			url = "http://open.t.qq.com/api/t/add";
		} else {
			url = "http://open.t.qq.com/api/t/add_pic";
			files.add(new QParameter("pic", pic));
		}

		OauthKey oauthKey = new OauthKey();
		oauthKey.customKey = appKey;
		oauthKey.customSecrect = appSecret;
		oauthKey.tokenKey = requestToken;
		oauthKey.tokenSecrect = requestTokenSecrect;

		List<QParameter> parameters = new ArrayList<QParameter>();

		String strFormat = null;
		if (format == ResultType.ResultType_Xml) {
			strFormat = "xml";
		} else if (format == ResultType.ResultType_Json) {
			strFormat = "json";
		} else {
			return "";
		}

		parameters.add(new QParameter("format", strFormat));
		try {
			parameters.add(new QParameter("content", new String(content
					.getBytes("UTF-8"))));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			return "";
		}
		parameters.add(new QParameter("clientip", "127.0.0.1"));

		QWeiboRequest request = new QWeiboRequest();
		String res = null;
		try {
			res = request.syncRequest(url, httpMethod, oauthKey, parameters,
					files);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	/**
	 * 处理字符串是空或NULL值
	 * @param str 处理字符串
	 * @return 为空或NULL，返回真，否则返回假
	 */
	public static boolean isNullOrEmpty(final String str) {
		if (str == null || "".equals(str.trim())) {
			return true;
		}
		return false;
	}

	/**
	 * 解析Token
	 * @param response response
	 * @param returnMap returnMap
	 * @return true or false
	 */
	public static boolean parseToken(final String response, final Map<String, String> returnMap) {
		if (response == null || response.equals("")) {
			return false;
		}
		String[] tokenArray = response.split("&");
		if (tokenArray.length < 2) {
			return false;
		}
		String strTokenKey = tokenArray[0];
		String strTokenSecrect = tokenArray[1];
		String[] token1 = strTokenKey.split("=");
		if (token1.length < 2) {
			return false;
		}
		String tokenKey = token1[1];
		returnMap.put("tokenKey", tokenKey);
		String[] token2 = strTokenSecrect.split("=");
		if (token2.length < 2) {
			return false;
		}
		String tokenSecret = token2[1];
		returnMap.put("tokenSecret", tokenSecret);
		if (tokenArray.length > 2) {
			String strTokenName = tokenArray[2];
			String[] token3 = strTokenName.split("=");
			if (token3.length == 2) {
			   String	unionLoginUid = token3[1];
			   returnMap.put("unionLoginUid", unionLoginUid);
			}
		}
		return true;
	}
	/**'
	 * actionPerformed
	 * @param requestUrl requestUrl
	 * @param httpMethod GET,POST
	 * @param authKey (appKey,appSecret,tokenKey,tokenSecret)
	 * @return result
	 */
	public String actionPerformed(final String requestUrl, final String httpMethod, final OauthKey authKey) {
		QWeiboRequest request = new QWeiboRequest();
		final String[] result = new String[1];
		QAsyncHandler asyncHandler = new QAsyncHandler() {
			private String mContent = "";
			public void onThrowable(final Throwable t, final Object cookie) {
			}
			public void onCompleted(final int statusCode, final String content, final Object cookie) {
				mContent = content;
				result[0] = mContent;
			}
		};
		List<QParameter> parameters = new ArrayList<QParameter>();
		QParameter param = new QParameter("format", "xml");
		parameters.add(param);
		List<QParameter> files = new ArrayList<QParameter>();
		request.asyncRequest(requestUrl, httpMethod, authKey, parameters, files, asyncHandler, null);
//		try {
//			Document document = DocumentHelper.parseText(result[0]);
//			Object name=document.selectObject("name");
//			Object nick=document.selectObject("nick");
//		} catch (DocumentException e) {
//
//			e.printStackTrace();
//		}
		return result[0];
	}

	public String getRequestTokenUrl() {
		return requestTokenUrl;
	}

	public String getUserInfoUrl() {
		return userInfoUrl;
	}

	public String getAccessTokenUrl() {
		return accessTokenUrl;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}
}
