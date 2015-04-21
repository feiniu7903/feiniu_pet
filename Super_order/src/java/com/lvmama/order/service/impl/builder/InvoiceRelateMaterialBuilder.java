package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.InvoiceRelate;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 发票管理查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class InvoiceRelateMaterialBuilder implements IMaterialBuilder,
		TableName {
	
	void addSameSQL(final SQlBuilderMaterial material){
		material.getTableSet().add(ORD_INVOICE);
		material.getTableSet().add(ORD_INVOICE_RELATION);
		material.getConditionSet().add("ORD_INVOICE.INVOICE_ID = ORD_INVOICE_RELATION.INVOICE_ID(+)");
	}
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
		final InvoiceRelate invoiceRelate = (InvoiceRelate) obj;
		if (UtilityTool.isValid(invoiceRelate.getInvoiceNo())) {
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add(
					"ORD_INVOICE.INVOICE_NO = '"
							+ SecurityTool.preventSqlInjection(invoiceRelate
									.getInvoiceNo()) + "'");
		}
		if (UtilityTool.isValid(invoiceRelate.getOrderId())) {
			addSameSQL(material);
			material.getConditionSet().add(
					"ORD_INVOICE_RELATION.ORDER_ID = " + invoiceRelate.getOrderId());
		}
		if(UtilityTool.isValid(invoiceRelate.getTitle())){
			addSameSQL(material);
			material.getConditionSet().add("ORD_INVOICE.TITLE LIKE '%"+SecurityTool.preventSqlInjection(invoiceRelate.getTitle())+"%'");
		}
		if (UtilityTool.isValid(invoiceRelate.getStatus())){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.STATUS = '"+SecurityTool.preventSqlInjection(invoiceRelate.getStatus())+"'");
		}
		if (UtilityTool.isValid(invoiceRelate.getCompanyId())) {
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.COMPANY = '"+SecurityTool.preventSqlInjection(invoiceRelate.getCompanyId())+"'");
		}
		if(UtilityTool.isValid(invoiceRelate.getDeliveryType())){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.DELIVERY_TYPE = '"+SecurityTool.preventSqlInjection(invoiceRelate.getDeliveryType())+"'");
		}
		if(UtilityTool.isValid(invoiceRelate.getInvoiceId())){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.INVOICE_ID = "+invoiceRelate.getInvoiceId());
		}
		if(UtilityTool.isValid(invoiceRelate.getRedFlag())){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.RED_FLAG = '"+invoiceRelate.getRedFlag()+"'");
		}
		
		if(UtilityTool.isValid(invoiceRelate.getLogisticsStatus())){
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add("ORD_INVOICE.LOGISTICS_STATUS = '"+invoiceRelate.getLogisticsStatus()+"'");
		}
		
		if (UtilityTool.isValid(invoiceRelate.getUserId())) {
			material.getTableSet().add(ORD_INVOICE);
			material.getConditionSet().add(
					"ORD_INVOICE.USER_ID = '"
							+ SecurityTool.preventSqlInjection(invoiceRelate
									.getUserId()) + "'");
		}
//		if(material.getTableSet().contains(ORD_ORDER)){
//			addSameSQL(material);
//			material.getConditionSet().add("ORD_INVOICE_RELATION.ORDER_ID = ORD_ORDER.ORDER_ID");
//		}
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
