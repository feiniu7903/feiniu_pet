package com.lvmama.back.sweb.ord;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.ModifySettlementPriceService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.PageIndex;
import com.lvmama.comm.bee.vo.ord.CompositeQuery.SortTypeEnum;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_REASON;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;

@Results({
		@Result(name = "order_item_list", location = "/WEB-INF/pages/back/ord/ord_item_list.jsp"),
		@Result(name = "order_item_list_page", location = "/WEB-INF/pages/back/ord/ord_item_list_page.jsp"),
		@Result(name = "op_ord_item_list", location = "/WEB-INF/pages/back/op/ord/op_ord_item_list.jsp"),
		@Result(name = "ord_item_audit_list", location = "/WEB-INF/pages/back/op/ord/ord_item_audit_list.jsp"),
		@Result(name = "ord_item_history_list", location = "/WEB-INF/pages/back/op/ord/ord_item_history_list.jsp"),
		@Result(name = "ord_item_approve", location = "/WEB-INF/pages/back/ord/ord_item_approve.jsp"),
		@Result(name = "ord_item_history", location = "/WEB-INF/pages/back/ord/ord_item_history.jsp"),
		@Result(name = "update_sett_price", location = "/WEB-INF/pages/back/ord/update_sett_price.jsp"),
		@Result(name = "toOpListAuditResult", type = "redirect", location = "/ordItem/opListAuditResult.do?workGetOrder=${workGetOrder}&permId=${permId}"),
		@Result(name = "toListAuditResult", type = "redirect", location = "/ordItem/listAuditResult.do?productType=${productType}&tab=2&workGetOrder=${workGetOrder}&permId=${permId}") })
/**
 * 资源审核类
 * 
 * @author huangl,shihui
 */
public class OrderItemApproveAction extends BaseAction {
	private static final long serialVersionUID = 1L;
	private CertificateService certificateServiceProxy;
	private UserUserProxy userUserProxy;

	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;

	/**
	 * 消息发送接口
	 */
	private TopicMessageProducer orderMessageProducer;
	/**
	 * 我的等待审核任务订单集合列表
	 */
	private List<OrdOrder> waitAuditOrderList;
	/**
	 * 我的审核任务订单集合列表
	 */
	private List<OrdOrder> auditOrderList;
	/**
	 * 我的历史订单集合列表
	 */
	private List<OrdOrder> historyOrdersList;
	/**
	 * 我的审核任务和历史订单详细信息
	 */
	private OrdOrder orderDetail;

	private ModifySettlementPriceService modifySettlementPriceService;

	private OrdOrderItemMetaPrice ordOrderItemMetaPrice;
	/**
	 * 订单id
	 */
	private Long orderId;
	/**
	 * 资源id
	 */
	private Long metaId;
	/**
	 * 产品id
	 * */
	private Long productId;

	private String productName;
	/**
	 * 资源对象
	 */
	private OrdOrderItemMeta orderItemMeta;
	/** 分页对象 */
	private Page<OrdOrder> historyOrdOrderPageConfig;
	private CompositeQuery compositeQuery; // 综合查询类
	private OrderIdentity orderIdentity; // 根据id查询相关参数
	private OrderTimeRange orderTimeRange; // 根据时间范围查询相关参数
	private OrderStatus orderStatus; // 根据状态查询相关参数
	private OrderContent orderContent; // 根据订单内容查询相关参数
	private PageIndex pageIndex; // 分页相关参数
	private String productType;
	private String permId;
	private int tab;
	private String retentionTime;
	private String currentDate = DateUtil.getDateTime("yyyy-MM-dd", new Date());
	private String filialeName;// 所属公司
	private Long metaBranchId;// 采购类别ID
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private SupplierService supplierService;
	private BCertificateTargetService bCertificateTargetService;
	private WorkOrderFinishedBiz workOrderFinishedProxy;

	// 订单子子项id
	private Long ordItemId;
	// 修改后的实际结算价格
	private BigDecimal settPrice;
	// 订单子子项ids
	private String ordItemIds;
	// 批量修改后的实际结算价格
	private Long settBatchPrice;
	private boolean opPage = false;
	private String supplierFlag;
	// 排除的订单子子项Id
	private Long excludeOrdMetaId;
	// 订单子子项供应商ID
	private Long supplierId;
	private String[] specDates;

	private Boolean workGetOrder = true;

	private List<Long> orderItemList;// 领单所独用

	private List<SortTypeEnum> typeList;

	public String[] getSpecDates() {
		return specDates;
	}

	public void setSpecDates(String[] specDates) {
		this.specDates = specDates;
	}

	private ORD_SETTLEMENT_PRICE_REASON[] resultList;

	public ORD_SETTLEMENT_PRICE_REASON[] getResultList() {
		return resultList;
	}

	public void setResultList(ORD_SETTLEMENT_PRICE_REASON[] resultList) {
		this.resultList = resultList;
	}

	/**
	 * 产品服务接口
	 */
	private ProdProductService prodProductService;

	public String getOrdItemIds() {
		return ordItemIds;
	}

	public void setOrdItemIds(String ordItemIds) {
		this.ordItemIds = ordItemIds;
	}

	public Long getSettBatchPrice() {
		return settBatchPrice;
	}

	public void setSettBatchPrice(Long settBatchPrice) {
		this.settBatchPrice = settBatchPrice;
	}

	public Long getOrdItemId() {
		return ordItemId;
	}

	public void setOrdItemId(Long ordItemId) {
		this.ordItemId = ordItemId;
	}

	public BigDecimal getSettPrice() {
		return settPrice;
	}

	public void setSettPrice(BigDecimal settPrice) {
		this.settPrice = settPrice;
	}

