package com.lvmama.order.service.impl.builder;


import com.lvmama.comm.bee.vo.ord.CompositeQuery.SettlementItemRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 结算管理子项查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class SettlementItemRelateMaterialBuilder implements IMaterialBuilder,
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
		final SettlementItemRelate settlementItemRelate = (SettlementItemRelate) obj;
		
		  
		
		if (UtilityTool.isValid(settlementItemRelate.getMetaProductId())) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
				.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
				.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet().add(
					"ORD_SETTLEMENT_QUEUE.META_PRODUCT_ID = "
							+ settlementItemRelate.getMetaProductId());
		}
		if (UtilityTool.isValid(settlementItemRelate.getTargetId())) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
		 	*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet().add(
					"ORD_SETTLEMENT_QUEUE.TARGET_ID = "
							+ settlementItemRelate.getTargetId());
		}
		if (UtilityTool.isValid(settlementItemRelate.getVisitTimeStart())) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
		 	*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(settlementItemRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementItemRelate.getVisitTimeEnd())) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
		 	*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(settlementItemRelate.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(settlementItemRelate.getOrderId())) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = "
							+ settlementItemRelate.getOrderId());
		}
		if (settlementItemRelate.getIncludeRefundmentOrder() && businessflag) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			*/
			this.initSQlBuilderMaterial(material);
			material.getTableSet().add(ORD_REFUNDMENT);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_REFUNDMENT.ORDER_ID");
		}
		//add by lijp 20111103
		if (settlementItemRelate.isDeleted()) {
			/*
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
			material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
			material.getConditionSet()
					.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			*/
			this.initSQlBuilderMaterial(material);
			material.getConditionSet().add("(ORD_SETTLEMENT_QUEUE_ITEM.DELETED != 'true' or ORD_SETTLEMENT_QUEUE_ITEM.DELETED IS NULL)");
		} 
		return material;
	}
	
	private void initSQlBuilderMaterial(SQlBuilderMaterial material) {
		material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
		material.getTableSet().add(ORD_SETTLEMENT_QUEUE_ITEM);
		material.getTableSet().add(ORD_ORDER_ITEM_META);
		material.getConditionSet()
				.add("ORD_SETTLEMENT_QUEUE.SETTLEMENT_QUEUE_ID = ORD_SETTLEMENT_QUEUE_ITEM.SETTLEMENT_QUEUE_ID");
		material.getConditionSet()
				.add("ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
	}
}
