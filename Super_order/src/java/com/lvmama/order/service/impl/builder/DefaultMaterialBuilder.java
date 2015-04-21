package com.lvmama.order.service.impl.builder;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * 默认SQL构建器原材料构建器.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class DefaultMaterialBuilder implements IDefaultMaterialBuilder,
		TableName {
	/**
	 * LOG.
	 */
	private static final Log LOG = LogFactory
			.getLog(DefaultMaterialBuilder.class);

	/**
	 * buildMaterial.
	 * 
	 * @param settlementBusinessFlag
	 *            settlementBusinessFlag
	 * @param invoiceBusinessFlag
	 *            invoiceBusinessFlag
	 * @param settlementItemBusinessFlag
	 *            settlementItemBusinessFlag
	 * @param ordOrderItemMetaBusinessFlag
	 *            ordOrderItemMetaBusinessFlag
	 * @param ordSettlementBusinessFlag
	 *            ordSettlementBusinessFlag
	 * @param ordOrderBusinessFlag
	 *            ordOrderBusinessFlag
	 * @param passPortDetailBusinessFlag
	 *            passPortDetailBusinessFlag
	 * @param passPortSummaryBusinessFlag
	 *            passPortSummaryBusinessFlag
	 * @param performDetailBusinessFlag
	 *            performDetailBusinessFlag
	 * @param material
	 *            {@link SQlBuilderMaterial}
	 * @return {@link SQlBuilderMaterial}
	 */
	@Override
	public SQlBuilderMaterial buildMaterial(
			final boolean settlementBusinessFlag,
			final boolean invoiceBusinessFlag,
			final boolean settlementItemBusinessFlag,
			final boolean ordOrderItemMetaBusinessFlag,
			final boolean ordSettlementBusinessFlag,
			final boolean ordOrderBusinessFlag,
			final boolean passPortDetailBusinessFlag,
			final boolean passPortSummaryBusinessFlag,
			final boolean performDetailBusinessFlag,
			final SQlBuilderMaterial material) {
		if (settlementBusinessFlag) {
			if (material.getTableSet().isEmpty()) {
				material.getTableSet().add(ORD_SETTLEMENT_QUEUE);
				material.getConditionSet()
						.add(ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default from: " + ORD_SETTLEMENT_QUEUE);
					LOG.debug("add default where: "
							+ ORD_SETTLEMENT_QUEUE_ITEM_EXISTS);
				}
			}
		}
		if (invoiceBusinessFlag) {
			if (material.getTableSet().isEmpty()) {
				material.getTableSet().add(ORD_INVOICE);
				material.getConditionSet().add("1 = 1");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default from: " + ORD_INVOICE);
					LOG.debug("add default where: 1 = 1");
				}
			}else if(material.getTableSet().contains(ORD_ORDER)){//不为空的情况下
				material.getTableSet().add(ORD_INVOICE);
				material.getTableSet().add(ORD_INVOICE_RELATION);
				material.getConditionSet().add("ORD_INVOICE.INVOICE_ID = ORD_INVOICE_RELATION.INVOICE_ID(+)");
				material.getConditionSet().add("ORD_INVOICE_RELATION.ORDER_ID = ORD_ORDER.ORDER_ID(+)");
			}
		}
		if (settlementItemBusinessFlag) {
			if (material.getTableSet().isEmpty()) {
				material.getTableSet().add(ORD_SUB_SETTLEMENT_ITEM);
				material.getTableSet().add(ORD_ORDER_ITEM_META);
				material.getConditionSet()
						.add("ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default from: " + ORD_SUB_SETTLEMENT_ITEM);
					LOG.debug("add default from: " + ORD_ORDER_ITEM_META);
					LOG.debug("add default where: ORD_SUB_SETTLEMENT_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID");
				}
			}
		}
		if (ordOrderItemMetaBusinessFlag) {
			if (material.getTableSet().isEmpty()) {
				material.getTableSet().add(ORD_ORDER_ITEM_META);
				material.getTableSet().add(ORD_ORDER);
				material.getConditionSet().add(
						"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default from: " + ORD_ORDER_ITEM_META);
					LOG.debug("add default from: " + ORD_ORDER);
					LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
				}
			}
		}
		if (ordSettlementBusinessFlag) {
			if (material.getTableSet().isEmpty()) {
				material.getTableSet().add(ORD_SETTLEMENT);
				material.getConditionSet().add("1 = 1");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default from: " + ORD_SETTLEMENT);
					LOG.debug("add default where: 1 = 1");
				}
			}
		}
		if (performDetailBusinessFlag
				&& (material.getTableSet().isEmpty() || !material
						.getOrderbySet().isEmpty())) {
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
			if(material.isDefaultOrderStauts()){
				material.getConditionSet()
						.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			}
			material.getConditionSet()
					.add("META_PRODUCT_BRANCH.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(" (( PASS_CODE.STATUS='SUCCESS' AND ORD_ORDER.PASSPORT='true') or ORD_ORDER.PASSPORT='false' )");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if (LOG.isDebugEnabled()) {
				LOG.debug("add default from: " + ORD_ORDER_ITEM_META);
				LOG.debug("add default from: " + SUP_SUPPLIER);
				LOG.debug("add default from: " + META_PRODUCT);
				LOG.debug("add default from: " + ORD_ORDER_ITEM_PROD);
				LOG.debug("add default from: " + ORD_ORDER);
				LOG.debug("add default from: " + META_PERFORM);
				LOG.debug("add default from: " + SUP_PERFORM_TARGET);
				LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
				if(material.isDefaultOrderStauts()){
					LOG.debug("add default where: ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
				}
				LOG.debug("add default where: META_PRODUCT.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
				LOG.debug("add default where: META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			}
		}
		if (passPortDetailBusinessFlag && material.getTableSet().isEmpty()) {
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
			if(material.isDefaultOrderStauts()){
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			}
			material.getConditionSet()
					.add("META_PRODUCT.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			if (LOG.isDebugEnabled()) {
				LOG.debug("add default from: " + ORD_ORDER_ITEM_META);
				LOG.debug("add default from: " + SUP_SUPPLIER);
				LOG.debug("add default from: " + META_PRODUCT_BRANCH);
				LOG.debug("add default from: " + ORD_ORDER_ITEM_PROD);
				LOG.debug("add default from: " + ORD_ORDER);
				LOG.debug("add default from: " + META_PERFORM);
				LOG.debug("add default from: " + SUP_PERFORM_TARGET);
				LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.META_BRANCH_ID = META_PRODUCT_BRANCH.META_BRANCH_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
				if(material.isDefaultOrderStauts()){
				LOG.debug("add default where: ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
				}
				LOG.debug("add default where: META_PRODUCT.META_PRODUCT_ID = META_PERFORM.META_PRODUCT_ID");
				LOG.debug("add default where: META_PERFORM.TARGET_ID = SUP_PERFORM_TARGET.TARGET_ID");
			}
		}
		if (passPortSummaryBusinessFlag && material.getTableSet().isEmpty()) {
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
			if(material.isDefaultOrderStauts()){
			material.getConditionSet()
					.add("((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
			}
			material.getGroupBySet().add("ORD_ORDER_ITEM_META.VISIT_TIME");
			if (LOG.isDebugEnabled()) {
				LOG.debug("add default from: " + ORD_ORDER_ITEM_META);
				LOG.debug("add default from: " + ORD_ORDER);
				LOG.debug("add default where: ORD_ORDER_ITEM_META.ORDER_ITEM_ID = ORD_ORDER_ITEM_PROD.ORDER_ITEM_PROD_ID");
				LOG.debug("add default where: ORD_ORDER_ITEM_PROD.ADDITIONAL = 'false'");
				if(material.isDefaultOrderStauts()){
				LOG.debug("add default where: ((ORD_ORDER.PAYMENT_TARGET = 'TOLVMAMA' AND ORD_ORDER.PAYMENT_STATUS = 'PAYED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')) OR (ORD_ORDER.PAYMENT_TARGET = 'TOSUPPLIER' AND ORD_ORDER.APPROVE_STATUS = 'VERIFIED' AND ORD_ORDER.ORDER_STATUS IN ('NORMAL', 'FINISHED')))");
				}
				LOG.debug("add default group by: ORD_ORDER_ITEM_META.VISIT_TIME");
			}
		}
		if (material.getTableSet().isEmpty()) {
			material.getTableSet().add(ORD_ORDER);
			if (LOG.isDebugEnabled()) {
				LOG.debug("add default from: " + ORD_ORDER);
			}
		}
		if (material.getConditionSet().isEmpty()) {
			material.getConditionSet().add("1 = 1");
			if (LOG.isDebugEnabled()) {
				LOG.debug("add default where: 1 = 1");
			}
		}
		if (material.getOrderbySet().isEmpty()) {
			if (ordOrderItemMetaBusinessFlag) {
				material.getOrderbySet().add(
						"ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID DESC");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default order by: ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID DESC");
				}
			}
			if (ordOrderBusinessFlag) {
				material.getOrderbySet().add("ORD_ORDER.ORDER_ID DESC");
				if (LOG.isDebugEnabled()) {
					LOG.debug("add default order by: ORD_ORDER.ORDER_ID DESC");
				}
			}
		}
		return material;
	}
}
