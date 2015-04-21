package com.lvmama.sso.processer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;

/**
 * 静默用户注册事件的处理器
 *
 * @author Brian
 *
 */
public class SilentRegisterProcesser extends DefaultRegisterProcesser {
    /**
     * Log类
     */
	private static final Log LOG = LogFactory
			.getLog(SilentRegisterProcesser.class);

	@Override
	protected boolean validate(final SSOMessage message) {
		if (null != message.getSubEvent()
				&& SSO_SUB_EVENT.SILENT.equals(message.getSubEvent())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("slient register!message.subEvent"
						+ message.getSubEvent());
			}
			LOG.info("用户注册成功，且来自于静默的注册!");
			return true;
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("it's not silent register,discard!message.subEvent"
						+ message.getSubEvent());
			}
			return false;
		}
	}

	@Override
	protected void sendSMS(final UserUser user, final SSOMessage message) {
		if (null == user || null == user.getMobileNumber()) {
			LOG.info("For empty mobile number, don't send sms!");
			return;
		}
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", user.getUserName());
			parameters.put("password", user.getRealPass());
			String cmsContent = comSmsTemplateRemoteService.getSmsContent(
					SMS_SSO_TEMPLATE.SMS_PHONE_USER_SILENT_REGIST_OK.toString(),
					parameters);
			smsRemoteService.sendSms(cmsContent, user.getMobileNumber());
			LOG.info("..silent...手机号是"+user.getMobileNumber()+"...发送短信内容为="+cmsContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
