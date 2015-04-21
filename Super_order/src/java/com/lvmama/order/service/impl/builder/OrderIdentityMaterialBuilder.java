package com.lvmama.order.service.impl.builder;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.vo.ord.CompositeQuery.OrderIdentity;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.comm.vo.Constant;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 与订单相关ID有关的参数，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrderIdentityMaterialBuilder implements IMaterialBuilder,
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
		final OrderIdentity orderIdentity = (OrderIdentity) obj;
		
		if (UtilityTool.isValid(orderIdentity.getOrderId())) {
			material.getTableSet().add(ORD_ORDER);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = " + orderIdentity.getOrderId());
		}
		
		if (UtilityTool.isValid(orderIdentity.getOrderIds())) {
			material.getTableSet().add(ORD_ORDER);
			if(orderIdentity.getOrderIds().size()==1){
				material.getConditionSet().add(
						"ORD_ORDER.ORDER_ID = " + orderIdentity.getOrderIds().get(0));
			}else{
				StringBuffer sb =new StringBuffer();
				for(Long id:orderIdentity.getOrderIds()){
					sb.append(id);
					sb.append(",");
				}
				sb.setLength(sb.length()-1);
				material.getConditionSet().add(
						"ORD_ORDER.ORDER_ID in (" + sb.toString() +")");
			}
		}
		
		if (UtilityTool.isValid(orderIdentity.getOrgId())) {
			material.getConditionSet().add("ORD_ORDER.ORG_ID = " + orderIdentity.getOrgId());
		}
		
		if (UtilityTool.isValid(orderIdentity.getOperatorId())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(COM_AUDIT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = COM_AUDIT.OBJECT_ID");
			material.getConditionSet().add(
					"COM_AUDIT.OBJECT_TYPE = 'ORD_ORDER'");
			material.getConditionSet().add(
					"COM_AUDIT.OPERATOR_NAME = '"
							+ SecurityTool.preventSqlInjection(orderIdentity
									.getOperatorId()) + "'");
		}
		if (UtilityTool.isValid(orderIdentity.getItemOperatorId())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(COM_AUDIT);
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.ORDER_ID = ORD_ORDER.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.ORDER_ITEM_META_ID = COM_AUDIT.OBJECT_ID");
			material.getConditionSet().add(
					"COM_AUDIT.OBJECT_TYPE = 'ORD_ORDER_ITEM_META'");
			material.getConditionSet().add(
					"COM_AUDIT.AUDIT_STATUS = 'AUDIT_GOING'");
			material.getConditionSet().add(
					"COM_AUDIT.OPERATOR_NAME = '"
							+ SecurityTool.preventSqlInjection(orderIdentity
									.getItemOperatorId()) + "'");
		}
		if (UtilityTool.isValid(orderIdentity.getSupplierId())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getTableSet().add(META_PRODUCT);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet()
					.add("ORD_ORDER_ITEM_META.META_PRODUCT_ID = META_PRODUCT.META_PRODUCT_ID");
			material.getConditionSet().add(
					"META_PRODUCT.SUPPLIER_ID = "
							+ orderIdentity.getSupplierId());
		}
		if (UtilityTool.isValid(orderIdentity.getProductid())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_PROD.PRODUCT_ID = "
							+ orderIdentity.getProductid());
		}
		if (UtilityTool.isValid(orderIdentity.getProductIds())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_PROD);
			material.getConditionSet().add("ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_PROD.ORDER_ID");
			
			List<String> productIdsList=processUserName(orderIdentity.getProductIds());
			StringBuffer tempCondition=new StringBuffer("(ORD_ORDER_ITEM_PROD.PRODUCT_ID in ("); 
			for (int i=0,len=productIdsList.size();i<len;i++) {
				if(i>=1){
					tempCondition.append("OR ORD_ORDER_ITEM_PROD.PRODUCT_ID in ("+productIdsList.get(i)+")");
				}
				else{
					tempCondition.append(productIdsList.get(i)+")");	
				}
			}
			tempCondition.append(")");
			material.getConditionSet().add(tempCondition.toString());
		}
		if (UtilityTool.isValid(orderIdentity.getUserId())) {
			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
			material.getConditionSet().add(
					"ORD_ORDER.USER_ID = '"
							+ SecurityTool.preventSqlInjection(orderIdentity
									.getUserId()) + "'");
		}
		if (UtilityTool.isValid(orderIdentity.getMetaProductid())) {
			material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_ORDER_ITEM_META);
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_ID = ORD_ORDER_ITEM_META.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER_ITEM_META.META_PRODUCT_ID = "
							+ orderIdentity.getMetaProductid());
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
	
	/**
	 * 处理oracle in超过1000的问题
	 * @author ZHANG Nan
	 * @param productIdList
	 * @return
	 */
	private List<String> processUserName(List<Long> productIdList){
		List<String> productIds=new ArrayList<String>();
		StringBuffer productId=new StringBuffer();
		for (int i=0,len=productIdList.size();i<len;i++) {
			productId.append(productIdList.get(i)+",");
			if(i%Constant.ORACLE_IN_SIZE_LIMIT==0 && i!=0){
				productIds.add(productId.substring(0, productId.length()-1));
				productId=new StringBuffer();
			}
		}
		if(StringUtils.isNotBlank(productId)){
			productIds.add(productId.substring(0, productId.length()-1));	
		}
		else{
			productIds.add("''");
		}
		productId=new StringBuffer();
		return productIds;
	}
}
