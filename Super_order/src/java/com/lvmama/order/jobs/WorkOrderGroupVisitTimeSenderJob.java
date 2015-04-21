package com.lvmama.order.jobs;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
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

public class WorkOrderGroupVisitTimeSenderJob implements Runnable {

	private static Log log = LogFactory
			.getLog(WorkOrderGroupVisitTimeSenderJob.class);

	private WorkOrderSenderBiz workOrderSenderProxy;

	private OrderService orderServiceProxy;

	private ProdProductService prodProductService;

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
	 * 上传出团通知发系统工单给计调
	 * */
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			log.info("Auto WorkOrderGroupVisitTimeSenderJob running.....");

			// 按条件获取订单
			// 出团通知书状态 :待发送
			getCompositeQuery(Constant.GROUP_ADVICE_STATE.NEEDSEND.name());
			List<OrdOrder> ordersList = orderServiceProxy
					.compositeQueryOrdOrder(compositeQuery);

			// 出团通知书状态 :已上传待发送
			getCompositeQuery(Constant.GROUP_ADVICE_STATE.UPLOADED_NOT_SENT
					.name());
			List<OrdOrder> ordersList2 = orderServiceProxy
					.compositeQueryOrdOrder(compositeQuery);
			ordersList.addAll(ordersList2);
			
			for (OrdOrder ordOrder : ordersList) {
				Long productId = ordOrder.getOrdOrderItemProds().get(0)
						.getProductId();
				ProdProduct prodProduct = prodProductService
						.getProdProduct(productId);
				boolean sendFlag = false;
				if (null != prodProduct) {
					// 判断是否出境跟团游和出境自由行,其它类型的都提前三天
					if (Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(
							prodProduct.getSubProductType())
							|| Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN
									.name().equals(
											prodProduct.getSubProductType())) {
						// 长线（澳非欧美）提前10天，日韩提前4天，东南亚、南亚、东南亚海岛、邮轮提前3天，港澳提前2天
						if (Constant.REGION_NAMES.GANGAO.name().equals(
								prodProduct.getRegionName())) {
							// 提前2天
							if (DateUtil.getDayStart(getDate(2)).compareTo(
									DateUtil.getDayStart(ordOrder
											.getVisitTime())) == 0) {
								sendFlag = true;
							}
						} else if (Constant.REGION_NAMES.DONGNANYA.name().equals(
								prodProduct.getRegionName())
								|| Constant.REGION_NAMES.NANYA.name().equals(
										prodProduct.getRegionName())
								|| Constant.REGION_NAMES.DONGNANYAHAIDAO.name().equals(
										prodProduct.getRegionName())
								|| Constant.REGION_NAMES.YOULUN.name().equals(
										prodProduct.getRegionName())) {
							// 提前3天
							if (DateUtil.getDayStart(getDate(3)).compareTo(
									DateUtil.getDayStart(ordOrder
											.getVisitTime())) == 0) {
								sendFlag = true;
							}
						} else if (Constant.REGION_NAMES.RIHAN.name().equals(
								prodProduct.getRegionName())) {
							// 提前4天
							if (DateUtil.getDayStart(getDate(4)).compareTo(
									DateUtil.getDayStart(ordOrder
											.getVisitTime())) == 0) {
								sendFlag = true;
							}
						} else if (Constant.REGION_NAMES.OUZHOU.name().equals(
								prodProduct.getRegionName())
								|| Constant.REGION_NAMES.AOZHOU.name().equals(
										prodProduct.getRegionName())
								|| Constant.REGION_NAMES.MEIZHOU.name().equals(
										prodProduct.getRegionName())
								|| Constant.REGION_NAMES.ZHONGDONGFEI.name().equals(
										prodProduct.getRegionName())) {
							// 提前10天
							if (DateUtil.getDayStart(getDate(10)).compareTo(
									DateUtil.getDayStart(ordOrder
											.getVisitTime())) == 0) {
								sendFlag = true;
							}
						}
					} else {
						// 所属公司不为上海总部
						if (!Constant.FILIALE_NAME.SH_FILIALE.name().equals(prodProduct.getFilialeName())) {
							// 提前3天
							if (DateUtil.getDayStart(getDate(3)).compareTo(
									DateUtil.getDayStart(ordOrder.getVisitTime())) == 0) {
								sendFlag = true;
							}
						}
						
					}
				}

				if (sendFlag) {
					workOrderSenderProxy.sendWorkOrder(
							ordOrder,
							Constant.WORK_ORDER_TYPE_AND_SENDGROUP.SCCTTZS
									.getWorkOrderTypeCode(),
							"/super_back/op/opOrderList.do?orderId="
									+ ordOrder.getOrderId(), Boolean.FALSE,
							Boolean.TRUE, null, null, null, null, null, false);
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

		// 订单状态:正常，已支付
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		orderStatus.setPaymentStatus(Constant.PAYMENT_STATUS.PAYED.name());
		// 出团通知书状态
		orderStatus.setGroupWordStatus(groupWordStatus);
		orderTimeRange.setOrdOrderVisitTimeStart(DateUtil
				.getDayStart(getDate(0)));
		// 后10天
		orderTimeRange.setOrdOrderVisitTimeEnd(DateUtil.getDayEnd(getDate(10)));
		orderContent.setPaymentTarget(Constant.PAYMENT_TARGET.TOLVMAMA.name());
		typeList.add(CompositeQuery.SortTypeEnum.ORDER_ID_DESC);// 降序
		compositeQuery.setExcludedContent(null);
		pageIndex.setBeginIndex(1);
		pageIndex.setEndIndex(4999);
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

	public ProdProductService getProdProductService() {
		return prodProductService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

}
