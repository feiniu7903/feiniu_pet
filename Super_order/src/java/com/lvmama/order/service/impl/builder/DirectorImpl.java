package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.Director;
import com.lvmama.order.service.builder.SQLBuilder;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL控制器.
 * 
 * <pre>
 * 控制SQL的生成
 * <strong>注意，此实现不是同步的。</strong>
 * 如果多个线程同时访问一个 <code>DirectorImpl</code>实例，
 * 而其中至少一个线程从结构上修改了 <code>DirectorImpl</code>，
 * 那么它必须保持外部同步。
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 * @see com.lvmama.ord.service.po.CompositeQuery
 * @see com.lvmama.order.po.SQlBuilderMaterial
 * @see com.lvmama.order.service.builder.Director
 * @see com.lvmama.order.service.builder.SQLBuilder
 * @see com.lvmama.order.service.builder.TableName
 */
public final class DirectorImpl extends BusinessFlagImpl implements Director,
		TableName {
	/**
	 * 综合查询.
	 */
	private transient CompositeQuery query;

	/**
	 * setCompositeQuery.
	 * 
	 * @param query
	 *            综合查询
	 */
	@Override
	public void setCompositeQuery(final CompositeQuery query) {
		this.query = query;
	}

	/**
	 * 与订单状态有关的参数代理.
	 */
	private IMaterialBuilder orderStatusMaterialBuilderProxy;

	/**
	 * setOrderStatusMaterialBuilderProxy.
	 * 
	 * @param orderStatusMaterialBuilderProxy
	 *            与订单状态有关的参数代理
	 */
	public void setOrderStatusMaterialBuilderProxy(
			final IMaterialBuilder orderStatusMaterialBuilderProxy) {
		this.orderStatusMaterialBuilderProxy = orderStatusMaterialBuilderProxy;
	}

	/**
	 * 订单的二次处理.
	 */
	private IMaterialBuilder orderTrackMaterialBuilderProxy;
	
	
	/**
	 * 设置订单的二次处理.
	 * @param orderTrackMaterialBuilderProxy
	 */
	public void setOrderTrackMaterialBuilderProxy(
			IMaterialBuilder orderTrackMaterialBuilderProxy) {
		this.orderTrackMaterialBuilderProxy = orderTrackMaterialBuilderProxy;
	}

	/**
	 * 与订单内容有关的参数.
	 */
	private IMaterialBuilder orderContentMaterialBuilderProxy;

	/**
	 * setOrderContentMaterialBuilderProxy.
	 * 
	 * @param orderContentMaterialBuilderProxy
	 *            与订单内容有关的参数
	 */
	public void setOrderContentMaterialBuilderProxy(
			final IMaterialBuilder orderContentMaterialBuilderProxy) {
		this.orderContentMaterialBuilderProxy = orderContentMaterialBuilderProxy;
	}

	/**
	 * 表示订单排除条件.
	 */
	private IMaterialBuilder excludedContentMaterialBuilderProxy;

	/**
	 * setExcludedContentMaterialBuilderProxy.
	 * 
	 * @param excludedContentMaterialBuilderProxy
	 *            表示订单排除条件
	 */
	public void setExcludedContentMaterialBuilderProxy(
			final IMaterialBuilder excludedContentMaterialBuilderProxy) {
		this.excludedContentMaterialBuilderProxy = excludedContentMaterialBuilderProxy;
	}

	/**
	 * 表示订单排除条件.
	 */
	private IMaterialBuilder orderTimeRangeMaterialBuilderProxy;

	/**
	 * setOrderTimeRangeMaterialBuilderProxy.
	 * 
	 * @param orderTimeRangeMaterialBuilderProxy
	 *            表示订单排除条件
	 */
	public void setOrderTimeRangeMaterialBuilderProxy(
			final IMaterialBuilder orderTimeRangeMaterialBuilderProxy) {
		this.orderTimeRangeMaterialBuilderProxy = orderTimeRangeMaterialBuilderProxy;
	}

	/**
	 * 与订单相关ID有关的参数.
	 */
	private IMaterialBuilder orderIdentityMaterialBuilderProxy;

	/**
	 * setOrderIdentityMaterialBuilderProxy.
	 * 
	 * @param orderIdentityMaterialBuilderProxy
	 *            与订单相关ID有关的参数
	 */
	public void setOrderIdentityMaterialBuilderProxy(
			final IMaterialBuilder orderIdentityMaterialBuilderProxy) {
		this.orderIdentityMaterialBuilderProxy = orderIdentityMaterialBuilderProxy;
	}

	/**
	 * 与订单相关排序类型有关的枚举.
	 */
	private IMaterialBuilder sortTypeEnumMaterialBuilderProxy;

	/**
	 * setSortTypeEnumMaterialBuilderProxy.
	 * 
	 * @param sortTypeEnumMaterialBuilderProxy
	 *            与订单相关排序类型有关的枚举
	 */
	public void setSortTypeEnumMaterialBuilderProxy(
			final IMaterialBuilder sortTypeEnumMaterialBuilderProxy) {
		this.sortTypeEnumMaterialBuilderProxy = sortTypeEnumMaterialBuilderProxy;
	}

	/**
	 * 与订单分页有关的参数.
	 */
	private IMaterialBuilder pageIndexMaterialBuilderProxy;

	/**
	 * setPageIndexMaterialBuilderProxy.
	 * 
	 * @param pageIndexMaterialBuilderProxy
	 *            与订单分页有关的参数
	 */
	public void setPageIndexMaterialBuilderProxy(
			final IMaterialBuilder pageIndexMaterialBuilderProxy) {
		this.pageIndexMaterialBuilderProxy = pageIndexMaterialBuilderProxy;
	}

	/**
	 * 发票管理查询条件.
	 */
	private IMaterialBuilder invoiceRelateMaterialBuilderProxy;

	/**
	 * setInvoiceRelateMaterialBuilderProxy.
	 * 
	 * @param invoiceRelateMaterialBuilderProxy
	 *            发票管理查询条件
	 */
	public void setInvoiceRelateMaterialBuilderProxy(
			final IMaterialBuilder invoiceRelateMaterialBuilderProxy) {
		this.invoiceRelateMaterialBuilderProxy = invoiceRelateMaterialBuilderProxy;
	}
 
	/**
	 * 投诉管理查询条件.
	 */
	private IMaterialBuilder saleServiceRelateMaterialBuilderProxy;

	/**
	 * setSaleServiceRelateMaterialBuilderProxy.
	 * 
	 * @param saleServiceRelateMaterialBuilderProxy
	 *            投诉管理查询条件
	 */
	public void setSaleServiceRelateMaterialBuilderProxy(
			final IMaterialBuilder saleServiceRelateMaterialBuilderProxy) {
		this.saleServiceRelateMaterialBuilderProxy = saleServiceRelateMaterialBuilderProxy;
	}

	/**
	 * 结算管理查询条件.
	 */
	private IMaterialBuilder settlementQueueRelateMaterialBuilderProxy;

	/**
	 * setSettlementQueueRelateMaterialBuilderProxy.
	 * 
	 * @param settlementQueueRelateMaterialBuilderProxy
	 *            结算管理查询条件
	 */
	public void setSettlementQueueRelateMaterialBuilderProxy(
			final IMaterialBuilder settlementQueueRelateMaterialBuilderProxy) {
		this.settlementQueueRelateMaterialBuilderProxy = settlementQueueRelateMaterialBuilderProxy;
	}

	/**
	 * 结算管理子项查询条件.
	 */
	private IMaterialBuilder settlementItemRelateMaterialBuilderProxy;

	/**
	 * setSettlementItemRelateMaterialBuilderProxy.
	 * 
	 * @param settlementItemRelateMaterialBuilderProxy
	 *            结算管理子项查询条件
	 */
	public void setSettlementItemRelateMaterialBuilderProxy(
			final IMaterialBuilder settlementItemRelateMaterialBuilderProxy) {
		this.settlementItemRelateMaterialBuilderProxy = settlementItemRelateMaterialBuilderProxy;
	}

	/**
	 * 结算管理完成的子项查询条件.
	 */
	private IMaterialBuilder finishSettlementItemRelateMaterialBuilderProxy;

	/**
	 * setFinishSettlementItemRelateMaterialBuilderProxy.
	 * 
	 * @param finishSettlementItemRelateMaterialBuilderProxy
	 *            结算管理完成的子项查询条件
	 */
	public void setFinishSettlementItemRelateMaterialBuilderProxy(
			final IMaterialBuilder finishSettlementItemRelateMaterialBuilderProxy) {
		this.finishSettlementItemRelateMaterialBuilderProxy = finishSettlementItemRelateMaterialBuilderProxy;
	}

	/**
	 * 结算单查询条件.
	 */
	private IMaterialBuilder ordSettlementRelateMaterialBuilderProxy;

	/**
	 * setOrdSettlementRelateMaterialBuilderProxy.
	 * 
	 * @param ordSettlementRelateMaterialBuilderProxy
	 *            结算单查询条件
	 */
	public void setOrdSettlementRelateMaterialBuilderProxy(
			final IMaterialBuilder ordSettlementRelateMaterialBuilderProxy) {
		this.ordSettlementRelateMaterialBuilderProxy = ordSettlementRelateMaterialBuilderProxy;
	}

	/**
	 * 履行对象查询条件.
	 */
	private IMaterialBuilder metaPerformRelateMaterialBuilderProxy;

	/**
	 * setMetaPerformRelateMaterialBuilderProxy.
	 * 
	 * @param metaPerformRelateMaterialBuilderProxy
	 *            履行对象查询条件
	 */
	public void setMetaPerformRelateMaterialBuilderProxy(
			final IMaterialBuilder metaPerformRelateMaterialBuilderProxy) {
		this.metaPerformRelateMaterialBuilderProxy = metaPerformRelateMaterialBuilderProxy;
	}

	/**
	 * 履行明细查询条件.
	 */
	private IMaterialBuilder performDetailRelateMaterialBuilderProxy;

	/**
	 * setPerformDetailRelateMaterialBuilderProxy.
	 * 
	 * @param performDetailRelateMaterialBuilderProxy
	 *            履行明细查询条件
	 */
	public void setPerformDetailRelateMaterialBuilderProxy(
			final IMaterialBuilder performDetailRelateMaterialBuilderProxy) {
		this.performDetailRelateMaterialBuilderProxy = performDetailRelateMaterialBuilderProxy;
	}

	/**
	 * 与履行明细排序类型有关的枚举.
	 */
	private IMaterialBuilder performDetailSortTypeEnumMaterialBuilderProxy;

	/**
	 * setPerformDetailSortTypeEnumMaterialBuilderProxy.
	 * 
	 * @param performDetailSortTypeEnumMaterialBuilderProxy
	 *            与履行明细排序类型有关的枚举
	 */
	public void setPerformDetailSortTypeEnumMaterialBuilderProxy(
			final IMaterialBuilder performDetailSortTypeEnumMaterialBuilderProxy) {
		this.performDetailSortTypeEnumMaterialBuilderProxy = performDetailSortTypeEnumMaterialBuilderProxy;
	}

	/**
	 * 通关明细查询条件.
	 */
	private IMaterialBuilder passPortDetailRelateMaterialBuilderProxy;

	/**
	 * setPassPortDetailRelateMaterialBuilderProxy.
	 * 
	 * @param passPortDetailRelateMaterialBuilderProxy
	 *            通关明细查询条件
	 */
	public void setPassPortDetailRelateMaterialBuilderProxy(
			final IMaterialBuilder passPortDetailRelateMaterialBuilderProxy) {
		this.passPortDetailRelateMaterialBuilderProxy = passPortDetailRelateMaterialBuilderProxy;
	}

	/**
	 * 通关汇总查询条件.
	 */
	private IMaterialBuilder passPortSummaryRelateMaterialBuilderProxy;

	/**
	 * setPassPortSummaryRelateMaterialBuilderProxy.
	 * 
	 * @param passPortSummaryRelateMaterialBuilderProxy
	 *            通关汇总查询条件
	 */
	public void setPassPortSummaryRelateMaterialBuilderProxy(
			final IMaterialBuilder passPortSummaryRelateMaterialBuilderProxy) {
		this.passPortSummaryRelateMaterialBuilderProxy = passPortSummaryRelateMaterialBuilderProxy;
	}

	/**
	 * 班次.
	 */
	private IMaterialBuilder toursMaterialBuilderProxy;

	/**
	 * setToursMaterialBuilderProxy.
	 * 
	 * @param toursMaterialBuilderProxy
	 *            班次
	 */
	public void setToursMaterialBuilderProxy(
			final IMaterialBuilder toursMaterialBuilderProxy) {
		this.toursMaterialBuilderProxy = toursMaterialBuilderProxy;
	}
	/**
	 * 与电子合同有关参数
	 */
	private IMaterialBuilder orderEContractRelateMaterialBuilderProxy;
	
	/**
	 * 团相关
	 */
	private IMaterialBuilder travelGroupBuilderProxy;
	
	/**
	 * @param travelGroupBuilderProxy the travelGroupBuilderProxy to set
	 */
	public void setTravelGroupBuilderProxy(IMaterialBuilder travelGroupBuilderProxy) {
		this.travelGroupBuilderProxy = travelGroupBuilderProxy;
	}

	public void setOrderEContractRelateMaterialBuilderProxy(
			final IMaterialBuilder orderEContractRelateMaterialBuilderProxy) {
		this.orderEContractRelateMaterialBuilderProxy = orderEContractRelateMaterialBuilderProxy;
	}
	/**
	 * 默认SQL构建器原材料构建器.
	 */
	private IDefaultMaterialBuilder defaultMaterialBuilder;

	/**
	 * setDefaultMaterialBuilder.
	 * 
	 * @param defaultMaterialBuilder
	 *            默认SQL构建器原材料构建器
	 */
	public void setDefaultMaterialBuilder(
			final IDefaultMaterialBuilder defaultMaterialBuilder) {
		this.defaultMaterialBuilder = defaultMaterialBuilder;
	}

	/**
	 * 命令SQL构建器构建SQL.
	 * 
	 * @param builder
	 *            SQL构建器
	 */
	@Override
	public void order(final SQLBuilder builder) {
		this.order(builder, true);
		/*
		final SQlBuilderMaterial material = buildMaterial();
		builder.buildSQLSelectStatement(material);
		builder.buildSQLFromStatement(material);
		builder.buildSQLWhereStatement(material);
		builder.buildSQLGroupByStatement(material);
		builder.buildSQLOrderByStatement(material);
		builder.buildSQLPageIndexStatement(material);
		*/
	}
	public void order(final SQLBuilder builder,boolean pageable) {
		final SQlBuilderMaterial material = buildMaterial();
		builder.buildSQLSelectStatement(material);
		builder.buildSQLFromStatement(material);
		builder.buildSQLWhereStatement(material);
		builder.buildSQLGroupByStatement(material);
		builder.buildSQLOrderByStatement(material);
		if (pageable) {
			builder.buildSQLPageIndexStatement(material);
		}
	}

	/**
	 * buildMaterial.
	 * 
	 * @return SQlBuilderMaterial
	 */
	private SQlBuilderMaterial buildMaterial() {
		SQlBuilderMaterial material = new SQlBuilderMaterial();
		material = orderStatusMaterialBuilderProxy.buildMaterial(
				query.getStatus(), material);
		material = orderContentMaterialBuilderProxy.buildMaterial(
				query.getContent(), material);
		material = excludedContentMaterialBuilderProxy.buildMaterial(
				query.getExcludedContent(), material);
		material = orderTimeRangeMaterialBuilderProxy.buildMaterial(
				query.getOrderTimeRange(), material);
		material = orderIdentityMaterialBuilderProxy.buildMaterial(
				query.getOrderIdentity(), material);
		material = sortTypeEnumMaterialBuilderProxy.buildMaterial(
				query.getTypeList(), material);
		material = pageIndexMaterialBuilderProxy.buildMaterial(
				query.getPageIndex(), material);
		material = invoiceRelateMaterialBuilderProxy.buildMaterial(
				query.getInvoiceRelate(), material);
		material = saleServiceRelateMaterialBuilderProxy.buildMaterial(
				query.getSaleServiceRelate(), material);
		material = settlementQueueRelateMaterialBuilderProxy.buildMaterial(
				query.getSettlementQueueRelate(), material);
		material = settlementItemRelateMaterialBuilderProxy.buildMaterial(
				query.getSettlementItemRelate(), material,
				isSettlementItemBusiness());
		material = finishSettlementItemRelateMaterialBuilderProxy
				.buildMaterial(query.getFinishSettlementItemRelate(), material);
		material = ordSettlementRelateMaterialBuilderProxy.buildMaterial(
				query.getOrdSettlementRelate(), material);
		material = metaPerformRelateMaterialBuilderProxy.buildMaterial(
				query.getMetaPerformRelate(), material);
		material = performDetailRelateMaterialBuilderProxy.buildMaterial(
				query.getPerformDetailRelate(), material);
		material = performDetailSortTypeEnumMaterialBuilderProxy.buildMaterial(
				query.getPerformTypeList(), material);
		material = passPortDetailRelateMaterialBuilderProxy.buildMaterial(
				query.getPassPortDetailRelate(), material);
		material = passPortSummaryRelateMaterialBuilderProxy.buildMaterial(
				query.getPassPortSummaryRelate(), material);
		material = orderTrackMaterialBuilderProxy.buildMaterial(
				query.getOrderTrackRelate(), material);
//		material = toursMaterialBuilderProxy.buildMaterial(
//				query.getToursRelate(), material);
		material = travelGroupBuilderProxy.buildMaterial(query.getTravelGroupStatus(),material);
		material = orderEContractRelateMaterialBuilderProxy.buildMaterial(
				query.getEContractRelate(), material);
		material = defaultMaterialBuilder.buildMaterial(isSettlementBusiness(),
				isInvoiceBusiness(), isSettlementItemBusiness(),
				isOrdOrderItemMetaBusiness(), isOrdSettlementBusiness(),
				isOrdOrderBusiness(), isPassPortDetailBusiness(),
				isPassPortSummaryBusiness(), isPerformDetailBusiness(),
				material);
		return material;
	}
}
