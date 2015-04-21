package com.lvmama.sso.web.union;

import hao3604j.Hao360Exception;
import hao3604j.http.RequestToken;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.sso.vo.Hao360V;

/**
 * 360联合登录RedirectAction
 *
 * @author panzhiyi
 *
 */
public class Hao360UnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2485809111836505122L;
    /**
     * Log类
     */
	private static final Log LOG = LogFactory
			.getLog(Hao360UnionLoginRedirectAction.class);

	@Override
	@Action("/cooperation/hao360UnionLogin")
	public String execute() {
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "HAO360";
	}

	@Override
	protected String redirect() throws IOException {
		Hao360V hao360 = new Hao360V();

		RequestToken resToken = null;
		try {
			resToken = (RequestToken) hao360
					.getOAuthRequestToken(getCallBackPage());
		} catch (Hao360Exception e) {
			e.printStackTrace();
		}
		if (resToken != null) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("获取到ResToken:" + resToken + "\t跳转页面地址:"
						+ resToken.getAuthenticationURL());
			}
			LOG.info("Got request token.");
			LOG.info("Request token: " + resToken.getToken());
			LOG.info("Request token secret: " + resToken.getTokenSecret());
			LOG.info("Request token AuthorizationURL: "
					+ resToken.getAuthorizationURL() + "&oauth_callback="
					+ getCallBackPage());

			getRequest().getSession().setAttribute("Hao360ResToken", resToken);
			getResponse().sendRedirect(
					resToken.getAuthorizationURL() + "&oauth_callback="
							+ getCallBackPage());
			return null;
		} else {
			LOG.error("无法获取到360的 resToken值，跳转失败");
		}
		return ERROR;
	}

}
