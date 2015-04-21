package com.lvmama.order.service.impl.builder;

import java.util.List;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailSortTypeEnum;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与履行明细排序类型有关的枚举，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class PerformDetailSortTypeEnumMaterialBuilder implements
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
		final List<PerformDetailSortTypeEnum> performTypeList = (List<PerformDetailSortTypeEnum>) obj;
		for (PerformDetailSortTypeEnum pst : performTypeList) {
			if (pst.equals(PerformDetailSortTypeEnum.ORDER_ID_ASC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.ORDER_ID ASC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.ORDER_ID_DESC)) {
				material.getTableSet().add(ORD_ORDER);
				material.getOrderbySet().add("ORD_ORDER.ORDER_ID DESC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.META_PRODUCT_NAME_ASC)) {
				material.getTableSet().add(META_PRODUCT);
				material.getOrderbySet().add("META_PRODUCT.PRODUCT_NAME ASC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.META_PRODUCT_NAME_DESC)) {
				material.getTableSet().add(META_PRODUCT);
				material.getOrderbySet().add("META_PRODUCT.PRODUCT_NAME DESC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.CONTACT_NAME_ASC)) {
				material.getTableSet().add(ORD_ORDER);
//				material.getTableSet().add(ORD_PERSON);
//				material.getConditionSet().add(
//						"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
				material.getConditionSet().add(
						"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
//				material.getConditionSet().add(
//						"ORD_PERSON.PERSON_TYPE = 'CONTACT'");
				material.getOrderbySet().add("ORD_PERSON.NAME ASC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.CONTACT_NAME_DESC)) {
				material.getTableSet().add(ORD_ORDER);
//				material.getTableSet().add(ORD_PERSON);
//				material.getConditionSet().add(
//						"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
				material.getConditionSet().add(
						"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
//				material.getConditionSet().add(
//						"ORD_PERSON.PERSON_TYPE = 'CONTACT'");
				material.getOrderbySet().add("ORD_PERSON.NAME DESC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.USED_TIME_ASC)) {
				material.getTableSet().add(PASS_PORT_CODE);
				material.getTableSet().add(PASS_CODE);
				material.getConditionSet().add(
						"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
				material.getConditionSet().add(" PASS_CODE.Reapply is null ");
				material.getOrderbySet().add("PASS_PORT_CODE.USED_TIME ASC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.USED_TIME_DESC)) {
				material.getTableSet().add(PASS_PORT_CODE);
				material.getTableSet().add(PASS_CODE);
				material.getConditionSet().add(
						"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
				material.getConditionSet().add(" PASS_CODE.Reapply is null ");
				material.getOrderbySet().add("PASS_PORT_CODE.USED_TIME DESC");
			}
			if (pst.equals(PerformDetailSortTypeEnum.STATUS)) {
				material.getTableSet().add(PASS_PORT_CODE);
				material.getTableSet().add(PASS_CODE);
				material.getConditionSet().add(
						"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
				material.getConditionSet().add(" PASS_CODE.Reapply is null ");
				material.getOrderbySet()
						.add("DECODE(PASS_PORT_CODE.STATUS, 'USED', 2, 'UNUSED', 1, 3)");
			}
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
