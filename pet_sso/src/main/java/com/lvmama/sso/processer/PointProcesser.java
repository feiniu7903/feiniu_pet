package com.lvmama.sso.processer;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProcesser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 积分处理的消息消费者
 * 此类将处理用户所有和积分相关的动作，并统一处理。
 * @author Brian
 *
 */
public class PointProcesser implements SSOMessageProcesser {
	/**
	 * 日志记录器
	 */
	private static final Log LOG = LogFactory.getLog(PointProcesser.class);
	/**
	 * 用户积分规则标识
	 */
	private static enum USER_POINT_RULES_ID {
		/**
		 * 网页注册
		 */
		POINT_FOR_NORMAL_REGISTER,
		/**
		 * 登录
		 */
		POINT_FOR_LOGIN,
		/**
		 * 邮箱验证
		 */
		POINT_FOR_EMAIL_AUTHENTICATION,
		/**
		 * 手机验证
		 */
		POINT_FOR_MOBILE_AUTHENTICATION,
		/**
		 * 发表体验点评
		 */
		POINT_FOR_EXPERIENCE_COMMENT,
		/**
		 * 发表普通点评
		 */
		POINT_FOR_COMMON_COMMENT,
		/**
		 * 点评加精
		 */
		POINT_FOR_ESSENTIAL_COMMENT,
		/**
		 * 点评手工加积分
		 */
		POINT_FOR_CUSTOMIZED_COMMENT,
		/**
		 * 点评招募
		 */
		POINT_FOR_RECRUIT_COMMENT,
		/**
		 * 发表攻略
		 */
		POINT_FOR_COMMON_GUIDE,
		/**
		 * 攻略手工加积分
		 */
		POINT_FOR_CUSTOMIZED_GUIDE,
		/**
		 * 论坛重点板块发帖
		 */
		POINT_FOR_PUBLISH_EMPHASIS_POST,
		/**
		 * 论坛非重点板块发帖
		 */
		POINT_FOR_PUBLISH_NON_EMPHASIS_POST,
		/**
		 * 重点板块回帖
		 */
		POINT_FOR_REPLY_EMPHASIS_POST,
		/**
		 * 论坛帖子一级加精
		 */
		POINT_FOR_FIRST_ESSENTIAL_POST,
		/**
		 * 论坛帖子二级加精
		 */
		POINT_FOR_SECOND_ESSENTIAL_POST,
		/**
		 * 论坛帖子三级加精
		 */
		POINT_FOR_THIRD_ESSENTIAL_POST,
		/**
		 * 论坛帖子手工加积分
		 */
		POINT_FOR_CUSTOMIZED_POST,
		/**
		 * 删帖
		 */
		POINT_FOR_DELETE_POST,
		/**
		 * 下单游玩结束
		 */
		POINT_FOR_ORDER_VISITED,
		/**
		 * 线上活动
		 */
		POINT_FOR_ONLINE_ACTIVITY,
		/**
		 * 写行程送积分
		 */
		POINT_FOR_TRIP_SYS_POINTS_SEND,
		/**
		 * 分享行程送积分
		 */
		POINT_FOR_TRIP_SHARE_POINTS_SEND
	};

	/**
	 * 用户操作接口
	 */
	private UserUserProxy userUserProxy;

	@Override
	public void process(final SSOMessage message) {
		if (null == message.getUserId()) {
			debug("user identification is empty");
			return;
		}

		if (Constant.SSO_EVENT.REGISTER.equals(message.getEvent())
				&& !Constant.SSO_SUB_EVENT.CONFIRM_REGISTER.equals(message.getSubEvent())) {
			addPointForRegister(message);
			return;
		}

		if (Constant.SSO_EVENT.AUTHENTICATE_MAIL.equals(message.getEvent())) {
			addPointForAuthenticateMail(message);
			return;
		}

		if (Constant.SSO_EVENT.AUTHENTICATE_MOBILE .equals(message.getEvent())) {
			addPointForAuthenticateMobile(message);
			return;
		}

		if (Constant.SSO_EVENT.LOGIN.equals(message.getEvent())) {
			addPointForLogin(message);
			return;
		}

		if (Constant.SSO_EVENT.COMMENT.equals(message.getEvent())) {
			addPointForComment(message);
			return;
		}

		if (Constant.SSO_EVENT.GUIDE.equals(message.getEvent())) {
			addPointForGuide(message);
			return;
		}
		
		if (Constant.SSO_EVENT.TRIP.equals(message.getEvent())) {
			addPointForTrip(message);
			return;
		}

		if (Constant.SSO_EVENT.POST.equals(message.getEvent())) {
			addPointForPost(message);
		}

		if (Constant.SSO_EVENT.ORDER.equals(message.getEvent())) {
			addPointForOrder(message);
			return;
		}
		
		if (Constant.SSO_EVENT.ONLINE_ACTIVITY.equals(message.getEvent())) {
			addPointForOnlineActivity(message);
			return;
		}
	}

