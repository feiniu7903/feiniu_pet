package com.lvmama.pet.job.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;

public class PinAnActivityLogic extends DefaultReceiveLogic {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(PinAnActivityLogic.class);
	private static Long[] COUPON_IDS = new Long[]{2857l,2858l,2859l,2860l };
	@Autowired
	private UserClient userClient;
	/**
	 * backend的远程调用接口
	 */
	@Autowired
	private MarkCouponService markCouponService;
	/**
	 * nsso的远程调用接口
	 */
	@Autowired
	private UserUserProxy userUserProxy;
	/**
	 * 短信逻辑接口
	 */
	@Autowired
	private SmsRemoteService smsRemoteService;	
	
	@Override
	public int execute(final String mobile, final String content) {
		if (StringUtils.isEmpty(content) || content.length() < 2) {
			return CONTINUE_MSSSAGE;
		}
		Long userId = null;
		try {
			if ("LVPACX".equalsIgnoreCase(content)) {
				if (!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)) {
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("mobileNumber", mobile);
					List<UserUser> users = userUserProxy.getUsers(param);
					if (users != null && !users.isEmpty()) {
						userId = users.get(0).getId();
					}
				} else {
					userId = userClient.batchRegisterWithChannel(mobile, null, null, null, null, null, null,"PACX");
				}
				
				if (null != userId) {
					for (Long id : COUPON_IDS) {
						MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(id);
						UserUser user = userUserProxy.getUserUserByPk(userId);
						markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
					}
					smsRemoteService.sendSmsWithType("恭喜您获得我们送给您的指定产品850元现金券，请登录www.lvmama.com登录获取.", mobile, "QUNFA");
				}
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return CONTINUE_MSSSAGE;
	}

	public UserClient getUserClient() {
		return userClient;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public SmsRemoteService getSmsRemoteService() {
		return smsRemoteService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}
	
	
}
