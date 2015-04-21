package com.lvmama.sso.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.sso.auth.AuthHandler;
import com.lvmama.sso.auth.PasswordHandler;
import com.lvmama.sso.auth.TrustHandler;
import com.lvmama.sso.utils.LvmamaMd5Key;
import com.lvmama.sso.utils.SSOUtil;

import edu.yale.its.tp.cas.ticket.GrantorCache;
import edu.yale.its.tp.cas.ticket.LoginTicketCache;
import edu.yale.its.tp.cas.ticket.ServiceTicket;
import edu.yale.its.tp.cas.ticket.ServiceTicketCache;
import edu.yale.its.tp.cas.ticket.TicketException;
import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;
import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * Handles logins for the Central Authentication Service.
 */
public class Login extends HttpServlet {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 335913317598616396L;

	// cookie IDs
	/**
	 * TGC_ID
	 */
	private static final String TGC_ID = "CASTGC";
	/**
	 * PRIVACY_ID
	 */
	private static final String PRIVACY_ID = "CASPRIVACY";

	// parameters
	/**
	 * SERVICE
	 */
	private static final String SERVICE = "service";
	/**
	 * RENEW
	 */
	private static final String RENEW = "renew";
	/**
	 * GATEWAY
	 */
	private static final String GATEWAY = "gateway";

	// *********************************************************************
	// Private state
	/**
	 * tgcCache
	 */
	private GrantorCache tgcCache;
	/**
	 * stCache
	 */
	private ServiceTicketCache stCache;
	/**
	 * ltCache
	 */
	private LoginTicketCache ltCache;
	/**
	 * handler
	 */
	private AuthHandler handler;
	/**
	 * field name
	 */
	private String loginForm, genericSuccess, serviceSuccess, confirmService,
			redirect;
	/**
	 * 上下文
	 */
	private ServletContext app;
	/**
	 *
	 */
	private static final int ID_POS = 3;
	/**
	 * 下次自动登陆
	 */
	private String keepLogin;
	
	// *********************************************************************
	// Initialization

	/**
	 * 初始化
	 *
	 * @param config
	 *            上下文
	 * @throws ServletException
	 *             ServletException
	 */
	public void init(final ServletConfig config) throws ServletException {
		// retrieve the context and the caches
		app = config.getServletContext();
		tgcCache = (GrantorCache) app.getAttribute("tgcCache");
		stCache = (ServiceTicketCache) app.getAttribute("stCache");
		ltCache = (LoginTicketCache) app.getAttribute("ltCache");

		try {
			// create an instance of the right authentication handler
			String handlerName = app
					.getInitParameter("edu.yale.its.tp.cas.authHandler");
			if (handlerName == null) {
				throw new ServletException(
						"need edu.yale.its.tp.cas.authHandler");
			}
			handler = (AuthHandler) Class.forName(handlerName).newInstance();
			if (!(handler instanceof TrustHandler)
					&& !(handler instanceof PasswordHandler)) {
				throw new ServletException("unrecognized handler type: "
						+ handlerName);
			}
		} catch (InstantiationException ex) {
			throw new ServletException(ex.toString());
		} catch (ClassNotFoundException ex) {
			throw new ServletException(ex.toString());
		} catch (IllegalAccessException ex) {
			throw new ServletException(ex.toString());
		}

		// retrieve a relative URL for the login form
		loginForm = app.getInitParameter("edu.yale.its.tp.cas.loginForm");
		serviceSuccess = app.getInitParameter("edu.yale.its.tp.cas.serviceSuccess");
		genericSuccess = app
				.getInitParameter("edu.yale.its.tp.cas.genericSuccess");
		confirmService = app
				.getInitParameter("edu.yale.its.tp.cas.confirmService");
		redirect = app.getInitParameter("edu.yale.its.tp.cas.redirect");
		if (loginForm == null || genericSuccess == null || redirect == null
				|| confirmService == null) {
			throw new ServletException(
					"need edu.yale.its.tp.cas.loginForm, "
							+ "-genericSuccess, -serviceSuccess, -redirect, and -confirmService");
		}
	}

	// *********************************************************************
	// Request handling

