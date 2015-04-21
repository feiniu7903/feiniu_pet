package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.MetaPerformRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 履行对象查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class MetaPerformRelateMaterialBuilder implements IMaterialBuilder,
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
		final MetaPerformRelate metaPerformRelate = (MetaPerformRelate) obj;
		if (UtilityTool.isValid(metaPerformRelate.getTargetId())) {
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("META_PERFORM.META_PRODUCT_ID = ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			if (1 == metaPerformRelate.getTargetId().length) {
				material.getConditionSet().add(
						"META_PERFORM.TARGET_ID = '"
								+ metaPerformRelate.getTargetId()[0].toString()
								+ "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("META_PERFORM.TARGET_ID IN (");
				for (int i = 0; i < metaPerformRelate.getTargetId().length; i++) {
					stringBuilder.append("'"
							+ metaPerformRelate.getTargetId()[i].toString()
							+ "'");
					if (metaPerformRelate.getTargetId().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		if (UtilityTool.isValid(metaPerformRelate.getOrderId())) {
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("META_PERFORM.META_PRODUCT_ID = ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = "
							+ metaPerformRelate.getOrderId());
		}
		if (UtilityTool.isValid(metaPerformRelate.getSupplierId())) {
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("META_PERFORM.META_PRODUCT_ID = ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.SUPPLIER_ID = "
							+ metaPerformRelate.getSupplierId());
		}
		if (UtilityTool.isValid(metaPerformRelate.getMetaProductId())) {
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("META_PERFORM.META_PRODUCT_ID = ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.META_PRODUCT_ID = "
							+ metaPerformRelate.getMetaProductId());
		}
		if (UtilityTool.isValid(metaPerformRelate.getMetaBranchId())) {
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet()
					.add("META_PERFORM.META_PRODUCT_ID = ORD_ORDER_ITEM_META.META_PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.META_BRANCH_ID = "
							+ metaPerformRelate.getMetaBranchId());
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
