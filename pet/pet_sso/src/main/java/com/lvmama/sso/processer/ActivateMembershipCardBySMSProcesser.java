package com.lvmama.sso.processer;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProcesser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;

/**
 * 短信激活会员卡的处理器
 * @author Brian
 *
 */
public final class ActivateMembershipCardBySMSProcesser  implements SSOMessageProcesser {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(ActivateMembershipCardBySMSProcesser.class);
	/**
	 * 注册短信并激活会员卡的短信内容
	 */
	private static final String REGISTE_AND_ACTIVE_SMS_CONTENT =
			"欢迎您成为网站会员！您的初始账号为：${username}，初始密码为：${password}，手机号和会员卡号也可登录；特送上驴妈妈优惠券，请登陆www.lvmama.com至"
					+ "“我的优惠券”查看。客服咨询电话：1010-6060。";
	/**
	 * 重复绑定会员卡的提醒短信
	 */
	private static final String DEPLICATE_BINDING_SMS_CONTENT =
			"驴妈妈旅游网提醒您：您已经拥有驴妈妈会员卡，一个手机号只可绑定一张驴妈妈会员卡，不能进行重复绑定。客服咨询电话1010-6060。";
	/**
	 * 激活会员卡的提醒短信
	 */
	private static final String ACTIVE_SMS_CONTENT =
			"恭喜您成功激活驴妈妈会员卡，会员卡号也可登录，特送上驴妈妈优惠券，请登陆www.lvmama.com至“我的优惠券”查看。客服咨询电话：1010-6060。";
	/**
	 * 无效的会员卡信息的提醒短信
	 */
	private static final String INVALID_MEMBERSHIPCODE_CONTENT = "驴妈妈旅游网提醒您：该会员卡号不存在或已被激活，请更换会员卡号。客服咨询电话1010-6060。";

    /**
     *  后台远程服务接口
     */
	private MarkCouponService markCouponService;

	/**
	 * 用户服务代理类
	 */
	private UserUserProxy userUserProxy;


	/**
	 * 远程SMS服务
	 */
	private SmsRemoteService smsRemoteService;

	@Override
	public void process(final SSOMessage message) {
		if (null == message
				|| !(SSO_EVENT.ACTIVATE_MEMBERSHIP_CARD.equals(message.getEvent())
						&& SSO_SUB_EVENT.SMS.equals(message.getSubEvent()))) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't need response this reqeust, discard!");
			}
			return;
		}
		if (null == message.getAttribute("mobile") || null == message.getAttribute("membershipCard")) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Don't include mobile number or membership card code,discard!");
			}
			return;
		}
		LOG.info("receive message for active member ship card!");

		String membershipCardCode = (String) message.getAttribute("membershipCard");
		String mobile = (String) message.getAttribute("mobile");

		if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MEMBERSHIPCARD, membershipCardCode)) {
			if (StringUtils.isEmpty(mobile)) {
				LOG.info("Joke? mobile numnber is empty,discard!");
			} else {
				UserUser users = userUserProxy.getUsersByMobOrNameOrEmailOrCard(mobile);
				if (null == users) {
					users = UserUserUtil.genDefaultUser();
					users.setIsMobileChecked("Y");
					users.setMemberShipCard(membershipCardCode);
					users.setActiveMscardDate(new java.util.Date());
					users.setChannel("011047");  //指定CHANNEL
					users = userUserProxy.register(users);

					Map<String, Object> params = new HashMap<String, Object>();
					params.put("username", users.getUserName());
					params.put("password", users.getRealPass());
					try {
						sendSMS(StringUtil
								.composeMessage(REGISTE_AND_ACTIVE_SMS_CONTENT,
										params), mobile);
					} catch (Exception e) {
						LOG.error("send sms failed!");
					}
				} else {
					if (null != users.getMemberShipCard()) {
						sendSMS(DEPLICATE_BINDING_SMS_CONTENT, mobile);
					} else {
						users.setMemberShipCard(membershipCardCode);
						users.setActiveMscardDate(new java.util.Date());
						userUserProxy.update(users);

						markCouponService.bindingUserAndCouponCode(users, membershipCardCode);
						sendSMS(ACTIVE_SMS_CONTENT, mobile);
					}
				}
			}
		} else {
			sendSMS(INVALID_MEMBERSHIPCODE_CONTENT, mobile);
		}
	}

	/**
	 * 发送短信
	 * @param content 内容
	 * @param mobile 手机号
	 */
	private void sendSMS(final String content, final String mobile) {
		try {
			smsRemoteService.sendQunFaSms(content, mobile);
		} catch (Exception e) {
			LOG.error("send sms failed!");
		}
	}
	
	

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

}