	/**
	 * doPost
	 *
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	public void doPost(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		doGet(request, response);
	}

	/**
	 * doGet
	 *
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	public void doGet(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException,
			IOException {
		// avoid caching (in the stupidly numerous ways we must)
//		response.setHeader("Pragma", "no-cache");
//		response.setHeader("Cache-Control", "no-store");
//		response.setDateHeader("Expires", -1);

		keepLogin = request.getParameter("keepLogin");

		// check to see whether we've been sent a valid TGC
		Cookie[] cookies = request.getCookies();
		TicketGrantingTicket tgt = null;
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(TGC_ID)) {
					tgt = (TicketGrantingTicket) tgcCache.getTicket(cookies[i]
							.getValue());
					if (tgt == null) {
						continue;
					}
					// unless RENEW is set, let the user through to the service.
					// otherwise, fall through and we'll be handled by
					// authentication
					// below. Note that tgt is still active.
					if (request.getParameter(RENEW) == null && null != ServletUtil.getSession(request, response, Constant.SESSION_FRONT_USER)) {
						grantForService(request, response, tgt,
								request.getParameter(SERVICE), false);

						return;
					}
				}
			}
		}

		// if not, but if we're passed "gateway", then simply bounce back
		if (request.getParameter(SERVICE) != null
				&& request.getParameter(GATEWAY) != null) {
			request.setAttribute("serviceId", request.getParameter(SERVICE));
			app.getRequestDispatcher(redirect).forward(request, response);
			return;
		}
		boolean needCheckVerifyCode = SSOUtil.needCheckVerifyCode(request) ;
		// if not, then see if our AuthHandler can help
		if (handler instanceof TrustHandler) {
			// try to get a trusted username by interpreting the request
			String trustedUsername = ((TrustHandler) handler)
					.getUsername(request);
			if (trustedUsername != null) {
				// success: send a new TGC if we don't have a valid TGT from
				// above
				if (tgt == null) {
					tgt = sendTgc(trustedUsername, request, response);
				} else if (!tgt.getUsername().equals(trustedUsername)) {
					// we're coming into a renew=true as a different user...
					// expire the old tgt
					tgt.expire();
					// and send a new one
					tgt = sendTgc(trustedUsername, request, response);
				}
				sendPrivacyCookie(request, response);
				grantForService(request, response, tgt,
						request.getParameter(SERVICE), true);
				return;
			} else {
				// failure: nothing else to be done
				throw new ServletException("unable to authenticate user");
			}
		} else if (handler instanceof PasswordHandler
				&& StringUtils.isNotBlank(request.getParameter("username"))
				&& StringUtils.isNotBlank(request.getParameter("password"))
				&& (!needCheckVerifyCode || StringUtils.isNotBlank(request.getParameter("verifycode")))
				&& request.getParameter("lt") != null) {
			if(!SSOUtil.checkLoginErrorCount(request)){
				// 同一个IP登录次数过于频繁
				request.setAttribute("edu.yale.its.tp.cas.loginCount2many", "");
			}else{
				String userName = request.getParameter("username");
				String password = request.getParameter("password");
				String verifycode = request.getParameter("verifycode");
				
				//登录4位验证码检查
				if (SSOUtil.needCheckVerifyCode(request) && !SSOUtil.checkKaptchaCode(request, verifycode, true)) {
					// failure: record failed verifycode authentication
					request.setAttribute("edu.yale.its.tp.cas.badVerifyCode", "");
				}
				// do we have a valid login ticket?
				else if (ltCache.getTicket(request.getParameter("lt")) != null) {
					// do we have a valid username and password?
					UserUser user = ((PasswordHandler) handler).authenticate(
							request, response, userName, password,"Front");
					if (null != user) {
//						SSOUtil.clearLoginErrorCount(request);
						// success: send a new TGC if we don't have a valid TGT from
						// above
						String tgtUsername = user.getUserName() + "^!^" + user.getUserNo();
						if (tgt == null) {
							tgt = sendTgc(tgtUsername, request, response);
						} else if (!tgt.getUsername().equals(tgtUsername)) {
							// we're coming into a renew=true as a different user...
							// expire the old tgt
							tgt.expire();
							// and send a new one
							tgt = sendTgc(tgtUsername, request, response);
						}
						//用户邮箱未登记或者未验证的标识
						if (StringUtils.isBlank(user.getEmail())) {
							Cookie tck = new Cookie("EMV", "E");
							//tck.setSecure(false);
							tck.setMaxAge(-1);
							tck.setDomain(DomainConstant.DOMAIN);
							tck.setPath("/");
							response.addCookie(tck);
						} 
						if (StringUtils.isNotBlank(user.getEmail()) && !"Y".equals(user.getIsEmailChecked())) {
							Cookie tck = new Cookie("EMV", "U");
							//tck.setSecure(false);
							tck.setMaxAge(-1);
							tck.setDomain(DomainConstant.DOMAIN);
							tck.setPath("/");
							response.addCookie(tck);
						}					
						sendPrivacyCookie(request, response);
						grantForService(request, response, tgt,
								request.getParameter(SERVICE), true);
						
						
						return;
					} else {
						SSOUtil.addLoginErrorCount(request);
						// failure: record failed password authentication
						request.setAttribute(
								"edu.yale.its.tp.cas.badUsernameOrPassword", "");
						request.setAttribute("userName",request.getParameter("username"));
					}
				} else {
					SSOUtil.addLoginErrorCount(request);
					// failure: record invalid login ticket
					request.setAttribute("edu.yale.its.tp.cas.badLoginTicket", "");
					// horrible way of logging, I know
				}
			}
		}
		// record the service in the request
		request.setAttribute("edu.yale.its.tp.cas.service",request.getParameter(SERVICE));

		// no success yet, so generate a login ticket and forward to the
		// login form
		try {
			String lt = ltCache.addTicket();
			request.setAttribute("edu.yale.its.tp.cas.lt", lt);
		} catch (TicketException ex) {
			throw new ServletException(ex);
		}
		request.setAttribute("needCheckVerifyCode", SSOUtil.needCheckVerifyCode(request));
		app.getRequestDispatcher(loginForm).forward(request, response);
		
	}

	/**
	 * Grants a service ticket for the given service, using the given
	 * TicketGrantingTicket. If no 'service' is specified, simply forward to
	 * message conveying generic success.
	 *
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @param t t
	 * @param serviceId serviceId
	 * @param first first
	 * @throws ServletException
	 *             ServletException
	 * @throws IOException
	 *             IOException
	 */
	private void grantForService(final HttpServletRequest request,
			final HttpServletResponse response, final TicketGrantingTicket t,
			final String serviceId, final boolean first) throws ServletException,
			IOException {
		try {
			String actualServiceId = serviceId != null ? serviceId : "http://www.lvmama.com/";
			if(actualServiceId.contains("&amp;")){
				//log("service=="+actualServiceId);
				actualServiceId = actualServiceId.replaceAll("&amp;", "&");
			}
			ServiceTicket st = new ServiceTicket(t, actualServiceId, first);
			String token = stCache.addTicket(st);
			request.setAttribute("serviceId", actualServiceId);
			request.setAttribute("token", token);
			if (!first) {
				if (privacyRequested(request)) {
					app.getRequestDispatcher(confirmService).forward(request,
							response);
				} else {
					request.setAttribute("first", "false");
					Cookie unCookie = null;
					Cookie[] cookies = request.getCookies();
					if (null != cookies) {
						for (int i = 0; i < cookies.length; i++) {
							if (cookies[i].getName().equals("UN")) {
								unCookie = cookies[i];
							}
						}
					}
					if (null == unCookie) {
						unCookie = new Cookie("UN", null);
						unCookie.setDomain(DomainConstant.DOMAIN);
						unCookie.setMaxAge(StringUtils.isNotEmpty(keepLogin)
								? Integer.parseInt(app.getInitParameter("UNCookieTimeOut")) : -1);
						unCookie.setPath("/");
						
						unCookie.setValue(URLEncoder.encode(new String(InfoBase64Coding.decrypt(t.getUsername())), "UTF-8"));

						response.addCookie(unCookie);
					}

					app.getRequestDispatcher(serviceSuccess).forward(request,
							response);
				}
			} else {
				request.setAttribute("first", "true");
				Cookie unCookie = null;
				Cookie[] cookies = request.getCookies();
				if (null != cookies) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equals("UN")) {
							unCookie = cookies[i];
						}
					}
				}
				if (null == unCookie) {
					unCookie = new Cookie("UN", null);
					unCookie.setDomain(DomainConstant.DOMAIN);
					unCookie.setMaxAge(StringUtils.isNotEmpty(keepLogin)
							? Integer.parseInt(app.getInitParameter("UNCookieTimeOut")) : -1);
					unCookie.setPath("/");
					unCookie.setValue(URLEncoder.encode(new String(InfoBase64Coding.decrypt(t.getUsername())), "UTF-8"));
					response.addCookie(unCookie);
				}
				app.getRequestDispatcher(serviceSuccess).forward(request,
						response);
			}
		} catch (TicketException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 * Creates, sends (to the given ServletResponse), and returns a
	 * TicketGrantingTicket for the given username.
	 * @param username username
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @return TicketGrantingTicket
	 * @throws ServletException ServletException
	 * @throws UnsupportedEncodingException UnsupportedEncodingException
	 */
	private TicketGrantingTicket sendTgc(final String username,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		try {
			TicketGrantingTicket t = new TicketGrantingTicket(
					InfoBase64Coding.encrypt(username));
			String token = tgcCache.addTicket(t);
			Cookie tgc = new Cookie(TGC_ID, token);
			tgc.setSecure(false);
			tgc.setMaxAge(StringUtils.isNotEmpty(keepLogin)
					? Integer.parseInt(app.getInitParameter("UNCookieTimeOut")) : -1);
			tgc.setPath("/");
			tgc.setDomain(DomainConstant.DOMAIN);
			response.addCookie(tgc);
			writeCookieWithName(username, response);
			return t;
		} catch (TicketException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 * If the user has so requested, creates and sends (to the given
	 * ServletResponse) a cookie recording the fact that the user wants to be
	 * warned before using CAS's single-sign-on capabilities.
	 * @param request
	 *            request
	 * @param response
	 *            response
	 * @throws ServletException ServletException
	 */
	private void sendPrivacyCookie(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException {
		if (request.getParameter("warn") != null) {
			// send the cookie if it's requested
			Cookie privacy = new Cookie(PRIVACY_ID, "enabled");
			privacy.setSecure(true);
			privacy.setMaxAge(-1);
			privacy.setPath(request.getContextPath());
			response.addCookie(privacy);
		} else if (privacyRequested(request)) {
			// delete the cookie if it's there but *not* requested this time
			Cookie privacy = new Cookie(PRIVACY_ID, "disabled");
			privacy.setSecure(true);
			privacy.setMaxAge(0);
			privacy.setPath(request.getContextPath());
			response.addCookie(privacy);
		}
	}

	/**
	 * Returns true if privacy has been requested, false otherwise.
	 * @param request request
	 * @return true or false
	 */
	private boolean privacyRequested(final HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals(PRIVACY_ID)
						&& cookies[i].getValue().equals("enabled")) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 把用户名写入cookie
	 * @param userName userName
	 * @param response response
	 * @throws UnsupportedEncodingException UnsupportedEncodingException
	 */
	private void writeCookieWithName(final String userName,
			final HttpServletResponse response) throws UnsupportedEncodingException {
		// "UN"的Cookie记录着用户登录后的用户名信息(只会在session中存在)
		Cookie cookie = new Cookie("UN", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(StringUtils.isNotEmpty(keepLogin)
				? Integer.parseInt(app.getInitParameter("UNCookieTimeOut")) : -1);
		cookie.setPath("/");
		cookie.setValue(URLEncoder.encode(userName, "UTF-8"));
		response.addCookie(cookie);

		// "unUserName"保存了用户的登录名，不论用户是否登录状态都会保留
		cookie = new Cookie("unUserName", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(Integer.parseInt(app
				.getInitParameter("userNameCookieTimeOut")));
		cookie.setPath("/");
		String unUserName = userName;
		String userId = userName;
		if (unUserName.indexOf("^!^") != -1) {
			unUserName = unUserName.substring(0, unUserName.indexOf("^!^"));
			userId = userName.substring(userName.indexOf("^!^") + ID_POS, userName.length());
		}
		cookie.setValue(URLEncoder.encode(unUserName, "UTF-8"));
		response.addCookie(cookie);

		//临时保持原样LSTA cookie给攻略用。
		//LvmamaMd5Key这个类是原来老LVMAMA COMMON里的类，我在新LVMAMA COMMON里没找到，也不敢移进去
		//暂时保留在PET SSO项目里，到时采用新方法后一起移除
		//TODO BY liuyi
		cookie = new Cookie("LSTA", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(StringUtils.isNotEmpty(keepLogin)
				? Integer.parseInt(app.getInitParameter("UNCookieTimeOut")) : -1);
		cookie.setPath("/");
		cookie.setValue(LvmamaMd5Key.encode(userId));
		response.addCookie(cookie);
	}
}
