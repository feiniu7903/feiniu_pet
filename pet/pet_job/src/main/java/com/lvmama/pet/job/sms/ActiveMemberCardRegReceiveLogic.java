package com.lvmama.pet.job.sms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.vo.Constant;

/**
 * 短信激活会员卡
 * 用户输入LVJH+会员卡号，能够注册+激活会员卡
 * @author Brian
 *
 */
public class ActiveMemberCardRegReceiveLogic extends DefaultReceiveLogic {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ActiveMemberCardRegReceiveLogic.class);
	/**
	 * 营销广告上行激活的开头指令
	 */
	private static final String PREFIX_TOKEN = "JH";
	/**
	 * SSO的JMS生产者
	 */
	private SSOMessageProducer ssoMessageProducer;

	@Override
	public int execute(final String mobile, final String content) {
		if (StringUtils.isEmpty(content) || !content.toUpperCase().startsWith(PREFIX_TOKEN)) {
			LOG.debug("it isn't active member card sms,ignor!");
			return CONTINUE_MSSSAGE;
		}
		String memberCardCode = content.toUpperCase().substring(PREFIX_TOKEN.length());
		if (!StringUtils.isEmpty(memberCardCode)) {
			SSOMessage message = new SSOMessage(Constant.SSO_EVENT.ACTIVATE_MEMBERSHIP_CARD);
			message.setSubEvent(Constant.SSO_SUB_EVENT.SMS);
			message.setUserId(1L); //无效的用户号，只为了保持消息的完整性
			message.putAttribute("mobile", mobile);
			message.putAttribute("membershipCard", memberCardCode);
			ssoMessageProducer.sendMsg(message);
		} else {
			LOG.debug("no membership card code");
		}

		return CONTINUE_MSSSAGE;
	}

	public void setSsoMessageProducer(final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}
}
