package com.lvmama.comm.bee.vo.ord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
/**
 * 综合查询.
 * 
 * <pre>
 * 为复杂的订单查询提供统一的查询条件组织方式
 * </pre>
 * 
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.common.UtilityTool.isValid#isValid(Object)
 * @see com.lvmama.common.vo.Constant.AUDIT_TAKEN_STATUS
 * @see com.lvmama.common.vo.Constant.CHANNEL
 * @see com.lvmama.common.vo.Constant.ORDER_APPROVE_STATUS
 * @see com.lvmama.common.vo.Constant.ORDER_PERFORM_STATUS
 * @see com.lvmama.common.vo.Constant.ORDER_RESOURCE_STATUS
 * @see com.lvmama.common.vo.Constant.ORDER_STATUS
 * @see com.lvmama.common.vo.Constant.PAYMENT_STATUS
 * @see com.lvmama.common.vo.Constant.PAYMENT_TARGET
 * @see com.lvmama.common.vo.Constant.SETTLEMENT_DIRECTION
 * @see com.lvmama.common.vo.Constant.SUB_PRODUCT_TYPE
 */
public final class CompositeQuery implements Serializable {
	public CompositeQuery(){
		
	}
	
	public CompositeQuery(Constant.MoneyAccountChangeType moneyAccountChangeType) {
		this.getMoneyAccountChangeLogRelate().moneyAccountChangeType=moneyAccountChangeType;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = -399743886088602606L;
	/**
	 * 与订单状态有关的参数.
	 */
	private OrderStatus status = new OrderStatus();
	/**
	 * 与订单内容有关的参数.
	 */
	private OrderContent content = new OrderContent();
	/**
	 * 与订单时间范围有关的参数.
	 */
	private OrderTimeRange orderTimeRange = new OrderTimeRange();
	/**
	 * 与订单相关ID有关的参数.
	 */
	private OrderIdentity orderIdentity = new OrderIdentity();
	/**
	 * 与订单相关排序类型有关的枚举列表.
	 */
	private List<SortTypeEnum> typeList = new ArrayList<SortTypeEnum>();
	/**
	 * 与订单分页有关的参数.
	 */
	private PageIndex pageIndex = new PageIndex();
	/**
	 * 表示订单排除条件.
	 */
	private ExcludedContent excludedContent = new ExcludedContent();
	/**
	 * 发票管理查询条件.
	 */
	private InvoiceRelate invoiceRelate = new InvoiceRelate();
	/**
	 * 投诉管理查询条件.
	 */
	private SaleServiceRelate saleServiceRelate = new SaleServiceRelate();
	/**
	 * 结算管理结算队列查询条件.
	 */
	private SettlementQueueRelate settlementQueueRelate = new SettlementQueueRelate();
	/**
	 * 结算管理结算子项查询条件.
	 */
	private SettlementItemRelate settlementItemRelate = new SettlementItemRelate();
	/**
	 * 结算单查询条件.
	 */
	private OrdSettlementRelate ordSettlementRelate = new OrdSettlementRelate();
	/**
	 * 结算管理完成的结算子项查询条件.
	 */
	private FinishSettlementItemRelate finishSettlementItemRelate = new FinishSettlementItemRelate();
	/**
	 * 履行对象查询条件.
	 */
	private MetaPerformRelate metaPerformRelate = new MetaPerformRelate();

	/**
	 * 履行明细查询条件.
	 */
	private PerformDetailRelate performDetailRelate = new PerformDetailRelate();
	/**
	 * 与履行明细相关排序类型有关的枚举列表.
	 */
	private List<PerformDetailSortTypeEnum> performTypeList = new ArrayList<PerformDetailSortTypeEnum>();
	/**
	 * 通关汇总查询条件.
	 */
	private PassPortSummaryRelate passPortSummaryRelate = new PassPortSummaryRelate();
	/**
	 * 通关明细查询条件.
	 */
	private PassPortDetailRelate passPortDetailRelate = new PassPortDetailRelate();

	/**
	 * 现金账户变更日志查询条件.
	 */
	private MoneyAccountChangeLogRelate moneyAccountChangeLogRelate = new MoneyAccountChangeLogRelate();
	/**
	 * 现金账户提现日志查询条件.
	 */
	private MoneyDrawRelate moneyDrawRelate = new MoneyDrawRelate();
	/**
	 * 打款记录查询条件.
	 */
	private PlayMoneyRelate playMoneyRelate = new PlayMoneyRelate();
	
	/**
	 * 是否需要查询pet_public当中的数据
	 */
	private QueryFlag queryFlag = new QueryFlag();

	/**
	 * 班次查询条件
	 */
	private List<Tours> toursRelate = new ArrayList<Tours>();
	private List<String> travelGroupStatus;
	/**
	 * 订单合同状态查询条件
	 */
	private EContractRelate eContractRelate=new EContractRelate();
	/**
	 * 二次处理查询条件.
	 */
	private OrderTrackRelate orderTrackRelate = new OrderTrackRelate();
	
	/**
	 * 区分奖金和现金
	 */
	private String payFrom;
	/**
	 * 区分奖金和现金的退款
	 */
	private String bonusRefundment;
	
	
	/**
	 * 获取二次处理查询条件.
	 * @return
	 */
	public OrderTrackRelate getOrderTrackRelate() {
		return orderTrackRelate;
	}

	/**
	 * 设置二次处理查询条件.
	 * @param orderTrackRelate
	 */
	public void setOrderTrackRelate(OrderTrackRelate orderTrackRelate) {
		this.orderTrackRelate = orderTrackRelate;
	}



	/**
	 * getPlayMoneyRelate.
	 * 
	 * @return 打款记录查询条件
	 */
	public PlayMoneyRelate getPlayMoneyRelate() {
		return playMoneyRelate;
	}

	

	/**
	 * @return the travelGroupStatus
	 */
	public List<String> getTravelGroupStatus() {
		return travelGroupStatus;
	}



	/**
	 * @param travelGroupStatus the travelGroupStatus to set
	 */
	public void setTravelGroupStatus(List<String> travelGroupStatus) {
		this.travelGroupStatus = travelGroupStatus;
	}



	/**
	 * setPlayMoneyRelate.
	 * 
	 * @param playMoneyRelate
	 *            打款记录查询条件
	 */
	public void setPlayMoneyRelate(final PlayMoneyRelate playMoneyRelate) {
		this.playMoneyRelate = playMoneyRelate;
	}

	/**
	 * getMoneyDrawRelate.
	 * 
	 * @return 现金账户提现日志查询条件
	 */
	public MoneyDrawRelate getMoneyDrawRelate() {
		return moneyDrawRelate;
	}

	/**
	 * setMoneyDrawRelate.
	 * 
	 * @param moneyDrawRelate
	 *            现金账户提现日志查询条件
	 */
	public void setMoneyDrawRelate(final MoneyDrawRelate moneyDrawRelate) {
		this.moneyDrawRelate = moneyDrawRelate;
	}

	/**
	 * getMoneyAccountChangeLogRelate.
	 * 
	 * @return 现金账户变更日志查询条件
	 */
	public MoneyAccountChangeLogRelate getMoneyAccountChangeLogRelate() {
		return moneyAccountChangeLogRelate;
	}

	/**
	 * setMoneyAccountChangeLogRelate.
	 * 
	 * @param moneyAccountChangeLogRelate
	 *            现金账户变更日志查询条件
	 */
	public void setMoneyAccountChangeLogRelate(
			final MoneyAccountChangeLogRelate moneyAccountChangeLogRelate) {
		this.moneyAccountChangeLogRelate = moneyAccountChangeLogRelate;
	}

	/**
	 * getStatus.
	 * 
	 * @return 与订单状态有关的参数
	 */
	public OrderStatus getStatus() {
		return status;
	}

	/**
	 * setStatus.
	 * 
	 * @param status
	 *            与订单状态有关的参数
	 */
	public void setStatus(final OrderStatus status) {
		this.status = status;
	}

	/**
	 * getContent.
	 * 
	 * @return 与订单内容有关的参数
	 */
	public OrderContent getContent() {
		return content;
	}

	/**
	 * setContent.
	 * 
	 * @param content
	 *            与订单内容有关的参数
	 */
	public void setContent(final OrderContent content) {
		this.content = content;
	}

	/**
	 * getOrderTimeRange.
	 * 
	 * @return 与订单时间范围有关的参数
	 */
	public OrderTimeRange getOrderTimeRange() {
		return orderTimeRange;
	}

	/**
	 * setOrderTimeRange.
	 * 
	 * @param orderTimeRange
	 *            与订单时间范围有关的参数
	 */
	public void setOrderTimeRange(final OrderTimeRange orderTimeRange) {
		this.orderTimeRange = orderTimeRange;
	}

	/**
	 * getOrderIdentity.
	 * 
	 * @return 与订单相关ID有关的参数
	 */
	public OrderIdentity getOrderIdentity() {
		return orderIdentity;
	}

	/**
	 * setOrderIdentity.
	 * 
	 * @param orderIdentity
	 *            与订单相关ID有关的参数
	 */
	public void setOrderIdentity(final OrderIdentity orderIdentity) {
		this.orderIdentity = orderIdentity;
	}

	/**
	 * getTypeList.
	 * 
	 * @return 与订单相关排序类型有关的枚举列表
	 */
	public List<SortTypeEnum> getTypeList() {
		return typeList;
	}

	/**
	 * setTypeList.
	 * 
	 * @param typeList
	 *            与订单相关排序类型有关的枚举列表
	 */
	public void setTypeList(final List<SortTypeEnum> typeList) {
		this.typeList = typeList;
	}

	/**
	 * getPageIndex.
	 * 
	 * @return 与订单分页有关的参数
	 */
	public PageIndex getPageIndex() {
		return pageIndex;
	}

	/**
	 * setPageIndex.
	 * 
	 * @param pageIndex
	 *            与订单分页有关的参数
	 */
	public void setPageIndex(final PageIndex pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * getExcludedContent.
	 * 
	 * @return 表示订单排除条件
	 */
	public ExcludedContent getExcludedContent() {
		return excludedContent;
	}

	/**
	 * setExcludedContent.
	 * 
	 * @param excludedContent
	 *            表示订单排除条件
	 */
	public void setExcludedContent(final ExcludedContent excludedContent) {
		this.excludedContent = excludedContent;
	}

	/**
	 * getInvoiceRelate.
	 * 
	 * @return 发票管理查询条件
	 */
	public InvoiceRelate getInvoiceRelate() {
		return invoiceRelate;
	}

	/**
	 * setInvoiceRelate.
	 * 
	 * @param invoiceRelate
	 *            发票管理查询条件
	 */
	public void setInvoiceRelate(final InvoiceRelate invoiceRelate) {
		this.invoiceRelate = invoiceRelate;
	}

	/**
	 * getSaleServiceRelate.
	 * 
	 * @return 投诉管理查询条件
	 */
	public SaleServiceRelate getSaleServiceRelate() {
		return saleServiceRelate;
	}

	/**
	 * setSaleServiceRelate.
	 * 
	 * @param saleServiceRelate
	 *            投诉管理查询条件
	 */
	public void setSaleServiceRelate(final SaleServiceRelate saleServiceRelate) {
		this.saleServiceRelate = saleServiceRelate;
	}

	/**
	 * getSettlementQueueRelate.
	 * 
	 * @return 结算管理结算队列查询条件
	 */
	public SettlementQueueRelate getSettlementQueueRelate() {
		return settlementQueueRelate;
	}

	/**
	 * setSettlementQueueRelate.
	 * 
	 * @param settlementQueueRelate
	 *            结算管理结算队列查询条件
	 */
	public void setSettlementQueueRelate(
			final SettlementQueueRelate settlementQueueRelate) {
		this.settlementQueueRelate = settlementQueueRelate;
	}

	/**
	 * getSettlementItemRelate.
	 * 
	 * @return 结算管理结算子项查询条件
	 */
	public SettlementItemRelate getSettlementItemRelate() {
		return settlementItemRelate;
	}

	/**
	 * setSettlementItemRelate.
	 * 
	 * @param settlementItemRelate
	 *            结算管理结算子项查询条件
	 */
	public void setSettlementItemRelate(
			final SettlementItemRelate settlementItemRelate) {
		this.settlementItemRelate = settlementItemRelate;
	}

	/**
	 * getOrdSettlementRelate.
	 * 
	 * @return 结算单查询条件
	 */
	public OrdSettlementRelate getOrdSettlementRelate() {
		return ordSettlementRelate;
	}

	/**
	 * setOrdSettlementRelate.
	 * 
	 * @param ordSettlementRelate
	 *            结算单查询条件
	 */
	public void setOrdSettlementRelate(
			final OrdSettlementRelate ordSettlementRelate) {
		this.ordSettlementRelate = ordSettlementRelate;
	}

	/**
	 * getFinishSettlementItemRelate.
	 * 
	 * @return 结算管理完成的结算子项查询条件
	 */
	public FinishSettlementItemRelate getFinishSettlementItemRelate() {
		return finishSettlementItemRelate;
	}

	/**
	 * setFinishSettlementItemRelate.
	 * 
	 * @param finishSettlementItemRelate
	 *            结算管理完成的结算子项查询条件
	 */
	public void setFinishSettlementItemRelate(
			final FinishSettlementItemRelate finishSettlementItemRelate) {
		this.finishSettlementItemRelate = finishSettlementItemRelate;
	}

	/**
	 * getMetaPerformRelate.
	 * 
	 * @return 履行对象查询条件
	 */
	public MetaPerformRelate getMetaPerformRelate() {
		return metaPerformRelate;
	}

	/**
	 * setMetaPerformRelate.
	 * 
	 * @param metaPerformRelate
	 *            履行对象查询条件
	 */
	public void setMetaPerformRelate(final MetaPerformRelate metaPerformRelate) {
		this.metaPerformRelate = metaPerformRelate;
	}

	/**
	 * getPerformDetailRelate.
	 * 
	 * @return 履行明细查询条件
	 */
	public PerformDetailRelate getPerformDetailRelate() {
		return performDetailRelate;
	}

	/**
	 * setPerformDetailRelate.
	 * 
	 * @param performDetailRelate
	 *            履行明细查询条件
	 */
	public void setPerformDetailRelate(
			final PerformDetailRelate performDetailRelate) {
		this.performDetailRelate = performDetailRelate;
	}

	/**
	 * getPerformTypeList.
	 * 
	 * @return 与履行明细相关排序类型有关的枚举列表
	 */
	public List<PerformDetailSortTypeEnum> getPerformTypeList() {
		return performTypeList;
	}

	/**
	 * setPerformTypeList.
	 * 
	 * @param performTypeList
	 *            与履行明细相关排序类型有关的枚举列表
	 */
	public void setPerformTypeList(
			final List<PerformDetailSortTypeEnum> performTypeList) {
		this.performTypeList = performTypeList;
	}

	/**
	 * getPassPortSummaryRelate.
	 * 
	 * @return 通关汇总查询条件
	 */
	public PassPortSummaryRelate getPassPortSummaryRelate() {
		return passPortSummaryRelate;
	}

	/**
	 * setPassPortSummaryRelate.
	 * 
	 * @param passPortSummaryRelate
	 *            通关汇总查询条件
	 */
	public void setPassPortSummaryRelate(
			final PassPortSummaryRelate passPortSummaryRelate) {
		this.passPortSummaryRelate = passPortSummaryRelate;
	}

	/**
	 * getPassPortDetailRelate.
	 * 
	 * @return 通关明细查询条件
	 */
	public PassPortDetailRelate getPassPortDetailRelate() {
		return passPortDetailRelate;
	}

	/**
	 * setPassPortDetailRelate.
	 * 
	 * @param passPortDetailRelate
	 *            通关明细查询条件
	 */
	public void setPassPortDetailRelate(
			final PassPortDetailRelate passPortDetailRelate) {
		this.passPortDetailRelate = passPortDetailRelate;
	}

	/**
	 * 返回班次列表
	 * 
	 * @return
	 */
	public List<Tours> getToursRelate() {
		return toursRelate;
	}

	/**
	 * 设置班次列表
	 */
	public void setToursRelate(final List<Tours> toursRelate) {
		this.toursRelate = toursRelate;
	}
	
	/**
	 * 返回订单合同状态信息
	 * @return
	 */
	public EContractRelate getEContractRelate() {
		return eContractRelate;
	}
	/**
	 * 设置订单合同状态信息
	 * @param econtractStatus
	 */
	public void setEContractRelate(EContractRelate eContractRelate) {
		this.eContractRelate = eContractRelate;
	}


	/**
	 * 与订单相关排序类型有关的枚举.
	 */
	public static enum SortTypeEnum {
		/**
		 * 订单创建时间升序.
		 */
		CREATE_TIME_ASC,
		/**
		 * 订单创建时间降序.
		 */
		CREATE_TIME_DESC,
		/**
		 * 领单时间升序.
		 */
		RECEIVE_ORDER_TIME_ASC,
		/**
		 * 领单时间降序.
		 */
		RECEIVE_ORDER_TIME_DESC,
		/**
		 * 订单ID升序.
		 */
		ORDER_ID_ASC,
		/**
		 * 订单ID降序.
		 */
		ORDER_ID_DESC,
		/**
		 * 按需重拨升序
		 */
		REDAIL_ASC,
		/**
		 * 投诉管理创建时间升序.
		 */
		SALE_SERVICE_CREATE_TIME_ASC,
		/**
		 * 投诉管理创建时间降序.
		 */
		SALE_SERVICE_CREATE_TIME_DESC,
		/**
		 * 采购产品订单子项ID升序.
		 */
		ORDER_ITEM_META_ID_ASC,
		/**
		 * 采购产品订单子项ID降序.
		 */
		ORDER_ITEM_META_ID_DESC,	
		/**
		 * 发票升序
		 */
		INVOICE_ID_ASC,
		/**
		 * 发票降序
		 */
		INVOICE_ID_DESC,
		/**
		 * 最晚取消时间升序
		 */
		
		ORD_LAST_CANCEL_TIME_ASC,
		/**
		 * 最晚取消小时数降序
		 */
		ORD_LAST_CANCEL_TIME_DESC
		
	}
	
	/**
	 * 与订单状态有关的参数.
	 */
	public static final class OrderStatus implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5343277695512019514L;
		/**
		 * 订单支付状态.
		 */
		private Constant.PAYMENT_STATUS paymentStatus;
		/**
		 * 订单审核状态，多个状态使用逗号分隔.
		 */
		private Constant.ORDER_APPROVE_STATUS[] orderApproveStatus;
		/**
		 * 订单审核状态，多个状态使用逗号分隔.
		 */
		private Constant.INFO_APPROVE_STATUS infoApproveStatus;
		
		/**
		 * 订单状态.
		 */
		private Constant.ORDER_STATUS orderStatus;
		/**
		 * 订单履行状态.
		 */
		private Constant.ORDER_PERFORM_STATUS orderPerformStatus;
		/**
		 * 订单资源状态.
		 */
		private Constant.ORDER_RESOURCE_STATUS orderResourceStatus;
		/**
		 * 订单结算状态.
		 */
		private Constant.SETTLEMENT_DIRECTION settlementDirection;
		/**
		 * 开票状态.
		 */
		private String ticketStatus;
		/**
		 * 出团通知书状态
		 */
		private String groupWordStatus;
		/**
		 * 订单领单状态.
		 */
		private Constant.AUDIT_TAKEN_STATUS auditTakenStatus;
		/**
		 * 子订单领单状态.
		 */
		private Constant.AUDIT_TAKEN_STATUS itemAuditTakenStatus;
		
		/**
		 * 订单领单状态（取false同时查询null值）
		 */
		private Constant.AUDIT_TAKEN_STATUS specialTakenStatus;
		
		/**
		 * 合同状态.
		 */
		private Constant.ECONTRACT_STATUS contractStatus; 
		/**
		 * 凭证状态
		 */
		private String certificateStatus;

		/**
		 * 资源审核不通过原因审核
		 */
		private Constant.ORDER_RESOURCE_LACK_REASON orderResourceLackReason;
		/**
		 * 不定期密码券使用状态.
		 */
		private Constant.PASSCODE_USE_STATUS useStatus;
		
		public void setOrderResourceLackReason(
				final String orderResourceLackReason) {
			if (UtilityTool.isValid(orderResourceLackReason)) {
				this.orderResourceLackReason = Enum.valueOf(Constant.ORDER_RESOURCE_LACK_REASON.class,
						orderResourceLackReason);
			}
		}
		
		public Constant.ORDER_RESOURCE_LACK_REASON getOrderResourceLackReason() {
			return orderResourceLackReason;
		}

		/**
		 * 合同状态.
		 * @return
		 */
		public Constant.ECONTRACT_STATUS getContractStatus() {
			return contractStatus;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 不定期密码券使用状态
		 */
		public Constant.PASSCODE_USE_STATUS getUseStatus() {
			return useStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            不定期密码券使用状态
		 */
		public void setUseStatus(final String useStatus) {
			if (UtilityTool.isValid(useStatus)) {
				this.useStatus = Enum
						.valueOf(Constant.PASSCODE_USE_STATUS.class, useStatus);
			}
		}
		
		/**
		 * getPaymentStatus.
		 * 
		 * @return 订单支付状态
		 */
		public Constant.PAYMENT_STATUS getPaymentStatus() {
			return paymentStatus;
		}

		/**
		 * setPaymentStatus.
		 * 
		 * @param paymentStatus
		 *            订单支付状态
		 */
		public void setPaymentStatus(final String paymentStatus) {
			if (UtilityTool.isValid(paymentStatus)) {
				this.paymentStatus = Enum.valueOf(Constant.PAYMENT_STATUS.class,
						paymentStatus);
			}
		}

		/**
		 * getOrderApproveStatus.
		 * 
		 * @return 订单审核状态
		 */
		public Constant.ORDER_APPROVE_STATUS[] getOrderApproveStatus() {
			return orderApproveStatus;
		}
		public Constant.INFO_APPROVE_STATUS getInfoApproveStatus() {
			return infoApproveStatus;
		}

		public void setInfoApproveStatus(final String infoApproveStatus) {
			this.infoApproveStatus = Enum.valueOf(Constant.INFO_APPROVE_STATUS.class,infoApproveStatus);
		}
		/**
		 * setOrderApproveStatus.
		 * 
		 * @param orderApproveStatus
		 *            订单审核状态，多个状态使用逗号分隔
		 */
		public void setOrderApproveStatus(final String orderApproveStatus) {
			final String[] array = orderApproveStatus.split(",");
			final Constant.ORDER_APPROVE_STATUS[] enumArray = new Constant.ORDER_APPROVE_STATUS[array.length];
			for (int i = 0; i < array.length; i++) {
				if (UtilityTool.isValid(array[i])) {
					enumArray[i] = Enum.valueOf(Constant.ORDER_APPROVE_STATUS.class,
							array[i]);
				}
			}
			this.orderApproveStatus = enumArray;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 订单状态
		 */
		public Constant.ORDER_STATUS getOrderStatus() {
			return orderStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            订单状态
		 */
		public void setOrderStatus(final String orderStatus) {
			if (UtilityTool.isValid(orderStatus)) {
				this.orderStatus = Enum
						.valueOf(Constant.ORDER_STATUS.class, orderStatus);
			}
		}

		/**
		 * getOrderPerformStatus.
		 * 
		 * @return 订单履行状态
		 */
		public Constant.ORDER_PERFORM_STATUS getOrderPerformStatus() {
			return orderPerformStatus;
		}

		/**
		 * setOrderPerformStatus.
		 * 
		 * @param orderPerformStatus
		 *            订单履行状态
		 */
		public void setOrderPerformStatus(final String orderPerformStatus) {
			if (UtilityTool.isValid(orderPerformStatus)) {
				this.orderPerformStatus = Enum.valueOf(
						Constant.ORDER_PERFORM_STATUS.class, orderPerformStatus);
			}
		}

		/**
		 * getOrderResourceStatus.
		 * 
		 * @return 订单资源状态
		 */
		public Constant.ORDER_RESOURCE_STATUS getOrderResourceStatus() {
			return orderResourceStatus;
		}

		/**
		 * setOrderResourceStatus.
		 * 
		 * @param orderResourceStatus
		 *            订单资源状态
		 */
		public void setOrderResourceStatus(final String orderResourceStatus) {
			if (UtilityTool.isValid(orderResourceStatus)) {
				this.orderResourceStatus = Enum.valueOf(
						Constant.ORDER_RESOURCE_STATUS.class, orderResourceStatus);
			}
		}

		/**
		 * getSettlementDirection.
		 * 
		 * @return 订单结算状态
		 */
		public Constant.SETTLEMENT_DIRECTION getSettlementDirection() {
			return settlementDirection;
		}

		/**
		 * setSettlementDirection.
		 * 
		 * @param settlementDirection
		 *            订单结算状态
		 */
		public void setSettlementDirection(final String settlementDirection) {
			if (UtilityTool.isValid(settlementDirection)) {
				this.settlementDirection = Enum.valueOf(
						Constant.SETTLEMENT_DIRECTION.class, settlementDirection);
			}
		}

		/**
		 * 获取开票状态.
		 * @return
		 */
		public String getTicketStatus() {
			return ticketStatus;
		}

		/**
		 * 设置开票状态.
		 * @param ticketStatus
		 */
		public void setTicketStatus(String ticketStatus) {
			this.ticketStatus = ticketStatus;
		}

		/**
		 * 得到出团通知书状态
		 * @author nixianjun
		 * @CreateDate 2012-7-13
		 */
		public String getGroupWordStatus() {
			return groupWordStatus;
		}

		/**
		 * 设置出团通知书状态
		 * @param groupWordStatus 出团通知书状态
		 * @author nixianjun
		 * @CreateDate 2012-7-13
		 */
		public void setGroupWordStatus(String groupWordStatus) {
			this.groupWordStatus = groupWordStatus;
		}

		/**
		 * getAuditTakenStatus.
		 * 
		 * @return 订单领单状态
		 */
		public Constant.AUDIT_TAKEN_STATUS getAuditTakenStatus() {
			return auditTakenStatus;
		}

		/**
		 * setAuditTakenStatus.
		 * 
		 * @param auditTakenStatus
		 *            订单领单状态
		 */
		public void setAuditTakenStatus(final String auditTakenStatus) {
			if (UtilityTool.isValid(auditTakenStatus)) {
				this.auditTakenStatus = Enum.valueOf(Constant.AUDIT_TAKEN_STATUS.class,
						auditTakenStatus);
			}
		}

		/**
		 * getItemAuditTakenStatus.
		 * 
		 * @return 子订单领单状态
		 */
		public Constant.AUDIT_TAKEN_STATUS getItemAuditTakenStatus() {
			return itemAuditTakenStatus;
		}

		/**
		 * setItemAuditTakenStatus.
		 * 
		 * @param itemAuditTakenStatus
		 *            子订单领单状态
		 */
		public void setItemAuditTakenStatus(final String itemAuditTakenStatus) {
			if (UtilityTool.isValid(itemAuditTakenStatus)) {
				this.itemAuditTakenStatus = Enum.valueOf(
						Constant.AUDIT_TAKEN_STATUS.class, itemAuditTakenStatus);
			}
		}

		public Constant.AUDIT_TAKEN_STATUS getSpecialTakenStatus() {
			return specialTakenStatus;
		}

		public void setSpecialTakenStatus(final String specialTakenStatus) {
			if (UtilityTool.isValid(specialTakenStatus)) {
				this.specialTakenStatus = Enum.valueOf(Constant.AUDIT_TAKEN_STATUS.class,
						specialTakenStatus);
			}
		}
		/**
		 * 凭证状态
		 * 
		 * @return
		 */
		public String getCertificateStatus() {
			return certificateStatus;
		}
		/**
		 * 凭证状态
		 * 
		 * @param certificateStatus
		 */
		public void setCertificateStatus(String certificateStatus) {
			this.certificateStatus = certificateStatus;
		}
	}

	/**
	 * 与订单内容有关的参数.
	 */
	public static final class OrderContent extends UserQuery implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -7053270400882477266L;
		/**
		 * 是否已关联发票:已关联.
		 */
		public static final String NEED_INVOICE_TRUE = "true";
		/**
		 * 是否已关联发票:未关联.
		 */
		public static final String NEED_INVOICE_FALSE = "false";
		/**
		 * 需资源审核
		 */
		private String resourceConfirm;
		/**
		 * 需重播.
		 */
		private String redail;
		/**
		 * 销售产品类型.
		 */
		private String productType;
		/**
		 * 订单类型，多个订单类型使用逗号分隔.
		 */
		private String[] orderType;
		
		/**
		 * 是否需要发票,
		 * 取值为OrderContent.NEED_INVOICE_TRUE,OrderContent.NEED_INVOICE_FALSE
		 */
		private String needInvoice;
		

		/**
		 * 游玩人姓名.
		 */
		private String visitName;
		/**
		 * 游玩人手机号码.
		 */
		private String visitMobile;
		/**
		 * 联系人姓名.
		 */
		private String contactName;
		/**
		 * 联系人手机号码.
		 */
		private String contactMobile;
		/**
		 * 下单人登录名.
		 */
		private String userName;

		/**
		 * 下单人userId
		 */
		private String userId;
		/**
		 * 支付目标.
		 */
		private Constant.PAYMENT_TARGET paymentTarget;
		/**
		 * 景区名称.
		 */
		private String placeName;
		/**
		 * 销售产品子类型.
		 */
		private Constant.SUB_PRODUCT_TYPE[] subProductType;
		/**
		 * 下单人email.
		 */
		private String email;
		/**
		 * 下单人手机.
		 */
		private String mobile;
		/**
		 * 下单人会员卡号
		 */
		private String membershipCard;
		/**
		 * 销售产品名称.
		 */
		private String productName;
		/**
		 * 销售产品经理名称.
		 */
		private String prodProductManagerName;
		
		

		/**
		 * 采购产品名称.
		 */
		private String metaProductName;
		
		/**
		 * 是否供应商资源确认
		 */
		private Boolean supplierFlag;
		/**
		 * 供应商名称.
		 */
		private String supplierName;
		/**
		 * 采购产品类型.
		 */
		private String metaProductType;
		/**
		 * 是否返现.
		 */
		private String isCashRefund;
		/**
		 * 订单渠道，多个渠道使用逗号分隔.
		 */
		private Constant.CHANNEL[] channel;
		/**
		 * 订单领单操作者.
		 */
		private String takenOperator;
		/**
		 * 分单操作者.
		 */
		private String assignOperator;
		/**
		 * 分单操作者.
		 */
		private String[] assignOperators;
		/**
         * 图号
         */
		private String travelCode;
		
		/**
		 * 分公司
		 */
		private String filialeName;
		/**
		 * @return the filialeName
		 */
		public String getFilialeName() {
			return filialeName;
		}
		/**
		 * @param filialeName the filialeName to set
		 */
		public void setFilialeName(String filialeName) {
			this.filialeName = filialeName;
		}
		/**
		 * 获取团号.
		 * @return
		 */
		public String getTravelCode() {
			return travelCode;
		}
        /**
         * 设置团号.
         * @param travelCode
         */
		public void setTravelCode(String travelCode) {
			this.travelCode = travelCode;
		}

		/**
		 * getMetaProductType.
		 * 
		 * @return 采购产品类型
		 */
		public String getMetaProductType() {
			return metaProductType;
		}

		/**
		 * setMetaProductType.
		 * 
		 * @param metaProductType
		 *            采购产品类型
		 */
		public void setMetaProductType(final String metaProductType) {
			this.metaProductType = metaProductType;
		}
		
		/**
		 * @return the needInvoice
		 */
		public String getNeedInvoice() {
			return needInvoice;
		}
		/**
		 * @param needInvoice the needInvoice to set
		 */
		public void setNeedInvoice(String needInvoice) {
			this.needInvoice = needInvoice;
		}

		public String getResourceConfirm() {
			return resourceConfirm;
		}

		public void setResourceConfirm(String resourceConfirm) {
			this.resourceConfirm = resourceConfirm;
		}

		/**
		 * getRedail.
		 * 
		 * @return 需重拨
		 */
		public String getRedail() {
			return redail;
		}

		/**
		 * setRedail.
		 * 
		 * @param redail
		 *            需重拨
		 */
		public void setRedail(final String redail) {
			this.redail = redail;
		}

		/**
		 * getProductType.
		 * 
		 * @return 销售产品类型
		 */
		public String getProductType() {
			return productType;
		}

		/**
		 * setProductType.
		 * 
		 * @param productType
		 *            销售产品类型
		 */
		public void setProductType(final String productType) {
			this.productType = productType;
		}

		/**
		 * getOrderType.
		 * 
		 * @return 订单类型，多个订单类型使用逗号分隔
		 */
		public String[] getOrderType() {
			return orderType;
		}

		/**
		 * setOrderType.
		 * 
		 * @param orderType
		 *            订单类型，多个订单类型使用逗号分隔
		 */
		public void setOrderType(final String orderType) {
			this.orderType = orderType.split(",");
		}
		/**
		 * setOrderType.
		 * @param orderType
		 */
		public void setOrderType(final String[] orderType) {
			this.orderType = orderType;
		}

		/**
		 * getVisitName.
		 * 
		 * @return 游玩人姓名
		 */
		public String getVisitName() {
			return visitName;
		}

		/**
		 * setVisitName.
		 * 
		 * @param visitName
		 *            游玩人姓名
		 */
		public void setVisitName(final String visitName) {
			this.visitName = visitName;
		}

		/**
		 * getVisitMobile.
		 * 
		 * @return 游玩人手机号码
		 */
		public String getVisitMobile() {
			return visitMobile;
		}

		/**
		 * setVisitMobile.
		 * 
		 * @param visitMobile
		 *            游玩人手机号码
		 */
		public void setVisitMobile(final String visitMobile) {
			this.visitMobile = visitMobile;
		}

		/**
		 * getContactName.
		 * 
		 * @return 联系人姓名
		 */
		public String getContactName() {
			return contactName;
		}

		/**
		 * setContactName.
		 * 
		 * @param contactName
		 *            联系人姓名
		 */
		public void setContactName(final String contactName) {
			this.contactName = contactName;
		}

		/**
		 * getContactMobile.
		 * 
		 * @return 联系人手机号码
		 */
		public String getContactMobile() {
			return contactMobile;
		}

		/**
		 * setContactMobile.
		 * 
		 * @param contactMobile
		 *            联系人手机号码
		 */
		public void setContactMobile(final String contactMobile) {
			this.contactMobile = contactMobile;
		}

		/**
		 * getUserName.
		 * 
		 * @return 下单人登录名
		 */
		public String getUserName() {
			return userName;
		}

		/**
		 * setUserName.
		 * 
		 * @param userName
		 *            下单人登录名
		 */
		public void setUserName(final String userName) {
			this.userName = userName;
		}

		/**
		 * getUserId
		 * 
		 * @return 下单人标识
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId
		 * 
		 * @param userId
		 *            下单人标识
		 */
		public void setUserId(String userId) {
			this.userId = userId;
		}

		/**
		 * getPaymentTarget.
		 * 
		 * @return 支付目标
		 */
		public Constant.PAYMENT_TARGET getPaymentTarget() {
			return paymentTarget;
		}

		/**
		 * setPaymentTarget.
		 * 
		 * @param paymentTarget
		 *            支付目标
		 */
		public void setPaymentTarget(final String paymentTarget) {
			if (UtilityTool.isValid(paymentTarget)) {
				this.paymentTarget = Enum.valueOf(Constant.PAYMENT_TARGET.class,
						paymentTarget);
			}
		}

		/**
		 * 景区名称.
		 * 
		 * @return 景区名称
		 */
		public String getPlaceName() {
			return placeName;
		}

		/**
		 * setPlaceName.
		 * 
		 * @param placeName
		 *            景区名称
		 */
		public void setPlaceName(final String placeName) {
			this.placeName = placeName;
		}

		/**
		 * getSubProductType.
		 * 
		 * @return 销售产品子类型
		 */
		public Constant.SUB_PRODUCT_TYPE[] getSubProductType() {
			return subProductType;
		}

		/**
		 * setSubProductType.
		 * 
		 * @param subProductType
		 *            销售产品子类型
		 */
		public void setSubProductType(final String subProductType) {
			final String[] array = subProductType.split(",");
			final Constant.SUB_PRODUCT_TYPE[] enumArray = new Constant.SUB_PRODUCT_TYPE[array.length];
			for (int i = 0; i < array.length; i++) {
				if (UtilityTool.isValid(array[i])) {
					enumArray[i] = Enum.valueOf(Constant.SUB_PRODUCT_TYPE.class,
							array[i]);
				}
			}
			this.subProductType = enumArray;
		}

		/**
		 * getEmail.
		 * 
		 * @return 下单人email
		 */
		public String getEmail() {
			return email;
		}

		/**
		 * setEmail.
		 * 
		 * @param email
		 *            下单人email
		 */
		public void setEmail(final String email) {
			this.email = email;
		}

		/**
		 * getMobile.
		 * 
		 * @return 下单人手机
		 */
		public String getMobile() {
			return mobile;
		}

		/**
		 * setMobile.
		 * 
		 * @param mobile
		 *            下单人手机
		 */
		public void setMobile(final String mobile) {
			this.mobile = mobile;
		}

		/**
		 * getMembershipCard
		 * 
		 * @return 下单人会员卡号
		 */
		public String getMembershipCard() {
			return membershipCard;
		}

		/**
		 * setMembershipCard
		 * 
		 * @param membershipCard
		 *            下单人会员卡号
		 */
		public void setMembershipCard(String membershipCard) {
			this.membershipCard = membershipCard;
		}

		/**
		 * getProductName.
		 * 
		 * @return 销售产品名称
		 */
		public String getProductName() {
			return productName;
		}

		/**
		 * setProductName.
		 * 
		 * @param productName
		 *            销售产品名称
		 */
		public void setProductName(final String productName) {
			this.productName = productName;
		}
		 
		/**
		 * 获取销售产品经理名称.
		 * @return 返回销售产品经理名称.
		 */
		public String getProdProductManagerName() {
			return prodProductManagerName;
		}
		/**
		 * 设置销售产品经理名称.
		 * @param prodProductManagerName
		 */
		public void setProdProductManagerName(String prodProductManagerName) {
			this.prodProductManagerName = prodProductManagerName;
		}
		/**
		 * getMetaProductName.
		 * 
		 * @return 采购产品名称
		 */
		public String getMetaProductName() {
			return metaProductName;
		}

		/**
		 * setMetaProductName.
		 * 
		 * @param metaProductName
		 *            采购产品名称
		 */
		public void setMetaProductName(final String metaProductName) {
			this.metaProductName = metaProductName;
		}

		/**
		 * getSupplierName.
		 * 
		 * @return 供应商名称
		 */
		public String getSupplierName() {
			return supplierName;
		}

		/**
		 * .setSupplierName
		 * 
		 * @param supplierName
		 *            供应商名称
		 */
		public void setSupplierName(final String supplierName) {
			this.supplierName = supplierName;
		}

		/**
		 * getIsCashRefund.
		 * 
		 * @return 是否返现
		 */
		public String getIsCashRefund() {
			return isCashRefund;
		}

		/**
		 * setIsCashRefund.
		 * 
		 * @param isCashRefund
		 *            是否返现
		 */
		public void setIsCashRefund(final String isCashRefund) {
			this.isCashRefund = isCashRefund;
		}

		/**
		 * getChannel.
		 * 
		 * @return 订单渠道，多个渠道使用逗号分隔
		 */
		public Constant.CHANNEL[] getChannel() {
			return channel;
		}

		/**
		 * setChannel.
		 * 
		 * @param channel
		 *            订单渠道，多个渠道使用逗号分隔
		 */
		public void setChannel(final String channel) {
			final String[] array = channel.split(",");
			final Constant.CHANNEL[] enumArray = new Constant.CHANNEL[array.length];
			for (int i = 0; i < array.length; i++) {
				if (UtilityTool.isValid(array[i])) {
					enumArray[i] = Enum.valueOf(Constant.CHANNEL.class, array[i]);
				}
			}
			this.channel = enumArray;
		}

		/**
		 * 
		 * @return 订单领单操作者
		 */
		public String getTakenOperator() {
			return takenOperator;
		}

		/**
		 * 
		 * @param takenOperator
		 *            订单领单操作者
		 */
		public void setTakenOperator(String takenOperator) {
			this.takenOperator = takenOperator;
		}

		public String getAssignOperator() {
			return assignOperator;
		}

		public void setAssignOperator(String assignOperator) {
			this.assignOperator = assignOperator;
		}
		public Boolean getSupplierFlag() {
			return supplierFlag;
		}
		public void setSupplierFlag(Boolean supplierFlag) {
			this.supplierFlag = supplierFlag;
		}
		public String[] getAssignOperators() {
			return assignOperators;
		}
		public void setAssignOperators(String[] assignOperators) {
			this.assignOperators = assignOperators;
		}

	}

	/**
	 * 与订单时间范围有关的参数.
	 */
	public static final class OrderTimeRange implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -8684933478020296770L;
		/**
		 * 起始订单创建时间（包括）.
		 */
		private Date createTimeStart;
		/**
		 * 结束订单创建时间（不包括）.
		 */
		private Date createTimeEnd;
		/**
		 * 订单起始游玩时间（包括）.
		 */
		private Date ordOrderVisitTimeStart;
		/**
		 * 订单结束游玩时间（包括）.
		 */
		private Date ordOrderVisitTimeEnd;
		/**
		 * 销售产品订单子项起始游玩时间（包括）.
		 */
		private Date ordOrderItemProdVisitTimeStart;
		/**
		 * 销售产品订单子项结束游玩时间（不包括）.
		 */
		private Date ordOrderItemProdVisitTimeEnd;
		/**
		 * 起始支付时间（包括）.
		 */
		private Date payTimeStart;
		/**
		 * 结束支付时间（不包括）.
		 */
		private Date payTimeEnd;

		/**
		 * 首处理开始时间
		 */
		private Date dealTimeStart;
		/**
		 * 首处理结束时间
		 */
		private Date dealTimeEnd;
		
		/**
		 * 发票创建开始时间. 
		 */
		private Date createInvoiceStart;
		
		/**
		 * 发票创建结束时间
		 */
		private Date createInvoiceEnd;
		
		/**
		 * 发票开票开始时间
		 */
		private Date billInvoiceStart;
		/**
		 * 发票开票结束时间
		 */
		private Date billInvoiceEnd;

		
		/**
		 * @return the billInvoiceStart
		 */
		public Date getBillInvoiceStart() {
			return billInvoiceStart;
		}

		/**
		 * @param billInvoiceStart the billInvoiceStart to set
		 */
		public void setBillInvoiceStart(Date billInvoiceStart) {
			this.billInvoiceStart = billInvoiceStart;
		}

		/**
		 * @return the billInvoiceEnd
		 */
		public Date getBillInvoiceEnd() {
			return billInvoiceEnd;
		}

		/**
		 * @param billInvoiceEnd the billInvoiceEnd to set
		 */
		public void setBillInvoiceEnd(Date billInvoiceEnd) {
			this.billInvoiceEnd = billInvoiceEnd;
		}

		/**
		 * getCreateTimeStart.
		 * 
		 * @return 起始订单创建时间（包括）
		 */
		public Date getCreateTimeStart() {
			return createTimeStart;
		}

		/**
		 * setCreateTimeStart.
		 * 
		 * @param createTimeStart
		 *            起始订单创建时间（包括）
		 */
		public void setCreateTimeStart(final Date createTimeStart) {
			this.createTimeStart = createTimeStart;
		}

		/**
		 * getCreateTimeEnd.
		 * 
		 * @return 结束订单创建时间（不包括）
		 */
		public Date getCreateTimeEnd() {
			return createTimeEnd;
		}

		/**
		 * setCreateTimeEnd.
		 * 
		 * @param createTimeEnd
		 *            结束订单创建时间（不包括）
		 */
		public void setCreateTimeEnd(final Date createTimeEnd) {
			this.createTimeEnd = createTimeEnd;
		}

		/**
		 * getOrdOrderVisitTimeStart.
		 * 
		 * @return 订单起始游玩时间（包括）
		 */
		public Date getOrdOrderVisitTimeStart() {
			return ordOrderVisitTimeStart;
		}

		/**
		 * setOrdOrderVisitTimeStart.
		 * 
		 * @param ordOrderVisitTimeStart
		 *            订单起始游玩时间（包括）
		 */
		public void setOrdOrderVisitTimeStart(final Date ordOrderVisitTimeStart) {
			this.ordOrderVisitTimeStart = ordOrderVisitTimeStart;
		}

		/**
		 * getOrdOrderVisitTimeEnd.
		 * 
		 * @return 订单结束游玩时间（不包括）
		 */
		public Date getOrdOrderVisitTimeEnd() {
			return ordOrderVisitTimeEnd;
		}

		/**
		 * setOrdOrderVisitTimeEnd.
		 * 
		 * @param ordOrderVisitTimeEnd
		 *            订单结束游玩时间（不包括）
		 */
		public void setOrdOrderVisitTimeEnd(final Date ordOrderVisitTimeEnd) {
			this.ordOrderVisitTimeEnd = ordOrderVisitTimeEnd;
		}

		/**
		 * getOrdOrderItemProdVisitTimeStart.
		 * 
		 * @return 销售产品订单子项起始游玩时间（包括）
		 */
		public Date getOrdOrderItemProdVisitTimeStart() {
			return ordOrderItemProdVisitTimeStart;
		}

		/**
		 * setOrdOrderItemProdVisitTimeStart..
		 * 
		 * @param ordOrderItemProdVisitTimeStart
		 *            销售产品订单子项起始游玩时间（包括）
		 */
		public void setOrdOrderItemProdVisitTimeStart(
				final Date ordOrderItemProdVisitTimeStart) {
			this.ordOrderItemProdVisitTimeStart = ordOrderItemProdVisitTimeStart;
		}

		/**
		 * getOrdOrderItemProdVisitTimeEnd.
		 * 
		 * @return 销售产品订单子项结束游玩时间（不包括）
		 */
		public Date getOrdOrderItemProdVisitTimeEnd() {
			return ordOrderItemProdVisitTimeEnd;
		}

		/**
		 * setOrdOrderItemProdVisitTimeEnd.
		 * 
		 * @param ordOrderItemProdVisitTimeEnd
		 *            销售产品订单子项结束游玩时间（不包括）
		 */
		public void setOrdOrderItemProdVisitTimeEnd(
				final Date ordOrderItemProdVisitTimeEnd) {
			this.ordOrderItemProdVisitTimeEnd = ordOrderItemProdVisitTimeEnd;
		}

		/**
		 * getPayTimeStart.
		 * 
		 * @return 起始支付时间（包括）
		 */
		public Date getPayTimeStart() {
			return payTimeStart;
		}

		/**
		 * setPayTimeStart.
		 * 
		 * @param payTimeStart
		 *            起始支付时间（包括）
		 */
		public void setPayTimeStart(final Date payTimeStart) {
			this.payTimeStart = payTimeStart;
		}

		/**
		 * getPayTimeEnd.
		 * 
		 * @return 结束支付时间（不包括）
		 */
		public Date getPayTimeEnd() {
			return payTimeEnd;
		}

		/**
		 * setPayTimeEnd.
		 * 
		 * @param payTimeEnd
		 *            结束支付时间（不包括）
		 */
		public void setPayTimeEnd(final Date payTimeEnd) {
			this.payTimeEnd = payTimeEnd;
		}

		public Date getDealTimeStart() {
			return dealTimeStart;
		}

		public void setDealTimeStart(Date dealTimeStart) {
			this.dealTimeStart = dealTimeStart;
		}

		public Date getDealTimeEnd() {
			return dealTimeEnd;
		}

		public void setDealTimeEnd(Date dealTimeEnd) {
			this.dealTimeEnd = dealTimeEnd;
		}

		/**
		 * @return the createInvoiceStart
		 */
		public Date getCreateInvoiceStart() {
			return createInvoiceStart;
		}

		/**
		 * @param createInvoiceStart the createInvoiceStart to set
		 */
		public void setCreateInvoiceStart(Date createInvoiceStart) {
			this.createInvoiceStart = createInvoiceStart;
		}

		/**
		 * @return the createInvoiceEnd
		 */
		public Date getCreateInvoiceEnd() {
			return createInvoiceEnd;
		}

		/**
		 * @param createInvoiceEnd the createInvoiceEnd to set
		 */
		public void setCreateInvoiceEnd(Date createInvoiceEnd) {
			this.createInvoiceEnd = createInvoiceEnd;
		}
		
		
	}

	/**
	 * 与订单相关ID有关的参数.
	 */
	public static final class OrderIdentity implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2808322228748134732L;
		/**
		 * 订单ID.
		 */
		private Long orderId;
		/**
		 * 订单ID列表
		 */
		private List<Long> orderIds;
		/**
		 * 销售产品ID.
		 */
		private Long productid;
		/**
		 * 销售产品ID集合
		 */
		private List<Long> productIds;
		/**
		 * 采购产品ID.
		 */
		private Long metaProductid;
		/**
		 * 供应商ID.
		 */
		private Long supplierId;
		/**
		 * 订单领单人ID.
		 */
		private String operatorId;
		/**
		 * 采购产品订单子项领单人ID.
		 */
		private String itemOperatorId;
		/**
		 * 用户ID.
		 */
		private String userId;
		/**
		 * 订单所属组织id.
		 */
		private Long orgId;
		
		/**
		 * getOrgId.
		 * @return 订单所属组织id.
		 */
		public Long getOrgId() {
			return orgId;
		}
		/**
		 * setOrgId.
		 * @param orgId 订单所属组织id.
		 */
		public void setOrgId(final Long orgId) {
			this.orgId = orgId;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}

		/**
		 * getProductid.
		 * 
		 * @return 销售产品ID
		 */
		public Long getProductid() {
			return productid;
		}

		/**
		 * setProductid.
		 * 
		 * @param productid
		 *            销售产品ID
		 */
		public void setProductid(final Long productid) {
			this.productid = productid;
		}
		public List<Long> getProductIds() {
			return productIds;
		}
		public void setProductIds(List<Long> productIds) {
			this.productIds = productIds;
		}
		/**
		 * getMetaProductid.
		 * 
		 * @return 采购产品ID
		 */
		public Long getMetaProductid() {
			return metaProductid;
		}

		/**
		 * setMetaProductid.
		 * 
		 * @param metaProductid
		 *            采购产品ID
		 */
		public void setMetaProductid(final Long metaProductid) {
			this.metaProductid = metaProductid;
		}

		/**
		 * getSupplierId.
		 * 
		 * @return 供应商ID
		 */
		public Long getSupplierId() {
			return supplierId;
		}

		/**
		 * setSupplierId.
		 * 
		 * @param supplierId
		 *            供应商ID
		 */
		public void setSupplierId(final Long supplierId) {
			this.supplierId = supplierId;
		}

		/**
		 * getOperatorId.
		 * 
		 * @return 订单领单人ID.
		 */
		public String getOperatorId() {
			return operatorId;
		}

		/**
		 * setOperatorId.
		 * 
		 * @param operatorId
		 *            订单领单人ID.
		 */
		public void setOperatorId(final String operatorId) {
			this.operatorId = operatorId;
		}

		/**
		 * getItemOperatorId.
		 * 
		 * @return 采购产品订单子项领单人ID
		 */
		public String getItemOperatorId() {
			return itemOperatorId;
		}

		/**
		 * setItemOperatorId.
		 * 
		 * @param itemOperatorId
		 *            采购产品订单子项领单人ID
		 */
		public void setItemOperatorId(final String itemOperatorId) {
			this.itemOperatorId = itemOperatorId;
		}

		/**
		 * getUserId.
		 * 
		 * @return 用户ID
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId.
		 * 
		 * @param userId
		 *            用户ID
		 */
		public void setUserId(final String userId) {
			this.userId = userId;
		}
		public List<Long> getOrderIds() {
			return orderIds;
		}
		public void setOrderIds(List<Long> orderIds) {
			this.orderIds = orderIds;
		}
	}

	/**
	 * 与订单分页有关的参数.
	 */
	public static final class PageIndex implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2981815553154764836L;
		/**
		 * 起始索引（包括）.
		 */
		private Integer beginIndex = 1;
		/**
		 * 结束索引（包括）.
		 */
		private Integer endIndex = 1;

		/**
		 * getBeginIndex.
		 * 
		 * @return 起始索引（包括）
		 */
		public Integer getBeginIndex() {
			return beginIndex;
		}

		/**
		 * setBeginIndex.
		 * 
		 * @param beginIndex
		 *            起始索引（包括）
		 */
		public void setBeginIndex(final Integer beginIndex) {
			this.beginIndex = beginIndex;
		}

		/**
		 * getEndIndex.
		 * 
		 * @return 结束索引（包括）
		 */
		public Integer getEndIndex() {
			return endIndex;
		}

		/**
		 * setEndIndex.
		 * 
		 * @param endIndex
		 *            结束索引（包括）
		 */
		public void setEndIndex(final Integer endIndex) {
			this.endIndex = endIndex;
		}
	}

	/**
	 * 表示订单排除条件.
	 */
	public static final class ExcludedContent implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = -2513997986652128995L;
		/**
		 * 需要清空list.
		 */
		public static final boolean NEED_CLEAR_LIST_TRUE = true;
		/**
		 * 不需要清空list.
		 */
		public static final boolean NEED_CLEAR_LIST_FALSE = false;
		/**
		 * 订单状态.
		 */
		private Constant.ORDER_STATUS orderStatus;
		
		/**
		 * 支付状态
		 */
		private Constant.PAYMENT_STATUS paymentStatus;
		
		/**
		 * 订单审核状态.
		 */
		private List<Constant.ORDER_APPROVE_STATUS> orderApproveStatusList = new ArrayList<Constant.ORDER_APPROVE_STATUS>();
		/**
		 * 是否在结算队列中.
		 */
		private boolean inSettlementQueue = false;
		/**
		 * 支付目标.
		 */
		private Constant.PAYMENT_TARGET paymentTarget;
		 
		private List<Long> orderMetaIds = new ArrayList<Long>();
		
		 
		
		private boolean unPayAndNeedPrePay = false;
		
		public boolean isUnPayAndNeedPrePay() {
			return unPayAndNeedPrePay;
		}

		public void setUnPayAndNeedPrePay(boolean unPayAndNeedPrePay) {
			this.unPayAndNeedPrePay = unPayAndNeedPrePay;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 订单状态
		 */
		public Constant.ORDER_STATUS getOrderStatus() {
			return orderStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            订单状态
		 */
		public void setOrderStatus(final String orderStatus) {
			if (UtilityTool.isValid(orderStatus)) {
				this.orderStatus = Enum
						.valueOf(Constant.ORDER_STATUS.class, orderStatus);
			}
		}

		/**
		 * getOrderApproveStatus.
		 * 
		 * @return 订单审核状态
		 */
		public List<Constant.ORDER_APPROVE_STATUS> getOrderApproveStatus() {
			return orderApproveStatusList;
		}

		/**
		 * 设置订单审核状态,此方法默认清空订单审核状态列表.
		 * 是setOrderApproveStatus(orderApproveStatus,ExcludedContent.NEED_CLEAR_LIST_TRUE)的便利方法.
		 * 
		 * @param orderApproveStatus
		 *            订单审核状态
		 */
		public void setOrderApproveStatus(final String orderApproveStatus) {
			this.setOrderApproveStatus(orderApproveStatus,ExcludedContent.NEED_CLEAR_LIST_TRUE);
		}
		/**
		 * 设置订单审核状态.<br/>如果传入的参数orderApproveStatus不是有效的ORDER_APPROVE_STATUS状态之一,则忽略此参数.
		 * @param orderApproveStatus 订单审核状态.
		 * @param clearList 是否清空订单审核状态list列表, 取值为ExcludedContent.NEED_CLEAR_LIST_TRUE,ExcludedContent.NEED_CLEAR_LIST_FALSE其中之一.
		 */
		public void setOrderApproveStatus(final String orderApproveStatus,boolean clearList) { 
			if (clearList) {
				this.orderApproveStatusList.clear();
			}
			Constant.ORDER_APPROVE_STATUS arg = Enum.valueOf(Constant.ORDER_APPROVE_STATUS.class,
					orderApproveStatus);
			if (arg != null) {
				this.orderApproveStatusList.add(arg);
			}
		}

		/**
		 * getInSettlementQueue.
		 * 
		 * @return 是否在结算队列中
		 */
		public boolean isInSettlementQueue() {
			return inSettlementQueue;
		}

		/**
		 * setInSettlementQueue.
		 * 
		 * @param inSettlementQueue
		 *            是否在结算队列中
		 */
		public void setInSettlementQueue(final boolean inSettlementQueue) {
			this.inSettlementQueue = inSettlementQueue;
		}

		/**
		 * getPaymentTarget.
		 * 
		 * @return 支付目标
		 */
		public Constant.PAYMENT_TARGET getPaymentTarget() {
			return paymentTarget;
		}

		/**
		 * setPaymentTarget.
		 * 
		 * @param paymentTarget
		 *            支付目标
		 */
		public void setPaymentTarget(Constant.PAYMENT_TARGET paymentTarget) {
			this.paymentTarget = paymentTarget;
		}

		/**
		 * @return the paymentStatus
		 */
		public Constant.PAYMENT_STATUS getPaymentStatus() {
			return paymentStatus;
		}

		/**
		 * @param paymentStatus the paymentStatus to set
		 */
		public void setPaymentStatus(Constant.PAYMENT_STATUS paymentStatus) {
			this.paymentStatus = paymentStatus;
		}

		public List<Long> getOrderMetaIds() {
			return orderMetaIds;
		}

		public void setOrderMetaIds(List<Long> orderMetaIds) {
			this.orderMetaIds = orderMetaIds;
		}
		
		
	}

	/**
	 * 发票管理查询条件.
	 */
	public static final class InvoiceRelate implements Serializable {
		/**
		 * 
		 */
		private static final long serialVersionUID = 3124850252958888484L;

		/**
		 * 订单ID.
		 */
		private Long orderId;
		
		/**
		 * 发票ID
		 */
		private Long invoiceId;
		/**
		 * 发票单号.
		 */
		private String invoiceNo;
		/**
		 * 我方结算主题ID.
		 */
		private String companyId;
		/**
		 * 发票人ID.
		 */
		private String userId;
		
		/**
		 * 发货类型
		 */
		private String deliveryType;
		
		/**
		 * 发票状态
		 */
		private String status;
		

		/**
		 * 发票抬头.
		 */
		private String title;
		
		/**
		 * 申请红冲标记
		 */
		private String redFlag;
		
		private String logisticsStatus;
		

		/**
		 * 是否需要读取地址信息
		 */
		private boolean findAddress=false;
		/**
		 * 是否需要读取订单发票关联信息
		 */
		private boolean findOrderRelation=true;
		
		private boolean findOrderInvoiceAmount=false;
		
		/**
		 * 是否需要读取订单信息,这个只有在findOrderRelation为true时有效
		 */
		private boolean findOrder=false;

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		/**
		 * @return the findOrderRelation
		 */
		public boolean isFindOrderRelation() {
			return findOrderRelation;
		}

		/**
		 * @param findOrderRelation the findOrderRelation to set
		 */
		public void setFindOrderRelation(boolean findOrderRelation) {
			this.findOrderRelation = findOrderRelation;
		}

		/**
		 * @return the findAddress
		 */
		public boolean isFindAddress() {
			return findAddress;
		}

		/**
		 * @param findAddress the findAddress to set
		 */
		public void setFindAddress(boolean findAddress) {
			this.findAddress = findAddress;
		}

		/**
		 * @return the deliveryType
		 */
		public String getDeliveryType() {
			return deliveryType;
		}

		/**
		 * @param deliveryType the deliveryType to set
		 */
		public void setDeliveryType(String deliveryType) {
			this.deliveryType = deliveryType;
		}

		/**
		 * @return the status
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * @param status the status to set
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		/**
		 * @return the invoiceId
		 */
		public Long getInvoiceId() {
			return invoiceId;
		}

		/**
		 * @param invoiceId the invoiceId to set
		 */
		public void setInvoiceId(Long invoiceId) {
			this.invoiceId = invoiceId;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}

		/**
		 * getInvoiceNo.
		 * 
		 * @return 发票单号
		 */
		public String getInvoiceNo() {
			return invoiceNo;
		}

		/**
		 * setInvoiceNo.
		 * 
		 * @param invoiceNo
		 *            发票单号
		 */
		public void setInvoiceNo(final String invoiceNo) {
			this.invoiceNo = invoiceNo;
		}

		/**
		 * getCompanyId.
		 * 
		 * @return 我方结算主题ID
		 */
		public String getCompanyId() {
			return companyId;
		}

		/**
		 * setCompanyId.
		 * 
		 * @param companyId
		 *            我方结算主题ID
		 */
		public void setCompanyId(final String companyId) {
			this.companyId = companyId;
		}

		/**
		 * getUserId.
		 * 
		 * @return 订票人ID
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId.
		 * 
		 * @param userId
		 *            订票人ID
		 */
		public void setUserId(final String userId) {
			this.userId = userId;
		}

		public boolean isFindOrder() {
			return findOrderRelation&&findOrder;
		}

		public void setFindOrder(boolean findOrder) {
			this.findOrder = findOrder;
			this.findOrderRelation = true;
		}

		

		public String getRedFlag() {
			return redFlag;
		}

		public void setRedFlag(String redFlag) {
			this.redFlag = redFlag;
		}

		public String getLogisticsStatus() {
			return logisticsStatus;
		}

		public void setLogisticsStatus(String logisticsStatus) {
			this.logisticsStatus = logisticsStatus;
		}

		public boolean isFindOrderInvoiceAmount() {
			return findOrderInvoiceAmount;
		}

		public void setFindOrderInvoiceAmount(boolean findOrderInvoiceAmount) {
			this.findOrderInvoiceAmount = findOrderInvoiceAmount;
		}

	}

	/**
	 * 投诉管理查询条件.
	 */
	public static final class SaleServiceRelate extends UserQuery implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 4896802005289181047L;
		/**
		 * 投诉管理订单ID.
		 */
		private Long saleServiceOrderId;
		/**
		 * 投诉管理下单人登录名.
		 */
		private String saleServiceUserName;
		/**
		 * 投诉管理下单人手机.
		 */
		private String saleServiceMobile;
		/**
		 * 投诉管理申请人登录名.
		 */
		private String saleServiceApplyName;
		/**
		 * 投诉管理订单类型.
		 */
		private String saleServiceOrderType;
		/**
		 * 投诉管理服务类型.
		 */
		private String saleServiceType;
		/**
		 * 起始售后服务创建时间（包括）.
		 */
		private Date saleServiceCreateTimeStart;
		/**
		 * 结束售后服务创建时间（包括）.
		 */
		private Date saleServiceCreateTimeEnd;
		/**
		 * 售后服务状态.
		 */
		private String saleStatus;
		/**
		 * 处理客服
		 * */
		private String takenOperator;
		/**
		 * 分配时间
		 * */
		private Date takenTimeBegin;
		/**
		 * 分配时间
		 * */
		private Date takenTimeEnd;
		
		/**
		 * 业务系统编码
		 */
		private String sysCode;
		
		public Date getTakenTimeBegin() {
			return takenTimeBegin;
		}

		public void setTakenTimeBegin(Date takenTimeBegin) {
			this.takenTimeBegin = takenTimeBegin;
		}

		public Date getTakenTimeEnd() {
			return takenTimeEnd;
		}

		public void setTakenTimeEnd(Date takenTimeEnd) {
			this.takenTimeEnd = takenTimeEnd;
		}

		public String getTakenOperator() {
			return takenOperator;
		}

		public void setTakenOperator(String takenOperator) {
			this.takenOperator = takenOperator;
		}

		/**
		 * getSaleServiceOrderId.
		 * 
		 * @return 投诉管理订单ID
		 */
		public Long getSaleServiceOrderId() {
			return saleServiceOrderId;
		}

		/**
		 * setSaleServiceOrderId.
		 * 
		 * @param saleServiceOrderId
		 *            投诉管理订单ID
		 */
		public void setSaleServiceOrderId(final Long saleServiceOrderId) {
			this.saleServiceOrderId = saleServiceOrderId;
		}

		/**
		 * getSaleServiceUserName.
		 * 
		 * @return 投诉管理下单人登录名
		 */
		public String getSaleServiceUserName() {
			return saleServiceUserName;
		}

		/**
		 * setSaleServiceUserName.
		 * 
		 * @param saleServiceUserName
		 *            投诉管理下单人登录名
		 */
		public void setSaleServiceUserName(final String saleServiceUserName) {
			this.saleServiceUserName = saleServiceUserName;
		}

		/**
		 * getSaleServiceMobile.
		 * 
		 * @return 投诉管理下单人手机
		 */
		public String getSaleServiceMobile() {
			return saleServiceMobile;
		}

		/**
		 * setSaleServiceMobile.
		 * 
		 * @param saleServiceMobile
		 *            投诉管理下单人手机
		 */
		public void setSaleServiceMobile(final String saleServiceMobile) {
			this.saleServiceMobile = saleServiceMobile;
		}

		/**
		 * getSaleServiceApplyName.
		 * 
		 * @return 投诉管理申请人登录名
		 */
		public String getSaleServiceApplyName() {
			return saleServiceApplyName;
		}

		/**
		 * setSaleServiceApplyName.
		 * 
		 * @param saleServiceApplyName
		 *            投诉管理申请人登录名
		 */
		public void setSaleServiceApplyName(final String saleServiceApplyName) {
			this.saleServiceApplyName = saleServiceApplyName;
		}

		/**
		 * getSaleServiceOrderType.
		 * 
		 * @return 投诉管理订单类型
		 */
		public String getSaleServiceOrderType() {
			return saleServiceOrderType;
		}

		/**
		 * setSaleServiceOrderType.
		 * 
		 * @param saleServiceOrderType
		 *            投诉管理订单类型
		 */
		public void setSaleServiceOrderType(final String saleServiceOrderType) {
			this.saleServiceOrderType = saleServiceOrderType;
		}

		/**
		 * getSaleServiceType.
		 * 
		 * @return 投诉管理服务类型
		 */
		public String getSaleServiceType() {
			return saleServiceType;
		}

		/**
		 * setSaleServiceType.
		 * 
		 * @param saleServiceType
		 *            投诉管理服务类型
		 */
		public void setSaleServiceType(final String saleServiceType) {
			this.saleServiceType = saleServiceType;
		}

		/**
		 * getSaleServiceCreateTimeStart.
		 * 
		 * @return 起始售后服务创建时间（包括）
		 */
		public Date getSaleServiceCreateTimeStart() {
			return saleServiceCreateTimeStart;
		}

		/**
		 * setSaleServiceCreateTimeStart.
		 * 
		 * @param saleServiceCreateTimeStart
		 *            起始售后服务创建时间（包括）
		 */
		public void setSaleServiceCreateTimeStart(
				final Date saleServiceCreateTimeStart) {
			this.saleServiceCreateTimeStart = saleServiceCreateTimeStart;
		}

		/**
		 * getSaleServiceCreateTimeEnd.
		 * 
		 * @return 结束售后服务创建时间（包括）
		 */
		public Date getSaleServiceCreateTimeEnd() {
			return saleServiceCreateTimeEnd;
		}

		/**
		 * setSaleServiceCreateTimeEnd.
		 * 
		 * @param saleServiceCreateTimeEnd
		 *            结束售后服务创建时间（包括）
		 */
		public void setSaleServiceCreateTimeEnd(
				final Date saleServiceCreateTimeEnd) {
			this.saleServiceCreateTimeEnd = saleServiceCreateTimeEnd;
		}

		/**
		 * getSaleStatus.
		 * 
		 * @return 售后服务状态
		 */
		public String getSaleStatus() {
			return saleStatus;
		}

		/**
		 * setSaleStatus.
		 * 
		 * @param saleStatus
		 *            售后服务状态
		 */
		public void setSaleStatus(final String saleStatus) {
			this.saleStatus = saleStatus;
		}

		public String getSysCode() {
			return sysCode;
		}

		public void setSysCode(String sysCode) {
			this.sysCode = sysCode;
		}
	}

	/**
	 * 结算管理查询条件.
	 */
	public static final class SettlementQueueRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -2567680323267999734L;
		/**
		 * 结算对象ID.
		 */
		private Long targetId;
		/**
		 * 结算管理采购产品ID.
		 */
		private Long metaProductId;
		/**
		 * 结算管理结算周期.
		 */
		private String settlementPeriod;
		/**
		 * 结算管理支付给驴妈妈.
		 */
		private String pay2Lvmama;
		/**
		 * 结算管理支付给供应商.
		 */
		private String pay2Supplier;

		/**
		 * 游玩开始时间
		 */
		private Date visitTimeStart;
		/**
		 * 游玩结束时间
		 */
		private Date visitTimeEnd;
		
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		public void setVisitTimeStart(Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		public void setVisitTimeEnd(Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * 订单开始支付时间
		 */
		private Date orderPayStartDate;
		/**
		 * 订单结束支付时间
		 */
		private Date orderPayEndDate;

		/**
		 * 我方结算主体
		 */
		private String settlementCommpany;

		/**
		 * 获取我方结算主体.
		 * 
		 * @return 我方结算主体.
		 */
		public String getSettlementCommpany() {
			return settlementCommpany;
		}

		/**
		 * 设置我方结算主体.
		 * 
		 * @param prodProductId
		 *            我方结算主体.
		 */

		public void setSettlementCommpany(String settlementCommpany) {
			this.settlementCommpany = settlementCommpany;
		}

		/**
		 * 获取订单开始支付时间.
		 * 
		 * @return 订单开始支付时间YYYY-MM-DD Hh:mm:ss.
		 */
		public Date getOrderPayStartDate() {
			return orderPayStartDate;
		}

		/**
		 * 设置订单开始支付时间.
		 * 
		 * @return orderPayStartDate 订单开始支付时间YYYY-MM-DD Hh:mm:ss.
		 */
		public void setOrderPayStartDate(Date orderPayStartDate) {
			this.orderPayStartDate = orderPayStartDate;
		}

		/**
		 * 获取订单结束支付时间.
		 * 
		 * @return 订单开始结束时间YYYY-MM-DD Hh:mm:ss.
		 */
		public Date getOrderPayEndDate() {
			return orderPayEndDate;
		}

		/**
		 * 设置订单结束支付时间.
		 * 
		 * @return orderPayEndDate 订单开始结束时间YYYY-MM-DD Hh:mm:ss.
		 */
		public void setOrderPayEndDate(Date orderPayEndDate) {
			this.orderPayEndDate = orderPayEndDate;
		}

		/**
		 * getTargetId.
		 * 
		 * @return serialVersionUID 结算对象ID
		 */
		public Long getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            结算对象ID
		 */
		public void setTargetId(final Long targetId) {
			this.targetId = targetId;
		}

		/**
		 * getMetaProductId.
		 * 
		 * @return 结算管理采购产品ID
		 */
		public Long getMetaProductId() {
			return metaProductId;
		}

		/**
		 * setMetaProductId.
		 * 
		 * @param metaProductId
		 *            结算管理采购产品ID
		 */
		public void setMetaProductId(final Long metaProductId) {
			this.metaProductId = metaProductId;
		}

		/**
		 * getSettlementPeriod.
		 * 
		 * @return 结算管理结算周期
		 */
		public String getSettlementPeriod() {
			return settlementPeriod;
		}

		/**
		 * setSettlementPeriod.
		 * 
		 * @param settlementPeriod
		 *            结算管理结算周期
		 */
		public void setSettlementPeriod(final String settlementPeriod) {
			this.settlementPeriod = settlementPeriod;
		}

		/**
		 * getPay2Lvmama.
		 * 
		 * @return 结算管理支付给驴妈妈
		 */
		public String getPay2Lvmama() {
			return pay2Lvmama;
		}

		/**
		 * setPay2Lvmama.
		 * 
		 * @param pay2Lvmama
		 *            结算管理支付给驴妈妈
		 */
		public void setPay2Lvmama(final String pay2Lvmama) {
			this.pay2Lvmama = pay2Lvmama;
		}

		/**
		 * getPay2Supplier.
		 * 
		 * @return 结算管理支付给供应商
		 */
		public String getPay2Supplier() {
			return pay2Supplier;
		}

		/**
		 * setPay2Supplier.
		 * 
		 * @param pay2Supplier
		 *            结算管理支付给供应商
		 */
		public void setPay2Supplier(final String pay2Supplier) {
			this.pay2Supplier = pay2Supplier;
		}

	}

	/**
	 * 结算管理子项查询条件.
	 */
	public static final class SettlementItemRelate implements Serializable {
		
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1991490531279017221L;
		//add by lijp 20111103
		private boolean deleted;
		
		public boolean isDeleted() {
			return deleted;
		}
		/**
		 * 设置是否查询待结算队列项(ORD_SETTLEMENT_QUEUE_ITEM)中"已筛选"状态(DELETED=TRUE)的记录.
		 * @param deleted 值为true时查询"已筛选"状态,值为false时忽略此查询条件.
		 */
		public void setDeleted(boolean deleted) {
			this.deleted = deleted;
		}

		/**
		 * 采购产品ID.
		 */
		private Long metaProductId;
		/**
		 * 结算对象ID.
		 */
		private Long targetId;
		/**
		 * 起始游玩时间（包括）.
		 */
		private Date visitTimeStart;
		/**
		 * 结束游玩时间（包括）.
		 */
		private Date visitTimeEnd;
		/**
		 * 订单ID.
		 */
		private Long orderId;
		/**
		 * 是否是退款订单.
		 * 
		 * <pre>
		 * <code>true</code>代表退款订单，<code>false</code>代表所有订单
		 * </pre>
		 */
		private boolean includeRefundmentOrder = false;

		/**
		 * getMetaProductId.
		 * 
		 * @return 采购产品ID
		 */
		public Long getMetaProductId() {
			return metaProductId;
		}

		/**
		 * setMetaProductId.
		 * 
		 * @param metaProductId
		 *            采购产品ID
		 */
		public void setMetaProductId(final Long metaProductId) {
			this.metaProductId = metaProductId;
		}

		/**
		 * getTargetId.
		 * 
		 * @return 结算对象ID
		 */
		public Long getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            结算对象ID
		 */
		public void setTargetId(final Long targetId) {
			this.targetId = targetId;
		}

		/**
		 * getVisitTimeStart.
		 * 
		 * @return 起始游玩时间（包括）
		 */
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		/**
		 * setVisitTimeStart.
		 * 
		 * @param visitTimeStart
		 *            起始游玩时间（包括）
		 */
		public void setVisitTimeStart(final Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		/**
		 * getVisitTimeEnd.
		 * 
		 * @return 结束游玩时间（包括）
		 */
		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		/**
		 * setVisitTimeEnd.
		 * 
		 * @param visitTimeEnd
		 *            结束游玩时间（包括）
		 */
		public void setVisitTimeEnd(final Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}

		/**
		 * getIncludeRefundmentOrder.
		 * 
		 * @return <pre>
		 * <code>true</code>代表退款订单，<code>false</code>代表所有订单
		 * </pre>
		 */
		public boolean getIncludeRefundmentOrder() {
			return includeRefundmentOrder;
		}

		/**
		 * setIncludeRefundmentOrder.
		 * 
		 * @param includeRefundmentOrder
		 *            <pre>
		 * <code>true</code>代表退款订单，<code>false</code>代表所有订单
		 * </pre>
		 */
		public void setIncludeRefundmentOrder(
				final boolean includeRefundmentOrder) {
			this.includeRefundmentOrder = includeRefundmentOrder;
		}
	}

	/**
	 * 结算单查询条件.
	 */
	public static final class OrdSettlementRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1314768988456027385L;
		/**
		 * 结算对象ID.
		 */
		private Long targetId;
		/**
		 * 采购产品ID.
		 */
		private Long metaProductId;
		/**
		 * 结算单ID.
		 */
		private Long settlementId;
		/**
		 * 起始创建时间（包括）.
		 */
		private Date createTimeStart;
		/**
		 * 结束创建时间（包括）.
		 */
		private Date createTimeEnd;

		/**
		 * 结算状态
		 */
		//mod by lijp 20111031
		private String settlementStatus;
		
		public String getSettlementStatus() {
			return settlementStatus;
		}

		public void setSettlementStatus(String settlementStatus) {
			this.settlementStatus = settlementStatus;
		}

		/**
		 * getTargetId.
		 * 
		 * @return 结算对象ID
		 */
		public Long getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            结算对象ID
		 */
		public void setTargetId(final Long targetId) {
			this.targetId = targetId;
		}

		/**
		 * getMetaProductId.
		 * 
		 * @return 采购产品ID
		 */
		public Long getMetaProductId() {
			return metaProductId;
		}

		/**
		 * setMetaProductId.
		 * 
		 * @param metaProductId
		 *            采购产品ID
		 */
		public void setMetaProductId(final Long metaProductId) {
			this.metaProductId = metaProductId;
		}

		/**
		 * getSettlementId.
		 * 
		 * @return 结算单ID
		 */
		public Long getSettlementId() {
			return settlementId;
		}

		/**
		 * setSettlementId.
		 * 
		 * @param settlementId
		 *            结算单ID
		 */
		public void setSettlementId(final Long settlementId) {
			this.settlementId = settlementId;
		}

		/**
		 * getCreateTimeStart.
		 * 
		 * @return 起始创建时间（包括）
		 */
		public Date getCreateTimeStart() {
			return createTimeStart;
		}

		/**
		 * setCreateTimeStart.
		 * 
		 * @param createTimeStart
		 *            起始创建时间（包括）
		 */
		public void setCreateTimeStart(final Date createTimeStart) {
			this.createTimeStart = createTimeStart;
		}

		/**
		 * getCreateTimeEnd.
		 * 
		 * @return 结束创建时间（包括）
		 */
		public Date getCreateTimeEnd() {
			return createTimeEnd;
		}

		/**
		 * setCreateTimeEnd.
		 * 
		 * @param createTimeEnd
		 *            结束创建时间（包括）
		 */
		public void setCreateTimeEnd(final Date createTimeEnd) {
			this.createTimeEnd = createTimeEnd;
		}
	}
	
	/**
	 * 结算管理完成的子项查询条件.
	 */
	public static final class FinishSettlementItemRelate implements
			Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5994573554395798507L;
		/**
		 * 子结算单ID.
		 */
		private Long subSettlementId;
		/**
		 * 结算单ID.
		 */
		private Long settlementId;
		/**
		 * 起始游玩时间（包括）.
		 */
		private Date visitTimeStart;
		/**
		 * 结束游玩时间（包括）.
		 */
		private Date visitTimeEnd;
		/**
		 * 订单ID.
		 */
		private Long orderId;

		/**
		 * getSubSettlementId.
		 * 
		 * @return 子结算单ID
		 */
		public Long getSubSettlementId() {
			return subSettlementId;
		}

		/**
		 * setSubSettlementId.
		 * 
		 * @param subSettlementId
		 *            子结算单ID
		 */
		public void setSubSettlementId(final Long subSettlementId) {
			this.subSettlementId = subSettlementId;
		}

		/**
		 * getSettlementId.
		 * 
		 * @return 结算单ID
		 */
		public Long getSettlementId() {
			return settlementId;
		}

		/**
		 * setSettlementId.
		 * 
		 * @param settlementId
		 *            结算单ID
		 */
		public void setSettlementId(final Long settlementId) {
			this.settlementId = settlementId;
		}

		/**
		 * getVisitTimeStart.
		 * 
		 * @return 起始游玩时间（包括）
		 */
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		/**
		 * setVisitTimeStart.
		 * 
		 * @param visitTimeStart
		 *            起始游玩时间（包括）
		 */
		public void setVisitTimeStart(final Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		/**
		 * getVisitTimeEnd.
		 * 
		 * @return 结束游玩时间（包括）
		 */
		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		/**
		 * setVisitTimeEnd.
		 * 
		 * @param visitTimeEnd
		 *            结束游玩时间（包括）
		 */
		public void setVisitTimeEnd(final Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}
	}

	/**
	 * 履行对象查询条件.
	 */
	public static final class MetaPerformRelate implements Serializable {
	
		/**
		 * 
		 */
		private static final long serialVersionUID = -5733016269217555420L;
		/**
		 * 履行对象ID，多个履行对象ID使用逗号分隔.
		 */
		private Long[] targetId;
		/**
		 * 订单ID.
		 */
		private Long orderId;
		/**
		 * 供应商ID.
		 */
		private Long supplierId;
		/**
		 * 采购产品ID
		 */
		private Long metaProductId;
		/**
		 * 类别ID
		 */
		private Long metaBranchId;

		/**
		 * getTargetId.
		 * 
		 * @return 行对象ID，多个履行对象ID使用逗号分隔
		 */
		public Long[] getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            行对象ID，多个履行对象ID使用逗号分隔
		 */
		public void setTargetId(final String targetId) {
			final String[] array = targetId.split(",");
			final Long[] enumArray = new Long[array.length];
			for (int i = 0; i < array.length; i++) {
				if (UtilityTool.isValid(array[i])) {
					enumArray[i] = Long.valueOf(array[i]);
				}
			}
			this.targetId = enumArray;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}

		/**
		 * getSupplierId.
		 * 
		 * @return 供应商ID
		 */
		public Long getSupplierId() {
			return supplierId;
		}

		/**
		 * setSupplierId.
		 * 
		 * @param supplierId
		 *            供应商ID
		 */
		public void setSupplierId(final Long supplierId) {
			this.supplierId = supplierId;
		}

		public Long getMetaProductId() {
			return metaProductId;
		}

		public void setMetaProductId(final Long metaProductId) {
			this.metaProductId = metaProductId;
		}

		public Long getMetaBranchId() {
			return metaBranchId;
		}

		public void setMetaBranchId(Long metaBranchId) {
			this.metaBranchId = metaBranchId;
		}
	}

	/**
	 * 与履行明细排序类型有关的枚举.
	 */
	public static enum PerformDetailSortTypeEnum {
		/**
		 * 订单ID升序.
		 */
		ORDER_ID_ASC,
		/**
		 * 订单ID降序.
		 */
		ORDER_ID_DESC,
		/**
		 * 采购产品名称升序.
		 */
		META_PRODUCT_NAME_ASC,
		/**
		 * 采购产品名称降序.
		 */
		META_PRODUCT_NAME_DESC,
		/**
		 * 联系人姓名升序.
		 */
		CONTACT_NAME_ASC,
		/**
		 * 联系人姓名降序.
		 */
		CONTACT_NAME_DESC,
		/**
		 * 使用时间升序.
		 */
		USED_TIME_ASC,
		/**
		 * 使用时间降序.
		 */
		USED_TIME_DESC,
		/**
		 * 使用状态排序.<br>
		 * UNUSED>USED>DESTROYED
		 */
		STATUS
	}

	/**
	 * 履行明细查询条件.
	 */
	public static final class PerformDetailRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5973739185045182225L;
		
		private List<Long> branchIds;
		
		private String paymentTarget;
		
		public void setBranchIds(List<Long> branchIds){
			this.branchIds=branchIds;
		}
		public void setPaymentTarget(String paymentTarget){
			this.paymentTarget=paymentTarget;
		}
		public List<Long> getBranchIds(){
			return branchIds;
		}
		public String getPaymentTarget(){
			return paymentTarget;
		}
		
		/**
		 * 供应商名称.
		 */
		private String supplierName;
		/**
		 * 采购产品名称.
		 */
		private String metaProductName;
		
		/**
		 * 采购产品类别名称
		 */
		private String branchName;

		/**
		 * 起始游玩时间（包括）.
		 */
		private Date visitTimeStart;
		/**
		 * 结束游玩时间（包括）.
		 */
		private Date visitTimeEnd;
		/**
		 * 履行状态.
		 */
		private Constant.ORDER_PERFORM_STATUS performStatus;
		/**
		 * 履行对象ID.
		 */
		private Long targetId;
		/**
		 * 订单状态.
		 */
		private Constant.ORDER_STATUS orderStatus;
		/**
		 * 联系人姓名.模糊查询
		 */
		private String contactName;
		/**
		 * 联系人手机.
		 */
		private String contactMobile;
		/**
		 * 订单ID.
		 */
		private Long orderId;
		/**
		 * e景通用户ID.
		 */
		private Long passPortUserId;
	/**
	 * 通关码或辅助码
	 */
		private String code;
		/**
		 * 采购产品编号
		 */
		private Long metaProductId;
		
		/**
		 * 采购产品类别
		 */
		private Long metaProductBranchId;
		/**
		 * 是否是二维码用户
		 */
		private boolean isPassPort;
		
		/**
		 * 订单人类型
		 */
		private String personType;
		
		/**
		 * 不定期密码券
		 */
		private String passwordCertificate;

		/**
		 * 是否为不定期产品
		 */
		private String isAperiodic;
		
		/**
		 * 密码券使用状态
		 */
		private Constant.PASSCODE_USE_STATUS useStatus;
		
		private Long orderItemMetaId;
		
		public void setOrderItemMetaId(Long orderItemMetaId){
			this.orderItemMetaId=orderItemMetaId;
		}
		public Long getOrderItemMetaId(){
			return this.orderItemMetaId;
		}
		/**
		 * getSupplierName.
		 * 
		 * @return 供应商名称
		 */
		public String getSupplierName() {
			return supplierName;
		}

		/**
		 * setSupplierName.
		 * 
		 * @param supplierName
		 *            供应商名称
		 */
		public void setSupplierName(final String supplierName) {
			this.supplierName = supplierName;
		}

		/**
		 * getMetaProductName.
		 * 
		 * @return 采购产品名称
		 */
		public String getMetaProductName() {
			return metaProductName;
		}

		/**
		 * setMetaProductName.
		 * 
		 * @param metaProductName
		 *            采购产品名称
		 */
		public void setMetaProductName(final String metaProductName) {
			this.metaProductName = metaProductName;
		}

		/**
		 * getVisitTimeStart.
		 * 
		 * @return 起始游玩时间（包括）
		 */
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		/**
		 * setVisitTimeStart.
		 * 
		 * @param visitTimeStart
		 *            起始游玩时间（包括）
		 */
		public void setVisitTimeStart(final Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		/**
		 * getVisitTimeEnd.
		 * 
		 * @return 结束游玩时间（包括）
		 */
		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		/**
		 * setVisitTimeEnd.
		 * 
		 * @param visitTimeEnd
		 *            结束游玩时间（包括）
		 */
		public void setVisitTimeEnd(final Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * getPerformStatus.
		 * 
		 * @return 履行状态
		 */
		public Constant.ORDER_PERFORM_STATUS getPerformStatus() {
			return performStatus;
		}

		/**
		 * setPerformStatus.
		 * 
		 * @param performStatus
		 *            履行状态
		 */
		public void setPerformStatus(final Constant.ORDER_PERFORM_STATUS performStatus) {
			this.performStatus = performStatus;
		}

		/**
		 * getTargetId.
		 * 
		 * @return 履行对象ID
		 */
		public Long getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            履行对象ID
		 */
		public void setTargetId(final Long targetId) {
			this.targetId = targetId;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 订单状态
		 */
		public Constant.ORDER_STATUS getOrderStatus() {
			return orderStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            订单状态
		 */
		public void setOrderStatus(final String orderStatus) {
			if (UtilityTool.isValid(orderStatus)) {
				this.orderStatus = Enum
						.valueOf(Constant.ORDER_STATUS.class, orderStatus);
			}
		}
		/**
		 * getUseStatus.
		 * 
		 * @return 不定期订单激活状态
		 */
		public Constant.PASSCODE_USE_STATUS getUseStatus() {
			return useStatus;
		}
		
		/**
		 * setUseStatus.
		 * 
		 * @param useStatus
		 *            不定期订单激活状态
		 */
		public void setUseStatus(final String useStatus) {
			if (UtilityTool.isValid(useStatus)) {
				this.useStatus = Enum
						.valueOf(Constant.PASSCODE_USE_STATUS.class, useStatus);
			}
		}

		/**
		 * getContactName.
		 * 
		 * @return 联系人姓名.模糊查询
		 */
		public String getContactName() {
			return contactName;
		}

		/**
		 * setContactName.
		 * 
		 * @param contactName
		 *            联系人姓名.模糊查询
		 */
		public void setContactName(final String contactName) {
			this.contactName = contactName;
		}

		/**
		 * getContactMobile.
		 * 
		 * @return 联系人手机
		 */
		public String getContactMobile() {
			return contactMobile;
		}

		/**
		 * setContactMobile.
		 * 
		 * @param contactMobile
		 *            联系人手机
		 */
		public void setContactMobile(final String contactMobile) {
			this.contactMobile = contactMobile;
		}

		/**
		 * getOrderId.
		 * 
		 * @return 订单ID
		 */
		public Long getOrderId() {
			return orderId;
		}

		/**
		 * setOrderId.
		 * 
		 * @param orderId
		 *            订单ID
		 */
		public void setOrderId(final Long orderId) {
			this.orderId = orderId;
		}

		/**
		 * getPassPortUserId.
		 * 
		 * @return e景通用户ID
		 */
		public Long getPassPortUserId() {
			return passPortUserId;
		}

		/**
		 * setPassPortUserId.
		 * 
		 * @param passPortUserId
		 *            e景通用户ID
		 */
		public void setPassPortUserId(final Long passPortUserId) {
			this.passPortUserId = passPortUserId;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}

		public Long getMetaProductId() {
			return metaProductId;
		}

		public void setMetaProductId(Long metaProductId) {
			this.metaProductId = metaProductId;
		}

		public boolean isPassPort() {
			return isPassPort;
		}

		public void setPassPort(boolean isPassPort) {
			this.isPassPort = isPassPort;
		}

		public Long getMetaProductBranchId() {
			return metaProductBranchId;
		}

		public void setMetaProductBranchId(Long metaProductBranchId) {
			this.metaProductBranchId = metaProductBranchId;
		}

		public String getBranchName() {
			return branchName;
		}

		public void setBranchName(String branchName) {
			this.branchName = branchName;
		}

		public String getPersonType() {
			return personType;
		}

		public void setPersonType(String personType) {
			this.personType = personType;
		}

		public String getPasswordCertificate() {
			return passwordCertificate;
		}
		public void setPasswordCertificate(String passwordCertificate) {
			this.passwordCertificate = passwordCertificate;
		}
		
		public String getIsAperiodic() {
			return isAperiodic;
		}
		public void setIsAperiodic(String isAperiodic) {
			this.isAperiodic = isAperiodic;
		}

	}

	/**
	 * 通关汇总查询条件.
	 */
	public static final class PassPortSummaryRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 8152223732725758798L;
		/**
		 * 起始游玩时间（包括）.
		 */
		private Date visitTimeStart;
		/**
		 * 结束游玩时间（包括）.
		 */
		private Date visitTimeEnd;
		/**
		 * 采购产品ID.
		 */
		private Long metaProductid;
		
		/**
		 * 采购产品类别ID
		 */
		private Long metaProductBranchId;
		/**
		 * 订单状态.
		 */
		private Constant.ORDER_STATUS orderStatus;
		/**
		 * e景通用户ID.
		 */
		private Long passPortUserId;

		/**
		 * getVisitTimeStart.
		 * 
		 * @return 起始游玩时间（包括）
		 */
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		/**
		 * setVisitTimeStart.
		 * 
		 * @param visitTimeStart
		 *            起始游玩时间（包括）
		 */
		public void setVisitTimeStart(final Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		/**
		 * getVisitTimeEnd.
		 * 
		 * @return 结束游玩时间（包括）
		 */
		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		/**
		 * setVisitTimeEnd.
		 * 
		 * @param visitTimeEnd
		 *            结束游玩时间（包括）
		 */
		public void setVisitTimeEnd(final Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * getMetaProductid.
		 * 
		 * @return 采购产品ID
		 */
		public Long getMetaProductid() {
			return metaProductid;
		}

		/**
		 * setMetaProductid.
		 * 
		 * @param metaProductid
		 *            采购产品ID
		 */
		public void setMetaProductid(final Long metaProductid) {
			this.metaProductid = metaProductid;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 订单状态
		 */
		public Constant.ORDER_STATUS getOrderStatus() {
			return orderStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            订单状态
		 */
		public void setOrderStatus(final String orderStatus) {
			if (UtilityTool.isValid(orderStatus)) {
				this.orderStatus = Enum
						.valueOf(Constant.ORDER_STATUS.class, orderStatus);
			}
		}

		/**
		 * getPassPortUserId.
		 * 
		 * @return e景通用户ID
		 */
		public Long getPassPortUserId() {
			return passPortUserId;
		}

		/**
		 * setPassPortUserId.
		 * 
		 * @param passPortUserId
		 *            e景通用户ID
		 */
		public void setPassPortUserId(final Long passPortUserId) {
			this.passPortUserId = passPortUserId;
		}

		public Long getMetaProductBranchId() {
			return metaProductBranchId;
		}

		public void setMetaProductBranchId(Long metaProductBranchId) {
			this.metaProductBranchId = metaProductBranchId;
		}
	}

	/**
	 * 通关明细查询条件.
	 */
	public static final class PassPortDetailRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5471321457108458101L;
		/**
		 * 起始游玩时间（包括）.
		 */
		private Date visitTimeStart;
		/**
		 * 结束游玩时间（包括）.
		 */
		private Date visitTimeEnd;
		/**
		 * 采购产品ID.
		 */
		private Long metaProductid;
		
		/**
		 * 采购产品类别ID
		 */
		private Long metaProductBranchId;
		/**
		 * 履行对象ID.
		 */
		private Long targetId;
		/**
		 * 订单状态.
		 */
		private Constant.ORDER_STATUS orderStatus;
		/**
		 * e景通用户ID.
		 */
		private Long passPortUserId;
		/**
		 * 是否是二维码用户
		 */
		private boolean isPassPort;
		/**
		 * getVisitTimeStart.
		 * 
		 * @return 起始游玩时间（包括）
		 */
		public Date getVisitTimeStart() {
			return visitTimeStart;
		}

		/**
		 * setVisitTimeStart.
		 * 
		 * @param visitTimeStart
		 *            起始游玩时间（包括）
		 */
		public void setVisitTimeStart(final Date visitTimeStart) {
			this.visitTimeStart = visitTimeStart;
		}

		/**
		 * getVisitTimeEnd.
		 * 
		 * @return 结束游玩时间（包括）
		 */
		public Date getVisitTimeEnd() {
			return visitTimeEnd;
		}

		/**
		 * setVisitTimeEnd.
		 * 
		 * @param visitTimeEnd
		 *            结束游玩时间（包括）
		 */
		public void setVisitTimeEnd(final Date visitTimeEnd) {
			this.visitTimeEnd = visitTimeEnd;
		}

		/**
		 * getMetaProductid.
		 * 
		 * @return 采购产品ID
		 */
		public Long getMetaProductid() {
			return metaProductid;
		}

		/**
		 * setMetaProductid.
		 * 
		 * @param metaProductid
		 *            采购产品ID
		 */
		public void setMetaProductid(final Long metaProductid) {
			this.metaProductid = metaProductid;
		}

		/**
		 * getTargetId.
		 * 
		 * @return 履行对象ID
		 */
		public Long getTargetId() {
			return targetId;
		}

		/**
		 * setTargetId.
		 * 
		 * @param targetId
		 *            履行对象ID
		 */
		public void setTargetId(final Long targetId) {
			this.targetId = targetId;
		}

		/**
		 * getOrderStatus.
		 * 
		 * @return 订单状态
		 */
		public Constant.ORDER_STATUS getOrderStatus() {
			return orderStatus;
		}

		/**
		 * setOrderStatus.
		 * 
		 * @param orderStatus
		 *            订单状态
		 */
		public void setOrderStatus(final String orderStatus) {
			if (UtilityTool.isValid(orderStatus)) {
				this.orderStatus = Enum
						.valueOf(Constant.ORDER_STATUS.class, orderStatus);
			}
		}

		/**
		 * getPassPortUserId.
		 * 
		 * @return e景通用户ID
		 */
		public Long getPassPortUserId() {
			return passPortUserId;
		}

		/**
		 * setPassPortUserId.
		 * 
		 * @param passPortUserId
		 *            e景通用户ID
		 */
		public void setPassPortUserId(final Long passPortUserId) {
			this.passPortUserId = passPortUserId;
		}

		public boolean isPassPort() {
			return isPassPort;
		}

		public void setPassPort(boolean isPassPort) {
			this.isPassPort = isPassPort;
		}

		public Long getMetaProductBranchId() {
			return metaProductBranchId;
		}

		public void setMetaProductBranchId(Long metaProductBranchId) {
			this.metaProductBranchId = metaProductBranchId;
		}
	}

	/**
	 * 现金账户变更日志查询条件.
	 */
	public static final class MoneyAccountChangeLogRelate implements
			Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = 5014509897670715731L;
		/**
		 * 现金账户变更日志类型.
		 */
		private Constant.MoneyAccountChangeType moneyAccountChangeType;
		/**
		 * 用户ID.
		 */
		private String userId;

		/**
		 * 用户No.
		 */
		private String userNo;
		
		/**
		 * 现金账户主键
		 */
		private Long cashAccountId;
		/**
		 * getMoneyAccountChangeType.
		 * 
		 * @return 现金账户变更日志类型
		 */
		public Constant.MoneyAccountChangeType getMoneyAccountChangeType() {
			return moneyAccountChangeType;
		}

		/**
		 * setMoneyAccountChangeType.
		 * 
		 * @param moneyAccountChangeType
		 *            现金账户变更日志类型
		 */
		public void setMoneyAccountChangeType(
				final Constant.MoneyAccountChangeType moneyAccountChangeType) {
			this.moneyAccountChangeType = moneyAccountChangeType;
		}

		/**
		 * getUserId.
		 * 
		 * @return 用户ID
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId.
		 * 
		 * @param userId
		 *            用户ID
		 */
		public void setUserId(final String userId) {
			this.userId = userId;
		}

		public Long getCashAccountId() {
			return cashAccountId;
		}

		public void setCashAccountId(Long cashAccountId) {
			this.cashAccountId = cashAccountId;
		}

		public String getUserNo() {
			return userNo;
		}

		public void setUserNo(String userNo) {
			this.userNo = userNo;
		}
	}

	/**
	 * 现金账户提现日志查询条件.
	 */
	public static final class MoneyDrawRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -6081795580507356951L;
		/**
		 * 用户ID.
		 */
		private String userId;
		/**
		 * 用户No.
		 */
		private String userNo;
		
		/**
		 * 用户手机号码
		 */
		private String userMobile;
		
		/**
		 * 银行卡账户名
		 */
		private String bankAccountName;
		
		/**
		 * 提现单状态.
		 */
		private String fincCashStatus;
		/**
		 * 起始创建时间（包括）.
		 */
		private Date createTimeStart;
		/**
		 * 结束创建时间（不包括）.
		 */
		private Date createTimeEnd;
		/**
		 * 现金账户id（外键）
		 */
		private Long cashAccountId;
		/**
		 * getUserId.
		 * 
		 * @return 用户ID
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId.
		 * 
		 * @param userId
		 *            用户ID
		 */
		public void setUserId(final String userId) {
			this.userId = userId;
		}

		/**
		 * getFincCashStatus.
		 * 
		 * @return 提现单状态
		 */
		public String getFincCashStatus() {
			return fincCashStatus;
		}

		/**
		 * setFincCashStatus.
		 * 
		 * @param fincCashStatus
		 *            提现单状态
		 */
		public void setFincCashStatus(final String fincCashStatus) {
			this.fincCashStatus = fincCashStatus;
		}

		/**
		 * getCreateTimeStart.
		 * 
		 * @return 起始创建时间（包括）
		 */
		public Date getCreateTimeStart() {
			return createTimeStart;
		}

		/**
		 * setCreateTimeStart.
		 * 
		 * @param createTimeStart
		 *            起始创建时间（包括）
		 */
		public void setCreateTimeStart(final Date createTimeStart) {
			this.createTimeStart = createTimeStart;
		}

		/**
		 * getCreateTimeEnd.
		 * 
		 * @return 结束创建时间（不包括）
		 */
		public Date getCreateTimeEnd() {
			return createTimeEnd;
		}

		/**
		 * setCreateTimeEnd.
		 * 
		 * @param createTimeEnd
		 *            结束创建时间（不包括）
		 */
		public void setCreateTimeEnd(final Date createTimeEnd) {
			this.createTimeEnd = createTimeEnd;
		}
		

		public Long getCashAccountId() {
			return cashAccountId;
		}

		public void setCashAccountId(Long cashAccountId) {
			this.cashAccountId = cashAccountId;
		}

		public String getUserNo() {
			return userNo;
		}

		public void setUserNo(String userNo) {
			this.userNo = userNo;
		}

		public String getUserMobile() {
			return userMobile;
		}

		public void setUserMobile(String userMobile) {
			this.userMobile = userMobile;
		}

		public String getBankAccountName() {
			return bankAccountName;
		}

		public void setBankAccountName(String bankAccountName) {
			this.bankAccountName = bankAccountName;
		}
		
	}
	
	
	public static final class QueryFlag implements Serializable{
		/**
		 * 
		 */
		private static final long serialVersionUID = 4865380831086574392L;
		/**
		 * 是否查询供应商
		 */
		private boolean querySupplier=true;
		/**
		 * 是否查询用户信息
		 */
		private boolean queryUser=true;
		public boolean isQuerySupplier() {
			return querySupplier;
		}
		public void setQuerySupplier(boolean querySupplier) {
			this.querySupplier = querySupplier;
		}
		public boolean isQueryUser() {
			return queryUser;
		}
		public void setQueryUser(boolean queryUser) {
			this.queryUser = queryUser;
		}
		
		
	}

	/**
	 * 打款记录查询条件.
	 */
	public static final class PlayMoneyRelate implements Serializable {
		
		/**
		 * 
		 */
		private static final long serialVersionUID = -4610448319320841724L;
		/**
		 * 用户ID.
		 */
		private String userId;
		/**
		 * 打款状态.
		 */
		private Constant.FINC_CASH_STATUS fincCashStatus;
		/**
		 * 起始打款时间（包括）.
		 */
		private Date createTimeStart;
		/**
		 * 结束打款时间（不包括）.
		 */
		private Date createTimeEnd;
		/**
		 * 打款类型.
		 */
		private Constant.PlayMoneyType playMoneyType = Constant.PlayMoneyType.ALL;

		/**
		 * getUserId.
		 * 
		 * @return 用户ID
		 */
		public String getUserId() {
			return userId;
		}

		/**
		 * setUserId.
		 * 
		 * @param userId
		 *            用户ID
		 */
		public void setUserId(final String userId) {
			this.userId = userId;
		}

		/**
		 * getFincCashStatus.
		 * 
		 * @return 打款状态
		 */
		public Constant.FINC_CASH_STATUS getFincCashStatus() {
			return fincCashStatus;
		}

		/**
		 * setFincCashStatus.
		 * 
		 * @param fincCashStatus
		 *            打款状态
		 */
		public void setFincCashStatus(final Constant.FINC_CASH_STATUS fincCashStatus) {
			this.fincCashStatus = fincCashStatus;
		}

		/**
		 * getCreateTimeStart.
		 * 
		 * @return 起始打款时间（包括）
		 */
		public Date getCreateTimeStart() {
			return createTimeStart;
		}

		/**
		 * setCreateTimeStart.
		 * 
		 * @param createTimeStart
		 *            起始打款时间（包括）
		 */
		public void setCreateTimeStart(final Date createTimeStart) {
			this.createTimeStart = createTimeStart;
		}

		/**
		 * getCreateTimeEnd.
		 * 
		 * @return 结束打款时间（不包括）
		 */
		public Date getCreateTimeEnd() {
			return createTimeEnd;
		}

		/**
		 * setCreateTimeEnd.
		 * 
		 * @param createTimeEnd
		 *            结束打款时间（不包括）
		 */
		public void setCreateTimeEnd(final Date createTimeEnd) {
			this.createTimeEnd = createTimeEnd;
		}

		/**
		 * getPlayMoneyType.
		 * 
		 * @return 打款类型
		 */
		public Constant.PlayMoneyType getPlayMoneyType() {
			return playMoneyType;
		}

		/**
		 * setPlayMoneyType.
		 * 
		 * @param playMoneyType
		 *            打款类型
		 */
		public void setPlayMoneyType(final Constant.PlayMoneyType playMoneyType) {
			this.playMoneyType = playMoneyType;
		}
	}
	
	/**
	 * 订单合同查询条件.
	 */
	public final static class EContractRelate  implements Serializable {
		private static final long serialVersionUID = 5144190284935517550L;

		/**
		 * 是否电子签约
		 */
		private String needEContract;
		
		/**
		 * 电子签约状态
		 * @return
		 */
		private String eContractStatus;

		public String getNeedEContract() {
			return needEContract;
		}

		public void setNeedEContract(String needEContract) {
			this.needEContract = needEContract;
		}

		public String getEContractStatus() {
			return eContractStatus;
		}

		public void setEContractStatus(String eContractStatus) {
			this.eContractStatus = eContractStatus;
		}
	}
	
	public final static class OrderTrackRelate implements Serializable {
		/**
		 * 序列化ID.
		 */
		private static final long serialVersionUID = -4511357579045059407L;
		/**
		 * 是否执行查询.
		 */
		private boolean isSearch = false;
		/**
		 * 二次处理的领单人.
		 */
		private String trackOperator;
	    /**
	     * 二次跟踪处理的领单开始时间.
	     */
		private Date trackCreateTimeStart;
		/**
		 * 二次跟踪处理的领单结束时间.
		 */
		private Date trackCreateTimeEnd;
		/**
		 * 二次跟踪处理结果.
		 */
		private String trackStatus;
		/**
		 * 二次跟踪处理LOG状态．
		 */
		private String trackLogStatus;
		/**
		 * 获取是否执行查询.
		 * @return
		 */
		public boolean isSearch() {
			return isSearch;
		}
		/**
		 * 设置是否执行查询.
		 * @param isSearch
		 */
		public void setSearch(boolean isSearch) {
			this.isSearch = isSearch;
		}
		/**
		 * 获取二次处理的领单人.
		 * @return
		 */
		public String getTrackOperator() {
			return trackOperator;
		}
		/**
		 * 设置二次处理的领单人.
		 * @param trackOperator
		 */
		public void setTrackOperator(String trackOperator) {
			this.trackOperator = trackOperator;
		}
		/**
		 * 获取二次跟踪处理的领单开始时间.
		 * @return
		 */
		public Date getTrackCreateTimeStart() {
			return trackCreateTimeStart;
		}
		/**
		 * 设置二次跟踪处理的领单开始时间.
		 * @param trackCreateTimeStart
		 */
		public void setTrackCreateTimeStart(Date trackCreateTimeStart) {
			this.trackCreateTimeStart = trackCreateTimeStart;
		}
		/**
		 * 获取二次跟踪处理的领单结束时间.
		 * @return
		 */
		public Date getTrackCreateTimeEnd() {
			return trackCreateTimeEnd;
		}
		/**
		 * 设置二次跟踪处理的领单结束时间.
		 * @param trackCreateTimeEnd
		 */
		public void setTrackCreateTimeEnd(Date trackCreateTimeEnd) {
			this.trackCreateTimeEnd = trackCreateTimeEnd;
		}
		/**
		 * 获取二次跟踪处理结果.
		 * @return
		 */
		public String getTrackStatus() {
			return trackStatus;
		}
		/**
		 * 设置二次跟踪处理结果.
		 * @param trackStatus
		 */
		public void setTrackStatus(String trackStatus) {
			this.trackStatus = trackStatus;
		}
		/**
		 * 获取二次跟踪处理LOG状态.
		 * @return
		 */
		public String getTrackLogStatus() {
			return trackLogStatus;
		}
		/**
		 * 跟踪二次跟踪处理LOG状态.
		 * @param trackLogStatus
		 */
		public void setTrackLogStatus(String trackLogStatus) {
			this.trackLogStatus = trackLogStatus;
		}
		
		
	}

	public void setPayFrom(String payFrom) {
		this.payFrom = payFrom;
	}

	public String getPayFrom() {
		return this.payFrom;
	}

	public String getBonusRefundment() {
		return bonusRefundment;
	}

	public void setBonusRefundment(String bonusRefundment) {
		this.bonusRefundment = bonusRefundment;
	}

	public QueryFlag getQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(QueryFlag queryFlag) {
		this.queryFlag = queryFlag;
	}
	
}


