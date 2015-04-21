package com.lvmama.push.util;

import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;

public class KeepAliveFilterBuilder extends KeepAliveFilter {

	public KeepAliveFilterBuilder(KeepAliveMessageFactory messageFactory,
			KeepAliveRequestTimeoutHandler policy) {
		super(messageFactory, IdleStatus.BOTH_IDLE, policy);
		// TODO Auto-generated constructor stub
	}

	
	
}
