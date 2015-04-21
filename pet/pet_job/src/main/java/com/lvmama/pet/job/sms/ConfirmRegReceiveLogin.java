package com.lvmama.pet.job.sms;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.vo.Constant;

/**
 * 当用户回复Y时，代表用户是二次注册用户。
 * 二次注册用户的含义是：兼职人员录入用户信息，系统发送是否需要注册成驴妈妈会员的短信，回复Y即表示同意。
 * @author Brian
 *
 */
public final class ConfirmRegReceiveLogin extends DefaultReceiveLogic {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ConfirmRegReceiveLogin.class);
	/**
	 * 指定的短信回复内容
	 */
	private static final String SMS_CONTENT = "Y";
	/**
	 * SSO的JMS生产者
	 */
	private SSOMessageProducer ssoMessageProducer;

	@Override
	public int execute(final String mobile, final String content) {
		if (StringUtils.isNotEmpty(content) && SMS_CONTENT.equalsIgnoreCase(content)) {
			LOG.debug("user second confirm, execute!");
			SSOMessage message = new SSOMessage(Constant.SSO_EVENT.REGISTER);
			message.setSubEvent(Constant.SSO_SUB_EVENT.CONFIRM_REGISTER);
			message.setUserId(1L); //无效的用户号，只为了保持消息的完整性
			message.putAttribute("mobile", mobile);
			ssoMessageProducer.sendMsg(message);
		}
		return CONTINUE_MSSSAGE;
	}

	//setter and getter
	public void setSsoMessageProducer(final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}
}
