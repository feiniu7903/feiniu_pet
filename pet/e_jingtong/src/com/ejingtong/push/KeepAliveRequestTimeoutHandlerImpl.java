package com.ejingtong.push;


import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.slf4j.Logger;

import com.ejingtong.uitls.MyThreadPoolExecutor;

import android.content.Context;

public class KeepAliveRequestTimeoutHandlerImpl implements
KeepAliveRequestTimeoutHandler {
	
	private Context mContext;
	public KeepAliveRequestTimeoutHandlerImpl(Context context){
		mContext = context;
	}

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
		session.close(false);
		System.out.println("心跳超时！");
		
	}
}
