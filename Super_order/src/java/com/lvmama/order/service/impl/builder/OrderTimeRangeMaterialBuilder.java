package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderTimeRange;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与订单时间范围有关的参数，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderTimeRangeMaterialBuilder implements IMaterialBuilder,
		TableName {
	/**
	 * 构建{@link SQlBuilderMaterial}.
	 * 
	 * @param obj
	 *            Object
	 * @param material
	 *            material
	 * @return {@link SQlBuilderMaterial}
	 */
	@Override
	public SQlBuilderMaterial buildMaterial(final Object obj,
			final SQlBuilderMaterial material) {
		final OrderTimeRange orderTimeRange = (OrderTimeRange) obj;
		if (UtilityTool.isValid(orderTimeRange.getCreateTimeStart())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.CREATE_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getCreateTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getCreateTimeEnd())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.CREATE_TIME < TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getCreateTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getOrdOrderVisitTimeStart())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange
									.getOrdOrderVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (null != orderTimeRange.getOrdOrderVisitTimeEnd()) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange
									.getOrdOrderVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getOrdOrderItemProdVisitTimeStart())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange
									.getOrdOrderItemProdVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getOrdOrderItemProdVisitTimeEnd())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.VISIT_TIME < TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange
									.getOrdOrderItemProdVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getPayTimeStart())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getPayTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getPayTimeEnd())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_TIME < TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getPayTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}

		// 首处理时间 shangzhengyuan
		if (UtilityTool.isValid(orderTimeRange.getDealTimeStart())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.DEAL_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getDealTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(orderTimeRange.getDealTimeEnd())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.DEAL_TIME < TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getDealTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		
		if (null != orderTimeRange.getCreateInvoiceStart()) {
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet()
					.add(
							"ORD_INVOICE.CREATE_TIME>= TO_DATE('"
									+ UtilityTool.formatDate(orderTimeRange
											.getCreateInvoiceStart())
									+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		
		if(null!=orderTimeRange.getCreateInvoiceEnd()){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet()
					.add(
							"ORD_INVOICE.CREATE_TIME<= TO_DATE('"
									+ UtilityTool.formatDate(orderTimeRange
											.getCreateInvoiceEnd())
									+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		
		if(null!=orderTimeRange.getBillInvoiceStart()){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add(
					"ORD_INVOICE.BILL_DATE>= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getBillInvoiceStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if(null!=orderTimeRange.getBillInvoiceEnd()){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add(
					"ORD_INVOICE.BILL_DATE<= TO_DATE('"
							+ UtilityTool.formatDate(orderTimeRange.getBillInvoiceEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		return material;
	}

	/**
	 * 构建{@link SQlBuilderMaterial}.
	 * 
	 * @param obj
	 *            Object
	 * @param material
	 *            material
	 * @param businessflag
	 *            businessflag
	 * @return {@link SQlBuilderMaterial}
	 */
	@Override
	public SQlBuilderMaterial buildMaterial(final Object obj,
			final SQlBuilderMaterial material, final boolean businessflag) {
		return material;
	}
}
