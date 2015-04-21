package com.lvmama.order.service.impl.builder;


import com.lvmama.comm.bee.vo.ord.CompositeQuery.SaleServiceRelate;
import com.lvmama.comm.utils.SecurityTool;
import com.lvmama.comm.utils.UtilityTool;
import com.lvmama.order.po.SQlBuilderMaterial;
import com.lvmama.order.service.builder.TableName;

/**
 * SQL构建器原材料构建器.
 * 
 * <pre>
 * 投诉管理查询条件，此类必须使用AOP代理
 * </pre>
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class SaleServiceRelateMaterialBuilder implements IMaterialBuilder,
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
		final SaleServiceRelate saleServiceRelate = (SaleServiceRelate) obj;
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceOrderId())) {
			//material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_SALE_SERVICE);
			//material.getConditionSet().add(
				//	"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.ORDER_ID = "
							+ saleServiceRelate.getSaleServiceOrderId());
		}
		// 用户ID列表查询
		if (saleServiceRelate != null && saleServiceRelate.getUserIdList() != null && saleServiceRelate.getUserIdList().size() > 0) {
			//material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_SALE_SERVICE);
			//material.getConditionSet().add(
			//		"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER.USER_ID in (" + saleServiceRelate.getUserIdListWithSqlFormat() + ")");
		}
//		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceUserName())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(ORD_SALE_SERVICE);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.USER_NAME LIKE '"
//							+ SecurityTool
//									.preventSqlInjection(saleServiceRelate
//											.getSaleServiceUserName()) + "%'");
//		}
//		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceMobile())) {
//			material.getTableSet().add(ORD_ORDER);
//			material.getTableSet().add(ORD_SALE_SERVICE);
//			material.getTableSet().add(USR_USERS);
//			material.getConditionSet().add(
//					"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
//			material.getConditionSet().add(
//					"ORD_ORDER.USER_ID = USR_USERS.USER_ID");
//			material.getConditionSet().add(
//					"USR_USERS.MOBILE_NUMBER LIKE '"
//							+ SecurityTool
//									.preventSqlInjection(saleServiceRelate
//											.getSaleServiceMobile()) + "%'");
//		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceApplyName())) {
			//material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_SALE_SERVICE);
			//material.getConditionSet().add(
			//		"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.OPERATOR_NAME LIKE '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getSaleServiceApplyName()) + "%'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceOrderType())) {
			//material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_SALE_SERVICE);
			//material.getConditionSet().add(
			//		"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_ORDER.ORDER_TYPE = '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getSaleServiceOrderType()) + "'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSysCode())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.SYS_CODE = '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getSysCode()) + "'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceType())) {
			//material.getTableSet().add(ORD_ORDER);
			material.getTableSet().add(ORD_SALE_SERVICE);
			//material.getConditionSet().add(
			//		"ORD_ORDER.ORDER_ID = ORD_SALE_SERVICE.ORDER_ID");
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.SERVICE_TYPE = '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getSaleServiceType()) + "'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceCreateTimeStart())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.CREATE_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(saleServiceRelate
									.getSaleServiceCreateTimeStart())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleServiceCreateTimeEnd())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.CREATE_TIME <= TO_DATE('"
							+ UtilityTool.formatDate(saleServiceRelate
									.getSaleServiceCreateTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(saleServiceRelate.getSaleStatus())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.STATUS = '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getSaleStatus()) + "'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getTakenOperator())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.TAKEN_OPERATOR = '"
							+ SecurityTool
									.preventSqlInjection(saleServiceRelate
											.getTakenOperator()) + "'");
		}
		if (UtilityTool.isValid(saleServiceRelate.getTakenTimeEnd())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.TAKEN_TIME >= TO_DATE('"
							+ UtilityTool.formatDate(saleServiceRelate
									.getTakenTimeEnd())
							+ "','YYYY-MM-DD HH24:MI:SS')");
		}
		if (UtilityTool.isValid(saleServiceRelate.getTakenTimeBegin())) {
			material.getTableSet().add(ORD_SALE_SERVICE);
			material.getConditionSet().add(
					"ORD_SALE_SERVICE.TAKEN_TIME < TO_DATE('"
							+ UtilityTool.formatDate(saleServiceRelate
									.getTakenTimeBegin())
							+ "','YYYY-MM-DD HH24:MI:SS')");
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
