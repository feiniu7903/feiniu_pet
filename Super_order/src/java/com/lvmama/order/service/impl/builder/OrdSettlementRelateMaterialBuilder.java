package com.lvmama.order.service.impl.builder;


import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrdSettlementRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 结算单查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrdSettlementRelateMaterialBuilder implements IMaterialBuilder,
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
		final OrdSettlementRelate ordSettlementRelate = (OrdSettlementRelate) obj;
		if (UtilityTool.isValid(ordSettlementRelate.getTargetId())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getTableSet().add(SUP_SETTLEMENT_TARGET);
			material.getConditionSet()
					.add("ORD_SETTLEMENT.TARGET_ID = SUP_SETTLEMENT_TARGET.TARGET_ID");
			material.getConditionSet().add(
					"SUP_SETTLEMENT_TARGET.TARGET_ID = "
							+ ordSettlementRelate.getTargetId());
		}
		if (UtilityTool.isValid(ordSettlementRelate.getMetaProductId())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getTableSet().add(ORD_SUB_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_SETTLEMENT.SETTLEMENT_ID = ORD_SUB_SETTLEMENT.SETTLEMENT_ID");
			material.getConditionSet().add(
					"ORD_SUB_SETTLEMENT.META_PRODUCT_ID = "
							+ ordSettlementRelate.getMetaProductId());
		}
		if (UtilityTool.isValid(ordSettlementRelate.getSettlementId())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getConditionSet().add(
					"ORD_SETTLEMENT.SETTLEMENT_ID = "
							+ ordSettlementRelate.getSettlementId());
		}
		if (UtilityTool.isValid(ordSettlementRelate.getCreateTimeStart())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getConditionSet().add(
					"ORD_SETTLEMENT.CREATE_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(ordSettlementRelate
									.getCreateTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(ordSettlementRelate.getCreateTimeEnd())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_SETTLEMENT.CREATE_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(ordSettlementRelate.getCreateTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		//结算状态  add by lijp 20111031
		if (UtilityTool.isValid(ordSettlementRelate.getSettlementStatus())) {
			material.getTableSet().add(ORD_SETTLEMENT);
			material.getConditionSet()
					.add("ORD_SETTLEMENT.STATUS = '"
							+ ordSettlementRelate.getSettlementStatus()
							+ "'");
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
