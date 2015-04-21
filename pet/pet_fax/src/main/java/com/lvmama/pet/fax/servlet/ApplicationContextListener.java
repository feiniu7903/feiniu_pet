package com.lvmama.pet.fax.servlet;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

/**
 * 不必专门为注入WebApplicationContext写一个servlet，继承ContextLoaderListener即可
 * 
 */
public class ApplicationContextListener extends ContextLoaderListener {

	/**
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
	 * @param event
	 *            ServletContextEvent
	 */
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);

		WebApplicationContext context = (WebApplicationContext) event
				.getServletContext()
				.getAttribute(
						WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);

		ServiceContext.setApplicationContext(context);
	}
}
