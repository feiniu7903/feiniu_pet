package com.lvmama.order.jobs;

import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderSum;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.MemberGradeUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.USER_MEMBER_GRADE;

/**
 * 用户等级变更的定时执行器
 * @author Brian
 */
public class UserMembertGradeJob {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(UserMembertGradeJob.class);
	/**
	 * 过去的12个月
	 */
	private static final int LASTTWELVEMONTHS = -12;
	/**
	 * 过去的12个月
	 */
	private static final int LASTELEVENMONTHS = -11;
	/**
	 * 同步锁
	 */
	private final ReentrantLock lock = new ReentrantLock();
	/**
	 * 订单远程调用服务
	 */
	private OrderService orderServiceProxy;
	private UserUserProxy userUserProxy;
	private ComSmsTemplateService comSmsTemplateService;
	private SmsRemoteService smsRemoteService;


	/**
	 * 升级用户等级。执行时间为每月1日
	 */
	public void updateUserMemberGrade() {
		if (Constant.getInstance().isJobRunnable()) {
			lock.lock();
			try {
				LOG.info("用户升级等级的定时任务开始执行......");
				List<OrdOrder> ordOrders = getLastestMonthOrders();
				debug("本月共有" + ordOrders.size() + "笔游玩订单");
				/* 用户名去重 */
				Set<String> userNos = new HashSet<String>(ordOrders.size());
				for (OrdOrder order : ordOrders) {
					userNos.add(order.getUserId());
				}
				debug("本月共有" + userNos.size() + "个用户需要进行升级校验");
				
				float amount = 0F;
				UserUser user = null;
				OrdOrderSum orderSum = null;
				USER_MEMBER_GRADE shouldGrade = null;

				for (String userNo : userNos) {
					user = userUserProxy.getUserUserByUserNo(userNo);
					orderSum = getLastestUserCost(userNo, LASTTWELVEMONTHS);
					amount = null == orderSum ? 0F : orderSum.getActualPayYan();
					shouldGrade = MemberGradeUtil.getUserMemberGrade(amount);

					if (MemberGradeUtil.compareGrade(
							MemberGradeUtil.getUserMemberGrade(user.getGrade()),
							shouldGrade) < 0) {
						debug("用户("
								+ user.getUserId()
								+ ")过去12个月一共消费了"
								+ amount + "元, 需要将其升级为"
								+ shouldGrade);
						userUserProxy.updateMemberGradeBySystem(user, shouldGrade, amount);
					}
				}
			} finally {
				LOG.info("用户升级等级的定时任务执行完成!");
				lock.unlock();
			}
		}
	}

	/**
	 * 用户等级降级提醒。执行时间为每年12月1日
	 */
	public void remindDegard() {
		if (Constant.getInstance().isJobRunnable()) {
			lock.lock();
			try {
				LOG.info("用户等级降级提醒的定时任务开始执行......");
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.MONTH, 12);
				calendar.add(Calendar.DAY_OF_MONTH, 31);
				List<UserUser> users = userUserProxy
						.getUsersByMemberGradeDateDue(calendar.getTime());

				float amount = 0F;
				OrdOrderSum orderSum = null;
				Map<String, Object> parameters = new HashMap<String, Object>();

				for (UserUser user : users) {
					parameters.clear();
					USER_MEMBER_GRADE currentGrade = MemberGradeUtil
							.getUserMemberGrade(user.getGrade());
					if (null != user.getMobileNumber()
							&& StringUtil.validMobileNumber(user
									.getMobileNumber())) {
						parameters.put("userName", user.getUserName());
						parameters.put("month", calendar.get(Calendar.MONTH) + 1);
						parameters.put("day", calendar.get(Calendar.DAY_OF_MONTH));
						parameters.put("oldGrade", currentGrade.getChGrade());
						parameters.put("newGrade", MemberGradeUtil
								.getPriorUserMemberGrade(currentGrade)
								.getChGrade());

						orderSum = getLastestUserCost(user.getUserId(), LASTELEVENMONTHS);
						amount = null == orderSum ? 0F : orderSum.getActualPayYan();
						parameters.put("amount", amount);

						smsRemoteService.sendSmsInWorking( comSmsTemplateService.getSmsContent("SMS_MEMBER_GRADE_DEGRADE_REMIND", parameters), user
								.getMobileNumber());
					}
				}
			} catch (Exception e) {
				LOG.error("用户等级降级提醒出错!" + e.getMessage());
			} finally {
				LOG.info("用户等级降级提醒的定时任务执行完成!");
				lock.unlock();
			}
		}
	}

	/**
	 * 用户降级。执行时间为每年1月1日
	 */
	public void degardUserMemberGrade() {
		if (Constant.getInstance().isJobRunnable()) {
			lock.lock();
			try {
				LOG.info("用户降级的定时任务开始执行......");
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.HOUR_OF_DAY, 0);
				calendar.add(Calendar.MINUTE, 0);
				calendar.add(Calendar.SECOND, 0);
				List<UserUser> users = userUserProxy
						.getUsersByMemberGradeDateDue(calendar.getTime());
				for (UserUser user : users) {
					debug("用户(" + user.getUserId() + ")进行降级处理");
					userUserProxy.updateMemberGradeBySystem(user, MemberGradeUtil
							.getPriorUserMemberGrade(MemberGradeUtil
									.getUserMemberGrade(user.getGrade())), 0F);
				}
				userUserProxy.expirationDateToNextYearForNormalGrade(calendar.getTime());
			} finally {
				LOG.info("用户降级的定时任务执行完成!");
				lock.unlock();
			}
		}
	}

	/**
	 * 获取最近一个月游玩的正常订单
	 * @return 订单列表
	 */
	private List<OrdOrder> getLastestMonthOrders() {
		CompositeQuery compositeQuery = new CompositeQuery();

		OrderStatus orderStatusForQuery = new OrderStatus();
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatusForQuery);

		OrderTimeRange orderTimeRange = new OrderTimeRange();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
		cal.add(Calendar.MONTH, -1);
		orderTimeRange.setOrdOrderVisitTimeStart(cal.getTime());
		compositeQuery.setOrderTimeRange(orderTimeRange);

		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(Integer.MAX_VALUE);
		compositeQuery.setPageIndex(pageIndex);

		return orderServiceProxy.lightedCompositeQueryOrdOrder(compositeQuery);
	}

	/**
	 * 获取用户最近几个月的消费额
	 * @param userId
	 *            用户标识
	 * @param beforeMonth
	 *            最近几个月
	 * @return 消费额
	 */
	private OrdOrderSum getLastestUserCost(final String userId,
			final int beforeMonth) {
		CompositeQuery compositeQuery = new CompositeQuery();

		OrderContent content = new OrderContent();
		content.setUserId(userId);
		compositeQuery.setContent(content);

		OrderStatus orderStatusForQuery = new OrderStatus();
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED
				.name());
		compositeQuery.setStatus(orderStatusForQuery);

		OrderTimeRange orderTimeRange = new OrderTimeRange();
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
		cal.add(Calendar.MONTH, beforeMonth);
		orderTimeRange.setOrdOrderVisitTimeStart(cal.getTime());
		compositeQuery.setOrderTimeRange(orderTimeRange);

		return orderServiceProxy.compositeQueryOrdOrderSum(compositeQuery);
	}

	/**
	 * 打印调试信息
	 * @param message
	 *            信息
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setComSmsTemplateService(ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}


}
