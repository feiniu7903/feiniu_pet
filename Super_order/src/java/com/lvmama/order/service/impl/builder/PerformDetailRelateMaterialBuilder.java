package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.PerformDetailRelate;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 履行明细查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class PerformDetailRelateMaterialBuilder implements IMaterialBuilder,
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
		final PerformDetailRelate performDetailRelate = (PerformDetailRelate) obj;
		if (UtilityTool.isValid(performDetailRelate.getSupplierName())) {
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
			if(performDetailRelate.isPassPort()){
			material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
			}
			material.getConditionSet().add(
					"SUP_SUPPLIER.SUPPLIER_NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getSupplierName()) + "%'");
		}
		if (UtilityTool.isValid(performDetailRelate.getBranchName())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"META_PRODUCT_BRANCH.PRODUCT_NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getBranchName()) + "%'");
		}
		if (UtilityTool.isValid(performDetailRelate.getVisitTimeStart())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(performDetailRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(performDetailRelate.getVisitTimeEnd())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(performDetailRelate.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(performDetailRelate.getPerformStatus())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.PERFORM_STATUS = '"
							+ performDetailRelate.getPerformStatus().toString()
							+ "'");
		}
		if (UtilityTool.isValid(performDetailRelate.getTargetId())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"SUP_PERFORM_TARGET.TARGET_ID = "
							+ performDetailRelate.getTargetId());
		}
		
		if (UtilityTool.isValid(performDetailRelate.getOrderId())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = " + performDetailRelate.getOrderId());
		}
		if (UtilityTool.isValid(performDetailRelate.getContactName())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
//			material.getTableSet().add(ORD_PERSON);
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
//			material.getConditionSet().add(
//					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
//			material.getConditionSet()
//					.add("ORD_PERSON.PERSON_TYPE = 'CONTACT'");
			material.getConditionSet().add(
					"ORD_PERSON.NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getContactName()) + "%'");
		}
		if (UtilityTool.isValid(performDetailRelate.getContactMobile())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
//			material.getTableSet().add(ORD_PERSON);
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
//			material.getConditionSet().add(
//					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
//			material.getConditionSet()
//					.add("ORD_PERSON.PERSON_TYPE = 'CONTACT'");
			material.getConditionSet().add(
					"ORD_PERSON.MOBILE = '"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getContactMobile()) + "'");
		}
		if (UtilityTool.isValid(performDetailRelate.getPassPortUserId())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet()
					.add("PASS_PORT_USER.PASS_PORT_USER_ID = USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet()
					.add("USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID = "
							+ performDetailRelate.getPassPortUserId());
		}
		if (UtilityTool.isValid(performDetailRelate.getCode())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet()
					.add("PASS_PORT_USER.PASS_PORT_USER_ID = USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet()
					.add("USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					" (PASS_CODE.CODE='"+performDetailRelate.getCode()+"' OR PASS_CODE.ADD_CODE='"+performDetailRelate.getCode()+"') ");
		}
		
		if (UtilityTool.isValid(performDetailRelate.getMetaProductBranchId())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet()
					.add("PASS_PORT_USER.PASS_PORT_USER_ID = USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet()
					.add("USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					" ORD_ORDER_ITEM_META.META_BRANCH_ID ="+performDetailRelate.getMetaProductBranchId());
		}
		if (UtilityTool.isValid(performDetailRelate.getOrderStatus())) {
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
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().remove("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ performDetailRelate.getOrderStatus().toString()
							+ "')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
							+ performDetailRelate.getOrderStatus().toString()
							+ "')))");
		}
		
		if (UtilityTool.isValid(performDetailRelate.getPersonType())) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(META_PERFORM);
			material.getTableSet().add(SUP_PERFORM_TARGET);
			material.getTableSet().add(PASS_PORT_CODE);
			material.getTableSet().add(PASS_CODE);
//			material.getTableSet().add(ORD_PERSON);
			material.getTableSet().add("(SELECT OBJECT_ID,NAME,MOBILE,CERT_NO ,OBJECT_TYPE FROM ORD_PERSON WHERE PERSON_TYPE = '"+performDetailRelate.getPersonType()+"') ORD_PERSON");
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
			if(material.isDefaultOrderStauts()){
				material.getConditionSet()
				.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			}
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if(performDetailRelate.isPassPort()){
				material.getConditionSet().add(" SUP_PERFORM_TARGET.TARGET_ID(+) =PASS_PORT_CODE.TARGET_ID");
				}
			material.getConditionSet().add(" ORD_PERSON.OBJECT_ID (+)= ORD_ORDER.ORDER_ID");
		}
		
		return material;
	}
	
	public void builderPerformSQL(final Object obj,
			final SQlBuilderMaterial material) {
		final PerformDetailRelate performDetailRelate = (PerformDetailRelate) obj;
		if (UtilityTool.isValid(performDetailRelate.getSupplierName())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"B.SUPPLIER_NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getSupplierName()) + "%'");
		}
		if (UtilityTool.isValid(performDetailRelate.getBranchName())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"META_PRODUCT_BRANCH.BRANCH_NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getBranchName()) + "%'");
		}
		if (UtilityTool.isValid(performDetailRelate.getVisitTimeStart())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"B.VISIT_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(performDetailRelate
									.getVisitTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(performDetailRelate.getVisitTimeEnd())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"B.VISIT_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(performDetailRelate.getVisitTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(performDetailRelate.getPerformStatus())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"B.PERFORM_STATUS = '"
							+ performDetailRelate.getPerformStatus().toString()
							+ "'");
		}
		if (UtilityTool.isValid(performDetailRelate.getTargetId())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")

			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"A.TARGET_ID = "
							+ performDetailRelate.getTargetId());
		}
		if (UtilityTool.isValid(performDetailRelate.getOrderStatus())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")

			/*
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			*/
				.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
			+ performDetailRelate.getOrderStatus().toString()
			+ "')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED', '"
			+ performDetailRelate.getOrderStatus().toString()
			+ "')))")
			
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")
			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");

		}
		if (UtilityTool.isValid(performDetailRelate.getOrderId())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

			.append("AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"B.ORDER_ID = " + performDetailRelate.getOrderId());
		}
		if (UtilityTool.isValid(performDetailRelate.getContactName())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

			.append(
					" and ORD_PERSON.NAME LIKE '%"
							+ SecurityTool
									.preventSqlInjection(performDetailRelate
											.getContactName()) + "%'")
			
		   .append(" AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
		}
		if (UtilityTool.isValid(performDetailRelate.getContactMobile())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

			.append(
					" AND ORD_PERSON.MOBILE = '"
					+ SecurityTool
							.preventSqlInjection(performDetailRelate
									.getContactMobile()) + "'")
			
		   .append(" AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");

		}
		if (UtilityTool.isValid(performDetailRelate.getPassPortUserId())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

		   .append(" AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID = "
							+ performDetailRelate.getPassPortUserId());
		}
		if (UtilityTool.isValid(performDetailRelate.getCode())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

		   .append(" AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					" (A.CODE='"+performDetailRelate.getCode()+"' OR A.ADD_CODE='"+performDetailRelate.getCode()+"') ");
		}
		
		if (UtilityTool.isValid(performDetailRelate.getMetaProductBranchId())) {
			material.getTableSet().add(PASS_PORT_USER);
			material.getTableSet().add(META_PRODUCT_BRANCH);
			material.getTableSet().add(USER_RELATE_SUPPLIER_PRODUCT);
			String A = new StringBuilder()
			.append("(SELECT SUP_PERFORM_TARGET.TARGET_ID, SUP_PERFORM_TARGET.CERTIFICATE_TYPE, PASS_PORT_CODE.USED_TIME,META_PERFORM.META_BRANCH_ID,")
			.append("PASS_PORT_CODE.CODE_ID,PASS_CODE.ORDER_ID,PASS_CODE.CODE_ID, PASS_CODE.ADD_CODE ,")
			.append("PASS_PORT_CODE.STATUS FROM SUP_PERFORM_TARGET,PASS_PORT_CODE,PASS_CODE,META_PERFORM")
			.append("WHERE SUP_PERFORM_TARGET.TARGET_ID =PASS_PORT_CODE.TARGET_ID(+)")
			.append("AND PASS_PORT_CODE.CODE_ID=PASS_CODE.CODE_ID")
			.append("AND META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID(+)")
			.append("AND  PASS_CODE.REAPPLY IS NULL) A").toString();
			material.getTableSet().add(A);
			
			String B = new StringBuilder()
			.append("(SELECT ORD_ORDER_ITEM_META.ORDER_ID,")
			.append("ORD_ORDER_ITEM_META.META_BRANCH_ID,")
			.append("ORD_ORDER_ITEM_META.ADULT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.PRODUCT_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.QUANTITY,")
			.append("ORD_ORDER_ITEM_META.CHILD_QUANTITY,")
			.append("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID,")
			.append("ORD_ORDER_ITEM_META.FAX_MEMO,")
			.append("ORD_ORDER.PAYMENT_TARGET,")
			.append("ORD_ORDER.OUGHT_PAY,")
			.append("ORD_ORDER.ORDER_STATUS,")
			.append("ORD_ORDER.CREATE_TIME,")
			.append("ORD_ORDER_ITEM_META.VISIT_TIME")
			.append("ORD_PERSON.NAME,")
			.append("ORD_PERSON.MOBILE")
			.append(" FROM ORD_ORDER_ITEM_META,ORD_ORDER,ORD_ORDER_ITEM_PROD,SUP_SUPPLIER,ORD_PERSON")
			.append(" WHERE  ORD_ORDER.ORDER_ID=ORD_ORDER_ITEM_PROD.ORDER_ID")
			.append(" AND  ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID=ORD_ORDER_ITEM_META.ORDER_ITEM_ID")
			.append(" AND ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'")
			.append(" AND ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND")
			.append(" ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR")
			.append("(ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND")
			.append(" ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND")
			.append(" ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))")
			.append(" AND ORD_ORDER_ITEM_META.SUPPLIER_ID =SUP_SUPPLIER.SUPPLIER_ID")

		   .append(" AND ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'")
           .append(" AND ORD_PERSON.PERSON_TYPE = 'CONTACT'")
           .append(" AND ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID)) B").toString();
             
			material.getTableSet().add(B);

			material.getConditionSet().add(
					"PASS_PORT_USER.PASS_PORT_USER_ID =USER_RELATE_SUPPLIER_PRODUCT.PASS_PORT_USER_ID");
			material.getConditionSet().add(
					"B.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet().add(
					"USER_RELATE_SUPPLIER_PRODUCT.META_BRANCH_ID =META_PRODUCT_BRANCH.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.META_BRANCH_ID(+)=B.META_BRANCH_ID");
			material.getConditionSet()
					.add("A.ORDER_ID(+)=B.ORDER_ID");
			
			material.getConditionSet().add(
					" (A.CODE='"+performDetailRelate.getCode()+"' OR A.ADD_CODE='"+performDetailRelate.getCode()+"') ");
			
			material.getConditionSet().add(
					" B.META_BRANCH_ID ="+performDetailRelate.getMetaProductBranchId());
		}
		
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
