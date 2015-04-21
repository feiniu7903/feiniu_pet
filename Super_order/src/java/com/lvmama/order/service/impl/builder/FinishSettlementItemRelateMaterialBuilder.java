package com.lvmama.order.service.impl.builder;


import com.lvmama.comm.bee.vo.ord.CompositeQuery.FinishSettlementItemRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 结算管理完成的子项查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class FinishSettlementItemRelateMaterialBuilder implements
		IMaterialBuilder, TableName {
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
		final FinishSettlementItemRelate finishSettlementItemRelate = (FinishSettlementItemRelate) obj;
		if (UtilityTool.isValid(finishSettlementItemRelate.getSubSettlementId())) {
			material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet().add(
					"ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = "
							+ finishSettlementItemRelate.getSubSettlementId());
		}
		if (UtilityTool.isValid(finishSettlementItemRelate.getSettlementId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
			material.getTableSet().add(ORD_SUB_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID = ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = ORD_SUB_SETTLEMENT.SUB_SETTLEMENT_ID");
			material.getConditionSet().add(
					"ORD_SUB_SETTLEMENT.SETTLEMENT_ID = "
							+ finishSettlementItemRelate.getSettlementId());
		}
		if (UtilityTool.isValid(finishSettlementItemRelate.getVisitTimeStart())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
			material.getTableSet().add(ORD_SUB_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = ORD_SUB_SETTLEMENT.SUB_SETTLEMENT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(finishSettlementItemRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(finishSettlementItemRelate.getVisitTimeEnd())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
			material.getTableSet().add(ORD_SUB_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.SUB_SETTLEMENT_ID = ORD_SUB_SETTLEMENT.SUB_SETTLEMENT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(finishSettlementItemRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(finishSettlementItemRelate.getOrderId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
			material.getConditionSet()
					.add("ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = "
							+ finishSettlementItemRelate.getOrderId());
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
