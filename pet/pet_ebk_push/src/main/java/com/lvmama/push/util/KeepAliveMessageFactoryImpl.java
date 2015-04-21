package com.lvmama.push.util;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {
	private static final String HEARTBEATRESPONSE = "<";
	private static final String HEARTBEATREQUEST = ">";
	 private static final Logger LOGGER = LoggerFactory.getLogger(KeepAliveMessageFactory.class);
	/*
	* (non-Javadoc)
	* 
	* @see
	* org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
	* (org.apache.mina.core.session.IoSession)
	*/
	 @Override
	 public Object getRequest(IoSession session) {
		 LOGGER.info("服务端 发送心跳包：" + HEARTBEATREQUEST +" remote address:"+session.getRemoteAddress());
		 return HEARTBEATREQUEST;
		 /**
		  * 设置心跳被动型，服务端不主动发心跳数据给客户端。
		  * 避免由于客户端读写繁忙，导致服务端的心跳超时而强制关闭session的情况。
		  */
		 // return null;
	 }

	/*
	* (non-Javadoc)
	* 
	* @see
	* org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getResponse
	* (org.apache.mina.core.session.IoSession, java.lang.Object)
	*/
	 @Override
	 public Object getResponse(IoSession session, Object request) {
		 
		 //LOGGER.info("发送客户端心跳 响应：" + HEARTBEATRESPONSE +" remote address:"+session.getRemoteAddress());
		 /** 返回预设语句 */
		 return null;
	 }

	/*
	* (non-Javadoc)
	* 
	* @see
	* org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isRequest
	* (org.apache.mina.core.session.IoSession, java.lang.Object)
	*/
	@Override
	public boolean isRequest(IoSession session, Object message) {
	if(message.equals(HEARTBEATREQUEST)) {
		LOGGER.info("心跳请求包：" + message+" remote address:"+session.getRemoteAddress());
		return true;
	}
	return false;
	}
	
	/*
	* (non-Javadoc)
	* 
	* @see
	* org.apache.mina.filter.keepalive.KeepAliveMessageFactory#isResponse
	* (org.apache.mina.core.session.IoSession, java.lang.Object)
	*/
	@Override
	public boolean isResponse(IoSession session, Object message) {
		if(message.equals(HEARTBEATRESPONSE)) {
			LOGGER.info("心跳响应包：" + message+" remote address:"+session.getRemoteAddress());
			return true;
		}
		return false;
	}

}
