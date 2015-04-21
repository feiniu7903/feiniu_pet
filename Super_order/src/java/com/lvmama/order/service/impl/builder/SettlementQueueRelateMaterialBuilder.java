package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.SettlementQueueRelate;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 结算管理查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class SettlementQueueRelateMaterialBuilder implements IMaterialBuilder,
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
		final SettlementQueueRelate settlementQueueRelate = (SettlementQueueRelate) obj;
		if (UtilityTool.isValid(settlementQueueRelate.getMetaProductId())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID = "
							+ settlementQueueRelate.getMetaProductId());
		}
		if (UtilityTool.isValid(settlementQueueRelate.getTargetId())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"ORD_SETTLEMENT_QUEUE.TARGET_ID = "
							+ settlementQueueRelate.getTargetId());
		}
		if (UtilityTool.isValid(settlementQueueRelate.getSettlementPeriod())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(SUP_SETTLEMENT_TARGET);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.TARGET_ID = SUP_SETTLEMENT_TARGET.TARGET_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"SUP_SETTLEMENT_TARGET.SETTLEMENT_PERIOD = '"
							+ SecurityTool
									.preventSqlInjection(settlementQueueRelate
											.getSettlementPeriod()) + "'");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getPay2Lvmama())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(META_PRODUCT);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"META_PRODUCT.PAY_TO_LVMAMA = '"
							+ SecurityTool
									.preventSqlInjection(settlementQueueRelate
											.getPay2Lvmama()) + "'");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getPay2Supplier())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(META_PRODUCT);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"META_PRODUCT.PAY_TO_SUPPLIER = '"
							+ SecurityTool
									.preventSqlInjection(settlementQueueRelate
											.getPay2Supplier()) + "'");
		}

		if (UtilityTool.isValid(settlementQueueRelate.getVisitTimeStart())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID=ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID =ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(settlementQueueRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getVisitTimeEnd())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID=ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID =ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(settlementQueueRelate
									.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getOrderPayStartDate())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_PAYMENT);
			material.getConditionSet()
			         .add("ORD_PAYMENT.ORDER_ID=ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
	                 .add("ORD_PAYMENT.STATUS = 'SUCCESS'");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID=ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID =ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					"ORD_PAYMENT.CALLBACK_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(settlementQueueRelate.getOrderPayStartDate())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getOrderPayEndDate())) {
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_PAYMENT);
			material.getConditionSet()
			         .add("ORD_PAYMENT.ORDER_ID=ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
	                 .add("ORD_PAYMENT.STATUS = 'SUCCESS'");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID=ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID =ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add(
					" ORD_PAYMENT.CALLBACK_TIME<= TO_DATE('"
							+ UtilityTool.formatDate(settlementQueueRelate.getOrderPayEndDate())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementQueueRelate.getSettlementCommpany())){
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_PAYMENT);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getConditionSet()
			         .add("ORD_PAYMENT.ORDER_ID=ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
	                 .add("ORD_PAYMENT.STATUS = 'SUCCESS'");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID=ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID =ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet().add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
			material.getConditionSet().add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet().add("SUP_SUPPLIER.COMPANY_ID = '"+settlementQueueRelate.getSettlementCommpany()+"'");
			
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
