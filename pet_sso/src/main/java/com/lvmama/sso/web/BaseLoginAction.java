package com.lvmama.sso.web;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.utils.InfoBase64Coding;
import com.lvmama.sso.utils.LvmamaMd5Key;

import edu.yale.its.tp.cas.ticket.GrantorCache;
import edu.yale.its.tp.cas.ticket.ServiceTicket;
import edu.yale.its.tp.cas.ticket.ServiceTicketCache;
import edu.yale.its.tp.cas.ticket.TicketException;
import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;
import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * 基础登录类。
 * 此类主要是将一些sso所需要的公用方法提取出来，供各个实现逻辑类调用。
 *@author Brian
 *
 */
public class BaseLoginAction extends BaseAction implements CooperationLoginAction {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2361211866644538332L;
	/**
	 * 与语言环境有关的方式来格式化和解析日期的具体类
	 */
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	/**
	 * 用户ID开始位置
	 */
	private static final int POS = 3;
	/**
	 * Creates, sends (to the given ServletResponse), and returns a
	 * TicketGrantingTicket for the given username.
	 * @param username 用户名
	 * @param request request
	 * @param response response
	 * @return TicketGrantingTicket
	 * @throws ServletException ServletException
	 * @throws UnsupportedEncodingException 不支持编码
	 */
	protected final TicketGrantingTicket sendTgc(final String username,
			final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, UnsupportedEncodingException {
		GrantorCache tgcCache = (GrantorCache) getServletContext().getAttribute(TGC_CACHE);

		try {
			TicketGrantingTicket t = new TicketGrantingTicket(InfoBase64Coding.encrypt(username));
			String token = tgcCache.addTicket(t);
			//this.getResponse().addHeader("P3P",
			//"CP=\"CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR\"");
			Cookie tgc = new Cookie(TGC_ID, token);
			tgc.setDomain(DomainConstant.DOMAIN);
			tgc.setPath("/");
			tgc.setSecure(false);
			tgc.setMaxAge(-1);

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
	 * @param request request
	 * @param response response
	 * @throws ServletException ServletException
	 */
	protected final void sendPrivacyCookie(final HttpServletRequest request,
			final HttpServletResponse response) throws ServletException {
		if (request.getParameter("warn") != null) {
			// send the cookie if it's requested
			Cookie privacy = new Cookie(PRIVACY_ID, "enabled");
			privacy.setSecure(true);
			privacy.setMaxAge(-1);
			privacy.setPath(request.getContextPath());
			response.addCookie(privacy);
		} else if (privacyRequested(request)) {
			// delete the cookie if it's there but *not*requested this time
			Cookie privacy = new Cookie(PRIVACY_ID, "disabled");
			privacy.setSecure(true);
			privacy.setMaxAge(0);
			privacy.setPath(request.getContextPath());
			response.addCookie(privacy);
		}
	}

	/**
	 * If the user has so requested, creates and sends (to the given
	 * ServletResponse) a cookie recording the fact that the user wants to be
	 * warned before using CAS's single-sign-on capabilities.
	 * @param request request
	 * @param response response
	 * @param ticket 凭证
	 * @throws ServletException ServletException
	 */
	protected final void sendPrivacyCookie(final HttpServletRequest request,
			final HttpServletResponse response, final String ticket) throws ServletException {
		sendPrivacyCookie(request, response);
		Cookie tck = new Cookie("ticket", ticket);
		tck.setSecure(false);
		tck.setMaxAge(-1);
		tck.setDomain(DomainConstant.DOMAIN);
		tck.setPath("/");
		response.addCookie(tck);
	}

	/**
	 * Returns true if privacy has been requested, false otherwise.
	 * @param request request
	 * @return Returns true if privacy has been requested, false otherwise
	 */
	protected final boolean privacyRequested(final HttpServletRequest request) {
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
	 * Grants a service ticket for the given service, using the given
	 * TicketGrantingTicket. If no 'service' is specified, simply forward to
	 * message conveying generic success.
	 * @param request request
	 * @param response response
	 * @param t TicketGrantingTicket
	 * @param serviceId servicedId
	 * @param first first
	 * @return If no 'service' is specified, simply forward to message conveying generic success
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	public String grantForService(final HttpServletRequest request,
			final HttpServletResponse response, final TicketGrantingTicket t,
			final String serviceId, final boolean first) throws ServletException,
			IOException {

		try {
			ServiceTicket st = new ServiceTicket(t, serviceId, first);
			ServiceTicketCache stCache = (ServiceTicketCache) getServletContext().getAttribute(ST_CACHE);

			String token = stCache.addTicket(st);
			request.setAttribute("serviceId", serviceId);
			request.setAttribute("token", token);
			if (!first) {
				if (privacyRequested(request)) {
					return "confirmService";
				} else {
					request.setAttribute("first", "false");
					return "serviceSuccess";
				}
			} else {
				request.setAttribute("first", "true");
				return "serviceSuccess";
			}
		} catch (TicketException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 * Grants a service ticket for the given service, using the given TicketGrantingTicket
	 * @param request request
	 * @param response response
	 * @param t TicketGrantingTicket
	 * @param serviceId serviceId
	 * @param first first
	 * @return token凭证
	 * @throws ServletException ServletException
	 * @throws IOException IOException
	 */
	protected String grantForClient(final HttpServletRequest request,
			final HttpServletResponse response, final TicketGrantingTicket t,
			final String serviceId, final boolean first) throws ServletException,
			IOException {

		try {
			ServiceTicket st = new ServiceTicket(t, serviceId, first);
			ServiceTicketCache stCache = (ServiceTicketCache) getServletContext().getAttribute(ST_CACHE);
			String token = stCache.addTicket(st);
			request.setAttribute("serviceId", serviceId);
			request.setAttribute("token", token);
			if (!first) {
				if (privacyRequested(request)) {
					return token;
				} else {
					request.setAttribute("first", "false");
					return token;
				}
			} else {
				request.setAttribute("first", "true");
				return token;
			}
		} catch (TicketException ex) {
			throw new ServletException(ex.toString());
		}
	}

	/**
	 * 获取Cookie的值
	 * @param cookieName Cookie名字
	 * @return 值
	 */
	protected String getCookieValue(final String cookieName) {
		Cookie[] cookies = this.getRequest().getCookies();
		if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				Cookie cookie = cookies[i];
				if (cookieName.equals(cookie.getName())) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}	

	/**
	 * 把用户名写入Cookie
	 * @param userName 用户名
	 * @param response response
	 * @throws UnsupportedEncodingException 不支持编码
	 */
	protected void writeCookieWithName(final String userName, final HttpServletResponse response)
			 throws UnsupportedEncodingException {
		//"UN"的Cookie记录着用户登录后的用户名信息(只会在session中存在)
		Cookie cookie = new Cookie("UN", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setValue(URLEncoder.encode(userName, "UTF-8"));
		response.addCookie(cookie);

		//"unUserName"保存了用户的登录名，不论用户是否登录状态都会保留
		cookie = new Cookie("unUserName", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(Integer.parseInt(getServletContext().getInitParameter("userNameCookieTimeOut")));
		cookie.setPath("/");
		String unUserName = userName;
		String userId = userName;
		if (unUserName.indexOf("^!^") != -1) {
			unUserName = unUserName.substring(0, unUserName.indexOf("^!^"));
			userId = userName.substring(userName.indexOf("^!^") + POS, userName.length());
		}

		cookie.setValue(URLEncoder.encode(unUserName, "UTF-8"));
		response.addCookie(cookie);
		
		
		
		//临时保持原样LSTA cookie给攻略用。
		//LvmamaMd5Key这个类是原来老LVMAMA COMMON里的类，我在新LVMAMA COMMON里没找到，也不敢移进去
		//暂时保留在PET SSO项目里，到时采用新方法后一起移除
		//TODO BY liuyi
		cookie = new Cookie("LSTA", null);
		cookie.setDomain(DomainConstant.DOMAIN);
		cookie.setMaxAge(-1);
		cookie.setPath("/");
		cookie.setValue(LvmamaMd5Key.encode(userId));
		response.addCookie(cookie);

	}

	/**
	 * 向Request设置属性
	 * @param msg 信息
	 */
	protected void errorMessage(final String msg) {
		this.getRequest().setAttribute("errorMessages", msg);
	}
}
