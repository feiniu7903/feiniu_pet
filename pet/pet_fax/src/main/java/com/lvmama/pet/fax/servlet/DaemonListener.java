package com.lvmama.pet.fax.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.lvmama.pet.fax.daemon.EastfaxReceiveDaemon;
import com.lvmama.pet.fax.daemon.EastfaxSendDaemon;
import com.lvmama.pet.fax.daemon.TrafaxErrorDaemon;
import com.lvmama.pet.fax.daemon.TrafaxReceiveDaemon;
import com.lvmama.pet.fax.daemon.TrafaxSendDaemon;
import com.lvmama.pet.fax.utils.Constant;

/**
 * Application Lifecycle Listener implementation class DaemonListener
 *
 */
public class DaemonListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public DaemonListener() {
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0) {
    	if (Constant.isTraFaxServerRecv()) {
    		TrafaxReceiveDaemon trafaxReceiveDaemon = (TrafaxReceiveDaemon)ServiceContext.getBean("trafaxReceiveDaemon");
    		trafaxReceiveDaemon.start();
    	} else {
    		EastfaxReceiveDaemon eastfaxReceiveDaemon = (EastfaxReceiveDaemon)ServiceContext.getBean("eastfaxReceiveDaemon");
    		eastfaxReceiveDaemon.start();
    		
    	}
    	if (Constant.isTraFaxServerSend()) {
    		TrafaxSendDaemon trafaxSendDaemon = (TrafaxSendDaemon)ServiceContext.getBean("trafaxSendDaemon");
    		trafaxSendDaemon.start();
    	} else {
    		EastfaxSendDaemon eastfaxSendDaemon = (EastfaxSendDaemon)ServiceContext.getBean("eastfaxSendDaemon");
    		eastfaxSendDaemon.start();
    	}
    	if(Constant.isTraFaxServerError()) {
    		TrafaxErrorDaemon trafaxErrorDaemon = (TrafaxErrorDaemon) ServiceContext.getBean("trafaxErrorDaemon");
    		trafaxErrorDaemon.start();
    	}
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    }
	
}
