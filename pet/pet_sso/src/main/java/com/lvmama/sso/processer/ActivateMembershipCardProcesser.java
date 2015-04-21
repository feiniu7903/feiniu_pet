package com.lvmama.sso.processer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProcesser;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
/**
 * 激活会员卡
 * @author Brian
 *
 */
public final class ActivateMembershipCardProcesser implements SSOMessageProcesser {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ActivateMembershipCardProcesser.class);
	
	private UserClient userClient;

	@Override
	public void process(final SSOMessage message) {
		if (null == message
				|| !(SSO_EVENT.ACTIVATE_MEMBERSHIP_CARD.equals(message.getEvent())
						&& SSO_SUB_EVENT.NORMAL.equals(message.getSubEvent()))) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't need response this reqeust, discard!");
			}
			return;
		}
		if (null == message.getUserId() || null == message.getAttribute("membershipCard")) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Withour nessary info in message, discard!");
			}
			return;
		}
		LOG.info("recieve message for active membership card by manual");

		userClient.bindingUserAndMembershipCardCode(
				message.getUserId(),
				(String) message.getAttribute("membershipCard"));
	}
	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}
	
}
