package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.PassPortDetailRelate;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 通关明细查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class PassPortDetailRelateMaterialBuilder implements IMaterialBuilder,
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
		final PassPortDetailRelate passPortDetailRelate = (PassPortDetailRelate) obj;
		if (UtilityTool.isValid(passPortDetailRelate.getMetaProductBranchId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(passPortDetailRelate.isPassPort()){
			material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
			}
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.META_BRANCH_ID = "
							+ passPortDetailRelate.getMetaProductBranchId());
		}
		if (UtilityTool.isValid(passPortDetailRelate.getVisitTimeStart())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(passPortDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(passPortDetailRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(passPortDetailRelate.getVisitTimeEnd())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");

			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(passPortDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(passPortDetailRelate.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(passPortDetailRelate.getTargetId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(passPortDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"SUP_PERFORM_TARGET.TARGET_ID = "
							+ passPortDetailRelate.getTargetId());
		}
		if (UtilityTool.isValid(passPortDetailRelate.getPassPortUserId())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			material.getConditionSet()
					.add("PASS_PORT_USER.PASS_PORT_USER_ID = USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet()
					.add("USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			if(passPortDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID = "
							+ passPortDetailRelate.getPassPortUserId());
		}
		
		if (UtilityTool.isValid(passPortDetailRelate.getOrderStatus())) {
			material.setDefaultOrderStauts(false);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
			material.getConditionSet().add(
					"PASS_PORT_CODE.CODE_ID(+) = PASS_CODE.CODE_ID");
			material.getConditionSet().add(" PASS_CODE.Reapply is null ");
			material.getConditionSet().add(
					"PASS_CODE.ORDER_ID(+) = ORD_ORDER.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(passPortDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().remove("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ passPortDetailRelate.getOrderStatus().toString()
							+ "')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ passPortDetailRelate.getOrderStatus().toString()
							+ "')))");
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
