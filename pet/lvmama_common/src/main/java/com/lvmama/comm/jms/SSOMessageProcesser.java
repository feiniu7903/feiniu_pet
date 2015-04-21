package com.lvmama.comm.jms;


/**
 * 消息的处理器
 * @author Brian
 *
 */
public interface SSOMessageProcesser {
	void process(final SSOMessage message);
}
