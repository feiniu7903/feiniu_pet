package edu.yale.its.tp.cas.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.yale.its.tp.cas.ticket.GrantorCache;
import edu.yale.its.tp.cas.ticket.ProxyGrantingTicket;
import edu.yale.its.tp.cas.ticket.ProxyTicket;
import edu.yale.its.tp.cas.ticket.ServiceTicketCache;
import edu.yale.its.tp.cas.ticket.TicketException;

/**
 * Handles PT acquisition.
 */
public class Proxy extends HttpServlet {
	private static final long serialVersionUID = 5997122859660666492L;
	
	// *********************************************************************
	// Constants
	private static final String INVALID_REQUEST = "INVALID_REQUEST";
	private static final String BAD_PGT = "BAD_PGT";
	private static final String INTERNAL_ERROR = "INTERNAL_ERROR";

	// *********************************************************************
	// Private state

	private GrantorCache pgtCache;
	private ServiceTicketCache ptCache;

	// *********************************************************************
	// Initialization

	public void init(ServletConfig config) throws ServletException {
		// retrieve the context and the caches
		ServletContext app = config.getServletContext();
		pgtCache = (GrantorCache) app.getAttribute("pgtCache");
		ptCache = (ServiceTicketCache) app.getAttribute("ptCache");
	}

	// *********************************************************************
	// Request handling

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		PrintWriter out = null;
		try {
			out = response.getWriter();
			if (request.getParameter("pgt") == null
					|| request.getParameter("targetService") == null) {
				proxyFailure(out, INVALID_REQUEST,
						"'pgt' and 'targetService' parameters are both required");
			} else {
				String pgtId = request.getParameter("pgt");
				String targetService = request.getParameter("targetService");
				ProxyGrantingTicket pgt = (ProxyGrantingTicket) pgtCache
						.getTicket(pgtId);
				if (pgt == null)
					proxyFailure(out, BAD_PGT, "unrecognized pgt: '" + pgtId
							+ "'");
				else {
					ProxyTicket pt = new ProxyTicket(pgt, targetService);
					String token = ptCache.addTicket(pt);
					proxySuccess(out, token);
				}
			}
		} catch (TicketException ex) {
			try {
				if (out != null)
					proxyFailure(out, INTERNAL_ERROR, "Unexpected exception");
			} catch (IOException ignoredEx) {
				// ignore
			}
		}
	}

	/** Sends a proxy failure message to the given PrintWriter. */
	protected void proxyFailure(PrintWriter out, String code,
			String errorMessage) throws IOException {
		out.println("<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>");
		out.println("  <cas:proxyFailure code='" + code + "'>");
		out.println("    " + errorMessage);
		out.println("  </cas:proxyFailure>");
		out.println("</cas:serviceResponse>");
	}

	/** Sends a validation success message to the given PrintWriter. */
	protected void proxySuccess(PrintWriter out, String proxyToken) {
		out.println("<cas:serviceResponse xmlns:cas='http://www.yale.edu/tp/cas'>");
		out.println("  <cas:proxySuccess>");
		out.println("    <cas:proxyTicket>" + proxyToken + "</cas:proxyTicket>");
		out.println("  </cas:proxySuccess>");
		out.println("</cas:serviceResponse>");
	}

}
