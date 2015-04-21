package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortSummaryRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 通关汇总查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class PassPortSummaryRelateMaterialBuilder implements IMaterialBuilder,
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
		final PassPortSummaryRelate passPortSummaryRelate = (PassPortSummaryRelate) obj;
		if (UtilityTool.isValid(passPortSummaryRelate.getMetaProductBranchId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.META_BRANCH_ID = "
							+ passPortSummaryRelate.getMetaProductBranchId());
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
		}
		if (UtilityTool.isValid(passPortSummaryRelate.getVisitTimeStart())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(passPortSummaryRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
		}
		if (UtilityTool.isValid(passPortSummaryRelate.getVisitTimeEnd())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(passPortSummaryRelate
									.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
		}
		if (UtilityTool.isValid(passPortSummaryRelate.getPassPortUserId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("PASS_PORT_USER.PASS_PORT_USER_ID = USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet()
					.add("USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID = "
							+ passPortSummaryRelate.getPassPortUserId());
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
		}
		if (UtilityTool.isValid(passPortSummaryRelate.getOrderStatus())) {
			material.setDefaultOrderStauts(false);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet().remove("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ passPortSummaryRelate.getOrderStatus().toString()
							+ "')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ passPortSummaryRelate.getOrderStatus().toString()
							+ "')))");
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
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
