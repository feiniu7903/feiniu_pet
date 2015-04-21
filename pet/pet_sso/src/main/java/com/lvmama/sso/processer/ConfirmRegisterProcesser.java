package com.lvmama.sso.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProcesser;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
/**
 * 对于二次确认注册用户的调用
 * @author Brian
 */
public class ConfirmRegisterProcesser implements SSOMessageProcesser {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(ConfirmRegisterProcesser.class);

	@Override
	public void process(final SSOMessage message) {
		if (null != message && SSO_EVENT.REGISTER.equals(message.getEvent())
				&& SSO_SUB_EVENT.CONFIRM_REGISTER.equals(message.getSubEvent())) {
			LOG.debug("second register confirm");
			//backendManagerRemoteService.secondConfirmRegister((String) message.getAttribute("mobile"));
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't need response this reqeust, discard!");
				return;
			}
		}
	}
}