	/**
	 * 确认关房操作(a.库存dayStock=0 b.overSale = "false")
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/operateCloseRoom")
	public String operateCloseRoom() throws IOException {
		PrintWriter out = this.getResponse().getWriter();
		try {
			metaProductService.updateStockByTimeAndBrachId(specDates,
					metaBranchId, this.getOperatorNameAndCheck());
			out.print("OK");
		} catch (Exception ex) {
			ex.printStackTrace();
			out.print("ERROR");
		} finally {
			out.close();
		}
		return null;
	}

	/**
	 * 查询所有待审核订单子子项列表.
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/listWaitAuditResult")
	public String listWaitAuditResult() {
		return "order_item_list";
	}

	/**
	 * op查询所有待审核订单子子项列表.
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/opListWaitAuditResult")
	public String opListWaitAuditResult() {
		return "op_ord_item_list";
	}

	/**
	 * 查询等待审核任务.
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/opListWaitResult")
	public String opListWaitResult() {
		opPage = true;
		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		orderContent = new OrderContent();
		pageIndex = new PageIndex();
		orderStatus = new OrderStatus();

		if (!this.getSessionUser().isAdministrator()) {
			orderIdentity.setOrgId(this.getSessionUser().getDepartmentId());
		}

		queryWaitAuditOrder();
		if (hasSameSupplierMetas()) {
			return "order_item_list_page";
		} else {
			return "op_ord_item_list";
		}
	}

	/**
	 * 查询等待审核任务.
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/listWaitResult")
	public String listWaitResult() {
		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		orderContent = new OrderContent();
		pageIndex = new PageIndex();
		orderStatus = new OrderStatus();

		queryWaitAuditOrder();
		if (hasSameSupplierMetas()) {
			return "order_item_list_page";
		} else {
			return "order_item_list";
		}
	}

	/**
	 * 查询--待审核任务.
	 */
	private void queryWaitAuditOrder() {

		String ordItemSupplierName = this.getRequest().getParameter(
				"ordItemSupplierName");
		String ordItemMetaProductName = this.getRequest().getParameter(
				"ordItemMetaProductName");
		String ordItemVisitTimeStart = this.getRequest().getParameter(
				"ordItemVisitTimeStart");
		String ordItemVisitTimeEnd = this.getRequest().getParameter(
				"ordItemVisitTimeEnd");
		// EBK
		String supplierFlag = null;
		if (Constant.PRODUCT_TYPE.HOTEL.name().equals(productType)
				|| Constant.PRODUCT_TYPE.ROUTE.name().equals(productType)
				|| opPage == true) {
			supplierFlag = this.getRequest().getParameter("supplierFlag");
			if (supplierFlag == null) {
				supplierFlag = "false";
			}
		}
		// 联系人手机号码.
		String contactMobile = this.getRequest().getParameter("contactMobile");
		// 联系人姓名.
		String contactName = this.getRequest().getParameter("contactName");
		// 采购产品名称.
		String metaProductName = this.getRequest().getParameter(
				"metaProductName");
		// 销售产品名称.
		String productName = this.getRequest().getParameter("productName");
		// 游玩时间:开始时间.
		String ordOrderVisitTimeStart = this.getRequest().getParameter(
				"ordOrderVisitTimeStart");
		// 游玩时间:结束时间.
		String ordOrderVisitTimeEnd = this.getRequest().getParameter(
				"ordOrderVisitTimeEnd");
		// 所属公司.
		String filialeName = this.getRequest().getParameter("filialeName");

		if (!StringUtil.isEmptyString(contactMobile)) {
			this.orderContent.setContactMobile(contactMobile.trim());
			this.getRequest().setAttribute("contactMobile",
					contactMobile.trim());
		}
		if (!StringUtil.isEmptyString(contactName)) {
			this.orderContent.setContactName(contactName.trim());
			this.getRequest().setAttribute("contactName", contactName.trim());
		}
		if (!StringUtil.isEmptyString(metaProductName)) {
			this.orderContent.setMetaProductName(metaProductName.trim());
			this.getRequest().setAttribute("metaProductName",
					metaProductName.trim());
		}
		if (!StringUtil.isEmptyString(productName)) {
			List<Long> productIds = prodProductService
					.selectProductIdsByLikeProductName(productName.trim());
			orderIdentity.setProductIds(productIds);
			this.getRequest().setAttribute("productName", productName.trim());
		}
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (!StringUtil.isEmptyString(ordOrderVisitTimeStart)
				&& !StringUtil.isEmptyString(ordOrderVisitTimeEnd)) {
			try {

				this.orderTimeRange.setOrdOrderVisitTimeStart(simpleDateFormat
						.parse(ordOrderVisitTimeStart));
				orderTimeRange.setOrdOrderVisitTimeEnd(simpleDateFormat
						.parse(ordOrderVisitTimeEnd));
				this.getRequest().setAttribute("ordOrderVisitTimeStart",
						ordOrderVisitTimeStart);
				this.getRequest().setAttribute("ordOrderVisitTimeEnd",
						ordOrderVisitTimeEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		if (StringUtils.isNotEmpty(filialeName)) {
			orderContent.setFilialeName(filialeName);
		}

		Calendar c = Calendar.getInstance();
		if (!StringUtil.isEmptyString(ordItemSupplierName)) {
			orderContent.setSupplierName(ordItemSupplierName.trim());
			this.getRequest().setAttribute("ordItemSupplierName",
					ordItemSupplierName.trim());
		}

		if (!StringUtil.isEmptyString(ordItemMetaProductName)) {
			orderContent.setMetaProductName(ordItemMetaProductName.trim());
			this.getRequest().setAttribute("ordItemMetaProductName",
					ordItemMetaProductName.trim());
		}

		if (StringUtils.isNotEmpty(filialeName)) {
			orderContent.setFilialeName(filialeName);
		}

		if (!StringUtil.isEmptyString(ordItemVisitTimeStart)
				&& !StringUtil.isEmptyString(ordItemVisitTimeEnd)) {
			try {
				orderTimeRange
						.setOrdOrderItemProdVisitTimeStart(simpleDateFormat
								.parse(ordItemVisitTimeStart));
				c.setTime(simpleDateFormat.parse(ordItemVisitTimeEnd)); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天，下同
				c.add(Calendar.DATE, 1);
				orderTimeRange.setOrdOrderItemProdVisitTimeEnd(c.getTime());
				this.getRequest().setAttribute("ordItemVisitTimeStart",
						ordItemVisitTimeStart);
				this.getRequest().setAttribute("ordItemVisitTimeEnd",
						ordItemVisitTimeEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		compositeQuery = new CompositeQuery();

		orderStatus.setItemAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.UNTAKEN
				.name());
		orderStatus
				.setOrderApproveStatus(Constant.ORDER_APPROVE_STATUS.INFOPASS
						.name()
						+ ","
						+ Constant.ORDER_APPROVE_STATUS.UNVERIFIED.name());
		orderStatus.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
		// 订单子子项需要增加只查询资源未确认的订单子子项
		orderStatus
				.setOrderResourceStatus(Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED
						.name());
		pageIndex.setEndIndex(10);
		orderContent.setMetaProductType(productType);
		if (supplierFlag != null) {
			orderContent.setSupplierFlag(Boolean.valueOf(supplierFlag));
		}
		orderTimeRange.setCreateTimeStart(DateUtils.addMonths(new Date(), -6));
		compositeQuery.setContent(orderContent);
		compositeQuery.setOrderTimeRange(orderTimeRange);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setPageIndex(pageIndex);
		compositeQuery.setOrderIdentity(orderIdentity);
		if (hasSameSupplierMetas()) {// 如果是取相同的产品只读 取固定的行数
			compositeQuery.getExcludedContent().getOrderMetaIds()
					.add(excludeOrdMetaId);
			compositeQuery.getOrderIdentity().setSupplierId(supplierId);
			pageIndex.setEndIndex(20);
		} else {
			Long totalRecords = orderServiceProxy
					.compositeQueryOrdOrderItemMetaCount(compositeQuery);
			pagination = initPagination();
			pagination.setTotalRecords(totalRecords);
			pageIndex.setBeginIndex(pagination.getFirstRow());
			pageIndex.setEndIndex(pagination.getLastRow());
			String url = "ordItem/listWaitResult.do?permId=";
			if (opPage) {// op的页面
				url = "ordItem/opListWaitResult.do?permId=";
			}
			pagination.setActionUrl(initPageActionUrl(url + permId));
			compositeQuery.setPageIndex(pageIndex);
		}
		waitAuditOrderList = orderServiceProxy
				.compositeQueryOrdOrderItemMeta(compositeQuery);
	}

	/**
	 * 计调进入页面初始化时执行 "我的订单审核任务"
	 */
	@Action(value = "/ordItem/opListAuditResult")
	public String opListAuditResult() {
		queryAuditOrder();
		resultList = ORD_SETTLEMENT_PRICE_REASON.values();
		return "ord_item_audit_list";
	}

	/**
	 * 进入页面初始化时执行 "我的订单审核任务"
	 */
	@Action(value = "/ordItem/listAuditResult")
	public String listAuditResult() {
		queryAuditOrder();
		return "order_item_list";
	}

	/**
	 * 进入 我的订单审核任务-->修改结算价 界面
	 */
	@Action(value = "/ordItem/updateSettPrice")
	public void updateSettPrice() {
		try {
			boolean flag = orderServiceProxy.updateSettPrice(ordItemId,
					settPrice.longValue(), getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 进入 我的订单审核任务-->批量修改结算价 界面
	 */
	@Action(value = "/ordItem/updateBatchPrice")
	public void updateBatchPrice() {
		try {
			boolean flag = orderServiceProxy.updateBatchPrice(ordItemIds,
					settBatchPrice, getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * @update by liuboen ,默认先查询待跟进,后查询普通审核
	 * @date 2012-3-23
	 */
	private void queryAuditOrder() {
		compositeQuery = new CompositeQuery();
		orderContent = new OrderContent();
		orderStatus = new OrderStatus();
		orderIdentity = new OrderIdentity();
		pageIndex = new PageIndex();

		// 获得当前操作人已领单未审核的订单
		orderIdentity.setItemOperatorId(getOperatorName());
		orderStatus.setItemAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.TAKEN
				.name());
		orderStatus
				.setOrderResourceStatus(Constant.ORDER_RESOURCE_STATUS.BEFOLLOWUP
						.name());

		pageIndex.setEndIndex(50); // TODO 我的订单审核任务页面无分页处理,最大显示条数为10.
		orderContent.setMetaProductType(productType);
		compositeQuery.setContent(orderContent);
		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setStatus(orderStatus);
		compositeQuery.setPageIndex(pageIndex);
		auditOrderList = orderServiceProxy
				.compositeQueryOrdOrderItemMeta(compositeQuery);

		orderStatus
				.setOrderResourceStatus(Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED
						.name());
		List<OrdOrder> tempOrdOrderList = orderServiceProxy
				.compositeQueryOrdOrderItemMeta(compositeQuery);
		// 查询EBK凭证不接受预订并且资源已经审核的订单
		orderStatus.setOrderResourceStatus(Constant.ORDER_RESOURCE_STATUS.AMPLE
				.name());
		orderStatus
				.setCertificateStatus(Constant.EBK_TASK_STATUS.REJECT.name());
		List<OrdOrder> ebkOrdOrderList = orderServiceProxy
				.compositeQueryOrdOrderItemMeta(compositeQuery);
		if (auditOrderList == null) {
			auditOrderList = new ArrayList<OrdOrder>();
		}
		auditOrderList.addAll(tempOrdOrderList);
		auditOrderList.addAll(ebkOrdOrderList);
		sortMetaList(auditOrderList);
	}

	private List<OrdOrderItemMeta> auditOrderMetaList;

	private void sortMetaList(List<OrdOrder> list) {
		auditOrderMetaList = new ArrayList<OrdOrderItemMeta>();
		for (OrdOrder order : list) {
			for (OrdOrderItemMeta meta : order.getAllOrdOrderItemMetas()) {
				if ((!meta.isApproveResourceAmple() || (meta
						.isApproveResourceAmple() && Constant.EBK_TASK_STATUS.REJECT
						.name().equals(meta.getCertificateStatus())))
						&& !StringUtils.equals(meta.getResourceStatus(),
								Constant.ORDER_RESOURCE_STATUS.LACK.name())
						&& !StringUtils.equals(meta.getTaken(),
								Constant.AUDIT_TAKEN_STATUS.UNTAKEN.name())) {
					meta.setOrdOrder(order);
					auditOrderMetaList.add(meta);
				}
			}
		}

		Map<Long, List<OrdOrderItemMeta>> map = new LinkedHashMap<Long, List<OrdOrderItemMeta>>();
		for (OrdOrderItemMeta meta : auditOrderMetaList) {
			List<OrdOrderItemMeta> metas = null;
			if (map.containsKey(meta.getSupplierId())) {
				metas = map.get(meta.getSupplierId());
			} else {
				metas = new ArrayList<OrdOrderItemMeta>();
				map.put(meta.getSupplierId(), metas);
			}
			metas.add(meta);
		}

		auditOrderMetaList = new ArrayList<OrdOrderItemMeta>();
		for (List<OrdOrderItemMeta> ims : map.values()) {
			auditOrderMetaList.addAll(ims);
		}
	}

	/**
	 * 计调我的历史订单查询
	 */
	@Action(value = "/ordItem/opOrdItemHistoryQuery")
	public String opOrdItemHistoryQuery() {
		queryHistoryOrder();
		pagination
				.setActionUrl(initPageActionUrl("ordItem/opOrdItemHistoryQuery.do?permId="
						+ permId));
		return "ord_item_history_list";
	}

	/**
	 * 我的历史订单查询
	 */
	@Action(value = "/ordItem/doOrdItemHistoryQuery")
	public String doOrdItemHistoryQuery() {
		queryHistoryOrder();
		pagination
				.setActionUrl(initPageActionUrl("ordItem/doOrdItemHistoryQuery.do?permId="
						+ permId));
		return "order_item_list";
	}

	private void queryHistoryOrder() {
		compositeQuery = new CompositeQuery();
		orderIdentity = new OrderIdentity();
		orderTimeRange = new OrderTimeRange();
		orderContent = new OrderContent();
		pageIndex = new PageIndex();

		String ordItemOrderId = this.getRequest()
				.getParameter("ordItemOrderId");
		String ordItemUserName = this.getRequest().getParameter(
				"ordItemUserName");
		String ordItemSupplierName = this.getRequest().getParameter(
				"ordItemSupplierName");
		String ordItemMetaProductName = this.getRequest().getParameter(
				"ordItemMetaProductName");
		String ordItemVisitTimeStart = this.getRequest().getParameter(
				"ordItemVisitTimeStart");
		String ordItemVisitTimeEnd = this.getRequest().getParameter(
				"ordItemVisitTimeEnd");

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();

		if (!StringUtil.isEmptyString(ordItemOrderId)) {
			orderIdentity.setOrderId(Long.parseLong(ordItemOrderId.trim()));
			this.getRequest().setAttribute("ordItemOrderId",
					ordItemOrderId.trim());
		}

		if (!StringUtil.isEmptyString(ordItemUserName)) {
			orderContent.setUserName(ordItemUserName);
			this.getRequest().setAttribute("ordItemUserName", ordItemUserName);
		}

		if (!StringUtil.isEmptyString(ordItemSupplierName)) {
			orderContent.setSupplierName(ordItemSupplierName.trim());
			this.getRequest().setAttribute("ordItemSupplierName",
					ordItemSupplierName.trim());
		}

		if (!StringUtil.isEmptyString(ordItemMetaProductName)) {
			orderContent.setMetaProductName(ordItemMetaProductName.trim());
			this.getRequest().setAttribute("ordItemMetaProductName",
					ordItemMetaProductName.trim());
		}

		if (!StringUtil.isEmptyString(ordItemVisitTimeStart)
				&& !StringUtil.isEmptyString(ordItemVisitTimeEnd)) {
			try {

				orderTimeRange
						.setOrdOrderItemProdVisitTimeStart(simpleDateFormat
								.parse(ordItemVisitTimeStart));
				c.setTime(simpleDateFormat.parse(ordItemVisitTimeEnd)); // 因取到的结束日期为当天的0:00:00，故查询时需往后延一天，下同
				c.add(Calendar.DATE, 1);
				orderTimeRange.setOrdOrderItemProdVisitTimeEnd(c.getTime());
				this.getRequest().setAttribute("ordItemVisitTimeStart",
						ordItemVisitTimeStart);
				this.getRequest().setAttribute("ordItemVisitTimeEnd",
						ordItemVisitTimeEnd);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		// 初始化各参数.
		orderStatus = new OrderStatus();
		orderIdentity.setItemOperatorId(getOperatorName());
		orderStatus.setItemAuditTakenStatus(Constant.AUDIT_TAKEN_STATUS.TAKEN
				.name());
		orderContent.setMetaProductType(productType);

		// 传值

		compositeQuery.setOrderIdentity(orderIdentity);
		compositeQuery.setStatus(orderStatus);
		// 将用户名Email等转为IdList形式查询
		Map<String, Object> param = orderContent.getUserParam(orderContent);
		if (param != null) {
			orderContent.setUserIdList(orderContent.getUserList(userUserProxy
					.getUsers(param)));
		}
		compositeQuery.setContent(orderContent);
		orderTimeRange.setCreateTimeStart(DateUtils.addYears(new Date(), -1));
		compositeQuery.setOrderTimeRange(orderTimeRange);

		Long totalRecords = orderServiceProxy
				.compositeQueryOrdOrderItemMetaCount(compositeQuery);
		pagination = initPagination();
		pagination.setTotalRecords(totalRecords);
		pageIndex.setBeginIndex(pagination.getFirstRow());
		pageIndex.setEndIndex(pagination.getLastRow());
		compositeQuery.setPageIndex(pageIndex);

		historyOrdersList = orderServiceProxy
				.compositeQueryOrdOrderItemMeta(compositeQuery);
	}

	public String initPageActionUrl(String actionUrl) {
		String ordItemOrderId = this.getRequest()
				.getParameter("ordItemOrderId");
		String ordItemUserName = this.getRequest().getParameter(
				"ordItemUserName");
		String ordItemSupplierName = this.getRequest().getParameter(
				"ordItemSupplierName");
		String ordItemMetaProductName = this.getRequest().getParameter(
				"ordItemMetaProductName");
		String ordItemVisitTimeStart = this.getRequest().getParameter(
				"ordItemVisitTimeStart");
		String ordItemVisitTimeEnd = this.getRequest().getParameter(
				"orderItemVisitTimeEnd");
		String tab = this.getRequest().getParameter("tab");
		String url = actionUrl;
		if (ordItemOrderId != null) {
			url += "&ordItemOrderId=" + ordItemOrderId;
		}
		if (ordItemUserName != null) {
			url += "&ordItemUserName=" + ordItemUserName;
		}
		if (ordItemMetaProductName != null) {
			url += "&ordItemMetaProductName=" + ordItemMetaProductName;
		}
		if (ordItemSupplierName != null) {
			url += "&ordItemSupplierName=" + ordItemSupplierName;
		}
		if (ordItemVisitTimeStart != null) {
			url += "&ordItemVisitTimeStart=" + ordItemVisitTimeStart;
		}
		if (ordItemVisitTimeEnd != null) {
			url += "&ordItemVisitTimeEnd=" + ordItemVisitTimeEnd;
		}
		if (tab != null) {
			url += "&tab=" + tab;
		}
		if (this.productType != null) {
			url += "&productType=" + productType;
		}
		if (StringUtils.isNotEmpty(filialeName)) {
			url += "&filialeName=" + filialeName;
		}
		return url;
	}

	/**
	 * 查看，显示该该资源的详细内容，利用异步返回数据
	 */
	@Action("/ordItem/showApproveOrderDetail")
	public String showApproveOrderDetail() {
		if (orderId != null && metaId != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
			this.orderItemMeta = getOrderItemMetaInfo(
					orderDetail.getAllOrdOrderItemMetas(), metaId);
			// 取出productId
			List<OrdOrderItemProd> ordOrderItemProds = this.orderDetail
					.getOrdOrderItemProds();
			for (Iterator<OrdOrderItemProd> iterator = ordOrderItemProds
					.iterator(); iterator.hasNext();) {
				OrdOrderItemProd prod = iterator.next();
				if (prod.getOrderItemProdId().equals(
						this.orderItemMeta.getOrderItemId())) {
					this.productId = prod.getProductId();
					this.productName = prod.getProductName();
					break;
				}
			}
			ordOrderItemMetaPrice = modifySettlementPriceService
					.selectByPrimaryKey(orderItemMeta.getOrderItemMetaId());
		}
		return "ord_item_approve";
	}

	/**
	 * 查看，显示该资源的详细内容，利用异步返回数据
	 */
	@Action("/ordItem/showHistoryOrderDetail")
	public String showHistoryOrderDetail() {
		if (orderId != null && metaId != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
			this.orderItemMeta = getOrderItemMetaInfo(
					orderDetail.getAllOrdOrderItemMetas(), metaId);
		}
		return "ord_item_history";
	}

	/**
	 * 退单
	 */
	@Action("/ordItem/doCancelOrderItem")
	public String doCancelOrderItem() {
		String id = this.getRequest().getParameter("id");
		long longId = Long.parseLong(id);
		orderServiceProxy
				.cancelOrdOrderItemMetaAudit(getOperatorName(), longId);
		this.listAuditResult();
		String op = getRequest().getParameter("opPage");
		if (StringUtils.equals(op, "true")) {
			return "op_ord_item_list";
		} else {
			return "order_item_list";
		}
	}

	/**
	 * 更改订单子子项对应的采购产品
	 */
	@Action("/ordItem/doUpdateOrderMeta")
	public void doUpdateOrderMeta() {
		JSONResult result = new JSONResult();
		try {
			Assert.notNull(orderId, "订单参数不存在");
			Assert.notNull(metaId, "订单子子项不存在");
			Assert.notNull(metaBranchId, "变更的订单子子项不存在");
			OrdOrderItemMeta ordOrderItemMeta = this.orderServiceProxy
					.queryOrdOrderItemMetaBy(metaId);
			List<SupBCertificateTarget> bCertificateTargetList = this.bCertificateTargetService
					.selectSuperMetaBCertificateByMetaProductId(ordOrderItemMeta
							.getMetaProductId());
			SupBCertificateTarget bCertificateTarget = bCertificateTargetList
					.get(0);
			ResultHandle handle = orderServiceProxy
					.updateOrderItemMetaBranchId(metaId, metaBranchId,
							bCertificateTarget, getOperatorNameAndCheck());
			if (handle.isFail()) {
				throw new Exception(handle.getMsg());
			}
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
			this.orderItemMeta = getOrderItemMetaInfo(
					order.getAllOrdOrderItemMetas(), metaId);

			result.put("resourceAmple", orderItemMeta.isApproveResourceAmple());
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	/**
	 * 读取类别信息并且会做类别的采购时间价读取
	 */
	@Action("/ordItem/getMetaBranch")
	public void doGetMetaBranch() {
		JSONResult result = new JSONResult();
		try {
			Assert.notNull(orderItemMeta, "参数不存在");
			Assert.notNull(orderItemMeta.getOrderId(), "订单ID不存在");
			Assert.notNull(orderItemMeta.getOrderItemMetaId(), "订单子子项不存在");
			Assert.notNull(orderItemMeta.getMetaBranchId(), "变更的采购类别不存在");
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(orderItemMeta.getOrderId());
			OrdOrderItemMeta itemMeta = getOrderItemMetaInfo(
					orderDetail.getAllOrdOrderItemMetas(),
					orderItemMeta.getOrderItemMetaId());

			TimePrice timePrice = metaProductBranchService.getTimePrice(
					orderItemMeta.getMetaBranchId(),
					orderItemMeta.getVisitTime());
			if (timePrice == null) {
				throw new Exception("该采购类别不存在时间价格表");
			}

			result.put("orderItemMetaId", itemMeta.getOrderItemMetaId());

			MetaProductBranch metaBranch = metaProductBranchService
					.getMetaBranch(orderItemMeta.getMetaBranchId());
			result.put("metaBranchName", metaBranch.getBranchName());

			MetaProduct metaProduct = metaProductService
					.getMetaProduct(metaBranch.getMetaProductId());
			result.put("metaProductName", metaProduct.getProductName());
			result.put("metaProductId", metaProduct.getMetaProductId());

			result.put("settlementPrice", timePrice.getSettlementPriceF());

			SupSupplier supplier = supplierService.getSupplier(metaProduct
					.getSupplierId());
			result.put("supplierName", supplier.getSupplierName());

			result.put("zhProductType", metaProduct.getZhProductType());

			result.put(
					"quantity",
					(Constant.PRODUCT_TYPE.HOTEL.name().equals(itemMeta
							.getProductType())) ? itemMeta.getHotelQuantity()
							: itemMeta.getProductQuantity()
									* itemMeta.getQuantity());

		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}

	/**
	 * @param supplierService
	 *            the supplierService to set
	 */
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	/**
	 * @param metaProductService
	 *            the metaProductService to set
	 */
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	/**
	 * 计调领单，用户可以自已领取所有的有订单.
	 * 
	 */
	@Action("/ordItem/opGetOrderAll")
	public String opGetOrderAll() {
		waitAuditOrder();
		return "op_ord_item_list";
	}

	@Action("/ordItem/workGetOrderAll")
	public String workGetOrderAll() {
		List<OrdOrderItemMeta> workOrderItemMetaList = this.waitAuditOrder();
		if (workOrderItemMetaList != null && workOrderItemMetaList.size() > 0) {
			workGetOrder = false;
		}
		OrdOrder order = orderServiceProxy
				.queryOrdOrderByOrdOrderItemMetaId(orderItemList.get(0));
		// 获取workTaskId，放入session中
		String workTaskId = getRequest().getParameter("workTaskId");
		if (workTaskId != null || workTaskId != "") {
			workGetOrder = true;
		}
		getRequest().getSession().setAttribute("workTaskId", workTaskId);

		if (StringUtils.isNotEmpty(order.getTravelGroupCode())
				|| (!order.isHotel() && !order.isHotelForeign()
						&& !order.isTicket() && !order.isOther())) {
			return "toOpListAuditResult";
		} else {
			for (OrdOrderItemMeta itemMeta : order.getAllOrdOrderItemMetas()) {
				productType = itemMeta.getProductType();
				break;
			}
			return "toListAuditResult";
		}
	}

	/**
	 * 领单，用户可以自已领取所有的有订单.
	 * 
	 */
	@Action("/ordItem/doGetOrderAll")
	public String doGetOrderAll() {
		waitAuditOrder();
		return "order_item_list";
	}

	private List<OrdOrderItemMeta> waitAuditOrder() {
		List<OrdOrderItemMeta> workOrderItemMetaIdList = null;
		String[] orderItemIdArray = this.getRequest().getParameterValues(
				"checkAuditName");
		orderItemList = new ArrayList<Long>();
		for (int i = 0; i < orderItemIdArray.length; i++) {
			long longId = Long.parseLong(orderItemIdArray[i]);
			orderItemList.add(longId);
		}
		if (orderItemList.size() > 0) {
			workOrderItemMetaIdList = orderServiceProxy
					.makeOrdOrderItemMetaListToAudit(getOperatorName(),
							orderItemList);
		}
		this.listWaitAuditResult();

		return workOrderItemMetaIdList;
	}

	/**
	 * 子订单资源不满足
	 */
	@Action("/ordItem/doResourceLackOrder")
	public String doResourceLackOrder() {
		String[] orderItemIdArray = this.getRequest().getParameterValues(
				"checkname");
		for (int i = 0; i < orderItemIdArray.length; i++) {
			long longId = Long.parseLong(orderItemIdArray[i]);
			String messages = "资源不满足直接废单";
			orderServiceProxy.resourceLack(longId, getOperatorName(), messages);
			OrdOrder order = orderServiceProxy
					.findOrderByOrderItemMetaId(longId);
			if (order.isNormal()) {
				orderServiceProxy.cancelOrder(order.getOrderId(),
						"资源审核不通过直接废单", "SYSTEM");
				orderServiceProxy.autoCreateOrderFullRefund(order,
						super.getOperatorName(), "资源审核不通过");
			}

		}
		this.listAuditResult();
		return "order_item_list";
	}

	/**
	 * 保存资源信息
	 */
	@Action("/ordItem/doSaveResourceStatus")
	public void doSaveInfo() {
		try {
			String status = this.orderItemMeta.getResourceStatus();
			Long orderItemMetaId = this.orderItemMeta.getOrderItemMetaId();
			String resourceLackReason = this.orderItemMeta
					.getResourceLackReason();
			// 初始化订单信息
			initOrderDetail();
			// 检查当前的资源状态
			// 如果是已经资源满足或不满足的情况下不可以再次更新
			if (Constant.ORDER_RESOURCE_STATUS.AMPLE.name().equals(
					orderItemMeta.getResourceStatus())
					|| Constant.ORDER_RESOURCE_STATUS.LACK
							.name()
							.equalsIgnoreCase(orderItemMeta.getResourceStatus())) {
				returnMessage(false);
				return;
			} else if (Constant.ORDER_RESOURCE_STATUS.BEFOLLOWUP.name()
					.equalsIgnoreCase(status)) {// 如果是待跟时，但资源不是未确认状态不可以操作
				if (!Constant.ORDER_RESOURCE_STATUS.UNCONFIRMED.name()
						.equalsIgnoreCase(orderItemMeta.getResourceStatus())) {
					returnMessage(false);
					return;
				}
			}
			boolean flag = false;
			OrdOrder order = orderServiceProxy
					.findOrderByOrderItemMetaId(orderItemMetaId);

			if (status.equalsIgnoreCase(Constant.ORDER_RESOURCE_STATUS.AMPLE
					.name())) {
				flag = orderServiceProxy.resourceAmple(orderItemMetaId,
						getOperatorName(), resourceLackReason,
						this.calcRetentionTime());
			} else if (status
					.equalsIgnoreCase(Constant.ORDER_RESOURCE_STATUS.BEFOLLOWUP
							.name())) {
				flag = orderServiceProxy.resourceBefollowup(orderItemMetaId,
						getOperatorName());
			} else {
				String messages = "资源不满足直接废单";
				flag = orderServiceProxy.resourceLack(orderItemMetaId,
						getOperatorName(), messages);

				if (order.isNormal()) {
					orderServiceProxy.cancelOrder(order.getOrderId(),
							"资源审核不通过直接废单", "SYSTEM");
					orderServiceProxy.autoCreateOrderFullRefund(order,
							super.getOperatorName(), "资源审核不通过");
				}
			}
			/*// add by zhushuying 完成工单
			if (null != getSession().getAttribute("workTaskId")) {
				String paramId = getSession().getAttribute("workTaskId")
						.toString();
				workOrderFinishedProxy.finishWorkOrder(Long.valueOf(paramId),
						"", this.getSessionUser().getUserName());

			}// end by zhushuying
*/			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 初始化订单信息
	 * 
	 * @return
	 */
	public void initOrderDetail() {
		orderId = orderItemMeta.getOrderId();
		metaId = orderItemMeta.getOrderItemMetaId();
		if (orderItemMeta.getOrderId() != null
				&& orderItemMeta.getOrderItemMetaId() != null) {
			this.orderDetail = orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
			this.orderItemMeta = getOrderItemMetaInfo(
					orderDetail.getAllOrdOrderItemMetas(), metaId);
		}
	}

	/**
	 * 拿资源保留时间
	 * 
	 * @return
	 */
	private Date calcRetentionTime() {
		Date date = null;
		if (retentionTime != null) {
			// 如果是酒店，资源保留时间默认为4小时
			if ("HOTEL".equals(orderItemMeta.getProductType())
					&& "TOLVMAMA".equals(orderItemMeta.getPaymentTarget())
					&& "true".equals(orderItemMeta.getIsResourceSendFax())) {
				date = getHotelResourceSaveTime();
			} else {
				date = getNotHotelResourceSaveTime();
			}
			// System.out.println("retenionTime: " + date);
			if (date.before(new Date())) {
				date = null;
			}
		}
		return date;
	}

	/**
	 * 对于酒店且支付给驴妈妈资保留的时间进行单独处理 从资源审核通过那刻算起，之后4小时为资源保留时间，
	 * 如果审核通过时间到最晚取消时间不足4小时，则以最晚取消时间为准
	 */

	public Date getHotelResourceSaveTime() {
		Date resourceSaveTime = DateUtil.DsDay_Hour(new Date(), 4);
		Date lastCalcelTime = getLastCancelTime();
		if (lastCalcelTime != null && resourceSaveTime.after(lastCalcelTime)) {
			resourceSaveTime = lastCalcelTime;
		}
		return resourceSaveTime;
	}

	/**
	 * 对于非酒店资源的处理 资源保留时间取设定的值， 如果支付等待时间在当前时间之前，取最晚取消时间
	 */

	public Date getNotHotelResourceSaveTime() {
		Date resourceSaveTime = DateUtil.toDate(retentionTime,
				"yyyy-MM-dd-HH-mm");
		Date lastCancelTime = getLastCancelTime();
		if (lastCancelTime != null) {
			if (resourceSaveTime.before(new Date())) {
				return resourceSaveTime;
			} else if (resourceSaveTime.after(lastCancelTime)) {
				return lastCancelTime;
			}
		}
		return resourceSaveTime;
	}

	/**
	 * 得到子项上的最晚取消时间
	 * 
	 * @return
	 */
	public Date getLastCancelTime() {
		if (orderDetail != null && orderDetail.getLastCancelTime() != null) {
			return orderDetail.getLastCancelTime();
		}
		return null;
	}

	/**
	 * 保存资源信息
	 */
	@Action("/ordItem/doSaveFaxMemo")
	public void doSaveFaxMemo() {
		try {
			Long id = this.orderItemMeta.getOrderItemMetaId();
			boolean flag = orderServiceProxy.updateFaxMemo(id,
					this.orderItemMeta.getFaxMemo(), getOperatorName());
			returnMessage(flag);
		} catch (Exception e) {
			e.printStackTrace();
			returnMessage(false);
		}
	}

	/**
	 * 从该订单资源信息列表中取出该资源信息
	 */
	private OrdOrderItemMeta getOrderItemMetaInfo(List<OrdOrderItemMeta> metas,
			Long metaId) {
		for (Iterator<OrdOrderItemMeta> iterator = metas.iterator(); iterator
				.hasNext();) {
			OrdOrderItemMeta meta = iterator.next();
			if (meta.getOrderItemMetaId().equals(metaId)) {
				return meta;
			}
		}
		return null;
	}

	private String creatorComplete = "";

	public String getCreatorComplete() {
		return creatorComplete;
	}

	public void setCreatorComplete(String creatorComplete) {
		this.creatorComplete = creatorComplete;
	}

	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				// add by zhushuying
				if (null != getSession().getAttribute("workTaskId")) {
					String paramId = getSession().getAttribute("workTaskId")
							.toString();
					creatorComplete = workOrderFinishedProxy.finishWorkOrder(
							Long.parseLong(paramId), getSessionUser()
									.getUserName());
					getSession().removeAttribute("workTaskId");
				}
				this.getResponse()
						.getWriter()
						.write("{result:true,creatorComplete:'"
								+ creatorComplete + "'}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public CompositeQuery getCompositeQuery() {
		return compositeQuery;
	}

	public void setCompositeQuery(CompositeQuery compositeQuery) {
		this.compositeQuery = compositeQuery;
	}

	public OrderIdentity getOrderIdentity() {
		return orderIdentity;
	}

	public void setOrderIdentity(OrderIdentity orderIdentity) {
		this.orderIdentity = orderIdentity;
	}

	public List<OrdOrder> getHistoryOrdersList() {
		return historyOrdersList;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}

	public void setOrderTimeRange(OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}

	public OrderContent getOrderContent() {
		return orderContent;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public Page<OrdOrder> getHistoryOrdOrderPageConfig() {
		return historyOrdOrderPageConfig;
	}

	public void setHistoryOrdOrderPageConfig(
			Page<OrdOrder> historyOrdOrderPageConfig) {
		this.historyOrdOrderPageConfig = historyOrdOrderPageConfig;
	}

	public int getTab() {
		return tab;
	}

	public void setTab(int tab) {
		this.tab = tab;
	}

	public List<OrdOrder> getWaitAuditOrderList() {
		return waitAuditOrderList;
	}

	public void setWaitAuditOrderList(List<OrdOrder> waitAuditOrderList) {
		this.waitAuditOrderList = waitAuditOrderList;
	}

	public List<OrdOrder> getAuditOrderList() {
		return auditOrderList;
	}

	public void setAuditOrderList(List<OrdOrder> auditOrderList) {
		this.auditOrderList = auditOrderList;
	}

	public void setMetaId(Long metaId) {
		this.metaId = metaId;
	}

	public OrdOrderItemMeta getOrderItemMeta() {
		return orderItemMeta;
	}

	public void setOrderItemMeta(OrdOrderItemMeta orderItemMeta) {
		this.orderItemMeta = orderItemMeta;
	}

	public Long getProductId() {
		return productId;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getRetentionTime() {
		return retentionTime;
	}

	public void setRetentionTime(String retentionTime) {
		this.retentionTime = retentionTime;
	}

	/**
	 * @return the filialeName
	 */
	public String getFilialeName() {
		return filialeName;
	}

	/**
	 * @param filialeName
	 *            the filialeName to set
	 */
	public void setFilialeName(String filialeName) {
		this.filialeName = filialeName;
	}

	/**
	 * 下拉框分公司列表
	 * 
	 * @return
	 */
	public List<CodeItem> getFilialeNameList() {
		List<CodeItem> list = CodeSet.getInstance().getCachedCodeList(
				"FILIALE_NAME");
		list.add(0, new CodeItem("", "全部"));
		return list;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	/**
	 * @param metaBranchId
	 *            the metaBranchId to set
	 */
	public void setMetaBranchId(Long metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public TopicMessageProducer getOrderMessageProducer() {
		return orderMessageProducer;
	}

	public void setOrderMessageProducer(
			TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getSupplierFlag() {
		return supplierFlag;
	}

	public void setSupplierFlag(String supplierFlag) {
		this.supplierFlag = supplierFlag;
	}

	public boolean hasSameSupplierMetas() {
		return supplierId != null && excludeOrdMetaId != null;
	}

	public void setExcludeOrdMetaId(Long excludeOrdMetaId) {
		this.excludeOrdMetaId = excludeOrdMetaId;
	}

	public Long getExcludeOrdMetaId() {
		return excludeOrdMetaId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}

	public List<OrdOrderItemMeta> getAuditOrderMetaList() {
		return auditOrderMetaList;
	}

	public String getProductName() {
		return productName;
	}

	public Boolean getWorkGetOrder() {
		return workGetOrder;
	}

	public void setWorkGetOrder(Boolean workGetOrder) {
		this.workGetOrder = workGetOrder;
	}

	public void setModifySettlementPriceService(
			ModifySettlementPriceService modifySettlementPriceService) {
		this.modifySettlementPriceService = modifySettlementPriceService;
	}

	public OrdOrderItemMetaPrice getOrdOrderItemMetaPrice() {
		return ordOrderItemMetaPrice;
	}

	/**
	 * 重发订单
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/reSendOrder")
	public void doReSendOrder() {
		Long id = Long.valueOf(this.getRequest().getParameter("id"));
		boolean result = certificateServiceProxy.reCreateSupplierCertificate(
				id, this.getOperatorId(), CertificateService.RETRANSMISSION);
		if (result) {
			this.sendAjaxMsg("SUCCESS");
		} else {
			this.sendAjaxMsg("重发订单出现异常");
		}
	}

	/**
	 * 确认修改完成 重发订单
	 * 
	 * @return
	 */
	@Action(value = "/reSendOrder")
	public void doReSend() {
		Long id = Long.valueOf(this.getRequest().getParameter("id"));
		orderMessageProducer.sendMsg(MessageFactory
				.newModifyOrderTravellerPersonMessage(id));
		this.sendAjaxMsg("SUCCESS");
	}

	/**
	 * 发送传真
	 * 
	 * @return
	 */
	@Action(value = "/ordItem/sendFax")
	public void doSendFax() {
		Long id = Long.valueOf(this.getRequest().getParameter("id"));
		boolean result = this.certificateServiceProxy
				.createCertificateEbkFaxTaskWithMetaId(id,
						this.getOperatorName());
		if (result) {
			this.sendAjaxMsg("SUCCESS");
		} else {
			this.sendAjaxMsg("发送失败");
		}
	}

	public CertificateService getCertificateServiceProxy() {
		return certificateServiceProxy;
	}

	public void setCertificateServiceProxy(
			CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public List<SortTypeEnum> getTypeList() {
		return typeList;
	}

	public void setTypeList(List<SortTypeEnum> typeList) {
		this.typeList = typeList;
	}
	
}
