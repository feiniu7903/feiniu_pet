package com.lvmama.order.service.impl.builder;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.ExcludedContent;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 表示订单排除条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class ExcludedContentMaterialBuilder implements IMaterialBuilder,
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
		final ExcludedContent excludedContent = (ExcludedContent) obj;
		if (UtilityTool.isValid(excludedContent.getOrderStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet()
					.add("ORD_ORDER.ORDER_STATUS <> '"
							+ excludedContent.getOrderStatus().toString() + "'");
		}
		if (!excludedContent.getOrderApproveStatus().isEmpty()) {
			material.getTableSet().add(ORD_ORDER);
			for (Constant.ORDER_APPROVE_STATUS orderApproveStatus : excludedContent.getOrderApproveStatus()) {
				material.getConditionSet().add(
						"ORD_ORDER.APPROVE_STATUS <> '"
								+ orderApproveStatus
										.name() + "'");
			}
			
		}
		if (UtilityTool.isValid(excludedContent.getPaymentStatus())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_STATUS <> '"
							+ excludedContent.getPaymentStatus().toString()
							+ "'");
		}
		if (excludedContent.isInSettlementQueue()) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
					.add("NOT EXISTS (SELECT 1 FROM ORD_SETTLEMENT_QUEUE_ITEM WHERE ORD_SETTLEMENT_QUEUE_ITEM.ORDER_ITEM_META_ID = ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID)");
		}
		if (UtilityTool.isValid(excludedContent.getPaymentTarget())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.PAYMENT_TARGET <> '"
							+ excludedContent.getPaymentTarget().toString()
							+ "'");
		}
 
		if(CollectionUtils.isNotEmpty(excludedContent.getOrderMetaIds())){
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			if(excludedContent.getOrderMetaIds().size()==1){
				material.getConditionSet().add("ORD_ORDER_ITEM_META.SUPPLIER_ID <>"+excludedContent.getOrderMetaIds().get(0));
			}else{
				StringBuffer sb =new StringBuffer();
				for(Long id:excludedContent.getOrderMetaIds()){
					sb.append(id);
					sb.append(",");
				}
				sb.setLength(sb.length()-1);
				material.getConditionSet().add("(ORD_ORDER_ITEM_META.SUPPLIER_ID  not in("+sb.toString()+"))");
			}
		}
		if(excludedContent.isUnPayAndNeedPrePay()){//是否要过滤掉必须用预授权的未支付的订单
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add("((ORD_ORDER.NEED_PRE_PAY = 'true' and ORD_ORDER.PAYMENT_STATUS = 'PAYED')or(ORD_ORDER.NEED_PRE_PAY = 'false'))");
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
