package com.test;



import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;

public class KeepAliveRequestTimeoutHandlerImpl implements
KeepAliveRequestTimeoutHandler {

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
		((Logger) LOG).info("心跳超时！");
	}
	}
