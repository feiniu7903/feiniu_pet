package com.lvmama.sso.service.impl.union;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.utils.UnionLoginUtil;
import com.snda.apipool.oauth.OAuthClient;
//import com.snda.apipool.oa.ApiPool;
//import com.snda.apipool.oa.Response;

/**
 * 盛大联合登陆服务实现类
 *
 * @author ganyingwen
 *
 */
public class SndaUnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(SndaUnionLoginService.class);
	/**
	 * HTTP请求参数对象
	 */
	private static JSONObject requestParams;
	/**
	 * Token Key
	 */
	private static final String SNDA_TOKEN_KEY = "access_token";
	/**
	 * Token URL
	 */
	private static final String SNDA_TOKEN_URL = "http://oauth.snda.com/oauth/token";
	/**
	 * API请求的url地址
	 */
//	private static final String SNDA_API_URL = "http://api.snda.com";
	/**
	 * API方法 ：格式化的用户账号
	 */
//	private static final String SNDA_API_MOTHOD_ACCOUNT = "sdo.accountdisplay_outer.displayacc_outer.DisplayAccount";
	/**
	 * 盛大通行证Id
	 */
	private String sdId;
	/**
	 * 格式化的用户账号
	 */
//	private String account;
	/**
	 * 访问令牌
	 */
	private String accessToken;

	static {
		// 初始化请求参数
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		requestParams = new JSONObject();
		try {
			requestParams.put("client_id", util.getValue("snda.consumer.key"));
			requestParams.put("redirect_uri",
					util.getValue("union.login.callbackPage"));
			requestParams.put("client_secret",
					util.getValue("snda.consumer.secrect"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		return this.getSdId(response, request);
	}

	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		// account = this.getAccount();
		UserUser users = UserUserUtil.genDefaultUser();
		users.setUserName("From SNDA(" + sdId + ")");
		return users;
	}

	/**
	 * 获取盛大通行证Id
	 * @param response response
	 * @param request requeset
	 * @return 盛大通行证
	 */
	private String getSdId(final HttpServletResponse response,
			final HttpServletRequest request) {
		if (sdId != null) {
			return sdId;
		}
		String authCode = request.getParameter("code");
		try {
			// 1.通过授权码code，获取令牌token和盛大通行证Id
			OAuthClient oauth = new OAuthClient();
			requestParams.put("code", authCode);
			JSONObject json = oauth.GetSystemToken(SNDA_TOKEN_URL,
					requestParams);
			if (json == null) {
				LOG.debug("cann't get token");
				return null;
			}
			accessToken = json.has("access_token") ? json
					.getString("access_token") : "";
			sdId = json.has("sdid") ? json.getString("sdid") : "";
			if (accessToken.equals("") || sdId.equals("")) {
				return null;
			}
			saveTokenToCookie(response, accessToken);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return sdId;
	}


	/**
	 * 保存登陆后的Token到cookie，方便下订单支付时操作
	 *
	 * @param response Response
	 * @param token token
	 */
	private void saveTokenToCookie(final HttpServletResponse response, final String token) {
		if (token.equals("")) {
			return;
		}
		Cookie cookie = new Cookie(SNDA_TOKEN_KEY, token);
		cookie.setDomain(".lvmama.com");
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		response.addCookie(cookie);
	}
}
