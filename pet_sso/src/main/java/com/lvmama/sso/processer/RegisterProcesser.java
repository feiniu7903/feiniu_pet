package com.lvmama.sso.processer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProcesser;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;


/**
 * 注册事件的处理器
 *
 * @author Brian
 *
 */
public abstract class RegisterProcesser implements SSOMessageProcesser {
	/**
	 * Object类当作锁
	 */
	protected static final Object LOCK = new Object();
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory.getLog(RegisterProcesser.class);
	/**
	 * 用户服务
	 */
	protected UserUserProxy userUserProxy;
	/**
	 * SSO消息生产者
	 */
	protected SSOMessageProducer ssoMessageProducer;
	
	protected UserClient userClient;

	@Override
	public void process(final SSOMessage message) {
		if (null == message || !SSO_EVENT.REGISTER.equals(message.getEvent())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't response this request, discard!");
			}
			return;
		}
		if (null == message.getUserId()) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't include necessary info, discard!");
			}
			return;
		}
		LOG.info("receive message for register, please go on!");

		synchronized (LOCK) {
			onProcess(message);
		}
	}

	/**
	 * 消息处理
	 *
	 * @param message
	 *            message
	 */
	protected void onProcess(final SSOMessage message) {
		UserUser users = getUsers(message);
		if (validate(message) && null != users) {
			sendSMS(users, message);
			sendMail(users, message);
			synchBBS(users, message);
			if (null != users.getMemberShipCard()) {
				try {
					// 发送会员卡优惠发放的消息
					SSOMessage ssoMessage = new SSOMessage(
							SSO_EVENT.ACTIVATE_MEMBERSHIP_CARD,
							SSO_SUB_EVENT.NORMAL, users.getId());
					ssoMessage.putAttribute("membershipCard",
							users.getMemberShipCard());
					ssoMessageProducer.sendMsg(ssoMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else {
			if (LOG.isDebugEnabled()) {
				LOG.debug("error message or empty user!");
			}
		}
	}

	/**
	 * 获得用户参数MAP
	 *
	 * @param user
	 *            用户
	 * @return 参数MAP
	 */
	protected Map<String, Object> getUserParameters(final UserUser user) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		if (null != user) {
			parameters.put("username", user.getUserName());
			parameters.put("password", user.getRealPass());
			parameters.put("userId", user.getUserId());
		}
		return parameters;
	}

	/**
	 * 验证此消息是否被自身处理
	 *
	 * @param message
	 *            接收到的消息
	 * @return boolean
	 */
	protected abstract boolean validate(final SSOMessage message);

	/**
	 * 发送短信
	 *
	 * @param user
	 *            消息所携带的用户标识所指向的用户
	 * @param message
	 *            接收到的消息
	 */
	protected abstract void sendSMS(UserUser user, SSOMessage message);

	/**
	 * 发送邮件
	 *
	 * @param user
	 *            消息所携带的用户标识所指向的用户
	 * @param message
	 *            接收到的消息
	 */
	protected abstract void sendMail(UserUser user, SSOMessage message);
	
	/**
	 * 同步BBS的账户
	 *
	 * @param user
	 *            user
	 * @param message
	 *            message
	 */
	protected abstract void synchBBS(final UserUser user,
			final SSOMessage message);	

	/**
	 * 返回用户实例
	 *
	 * @param message
	 *            接收到的消息
	 * @return 用户
	 */
	protected UserUser getUsers(final SSOMessage message) {
		return this.userUserProxy.getUserUserByPk(message.getUserId());
	}

	// setter and getter


	public SSOMessageProducer getSsoMessageProducer() {
		return ssoMessageProducer;
	}

	public void setSsoMessageProducer(
			final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}
	
	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
}
