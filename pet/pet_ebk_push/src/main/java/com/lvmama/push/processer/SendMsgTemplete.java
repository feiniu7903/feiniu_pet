package com.lvmama.push.processer;

import org.apache.mina.core.session.IoSession;

public interface SendMsgTemplete {
	void SendMsg(String msg,IoSession session);
}
