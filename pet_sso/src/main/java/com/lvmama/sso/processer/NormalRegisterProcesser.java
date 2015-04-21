package com.lvmama.sso.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;

/**
 * 普通的用户注册事件的处理器
 * @author Brian
 *
 */
public class NormalRegisterProcesser extends DefaultRegisterProcesser {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(NormalRegisterProcesser.class);

	@Override
	protected boolean validate(final SSOMessage message) {
		if (null != message.getSubEvent() && SSO_SUB_EVENT.NORMAL.equals(message.getSubEvent())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("normal register successfully!message.subEvent" + message.getSubEvent());
			}
			LOG.info("normal register successfully!");
			return true;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't need response this reqeust for register from different channel, discard!!message.subEvent" + message.getSubEvent());
			}
			LOG.info("Don't need response this reqeust for register from different channel, discard");
			return false;
		}
	}
}
