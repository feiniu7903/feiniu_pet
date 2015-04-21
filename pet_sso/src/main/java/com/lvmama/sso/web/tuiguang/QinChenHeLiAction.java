package com.lvmama.sso.web.tuiguang;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.web.BaseLoginAction;

/**
 * 挚诚合力终端机的接口应用
 * 实现用户在挚诚终端机上输入手机号码，便可获取驴妈妈优惠券
 * @author Brian
 *
 */
public final class QinChenHeLiAction extends BaseLoginAction {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 8302588238419016716L;
	/**
	 * 指定的优惠券标识号
	 */
	private static final Long COUPON_ID = 1486L;
	/**
	 * 远程SMS服务
	 */
	private SmsRemoteService smsRemoteService;
	/**
	 * 用户服务类
	 */
	private UserUserProxy userUserProxy;
	/**
	 * SSO系统的消息生成者
	 */
	private SSOMessageProducer ssoMessageProducer;
	/**
	 * 后台管理远程服务
	 */
	private MarkCouponService markCouponService;

	/**
	 * 手机号
	 */
	private String mobile;

	/**
	 * 实现用户在挚诚终端机上输入手机号码，便可获取驴妈妈优惠券
	 * @throws Exception IO错误
	 */
	@Action("/tuiguang/zhichen")
	public void duijie() throws Exception {
		if (StringUtils.isEmpty(mobile) || !StringUtil.validMobileNumber(mobile)) {
			getResponse().getWriter().print("false");
			return;
		}
		if (userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)) {
			UserUser user = UserUserUtil.genDefaultUserByMobile(mobile);
			user.setChannel("011084");
			user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
			user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
			user = userUserProxy.register(user);
			ssoMessageProducer.sendMsg(
					new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId()));
		}
		MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(COUPON_ID);
		if (markCouponCode !=null && StringUtils.isNotEmpty(markCouponCode.getCouponCode())) {
			smsRemoteService.sendSmsWithType("恭喜您获得驴妈妈旅游网价值150元出境游优惠券一张，优惠券代码："
					+ markCouponCode.getCouponCode() + "，有效期2012年4月1日至6月30日。客服咨询电话：1010-6060。", mobile, "QUNFA");
		}
		getResponse().getWriter().print("true");
	}

	public void setSmsRemoteService(final SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public SSOMessageProducer getSsoMessageProducer() {
		return ssoMessageProducer;
	}

	public void setSsoMessageProducer(final SSOMessageProducer ssoMessageProducer) {
		this.ssoMessageProducer = ssoMessageProducer;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}
}
