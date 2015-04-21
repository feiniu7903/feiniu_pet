package com.lvmama.back.job;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.ActivityUtil;
import com.lvmama.comm.utils.MemberGradeUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 购买后赠送积分的定时任务
 * @author yuzhizeng
 *
 */
public class OrderPointJob {
	/**
	 * 日志输出器
	 */
	private final static Log LOG = LogFactory.getLog(OrderPointJob.class);
	/**
	 * 订单远程调用服务
	 */
	private OrderService orderServiceProxy;
	/**
	 * SSO远程调用服务
	 */
	private UserUserProxy userUserProxy;
	
	public void run() throws Exception {
		
		if (Constant.getInstance().isJobRunnable()) {		
			LOG.info("开始执行定时任务......");			
			float coefficient = 0f;
			String orderChannel = null;
			UserUser user = null; 
			
			List<OrdOrder> orders = getTodayVisitOrder();
			for (OrdOrder order : orders) {
					debug("订单：" + order.getOrderId() + " 进行积分赠送.");
					
					orderChannel = order.getChannel();
				    user = userUserProxy.getUserUserByUserNo(order.getUserId());
					if(null != user.getGrade() ){
						coefficient = MemberGradeUtil.getOrderCoefficient(user.getGrade(), orderChannel);
					}
					
					if(coefficient == 0f){
						debug("订单(" + order.getOrderId() + ")获取的积分系数为0，忽略！");
					}else{
						long point = Math.round(Math.round(order.getActualPayYuan()) * coefficient);
						if(ActivityUtil.getInstance().checkActivityIsValid(Constant.ACTIVITY_FIVE_YEAR, order.getCreateTime())){
							point = 2* point; //5周年固定双倍积分
						}
						debug("订单(" + order.getOrderId() + ") 赠送积分:" + point);
						userUserProxy.addUserPoint(user.getId(),
								"POINT_FOR_ORDER_VISITED",
								point, "订单(" + order.getOrderId()+ ")获取积分");
					}
			}
			LOG.info("定时任务执行完成!");
		}
	}
	
	/**
	 * 查询游玩日期为当天，已经支付且未取消的订单列表(所有渠道)
	 * @return 订单列表
	 */
	private List<OrdOrder> getTodayVisitOrder() {
		Calendar cal = Calendar.getInstance();
		CompositeQuery compositeQuery = new CompositeQuery();
		
		//订单状态(已支付且未取消)
		OrderStatus orderStatusForQuery=new OrderStatus();
		orderStatusForQuery.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());	
		orderStatusForQuery.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		compositeQuery.setStatus(orderStatusForQuery);
		
		//订单状态(游玩时间当天)
		OrderTimeRange orderTimeRange = new OrderTimeRange();
		cal.add(Calendar.DATE, -1);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		orderTimeRange.setOrdOrderVisitTimeStart(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		orderTimeRange.setOrdOrderVisitTimeEnd(cal.getTime());
		compositeQuery.setOrderTimeRange(orderTimeRange);
		
		//订单分页
		PageIndex pageIndex = new PageIndex();
		pageIndex.setBeginIndex(0);
		pageIndex.setEndIndex(Integer.MAX_VALUE);
		compositeQuery.setPageIndex(pageIndex);
		
		return orderServiceProxy.lightedCompositeQueryOrdOrder(compositeQuery);
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

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

}
