package edu.yale.its.tp.cas.client.filter;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.lvmama.comm.utils.InfoBase64Coding;

import edu.yale.its.tp.cas.client.ProxyTicketValidator;
import edu.yale.its.tp.cas.client.Util;

// Referenced classes of package edu.yale.its.tp.cas.client.filter:
//            CASFilterRequestWrapper

public class CASFilter implements Filter {

	public static final String CAS_FILTER_USER = "edu.yale.its.tp.cas.client.filter.user";
	private String casLogin;
	private String casValidate;
	private String casAuthorizedProxy;
	private String casServiceUrl;
	private String casRenew;
	private String casServerName;
	private boolean wrapRequest;

	public void init(FilterConfig filterconfig) throws ServletException {
		casLogin = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.loginUrl");
		casValidate = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.validateUrl");
		casServiceUrl = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.serviceUrl");
		casAuthorizedProxy = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.authorizedProxy");
		casRenew = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.renew");
		casServerName = filterconfig
				.getInitParameter("edu.yale.its.tp.cas.client.filter.serverName");
		wrapRequest = Boolean
				.valueOf(
						filterconfig
								.getInitParameter("edu.yale.its.tp.cas.client.filter.wrapRequest"))
				.booleanValue();
	}

	public void doFilter(ServletRequest servletrequest,
			ServletResponse servletresponse, FilterChain filterchain)
			throws ServletException, IOException {
		if (!(servletrequest instanceof HttpServletRequest)
				|| !(servletresponse instanceof HttpServletResponse)) {
			throw new ServletException("CASFilter protects only HTTP resources");
		}
		if (wrapRequest) {
			servletrequest = new CASFilterRequestWrapper(
					(HttpServletRequest) servletrequest);
		}
		HttpSession httpsession = ((HttpServletRequest) servletrequest)
				.getSession();
		if (httpsession != null
				&& httpsession
						.getAttribute("edu.yale.its.tp.cas.client.filter.user") != null) {
			filterchain.doFilter(servletrequest, servletresponse);
			return;
		}
		String s = servletrequest.getParameter("ticket");
		if (s == null || s.equals("")) {
			if (casLogin == null) {
				throw new ServletException(
						"When CASFilter protects pages that do not receive a 'ticket' parameter, it needs a edu.yale.its.tp.cas.client.filter.loginUrl filter parameter");
			} else {
				((HttpServletResponse) servletresponse).sendRedirect(casLogin
						+ "?service="
						+ getService((HttpServletRequest) servletrequest)
						+ (casRenew == null || casRenew.equals("") ? ""
								: "&renew=" + casRenew));
				return;
			}
		}
		String s1 = getAuthenticatedUser((HttpServletRequest) servletrequest);
		if (s1 == null) {
			throw new ServletException("Unexpected CAS authentication error");
		}
		if (httpsession != null) {
			String b = InfoBase64Coding.decrypt(s1);
			httpsession.setAttribute("edu.yale.its.tp.cas.client.filter.user",
					b);
		}
		filterchain.doFilter(servletrequest, servletresponse);
	}

	public void destroy() {
	}

	private String getAuthenticatedUser(HttpServletRequest httpservletrequest)
			throws ServletException {
		ProxyTicketValidator proxyticketvalidator = null;
		try {
			proxyticketvalidator = new ProxyTicketValidator();
			proxyticketvalidator.setCasValidateUrl(casValidate);
			proxyticketvalidator.setServiceTicket(httpservletrequest
					.getParameter("ticket"));
			proxyticketvalidator.setService(getService(httpservletrequest));
			proxyticketvalidator.setRenew(Boolean.valueOf(casRenew)
					.booleanValue());
			proxyticketvalidator.validate();
			if (!proxyticketvalidator.isAuthenticationSuccesful()) {
				throw new ServletException("CAS authentication error: "
						+ proxyticketvalidator.getErrorCode() + ": "
						+ proxyticketvalidator.getErrorMessage());
			}
			if (proxyticketvalidator.getProxyList().size() != 0) {
				if (casAuthorizedProxy == null) {
					throw new ServletException(
							"this page does not accept proxied tickets");
				}
				boolean flag = false;
				String s = (String) proxyticketvalidator.getProxyList().get(0);
				StringTokenizer stringtokenizer = new StringTokenizer(
						casAuthorizedProxy);
				do {
					if (!stringtokenizer.hasMoreTokens()) {
						break;
					}
					if (!s.equals(stringtokenizer.nextToken())) {
						continue;
					}
					flag = true;
					break;
				} while (true);
				if (!flag) {
					throw new ServletException(
							"unauthorized top-level proxy: '"
									+ proxyticketvalidator.getProxyList()
											.get(0) + "'");
				}
			}
			return proxyticketvalidator.getUser();
		} catch (SAXException saxexception) {
			String s1 = "";
			if (proxyticketvalidator != null) {
				s1 = proxyticketvalidator.getResponse();
			}
			throw new ServletException(saxexception + " " + s1);
		} catch (ParserConfigurationException parserconfigurationexception) {
			throw new ServletException(parserconfigurationexception);
		} catch (IOException ioexception) {
			throw new ServletException(ioexception);
		}
	}

	@SuppressWarnings("deprecation")
	private String getService(HttpServletRequest httpservletrequest)
			throws ServletException {
		if (casServerName == null && casServiceUrl == null) {
			throw new ServletException(
					"need one of the following configuration parameters: edu.yale.its.tp.cas.client.filter.serviceUrl or edu.yale.its.tp.cas.client.filter.serverName");
		}
		if (casServiceUrl != null) {
			return URLEncoder.encode(casServiceUrl);
		} else {
			return Util.getService(httpservletrequest, casServerName);
		}
	}

}
