package com.lvmama.order.service.impl.builder;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderStatus;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与订单状态有关的参数，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderStatusMaterialBuilder implements IMaterialBuilder, TableName {
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
		final OrderStatus status = (OrderStatus) obj;
		if (UtilityTool.isValid(status.getPaymentStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_STATUS = '"
							+ SecurityTool.preventSqlInjection(status
									.getPaymentStatus().toString()) + "'");
		}
		if (UtilityTool.isValid(status.getTicketStatus())) {
			material.getTableSet().add(ORD_ORDER_ROUTE);
			material.getConditionSet().add(
			      "ORD_ORDER.ORDER_ID = ORD_ORDER_ROUTE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ROUTE.TRAFFIC_TICKET_STATUS = '"
							+ SecurityTool.preventSqlInjection(status
									.getTicketStatus().toString()) + "'");
		}
		/**
		 * 出团通知书状态
		 * @author nixianjun
		 */
		if (UtilityTool.isValid(status.getGroupWordStatus())) {
			material.getTableSet().add(ORD_ORDER_ROUTE);
			material.getConditionSet().add(
			      "ORD_ORDER.ORDER_ID = ORD_ORDER_ROUTE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ROUTE.GROUP_WORD_STATUS = '"
							+ SecurityTool.preventSqlInjection(status
									.getGroupWordStatus().toString()) + "'");
		}
		
		/**
		 * 期票订单状态
		 * @author zhangjie
		 */
		if (UtilityTool.isValid(status.getUseStatus())) {
			material.getTableSet().add("PASS_CODE");
			material.getTableSet().add("PASS_PORT_CODE");
			material.getConditionSet().add(
					"ORD_ORDER.IS_APERIODIC = '"+Constant.TRUE_FALSE.TRUE.getAttr1()+"'");
			material.getConditionSet().add(
				      "ORD_ORDER.ORDER_ID = PASS_CODE.Order_Id");
			material.getConditionSet().add(
					"PASS_CODE.Code_Id = PASS_PORT_CODE.Code_Id");
			material.getConditionSet().add(
					"PASS_PORT_CODE.Status = '"
							+ SecurityTool.preventSqlInjection(status
									.getUseStatus().toString()) + "'");
		}
		
		//
		if (UtilityTool.isValid(status.getOrderApproveStatus())) {
			material.getTableSet().add(ORD_ORDER);
			if (1 == status.getOrderApproveStatus().length) {
				material.getConditionSet().add(
						"ORD_ORDER.APPROVE_STATUS = '"
								+ status.getOrderApproveStatus()[0].toString()
								+ "'");
			} else {
				final StringBuilder stringBuilder = new StringBuilder();
				stringBuilder.append("ORD_ORDER.APPROVE_STATUS IN (");
				for (int i = 0; i < status.getOrderApproveStatus().length; i++) {
					stringBuilder.append("'"
							+ status.getOrderApproveStatus()[i].toString()
							+ "'");
					if (status.getOrderApproveStatus().length - 1 > i) {
						stringBuilder.append(", ");
					}
				}
				stringBuilder.append(")");
				material.getConditionSet().add(stringBuilder.toString());
			}
		}
		
		if (UtilityTool.isValid(status.getInfoApproveStatus())) {
			material.getTableSet().add(ORD_ORDER);
			if ( null != status.getInfoApproveStatus()) {
				material.getConditionSet().add(
						"ORD_ORDER.INFO_APPROVE_STATUS = '"
								+ status.getInfoApproveStatus().toString()
								+ "'");
			} 
		}
		if (UtilityTool.isValid(status.getOrderStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_STATUS = '"
							+ SecurityTool.preventSqlInjection(status
									.getOrderStatus().toString()) + "'");
		}
		if (UtilityTool.isValid(status.getOrderResourceLackReason())) {
			material.getTableSet().add(ORD_ORDER);
			String resourceLackReson = status.getOrderResourceLackReason().toString();
			if (resourceLackReson.equalsIgnoreCase(Constant.ORDER_RESOURCE_LACK_REASON.OTHER.name())) {
				material.getConditionSet()
						.add("ORD_ORDER.RESOURCE_LACK_REASON NOT IN ('"
								+ Constant.ORDER_RESOURCE_LACK_REASON.NO_RESOURCE.name()
								+ "','"
								+ Constant.ORDER_RESOURCE_LACK_REASON.PRICE_CHANGE.name()
								+ "','"
								+ Constant.ORDER_RESOURCE_LACK_REASON.UNABLE_MEET_REQUIREMENTS.name() + "')");
			}else {
				material.getConditionSet().add(
						"ORD_ORDER.RESOURCE_LACK_REASON = '"
								+ SecurityTool.preventSqlInjection(resourceLackReson)
								+ "'");
			}
			
		}
		if (UtilityTool.isValid(status.getOrderResourceStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.RESOURCE_STATUS = '"
							+ SecurityTool.preventSqlInjection(status
									.getOrderResourceStatus().toString()) + "'");
		}
		if (UtilityTool.isValid(status.getCertificateStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
			.add("ORD_ORDER_ITEM_META.CERTIFICATE_STATUS = '"
					+ SecurityTool.preventSqlInjection(status
							.getCertificateStatus()) + "'");
		}
 
		if (UtilityTool.isValid(status.getAuditTakenStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.TAKEN = '"
							+ status.getAuditTakenStatus().toString() + "'");
		}
		if (UtilityTool.isValid(status.getItemAuditTakenStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.TAKEN = '"
							+ status.getItemAuditTakenStatus().toString() + "'");
		}
		if (UtilityTool.isValid(status.getSpecialTakenStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"(ORD_ORDER.TAKEN = '"
							+ status.getSpecialTakenStatus().toString() + "' OR ORD_ORDER.TAKEN IS NULL)");
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
