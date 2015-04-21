package com.lvmama.sso.web.union;

import java.io.IOException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

/**
 * 开心网联合登录重定向
 *
 * @author panzhiyi
 *
 */
public class KaixinUnionLoginRedirectAction extends
		AbstractUnionLoginRedirectAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 39630622890293604L;
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(KaixinUnionLoginRedirectAction.class);
	/**
	 * cookie生命周期
	 */
	private static final int LIFT_TIME = 60 * 60 * 24;

	@Override
	@Action("/cooperation/kaixinUnionLogin")
	public String execute() {
		LOG.info("开心网重定向开始");
		writeSessionKey(getRequest(), getResponse());
		return super.execute();
	}

	@Override
	protected String getCooperationName() {
		return "KAIXIN";
	}

	@Override
	protected String redirect() throws IOException {
		getResponse().sendRedirect(getCallBackPage());
		return null;
	}

	/**
	 * 将获得的用户sessionKey写入Cookie
	 *
	 * @param request request
	 * @param response response
	 */
	private void writeSessionKey(final HttpServletRequest request,
			final HttpServletResponse response) {
		String sessionKey = request.getParameter("session_key");
		Cookie cookie = new Cookie("kaixin_session_key", sessionKey);
		cookie.setPath("/");
		cookie.setDomain(".lvmama.com");
		cookie.setMaxAge(LIFT_TIME);
		cookie.setSecure(false);
		response.addCookie(cookie);
	}
}