	/**
	 * 用户线上活动添加积分.
	 * @param message 消息
	 */
	private void addPointForOnlineActivity(final SSOMessage message) {

		String memo = (String)message.getAttribute("memo");
		debug("user(" + message.getUserId() + ") add point for online activity("+ memo +")");
		UserUser users = userUserProxy.getUserUserByPk(message.getUserId());
		if(users != null){
			userUserProxy.addUserPoint(message.getUserId(), USER_POINT_RULES_ID.POINT_FOR_ONLINE_ACTIVITY.name(), 
					(Long)message.getAttribute("point"), memo);
		}
	}
	
	
	/**
	 * 用户注册添加积分
	 * @param message 消息
	 */
	private void addPointForRegister(final SSOMessage message) {
		debug("user(" + message.getUserId() + ") add point for register");
		userUserProxy.addUserPoint(message.getUserId(), USER_POINT_RULES_ID.POINT_FOR_NORMAL_REGISTER.name(), null, null);

		UserUser users = userUserProxy.getUserUserByPk(message.getUserId());
		//用户在注册时进行了邮箱验证
		if (null != users && "Y".equals(users.getIsEmailChecked())) {
			addPointForAuthenticateMail(message);
		}
		//用户在注册时进行了手机验证
		if (null != users && "Y".equals(users.getIsMobileChecked())) {
			addPointForAuthenticateMobile(message);
		}
	}

	/**
	 * 用户验证邮箱添加积分
	 * @param message 消息
	 */
	private void addPointForAuthenticateMail(final SSOMessage message) {
		debug("user(" + message.getUserId() + ") add point for validation mail");
		userUserProxy.addUserPoint(message.getUserId(),
				USER_POINT_RULES_ID.POINT_FOR_EMAIL_AUTHENTICATION.name(), null, null);
	}

	/**
	 * 用户验证手机添加积分
	 * @param message 消息
	 */
	private void addPointForAuthenticateMobile(final SSOMessage message) {
		debug("user(" + message.getUserId() + ") add point for validation mobile");
		userUserProxy.addUserPoint(message.getUserId(),
				USER_POINT_RULES_ID.POINT_FOR_MOBILE_AUTHENTICATION.name(), null, null);
	}

	/**
	 * 用户登录添加积分
	 * @param message 消息
	 */
	private void addPointForLogin(final SSOMessage message) {
		debug("user(" + message.getUserId() + ") add point for login");
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("userId", message.getUserId());
		param.put("ruleId", USER_POINT_RULES_ID.POINT_FOR_LOGIN.name());
		param.put("today", true);
		if (userUserProxy.getCountUserPointLog(param) <= 0) {
			userUserProxy.addUserPoint(message.getUserId(), USER_POINT_RULES_ID.POINT_FOR_LOGIN.name(), null, null);
		} else {
			debug("user(" + message.getUserId() + ") had get point today");
		}
	}

