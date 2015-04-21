package com.test;



import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public  class KeepAliveMessageFactoryImpl implements KeepAliveMessageFactory {
	private static final String HEARTBEATRESPONSE = "<";
	private static final String HEARTBEATREQUEST = ">";
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.filter.keepalive.KeepAliveMessageFactory#getRequest
	 * (org.apache.mina.core.session.IoSession)
	 */
	@Override
	public Object getRequest(IoSession session) {
		System.out.println("返回预设语句" + HEARTBEATREQUEST);
		/** 返回预设语句 */
		return HEARTBEATREQUEST;
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
		System.out.println("得到返回");
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
		System.out.println("是否是心跳包: " + message);
		if(message.equals(HEARTBEATREQUEST))
			return true;
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
		System.out.println("是否是心跳包: " + message);
		if(message.equals(HEARTBEATRESPONSE))
			return true;
		return false;
	}
}
