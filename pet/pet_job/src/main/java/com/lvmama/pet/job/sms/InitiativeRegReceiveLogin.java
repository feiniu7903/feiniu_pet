package com.lvmama.pet.job.sms;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.sms.SmsInstruction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.sms.SmsInstructionService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;



/**
 * 主动上行注册的处理器
 * 当短信内容为指定开头的字符(如：LV)，则代表手机需要主动注册为驴妈妈用户
 * @author Brian
 *
 */
public final class InitiativeRegReceiveLogin extends DefaultReceiveLogic {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(InitiativeRegReceiveLogin.class);
	/**
	 * 营销广告上行注册的开头指令
	 */
	private static final String PREFIX_TOKEN = "LV";
	/**
	 * 存放在Memcache中的上行注册用户的渠道集
	 */
	public static final String SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE = "SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE";
	/**
	 * 存放在Memcache中的时间
	 */
	private static final int SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE_EXPIRE = 4 * 60;
	/**
	 * nsso的远程调用接口
	 */
	@Autowired
	private UserUserProxy userUserProxy;
	
	@Autowired
	private UserClient userClient;
	/**
	 * backend的远程调用接口
	 */
	@Autowired
	private MarkCouponService markCouponService;
	/**
	 * 短信逻辑接口
	 */
	@Autowired
	private SmsRemoteService smsRemoteService;
	/**
	 * 上行指令的逻辑接口
	 */
	@Autowired
	private SmsInstructionService smsInstructionService;

	@Override
	public int execute(final String mobile, final String content) {
		if (StringUtils.isEmpty(content) || content.length() < 2) {
			return CONTINUE_MSSSAGE;
		}
		String prefix = content.substring(0, PREFIX_TOKEN.length());
		if (PREFIX_TOKEN.equalsIgnoreCase(prefix)) {
			debug("mobile:" + mobile + "send content (" + content + ") for register");

			String channel = content.substring(2);
			String couponCode = null;
			String couponId = null;
			List<SmsInstruction> channelSet = getSmsUplinkChannelSet();

			boolean channelFlag = true;
			if (null != channel && null != channelSet && !channelSet.isEmpty()) {
				for (SmsInstruction ins : channelSet) {
					if (ins.getInstructionCode().equalsIgnoreCase(channel)) {
						channelFlag = false;
						couponCode = ins.getCouponCode();
						couponId = ins.getCouponId();
						break;
					}
				}
			}

			if (channelFlag) {
				channel = null;
			}

			try {
				Long userId = userClient.batchRegisterWithChannel(mobile, null,
						null, null, null, null, null, null == channel ? null : channel.toUpperCase());

				if (null != userId) {
					LOG.info("mobile:" + mobile + "register successful,user id:" + userId);
				} else {
					LOG.info(mobile + "has a user，cann't register again");
					smsRemoteService.sendSmsWithType("驴妈妈旅游网提醒您：该手机号码已经注册过，您可使用该手机号码在驴妈妈网站上直接登录享受旅游优惠。详询：1010-6060。", mobile, "QUNFA");
					return CONTINUE_MSSSAGE;
				}
				
				String newCouponCode = "";  //生成的新的需要发送给用户的优惠券号码
				if (!StringUtils.isEmpty(couponId)) {
					StringTokenizer st = new StringTokenizer(couponId, ",");
					MarkCouponCode markCouponCode = null;
					while (st.hasMoreTokens()) {
						try {
							markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(Long.parseLong(st.nextToken()));
							UserUser user = userUserProxy.getUserUserByPk(userId);
							markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
						} catch (Exception e) {
							LOG.error("生成优惠券代码失败:" + e.getMessage());
						}
						if (StringUtils.isEmpty(newCouponCode)) {
							newCouponCode = markCouponCode.getCouponCode();
						}
					}
				}
				//发送福利短信
				if (!StringUtils.isEmpty(couponCode)) {
					Map<String, Object> parameters = new HashMap<String, Object>();
					parameters.put("couponCode", newCouponCode);
					smsRemoteService.sendSmsWithType(StringUtil.composeMessage(couponCode, parameters), mobile, "QUNFA");					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return CONTINUE_MSSSAGE;
	}

	/**
	 * 获取上行注册渠道的列表
	 * @return 指令集
	 */
	@SuppressWarnings("unchecked")
	private List<SmsInstruction> getSmsUplinkChannelSet() {
		Object cache = MemcachedUtil.getInstance().get(SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE);
		if (null == cache) {
			cache = smsInstructionService.queryAll();

			if (null != cache) {
				MemcachedUtil.getInstance().set(SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE,
						SMS_UPLINK_CHANNEL_SETS_IN_MEMCACHE_EXPIRE, cache);
			}
		}
		if (null != cache) {
			return (List<SmsInstruction>) cache;
		} else {
			return null;
		}
	}

	//setter and getter
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public void setSmsInstructionService(SmsInstructionService smsInstructionService) {
		this.smsInstructionService = smsInstructionService;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}	

}