	/**
	 * 用户点评添加积分
	 * @param message 消息
	 */
	private void addPointForComment(final SSOMessage message) {
		if (Constant.SSO_SUB_EVENT.CUSTOMIZED.equals(message.getSubEvent())) {
			//手工给点评加积分
			debug("user(" + message.getUserId() + ") add point for comment by manual");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_CUSTOMIZED_COMMENT.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.ESSENTIAL_COMMENT.equals(message.getSubEvent())) {
			//点评加精
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_ESSENTIAL_COMMENT.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.EXPERIENCE_COMMENT.equals(message.getSubEvent())) {
			//体验点评
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_EXPERIENCE_COMMENT.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.RECRUIT_COMMENT.equals(message.getSubEvent())) {
			//点评招募
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_RECRUIT_COMMENT.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		//普通点评加积分
		userUserProxy.addUserPoint(message.getUserId(),
				USER_POINT_RULES_ID.POINT_FOR_COMMON_COMMENT.name(),
				null, (String) message.getAttribute("memo"));
	}

	/**
	 * 攻略添加积分
	 * @param message 消息
	 */
	private void addPointForGuide(final SSOMessage message) {
		if (Constant.SSO_SUB_EVENT.CUSTOMIZED.equals(message.getSubEvent())) {
			//手工给攻略加积分
			debug("user(" + message.getUserId() + ") add point for guide by manual");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_CUSTOMIZED_GUIDE.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
		} else {
			//发表攻略加积分
			debug("user(" + message.getUserId() + ") add point for publish guide");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_COMMON_GUIDE.name(),
					null, (String) message.getAttribute("memo"));
		}
	}
	
	
	/**
	 * 行程添加积分
	 * @param message 消息
	 */
	private void addPointForTrip(final SSOMessage message) {
		if (Constant.SSO_SUB_EVENT.TRIP_SYS_POINTS_SEND.equals(message.getSubEvent())) {
			//手工给行程加积分
			debug("user(" + message.getUserId() + ") add point for trip by manual");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_TRIP_SYS_POINTS_SEND.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
		}else if (Constant.SSO_SUB_EVENT.TRIP_SHARE_POINTS_SEND.equals(message.getSubEvent())) {
			//手工给行程加积分
			debug("user(" + message.getUserId() + ") add point for trip by manual");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_TRIP_SHARE_POINTS_SEND.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
		}
	}

	/**
	 * 帖子添加积分
	 * @param message 消息
	 */
	private void addPointForPost(final SSOMessage message) {
		if (Constant.SSO_SUB_EVENT.EMPHASIS_POST.equals(message.getSubEvent())) {
			//发表攻略加积分
			debug("user(" + message.getUserId() + ") add point for publishing master post.");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_PUBLISH_EMPHASIS_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.NON_EMPHASIS_POST.equals(message.getSubEvent())) {
			//发表主贴加积分
			debug("user(" + message.getUserId() + ") add point for publishing slave post");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_PUBLISH_NON_EMPHASIS_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.REPLY_EMPHASIS_POST.equals(message.getSubEvent())) {
			//发表贴子加积分
			debug("user(" + message.getUserId() + ") add point for reply post");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_REPLY_EMPHASIS_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.FIRST_ESSENTIAL_POST.equals(message.getSubEvent())) {
			//贴子一级加精
			debug("user(" + message.getUserId() + ") add point for first essential post");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_FIRST_ESSENTIAL_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.SECOND_ESSENTIAL_POST.equals(message.getSubEvent())) {
			//贴子二级加精
			debug("user(" + message.getUserId() + ") add point for second essential post");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_SECOND_ESSENTIAL_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.THIRD_ESSENTIAL_POST.equals(message.getSubEvent())) {
			//贴子三级加精
			debug("user(" + message.getUserId() + ") add point for third essential post");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_THIRD_ESSENTIAL_POST.name(),
					null, (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.CUSTOMIZED.equals(message.getSubEvent())) {
			//攻略手工加积分
			debug("user(" + message.getUserId() + ") add point for post by manual");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_CUSTOMIZED_POST.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
			return;
		}
		if (Constant.SSO_SUB_EVENT.DELETE_POST.equals(message.getSubEvent())) {
			//删除帖子
			debug("user(" + message.getUserId() + ") add point for deleting post");
			String objectId = (String) message.getAttribute("momo");

			if (null != objectId) {
				Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("userId", message.getUserId());
				parameters.put("memo", objectId);
				Long point = userUserProxy.getSumUserPoint(parameters);
				if (null != point) {
					userUserProxy.addUserPoint(message.getUserId(),
							USER_POINT_RULES_ID.POINT_FOR_DELETE_POST.name(),
							0 - point.longValue(), (String) message.getAttribute("memo"));
					return;
				} else {
					return;
				}
			}
		}
	}

	/**
	 * 订单添加积分
	 * @param message 消息
	 */
	private void addPointForOrder(final SSOMessage message) {
		
		if (Constant.SSO_SUB_EVENT.VISITED.equals(message.getSubEvent())) {
			debug("user(" + message.getUserId() + ") add point for finished order ");
			userUserProxy.addUserPoint(message.getUserId(),
					USER_POINT_RULES_ID.POINT_FOR_ORDER_VISITED.name(),
					(Long) message.getAttribute("point"), (String) message.getAttribute("memo"));
			return;
		}
	}
	
	/**
	 * 打印调式信息
	 * @param message 调式信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}


}
