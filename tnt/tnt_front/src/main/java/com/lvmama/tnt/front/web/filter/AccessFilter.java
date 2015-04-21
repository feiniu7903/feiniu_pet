/**
 * 登陆验证跳转
 * @author william liu
 * @modify courage,bing
 */
package com.lvmama.tnt.front.web.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.lvmama.tnt.comm.util.TntUtil;
import com.lvmama.tnt.comm.vo.TntConstant;
import com.lvmama.tnt.user.po.TntUser;

public class AccessFilter implements Filter {

	// 用户管理
	private static Logger log = Logger.getLogger(AccessFilter.class);
	private static String casLoginUrl;
	private static String[] urls;

	public void destroy() {
	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) arg0;
		HttpServletResponse res = (HttpServletResponse) arg1;
		String path = req.getRequestURI();
		if (log.isDebugEnabled()) {
			log.debug("filter::::::::::::::" + path);
		}
		if (isInCheck(path, req.getContextPath())) {
			if (!isLogon(req, res)) {
				String reqUrl = req.getRequestURL().toString();
				log.info(" AccessFilter getRequestURL " + reqUrl);
				Map<String, String[]> paras = req.getParameterMap();
				if (paras != null) {
					Set<String> set = paras.keySet();
					int i = 0;
					for (String key : set) {
						if (!key.equalsIgnoreCase("ticket")) {
							if (i == 0) {
								reqUrl = reqUrl + "?" + key + "="
										+ paras.get(key)[0];
							} else {
								reqUrl = reqUrl + "&" + key + "="
										+ paras.get(key)[0];
							}
							i++;
						}
					}
				}
				res.sendRedirect(casLoginUrl + "?service="
						+ URLEncoder.encode(reqUrl, "UTF-8"));
				return;
			} else if (path.indexOf("auditInfo.do") >= 0
					|| path.indexOf("userAuditSave.do") >= 0) {

			} else {
				TntUser tntUser = (TntUser) req.getSession().getAttribute(
						TntConstant.SESSION_TNT_USER);
				if (!TntConstant.USER_INFO_STATUS.isActived(tntUser.getDetail()
						.getInfoStatus())) {
					String url = null;
					String redirect = null;
					try {
						if (TntConstant.USER_INFO_STATUS.WAITING.name()
								.equalsIgnoreCase(
										tntUser.getDetail().getInfoStatus())) {
							url = "/reg/regWaiting";
						} else if (TntConstant.USER_INFO_STATUS.REWAITING
								.name().equalsIgnoreCase(
										tntUser.getDetail().getInfoStatus())) {
							url = "/reg/auditActive";
						} else if (TntConstant.USER_INFO_STATUS.NEEDACTIVE
								.name().equalsIgnoreCase(
										tntUser.getDetail().getInfoStatus())) {
							req.setAttribute("email", tntUser.getEmail());
							req.setAttribute("userId", tntUser.getUserId());
							url = "/reg/regActive";
						} else if (TntConstant.USER_INFO_STATUS.REJECT.name()
								.equalsIgnoreCase(
										tntUser.getDetail().getInfoStatus())
								|| TntConstant.USER_INFO_STATUS.REREJECT.name()
										.equalsIgnoreCase(
												tntUser.getDetail()
														.getInfoStatus())) {
							redirect = "/user/auditInfo.do";
						}
					} catch (Exception e) {
						redirect = "/login/index";
					}
					if (url != null) {
						url = TntUtil.getPageUrl(url);
						req.getRequestDispatcher(url).forward(req, res);
					} else {
						redirect = redirect == null ? "/index" : redirect;
						res.sendRedirect(redirect);
					}
					return;
				}
			}
		}
		long beginTime = System.currentTimeMillis();
		arg2.doFilter(arg0, arg1);
		long count = System.currentTimeMillis() - beginTime;
		if (log.isDebugEnabled()) {
			log.debug(req.getRequestURI() + " spent " + count + " ms.");
		}
	}

	private boolean isLogon(HttpServletRequest req, HttpServletResponse resp) {
		TntUser tntUser = (TntUser) req.getSession().getAttribute(
				TntConstant.SESSION_TNT_USER);
		if (log.isDebugEnabled()) {
			log.info("tntUser " + tntUser);
		}
		if (tntUser == null || (tntUser != null && tntUser.getUserId() == null)) {
			return false;
		}
		return true;
	}

	/**
	 * 解析urlrewrite path.
	 * 
	 * @param path
	 *            .
	 * @return String 解析后的url.
	 */
	private boolean isInCheck(final String uri, final String contextPath) {
		String path = uri.replaceFirst(contextPath, "");
		if (checkUrl(path)) {
			if (urls == null)
				return false;
			if (urls.length == 0)
				return false;
			// if(path.contains(".do")){
			for (int i = 0; i < urls.length; i++) {
				if (pathMatcher.match(urls[i], path)) {
					return true;
				}
			}
			// return false;
			// }

		}
		return false;
	}

	/**
	 * 以下后缀的不需要过滤
	 * 
	 * @param str
	 * @return
	 */
	private boolean checkUrl(String str) {
		String[] s = new String[] { ".jpg", ".js", ".gif", ".css" };
		for (int i = 0; i < s.length; i++) {
			if (str.toLowerCase().lastIndexOf(s[i]) > 0) {
				return false;
			}
		}
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
	 */
	public void init(FilterConfig arg0) throws ServletException {
		casLoginUrl = arg0.getInitParameter("casLoginUrl");
		if (casLoginUrl == null || casLoginUrl.isEmpty()) {
			casLoginUrl = "/login/index";
		}
		String temp = arg0.getInitParameter("url");
		if (log.isDebugEnabled())
			log.debug("get init parameter");
		if (temp != null) {
			urls = temp.split(",");
		}
	}

	private static final PathMatcher pathMatcher = new AntPathMatcher();

}
