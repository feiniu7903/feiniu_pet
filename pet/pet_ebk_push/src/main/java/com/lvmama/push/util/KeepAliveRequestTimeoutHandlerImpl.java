package com.lvmama.push.util;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lvmama.push.model.ClientSessionInfo;
import com.lvmama.push.model.SessionManager;

public class KeepAliveRequestTimeoutHandlerImpl implements KeepAliveRequestTimeoutHandler {
	 private static final Logger LOGGER = LoggerFactory.getLogger(KeepAliveRequestTimeoutHandlerImpl.class);
	/*
	* (non-Javadoc)
	* 
	* @seeorg.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler#
	* keepAliveRequestTimedOut
	* (org.apache.mina.filter.keepalive.KeepAliveFilter,
	* org.apache.mina.core.session.IoSession)
	*/
	@Override
	public void keepAliveRequestTimedOut(KeepAliveFilter filter,
		IoSession session) throws Exception {
		String udid = (String)session.getAttribute("udid");
    	//String userId = (String)session.getAttribute("userId");
    	ClientSessionInfo csi = SessionManager.getInstance().getSessions().get(udid);
    	if(csi!=null){
    		csi.setState(ConstantPush.CLIENT_SESSION_STATUS.OFFLINE.name());
    	}
		session.close(false);
		LOGGER.info("keeAlive message time out");
	}
	}
