package com.lvmama.back.job;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkOrderCouponRuleService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.mark.MarkOrderCouponRule;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;


/**
 * 购买后赠送优惠券的定时任务
 * @author Brian
 *
 */
public class OrderCouponJob {
	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory.getLog(OrderCouponJob.class);
	/**
	 * 赠送优惠券的次数
	 */
	private final static int PRESENT_COUPON_COUNT = 2;
	/**
	 * 订单远程调用服务
	 */
	private OrderService orderServiceProxy;
	/**
	 * 后台调用服务
	 */
	private MarkCouponService markCouponService;
	/**
	 * 短信服务
	 */
	private SmsService smsService;
	/**
	 * 订单优惠券的服务
	 */
	private MarkOrderCouponRuleService markOrderCouponRuleService;
	/**
	 * 短信模板服务
	 */
	private ComSmsTemplateService comSmsTemplateService;
	
	private UserUserProxy userUserProxy;
	
	public void run(){
		if (Constant.getInstance().isJobRunnable()) {
			LOG.info("开始执行定时任务......");
			List<MarkOrderCouponRule> rules = markOrderCouponRuleService.query(new HashMap<String, Object>());
			if (null != rules && !rules.isEmpty()) {
				List<OrdOrder> orders = getTodayVisitOrder();
				for (OrdOrder order : orders) {
					debug("订单：" + order.getOrderId() + " 进行判断是否需要进行优惠券发送");
					if (needPresentCoupon(order.getUserId())) {
						debug("订单：" + order.getOrderId() + " 用户是第一笔或者第二笔订单，可以发送优惠券");
						for (MarkOrderCouponRule rule : rules) {
							if (rule.getSubProductTypes().contains(order.getOrderType())
									&& rule.getMinAmount() <= order.getOughtPayFloat()
									&& rule.getMaxAmount() > order.getOughtPayFloat()) {
								debug("订单：" + order.getOrderId() + " 符合赠送优惠券规则条件" + rule.getMarkOrderCouponRuleId() + "，进行优惠券发送.");
								UserUser user = userUserProxy.getUserUserByUserNo(order.getUserId());
								markCouponService.bindingUserAndCouponCode(user, markCouponService.generateSingleMarkCouponCodeByCouponId(rule.getCouponId()).getCouponCode());
								
								Map<String, Object> parameters = new HashMap<String, Object>();
								parameters.put("amount", Math.round(Math.abs(rule.getCouponAmountYuan())));
								parameters.put("endDate", DateUtil.getFormatDate(rule.getCouponEndTime(),"yyyy-MM-dd"));
								debug("订单：" + order.getOrderId() + " 发送短信.");
								sendCouponMessage(order, parameters);
							}
						}
					} else {
						debug("订单：" + order.getOrderId() + " 无需发送优惠券");
					}
				}
			} else {
				debug("未能发现任何的针对订单的优惠券赠送规则");
			}
			LOG.info("定时任务执行完成!");
		}
	}
	
	/**
	 * 查询游玩日期为当天，已经支付且未取消的订单列表
	 * @return 订单列表
	 */
	private List<OrdOrder> getTodayVisitOrder() {
		Calendar cal = Calendar.getInstance();
		CompositeQuery compositeQuery = new CompositeQuery();
		
		OrderStatus orderStatusForQuery=new OrderStatus();
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());	
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatusForQuery);
		
		OrderContent orderContentForQuery = new OrderContent();
		orderContentForQuery.setChannel(Constant.CHANNEL.FRONTEND.name());
		compositeQuery.setContent(orderContentForQuery);
		
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		orderTimeRange.setOrdOrderVisitTimeStart(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
		compositeQuery.setOrderTimeRange(orderTimeRange);
		
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(Integer.MAX_VALUE);
		compositeQuery.setPageIndex(pageIndex);
		
		return orderServiceProxy.compositeQueryOrdOrder(compositeQuery);
	}
	
	/**
	 * 判断是否需要发送优惠券
	 * @param order 订单
	 * @return 是否需要发送优惠券
	 * 当用户是第一笔或者第二笔订单，才发送优惠券。
	 * 此处的第二笔订单，和产品经理确认后，是以游玩日期为标准
	 */
	private boolean needPresentCoupon(final String userId) {
		CompositeQuery compositeQuery = new CompositeQuery();
		OrderStatus orderStatusForQuery=new OrderStatus();
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());	
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatusForQuery);
		
		OrderContent orderContentForQuery = new OrderContent();
		orderContentForQuery.setUserId(userId);
		orderContentForQuery.setChannel(Constant.CHANNEL.FRONTEND.name());
		compositeQuery.setContent(orderContentForQuery);
		
		Calendar cal = Calendar.getInstance();
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
		compositeQuery.setOrderTimeRange(orderTimeRange);
		
		return orderServiceProxy.compositeQueryOrdOrderCount(compositeQuery) <= PRESENT_COUPON_COUNT;
	}
	
	/**
	 * 发送优惠券的信息
	 * @param order
	 */
	private void sendCouponMessage(final OrdOrder order, final Map<String,Object> parameters) {
		String mobile = null;
		if (StringUtils.isEmpty(mobile) && null != order.getUser()) {
			mobile = order.getUser().getMobileNumber();
		}
		if (StringUtils.isEmpty(mobile) && null != order.getContact()) {
			mobile = order.getContact().getMobile();
		}
		if (StringUtils.isEmpty(mobile)) {
			List<OrdPerson> persons = order.getTravellerList();
			for (OrdPerson person : persons) {
				if (null != person.getMobile()) {
					mobile = person.getMobile();
					break;
				}
			}
		}
		
		if (!StringUtils.isEmpty(mobile)) {
			ComSmsTemplate template = comSmsTemplateService.selectSmsTemplateByPrimaryKey(Constant.SMS_TEMPLATE.ORDER_PRESENT_COUPON.name());
			String content = template.getContent();
			try {
				content = StringUtil.composeMessage(template.getContent(), parameters);
			} catch (Exception e) {
				LOG.error(e.getMessage());
			}
			
			Calendar cal = Calendar.getInstance();
			
			ComSms cs = new ComSms();
			cs.setContent(content);
			cs.setMobile(mobile);
			cs.setCreateTime(cal.getTime());
			cal.set(Calendar.HOUR_OF_DAY, 12);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cs.setSendTime(cal.getTime());
			cs.setDescription("订单赠送优惠券");
			cs.setObjectId(order.getOrderId());
			cs.setStatus(Constant.SMS_STATUS.WAITSEND.name());
			cs.setTemplateId(Constant.SMS_TEMPLATE.ORDER_PRESENT_COUPON.name());
			cs.setMms("false");
			
	        smsService.insertComSms(cs);
		}
	}
	
	/**
	 * 打印调试信息
	 * @param message
	 */
	private void debug(final String message) {
		if (LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public void setMarkOrderCouponRuleService(
			final MarkOrderCouponRuleService markOrderCouponRuleService) {
		this.markOrderCouponRuleService = markOrderCouponRuleService;
	}

	public void setComSmsTemplateService(ComSmsTemplateService comSmsTemplateService) {
		this.comSmsTemplateService = comSmsTemplateService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	
}
