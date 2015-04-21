package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderContent;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与订单内容有关的参数，此类必须使用AOP代理
 * </pre>`
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderContentMaterialBuilder implements IMaterialBuilder, TableName {
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
		final OrderContent content = (OrderContent) obj;
		if (UtilityTool.isValid(content.getResourceConfirm())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.RESOURCE_CONFIRM = '"
							+ SecurityTool.preventSqlInjection(content
									.getResourceConfirm()) + "'");
		}
		if (UtilityTool.isValid(content.getFilialeName())){
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add("ORD_ORDER.FILIALE_NAME = '"+content.getFilialeName()+"'");
		}
		if (UtilityTool.isValid(content.getRedail())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.REDAIL = '"
							+ SecurityTool.preventSqlInjection(content
									.getRedail()) + "'");
		}
		if (UtilityTool.isValid(content.getNeedInvoice())) {
			final String[] array = content.getNeedInvoice().split(",");			
			material.getTableSet().add(ORD_ORDER);
			if(array.length==1){
				material.getConditionSet().add(
						"ORD_ORDER.NEED_INVOICE = '"
								+ SecurityTool.preventSqlInjection(content
										.getNeedInvoice()) + "'");
			} else {
				StringBuffer sb = new StringBuffer();

				for (String str : array) {
					sb.append("'");
					sb.append(SecurityTool.preventSqlInjection(str));
					sb.append("',");					
				}
				sb.setLength(sb.length()-1);
				material.getConditionSet().add("ORD_ORDER.NEED_INVOICE in("+sb.toString()+")");
			}
		}
		if (UtilityTool.isValid(content.getTravelCode())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.TRAVEL_GROUP_CODE = '"
							+ SecurityTool.preventSqlInjection(content
									.getTravelCode()) + "'");
		}
		if (UtilityTool.isValid(content.getProductType())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			final String[] array = content.getProductType().split(",");
			if (1 == array.length) {
				material.getConditionSet().add(
						"ORD_ORDER_ITEM_PROD.PRODUCT_TYPE = '"
								+ SecurityTool.preventSqlInjection(content
										.getProductType()) + "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("ORD_ORDER_ITEM_PROD.PRODUCT_TYPE IN (");
				int i = 0;
				for (String productType : array) {
					stringBuilder.append("'"
							+ SecurityTool.preventSqlInjection(productType)
							+ "'");
					if (array.length - 1 > i) {
						stringBuilder.append(", ");
					}
					i++;
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		if (UtilityTool.isValid(content.getOrderType())) {
			material.getTableSet().add(ORD_ORDER);
			if (1 == content.getOrderType().length) {
				material.getConditionSet().add(
						"ORD_ORDER.ORDER_TYPE = '"
								+ SecurityTool.preventSqlInjection(content
										.getOrderType()[0]) + "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("ORD_ORDER.ORDER_TYPE IN (");
				for (int i = 0; i < content.getOrderType().length; i++) {
					stringBuilder.append("'"
							+ SecurityTool.preventSqlInjection(content
									.getOrderType()[i]) + "'");
					if (content.getOrderType().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		if (UtilityTool.isValid(content.getVisitName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_PERSON);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
			// 王正艳 不区分类型 2010-12-08
			// material.getConditionSet().add("ORD_PERSON.PERSON_TYPE = '"
			// + ORD_PERSON_TYPE.TRAVELLER + "'");
			material.getConditionSet().add(
					"ORD_PERSON.NAME LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getVisitName()) + "%'");
		}
		if (UtilityTool.isValid(content.getVisitMobile())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_PERSON);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
			// // 王正艳 不区分类型 2010-12-08
			// material.getConditionSet().add("ORD_PERSON.PERSON_TYPE = '"
			// + ORD_PERSON_TYPE.TRAVELLER + "'");
			material.getConditionSet().add(
					"ORD_PERSON.MOBILE LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getVisitMobile()) + "%'");
		}
		if (UtilityTool.isValid(content.getContactName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_PERSON);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
			// 王正艳 不区分类型 2010-12-08
			// material.getConditionSet().add("ORD_PERSON.PERSON_TYPE = '"
			// + ORD_PERSON_TYPE.CONTACT + "'");
			material.getConditionSet().add(
					"ORD_PERSON.NAME LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getContactName()) + "%'");
		}
		if (UtilityTool.isValid(content.getContactMobile())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_PERSON);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_PERSON.OBJECT_ID");
			material.getConditionSet().add(
					"ORD_PERSON.OBJECT_TYPE = 'ORD_ORDER'");
			// 王正艳 不区分类型 2010-12-08
			// material.getConditionSet().add("ORD_PERSON.PERSON_TYPE = '"
			// + ORD_PERSON_TYPE.CONTACT + "'");
			material.getConditionSet().add(
					"ORD_PERSON.MOBILE LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getContactMobile()) + "%'");
		}
//		if (UtilityTool.isValid(content.getUserName())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.USER_NAME LIKE '"
//							+ SecurityTool.preventSqlInjection(content
//									.getUserName()) + "%'");
//		}
		if (UtilityTool.isValid(content.getUserId())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.USER_ID = '" + content.getUserId() + "'");
		}
		// 用户ID列表查询
		if (content != null && content.getUserIdList() != null && content.getUserIdList().size() > 0) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.USER_ID in (" + content.getUserIdListWithSqlFormat() + ")");
		}
//		if (UtilityTool.isValid(content.getMembershipCard())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.MEMBERSHIP_CARD = '"
//							+ SecurityTool.preventSqlInjection(content
//									.getMembershipCard()) + "'");
//		}
		if (UtilityTool.isValid(content.getPaymentTarget())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_TARGET = '"
							+ content.getPaymentTarget().toString() + "'");
		}
		if (UtilityTool.isValid(content.getPlaceName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(PROD_PRODUCT_PLACE);
			material.getTableSet().add(COM_PLACE);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_PROD.PRODUCT_ID = PROD_PRODUCT_PLACE.PRODUCT_ID");
			material.getConditionSet().add(
					"PROD_PRODUCT_PLACE.PLACE_ID = COM_PLACE.ID");
			material.getConditionSet().add(
					"COM_PLACE.NAME LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getPlaceName()) + "%'");
		}
		if (UtilityTool.isValid(content.getSubProductType())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			if (1 == content.getSubProductType().length) {
				material.getConditionSet().add(
						"ORD_ORDER_ITEM_PROD.SUB_PRODUCT_TYPE = '"
								+ content.getSubProductType()[0].toString()
								+ "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder
						.append("ORD_ORDER_ITEM_PROD.SUB_PRODUCT_TYPE IN (");
				for (int i = 0; i < content.getSubProductType().length; i++) {
					stringBuilder.append("'"
							+ content.getSubProductType()[i].toString() + "'");
					if (content.getSubProductType().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
//		if (UtilityTool.isValid(content.getEmail())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.EMAIL LIKE '"
//							+ SecurityTool.preventSqlInjection(content
//									.getEmail()) + "%'");
//		}
//		if (UtilityTool.isValid(content.getMobile())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.MOBILE_NUMBER LIKE '"
//							+ SecurityTool.preventSqlInjection(content
//									.getMobile()) + "%'");
//		}
		if (UtilityTool.isValid(content.getProductName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			// material.getTableSet().add(PROD_PRODUCT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			// material.getConditionSet()
			// .add("ORD_ORDER_ITEM_PROD.PRODUCT_ID = PROD_PRODUCT.PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.PRODUCT_NAME LIKE '%"
							+ SecurityTool.preventSqlInjection(content
									.getProductName()) + "%'");
		}
		if (UtilityTool.isValid(content.getMetaProductName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			// material.getTableSet().add(META_PRODUCT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			// material.getConditionSet()
			// .add("ORD_ORDER_ITEM_META.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.PRODUCT_NAME LIKE '%"
							+ SecurityTool.preventSqlInjection(content
									.getMetaProductName()) + "%'");
		}
		if (UtilityTool.isValid(content.getSupplierName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(SUP_SUPPLIER);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.SUPPLIER_ID = SUP_SUPPLIER.SUPPLIER_ID");
			material.getConditionSet().add(
					"SUP_SUPPLIER.SUPPLIER_NAME LIKE '"
							+ SecurityTool.preventSqlInjection(content
									.getSupplierName()) + "%'");
		}
		if (UtilityTool.isValid(content.getMetaProductType())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.PRODUCT_TYPE = '"
							+ SecurityTool.preventSqlInjection(content
									.getMetaProductType()) + "'");
		}
		if (UtilityTool.isValid(content.getIsCashRefund())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add("ORD_ORDER.CASH_REFUND IS NOT NULL");
			material.getConditionSet().add(
					"ORD_ORDER.IS_CASH_REFUND = '"
							+ SecurityTool.preventSqlInjection(content
									.getIsCashRefund()) + "'");
		}
		if (UtilityTool.isValid(content.getChannel())) {
			material.getTableSet().add(ORD_ORDER);
			if (1 == content.getChannel().length) {
				material.getConditionSet().add(
						"ORD_ORDER.CHANNEL = '"
								+ content.getChannel()[0].toString() + "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("ORD_ORDER.CHANNEL IN (");
				for (int i = 0; i < content.getChannel().length; i++) {
					stringBuilder.append("'"
							+ content.getChannel()[i].toString() + "'");
					if (content.getChannel().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		if (UtilityTool.isValid(content.getTakenOperator())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.TAKEN_OPERATOR = '"
							+ SecurityTool.preventSqlInjection(content
									.getTakenOperator()) + "'");
		}
		if (UtilityTool.isValid(content.getAssignOperator())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(COM_AUDIT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = COM_AUDIT.OBJECT_ID");
			material.getConditionSet().add(
					"COM_AUDIT.ASSIGN_USER_ID = '"
							+ content.getAssignOperator() + "'");
		}
		if (UtilityTool.isValid(content.getAssignOperators())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(COM_AUDIT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = COM_AUDIT.OBJECT_ID");
			if(content.getAssignOperators().length==1){
				material.getConditionSet().add(
						"COM_AUDIT.ASSIGN_USER_ID = '"
								+ content.getAssignOperators()[0] + "'");
			}else{
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("COM_AUDIT.ASSIGN_USER_ID IN (");
				for (int i = 0; i < content.getAssignOperators().length; i++) {
					stringBuilder.append("'"
							+ content.getAssignOperators()[i] + "'");
					if (content.getAssignOperators().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		
		//销售产品经理名称.
		if (UtilityTool.isValid(content.getProdProductManagerName())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getTableSet().add(PROD_PRODUCT);
			material.getTableSet().add(PERM_USER);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			material.getConditionSet().add(
			"ORD_ORDER_ITEM_PROD.PRODUCT_ID = PROD_PRODUCT.PRODUCT_ID");
			material.getConditionSet().add(
			"PROD_PRODUCT.MANAGER_ID = lvmama_pet.PERM_USER.USER_ID");
			material.getConditionSet().add(
			"(lvmama_pet.PERM_USER.REAL_NAME LIKE '%" + content.getProdProductManagerName() + "%' OR lvmama_pet.PERM_USER.USER_NAME LIKE '%" + content.getProdProductManagerName() + "%')");		
		}
		if(content.getSupplierFlag()!=null){
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add("ORD_ORDER_ITEM_META.SUPPLIER_FLAG = '"+content.getSupplierFlag()+"'");
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
