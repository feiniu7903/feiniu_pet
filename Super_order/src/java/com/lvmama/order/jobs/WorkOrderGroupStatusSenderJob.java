package com.lvmama.order.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

public class WorkOrderGroupStatusSenderJob implements Runnable {

	private static Log log = LogFactory
			.getLog(WorkOrderGroupStatusSenderJob.class);

	private WorkOrderSenderBiz workOrderSenderProxy;

	private OrderService orderServiceProxy;

	/**
	 * 综合查询类.
	 */
	private CompositeQuery compositeQuery;
	/**
	 * 根据id查询相关参数.
	 */
	private OrderIdentity orderIdentity;
	/**
	 * 根据时间范围查询相关参数
	 */
	private OrderTimeRange orderTimeRange;
	/**
	 * 根据状态查询相关参数.
	 */
	private OrderStatus orderStatus;
	/**
	 * 根据订单内容查询相关参数.
	 */
	private OrderContent orderContent;
	/**
	 * 分页相关参数.
	 */
	private PageIndex pageIndex;
	/**
	 * 排序的相关参数.
	 */
	private List<SortTypeEnum> typeList;

	/**
	 * 出团通知书未通知发系统工单给客服
	 * */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto WorkOrderGroupStatusSenderJob running.....");

			// 按条件获取订单
			// 出团通知书状态 :已发送未通知
			getCompositeQuery(Constant.GROUP_ADVICE_STATE.SENT_NO_NOTICE.name());
			List<OrdOrder> ordersList = orderServiceProxy
					.compositeQueryOrdOrder(compositeQuery);

			// 出团通知书状态 :已修改未通知
			getCompositeQuery(Constant.GROUP_ADVICE_STATE.MODIFY_NO_NOTICE
					.name());
			List<OrdOrder> ordersList2 = orderServiceProxy
					.compositeQueryOrdOrder(compositeQuery);
			ordersList.addAll(ordersList2);
			// 出团通知书未通知提醒
			for (OrdOrder ordOrder : ordersList) {
				if((Constant.SUB_PRODUCT_TYPE.GROUP_LONG.name().equalsIgnoreCase(ordOrder.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.name().equalsIgnoreCase(ordOrder.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(ordOrder.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(ordOrder.getOrderType()))
						&& (DateUtil.getDayStart(getDate(2)).compareTo(
								DateUtil.getDayStart(ordOrder.getVisitTime())) == 0)){
					workOrderSenderProxy.sendWorkOrder(ordOrder,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CTTZSWTZTX
									.getWorkOrderTypeCode(),
							"/super_back/ord/order_monitor_list!doOrderQuery.do?pageType=monitor&orderId="
									+ ordOrder.getOrderId(), Boolean.FALSE,
							Boolean.FALSE, null, null, null, null,null,false);
				}
			}
		}
	}

	/**
	 * 包装查询条件.
	 * 
	 * @return CompositeQuery.
	 */
	private CompositeQuery getCompositeQuery(String groupWordStatus) {
		compositeQuery = new CompositeQuery();
		orderTimeRange = new OrderTimeRange();
		orderStatus = new OrderStatus();
		orderContent = new OrderContent();
		orderIdentity = new OrderIdentity();
		typeList = new ArrayList<SortTypeEnum>();
		pageIndex = new PageIndex();

		// 订单状态
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		// 出团通知书状态
		orderStatus.setGroupWordStatus(groupWordStatus);
		orderTimeRange.setOrdOrderVisitTimeStart(DateUtil
				.getDayStart(getDate(0)));

		orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);// 降序
		compositeQuery.setExcludedContent(null);
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(9999);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setContent(orderContent);
		compositeQuery.setTypeList(typeList);
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.getQueryFlag().setQuerySupplier(false);
		compositeQuery.getQueryFlag().setQueryUser(false);
		return compositeQuery;
	}

	private Date getDate(int addDayCnt) {
		Date date = null;
		Calendar cl = Calendar.getInstance();
		if (addDayCnt != 0) {
			cl.add(Calendar.DATE, addDayCnt);
		}
		date = cl.getTime();
		return date;
	}
	
	public WorkOrderSenderBiz getWorkOrderSenderProxy() {
		return workOrderSenderProxy;
	}

	public void setWorkOrderSenderProxy(WorkOrderSenderBiz workOrderSenderProxy) {
		this.workOrderSenderProxy = workOrderSenderProxy;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
