package com.lvmama.order.service.builder;

/**
 * 业务标示.
 *
 * <pre>
 * 控制SQL的生成
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public interface BusinessFlag {
	/**
	 * isSettlementBusiness.
	 *
	 * @return <code>true</code>代表是结算管理业务，<code>false</code>代表不是结算管理业务
	 */
	boolean isSettlementBusiness();

	/**
	 * setSettlementBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算管理业务，<code>false</code>代表不是结算管理业务
	 */
	void setSettlementBusiness(boolean flag);

	/**
	 * getInvoiceBusiness.
	 *
	 * @return <code>true</code>代表是发票管理业务，<code>false</code>代表不是发票管理业务
	 */
	boolean isInvoiceBusiness();

	/**
	 * setInvoiceBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是发票管理业务，<code>false</code>代表不是发票管理业务
	 */
	void setInvoiceBusiness(boolean flag);

	/**
	 * getSettlementItemBusiness.
	 *
	 * @return <code>true</code>代表是结算子项管理业务，<code>false</code>代表不是结算子项业务
	 */
	boolean isSettlementItemBusiness();

	/**
	 * setSettlementItemBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算子项管理业务，<code>false</code>代表不是结算子项业务
	 */
	void setSettlementItemBusiness(boolean flag);

	/**
	 * isOrdOrderItemMetaBusiness.
	 *
	 * @return <pre>
	 * <code>true</code>代表是采购产品订单子项业务，<code>false</code> 代表不是采购产品订单子项业务
	 * </pre>
	 */
	boolean isOrdOrderItemMetaBusiness();

	/**
	 * setOrdOrderItemMetaBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是采购产品订单子项业务，<code>false</code>
	 *            代表不是采购产品订单子项业务
	 */
	void setOrdOrderItemMetaBusiness(final boolean flag);

	/**
	 * isOrdSettlementBusiness.
	 *
	 * @return <code>true</code>代表是结算单业务，<code>false</code>代表不是结算单业务
	 */
	boolean isOrdSettlementBusiness();

	/**
	 * setOrdSettlementBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算单业务，<code>false</code>代表不是结算单业务
	 */
	void setOrdSettlementBusiness(final boolean flag);

	/**
	 * isOrdOrderBusiness.
	 *
	 * @return <code>true</code>代表是订单业务，<code>false</code>代表不是订单业务
	 */
	boolean isOrdOrderBusiness();

	/**
	 * setOrdOrderBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是订单业务，<code>false</code>代表不是订单业务
	 */
	void setOrdOrderBusiness(boolean flag);

	/**
	 * isPerformDetailBusiness.
	 *
	 * @return <code>true</code>代表是履行明细业务，<code>false</code>代表不是履行明细业务
	 */
	boolean isPerformDetailBusiness();

	/**
	 * setPerformDetailBusiness.
	 *
	 * @param performDetailBusiness
	 *            <code>true</code>代表是履行明细业务，<code>false</code>代表不是履行明细业务
	 */
	void setPerformDetailBusiness(boolean performDetailBusiness);

	/**
	 * isPassPortDetailBusiness.
	 *
	 * @return <code>true</code>代表是通关明细业务，<code>false</code>代表不是通关明细业务
	 */
	boolean isPassPortDetailBusiness();

	/**
	 * setPassPortDetailBusiness.
	 *
	 * @param passPortDetailBusiness
	 *            <code>true</code>代表是通关明细业务，<code>false</code>代表不是通关明细业务
	 */
	void setPassPortDetailBusiness(final boolean passPortDetailBusiness);

	/**
	 * isPassPortSummaryBusiness.
	 *
	 * @return <code>true</code>代表是通关汇总业务，<code>false</code>代表不是通关汇总业务
	 */
	boolean isPassPortSummaryBusiness();

	/**
	 * setPassPortSummaryBusiness.
	 *
	 * @param passPortSummaryBusiness
	 *            <code>true</code>代表是通关汇总业务，<code>false</code>代表不是通关汇总业务
	 */
	void setPassPortSummaryBusiness(boolean passPortSummaryBusiness);
}
