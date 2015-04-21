package com.lvmama.sso.web.union;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.json.JSONException;
import org.json.JSONObject;

import com.lvmama.sso.utils.UnionLoginUtil;
import com.snda.apipool.oauth.OAuthClient;

/**
 * 盛付通联合登陆Action
 *
 * @author ganyingwen
 *
 */
public class SndaUnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(SndaUnionLoginRedirectAction.class);
	/**
	 * 认证URL
	 */
	private static final String OAUTHORIZE_URL = "http://oauth.snda.com/oauth/authorize";
	/**
	 * 请求参数
	 */
	private static JSONObject requestParams;

	static {
		// 初始化requestParams属性
		UnionLoginUtil util = UnionLoginUtil.getInstance();
		requestParams = new JSONObject();
		try {
			requestParams.put("client_id", util.getValue("snda.consumer.key"));
			requestParams.put("redirect_uri",
					util.getValue("union.login.callbackPage"));
			requestParams.put("response_type",
					util.getValue("snda.consumer.response_type"));
			requestParams
					.put("display", util.getValue("snda.cousumer.display"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@Override
	@Action("/cooperation/sndaUnionLogin")
	public String execute() {
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "SNDA";
	}

	@Override
	protected String redirect() throws IOException {
		OAuthClient oauth = new OAuthClient();
		// 生成授权地址
		String authorizeUrl = oauth.generateAuthorizeUrl(OAUTHORIZE_URL,
				requestParams);
		LOG.info("redirect to:" + authorizeUrl);
		getResponse().sendRedirect(authorizeUrl);
		return null;
	}
}
