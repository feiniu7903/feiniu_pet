package com.lvmama.order.service.impl.builder;

import java.util.List;

import com.lvmama.comm.bee.vo.ord.Tours;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 班次，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class ToursMaterialBuilder implements IMaterialBuilder, TableName {
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
		List<Tours> toursRelate = (List<Tours>) obj;
		material.getTableSet().add(ORD_ORDER);
		material.getTableSet().add(ORD_ORDER_ITEM_PROD);
		material.getConditionSet().add(
				"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");

		StringBuilder sb = new StringBuilder();
		for (Tours tours : toursRelate) {
			sb.append("(ORD_ORDER_ITEM_PROD.PRODUCT_ID = "
					+ tours.getProductId()
					+ " AND ORD_ORDER_ITEM_PROD.VISIT_TIME = TO_DATE('"
					+ UtilityTool.formatDate(tours.getVisitDate())
					+ "','YYYY-MM-DD HH24:MI:SS')) OR ");
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 4);
			sb.insert(0, '(').append(')');
			material.getConditionSet().add(sb.toString());
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
