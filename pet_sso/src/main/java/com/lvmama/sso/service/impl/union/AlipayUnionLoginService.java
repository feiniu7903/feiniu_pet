package com.lvmama.sso.service.impl.union;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alipay.config.AlipayConfig;
import com.alipay.util.AlipayNotify;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.web.union.AlipayUnionLoginRedirectAction;

/**
 * @author yangbin
 */
public class AlipayUnionLoginService implements UnionLoginService {
	/**
	 * 令牌KEY
	 */
	private static final String TOKEN_KEY = "token";
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(AlipayUnionLoginRedirectAction.class);

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.lvmama.sso.service.UnionLoginService#generateUsers(javax.servlet.
	 * http.HttpServletRequest)
	 */
	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		// TODO Auto-generated method stub
		// return null;
		UserUser users = UserUserUtil.genDefaultUser();
		Map<String, String> params = parseRequest(request);
		users.setUserName("From alipay" + params.get("real_name") + "("
				+ params.get("user_id") + ")");
		return users;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * com.lvmama.sso.service.UnionLoginService#getThirdCooperationUUID(javax
	 * .servlet.http.HttpServletRequest)
	 */
	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		// TODO Auto-generated method stub
		LOG.info("ALIPAY LOGIN COMPLETE..");
		String userid = null; // 支付宝用户编号
		String token = null;
		Map<String, String> params = parseRequest(request);

		if (AlipayNotify.verify(params)) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("verify complete ");
			}

			userid = params.get("user_id");
			if (params.containsKey(TOKEN_KEY)) {
				token = params.get(TOKEN_KEY);

				addTokenToCookie(response, token);
			}

		}
		if (LOG.isInfoEnabled()) {
			LOG.info("alipay user_id " + userid);
			LOG.info("alipay token " + token);
		}
		return userid;
	}

	/**
	 * 解析请求信息，并封装成MAP结构
	 * @param request request
	 * @return MAP结构
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> parseRequest(final HttpServletRequest request) {
		Map<String, String> params = new HashMap<String, String>();
		Map requestParams = request.getParameterMap();
		for (Iterator<String> it = requestParams.keySet().iterator(); it
				.hasNext();) {
			String name = it.next();
			String[] values = (String[]) requestParams.get(name);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < values.length; i++) {
				if (i > 0) {
					sb.append(",");
				}
				sb.append(values[i]);
			}
			if (LOG.isDebugEnabled()) {
				LOG.debug("request " + name + "   value:" + sb.toString());
			}
			try {
				params.put(name, new String(sb.toString()
						.getBytes("ISO-8859-1"), "UTF-8"));
			} catch (UnsupportedEncodingException ex) {
				ex.printStackTrace();
			}
		}

		return params;
	}

	/**
	 * token 保存在cookie当中供后面的用户直接支付宝付款处理
	 *
	 * @param res response
	 * @param token 凭证
	 */
	private void addTokenToCookie(final HttpServletResponse res, final String token) {
		Cookie cookie = new Cookie(AlipayConfig.ALIPAY_TOKEN_KEY, token);
		cookie.setDomain(".lvmama.com");
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		res.addCookie(cookie);
	}



}
