package com.lvmama.order.service.impl.builder;

import com.lvmama.order.service.builder.BusinessFlag;

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
public class BusinessFlagImpl implements BusinessFlag {
	/**
	 * <code>true</code>代表是结算管理业务，<code>false</code>代表不是结算管理业务.
	 */
	private boolean settlementBusiness = false;;

	/**
	 * isSettlementBusiness.
	 *
	 * @return <code>true</code>代表是结算管理业务，<code>false</code>代表不是结算管理业务
	 */
	@Override
	public boolean isSettlementBusiness() {
		return settlementBusiness;
	}

	/**
	 * setSettlementBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算管理业务，<code>false</code>代表不是结算管理业务
	 */
	@Override
	public void setSettlementBusiness(final boolean flag) {
		settlementBusiness = flag;
	}

	/**
	 * <code>true</code>代表是发票管理业务，<code>false</code>代表不是发票管理业务.
	 */
	private boolean invoiceBusiness = false;;

	/**
	 * isInvoiceBusiness.
	 *
	 * @return <code>true</code>代表是发票管理业务，<code>false</code>代表不是发票管理业务
	 */
	@Override
	public boolean isInvoiceBusiness() {
		return invoiceBusiness;
	}

	/**
	 * setInvoiceBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是发票管理业务，<code>false</code>代表不是发票管理业务
	 */
	@Override
	public void setInvoiceBusiness(final boolean flag) {
		invoiceBusiness = flag;
	}

	/**
	 * <code>true</code>代表是结算子项管理业务，<code>false</code>代表不是结算子项业务.
	 */
	private boolean settlementItemBusiness = false;

	/**
	 * getSettlementItemBusiness.
	 *
	 * @return <code>true</code>代表是结算子项管理业务，<code>false</code>代表不是结算子项业务
	 */
	@Override
	public boolean isSettlementItemBusiness() {
		return settlementItemBusiness;
	}

	/**
	 * setSettlementItemBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算子项管理业务，<code>false</code>代表不是结算子项业务
	 */
	@Override
	public void setSettlementItemBusiness(final boolean flag) {
		settlementItemBusiness = flag;
	}

	/**
	 * <code>true</code>代表是采购产品订单子项业务，<code>false</code>代表不是采购产品订单子项业务.
	 */
	private boolean ordOrderItemMetaBusiness = false;

	/**
	 * isOrdOrderItemMetaBusiness.
	 *
	 * @return <pre>
	 * <code>true</code>代表是采购产品订单子项业务，<code>false</code>代表不是采购产品订单子项业务
	 * </pre>
	 */
	@Override
	public boolean isOrdOrderItemMetaBusiness() {
		return ordOrderItemMetaBusiness;
	}

	/**
	 * setOrdOrderItemMetaBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是采购产品订单子项业务，<code>false</code>
	 *            代表不是采购产品订单子项业务
	 */
	@Override
	public void setOrdOrderItemMetaBusiness(final boolean flag) {
		ordOrderItemMetaBusiness = flag;
	}

	/**
	 * <code>true</code>代表是结算单业务，<code>false</code>代表不是结算单业务.
	 */
	private boolean ordSettlementBusiness = false;

	/**
	 * isOrdSettlementBusiness.
	 *
	 * @return <code>true</code>代表是结算单业务，<code>false</code>代表不是结算单业务
	 */
	@Override
	public boolean isOrdSettlementBusiness() {
		return ordSettlementBusiness;
	}

	/**
	 * setOrdSettlementBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是结算单业务，<code>false</code>代表不是结算单业务
	 */
	@Override
	public void setOrdSettlementBusiness(final boolean flag) {
		ordSettlementBusiness = flag;
	}

	/**
	 * <code>true</code>代表是订单业务，<code>false</code>代表不是订单业务.
	 */
	private boolean ordOrderBusiness = false;

	/**
	 * isOrdOrderBusiness.
	 *
	 * @return <code>true</code>代表是订单业务，<code>false</code>代表不是订单业务
	 */
	@Override
	public boolean isOrdOrderBusiness() {
		return ordOrderBusiness;
	}

	/**
	 * setOrdOrderBusiness.
	 *
	 * @param flag
	 *            <code>true</code>代表是订单业务，<code>false</code>代表不是订单业务
	 */
	@Override
	public void setOrdOrderBusiness(final boolean flag) {
		ordOrderBusiness = flag;
	}

	/**
	 * <code>true</code>代表是履行明细业务，<code>false</code>代表不是履行明细业务.
	 */
	private boolean performDetailBusiness = false;

	/**
	 * isPerformDetailBusiness.
	 *
	 * @return <code>true</code>代表是履行明细业务，<code>false</code>代表不是履行明细业务
	 */
	@Override
	public boolean isPerformDetailBusiness() {
		return performDetailBusiness;
	}

	/**
	 * setPerformDetailBusiness.
	 *
	 * @param performDetailBusiness
	 *            <code>true</code>代表是履行明细业务，<code>false</code>代表不是履行明细业务
	 */
	@Override
	public void setPerformDetailBusiness(final boolean performDetailBusiness) {
		this.performDetailBusiness = performDetailBusiness;
	}

	/**
	 * <code>true</code>代表是通关明细业务，<code>false</code>代表不是通关明细业务.
	 */
	private boolean passPortDetailBusiness = false;

	/**
	 * isPassPortDetailBusiness.
	 *
	 * @return <code>true</code>代表是通关明细业务，<code>false</code>代表不是通关明细业务
	 */
	@Override
	public boolean isPassPortDetailBusiness() {
		return passPortDetailBusiness;
	}

	/**
	 * setPassPortDetailBusiness.
	 *
	 * @param passPortDetailBusiness
	 *            <code>true</code>代表是通关明细业务，<code>false</code>代表不是通关明细业务
	 */
	@Override
	public void setPassPortDetailBusiness(final boolean passPortDetailBusiness) {
		this.passPortDetailBusiness = passPortDetailBusiness;
	}

	/**
	 * <code>true</code>代表是通关汇总业务，<code>false</code>代表不是通关汇总业务.
	 */
	private boolean passPortSummaryBusiness = false;

	/**
	 * isPassPortSummaryBusiness.
	 *
	 * @return <code>true</code>代表是通关汇总业务，<code>false</code>代表不是通关汇总业务
	 */
	@Override
	public boolean isPassPortSummaryBusiness() {
		return passPortSummaryBusiness;
	}

	/**
	 * setPassPortSummaryBusiness.
	 *
	 * @param passPortSummaryBusiness
	 *            <code>true</code>代表是通关汇总业务，<code>false</code>代表不是通关汇总业务
	 */
	@Override
	public void setPassPortSummaryBusiness(final boolean passPortSummaryBusiness) {
		this.passPortSummaryBusiness = passPortSummaryBusiness;
	}
}
