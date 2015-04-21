package edu.yale.its.tp.cas.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import edu.yale.its.tp.cas.ticket.GrantorCache;
import edu.yale.its.tp.cas.ticket.LoginTicketCache;
import edu.yale.its.tp.cas.ticket.ProxyGrantingTicket;
import edu.yale.its.tp.cas.ticket.ProxyTicket;
import edu.yale.its.tp.cas.ticket.ServiceTicket;
import edu.yale.its.tp.cas.ticket.ServiceTicketCache;
import edu.yale.its.tp.cas.ticket.TicketGrantingTicket;

/**
 * Sets up shared ticket caches for the CAS web application.
 */
public class CacheInit implements ServletContextListener {

	// *********************************************************************
	// Constants

	private static final int GRANTING_TIMEOUT_DEFAULT = 2 * 60 * 60;
	private static final int SERVICE_TIMEOUT_DEFAULT = 5 * 60;
	private static final int LOGIN_TIMEOUT_DEFAULT = 12 * 60 * 60;

	// *********************************************************************
	// Initialization

	public void contextInitialized(ServletContextEvent sce) {
		// retrieve appropriate initialization parameters
		ServletContext app = sce.getServletContext();
		String grantingTimeoutString = app
				.getInitParameter("edu.yale.its.tp.cas.grantingTimeout");
		String serviceTimeoutString = app
				.getInitParameter("edu.yale.its.tp.cas.serviceTimeout");
		String loginTimeoutString = app
				.getInitParameter("edu.yale.its.tp.cas.loginTimeout");
		int grantingTimeout, serviceTimeout, loginTimeout;
		try {
			grantingTimeout = Integer.parseInt(grantingTimeoutString);
		} catch (NumberFormatException ex) {
			grantingTimeout = GRANTING_TIMEOUT_DEFAULT;
		} catch (NullPointerException ex) {
			grantingTimeout = GRANTING_TIMEOUT_DEFAULT;
		}
		try {
			serviceTimeout = Integer.parseInt(serviceTimeoutString);
		} catch (NumberFormatException ex) {
			serviceTimeout = SERVICE_TIMEOUT_DEFAULT;
		} catch (NullPointerException ex) {
			serviceTimeout = SERVICE_TIMEOUT_DEFAULT;
		}
		try {
			loginTimeout = Integer.parseInt(loginTimeoutString);
		} catch (NumberFormatException ex) {
			loginTimeout = LOGIN_TIMEOUT_DEFAULT;
		} catch (NullPointerException ex) {
			loginTimeout = LOGIN_TIMEOUT_DEFAULT;
		}

		// set up the caches...

		GrantorCache tgcCache = new GrantorCache(TicketGrantingTicket.class,
				grantingTimeout);

		GrantorCache pgtCache = new GrantorCache(ProxyGrantingTicket.class,
				grantingTimeout);

		ServiceTicketCache stCache = new ServiceTicketCache(
				ServiceTicket.class, serviceTimeout);

		ServiceTicketCache ptCache = new ServiceTicketCache(ProxyTicket.class,
				serviceTimeout);

		LoginTicketCache ltCache = new LoginTicketCache(loginTimeout);

		// ... and share them
		app.setAttribute("tgcCache", tgcCache);
		app.setAttribute("pgtCache", pgtCache);
		app.setAttribute("stCache", stCache);
		app.setAttribute("ptCache", ptCache);
		app.setAttribute("ltCache", ltCache);
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}
