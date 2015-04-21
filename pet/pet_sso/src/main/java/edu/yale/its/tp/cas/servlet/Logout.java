package edu.yale.its.tp.cas.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lvmama.sso.utils.SSOUtil;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * Lets users explicitly log out from the Central Authentication Servlet.
 */
public class Logout extends HttpServlet {
	private static final long serialVersionUID = 1201631178286936846L;
	
	private ServletContext app;
	private String logoutPage;

	// *********************************************************************
	// Initialization

	public void init(ServletConfig config) throws ServletException {
		// retrieve the context and the caches
		app = config.getServletContext();

		// retrieve a relative URL for the login form
		logoutPage = app.getInitParameter("edu.yale.its.tp.cas.logoutPage");
		if (logoutPage == null)
			throw new ServletException("need edu.yale.its.tp.cas.logoutPage");
	}

	// *********************************************************************
	// Request handling

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// avoid caching (in the stupidly numerous ways we must)
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);

		SSOUtil.logout(request, response, app);

		// forward to the UI to reassure the user
		if (null == request.getParameter("serviceId")) {
			response.sendRedirect("http://www.lvmama.com");
		} else {
			Cookie cookie=new Cookie("serviceId",null);
			cookie.setDomain(DomainConstant.DOMAIN);
			cookie.setMaxAge(-1);
			cookie.setPath("/");
			cookie.setValue(request.getParameter("serviceId"));
			response.addCookie(cookie);
			response.sendRedirect(request.getParameter("serviceId"));
		}
	}
}
