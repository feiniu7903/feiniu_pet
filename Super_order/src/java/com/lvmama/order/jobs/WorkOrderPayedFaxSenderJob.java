package com.lvmama.order.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

public class WorkOrderPayedFaxSenderJob implements Runnable {

	private static Log log = LogFactory
			.getLog(WorkOrderPayedFaxSenderJob.class);

	private WorkOrderSenderBiz workOrderSenderProxy;

	private OrderService orderServiceProxy;

	private BCertificateTargetService bCertificateTargetService;

	private MetaProductService metaProductService;

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
	 * 支付后传真处理发系统工单给长线计调
	 * */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto WorkOrderPayedFaxSenderJob running.....");

			MetaProduct metaProdcut = null;

			// 按条件获取订单
			getCompositeQuery();
			List<OrdOrder> ordersList = orderServiceProxy
					.compositeQueryOrdOrder(compositeQuery);

			for (OrdOrder ordOrder : ordersList) {
				metaProdcut = metaProductService.getMetaProduct(ordOrder
						.getAllOrdOrderItemMetas().get(0).getMetaProductId());
				// 如果采购产品中的所在组织不为空就发送工单
				if (metaProdcut.getWorkGroupId() != null
						&& !"".equals(metaProdcut.getWorkGroupId())) {
					// 判断线路类型：长途跟团游、长途自由行的订单指向长线计调
					if (Constant.ORDER_TYPE.GROUP_LONG.name().equals(
							ordOrder.getOrderType())
							|| Constant.ORDER_TYPE.FREENESS_LONG.name().equals(
									ordOrder.getOrderType())) {
						// 只处理传真策略为手工发送传真的订单
						Long metaProductId = ordOrder.getAllOrdOrderItemMetas()
								.get(0).getMetaProductId();
						List<SupBCertificateTarget> certList = bCertificateTargetService
								.selectSuperMetaBCertificateByMetaProductId(metaProductId);
						if (null != certList && certList.size() > 0) {
							SupBCertificateTarget st = certList.get(0);
							if (Constant.FAX_STRATEGY.MANUAL_SEND.name()
									.equals(st.getFaxStrategy())) {
								// 支付后传真处理（长线计调）
								workOrderSenderProxy
										.sendWorkOrder(
												ordOrder,
												Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZFHCZCL
														.getWorkOrderTypeCode(),
												"/super_back/fax/ebkFax.do?ebkFaxTaskTab=ALLFAX&orderId="
														+ ordOrder.getOrderId(),
												Boolean.FALSE, Boolean.TRUE,
												null, null, null, null,null,false);
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 包装查询条件.
	 * 
	 * @return CompositeQuery.
	 */
	private CompositeQuery getCompositeQuery() {
		compositeQuery = new CompositeQuery();
		orderTimeRange = new OrderTimeRange();
		orderStatus = new OrderStatus();
		orderContent = new OrderContent();
		orderIdentity = new OrderIdentity();
		typeList = new ArrayList<SortTypeEnum>();
		pageIndex = new PageIndex();

		// 订单状态:正常
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		// 支付状态：已支付
		orderStatus.setPaymentStatus(Constant.ORDER_VIEW_STATUS.PAYED.name());
		// 游玩时间大于系统时间
		orderTimeRange.setOrdOrderVisitTimeStart(DateUtil
				.getDayStart(getDate(0)));
		// 只获取3个月内创建的订单
		orderTimeRange.setCreateTimeStart(DateUtil.getDayStart(getDate(-90)));
		orderTimeRange.setCreateTimeEnd(DateUtil.getDayEnd(getDate(0)));

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

	public BCertificateTargetService getbCertificateTargetService() {
		return bCertificateTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

}
